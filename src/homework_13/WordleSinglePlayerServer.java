package homework_13;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Single player wordle game with communication over TCP.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class WordleSinglePlayerServer {
    int port = 1234;
    int guessCount = 0;
    boolean didGameEnd = false;
    boolean shouldGameRestart = true;
    static int soManyWordToPLayWith = 0;
    static final String[] theWords = new String[10231];
    static final int MAX_GUESS_COUNT = 5;

    /**
     * Creates a TCP server for the wordle game.
     *
     * @param filename File name of the document containing the word list
     */
    public WordleSinglePlayerServer(String filename) {
        readWordsFromFile(filename);
    }

    /**
     * Read the file and saved them in word array. Updates the word count.
     *
     * @param fileName File name of the document containing the word list
     */
    public void readWordsFromFile(String fileName) {
        try (
                BufferedReader input = new BufferedReader(new FileReader(fileName))
        ) {
            int counter = 0;
            System.out.println("Reading words from file...");
            while ((theWords[counter++] = input.readLine()) != null)
                soManyWordToPLayWith++;
        } catch (Exception e) {
            System.out.println("ExceptionType occurred: " + e.getMessage());
        }
    }

    /**
     * Marks the characters correctly guessed depending on position.
     *
     * @param theWordToShow The target word to compare against
     * @param guess The user guess to verify
     * @return A feedback string containing the correctly guessed characters
     */
    public String analyze(String theWordToShow, String guess) {
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

    /**
     * Checks if the feedback string is fully guessed correctly.
     *
     * @param progressSoFar The feedback string
     * @return True if the string is completed, else False
     */
    public boolean isItSolved(String progressSoFar) {
        int counter = 0;
        for (int index = 0; index < progressSoFar.length(); index++) {
            if (progressSoFar.charAt(index) == '*')
                counter++;
        }
        return progressSoFar.length() == counter;
    }

    /**
     * Gets a new word to play from the word list.
     *
     * @return The selected word string
     */
    public String getWord() {
        System.out.println("Getting a word...");
        return theWords[new Random().nextInt(soManyWordToPLayWith)];
    }

    /**
     * Resets the game for a new start.
     */
    private void resetGame() {
        guessCount = 0;
    }

    /**
     * Checks if the user wants to replay.
     *
     * @param in A BufferReader connected to the socket
     * @throws IOException
     */
    private void processRestartCmd(BufferedReader in) throws IOException {
        String[] replayRes = in.readLine().split(":");
        shouldGameRestart = !replayRes[1].equals("false");
    }

    /**
     * Runs the game for one round until the guess is successful or the guess
     * count is at 5.
     *
     * @param in A BufferReader connected to the socket
     * @param out A PrinterWriter connected to the socket
     * @throws IOException
     */
    private void runOneRound(BufferedReader in, PrintWriter out) throws IOException {
        System.out.println("Starting new game...");
        String targetWord = getWord();
        System.out.println("Game word: " + targetWord);
        out.println("start_game");

        while (!didGameEnd) {
            // Being gameplay
            if(guessCount == (MAX_GUESS_COUNT-1)) {
                resetGame();
                out.println("game_fail");
                didGameEnd = true;
                processRestartCmd(in);
                continue;
            }
            String line = in.readLine();
            if (line == null) {
                break;
            } else {
                String[] lineComponents = line.split(":");
                String cmd = lineComponents[0], msg = lineComponents[1];

                if(cmd.equals("user_guess")) {
                    String feedbackStr = analyze(targetWord, msg);
                    boolean isSolved = isItSolved(feedbackStr);
                    System.out.println(cmd + " " + feedbackStr + " " + isSolved);
                    if(!isSolved) {
                        guessCount++;
                        out.println(String.format("failed_attempt:%s", feedbackStr));
                    } else {
                        resetGame();
                        didGameEnd = true;
                        out.println(String.format("success:%s", feedbackStr));
                    }
                }
            }
        }
        processRestartCmd(in);
    }

    /**
     * Runs the Wordle Game TCP server. The server execution ends if it crashes, the client
     * decides to not replay, or communication is lost.
     */
    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.printf("Server starting: %s\n", server);
            System.out.println("Waiting for a connection...");
            Socket socket = server.accept();
            System.out.printf(
                    "New active socket connection: %s\n", socket.toString());
            try (
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(),
                            true) ) {
                do {
                    didGameEnd = false;
                    runOneRound(in, out);
                } while(shouldGameRestart);
            } catch (Exception e) {
                System.out.println("Error with connection transactions.");
                System.out.println(e.getMessage());
                System.exit(3);
            }
            socket.close();
            server.close();
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
        WordleSinglePlayerServer wordleServer =
                new WordleSinglePlayerServer(args[0]);
        wordleServer.run();
    }
}
