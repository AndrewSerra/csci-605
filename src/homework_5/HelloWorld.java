package homework_5;

public class HelloWorld {
    static final int START = 0;
    static int aInt = 0;
    String aString = "";
    static String bString = "";

    final int END;
    {
        END = 10;
    }

    public HelloWorld()	{
        aString = "hello";
        bString = "hello";
        aInt ++;
    }

    public HelloWorld(int aInt)	{
        this.aInt = aInt;
    }

    private static int getCounter()	{
        return aInt;
    }

    private void setState(int aInt)	{
        this.aInt = aInt;
    }

    private void setBoth(HelloWorld aHelloWorld)	{
        aInt = 42;
        aHelloWorld.setState(aInt * 2 );
    }

    public void setStrings()	{
        for ( int index = START; index < END; index ++ )	{
            aString += index;
            bString += index;
        }
    }

    public String toString()	{
        return "" + aInt + "/" + aString + "/" + bString;
    }

    public static void main(String args[] )	{
        HelloWorld aHelloWorld1 = new HelloWorld();
        HelloWorld aHelloWorld2 = new HelloWorld(4);
        new HelloWorld();
        aHelloWorld1.setBoth(aHelloWorld2);
        aHelloWorld1.setStrings();
        aHelloWorld2.setStrings();
        System.out.println( aHelloWorld1);
        System.out.println( aHelloWorld2);
        System.out.println( getCounter());
    }
}
