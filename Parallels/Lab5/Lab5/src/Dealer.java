import java.io.*;
import java.net.*;

public class Dealer {

    // необходимые переменные для работы сервера
    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;
    public static boolean endGame = false;

    public static void main(String[] args){
        try{
            try{
                // привязываю серверный сокет к порту
                server = new ServerSocket(3000);
                System.out.println("Welcome to BlackJack!");
                // прослушиваю подключение клиента к серверному сокету
                clientSocket = server.accept();
                System.out.println("Player connected!");
                try{
                    // необходимые переменные для InputStream и OutputStream
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    // создаю объект колоды, заполняю колоду и перемешиваю карты в ней
                    Deck playingDeck = new Deck();
                    playingDeck.createFullDeck();
                    playingDeck.shuffle();
                    System.out.println("Deck is created!");

                    // создаю объекты руки игрока и руки дилера
                    Deck playerCards = new Deck();
                    Deck dealerCards = new Deck();

                    out.write("STARTING THE GAME!\n");
                    out.flush();

                    while(!endGame) {
                        out.write("Dealing...\n");
                        out.flush();

                        // набираю 2 карты в руку игрока
                        playerCards.draw(playingDeck);
                        playerCards.draw(playingDeck);

                        // набираю 2 карты в руку дилера
                        dealerCards.draw(playingDeck);
                        dealerCards.draw(playingDeck);

                        while (true) {
                            // вывожу руку игрока, значение руки игрока, руку дилера со скрытой картой и вопрос брать еще карту, или пасовать
                            out.write("Your Hand: " + playerCards.toString() + "\n");
                            out.flush();

                            out.write("Your hand is currently valued at: " + playerCards.cardsValue() + "\n");
                            out.flush();

                            out.write("Dealer Hand: " + dealerCards.getCard(0).toString() + " and [hidden]\n");
                            out.flush();

                            out.write("Would you like to 1 - Hit or 2 - Stand?\n");
                            out.flush();

                            // считываю ответ клиента
                            String clientWord = in.readLine();
                            System.out.println(clientWord);

                            if (clientWord.equals("1") && clientWord != null) {
                                // набираю еще одну карту в руку
                                playerCards.draw(playingDeck);
                                out.write("You draw a:" + playerCards.getCard(playerCards.deckSize() - 1).toString() + "\n");
                                out.flush();

                                // в случае, если набралось больше 21, игрок проигрывает
                                if (playerCards.cardsValue() > 21) {
                                    out.write("YOU LOST. Currently valued at: " + playerCards.cardsValue() + "\n");
                                    out.flush();
                                    endGame = true;
                                    break;
                                }
                            }

                            // останавливаем цикл набора карт, если игрок выбрал пас
                            if (clientWord.equals("2") && clientWord != null) {
                                break;
                            }
                        }

                        // выводим карты дилера
                        out.write("Dealer Cards:" + dealerCards.toString() + "\n");
                        out.flush();

                        // если рука дилера больше руки игрока, то дилер побеждает
                        if((dealerCards.cardsValue() > playerCards.cardsValue())&& endGame == false){
                            out.write("Dealer beats you " + dealerCards.cardsValue() + " to " + playerCards.cardsValue() + "\n");
                            out.flush();
                            endGame = true;
                        }

                        // если у дилера сумма карт меньше 17, то он добирает еще одну карту
                        while((dealerCards.cardsValue() < 17) && endGame == false){
                            dealerCards.draw(playingDeck);
                            out.write("Dealer draws: " + dealerCards.getCard(dealerCards.deckSize()-1).toString() + "\n");
                            out.flush();
                        }

                        out.write("Dealers hand value: " + dealerCards.cardsValue() + "\n");
                        out.flush();

                        // если рука дилера больше 21, он проигрывает и выигрывает клиент
                        if((dealerCards.cardsValue()>21)&& endGame == false){
                            out.write("Dealer LOST. YOU WIN!\n");
                            out.flush();
                            endGame = true;
                        }

                        // если рука дилера равна руке игрока, то ничья
                        if((dealerCards.cardsValue() == playerCards.cardsValue()) && endGame == false){
                            out.write("Push.\n");
                            out.flush();
                            endGame = true;
                        }

                        // если рука игрока, больше руки дилера, то игрок побеждает
                        if((playerCards.cardsValue() > dealerCards.cardsValue()) && endGame == false){
                            out.write("You WIN the hand.\n");
                            out.flush();
                            endGame = true;
                        } else if(endGame == false) {
                            out.write("Dealer wins.\n");
                            out.flush();
                            endGame = true;
                        }

                        // после окончания игры помещаем все карты из рук дилера и игрока обратно в колоду
                        // (было расчитано на игру с денежными ставками и несколькими раундами)
                        playerCards.moveAllToDeck(playingDeck);
                        dealerCards.moveAllToDeck(playingDeck);
                        out.write("End of Hand.\n");
                        out.flush();
                    }
                } finally {
                    // закрываем сокет клиента и InputStream, OutputStream
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                // закрываем сервер
                System.out.println("Server is closed!");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static boolean isEndGame() {
        return endGame;
    }

}
