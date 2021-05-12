import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class GuessTheNumberServer {
    public static void main(String[] args) throws IOException {
        //create a ServerSocket Object
        ServerSocket serverSock = null;
        try {
            //instantiate the server object on a port
            serverSock = new ServerSocket(50000);
        }
        //in case the connection is unsuccessful
        catch (IOException ie) {
            System.out.println("Can't listen on 50000");
            System.exit(1);
        }
        //put the server into a waiting state
        Socket link = null;
        System.out.println("Listening for connection ...");
        try {
            link = serverSock.accept();
        } catch (IOException ie) {
            System.out.println("Accept failed");
            System.exit(1);
        }
        System.out.println("Connection successful");
        System.out.println("Listening for input...");
        //get reference to strem associated with the socket
        PrintWriter output = new PrintWriter(link.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(link.getInputStream()));
        String inputLine;
        //keep reading until Bye
        if((inputLine = input.readLine()).equals("Play?") ){
            System.out.println("Message from client: " + inputLine);
            output.println("I have number between 0 and 100, can you guess it?");

            int number = (int) (Math.random() * (101));

            System.out.println("Message from client: " + inputLine);
            while ((inputLine = input.readLine()) != null) {
                System.out.println("Message from client: " + inputLine);

                if (Integer.parseInt(inputLine) < number) {
                    output.println("Guess lower");
                } else if (Integer.parseInt(inputLine) > number) {
                    output.println("Guess higher");
                } else {
                    output.println("You got it");
                    break;
                }
            }
        }
            //close all the connections
            output.close();
            input.close();
            link.close();
            serverSock.close();

    }
}
