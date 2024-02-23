package homework_9;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is a class that transforms the data using swapping
 * of elements. The elements are read in from a file and
 * written to standard output. After multiple shifts, an
 * already scrambled document is descrambled.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class MyScramble {

    private String filename;
    private final List<String> docList = new ArrayList<>();

    /**
     * Prints the usage of this program with the order of
     * arguments and options.
     */
    private void printHelp() {
        System.out.println(
                "Usage: java MyScramble <-scramble|-descramble> <filename>");
    }

    /**
     * Populates the instance variables of the class for the
     * given command line arguments.
     *
     * @param args Command line arguments passed to the program
     */
    private void parseArgs(String[] args) {
        if(args.length != 2) {
            printHelp();
            throw new IllegalArgumentException("Wrong amount of arguments.");
        }

        if(!args[0].equals("-scramble") && !args[0].equals("-descramble")) {
            throw new IllegalArgumentException("Invalid option: " + args[0]);
        }

        filename = args[1];
    }

    /**
     *  Works both in scrambling and descrambling. Goes through all
     *  characters read in and swaps them one by one from start to
     *  end.
     *
     * @param iterDist Step distance of swapping
     */
    private void processData(int iterDist) {
        int i = 0, swapDist = 3;
        while(i < docList.size()) {
            if(((i + swapDist) < docList.size())) {
                Collections.swap(docList, i, i + swapDist);
            }
            i += iterDist;
        }
    }

    /**
     * Executes the program for a given file and options.
     *
     * @param args Command line arguments passed to the program
     */
    public void run(String[] args) {
        parseArgs(args);

        try (BufferedReader input = new BufferedReader(new FileReader(filename));
             PrintWriter output = new PrintWriter(System.out) ) {
            int data;
            while((data = input.read()) != -1) {
                docList.add(String.valueOf((char)data));
            }

            for(int i=2; i <= 12; i += 2) {
                processData(i);
            }

            for (int i = 0; i < docList.size(); i++) {
                output.write(docList.get(i));
            }
        } catch (IOException e) {
            System.out.println("Error encountered reading file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        (new MyScramble()).run(args);
    }
}
