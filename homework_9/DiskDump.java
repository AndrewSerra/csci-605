package homework_9;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a simplified implementation of the dd command
 * in unix. The tool will copy contents from an input stream of a
 * file or system.in to either an output file or system.out
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class DiskDump {

    private final HashMap<String, String> options;
    private int bsBlockCount = 0;
    private int bsIncBlockCount = 0;
    private int totalProcessedBytes = 0;

    /**
     * Creates a DiskDump object and initializes the
     * options with default values.
     */
    public DiskDump() {
        options = new HashMap<>() {{
            put("if", "-"); // Uses stdin by default
            put("of", "-"); // Uses stdout by default
            put("bs", "64");
            put("skip", "0");
        }};
    }

    /**
     * Prints the usage of this program with the order of
     * arguments and options.
     */
    private static void printHelp() {
        System.out.println(
                "Usage: java DiskDump <filename> [-if=FILE] [-of=FILE] [-bs=BYTES] [skip=N]");
    }

    /**
     * The counts of the total processed bytes, block count, and
     * the incomplete block counts are printed.
     */
    private void printOutcome() {
        System.out.printf("Total bytes processed: %d\n", totalProcessedBytes);
        System.out.printf("Complete blocks: %d\n", bsBlockCount);
        System.out.printf("Partial blocks: %d\n", bsIncBlockCount);
    }

    /**
     * Verifies if a value is numeric.
     *
     * @param value String value expected to have only digits
     * @return True if the value is an integer, false if not
     */
    private static boolean isNumericParam(String value) {
        return Pattern.matches("^\\d*$", value);
    }

    /**
     * Breaks down a command line argument and its value using
     * regex.
     *
     * @param arg Command line argument as a key-value pair
     * @return A Matcher object containing the groups of with a
     *         key and value.
     */
    private static Matcher getArgValue(String arg) {
        Pattern p = Pattern.compile(
                "^(if|of|bs|skip)=\"?([^\"]+|-|\\d+)\"?$");
        return p.matcher(arg);
    }

    /**
     * Populates the instance variables of the class for the
     * given command line arguments.
     *
     * @param args Command line arguments passed to the program
     */
    private void parseOptArgs(String[] args) {
        int i = 0; // Skip the positional file name

        while (i < args.length) {
            Matcher matcher = getArgValue(args[i]);

            if(!matcher.find()) {
                System.out.printf("Unknown argument \"%s\".\n", args[i++]);
                continue;
            }

            String argname = matcher.group(1);
            String argvalue = matcher.group(2);

            if(argname.equals("skip") || argname.equals("bs")) {
                if(!isNumericParam(argvalue)) {
                    throw new IllegalArgumentException(
                            String.format(
                                    "Argument \"%s\" with value \"%s\" does not have a numeric value.",
                                    argname, argvalue));
                }
            }
            options.put(argname, argvalue);
            i++;
        }
    }

    /**
     * Depending on if option in the command line arguments,
     * an BufferedInputStream is returned. It is either based
     * on System.in or a FileInputStream.
     *
     * @return InputStream based off of System.in or FileInputStream
     * @throws FileNotFoundException
     */
    private InputStream getInputStream() throws FileNotFoundException {
        int byteSize = Integer.parseInt(options.get("bs"));
        if(options.get("if").equals("-")) {
            return new BufferedInputStream(System.in, byteSize);
        }
        return new BufferedInputStream(
                new FileInputStream(options.get("if")), byteSize);
    }

    /**
     * Depending on if option in the command line arguments,
     * an OutputStream is returned. It is either PrintStream or
     * FileOutputStream.
     *
     * @return OutputStream based off of System.out or FileOutputStream
     * @throws IOException
     */
    private OutputStream getOutputStream() throws IOException {
        if(options.get("of").equals("-")) {
            return new PrintStream(System.out);
        }
        try {
            return new FileOutputStream(options.get("of"));
        } catch (FileNotFoundException e) {
            File newOutfile = new File(options.get("of"));
            if(newOutfile.createNewFile()) {
                return new FileOutputStream(newOutfile);
            } else {
                throw new RuntimeException("Cannot create new output file.");
            }
        }
    }

    /**
     * Executes the program for a given file and options.
     *
     * @param args Command line arguments passed to the program
     */
    public void run(String[] args) {
        parseOptArgs(args);

        try (
                InputStream input = getInputStream();
                OutputStream output = getOutputStream() ) {
            int size = Integer.parseInt(options.get("bs"));
            byte[] b = new byte[Math.min(input.available(), size)];
            long skip = input.skip(
                    (long) size * Integer.parseInt(options.get("skip")));
            int bytesRead;
            while( (bytesRead = input.read(b)) != -1 ) {
                output.write(b, 0 ,bytesRead);
                if(bytesRead == size) {
                    bsBlockCount += 1;
                } else {
                    bsIncBlockCount += 1;
                }
                totalProcessedBytes += bytesRead;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error creating output file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error found: " + e.getMessage());
        } finally {
            printOutcome();
        }
    }

    public static void main(String[] args) {
        try {
            new DiskDump().run(args);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            printHelp();
        }
    }
}
