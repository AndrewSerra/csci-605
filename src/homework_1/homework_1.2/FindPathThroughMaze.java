/*
 * FindPathThroughMaze.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */

/**
 * Looks for an exit in the maze provided in the class. If there is an exit,
 * the paths and the path length is printed.
 *
 * @author      Andrew Serra
 */
public class  FindPathThroughMaze {
    /*		---> column
     *		|
     *		|
     *		v
     *		row
     * The maze has no circles
     */

    static int[][] maze =
                   {{-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 },
                    {-9, -1, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -9 },
                    {-9, -1, -1,  0,  0,  0,  0, -1,  0,  0,  0, -1, -9 },
                    {-9, -1, -1,  0, -1, -1, -1, -1,  0, -1,  0, -1, -9 },
                    {-9, -1, -1,  0,  0,  0,  0,  0,  0, -1,  0, -1, -9 },
                    {-9, -1,  0, -1, -1, -1,  0, -1,  0,  0,  0, -1, -9 },
                    {-9, -1,  0, -1, -1, -1,  0, -1, -1, -1, -1, -1, -9 },
                    {-9, -1,  0, -1, -1,  0,  0, -1, -1, -1, -1, -1, -9 },
                    {-9, -1,  0, -1, -1,  0, -1, -1, -1, -1, -1, -1, -9 },
                    {-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 } };

//    static int[][] maze =
//                   {{-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 },
//                    {-9, -1, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -9 },
//                    {-9, -1, -1,  0,  0,  0,  0, -1,  0,  0,  0, -1, -9 },
//                    {-9, -1, -1,  0, -1, -1, -1, -1,  0, -1,  0, -1, -9 },
//                    {-9, -1, -1,  0,  0,  0,  0,  0,  0, -1,  0, -1, -9 },
//                    {-9, -1,  0, -1, -1, -1,  0, -1,  0,  0,  0, -1, -9 },
//                    {-9, -1,  0, -1, -1, -1,  0, -1, -1, -1, -1, -1, -9 },
//                    {-9, -1,  0, -1, -1,  0,  0, -1, -1, -1, -1, -1, -9 },
//                    {-9, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -9 },
//                    {-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 } };

    static int entryRow = 1;
    static int entryColumn = 3;
    static boolean isExitFound = false;

    /**
     * Prints the map path in a formatted string set.
     *
     * @param       maze    2D array that is the state of the maze
     */
    public static void printMaze(int maze[][])	{
        for ( int row = 0; row < maze.length; row++ )	{
            for ( int column = 0; column < maze[0].length; column++ ) {
                int currentItem = maze[row][column];

                if(currentItem == -9) {
                    continue;
                }

                if(currentItem > 0) {
                    System.out.print(":");
                } else {
                    System.out.print("*");
                }
            }
            System.out.println("");
        }
        return;
    }

    /**
     * Determines whether the given row and coordinate set is a wall in the maze
     *
     * @param       row       Array index of the target row
     * @param       column    Array index of the target column
     */
    public static boolean isWallNode(int row, int column) {
        if (maze[row][column] == -9) {
            return true;
        }
        return false;
    }

    /**
     * Determines whether the given row and coordinate set is a edge in the maze
     *
     * @param       row       Array index of the target row
     * @param       column    Array index of the target column
     */
    public static boolean isEdgeNode(int row, int column) {
        if (maze[row][column] == -1) {
            return true;
        }
        return false;
    }

    /**
     * Given a set of integers, returns the maximum value.
     *
     * @param       solns     All the numbers being compared to find the maximum
     */
    public static int getMaxSolution(int... solns) {
        int max = solns[0];

        for(int i = 1; i < solns.length; i++) {
            if(solns[i] > max) {
                max = solns[i];
            }
        }
        return max;
    }

    /**
     * Recursive function to run Depth First Search on the maze. Looks for
     * the pathsa and marks them as each item is visited.
     *
     * @param       row     Row index of the current node
     * @param       column  Column index of the current node
     * @param       count   Current path length or depth number
     *
     * @return              Length of the path found at the given depth
     */
    public static int findNextFree(int row, int column, int count) {
//        System.out.printf("Step 1 - %d %d %d\n", row, column, count);

        if(row < 0 || column < 0
            || row > maze.length || column > maze[0].length) {
            return -1;
        }
        if(isEdgeNode(row, column) || maze[row][column] > 0) {
            return -1;
        }
        if(isWallNode(row, column)) {
            System.out.printf("%d %d %d %d\n", row, column, count,
                    maze[row][column]);
            isExitFound = true;
            return count;
        }
        // Convert the 0 to an integer greater than 0 to indicate being visited
        maze[row][column] = count;

        int solution1 = findNextFree(row+1, column  , count+1);
        int solution2 = findNextFree(row  , column+1, count+1);
        int solution3 = findNextFree(row  , column-1, count+1);
        int solution4 = findNextFree(row-1, column  , count+1);

        return getMaxSolution(solution1, solution2, solution3, solution4);
    }

    public static void main(String[] args){
        int count = findNextFree(entryRow, entryColumn, 1);
        printMaze(maze);
        System.out.println("-----------------");
        System.out.printf("Path length: %d -- %b\n", count, isExitFound);
        System.out.println("-----------------");
        return;
    }
}