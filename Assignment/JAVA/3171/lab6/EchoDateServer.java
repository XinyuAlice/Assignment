import java.net.*;
import java.io.*;
import java.util.Date;
public class EchoDateServer{
    public static void main(String[] args) throws IOException{
        //create a ServerSocket Object
        ServerSocket serverSock = null;
        try{
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
        }
        catch (IOException ie) {
            System.out.println("Accept failed");
            System.exit(1);
        }
        System.out.println("Connection successful");
        System.out.println("Listening for input...");
        //get reference to strem associated with the socket
        PrintWriter output = new PrintWriter(link.getOutputStream(),true);
        BufferedReader input = new BufferedReader(new InputStreamReader(link.getInputStream()));
        String inputLine;
        //keep reading until Bye
        while ((inputLine = input.readLine()) !=null) {
            if (inputLine.equals("What time is it?")) {
                output.println(new Date().toString());
            }
                System.out.println("Message from client: " + inputLine);
                output.println(inputLine);

            if (inputLine.equals("Bye")) {
                break;
            }
        }
        //close all the connections
        output.close();
        input.close();
        link.close();
        serverSock.close();
    }
}