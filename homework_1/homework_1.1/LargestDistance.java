/*
 * LargestDistance.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */

import java.io.*;

/**
 * This is a class that finds integer solutions to the
 * equation a^3 + b^3 = z^2. All the results are printed
 * as they are found.
 *
 * @author      Andrew Serra
 */
public class LargestDistance {

    static int MAXIMUM = 50;
    static int MINIMUM = 1;

    /**
     * A test function to test integer solutions found by the findAllNumbers
     * function.
     *
     * @param       a    First parameter to be cubed
     * @param       b    Second parameter to be cubed
     * @param       c    The integer that is expected to be squared and equal
     *                   to the result
     *
     * @return           Result of the equation with parameters entered
     */
    private static boolean testProperty(int a, int b, int c )	{
        return (a*a*a) + (b*b*b) == (c*c);
    }

    /**
     * Prints the integer solution in a formatted string.
     *
     * @param       a    First parameter to be cubed
     * @param       b    Second parameter to be cubed
     * @param       c    The integer that is expected to be squared and equal
     *                   to the result
     */
    private static void printSolution(int a, int b, int c )	{
        System.out.printf("%d^3 + %d^3 = %d^2\n", a, b, c);
        System.out.printf("\t%d + %d = %d\n", a*a*a, b*b*b, c*c);
        return;
    }

    /**
     * Iteratively tests all the available numbers in the range between
     * minimum and maximum numbers.
     *
     */
    private static void findAllNumbers() {
        int maxSquared = MAXIMUM * MAXIMUM;

        for(int i = MINIMUM; i <= MAXIMUM; i++) {
            int iCube = i * i * i;

            if(iCube > maxSquared) {
                continue;
            }

            for(int j = MINIMUM; j <= MAXIMUM; j++) {
                int jCube = j * j * j;

                if(jCube > maxSquared) {
                    continue;
                }

                for(int k = MINIMUM; k < MAXIMUM; k++) {
                    if(iCube + jCube == k*k) {
                        printSolution(i, j, k);
                    }
                }
            }
        }
        return;
    }
    public static void main( String[] args ) {
        findAllNumbers();
    }
}