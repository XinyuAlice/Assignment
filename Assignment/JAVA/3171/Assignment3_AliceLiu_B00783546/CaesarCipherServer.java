import java.net.*;
import java.io.*;


public class CaesarCipherServer{
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
        int key = (int)(Math.random() * 25 + 1);
        //keep reading until Bye
        char c;
        if((inputLine = input.readLine()).equals("Please send the key") ) {
            System.out.println("Message from client: " + inputLine);
            output.println("The key is " + key);
            while ((inputLine = input.readLine()) !=null) {
                String out = "";
                for (int i = 0; i < inputLine.length(); i++) {
                    //read the character
                    c=inputLine.charAt(i);
                    //transfer
                    out += Character.toString((char) (((c - 'a' + key) % 26) + 'a'));

                }
                System.out.println("Message from client: " + out);
                System.out.println("Decrypted: " + inputLine);
                output.println(inputLine);
                if (inputLine.equals("Bye")) {
                    System.out.println("Closing connection");
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
