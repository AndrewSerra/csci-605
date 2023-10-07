package homework_4;

import java.io.File;
import java.util.Scanner;

/**
 * This is a class that reads in a file with lines containing strings to test
 * against a set of patterns. Each line in the file is tested with all the
 * patterns saved.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class Grep {

    static String[] words = new String[10231];
    static int wordCount = 0;
    final static int FAIL_STATE = -1;

    /**
     * Reads in a given file and populates the words array, increments the
     * word count.
     *
     * @param fileName The file along with the path to read in strings for
     *                 pattern matching.
     */
    private static void readFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));

            while(scanner.hasNextLine()) {
                words[wordCount++] = scanner.nextLine();
            }

        } catch (Exception e) {
            System.out.println("Something went wrong. Error: " + e.getMessage());
        }
    }

    /**
     * Runs the first pattern state machine for a given input string.
     *
     * @param input The string to test for the pattern acceptance.
     * @return True if the input string is accepted, false if rejected.
     */
    private static boolean run_p1(String input) {
        int state = 0, prevState = 0;
        int idx = 0;
        int repeatCount = 0;

        while(idx < input.length()) {
            if(state != prevState) {
                repeatCount = 0;
            }
            switch (state) {
                case 0:
                    state = (input.charAt(idx) == 'a') ? 1 : FAIL_STATE;
                    break;
                case 1:
                    state = (input.charAt(idx) == 'b') ? 2 : FAIL_STATE;
                    break;
                case 2:
                    if(repeatCount >= 1) {
                        state = FAIL_STATE;
                    }
                    break;
            }
            repeatCount++;
            idx++;
        }
        return (state == 2);
    }

    /**
     * Runs the second pattern state machine for a given input string.
     *
     * @param input The string to test for the pattern acceptance.
     * @return True if the input string is accepted, false if rejected.
     */
    private static boolean run_p2(String input) {
        int state = 0, prevState = 0;
        int idx = 0;
        int repeatCount = 0;

        while(idx < input.length()) {
            if(state != prevState) {
                repeatCount = 0;
            }
            switch (state) {
                case 0:
                    state = 1;  // Accept any incoming character
                    break;
                case 1:
                    state = (input.charAt(idx) == 'a') ? 2 : FAIL_STATE;
                    break;
                case 2:
                    if(input.charAt(idx) == 'b') {
                        state = 3;
                    } else if(input.charAt(idx) != 'a') {
                        state = FAIL_STATE;
                    }
                    break;
                case 3:
                    state = 4;
                    break;
                case 4:
                    if(repeatCount >= 1) {
                        state = FAIL_STATE;
                    }
                    break;
            }
            repeatCount++;
            idx++;
        }
        return (state == 4);
    }

    /**
     * Runs the third pattern state machine for a given input string.
     *
     * @param input The string to test for the pattern acceptance.
     * @return True if the input string is accepted, false if rejected.
     */
    private static boolean run_p3(String input) {
        int state = 0, prevState = 0;
        int idx = 0;
        int repeatCount = 0;

        while(idx < input.length()) {
            if(state != prevState) {
                repeatCount = 0;
            }
            switch (state) {
                case 0:
                    state = 1;  // Accept any incoming character
                    break;
                case 1:
                    state = (input.charAt(idx) == 'a') ? 2 : FAIL_STATE;
                    break;
                case 2:
                    state = (input.charAt(idx) == 'b') ? 3 : FAIL_STATE;
                    break;
                case 3:
                    if(repeatCount > 0) {
                        state = FAIL_STATE;
                    }
                    break;
            }
            repeatCount++;
            idx++;
        }
        return (state == 3);
    }

    /**
     * Runs the fourth pattern state machine for a given input string.
     *
     * @param input The string to test for the pattern acceptance.
     * @return True if the input string is accepted, false if rejected.
     */
    private static boolean run_p4(String input) {
        int state = 0, prevState = 0;
        int idx = 0;
        int repeatCount = 0;

        while(idx < input.length()) {
            char c = input.charAt(idx);
            if(state != prevState) {
                repeatCount = 0;
            }
            switch (state) {
                case 0:
                    state = (c == 'a' || c == 'b') ? 1 : FAIL_STATE;
                    break;
                case 1:
                    state = (input.charAt(idx) == 'c') ? 2 : FAIL_STATE;
                    break;
                case 2:
                    if(repeatCount >= 1) {
                        state = FAIL_STATE;
                    }
                    break;
            }
            repeatCount++;
            idx++;
        }
        return (state == 2);
    }

    /**
     * Runs the fifth pattern state machine for a given input string.
     *
     * @param input The string to test for the pattern acceptance.
     * @return True if the input string is accepted, false if rejected.
     */
    private static boolean run_p5(String input) {
        int state = 0, prevState = 0;
        int idx = 0;
        int repeatCount = 0;

        while(idx < input.length()) {
            char c = input.charAt(idx);
            if(state != prevState) {
                repeatCount = 0;
            }
            switch (state) {
                case 0:
                    if(c == 'a' || c == 'b') {
                        state = 1;
                    } else if(c == 'c') {
                        state = 2;
                    } else {
                        state = FAIL_STATE;
                    }
                    break;
                case 1:
                    state = (c == 'c') ? 2 : FAIL_STATE;
                    break;
                case 2:
                    if(repeatCount >= 1) {
                        state = FAIL_STATE;
                    }
                    break;
            }
            repeatCount++;
            idx++;
        }
        return (state == 2);
    }

    /**
     * Runs the sixth pattern state machine for a given input string.
     *
     * @param input The string to test for the pattern acceptance.
     * @return True if the input string is accepted, false if rejected.
     */
    private static boolean run_p6(String input) {
        int state = 0, prevState = 0;
        int idx = 0;
        int repeatCount = 0;

        // Both strings are optional, if length is zero, accept result
        if(input.isEmpty()) {
            return true;
        }

        while(idx < input.length()) {
            char c = input.charAt(idx);
            if(state != prevState) {
                repeatCount = 0;
            }
            switch (state) {
                case 0:
                    if(c == 'a' || c == 'b') {
                        state = 1;
                    } else if(c == 'c') {
                        state = 2;
                    } else {
                        state = FAIL_STATE;
                    }
                    break;
                case 1:
                case 2:
                    if(repeatCount >= 1) {
                        state = FAIL_STATE;
                    }
                    break;
            }
            repeatCount++;
            idx++;
        }
        return (state == 1 || state == 2);
    }

    /**
     * Runs the seventh pattern state machine for a given input string.
     *
     * @param input The string to test for the pattern acceptance.
     * @return True if the input string is accepted, false if rejected.
     */
    private static boolean run_p7(String input) {
        int state = 0;
        int idx = 0;
        char capture1 = ' ';
        char capture2 = ' ';
        int repeatCount = 0;

        while(idx < input.length()) {
            char c = input.charAt(idx);

            switch(state) {
                case 0:
                    capture1 = c;
                    state = 1;
                    break;
                case 1:
                    capture2 = c;
                    state = 2;
                    break;
                case 2:
                    state = (c == capture2) ? 3 : FAIL_STATE;
                    break;
                case 3:
                    state = (c == capture1) ? 4 : FAIL_STATE;
                    break;
                case 4:
                    if(repeatCount >= 1) {
                        state = FAIL_STATE;
                    }
                    break;
            }
            repeatCount++;
            idx++;
        }
        return state == 4;
    }

    /**
     * Iterates through all the lines saved and patterns to test all
     * combinations. If accepted, the pattern and string is printed.
     *
     * @param patterns The array of patterns that are being executed as
     *                 finite state machines.
     */
    private static void testPatterns(String[] patterns) {
        for(int i=0; i < wordCount; i++) {
            for(int j=0; j < patterns.length; j++) {
                boolean testResult = false;
                switch(j) {
                    case 0: testResult = run_p1(words[i]); break;
                    case 1: testResult = run_p2(words[i]); break;
                    case 2: testResult = run_p3(words[i]); break;
                    case 3: testResult = run_p4(words[i]); break;
                    case 4: testResult = run_p5(words[i]); break;
                    case 5: testResult = run_p6(words[i]); break;
                    case 6: testResult = run_p7(words[i]); break;
                }
                if (testResult) {
                    System.out.printf("Line: %s %s\n", patterns[j], words[i]);
                }
            }
        }
    }

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("File not provided. Exiting...");
            System.exit(1);
        }

        readFile(args[0]);

        String[] patterns = {
                "^ab$", ".a+b.", ".ab.", "^[ab]c$",
                "^[ab]?c$", "^[ab]?|c?$", "^..\\2\\1$" };

        testPatterns(patterns);
    }
}
