package homework_11_inc;

public class Hw11 extends Thread {

    private String info;
    static Object o = new Object(); // The object lock for threads with
                                    // id "0" and "1"
    public Hw11 (String info) {
        this.o = new Object();
        this.info    = info;
    }

    public void run () {
        synchronized ( o ) {
            o.notify(); // As soon as one locks object o it will
                        // notify other threads using the same resource
                        // that it is free
            System.out.println(info);
            try {
                /*
                Here there is a problem where the wait statement could
                be left hanging. This will make the program run and not
                end. There are two ways to resolve this. Either a parameter
                should be given to wait a certain amount of time shown in
                option 1 to continue execution, or another notify call should
                be made to remove to prevent a hanging wait call on another
                thread shown in option 2.

                Depending on which thread is scheduled first, the synchronized
                block will use object o and the other will be blocked. once
                the block is fully executed the other can go, or an object
                can be freed by calling notify. Because there is a wait call
                without a notify call afterwards, the wait on might end up
                being indefinite.
                 */
                o.wait(500); // Option 1 - time limit
                // o.notify(); // Option 2
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    public static void main (String[] args) {
        new Hw11("0").start();
        new Hw11("1").start();
    }
}
