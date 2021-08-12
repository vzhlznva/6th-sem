import java.io.*;
import java.net.*;

public class Server {

    // необхідні змінні для роботи серверу 
    private static ServerSocket server;
    private static BufferedReader in1;
    private static BufferedWriter out1;
    private static BufferedReader in2;
    private static BufferedWriter out2;

    public static void main(String[] args){
        try{
            try{
                // прив'язую серверний сокет до порта 
                server = new ServerSocket(7000);
                System.out.println("Server is started!");
                // прослуховую під'єднання першого клієнта до сокета 
                Socket client1 = server.accept();
                System.out.println("Client 1 connected!");
                // прослуховую під'єднання другого клієнта до сокета 
                Socket client2 = server.accept();
                System.out.println("Client 2 connected!");
                try{
                    // створюю необхідні змінні для вводу/виводу інформації на клієнти через InputStream та OutputStream
                    in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
                    out1 = new BufferedWriter(new OutputStreamWriter(client1.getOutputStream()));
                    in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
                    out2 = new BufferedWriter(new OutputStreamWriter(client2.getOutputStream()));

                    out1.write("Welcome to the Chat! \n");
                    out1.flush();

                    out2.write("Welcome to the Chat! \n");
                    out2.flush();

                    out1.write("Please, write your name and age:) \n");
                    out1.flush();

                    out2.write("Please, write your name and age:) \n");
                    out2.flush();

                    // запитую у першого клієнта ім'я та вік 
                    String cliWord1 = in1.readLine();
                    System.out.println("Client 1: " + cliWord1);

                    // запитую у другого клієнта ім'я та вік 
                    String cliWord2 = in2.readLine();
                    System.out.println("Client 2: " + cliWord2);

                    // вивожу другому клієнту ім'я та вік першого, якщо отриманий рядок не пустий 
                    if (cliWord1 != null){
                        out2.write("Your collocutor on client 1 is " + cliWord1 + " \n");
                    } 
                    // якщо отриманий рядок пустий, то вивожу другому клієнту інформацію
                    // що перший клієнт нічого не написав 
                    else {
                        out2.write("Your collocutor on client 1 wrote nothing \n");
                    }
                    out2.flush();

                    // вивожу першому клієнту ім'я та вік другого, якщо отриманий рядок не пустий
                    if (cliWord2 != null){
                        out1.write("Your collocutor on client 2 is " + cliWord2 + " \n");
                    } 
                    // якщо отриманий рядок пустий, то вивожу першому клієнту інформацію
                    // що другий клієнт нічого не написав 
                    else {
                        out1.write("Your collocutor on client 2 wrote nothing \n");
                    }
                    out1.flush();

                } finally {
                    // закриваю клієнт, Input/OutputStreams
                    client1.close();
                    client2.close();
                    in1.close();
                    out1.close();
                    in2.close();
                    out2.close();
                }
            } finally{
                // закриваю сервер 
                System.out.println("Server is closed!");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
