package _07blackjack.blackjack;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The type Draw cards.
 */
public class DrawCards extends JComponent {

    private static final int SIZE_X = 100;
    private static final int SIZE_Y = 150;
    private ArrayList<Card> cards;
    private int x;
    private int y;
    private String string;

    /**
     * Instantiates a new Draw cards.
     *
     * @param cards  the cards
     * @param string the string
     * @param x      the x
     * @param y      the y
     */
    //constructor for the card
    public DrawCards(ArrayList<Card> cards, String string, int x, int y) {
        this.cards = cards;
        this.x = x;
        this.y = y;
        this.string = string;
    }

    //draw the card based on the card library
    public void paintComponent(Graphics g) {
        int i = 0;
        for (Card card : cards) {
            i++;
            String filename = Integer.toString(card.getNumber()) + "_of_" + card.getSuit().toString() + ".png";
            g.drawImage(new ImageIcon("/Users/Wen/proJava/src/_07blackjack/blackjack/pokercards/" + filename).getImage(), x + i * 15, y, SIZE_X, SIZE_Y, null);
        }
    }

}