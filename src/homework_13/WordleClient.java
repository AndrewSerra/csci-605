package homework_13;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A TCP Client playing the game of Wordle.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class WordleClient {

    private Socket socket;
    private final String host;
    private final int port;
    private final Scanner userInput = new Scanner(System.in);

    /**
     * Creates a TCP client.
     *
     * @param _host Host of the server as string
     * @param _port Port of the datagram socket to connect to
     */
    public WordleClient(String _host, int _port) {
        host = _host;
        port = _port;
    }

    /**
     * Connects to the socket of the server.
     */
    private void connectToGameServer() {
        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            System.out.println("Error connecting to socket.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Prints the game instructions to the user.
     */
    private static void printInstructions() {
        System.out.println("_ indicates the letter is in the word but in the wrong spot.");
        System.out.println("* indicates the letter is in the word and correct spot.");
        System.out.println("x indicates the letter is not in the word.");
        System.out.println("Try to guess the word in 5 tries.");
    }

    /**
     * Reads in user guesses and verifies that it has length of 5.
     *
     * @return The string guess of length 5.
     */
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

    /**
     * Asks the user if they want to replay the game. Verifies that only "y" or "n"
     * is entered.
     *
     * @return Boolean value, true if "y", false if "n"
     */
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

    /**
     * Runs the game for one round until the guess is successful or the guess
     * count is at 5.
     *
     * @param in A BufferReader connected to the socket
     * @param out A PrinterWriter connected to the socket
     * @throws IOException
     */
    private void playOneRound(BufferedReader in, PrintWriter out) throws IOException {
        String line;
        int guessCount = 0;
        boolean isRoundCompleted = false;

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

    /**
     * Runs the Wordle Game TCP Client. The client execution ends if it crashes, the client
     * decides to not replay, or communication is lost.
     */
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
