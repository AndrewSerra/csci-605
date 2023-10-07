package homework_4;

import homework_4.SortedStorage;

public class Test {
    public static void testIt(SortedStorage aSortedStorage)	{

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
    public static void main(String args[] )	{
        SortedStorage aSortedStorage = new SortedStorage();
        testIt(aSortedStorage);
    }
}
