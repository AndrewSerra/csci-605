package homework_6;

/**
 * This class is printing out the values of a simulated version of the game FIFA.
 * It prints out the attributes of the two best players in the game, Messi and Ronaldo.
 * The attributes are values that have been imposed on the player's class by the abstract class GamePhysics.
 * It also prints out the values if the interface Movement has been implemented by the player class.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */

public class Fifa {

    /*
        In this example, this example shows why abstract classes are not
        suitable to be used. Here we have soccer player defined and also a
        menu defined. They have nothing in common aside from the action of
        moving. A player can change position on a game field, and in a menu,
        typically the movement is to navigate through a set of buttons and
        lists.

        These two are different types of movements, but they are common for
        both the players and a menu. This is why an abstract class is not
        preferred.

        Another advantage of using interfaces is that interfaces can define
        simple qualities and each class can implement multiple of these
        interfaces. Abstract classes are restricted to be extended only one
        by a class.

        As an example for where to use abstract classes, the best usage is
        where similar classes are sharing similar methods. In our example
        Messi and Ronaldo are both characters in a game which require to act
        according a physics engine which is a shared quality. This won't be
        useful with interfaces because There are no other type of class that
        will directly need to behave depending on physics.
     */

    public static void main(String[] args) {

        Ronaldo cr7 = new Ronaldo();
        Messi m10 = new Messi();
        Menu menu = new Menu();

        System.out.println("Default player attributes of Ronaldo:  " + cr7);
        System.out.println("Default player attributes of Ronaldo:  " + m10);
        System.out.println("Player attributes of Ronaldo after implementing abstract method:  " + cr7.Attributes());
        System.out.println("Player attributes of Messi after implementing abstract method:  " + m10.Attributes());
        System.out.println("After implementing interface for Ronaldo:  " + cr7.moveLeft());
        System.out.println("After implementing interface for Messi:  " + m10.moveLeft());
        System.out.println("After implementing interface for Fifa game menu:  " + menu.moveLeft());
    }
}