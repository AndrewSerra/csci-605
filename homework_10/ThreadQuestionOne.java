package homework_10;

import java.util.Random;

public class ThreadQuestionOne extends Thread    {
    private  static Random random = new Random();
    String info;

    public ThreadQuestionOne (String info) {
        this.info    = info;
    }

    public void run () {
        System.err.println(info + " ---> ");
        System.err.println(info + " <--- ");

        if ( info == "first" )
            new ThreadQuestionOne("fourth").start();


    }

    public static void main (String[] args) {
        ThreadQuestionOne aT4_0 = new ThreadQuestionOne("first");
        ThreadQuestionOne aT4_1 = new ThreadQuestionOne("second");
        ThreadQuestionOne aT4_2 = new ThreadQuestionOne("third");

        aT4_0.run();
        aT4_1.run();
        aT4_2.start();
    }
}