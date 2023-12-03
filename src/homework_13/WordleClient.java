package homework_13;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Wordle {
    static int soManyWordToPLayWith = 0;
    static final String[] theWords = new String[10231];
    static final Scanner readGuess = new Scanner(System.in);

    public static void printArray() {
        int counter = 0;
        while (theWords[counter++] != null)
            System.out.println(theWords[counter - 1]);
    }

    public static void readWordsFromFile(String fileName) {
        try (
                BufferedReader input = new BufferedReader(new FileReader(fileName));
        ) {
            String word;
            int counter = 0;
            while ((theWords[counter++] = input.readLine()) != null)
                soManyWordToPLayWith++;
        } catch (Exception e) {
            System.out.println("ExceptionType occurred: " + e.getMessage());
        }
    }

    public static String readUserInput() {
        String guess = "";
        System.out.print("> ");
        if (readGuess.hasNext()) {
            guess = readGuess.nextLine();
        }
        return guess;
    }

    //	abcdef
    //	acxxxx
    //	*_xxxx
    public static String analyse(String theWordToShow, String guess) {
        String rValue = "";
        for (int index = 0; index < guess.length(); index++) {
            char charAtPositionIndex = guess.charAt(index);
            if (charAtPositionIndex == theWordToShow.charAt(index))
                rValue += "*";
            else if (theWordToShow.indexOf(charAtPositionIndex) > 0)
                rValue += "_";
            else
                rValue += "x";

        }
        return rValue;

    }

    public static boolean isItSolved(String progressSoFar) {
        int counter = 0;
        for (int index = 0; index < progressSoFar.length(); index++) {
            if (progressSoFar.charAt(index) == '*')
                counter++;
        }
        return progressSoFar.length() == counter;
    }

    public static String getWord() {
        return theWords[new Random().nextInt(soManyWordToPLayWith)];
    }

    public static void playWordle() {
        System.out.println("_ indicates the letter is in the word but in the wrong spot.");
        System.out.println("* indicates the letter is in the word and correct spot.");
        System.out.println("x indicates the letter is not in the word.");
        System.out.println("Try to guess the word in 5 tries.");
        int counter = 0;
        String theWordToGuess = getWord();
        String theWordToShow = theWordToGuess.replaceAll(".", ".");
        // System.out.println(theWordToGuess);
        do {
            counter++;
            System.out.print(counter);
            String theGuess;
            do {
                theGuess = readUserInput();
            } while (theGuess.length() != 5);
            theWordToShow = analyse(theWordToGuess, theGuess);
            System.out.println(theGuess);
            System.out.println(theWordToShow);
        } while (!isItSolved(theWordToShow) && (counter < 6));

        if (isItSolved(theWordToShow))
            System.out.println("well done.");
        else
            System.out.println("!well done.");
    }

    public static void main(String args[]) {
        readWordsFromFile("5_char_word.txt");
        // printArray();
        playWordle();
    }
}
