package homework_12;

import java.util.ArrayList;
import java.util.Vector;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * This class is a Consumer Producer model that takes in the
 * number of producers and consumers from the command line. The
 * design foes not contain busy loops.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class ConsumerProducer extends Thread {

    public static void main(String[] args)	{
        if(args.length != 2) {
            System.out.println(
                    "Usage: java ConsumerProducer <num-producers> <num-consumers>");
            throw new IllegalArgumentException(
                    "Incorrect number of arguments.");
        }

        if(!Pattern.matches("^\\d+$", args[0]) ||
                !Pattern.matches("^\\d+$", args[1])) {
            throw new IllegalArgumentException(
                    "Arguments must be positive integers.");
        }

        int soManyC = Integer.parseInt(args[1]);
        int soManyP = Integer.parseInt(args[0]);
        Storage theStorage = new Storage();

        System.out.println("# producer = " + soManyP );
        System.out.println("# consumer = " + soManyC );


        for (int id = 1 ; id <= soManyP ; id ++)	{
            new Producer(id, theStorage).start();
        }

        for (int id = 1 ; id <= soManyC ; id ++)	{
            new Consumer(id, theStorage).start();
        }
    }
}

class Storage {
    static final int N = 100;
    static int soManyAreIn = 0;
    int soMany = 0; 			// counter, used for verification
    private ArrayList theStorage = new ArrayList(N);
    private final Object sync = new Object();

    void test()	{
        if ( soManyAreIn != 1  )	{
            System.out.println("soManyAreIn " + soManyAreIn );
            System.exit(0);
        }
        if ( soMany > N  )	{
            System.out.println("overflow " + soMany );
            System.exit(0);
        }
        if ( soMany < 0 )	{
            System.out.println("underflow " + soMany );
            System.exit(0);
        }
        try {
            Thread.sleep(1000);
        } catch ( Exception e ) { }
    }

    void addItems(Vector addTheseItems)	{
        synchronized (sync) {
            soManyAreIn++;
            while ((soMany + addTheseItems.size()) > N) {
                try {
                    // Wait because storage full
                    soManyAreIn--;
                    sync.wait();
                    soManyAreIn++;
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

            for(int index = 0; index < addTheseItems.size(); index++) {
                theStorage.add(addTheseItems.elementAt(index));
            }

            soMany += addTheseItems.size();
            test();
            soManyAreIn--;
            sync.notifyAll();
        }
    }

    Vector consume(int id)	{
        Vector aVector = new Vector(id);

        synchronized (sync) {
            soManyAreIn++;
            while (soMany == 0) {
                try {
                    soManyAreIn--;
                    sync.wait();
                    soManyAreIn++;
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            soMany -= Math.min(id, soMany);
            for(int index = 0; index < Math.min(id, soMany); index++)	{
                aVector.add(theStorage.remove(0));
            }

            test();
            soManyAreIn--;
            sync.notifyAll();
        }
        return aVector;
    }
}

class Consumer extends Thread {
    int id;
    Storage thisStorage;
    final int SO_MANY;

    Consumer(int id, Storage thisStorage)	{
        this.id = id;
        this.thisStorage = thisStorage;
        SO_MANY = id * 3;
        setName("Consumer: " + id );
        System.out.println("C: " + id );
    }
    public void run()	{
        while(true) {
            Vector aVector = thisStorage.consume(id);

            if(thisStorage.soMany == 0) {
                break;
            }
        }
    }
}

class Producer extends Thread {
    int id;
    final int SO_MANY;
    Storage thisStorage;

    Producer(int id, Storage thisStorage)	{
        this.id = id;
        this.thisStorage = thisStorage;
        SO_MANY = id * 7;
        setName("Producer: " + id );
        System.out.println("P: " + id );
    }
    public void run()	{
        Vector aVector = new Vector();
        for(int counter = 0; counter < SO_MANY; counter++)	{
            aVector.add(id + "_" + new Date());
        }
        thisStorage.addItems(aVector);
    }
}
