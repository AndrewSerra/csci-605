package homework_8_inc;

import java.util.Iterator;

public class Test {
    public static void testInteger0(StorageInterface<Integer> aStorageInterfaceInteger)
            throws StorageHasBeenModifiedException {
        System.out.println("Test 0");
        Iterator<Integer> aIterator =  aStorageInterfaceInteger.iterator();
        aStorageInterfaceInteger.add(null);

        aStorageInterfaceInteger.add(Integer.valueOf("2"));
        aStorageInterfaceInteger.add(Integer.valueOf("3"));
        aStorageInterfaceInteger.add(Integer.valueOf("4"));
        System.out.println("3: " + aStorageInterfaceInteger);
        while ( aIterator.hasNext() )
            System.out.println("in Test: " + aIterator.next());
        aStorageInterfaceInteger.add(Integer.valueOf("5"));
    }
    public static void testInteger1(StorageInterface<Integer> aStorageInterfaceInteger)
            throws StorageHasBeenModifiedException {
        System.out.println("Test 1");
        Iterator<Integer> aIterator =  aStorageInterfaceInteger.iterator();

        aStorageInterfaceInteger.add(Integer.valueOf("1"));
        aStorageInterfaceInteger.add(Integer.valueOf("2"));
        aStorageInterfaceInteger.add(Integer.valueOf("3"));
        aIterator.hasNext();
        aIterator.remove();
        System.out.println("in Test removed: " + aIterator.next());
        while ( aIterator.hasNext() )
            System.out.println("in Test: " + aIterator.next());
    }
    public static void testInteger2(StorageInterface<Integer> aStorageInterfaceInteger)
            throws StorageHasBeenModifiedException	{
        System.out.println("Test 2");
        Iterator<Integer> aIterator =  aStorageInterfaceInteger.iterator();

        aStorageInterfaceInteger.add(Integer.valueOf("1"));
        aStorageInterfaceInteger.add(Integer.valueOf("2"));
        aIterator.hasNext();
        aStorageInterfaceInteger.add(Integer.valueOf("3"));
        System.out.println("in Test: " + aIterator.next());
        System.out.println("This line should not be printed");
    }
    public static void main(String[] args)	{
        try {
            StorageInterface<Integer> aStorageInterfaceInteger =
                    new SortedStorage<>(false);
            testInteger0(aStorageInterfaceInteger);

            aStorageInterfaceInteger = new SortedStorage<>(false);
            testInteger1(aStorageInterfaceInteger);

            aStorageInterfaceInteger = new SortedStorage<>(false);
            testInteger2(aStorageInterfaceInteger);

        } catch (StorageHasBeenModifiedException e)	{
            System.out.println(e.getMessage());
        }
    }
}