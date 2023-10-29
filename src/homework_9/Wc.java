package homework_9;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 *
 */
public class Wc {

    private String fileName;
    private final HashMap<String, Boolean> options;
    private int byteCount = 0;
    private int charCount = 0;
    private int lineCount = 0;

    public Wc() {
        options = new HashMap<>() {{
            put("-l", false);
            put("-c", false);
            put("-m", false);
        }};
    }

    /**
     *
     */
    private void printResults() {
        StringBuilder sb = new StringBuilder();

        if(options.get("-l")) {
            sb.append(lineCount);
        } else if(options.get("-c")) {
            sb.append(charCount);
        } else if(options.get("-m")) {
            sb.append(byteCount);
        } else {
            sb.append("\t").append(lineCount);
            sb.append("\t").append(charCount);
            sb.append("\t").append(byteCount);
        }

        sb.append("\t").append(fileName);
        System.out.println(sb);
    }

    /**
     *
     */
    private static void printHelp() {
        System.out.println("Usage: java Wc <filename> [-l] [-c] [-m]");
    }

    /**
     *
     * @param args
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
     *
     */
    private void count() {
        try ( FileInputStream fs = new FileInputStream(fileName); ) {
            int data;
            byteCount = fs.available();
            // Continue looping if there are more bytes of data left
            while((data = fs.read()) != -1) {
                if((char)data == '\n') {
                    lineCount++;
                }
                if(((char)data == ' ') || ((char)data == '\n')) {
                    charCount++;
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
     *
     * @param args
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
