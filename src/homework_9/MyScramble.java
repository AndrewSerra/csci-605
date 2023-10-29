package homework_9;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 */
public class MyScramble {

    private boolean isScrambleMode;
    private String filename;

    /**
     *
     */
    private void printHelp() {
        System.out.println(
                "Usage: java MyScramble <-scramble|-descramble> <filename>");
    }

    /**
     *
     * @param args
     */
    private void parseArgs(String[] args) {
        if(args.length != 2) {
            printHelp();
            throw new IllegalArgumentException("Wrong amount of arguments.");
        }

        if((args[0].equals("-scramble")) || (args[0].equals("-descramble"))) {
            isScrambleMode = args[0].equals("-scramble");
        } else {
            throw new IllegalArgumentException("Invalid option: " + args[0]);
        }

        filename = args[1];
    }

    /**
     *
     * @param b
     * @return
     */
    private Integer scramble(int b) {
        if(String.valueOf((char)b).matches(System.getProperty("line.separator"))) {
            return b;
        }
        return ((((b + 5) * 2) - 6) / 2);
    }

    /**
     *
     * @param b
     * @return
     */
    private Integer descramble(int b) {
        if(String.valueOf((char)b).matches(System.getProperty("line.separator"))) {
            return b;
        }
        return (((b * 2) + 6) / 2) - 5;
    }

    /**
     *
     * @param args
     */
    public void run(String[] args) {
        parseArgs(args);

        try ( FileReader input = new FileReader(filename);
              PrintWriter output = new PrintWriter(System.out) ) {
            int data;
            while((data = input.read()) != -1) {
                int wd = (isScrambleMode ? scramble(data) : descramble(data));
                output.write(wd);
            }
        } catch (IOException e) {
            System.out.println("Error encountered reading file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        (new MyScramble()).run(args);
    }
}
