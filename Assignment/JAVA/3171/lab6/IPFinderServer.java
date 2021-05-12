import java.net.*;
import java.util.Scanner;
import java.io.*;
public class IPFinderServer
{
    public static void main(String[] args) throws Exception
    {
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
                if (inputLine.equals("Find IP")) {
                    output.println("Enter host name (Do not include http or https)");
                    inputLine=input.readLine();
                    InetAddress address = InetAddress.getByName(inputLine);
                    output.println(address);
                }
            else if (inputLine.equals("Bye")) {
                output.println("closing connection");
                break;
            }
            else {
                    System.out.println("Message from client: " + inputLine);
                    output.println(inputLine);
                }

        }
        //close all the connections
        output.close();
        input.close();
        link.close();
        serverSock.close();

    }
}
