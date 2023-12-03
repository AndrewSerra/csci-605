package homework_13;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class WordleClient {

    private Socket socket;
    private final String host;
    private final int port;
    private final Scanner userInput = new Scanner(System.in);

    public WordleClient(String _host, int _port) {
        host = _host;
        port = _port;
    }

    private void connectToGameServer() {
        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            System.out.println("Error connecting to socket.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void printInstructions() {
        System.out.println("_ indicates the letter is in the word but in the wrong spot.");
        System.out.println("* indicates the letter is in the word and correct spot.");
        System.out.println("x indicates the letter is not in the word.");
        System.out.println("Try to guess the word in 5 tries.");
    }

    private String readUserInput() {
        String guess = "";
        do {
            System.out.print(" > ");
            if  ( userInput.hasNext() )	{
                guess = userInput.nextLine();
            }
        } while ( guess.length() != 5 );
        return guess;
    }

    private boolean askForReplay() {
        boolean continueGame = false;
        String response = "";

        do {
            System.out.print("Do you want to play another game? (y/n) ");
            if(userInput.hasNext()) {
                response = userInput.nextLine();
            }
        } while(!Pattern.matches("^[yn]$", response));

        if(response.equals("y")) {
            continueGame = true;
        }
        return continueGame;
    }

    private void playOneRound(BufferedReader in, PrintWriter out) throws IOException {
        String line;
        int guessCount = 0;
        boolean isRoundCompleted = false;

        label:
        while(!isRoundCompleted) {
            String userGuess = readUserInput();
            String serverMsg = String.format("user_guess:%s", userGuess);
            out.println(serverMsg);
            line = in.readLine();
            String[] lineComponents = line.split(":");
            switch (lineComponents[0]) {
                case "success":
                    System.out.println("Correct guess. Word found!");
                    isRoundCompleted = true;
                    break;
                case "failed_attempt":
                    System.out.printf("Result: %s\tGuess Number: %d\n",
                            lineComponents[1], (++guessCount));
                    break;
                case "game_fail":
                    System.out.println("Reached maximum attempts. Failed.");
                    isRoundCompleted = true;
                    break;
            }
        }
    }

    private void play() {
        connectToGameServer();
        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            printInstructions();

            while(true) {
                String line = in.readLine();
                if(line == null) break;
                if(!line.equals("start_game")) {
                    continue;
                }
                System.out.println("Starting game!");
                playOneRound(in, out);
                boolean goingToReplay = askForReplay();

                if(!goingToReplay) {
                    out.println("replay:false");
                    System.out.println("Thank you playing. Bye!");
                    break;
                }
                out.println("replay:true");
            }
        } catch (Exception e) {
            System.out.println("Error with connection transactions.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;
        new WordleClient(host, port).play();
    }
}
