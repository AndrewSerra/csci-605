package homework_6;

import java.util.Arrays;

public class TestArrayOperator {

    public static void testExpandOperation() {

        String[][] expandTestCases = {
                { "hey", "ho", null, "welcome", null, "yoyo", "tatatata", null },
                { "hey", "ho", null, "tatatata" },
        };

        String[][] expandTestExpectedResults = {
                { "hey", "ho", null, "welcome", null, "yoyo", "tatatata",
                        null, null, null, null, null, null, null, null, null },
                { "hey", "ho", null, "tatatata", null, null, null, null },
        };

        System.out.println("\n--- Expand test cases: \n");
        for(int i=0; i < expandTestCases.length; i++) {
            String[] result =
                    ArrayOperator.expand(expandTestCases[i]);
            System.out.printf("Test case %d: %b\n\n", (i+1),
                    (Arrays.equals(result, expandTestExpectedResults[i])));
        }
    }

    public static void testInsertOperation() {

        String[] storage = {
                "hey", "ho", "welcome", "yoyo", "tatatata", null, null, null
        };
        Integer[] insertIndexTests = { 0, 2, 5, 7 };
        String[] stringsToInsert = {
                "here", "car", "dart", "end"
        };
        String[][] insertTestExpectedResults = {
                { "here", "hey", "ho", "welcome", "yoyo", "tatatata", null,
                        null },
                { "here", "hey", "car", "ho", "welcome", "yoyo", "tatatata",
                        null },
                { "here", "hey", "car", "ho", "welcome", "dart", "yoyo",
                        "tatatata" },
                { "here", "hey", "car", "ho", "welcome", "dart", "yoyo", "end",
                        "tatatata", null, null, null, null, null, null, null },
        };

        System.out.println("\n--- Insert test cases: \n");
        for(int i=0; i < stringsToInsert.length; i++) {
            System.out.printf("Test case %d\nStorage: %s\n", (i+1), Arrays.toString(storage));
            storage = ArrayOperator.insertItemAtIdx(
                    storage, stringsToInsert[i], insertIndexTests[i]);
            System.out.printf("Result: %b\n",
                    (Arrays.equals(storage, insertTestExpectedResults[i])));
            System.out.printf("Word: %s at index: %d\nExpected: %s\n\n",
                    stringsToInsert[i],
                    insertIndexTests[i],
                    Arrays.toString(insertTestExpectedResults[i]));
        }
    }

    public static void testDeleteOperation() {

        String[] storage = {
                "here", "hey", "car", "ho", "welcome", "dart", "yoyo", "end",
                "tatatata", null, null, null, null, null, null, null,
        };
        Integer[] deleteIndexTests = { 0, 2, 5, 7 };
        String[][] deleteTestExpectedResults = {
                { "hey", "car", "ho", "welcome", "dart", "yoyo", "end",
                        "tatatata", null, null, null, null, null, null, null, null },
                { "hey", "car", "welcome", "dart", "yoyo", "end", "tatatata",
                        null, null, null, null, null, null, null, null, null },
                { "hey", "car", "welcome", "dart", "yoyo", "tatatata", null,
                        null, null, null, null, null, null, null, null, null },
                { "hey", "car", "welcome", "dart", "yoyo", "tatatata", null,
                        null, null, null, null, null, null, null, null, null },
        };

        System.out.println("\n--- Delete test cases: \n");
        for(int i=0; i < deleteIndexTests.length; i++) {
            System.out.printf("Test case %d\nStorage: %s\n", (i+1), Arrays.toString(storage));
            storage = ArrayOperator.deleteItemAtIdx(storage, deleteIndexTests[i]);
            System.out.printf("Result: %b\n",
                    (Arrays.equals(storage, deleteTestExpectedResults[i])));
            System.out.printf("Index: %d\nExpected: %s\n\n",
                    deleteIndexTests[i], Arrays.toString(deleteTestExpectedResults[i]));
        }
    }

    public static void main(String[] args) {
        // Test Expand
        testExpandOperation();
        // Test Insert
        testInsertOperation();
        // Test Delete
        testDeleteOperation();
    }
}
