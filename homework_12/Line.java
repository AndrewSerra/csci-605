package homework_12;

import java.util.HashMap;
import java.util.Objects;

/**
 * This class simulates firefighters passing a bucket in order
 * and returning back to the beginning that repeats n times.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class Line {
    private HashMap<String, Integer> options = null;
    private static final Object o = new Object();
    private static volatile int order = 1;
    private static volatile boolean allowRun = false;
    private static volatile boolean seqComplete = false;

    /**
     * A static runnable class that simulates execution in order
     * using the id passed to the class.
     */
    static class Printer implements Runnable {

        int id;
        boolean isLast;
        public Printer(int _id, boolean _isLast) {
            id = _id;
            isLast = _isLast;
        }

        private String getFireFighterMessage() {
            StringBuilder builder = new StringBuilder();

            if (id == 1) {
                builder.append("Bucket is filled by firefighter ")
                        .append(id)
                        .append(".");
            } else {
                builder.append("\tbucket is handed to firefighter ")
                        .append(id)
                        .append(".");
                if(isLast) {
                    builder.append("\n\t\tand firefighter ")
                            .append(id)
                            .append(" empties the bucket and\n")
                            .append("\t\tdrops bucket and firefighter 1 " +
                                    "catches the bucket.");
                }
            }
            return builder.toString();
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
                            System.out.println(getFireFighterMessage());
                            order++;

                            if(isLast) {
                                seqComplete = true;
                            }
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
                if(Objects.equals(args[i], "-soManyFireFighters")) {
                    put("soManyFireFighters", Integer.parseInt(args[i+1]));
                } else if(Objects.equals(args[i], "-soOften")) {
                    put("soOften", Integer.parseInt(args[i+1]));
                }
            }
        }};

        if(options.size() != 2) {
            throw new IllegalArgumentException(
                    "Invalid keys provided. Only -soManyFireFighters and -soOften allowed.");
        }
    }

    /**
     * Execution of sequential printing of thread ids.
     *
     * @throws Exception
     */
    public void execute() throws Exception {

        if(!options.containsKey("soManyFireFighters") || !options.containsKey("soOften")) {
            throw new Exception("Incomplete arguments.");
        }

        Thread[] threads = new Thread[options.get("soManyFireFighters")];

        for(int i=0; i < threads.length; i++) {
            threads[i] = new Thread(
                    new Line.Printer(i+1, i == (threads.length - 1)));
            threads[i].start();
        }
        int count = 0;

        while(count < options.get("soOften")) {
            synchronized (o) {
                o.notify();
                allowRun = true;
                if(seqComplete) {
                    o.wait();
                    allowRun = false;
                    count++;
                    order = 1;
                    seqComplete = false;
                }
            }
        }

        for (Thread thread : threads) thread.interrupt();
    }

    public static void main(String[] args) throws Exception {
        Line lineTransfer = new Line();
        lineTransfer.parseArgs(args);
        lineTransfer.execute();
    }
}
