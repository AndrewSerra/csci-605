package homework_9;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class DiskDump {

    private final HashMap<String, String> options;
    private int bsBlockCount = 0;
    private int bsIncBlockCount = 0;
    private int totalProcessedBytes = 0;

    public DiskDump() {
        options = new HashMap<>() {{
            put("if", "-"); // Uses stdin by default
            put("of", "-"); // Uses stdout by default
            put("bs", "64");
            put("skip", "0");
        }};
    }

    /**
     *
     */
    private static void printHelp() {
        System.out.println(
                "Usage: java DiskDump <filename> [-if=FILE] [-of=FILE] [-bs=BYTES] [skip=N]");
    }

    /**
     *
     */
    private void printOutcome() {
        System.out.printf("Total bytes processed: %d\n", totalProcessedBytes);
        System.out.printf("Complete blocks: %d\n", bsBlockCount);
        System.out.printf("Partial blocks: %d\n", bsIncBlockCount);
    }

    /**
     *
     * @param value
     * @return
     */
    private static boolean isNumericParam(String value) {
        return Pattern.matches("^\\d*$", value);
    }

    /**
     *
     * @param arg
     * @return
     */
    private static Matcher getArgValue(String arg) {
        Pattern p = Pattern.compile(
                "^(if|of|bs|skip)=\"?([^\"]+|-|\\d+)\"?$");
        return p.matcher(arg);
    }

    /**
     *
     * @param args
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
     *
     * @return
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
     *
     * @return
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
     *
     * @param args
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
