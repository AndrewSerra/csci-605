package homework_12;

import java.util.HashMap;
import java.util.Objects;


/**
 * This class takes command line variables for number of threads
 * and how many times the sequence has to be executed. The sequence
 * consists of threads printing their id number when it is their turn.
 * Repeats until the amount of "soOften" value is reached.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class SoMany {

    private HashMap<String, Integer> options = null;
    private static final Object o = new Object();
    private static volatile int order = 0;
    private static volatile boolean allowRun = false;

    /**
     * A static runnable class that simulates execution in order
     * using the id passed to the class.
     */
    static class Printer implements Runnable {

        int id;
        public Printer(int _id) {
            id = _id;
        }

        @Override
        public void run() {

            while (true) {
                synchronized (o) {
                    try {
                        if(!allowRun) {
                            o.wait();
                        }
                        o.notify();
                        if(id == order) {
                            System.out.println("---> " + id);
                            order++;
                        }
                        o.wait();
                    } catch (InterruptedException e) {
                      break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Parse arguments into the options hashmap.
     *
     * @param args - Command line argument array.
     */
    public void parseArgs(String[] args) {
        if(args.length != 4) {
            throw new IllegalArgumentException("Incorrect amount of arguments");
        }
        options = new HashMap<>() {{
            for(int i = 0; i < 4; i += 2) {
                if(Objects.equals(args[i], "-nThreads")) {
                    put("nThreads", Integer.parseInt(args[i+1]));
                } else if(Objects.equals(args[i], "-soOften")) {
                    put("soOften", Integer.parseInt(args[i+1]));
                }
            }
        }};

        if(options.size() != 2) {
            throw new IllegalArgumentException(
                    "Invalid keys provided. Only -nThreads and -soOften allowed.");
        }
    }

    /**
     * Execution of sequential printing of thread ids.
     *
     * @throws Exception
     */
    public void execute() throws Exception {

        if(!options.containsKey("nThreads") || !options.containsKey("soOften")) {
            throw new Exception("Incomplete arguments.");
        }

        Thread[] threads = new Thread[options.get("nThreads")];

        for(int i=0; i < threads.length; i++) {
            threads[i] = new Thread(new Printer(i));
            threads[i].start();
        }
        int count = 0;

        while(count < options.get("soOften")) {
            System.out.println("Running round " + (count + 1));
            synchronized (o) {
                o.notify();
                allowRun = true;
                o.wait();
                allowRun = false;
                count++;
                order = 0;
            }
        }

        for (Thread thread : threads) thread.interrupt();
    }

    public static void main(String[] args) throws Exception {
        SoMany writer = new SoMany();
        writer.parseArgs(args);
        writer.execute();
    }
}
