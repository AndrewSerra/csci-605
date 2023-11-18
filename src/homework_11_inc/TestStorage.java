package homework_11_inc;

import java.util.Iterator;
import java.util.Random;

public class TestStorage {

    private boolean test1_equal_add_del() throws InterruptedException {
        SortedStorage<Integer> sortedStorage = new SortedStorage<>();
        int operationCount = 100;

        class AddRunOperation implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < operationCount; i++) {
                    try {
                        sortedStorage.add(i);
                    } catch (StorageHasBeenModifiedException e) {
                        // Handle exception if needed
                        e.printStackTrace();
                    }
                }
            }
        }

        class DeleteRunOperation implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < operationCount; i++) {
                    try {
                        sortedStorage.delete(i);
                    } catch (StorageHasBeenModifiedException e) {
                        // Handle exception if needed
                        e.printStackTrace();
                    }
                }
            }
        }

        Thread addThread = new Thread(new AddRunOperation());
        Thread deleteThread = new Thread(new DeleteRunOperation());

        addThread.start();
        deleteThread.start();

        addThread.join();
        deleteThread.join();

        boolean result = true;

        if(sortedStorage.getTotalCount() != 0) {
            result = false;
        }
        return result;
    }

    private boolean test2_equal_add_del_w_null() throws InterruptedException {
        SortedStorage<Integer> sortedStorage = new SortedStorage<>();
        int operationCount = 100;

        class AddRunOperation implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < operationCount; i++) {
                    try {
                        if((i % 5) == 0) {
                            sortedStorage.add(i);
                        } else {
                            sortedStorage.add(null);
                        }
                    } catch (StorageHasBeenModifiedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        class DeleteRunOperation implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < operationCount; i++) {
                    try {
                        if((i % 5) == 0) {
                            sortedStorage.delete(i);
                        } else {
                            sortedStorage.delete(null);
                        }
                    } catch (StorageHasBeenModifiedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Thread addThread = new Thread(new AddRunOperation());
        Thread deleteThread = new Thread(new DeleteRunOperation());

        addThread.start();
        deleteThread.start();

        addThread.join();
        deleteThread.join();

        boolean result = true;

        if(sortedStorage.getTotalCount() != 0) {
            result = false;
        }
        return result;
    }

    private boolean test3_add_two_threads() throws InterruptedException {
        SortedStorage<Integer> sortedStorage = new SortedStorage<>(false);
        int operationCount = 50;
            class AddRunOperation implements Runnable {
                @Override
                public void run() {
                    for (int i = 0; i < operationCount; i++) {
                        try {
                            sortedStorage.add(new Random().nextInt(500));
                        } catch (StorageHasBeenModifiedException e) {
                            // Handle exception if needed
                            e.printStackTrace();
                        }
                    }
                }
            }

        Thread add1Thread = new Thread(new AddRunOperation());
        Thread add2Thread = new Thread(new AddRunOperation());

        add1Thread.start();
        add2Thread.start();

        add1Thread.join();
        add2Thread.join();

        boolean result = true;

        Iterator<Integer> iter = sortedStorage.iterator();
        Integer prev = iter.next();

        while(iter.hasNext()) {
            Integer next = iter.next();
            if(prev > next) {
                result = false;
                break;
            }
        }
        return result;
    }


    public static void main(String[] args) throws InterruptedException {
        TestStorage test_suite = new TestStorage();

        System.out.println("Test 1:");
        System.out.println("Equal Add and Delete operations.");
        System.out.println("Expected to have no errors, and zero items.");
        boolean test1_result = test_suite.test1_equal_add_del();

        if(!test1_result) {
            System.out.println("\tFAIL: Storage contains more than 0 items");
        } else {
            System.out.println("\tSUCCESS");
        }

        System.out.println("\nTest 2:");
        System.out.println("Equal Add and Delete operations with null items.");
        System.out.println("Expected to have no errors, and zero items.");
        boolean test2_result = test_suite.test2_equal_add_del_w_null();

        if(!test2_result) {
            System.out.println("\tFAIL: Storage contains more than 0 items");
        } else {
            System.out.println("\tSUCCESS");
        }

        System.out.println("\nTest 3:");
        System.out.println("100 items added using two threads, non-set behavior.");
        System.out.println("Expected to have no errors, and 100 items.");
        boolean test3_result = test_suite.test3_add_two_threads();

        if(!test3_result) {
            System.out.println("\tFAIL: Storage contains items not sorted.");
        } else {
            System.out.println("\tSUCCESS");
        }
    }
}
