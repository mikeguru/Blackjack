package _07blackjack.blackjack;

/**
 * The type Card.
 */
public class Card {

    private suit type;
    private int number;

    /**
     * Instantiates a new Card.
     *
     * @param type   the type
     * @param number the number
     */
    public Card(suit type, int number) {
        this.type = type;
        this.number = number;
    }

    /**
     * Equals boolean.
     *
     * @param card the card
     * @return the boolean
     */
    public boolean equals(Card card) {
        return (card.type == this.type && card.number == this.number);
    }

    /**
     * Gets suit.
     *
     * @return the suit
     */
    public suit getSuit() {
        return type;
    }

    /**
     * Gets number.
     *
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Get point int.
     *
     * @return the int
     */
    public int getPoint() {
        return Math.min(number, 10);
    }

    /**
     * The enum Suit.
     */
//the suit
    public enum suit {

        /**
         * Spades suit.
         */
        SPADES,
        /**
         * Diamonds suit.
         */
        DIAMONDS,
        /**
         * Clubs suit.
         */
        CLUBS,
        /**
         * Hearts suit.
         */
        HEARTS;

        public String toString() {
            switch (this) {
                case SPADES:
                    return "spades";
                case DIAMONDS:
                    return "diamonds";
                case CLUBS:
                    return "clubs";
                case HEARTS:
                    return "hearts";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}