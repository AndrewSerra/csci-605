package homework_7;

import java.util.Arrays;

/**
 * This is a test file to test String, Integer, MusicStorageOfThePast, and
 * OldFashionedEmailAddress classes being used with SortedStorage.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class TestSortedStorage {

    private static class Test<T extends Comparable<T>> implements ITestCase {
        private boolean isTestExecuted = false;
        private boolean isAccepted = false;
        private final SortedStorage<T> s;
        private final StorageOperations operation;
        private final T[] testCases;
        private final T[][] storageExpected;
        private final Boolean[] operationResultsExpected;

        public Test(
                SortedStorage<T> storage,
                StorageOperations op,
                T[] cases,
                T[][] endStorageStates,
                Boolean[] results) {
            s = storage;
            operation = op;
            testCases = cases;
            storageExpected = endStorageStates;
            operationResultsExpected = results;
        }

        private boolean checkArrayLengths(Object[] ...arr) {
            boolean isEqual = true;
            int targetLen = arr[0].length;
            for(Object[] o : arr) {
                if(o.length != targetLen) {
                    isEqual = false;
                    break;
                }
            }
            return isEqual;
        }

        private boolean executeOperation(T testCase) {
            boolean opResult = false;
            switch(operation) {
                case ADD: { opResult = s.add(testCase); break; }
                case FIND: { opResult = s.find(testCase); break; }
                case DELETE: { opResult = s.delete(testCase); break; }
                case INCLUDES_NULL: { opResult = s.includesNull(); break; }
            }
            return opResult;
        }

        @Override
        public boolean isSuccessful() throws Exception {
            if(!isTestExecuted) {
                throw new Exception(
                        String.format(
                                "Test result requires test to be executed in " +
                                        "class %s with type %s.",
                                getClass().getName(),
                                testCases[0].getClass().getSimpleName()
                        )
                );
            }
            return isAccepted;
        }

        @Override
        public void run() throws Exception {
            if(!checkArrayLengths(
                    (Object[]) testCases,
                    (Object[]) storageExpected,
                    (Object[]) operationResultsExpected)) {
                throw new Exception(
                        String.format(
                                "Test case length %d, storage result length " +
                                        "%d, and operation result length %d " +
                                        "do not match.",
                                testCases.length,
                                storageExpected.length,
                                operationResultsExpected.length
                        )
                );
            }

            isAccepted = true; // Accept by default until failure

            for(int i=0; i < testCases.length; i++) {
                boolean result = executeOperation(testCases[i]);
                boolean sMatches = Arrays.equals(storageExpected[i], s.storage);

                System.out.printf("Test Case: %d\n", i);
                if(!sMatches || (result != operationResultsExpected[i])) {
                    isAccepted = false;
                    System.out.print("\tResult: FAILED\n");
                    System.out.printf(
                            "\tCase: %s\n",
                            testCases[i] == null ? "null" : testCases[i].toString());
                    System.out.printf(
                            "\tStorage Expected: %s\n",
                            Arrays.toString(storageExpected[i]));
                    System.out.printf(
                            "\tStorage: %s\n",
                            Arrays.toString(s.storage));
                    System.out.printf(
                            "\tOperation Result: %b, Expected: %b\n",
                            result,
                            operationResultsExpected[i]);
                } else {
                    System.out.print("\tResult: SUCCESS\n");
                }
            }

            isTestExecuted = true;
        }
    }

    private static void printLineSeparator() {
        System.out.print("\n---------------------------\n\n");
    }

    private static void runStrInsertUniqueTest(SortedStorage<String> storage) {

        String[] stringInsertTestCases = {
                "hello", "welcome", null, "hey", "computer", "car", "car"
        };
        String[][] storageExpected = {
                { "hello", null },
                { "hello", "welcome" },
                { "hello", "welcome" },
                { "hello", "hey", "welcome", null },
                { "computer", "hello", "hey", "welcome" },
                { "car", "computer", "hello", "hey", "welcome", null, null, null },
                { "car", "computer", "hello", "hey", "welcome", null, null, null }
        };
        Boolean[] opResultsExpected = {
                true, true, true, true, true, true, false
        };

        Test<String> insertUniqueTest = new Test<>(
                storage,
                StorageOperations.ADD,
                stringInsertTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            insertUniqueTest.run();
            System.out.printf(
                    "Overall string insert test result %b.\n",
                    insertUniqueTest.isSuccessful());
        } catch (Exception e) {
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }

    private static void runStrInsertNonUniqueTest(SortedStorage<String> storage) {

        String[] stringInsertTestCases = {
                "hello", "welcome", null, "hey", "computer", "car", "car"
        };
        String[][] storageExpected = {
                { "hello", null },
                { "hello", "welcome" },
                { "hello", "welcome" },
                { "hello", "hey", "welcome", null },
                { "computer", "hello", "hey", "welcome" },
                { "car", "computer", "hello", "hey", "welcome", null, null, null },
                { "car", "car", "computer", "hello", "hey", "welcome", null, null }
        };
        Boolean[] opResultsExpected = {
                true, true, true, true, true, true, true
        };

        Test<String> insertUniqueTest = new Test<>(
                storage,
                StorageOperations.ADD,
                stringInsertTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            insertUniqueTest.run();
            System.out.printf(
                    "Overall string non-unique insert test result %b.\n",
                    insertUniqueTest.isSuccessful());
        } catch (Exception e) {
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }

    private static void runStrFindTest(SortedStorage<String> storage) {

        String[] findTestCases = { "hello", "welcome", null, "truck" };
        String[][] storageExpected = {
                { "car", "computer", "hello", "hey", "welcome", null, null, null },
                { "car", "computer", "hello", "hey", "welcome", null, null, null },
                { "car", "computer", "hello", "hey", "welcome", null, null, null },
                { "car", "computer", "hello", "hey", "welcome", null, null, null },
        };
        Boolean[] opResultsExpected = {
                true, true, true, false
        };

        Test<String> findTest = new Test<>(
                storage,
                StorageOperations.FIND,
                findTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            findTest.run();
            System.out.printf(
                    "Overall string find test result %b.\n", findTest.isSuccessful());
        } catch (Exception e) {
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }

    private static void runStrDeleteTest(SortedStorage<String> storage) {

        String[] deleteTestCases = { "hello", null, "truck" };
        String[][] storageExpected = {
                { "car", "computer", "hey", "welcome", null, null, null, null },
                { "car", "computer", "hey", "welcome", null, null, null, null },
                { "car", "computer", "hey", "welcome", null, null, null, null },
        };
        Boolean[] opResultsExpected = {
                true, true, false
        };

        Test<String> deleteTest = new Test<>(
                storage,
                StorageOperations.DELETE,
                deleteTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            deleteTest.run();
            System.out.printf(
                    "Overall string delete test result %b.\n",
                    deleteTest.isSuccessful());
        } catch (Exception e) {
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }



    private static void runIntInsertUniqueTest(SortedStorage<Integer> storage) {

        Integer[] stringInsertTestCases = { 1, 5, null, 2, 8, 1, 4 };
        Integer[][] storageExpected = {
                { 1, null },
                { 1, 5 },
                { 1, 5 },
                { 1, 2, 5, null },
                { 1, 2, 5, 8 },
                { 1, 2, 5, 8 },
                { 1, 2, 4, 5, 8, null, null, null }
        };
        Boolean[] opResultsExpected = {
                true, true, true, true, true, false, true
        };

        Test<Integer> insertUniqueTest = new Test<>(
                storage,
                StorageOperations.ADD,
                stringInsertTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            insertUniqueTest.run();
            System.out.printf(
                    "Overall integer insert test result %b.\n",
                    insertUniqueTest.isSuccessful());
        } catch (Exception e) {
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }

    private static void runIntInsertNonUniqueTest(SortedStorage<Integer> storage) {

        Integer[] stringInsertTestCases = { 1, 5, null, 2, 8, 1, 4 };
        Integer[][] storageExpected = {
                { 1, null },
                { 1, 5 },
                { 1, 5 },
                { 1, 2, 5, null },
                { 1, 2, 5, 8 },
                { 1, 1, 2, 5, 8, null, null, null },
                { 1, 1, 2, 4, 5, 8, null, null }
        };
        Boolean[] opResultsExpected = {
                true, true, true, true, true, true, true
        };

        Test<Integer> insertUniqueTest = new Test<>(
                storage,
                StorageOperations.ADD,
                stringInsertTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            insertUniqueTest.run();
            System.out.printf(
                    "Overall integer non-unique insert test result %b.\n",
                    insertUniqueTest.isSuccessful());
        } catch (Exception e) {
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }

    private static void runIntFindTest(SortedStorage<Integer> storage) {

        Integer[] findTestCases = { 1, 9, null, 2 };
        Integer[][] storageExpected = {
                { 1, 2, 4, 5, 8, null, null, null },
                { 1, 2, 4, 5, 8, null, null, null },
                { 1, 2, 4, 5, 8, null, null, null },
                { 1, 2, 4, 5, 8, null, null, null }
        };
        Boolean[] opResultsExpected = { true, false, true, true };

        Test<Integer> findTest = new Test<>(
                storage,
                StorageOperations.FIND,
                findTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            findTest.run();
            System.out.printf(
                    "Overall integer find test result %b.\n",
                    findTest.isSuccessful());
        } catch (Exception e) {
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }

    private static void runIntDeleteTest(SortedStorage<Integer> storage) {

        Integer[] deleteTestCases = { 1, 9, null, 2 };
        Integer[][] storageExpected = {
                { 2, 4, 5, 8, null, null, null, null },
                { 2, 4, 5, 8, null, null, null, null },
                { 2, 4, 5, 8, null, null, null, null },
                { 4, 5, 8, null, null, null, null, null }
        };
        Boolean[] opResultsExpected = { true, false, true, true };

        Test<Integer> deleteTest = new Test<>(
                storage,
                StorageOperations.DELETE,
                deleteTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            deleteTest.run();
            System.out.printf(
                    "Overall integer delete test result %b.\n",
                    deleteTest.isSuccessful());
        } catch (Exception e) {
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }



    private static void runMusicInsertUniqueTest(
            SortedStorage<MusicStorageOfThePast> storage) {
        MusicStorageOfThePast wHouston = new MusicStorageOfThePast(
                "Whitney Houston", "I will Always Love You",
                1992, 4.31F, 1);
        MusicStorageOfThePast ePresley = new MusicStorageOfThePast(
                "Elvis Presley", "Love Me Tender",
                1956, 2.46F, 1);
        MusicStorageOfThePast cDion = new MusicStorageOfThePast(
                "Celine Dion", "My Heart Will Go On",
                1997, 4.36F, 5);
        MusicStorageOfThePast journey = new MusicStorageOfThePast(
                "Journey", "Open Arms",
                1982, 3.19F, 3);
        MusicStorageOfThePast[] musicInsertTestCases = {
                wHouston, ePresley, cDion, journey, null, cDion };
        MusicStorageOfThePast[][] storageExpected = {
                { wHouston, null },
                { ePresley, wHouston },
                { ePresley, wHouston, cDion, null },
                { ePresley, journey, wHouston, cDion },
                { ePresley, journey, wHouston, cDion },
                { ePresley, journey, wHouston, cDion }
        };
        Boolean[] opResultsExpected = { true, true, true, true, true, false };

        Test<MusicStorageOfThePast> insertUniqueTest = new Test<>(
                storage,
                StorageOperations.ADD,
                musicInsertTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            insertUniqueTest.run();
            System.out.printf(
                    "Overall music insert test result %b.\n",
                    insertUniqueTest.isSuccessful());
        } catch (Exception e) {
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }

    private static void runMusicInsertNonUniqueTest(
            SortedStorage<MusicStorageOfThePast> storage) {
        MusicStorageOfThePast wHouston = new MusicStorageOfThePast(
                "Whitney Houston", "I will Always Love You",
                1992, 4.31F, 1);
        MusicStorageOfThePast ePresley = new MusicStorageOfThePast(
                "Elvis Presley", "Love Me Tender",
                1956, 2.46F, 1);
        MusicStorageOfThePast cDion = new MusicStorageOfThePast(
                "Celine Dion", "My Heart Will Go On",
                1997, 4.36F, 5);
        MusicStorageOfThePast journey = new MusicStorageOfThePast(
                "Journey", "Open Arms",
                1982, 3.19F, 3);
        MusicStorageOfThePast[] musicInsertTestCases = {
                wHouston, ePresley, cDion, journey, null, cDion };
        MusicStorageOfThePast[][] storageExpected = {
                { wHouston, null },
                { ePresley, wHouston },
                { ePresley, wHouston, cDion, null },
                { ePresley, journey, wHouston, cDion },
                { ePresley, journey, wHouston, cDion },
                { ePresley, journey, wHouston, cDion, cDion, null, null, null }
        };
        Boolean[] opResultsExpected = { true, true, true, true, true, true };

        Test<MusicStorageOfThePast> insertUniqueTest = new Test<>(
                storage,
                StorageOperations.ADD,
                musicInsertTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            insertUniqueTest.run();
            System.out.printf(
                    "Overall music non-unique insert test result %b.\n",
                    insertUniqueTest.isSuccessful());
        } catch (Exception e) {
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }

    private static void runMusicFindTest(
            SortedStorage<MusicStorageOfThePast> storage) {
        MusicStorageOfThePast wHouston = new MusicStorageOfThePast(
                "Whitney Houston", "I will Always Love You",
                1992, 4.31F, 1);
        MusicStorageOfThePast ePresley = new MusicStorageOfThePast(
                "Elvis Presley", "Love Me Tender",
                1956, 2.46F, 1);
        MusicStorageOfThePast cDion = new MusicStorageOfThePast(
                "Celine Dion", "My Heart Will Go On",
                1997, 4.36F, 5);
        MusicStorageOfThePast journey = new MusicStorageOfThePast(
                "Journey", "Open Arms",
                1982, 3.19F, 3);
        MusicStorageOfThePast wings = new MusicStorageOfThePast(
                "Wings", "Maybe I'm Amazed",
                2001, 3.51F, 3);

        MusicStorageOfThePast[] findTestCases = {
                wHouston, null, ePresley, wings };
        MusicStorageOfThePast[][] storageExpected = {
                { ePresley, journey, wHouston, cDion },
                { ePresley, journey, wHouston, cDion },
                { ePresley, journey, wHouston, cDion },
                { ePresley, journey, wHouston, cDion },
        };
        Boolean[] opResultsExpected = { true, true, true, false };

        Test<MusicStorageOfThePast> findTest = new Test<>(
                storage,
                StorageOperations.FIND,
                findTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            findTest.run();
            System.out.printf(
                    "Overall music find test result %b.\n",
                    findTest.isSuccessful());
        } catch (Exception e) {
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }

    private static void runMusicDeleteTest(
            SortedStorage<MusicStorageOfThePast> storage) {
        MusicStorageOfThePast wHouston = new MusicStorageOfThePast(
                "Whitney Houston", "I will Always Love You",
                1992, 4.31F, 1);
        MusicStorageOfThePast ePresley = new MusicStorageOfThePast(
                "Elvis Presley", "Love Me Tender",
                1956, 2.46F, 1);
        MusicStorageOfThePast cDion = new MusicStorageOfThePast(
                "Celine Dion", "My Heart Will Go On",
                1997, 4.36F, 5);
        MusicStorageOfThePast journey = new MusicStorageOfThePast(
                "Journey", "Open Arms",
                1982, 3.19F, 3);
        MusicStorageOfThePast wings = new MusicStorageOfThePast(
                "Wings", "Maybe I'm Amazed",
                2001, 3.51F, 3);

        MusicStorageOfThePast[] deleteTestCases = {
                wHouston, null, ePresley, wings };
        MusicStorageOfThePast[][] storageExpected = {
                { ePresley, journey, cDion, null },
                { ePresley, journey, cDion, null },
                { journey, cDion, null, null },
                { journey, cDion, null, null },
        };
        Boolean[] opResultsExpected = { true, true, true, false };

        Test<MusicStorageOfThePast> deleteTest = new Test<>(
                storage,
                StorageOperations.DELETE,
                deleteTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            deleteTest.run();
            System.out.printf(
                    "Overall music delete test result %b.\n",
                    deleteTest.isSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }



    private static void runAddressInsertUniqueTest(
            SortedStorage<OldFashionedEmailAddress> storage) {
        OldFashionedEmailAddress addr1 = new OldFashionedEmailAddress(
                558,
                "7343 Kuphal Ford",
                "Yukberg",
                "DE",
                62681
        );
        OldFashionedEmailAddress addr2 = new OldFashionedEmailAddress(
                851,
                "397 Analisa Burg",
                "Colefurt",
                "MO",
                58841
        );
        OldFashionedEmailAddress addr3 = new OldFashionedEmailAddress(
                784,
                "739 Houston Branch",
                "Mannport",
                "KS",
                88168
        );
        OldFashionedEmailAddress addr4 = new OldFashionedEmailAddress(
                984,
                "60232 Katharina Summit",
                "East Rodney",
                "NH",
                11376
        );
        OldFashionedEmailAddress[] addressInsertTestCases = {
                addr1, addr2, addr3, addr4, addr1 };
        OldFashionedEmailAddress[][] storageExpected = {
                { addr1, null },
                { addr1, addr2 },
                { addr1, addr3, addr2, null },
                { addr1, addr3, addr2, addr4 },
                { addr1, addr3, addr2, addr4 }
        };
        Boolean[] opResultsExpected = { true, true, true, true, false };

        Test<OldFashionedEmailAddress> insertUniqueTest = new Test<>(
                storage,
                StorageOperations.ADD,
                addressInsertTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            insertUniqueTest.run();
            System.out.printf(
                    "Overall address insert test result %b.\n",
                    insertUniqueTest.isSuccessful());
        } catch (Exception e) {
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }

    private static void runAddressInsertNonUniqueTest(
            SortedStorage<OldFashionedEmailAddress> storage) {
        OldFashionedEmailAddress addr1 = new OldFashionedEmailAddress(
                558,
                "7343 Kuphal Ford",
                "Yukberg",
                "DE",
                62681
        );
        OldFashionedEmailAddress addr2 = new OldFashionedEmailAddress(
                851,
                "397 Analisa Burg",
                "Colefurt",
                "MO",
                58841
        );
        OldFashionedEmailAddress addr3 = new OldFashionedEmailAddress(
                784,
                "739 Houston Branch",
                "Mannport",
                "KS",
                88168
        );
        OldFashionedEmailAddress addr4 = new OldFashionedEmailAddress(
                984,
                "60232 Katharina Summit",
                "East Rodney",
                "NH",
                11376
        );
        OldFashionedEmailAddress[] addressInsertTestCases = {
                addr1, addr2, addr3, addr4, addr1 };
        OldFashionedEmailAddress[][] storageExpected = {
                { addr1, null },
                { addr1, addr2 },
                { addr1, addr3, addr2, null },
                { addr1, addr3, addr2, addr4 },
                { addr1, addr1, addr3, addr2, addr4, null, null, null }
        };
        Boolean[] opResultsExpected = { true, true, true, true, true };

        Test<OldFashionedEmailAddress> insertUniqueTest = new Test<>(
                storage,
                StorageOperations.ADD,
                addressInsertTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            insertUniqueTest.run();
            System.out.printf(
                    "Overall address non-unique insert test result %b.\n",
                    insertUniqueTest.isSuccessful());
        } catch (Exception e) {
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }

    private static void runAddressFindTest(
            SortedStorage<OldFashionedEmailAddress> storage) {
        OldFashionedEmailAddress addr1 = new OldFashionedEmailAddress(
                558,
                "7343 Kuphal Ford",
                "Yukberg",
                "DE",
                62681
        );
        OldFashionedEmailAddress addr2 = new OldFashionedEmailAddress(
                851,
                "397 Analisa Burg",
                "Colefurt",
                "MO",
                58841
        );
        OldFashionedEmailAddress addr3 = new OldFashionedEmailAddress(
                784,
                "739 Houston Branch",
                "Mannport",
                "KS",
                88168
        );
        OldFashionedEmailAddress addr4 = new OldFashionedEmailAddress(
                984,
                "60232 Katharina Summit",
                "East Rodney",
                "NH",
                11376
        );
        OldFashionedEmailAddress addr5 = new OldFashionedEmailAddress(
                435,
                "1744 Altenwerth Freeway",
                "Leannfurt",
                "ID",
                37312
        );

        OldFashionedEmailAddress[] findTestCases = {
                addr1, addr2, null, addr5 };
        OldFashionedEmailAddress[][] storageExpected = {
                { addr1, addr3, addr2, addr4 },
                { addr1, addr3, addr2, addr4 },
                { addr1, addr3, addr2, addr4 },
                { addr1, addr3, addr2, addr4 }
        };
        Boolean[] opResultsExpected = { true, true, false, false };

        Test<OldFashionedEmailAddress> findTest = new Test<>(
                storage,
                StorageOperations.FIND,
                findTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            findTest.run();
            System.out.printf(
                    "Overall address find test result %b.\n",
                    findTest.isSuccessful());
        } catch (Exception e) {
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }

    private static void runAddressDeleteTest(
            SortedStorage<OldFashionedEmailAddress> storage) {
        OldFashionedEmailAddress addr1 = new OldFashionedEmailAddress(
                558,
                "7343 Kuphal Ford",
                "Yukberg",
                "DE",
                62681
        );
        OldFashionedEmailAddress addr2 = new OldFashionedEmailAddress(
                851,
                "397 Analisa Burg",
                "Colefurt",
                "MO",
                58841
        );
        OldFashionedEmailAddress addr3 = new OldFashionedEmailAddress(
                784,
                "739 Houston Branch",
                "Mannport",
                "KS",
                88168
        );
        OldFashionedEmailAddress addr4 = new OldFashionedEmailAddress(
                984,
                "60232 Katharina Summit",
                "East Rodney",
                "NH",
                11376
        );
        OldFashionedEmailAddress addr5 = new OldFashionedEmailAddress(
                435,
                "1744 Altenwerth Freeway",
                "Leannfurt",
                "ID",
                37312
        );

        OldFashionedEmailAddress[] deleteTestCases = {
                addr1, addr2, null, addr5 };
        OldFashionedEmailAddress[][] storageExpected = {
                { addr3, addr2, addr4, null },
                { addr3, addr4, null, null },
                { addr3, addr4, null, null },
                { addr3, addr4, null, null },
        };
        Boolean[] opResultsExpected = { true, true, false, false };

        Test<OldFashionedEmailAddress> deleteTest = new Test<>(
                storage,
                StorageOperations.DELETE,
                deleteTestCases,
                storageExpected,
                opResultsExpected
        );

        try {
            deleteTest.run();
            System.out.printf(
                    "Overall address delete test result %b.\n",
                    deleteTest.isSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("Invalid test cases. Error: %s\n", e.getMessage());
        }
    }


    public static void main(String[] args) throws Exception {
        SortedStorage<String> strStorage = new SortedStorage<>();
        SortedStorage<Integer> intStorage = new SortedStorage<>();
        SortedStorage<MusicStorageOfThePast> musicStorage = new SortedStorage<>();
        SortedStorage<OldFashionedEmailAddress> addressStorage =
                new SortedStorage<>();

        // Insert
        System.out.println("\n--- Inserting: ");
        runStrInsertUniqueTest(strStorage);
        printLineSeparator();
        runIntInsertUniqueTest(intStorage);
        printLineSeparator();
        runMusicInsertUniqueTest(musicStorage);
        printLineSeparator();
        runAddressInsertUniqueTest(addressStorage);

        // Find
        System.out.println("\n--- Find: ");
        runStrFindTest(strStorage);
        printLineSeparator();
        runIntFindTest(intStorage);
        printLineSeparator();
        runMusicFindTest(musicStorage);
        printLineSeparator();
        runAddressFindTest(addressStorage);

        // Delete
        System.out.println("\n--- Deleting: ");
        runStrDeleteTest(strStorage);
        printLineSeparator();
        runIntDeleteTest(intStorage);
        printLineSeparator();
        runMusicDeleteTest(musicStorage);
        printLineSeparator();
        runAddressDeleteTest(addressStorage);

        // Make the storages function non-set-like.
        strStorage = new SortedStorage<>(false);
        intStorage = new SortedStorage<>(false);
        musicStorage = new SortedStorage<>(false);
        addressStorage = new SortedStorage<>(false);

        // Non-unique insert
        System.out.println("\n--- Non-unique inserting: ");
        runStrInsertNonUniqueTest(strStorage);
        printLineSeparator();
        runIntInsertNonUniqueTest(intStorage);
        printLineSeparator();
        runMusicInsertNonUniqueTest(musicStorage);
        printLineSeparator();
        runAddressInsertNonUniqueTest(addressStorage);
    }
}
