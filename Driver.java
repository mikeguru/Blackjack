package _07blackjack.blackjack;

import javax.swing.*;

/**
 * The type Driver.
 */
public class Driver {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        //Frame for the game
        BlackJackFrame frame = new BlackJackFrame();
        frame.setSize(1000, 500);
        frame.setTitle("Welcome to Black Jack");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}