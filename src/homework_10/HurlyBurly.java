package homework_10;

public class HurlyBurly extends Thread {

    static int aStaticInt;
    int id;

    HurlyBurly(int id) {
        this.id = id;
        aStaticInt = id;
        if ( id == 1 ) {
            new HurlyBurly(2).run();
        }
    }
    public void run() {
        System.out.println( id + " -----> ");
        System.out.println("id/aStaticInt = " + id + "/" + aStaticInt );
        System.out.println( id + " <----- ");
    }
    public static void main( String[] args ) {
        new HurlyBurly(1).start();
    }
}
