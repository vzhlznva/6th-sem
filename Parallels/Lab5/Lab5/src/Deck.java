import java.util.ArrayList;
import java.util.Random;

public class Deck {

    private ArrayList<Card> cards;

    public Deck(){
        //Создание новой колоды игральных карт
        this.cards = new ArrayList<Card>();

    }

    //Добавляем 52 карты в колоду
    public void createFullDeck(){
        //Генерация карт
        //Цикл по мастям
        for(Suit cardSuit : Suit.values()){
            //Цикл по значениям
            for(Value cardValue : Value.values()){
                //Добавление новой карты
                this.cards.add(new Card(cardSuit,cardValue));
            }
        }
    }


    //Перемешиваем колоду карт
    public void shuffle(){
        //Создаем новый arraylist для временного хранения перетасованных карт
        ArrayList<Card> tmpDeck = new ArrayList<Card>();
        //Рандомно достаем из первоначальной колоды и запихиваем в перетасованную колоду
        Random random = new Random();
        int randomCardIndex = 0;
        int originalSize = this.cards.size();
        for(int i = 0; i<originalSize;i++){
            //генерируем случайное число
            randomCardIndex = random.nextInt((this.cards.size() - 1) + 1);
            //засовываем новую карту во временную колоду
            tmpDeck.add(this.cards.get(randomCardIndex));
            //удаляем выбранную карту из старой колоды
            this.cards.remove(randomCardIndex);
        }
        //перетасованную колоду помещаем в переменную с колодой
        this.cards = tmpDeck;
    }


    //Убрать карту с колоды
    public void removeCard(int i){
        this.cards.remove(i);
    }
    //Взять карту с колоды
    public Card getCard(int i){
        return this.cards.get(i);
    }

    //Добавить карту в колоду
    public void addCard(Card addCard){
        this.cards.add(addCard);
    }

    //Взять верхнюю карту с колоды
    public void draw(Deck comingFrom){
        this.cards.add(comingFrom.getCard(0));
        comingFrom.removeCard(0);
    }

    //Вывод колоды / руки
    public String toString(){
        StringBuilder cardListOutput = new StringBuilder();
        int i = 0;
        for(Card aCard : this.cards){
            cardListOutput.append(" ").append(aCard.toString());
            i++;
        }
        return cardListOutput.toString();
    }

    // Возвращаем все карты в колоду
    public void moveAllToDeck(Deck moveTo){
        int thisDeckSize = this.cards.size();
        for(int i = 0; i < thisDeckSize; i++){
            moveTo.addCard(this.getCard(i));
        }
        for(int i = 0; i < thisDeckSize; i++){
            this.removeCard(0);
        }
    }

    public int deckSize(){
        return this.cards.size();
    }

    //Подсчет значения карт
    public int cardsValue(){
        int totalValue = 0;
        int aces = 0;
        //Для каждой карты в руке
        for(Card aCard : this.cards){
            //Switch по возможным значениям
            switch (aCard.getValue()) {
                case TWO, JACK -> totalValue += 2;
                case THREE, QUEEN -> totalValue += 3;
                case FOUR, KING -> totalValue += 4;
                case FIVE -> totalValue += 5;
                case SIX -> totalValue += 6;
                case SEVEN -> totalValue += 7;
                case EIGHT -> totalValue += 8;
                case NINE -> totalValue += 9;
                case TEN -> totalValue += 10;
                case ACE -> aces += 1;
            }
        }

        //Определяем общее значения карт с тузами
        //Тузы или 11, или 1 - если туз = 11 будет превышать 21, то туз будет равен 1
        for(int i = 0; i < aces; i++){
            if (totalValue > 10){
                totalValue += 1;
            }
            else{
                totalValue += 11;
            }
        }

        //Возврат значения
        return totalValue;

    }


}