package homework_13;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

class ClientWorker implements Runnable {

    Socket socket;
    int guessCount = 0;
    boolean didGameEnd = false;
    boolean shouldGameRestart = true;
    int id;
    static final int MAX_GUESS_COUNT = 5;
    static int soManyWordToPLayWith = 0;
    static String[] theWords = new String[10231];

    ClientWorker(Socket _socket, int _id) {
        socket = _socket;
        id = _id;
    }

    public static void loadWords(String[] words) {
        soManyWordToPLayWith = words.length - 1;
        theWords = words;
    }

    private String analyze(String theWordToShow, String guess) {
        StringBuilder rValue = new StringBuilder();
        for (int index = 0; index < guess.length(); index++) {
            char charAtPositionIndex = guess.charAt(index);
            if (charAtPositionIndex == theWordToShow.charAt(index))
                rValue.append("*");
            else if (theWordToShow.indexOf(charAtPositionIndex) > 0)
                rValue.append("_");
            else
                rValue.append("x");

        }
        return rValue.toString();

    }

    private boolean isItSolved(String progressSoFar) {
        int counter = 0;
        for (int index = 0; index < progressSoFar.length(); index++) {
            if (progressSoFar.charAt(index) == '*')
                counter++;
        }
        return progressSoFar.length() == counter;
    }

    private synchronized String getWord() {
        System.out.println("Getting a word...");
        return theWords[new Random().nextInt(soManyWordToPLayWith)];
    }

    private void resetGame() {
        guessCount = 0;
    }

    private synchronized void processRestartCmd(BufferedReader in) throws IOException {
        String[] replayRes = in.readLine().split(":");
        shouldGameRestart = !replayRes[1].equals("false");
    }

    private synchronized String beginGame(PrintWriter out) {
        System.out.printf("[%d] Starting new game...", id);
        String targetWord = getWord();
        System.out.printf("[%d] Game word: %s\n", id, targetWord);
        out.println("start_game");
        return targetWord;
    }

    private void runOneRound(BufferedReader in, PrintWriter out) throws IOException {
        String targetWord = beginGame(out);

        while (!didGameEnd) {
            // Being gameplay
            if(guessCount == MAX_GUESS_COUNT) {
                resetGame();
                out.println("game_fail");
                didGameEnd = true;
                processRestartCmd(in);
                continue;
            }

            String line = in.readLine();
            if (line == null) {
                break;
            }
            String[] lineComponents = line.split(":");
            String cmd = lineComponents[0], msg = lineComponents[1];

            if (cmd.equals("user_guess")) {
                String feedbackStr = analyze(targetWord, msg);
                boolean isSolved = isItSolved(feedbackStr);
                if (!isSolved) {
                    guessCount++;
                    out.println(String.format("failed_attempt:%s", feedbackStr));
                } else {
                    resetGame();
                    didGameEnd = true;
                    out.println(String.format("success:%s", feedbackStr));
                }
            }
        }
        processRestartCmd(in);
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(),
                        true)) {
                do {
                    didGameEnd = false;
                    runOneRound(in, out);
                } while (shouldGameRestart);

        } catch (Exception e) {
            System.out.println("Error with connection transactions.");
            System.out.println(e.getMessage());
            System.exit(3);
        }
    }
}

public class WordleMultiPlayerServer {

    ServerSocket server;
    int port = 1234;
    static int clientCount = 0;
    static final String[] theWords = new String[10231];

    public WordleMultiPlayerServer(String filename) {
        readWordsFromFile(filename);
        ClientWorker.loadWords(theWords);
    }

    public WordleMultiPlayerServer(String filename, int _port) {
        readWordsFromFile(filename);
        port = _port;
        ClientWorker.loadWords(theWords);
    }

    public void readWordsFromFile(String fileName) {
        try (
                BufferedReader input = new BufferedReader(new FileReader(fileName));
        ) {
            int counter = 0;
            System.out.println("Reading words from file...");
            while((theWords[counter++] = input.readLine()) != null);
        } catch (Exception e) {
            System.out.println("ExceptionType occurred: " + e.getMessage());
        }
    }

    public void run() {
        try {
            server = new ServerSocket(port);
            System.out.printf("Server starting: %s\n", server);
            System.out.println("Waiting for a connection...");
            while(true) {
                Socket socket = server.accept();
                System.out.printf(
                        "[%d] New active socket connection: %s\n",
                        ++clientCount,
                        socket.toString());
                new Thread(new ClientWorker(socket, clientCount)).start();
            }
        } catch (Exception e) {
            System.out.println("Server cannot be started. Exiting...");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Incorrect number of arguments.");
            System.out.println("Usage: java WordleSinglePlayerServer " +
                    "<filename>");
            System.exit(2);
        }
        WordleMultiPlayerServer wordleServer =
                new WordleMultiPlayerServer(args[0]);
        wordleServer.run();
    }
}
