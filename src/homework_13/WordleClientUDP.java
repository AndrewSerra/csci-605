package homework_13;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A UDP Client playing the game of Wordle.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class WordleClientUDP {

    private DatagramSocket socket;
    private InetAddress host, serverHost;
    private int port, serverPort;
    boolean didGameEnd = false;
    private static final String DELIMITER = ":";
    private final Scanner userInput = new Scanner(System.in);

    /**
     * Creates a UDP client and connects to a datagram socket.
     *
     * @param _host Host of the server as string
     * @param _port Port of the datagram socket to connect to
     */
    public WordleClientUDP(String _host, int _port) {
        try {
            host = InetAddress.getByName(_host);
            port = _port;
            serverHost = InetAddress.getByName("localhost");
            serverPort = 1234;
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.err.println(e.getMessage());
            System.exit(2);
        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
            System.exit(3);
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
     * Creates the initial communication data packet. The command is "conn_game".
     *
     * @return A datagram packet with the "conn_game" command
     */
    private DatagramPacket getGameServerInitPacket() {
        String data = "conn_game:_";
        byte[] buf = data.getBytes();
        return new DatagramPacket(buf, buf.length, serverHost, serverPort);
    }

    /**
     * Extracts the data received in the packet.
     *
     * @param buf A byte array storing the data
     * @param len The length of the data occupying in buf
     * @return A string array containing the command and message from client
     */
    private String[] getPacketContents(byte[] buf,int len) {
        String content = new String(buf, 0, len);
        return content.split(DELIMITER);
    }

    /**
     * Waits for a package to arrive and extracts the contents as a length 2
     * String array.
     *
     * @return String array consisting of a command and a message
     * @throws IOException
     */
    private String[] receivePacket() throws IOException {
        byte[] buf = new byte[512];
        DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);
        socket.receive(recvPacket);
        return getPacketContents(buf, recvPacket.getLength());
    }

    /**
     * Creates a datagram packet and sends to the client.
     *
     * @param data String containing the command and message to send to the client
     * @throws IOException
     */
    private void sendPacket(String data) throws IOException {
        byte[] buf = data.getBytes();
        DatagramPacket dp = new DatagramPacket(buf, buf.length, serverHost, serverPort);
        socket.send(dp);
    }

    /**
     * Creates a datagram packet and sends to the client.
     *
     * @param dp The datagram package to send over the socket
     * @throws IOException
     */
    private void sendPacket(DatagramPacket dp) throws IOException {
        socket.send(dp);
    }

    /**
     * Creates a string formatted as "<command>:<message>". An empty message
     * is marked as a "_".
     *
     * @param command The command to use as string
     * @param msg The message to use as string
     * @return Formatted string of the command and message using delimiter
     */
    private String getMessageStr(String command, String msg) {
        return String.format("%s:%s", command, msg);
    }

    /**
     * Runs the game for one round until the guess is successful or the guess
     * count is at 5.
     *
     * @throws IOException
     */
    private void runOneRound() throws IOException {
        int guessCount = 0;
        boolean isRoundCompleted = false;
        System.out.println("Starting game!");

        while(!isRoundCompleted) {
            String userGuess = readUserInput();
            String serverMsg = getMessageStr("user_guess", userGuess);
            sendPacket(serverMsg);
            String[] msg = receivePacket();

            switch (msg[0]) {
                case "success":
                    System.out.println("Correct guess. Word found!");
                    isRoundCompleted = true;
                    break;
                case "failed_attempt":
                    System.out.printf("Result: %s\tGuess Number: %d\n",
                            msg[1], (++guessCount));
                    break;
                case "game_fail":
                    System.out.println("Reached maximum attempts. Failed.");
                    isRoundCompleted = true;
                    break;
            }
        }
    }

    /**
     * Runs the Wordle Game until the user decides not to replay.
     */
    private void play() {
        printInstructions();
        boolean isReplayed = false;
        while(true) {
            DatagramPacket startGamePacket = getGameServerInitPacket();
            try {
                if(!isReplayed) {
                    sendPacket(startGamePacket);
                }

                didGameEnd = false;
                runOneRound();
                boolean goingToReplay = askForReplay();

                if(!goingToReplay) {
                    sendPacket(getMessageStr("replay", "false"));
                    System.out.println("Thank you playing. Bye!");
                    break;
                }
                isReplayed = true;
                sendPacket(getMessageStr("replay", "true"));
            } catch(IOException e) {
                System.out.println("Error with connection transactions.");
                System.out.println(e.getMessage());
                System.exit(4);
            }
        }
    }

    public static void main(String[] args) {
        String _host = null;
        int _port = -1;
        for (int i = 0; i < args.length; i ++) {
            if (args[i].equals("-host"))
                _host = args[++i];
            else if (args[i].equals("-port")) {
                _port = Integer.parseInt(args[++i]);
            }
        }
        if(_host == null || _port == -1) {
            System.out.println("Host or port not provided in cli.");
            System.exit(1);
        }

        WordleClientUDP wmp = new WordleClientUDP(_host, _port);
        wmp.play();
    }
}
