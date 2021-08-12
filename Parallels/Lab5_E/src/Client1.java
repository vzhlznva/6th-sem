import java.io.*;
import java.net.*;

public class Client1 {

    // необхідні змінні для роботи клієнту 
    private static Socket client1;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args){
        try{
            try{
                // спроба підключення клієнта до сервера 
                client1 = new Socket("localhost", 7000);
                // створюю необхідні Input/Output Streams для передачі інформації між клієнтом та сервером 
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(client1.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(client1.getOutputStream()));

                // зчитую інформацію з сервера 
                String serverWord = in.readLine(); // welcome to the chat
                System.out.println(serverWord);

                serverWord = in.readLine(); // name age
                System.out.println(serverWord);

                // зчитую інформацію на з консолі для передачі на сервер 
                String clientWord = reader.readLine();

                // передаю інформацію на сервер 
                out.write(clientWord + "\n");
                out.flush();

                serverWord = in.readLine(); // name age of cli 2
                System.out.println(serverWord);

            } finally {
                // закриваю клієнт та стріми 
                System.out.println("Client 1 was closed");
                client1.close();
                in.close();
                out.close();
                reader.close();
            }
        } catch (IOException e){
            System.err.println(e);
        }
    }
}
