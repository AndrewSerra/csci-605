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
 * A description of what the class does.
 *
 * @author      Author name
 * @author      Author name
 */
public class LargestDistance {

    static int MAXIMUM = 50;
    static int MINIMUM = 1;

    /**
     * A description of what the method does
     *
     * @param       name    description
     * @param       name    description
    .
    .
     * @param       name    description
     *
     * @return              description
     *
     * @exception   name    description
     * @exception   name    description
    .
    .
     * @exception   name    description
     */
    private static boolean testProperty(int a, int b, int c )	{
        return true;
    }

    /**
     * A description of what the method does
     *
     * @param       name    description
     * @param       name    description
    .
    .
     * @param       name    description
     *
     * @return              description
     *
     * @exception   name    description
     * @exception   name    description
    .
    .
     * @exception   name    description
     */
    private static void printSolution(int a, int b, int c )	{
        System.out.printf("%d^3 + %d^3 = %d^2\n", a, b, c);
        System.out.printf("\t%d + %d = %d\n", a*a*a, b*b*b, c*c);
        return;
    }

    /**
     * A description of what the method does
     *
     * @param       name    description
     * @param       name    description
    .
    .
     * @param       name    description
     *
     * @return              description
     *
     * @exception   name    description
     * @exception   name    description
    .
    .
     * @exception   name    description
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