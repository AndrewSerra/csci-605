package homework_13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

/**
 * A UDP server processing multiple clients. If a connection is
 * made, there is a game created. If the user decides to not replay,
 * it is removed until a new connection is made.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class WordleMultiPlayerUDPServer {

    DatagramSocket socket;
    private final HashMap<String, Game> conn = new HashMap<>();
    private static final String DELIMITER = ":";
    static String host;
    int port;

    /**
     * Creates a UDP server and connects to DatagramSocket for a given
     * port. The filename reads in words to play in the game.
     *
     * @param filename Word list for the game
     * @param _host Host of the server as string
     * @param _port Port of the datagram socket to connect to
     */
    public WordleMultiPlayerUDPServer(String filename, String _host, int _port) {
        host = _host;
        port = _port;
        try {
            readWordsFromFile(filename);
            socket = new DatagramSocket(port);
            System.out.println ("Listening on port: " + socket.getLocalPort());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads in the words from the input file and loads the game
     * with the word set.
     *
     * @param fileName File name of the words to play in the game
     */
    private void readWordsFromFile(String fileName) {
        try (
                BufferedReader input = new BufferedReader(new FileReader(fileName));
        ) {
            String[] theWords = new String[10231];
            int counter = 0;
            System.out.println("Reading words from file...");
            while ((theWords[counter++] = input.readLine()) != null) {}
            Game.loadWords(theWords, counter-1);
        } catch (Exception e) {
            System.out.println("ExceptionType occurred: " + e.getMessage());
        }
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
     * Creates a datagram packet and sends to the client.
     *
     * @param data String containing the command and message to send to the client
     * @param serverHost The host name of the client as InetAddress
     * @param serverPort The port of the client as integer
     * @throws IOException
     */
    private void sendPacket(String data, InetAddress serverHost, int serverPort) throws IOException {
        byte[] buf = data.getBytes();
        DatagramPacket dp = new DatagramPacket(buf, buf.length, serverHost, serverPort);
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
     * Extract the address and the port of the data packet received. Address and
     * port are concatenated with a colon in between.
     *
     * @param dp Data packet received from a client
     * @return A string formatted with the address and port
     */
    private String getPacketSrc(DatagramPacket dp) {
        return String.format("%s:%s", dp.getAddress(), dp.getPort());
    }

    /**
     * For a recevied data packet, its source, content, and game saved, it
     * processes the incoming command and sends response to the user.
     *
     * @param p Data packet received from the client
     * @param key String containing the client address and port with a colon delimiter in between
     * @param content String array of the command and the message
     * @param clientGame The game created for the client
     * @throws IOException
     */
    private void processAction(
            DatagramPacket p, String key, String[] content, Game clientGame) throws IOException {
        switch (content[0]) {
            case "user_guess": {
                String respMsg;
                String feedbackStr = clientGame.evaluateGuess(content[1]);
                boolean isSuccessful = clientGame.isItSolved(feedbackStr);
                if(isSuccessful) {
                    respMsg = getMessageStr("success", feedbackStr);
                } else if(clientGame.isAtMaxAttempts()) {
                    respMsg = getMessageStr("game_fail", "_");
                } else {
                    respMsg = getMessageStr("failed_attempt", feedbackStr);
                }
                sendPacket(respMsg, p.getAddress(), p.getPort());
                conn.put(key, clientGame);
                break;
            }
            case "replay": {
                if(content[1].equals("false")) {
                    conn.remove(key);
                } else {
                    clientGame.reset();
                }
                break;
            }
            default: {
                System.out.println("Unknown command: " + content[0]);
                break;
            }
        }
    }

    /**
     * Runs the Wordle Game UDP server. The server execution ends if it crashes or
     * an interrupt is sent.
     */
    public void run() {
        while(true) {
            try {
                byte[] buf = new byte[512];
                DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);
                socket.receive(recvPacket);
                String[] msgContent = getPacketContents(buf, recvPacket.getLength());
                String key = getPacketSrc(recvPacket);

                if(!conn.containsKey(key)) {
                    conn.put(key, new Game(key));
                    continue;
                }

                Game clientGame = conn.get(key);
                processAction(recvPacket, key, msgContent, clientGame);
            } catch (IOException e) {
                System.out.println("Error running server. Exiting...");
                System.err.println(e.getMessage());
                System.exit(2);
            }
        }
    }

    public static void main(String[] args) {
        String _host = null;
        int _port = -1;
        for (int i = 1; i < args.length; i ++) {
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

        WordleMultiPlayerUDPServer wmp =
                new WordleMultiPlayerUDPServer(args[0], _host, _port);
        wmp.run();
    }
}
