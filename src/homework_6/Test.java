package homework_6;

public class Test {

    final boolean RUN_UNIQUE = false;
    public static void testItWithString(UniqueSortedStorage<String> aSortedStorage)	{

        String toInsert[] = { "8", null, "1", "2"};
        String toDelete[] = { "1", null, "1"};
        String toFind[]   = { "1", null, "1"};


        for (int index = 0; index < toInsert.length; index ++ )	{
            System.out.println("- add(" + toInsert[index] + "): "  + aSortedStorage.add(toInsert[index]));
        }

        System.out.println("- includesNull: "  + aSortedStorage.includesNull());
        System.out.println("- toString: "  + aSortedStorage.toString());

        for (int index = 0; index < toFind.length; index ++ )	{
            System.out.println("- find(" + toFind[index] + "): "  + aSortedStorage.find(toFind[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
    }
    public static void testItWithIntegers(UniqueSortedStorage<Integer> aSortedStorage)	{

        Integer toInsert[] = {  Integer.valueOf(8), null,  Integer.valueOf(1),  Integer.valueOf(2)};
        Integer toDelete[] = {  Integer.valueOf(8), null,  Integer.valueOf(1) };
        Integer toFind[]   = {  Integer.valueOf(8), null,  Integer.valueOf(1),  Integer.valueOf(2)};


        for (int index = 0; index < toInsert.length; index ++ )	{
            System.out.println("- add(" + toInsert[index] + "): "  + aSortedStorage.add(toInsert[index]));
        }

        System.out.println("- includesNull: "  + aSortedStorage.includesNull());
        System.out.println("- toIntegers: "  + aSortedStorage.toString());

        for (int index = 0; index < toFind.length; index ++ )	{
            System.out.println("- find(" + toFind[index] + "): "  + aSortedStorage.find(toFind[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
    }
    public static void testItWithSortedStorage(UniqueSortedStorage aSortedStorage,
                                               UniqueSortedStorage[] theSortedStorages)	{
        UniqueSortedStorage toInsert[] = {  theSortedStorages[0], theSortedStorages[1], null, theSortedStorages[1] };
        UniqueSortedStorage toDelete[] = {  theSortedStorages[0], theSortedStorages[1], null };
        UniqueSortedStorage toFind[]   = {  theSortedStorages[0], theSortedStorages[1], null, theSortedStorages[1] };

        for (int index = 0; index < toInsert.length; index ++ )	{
            System.out.println("- add(" + toInsert[index] + "): "  + aSortedStorage.add(toInsert[index]));
        }

        System.out.println("- includesNull: "  + aSortedStorage.includesNull());
        System.out.println("- toIntegers: "  + aSortedStorage.toString());

        for (int index = 0; index < toFind.length; index ++ )	{
            System.out.println("- find(" + toFind[index] + "): "  + aSortedStorage.find(toFind[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
    }

    public static void testItWithString(NonUniqueSortedStorage<String> aSortedStorage)	{

        String toInsert[] = { "8", null, "1", "2", "8", null, "1", "2" };
        String toDelete[] = { "1", null, "1"};
        String toFind[]   = { "1", null, "1"};


        for (int index = 0; index < toInsert.length; index ++ )	{
            System.out.println("- add(" + toInsert[index] + "): "  + aSortedStorage.add(toInsert[index]));
        }

        System.out.println("- includesNull: "  + aSortedStorage.includesNull());
        System.out.println("- toString: "  + aSortedStorage.toString());

        for (int index = 0; index < toFind.length; index ++ )	{
            System.out.println("- find(" + toFind[index] + "): "  + aSortedStorage.find(toFind[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
    }
    public static void testItWithIntegers(NonUniqueSortedStorage<Integer> aSortedStorage)	{

        Integer toInsert[] = {  Integer.valueOf(8), null,  Integer.valueOf(1),  Integer.valueOf(2)};
        Integer toDelete[] = {  Integer.valueOf(8), null,  Integer.valueOf(1) };
        Integer toFind[]   = {  Integer.valueOf(8), null,  Integer.valueOf(1),  Integer.valueOf(2)};


        for (int index = 0; index < toInsert.length; index ++ )	{
            System.out.println("- add(" + toInsert[index] + "): "  + aSortedStorage.add(toInsert[index]));
        }

        System.out.println("- includesNull: "  + aSortedStorage.includesNull());
        System.out.println("- toIntegers: "  + aSortedStorage.toString());

        for (int index = 0; index < toFind.length; index ++ )	{
            System.out.println("- find(" + toFind[index] + "): "  + aSortedStorage.find(toFind[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
    }
    public static void testItWithSortedStorage(NonUniqueSortedStorage aSortedStorage,
                                               NonUniqueSortedStorage[] theSortedStorages)	{
        NonUniqueSortedStorage toInsert[] = {  theSortedStorages[0], theSortedStorages[1], null, theSortedStorages[1] };
        NonUniqueSortedStorage toDelete[] = {  theSortedStorages[0], theSortedStorages[1], null };
        NonUniqueSortedStorage toFind[]   = {  theSortedStorages[0], theSortedStorages[1], null, theSortedStorages[1] };

        for (int index = 0; index < toInsert.length; index ++ )	{
            System.out.println("- add(" + toInsert[index] + "): "  + aSortedStorage.add(toInsert[index]));
        }

        System.out.println("- includesNull: "  + aSortedStorage.includesNull());
        System.out.println("- toIntegers: "  + aSortedStorage.toString());

        for (int index = 0; index < toFind.length; index ++ )	{
            System.out.println("- find(" + toFind[index] + "): "  + aSortedStorage.find(toFind[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
    }

    public void test()	{
        if(RUN_UNIQUE) {
            UniqueSortedStorage<String> aSortedStringStorage = new UniqueSortedStorage();
            testItWithString(aSortedStringStorage);

            System.out.printf("\n\n------------------\n\n");

            UniqueSortedStorage<Integer> aSortedIntegerStorage = new UniqueSortedStorage();
            testItWithIntegers(aSortedIntegerStorage);

            UniqueSortedStorage aSortedSortedStorageStorage = new UniqueSortedStorage();

            System.out.printf("\n\n------------------\n\n");

            UniqueSortedStorage[] theSortedStorages = {
                    aSortedStringStorage,
                    aSortedIntegerStorage,
                    aSortedSortedStorageStorage
            };
            testItWithSortedStorage(aSortedSortedStorageStorage, theSortedStorages);
        } else {
            NonUniqueSortedStorage<String> aSortedStringStorage = new NonUniqueSortedStorage();
            testItWithString(aSortedStringStorage);

            System.out.printf("\n\n------------------\n\n");

            NonUniqueSortedStorage<Integer> aSortedIntegerStorage = new NonUniqueSortedStorage();
            testItWithIntegers(aSortedIntegerStorage);

            NonUniqueSortedStorage aSortedSortedStorageStorage = new NonUniqueSortedStorage();

            System.out.printf("\n\n------------------\n\n");

            NonUniqueSortedStorage[] theSortedStorages = {
                    aSortedStringStorage,
                    aSortedIntegerStorage,
                    aSortedSortedStorageStorage
            };
            testItWithSortedStorage(aSortedSortedStorageStorage, theSortedStorages);
        }
    }

    public static void main(String args[])	{
        new Test().test();
    }
}
