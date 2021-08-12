public class Card {

    private final Suit suit;
    private final Value value;

    public Card(Suit suit, Value value){
        this.suit = suit;
        this.value = value;
    }

    public String toString(){
        return this.suit.toString() + "-" + this.value.toString();
    }

    public Value getValue(){
        return this.value;
    }
}
