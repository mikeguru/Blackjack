package _07blackjack.blackjack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The type Black jack frame.
 */
public class BlackJackFrame extends JFrame {

    //instance for below implemetation
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;

    private JTextField textPlayerScore;
    private JTextField textCurrentMoney;
    private JTextField textRoundNum;
    private JTextField textBet;

    private int bet;
    private int balance;
    private int round;
    private int surrender;
    private ArrayList<Card> playerCards;
    private ArrayList<Card> computerCard;

    private int computerScore;
    private int playerScore;
    private int status;
    private JLabel text4;
    private DrawCards drawDealer;
    private DrawCards drawPlayer;

    private JPanel imPanel = new JPanel();

    /**
     * Instantiates a new Black jack frame.
     */
    public BlackJackFrame() {
        playerCards = new ArrayList<Card>();
        computerCard = new ArrayList<Card>();
        createComponents();
    }

    /**
     * Create components.
     */
    public void createComponents() {

        //button as named
        button1 = new JButton("Hit");
        button2 = new JButton("Stand");
        button3 = new JButton("Double Down");
        button4 = new JButton("Next Round");
        button5 = new JButton("Surrender");
        button6 = new JButton("Exit");

        //player balance
        balance = 1000;

        //number of rounds
        round = 1;

        //check whether game begun
        status = 1;

        textCurrentMoney = new JTextField(10);
        textPlayerScore = new JTextField(10);
        textRoundNum = new JTextField(10);
        textBet = new JTextField(10);

        //text on screen
        JLabel text1 = new JLabel("\n\nYour Current Money:");
        JLabel text2 = new JLabel("\n\nGame Round:");
        JLabel text5 = new JLabel("\n\nYou Allocate");

        //game status
        text4 = new JLabel("");

        //initialize the Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 10));

        //add all the buttons
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        buttonPanel.add(button5);
        buttonPanel.add(button6);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        //initialize the panel
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(10, 1));
        textCurrentMoney.setEditable(false);
        textPlayerScore.setEditable(false);
        textRoundNum.setEditable(false);

        //add text to the panel
        scorePanel.add(text1);
        textCurrentMoney.setText(Integer.toString(balance));
        scorePanel.add(textCurrentMoney);
        scorePanel.add(text2);
        textRoundNum.setText(Integer.toString(round));
        scorePanel.add(textRoundNum);
        scorePanel.add(text5);

        //defualt setting on amount of bet
        bet = 10;
        textBet.setText(Integer.toString(bet));
        scorePanel.add(textBet);
        scorePanel.add(text4);
        panel.add(scorePanel, BorderLayout.EAST);

        //iniltization
        drawPlayer = new DrawCards(playerCards, "PLAYER", 10, 10);
        drawDealer = new DrawCards(computerCard, "DEALER", 10, 10);
        imPanel = new JPanel();
        imPanel.setLayout(new GridLayout(2, 1));
        imPanel.add(drawDealer);
        imPanel.add(drawPlayer);

        //add to the position
        panel.add(imPanel, BorderLayout.CENTER);

        //add to the panel
        add(panel);

        //add the click listener
        ActionListener listener1 = new ClickListener1();
        button1.addActionListener(listener1);
        ActionListener listener2 = new ClickListener2();
        button2.addActionListener(listener2);
        ActionListener listener3 = new ClickListener3();
        button3.addActionListener(listener3);
        ActionListener listener4 = new ClickListener4();
        button4.addActionListener(listener4);
        ActionListener listener5 = new ClickListener5();
        button5.addActionListener(listener5);
        ActionListener listener6 = new ClickListener6();
        button6.addActionListener(listener6);
    }

    /**
     * Computer run.
     */
    //game round
    public void computerRun() {
        Card c;
        int down = scoreDown(computerCard);

        //The dealer stands on a soft 17 as wiki
        while (down <= 17) {
            //otherwise keep going
            c = generateCard();
            computerCard.add(c);
            down = scoreDown(computerCard);
        }

        //repaint the card
        if (scoreDown(playerCards) <= 21)
            drawDealer.repaint();

    }

    /**
     * Launch game.
     */
    public void launchGame() {

        //as long as player cards is bigger than 21, the computer won
        if (scoreDown(playerCards) > 21) {
            text4.setText("Computer Won");
            bet = Integer.parseInt(textBet.getText());
            balance -= bet;
            textCurrentMoney.setText(Integer.toString(balance));
            playerScore = scoreDown(playerCards);

            textPlayerScore.setText(Integer.toString(playerScore));
        }
         //as long as the computer cards is bigger than 21, the player won
         else if (scoreDown(computerCard) > 21) {
            text4.setText("You Won");
            bet = Integer.parseInt(textBet.getText());
            balance += 2 * bet;
            textCurrentMoney.setText(Integer.toString(balance));

            //adjust accordinglys
            if (scoreUp(playerCards) > 21)
                playerScore = scoreDown(playerCards);
            else playerScore = scoreUp(playerCards);
            textPlayerScore.setText(Integer.toString(playerScore));
        } else {

            //check for who busted
            if (scoreUp(playerCards) > 21)
                playerScore = scoreDown(playerCards);
            else playerScore = scoreUp(playerCards);


            //check for who busted
            if (scoreUp(computerCard) > 21)
                computerScore = scoreDown(computerCard);
            else
                computerScore = scoreUp(computerCard);

            if (surrender == 1) {
                //half to the player; this terminates the player's interest in the hand.
                text4.setText("You Surrendered");
                bet = Integer.parseInt(textBet.getText());
                balance -= (bet / 2);
                textCurrentMoney.setText(Integer.toString(balance));
                //reset
                surrender = 0;
            }

            //compare the card value, playerScore > computerScore, player won
            else if (playerScore > computerScore) {
                text4.setText("You Won");
                bet = Integer.parseInt(textBet.getText());
                balance += 2 * bet;
                textCurrentMoney.setText(Integer.toString(balance));
            }

            //opposite, player lose
            else if (playerScore < computerScore) {
                //computer win and player lose bet
                text4.setText("Computer Won");
                bet = Integer.parseInt(textBet.getText());
                balance -= bet;
                textCurrentMoney.setText(Integer.toString(balance));
            }

            //draw nothing change
            else {
                text4.setText("Draw Game");
                textCurrentMoney.setText(Integer.toString(balance));
            }
            textPlayerScore.setText(Integer.toString(playerScore));
        }
        status = 0;
        surrender = 0;
    }

    /**
     * Generate card card.
     *
     * @return the card
     */
    //generate the card
    public Card generateCard() {
        int flag;
        Card c;
        do {
            flag = 0;

            //inlize the new card
            c = new Card(Card.suit.values()[(int) (4 * Math.random())], (int) (13 * Math.random() + 1));
            for (Card playerCard : playerCards) {
                if (playerCard.equals(c)) flag = 1;
            }
            for (Card dealerCard : computerCard) {
                if (dealerCard.equals(c)) flag = 1;
            }
        } while (flag == 1);
        return c;
    }

    /**
     * Score up int.
     *
     * @param cards the cards
     * @return the int
     */
    //add the score
    public int scoreUp(ArrayList<Card> cards) {
        int sum = 0;
        int num_of_A = 0;
        for (Card card : cards) {
            if (card.getNumber() == 1)
                num_of_A++;
            sum += card.getPoint();
        }
        if (num_of_A == 0)
            return sum;
        else
            return (sum + 10);
    }

    /**
     * Score down int.
     *
     * @param cards the cards
     * @return the int
     */
    //add the score
    public int scoreDown(ArrayList<Card> cards) {

        int sum = 0;
        for (Card card : cards) {
            sum += card.getPoint();
        }
        return sum;
    }

    /**
     * The type Click listener 1.
     */
    //corresponding listener
    class ClickListener1 implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (status == 1) {
                Card c = generateCard();
                playerCards.add(c);
                drawPlayer.repaint();

                int up = scoreUp(playerCards);
                int down = scoreDown(playerCards);

                if (up == down) {
                    textPlayerScore.setText(Integer.toString(up));
                } else {
                    textPlayerScore.setText(Integer.toString(down) + '/' + Integer.toString(up));
                }

                if (down > 21) {
                    computerRun();
                    launchGame();
                }
            }
        }
    }

    /**
     * The type Click listener 2.
     */
    //corresponding listener
    class ClickListener2 implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (status == 1) {
                computerRun();
                launchGame();
            }
        }
    }

    /**
     * The type Click listener 3.
     */
    //corresponding listener
    class ClickListener3 implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (status == 1) {
                Card c = generateCard();
                playerCards.add(c);
                drawPlayer.repaint();
                textBet.setText(Integer.toString(bet * 2));
                computerRun();
                launchGame();
            }
        }
    }

    /**
     * The type Click listener 4.
     */
    //corresponding listener
    class ClickListener4 implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            playerCards.clear();
            computerCard.clear();
            drawDealer.repaint();
            drawPlayer.repaint();

            text4.setText("Continue");
            textPlayerScore.setText("0");
            bet = 10;
            textBet.setText(Integer.toString(10));

            round++;
            textRoundNum.setText(Integer.toString(round));
            status = 1;
        }
    }

    /**
     * The type Click listener 5.
     */
    //corresponding listener
    class ClickListener5 implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            playerCards.clear();
            computerCard.clear();
            drawDealer.repaint();
            drawPlayer.repaint();

            text4.setText("Continue");
            textPlayerScore.setText("0");
            bet = 10;
            textBet.setText(Integer.toString(10));
            round++;
            textRoundNum.setText(Integer.toString(round));

            status = 1;
            surrender = 1;
        }
    }

    /**
     * The type Click listener 6.
     */
    //corresponding listener
    class ClickListener6 implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    }
}