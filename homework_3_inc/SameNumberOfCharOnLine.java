package homework_3_inc;

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

public class SameNumberOfCharOnLine {

    static String[] lines = new String[10231];
    static int lineCount = 0;

    public static void readFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));

            while(scanner.hasNextLine()) {
                lines[lineCount++] = formatLineToLowerCase(scanner.nextLine());
            }

        } catch (Exception e) {
            System.out.println("Something went wrong. Error: " + e.getMessage());
        }
    }

    public static HashMap<Character, Integer> getCharFreq(String s) {
        HashMap<Character, Integer> freq = new HashMap<Character, Integer>();

        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (!freq.containsKey(c)) {
                freq.put(c, 1);
            } else {
                freq.put(c, freq.get(c) + 1);
            }
        }

        return freq;
    }

    public static String formatLineToLowerCase(String line) {
        String newLine = "";
        for(int i = 0; i < line.length(); i++) {
            char currChar = line.charAt(i);
            if(Character.isUpperCase(currChar)) {
                char lowerChar = Character.toLowerCase(currChar);
                newLine = newLine + lowerChar;
            } else if (line.charAt(i) != ' ') {
                newLine = newLine + currChar;
            }
        }
        return newLine;
    }

    public static String getSortedString(String line) {
        char[] sCharArr = line.toCharArray();

        for(int i = 1; i < sCharArr.length; i++) {
            int tmp = (int)sCharArr[i];
            int j = i - 1;

            while(j >= 0 && tmp < (int)sCharArr[j]) {
                sCharArr[j + 1] = sCharArr[j];
                j = j - 1;
            }

            sCharArr[j + 1] = (char)tmp;
        }

        String sortedStr = "";

        for(int i = 0; i < sCharArr.length; i++) {
            sortedStr = sortedStr + sCharArr[i];
        }

        return sortedStr;
    }

    public static void main(String[] args) {
        readFile("src/homework_3/words.txt");

        // Iterate through all lines saved
        for(int i = 0; i < lineCount; i++) {
            System.out.println(getSortedString(lines[i]));
        }
    }
}
