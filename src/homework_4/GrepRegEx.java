package homework_4;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a class that checks lines from a file to match patterns stated in
 * this class. All lines separated by a delimiter are tested for each pattern.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class GrepRegEx {

    static String[] lines = new String[10231];
    static int lineCount = 0;
    static String fileName;
    static String delimiter;

    static String[] patternStrs = {
            // Ordered vowels, single consonants between vowels
            "a[a-z&&[^aeiou]]*e[a-z&&[^aeiou]]*i[a-z&&[^aeiou]]*o[a-z" +
                    "&&[^aeiou]]*u",
            // 4 or 5 char palindrome
            "^(.)(.).?\\2\\1$",
            // Date formatting
            "(0[1-9]|1[0-9]|2[0-9]|3[0-1])/(0[1-9]|1[0-9]|2[0-9]|3[0-1])/[0-9]{2}",
            // [n-k]|(nl) pattern
            "^\\[([0-9])-[0-9]\\]\\|\\(\\1\\d\\)"
            };

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
            scanner.useDelimiter(delimiter);

            while(scanner.hasNextLine()) {
                lines[lineCount++] = scanner.nextLine();
            }

        } catch (Exception e) {
            System.out.println("Something went wrong. Error: " + e.getMessage());
        }
    }

    /**
     * Find the delimiter and the file name from the command line arguments.
     * Saves values as a static variable.
     *
     * @param args String array of the command line arguments.
     */
    private static void parseInputArgs(String[] args) {
        int idx = 0;
        try {
            while (idx < args.length-1) {
                String value = args[idx + 1];
                switch(args[idx]) {
                    case "-d":
                        delimiter = value;
                        break;
                    case "-f":
                        fileName = value;
                        break;
                }
                idx += 2;
            }
        } catch (Exception e) {
            System.out.println("Something went wrong. Error: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Iterate over pattern string and create Pattern object array that is
     * compiled.
     *
     * @return An array of Pattern objects ready to test with strings.
     */
    private static Pattern[] createPatternObjects() {
        Pattern[] patterns = new Pattern[patternStrs.length];
        for(int i=0; i < patterns.length; i++) {
            patterns[i] = Pattern.compile(patternStrs[i]);
        }
        return patterns;
    }

    /**
     * Iterate through all the lines and try to find all strings that are
     * matched. Accepted strings are printed.
     *
     * @param patterns Array of pattern objects compiled with pattern strings.
     */
    private static void testPatterns(Pattern[] patterns) {
        for(int i=0; i < lineCount; i++) {
            for(int j=0; j < patterns.length; j++) {
                Matcher m = patterns[j].matcher(lines[i]);
                if(m.matches()) {
                    System.out.printf("%s\n", lines[i]); // Print the matching
                    // line
                }
            }
        }
    }

    public static void main(String[] args) {
        parseInputArgs(args);
        readFile(fileName);
        testPatterns(createPatternObjects());
    }
}
