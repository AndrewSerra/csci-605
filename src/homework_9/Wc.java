package homework_9;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * This is a class that counts number of words, bytes,
 * and lines in a given file. Optional arguments allows
 * respective counts to be printed.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class Wc {

    private String fileName;
    private final HashMap<String, Boolean> options;
    private int byteCount = 0;
    private int wordCount = 0;
    private int lineCount = 0;

    /**
     * Creates a Wc instance with defaults of false to
     * all the optional arguments.
     */
    public Wc() {
        options = new HashMap<>() {{
            put("-l", false);
            put("-w", false);
            put("-c", false);
            put("-m", false);
        }};
    }

    /**
     * Prints the result counts in the order lines, bytes,
     * and characters. If the option is marked to be true,
     * it is appended to the result string.
     */
    private void printResults() {
        StringBuilder sb = new StringBuilder();

        if(options.get("-l")) {
            sb.append("\t").append(lineCount);
        }
        if(options.get("-w")) {
            sb.append("\t").append(wordCount);
        }
        if(options.get("-c")) {
            sb.append("\t").append(byteCount);
        }
        if(options.get("-m")) {
            sb.append("\t").append(byteCount);
        }

        if(sb.length() == 0){
            sb.append("\t").append(lineCount);
            sb.append("\t").append(wordCount);
            sb.append("\t").append(byteCount);
        }

        sb.append("\t").append(fileName);
        System.out.println(sb);
    }

    /**
     * Prints the usage of this program with the order of
     * arguments and options.
     */
    private static void printHelp() {
        System.out.println("Usage: java Wc <filename> [-l] [-w] [-c] [-m]");
    }

    /**
     * Populates the instance variables of the class for the
     * given command line arguments.
     *
     * @param args Command line arguments passed to the program
     */
    private void parseOptArgs(String[] args) {
        int i = 0; // Skip the positional file name

        while (i < args.length-1) {
            if((args[i].charAt(0) == '-') && options.containsKey(args[i])) {
                options.put(args[i], true);
            }
            i++;
        }
    }

    /**
     * Runs until the stream is completed fully and counts the lines,
     * bytes, and characters, in the stream from a given file.
     */
    private void count() {
        try ( FileInputStream fs = new FileInputStream(fileName) ) {
            int data;
            boolean prevIsWs = false;
            // Continue looping if there are more bytes of data left
            while((data = fs.read()) != -1) {
                byteCount++;
                boolean isLast = (byteCount > 0) && (fs.available() == 0);
                if((char)data == '\n' || isLast) {
                    lineCount++;
                }

                if((!prevIsWs) &&
                        ((char)data == ' ') || ((char)data == '\n') || isLast) {
                    wordCount++;
                    prevIsWs = true;
                } else {
                    prevIsWs = false;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " cannot be found.");
            System.exit(1);
        } catch (Exception e) {
            System.out.println(
                    "Exception occurred during operation: " + e.getMessage());
            System.exit(2);
        }
    }

    /**
     * Executes the program for a given file and options.
     *
     * @param args Command line arguments passed to the program
     */
    public void run(String[] args) {
            if(args.length == 0) {
                throw new IllegalArgumentException(
                        "Arguments should contain at least the file name.");
            }
            fileName = args[args.length-1];
            parseOptArgs(args);
            count();
            printResults();
    }

    public static void main(String[] args) {
        try {
            new Wc().run(args);
        } catch (IllegalArgumentException e) {
            printHelp();
        }
    }
}
