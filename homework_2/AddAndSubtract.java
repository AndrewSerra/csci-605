
package homework_2;

public class AddAndSubtract {
    static int[] numberToUse = { 2, 3, 4, 5 };
    static int[] numberToCalculate = { 1, 5, 7 };

    public static boolean testSubtractOperation(int a, int b, int expected) {
        return (a - b) == expected;
    }

    public static boolean testSumOperation(int a, int b, int expected) {
        return (a + b) == expected;
    }

    public static void printSolution(int a, int b, String result, String op) {
        System.out.printf(
                "%s can be calculated by the %s of: %d %d", result, op, a, b);
    }

    public static void testProperty(int target) {
        int numberToUseIdx = 0;
        while(numberToUseIdx < numberToUse.length) {

        }
    }

    public static void main( String[] arguments ) {
        for(int index = 0; index < numberToCalculate.length; index++) {
            testProperty(numberToCalculate[index]);
        }
    }
}
