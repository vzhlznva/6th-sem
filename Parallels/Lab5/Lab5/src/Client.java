import java.io.*;
import java.net.*;

public class Client {

    // необходимые переменные для работы клиента
    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args){
        try{
            try{
                // попытка подключения киента к серверу
                clientSocket = new Socket("localhost", 3000);
                // необходимые переменные InputStream, OutputStream
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                boolean endGame = Dealer.isEndGame();
                String serverWord;
                serverWord = in.readLine(); //starting
                System.out.println(serverWord);

                // заходим в цикл пока не закончится игра
                while(!endGame){
                    serverWord = in.readLine(); //Dealing
                    System.out.println(serverWord);

                    serverWord = in.readLine(); //Your Hand
                    System.out.println(serverWord);

                    serverWord = in.readLine(); //Your Value
                    System.out.println(serverWord);

                    serverWord = in.readLine(); //Dealer Hand
                    System.out.println(serverWord);

                    serverWord = in.readLine(); //Hit or Stand
                    System.out.println(serverWord);

                    String clientWord = reader.readLine(); //
                    out.write(clientWord + "\n");
                    out.flush();

                    if (clientWord.equals("Hit") && clientWord != null){
                        serverWord = in.readLine();
                        System.out.println(serverWord);
                        serverWord = in.readLine();
                        System.out.println(serverWord);
                        endGame = Dealer.isEndGame();
                    }

                    serverWord = in.readLine();
                    System.out.println(serverWord);

                    serverWord = in.readLine();
                    System.out.println(serverWord);
                    endGame = Dealer.isEndGame();

                    serverWord = in.readLine();
                    System.out.println(serverWord);

                    serverWord = in.readLine();
                    System.out.println(serverWord);
                    endGame = Dealer.isEndGame();

                    serverWord = in.readLine();
                    System.out.println(serverWord);
                }
            } finally {
                // закрываем клиент и стримы ввода/вывода/считывателя
                System.out.println("Client was closed");
                clientSocket.close();
                in.close();
                out.close();
                reader.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
