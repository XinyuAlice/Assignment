import java.io.*;
import java.net.*;
public class MultiEchoServer
{
    //declare the ServerSocket variable and the port number for the server (constant)
    private static ServerSocket serverSock;
    private static final int PORT = 1234;
    //the main method will create the ServerSocket object and listens to inputs from
    // multiple client
    public static void main(String[] args) throws IOException
    {
        try{
            //instantiate the server object on a port
            serverSock=new ServerSocket(PORT);
        }
        //in case the connection is unsuccessful
        catch (IOException e)
        {
            System.out.println("Can't listen on " + PORT);
            System.exit(1);
        }
        do
        {
            //put the server into a waiting state
            Socket client = null;
            System.out.println("Listening for connection...");
            //if the client send s request , we sccept the connection
            try{
                client = serverSock.accept();
                System.out.println("New client accepted");
                ClientHandler handler = new ClientHandler(client);
                handler.start();
            }
            catch (IOException e)
            {
                System.out.println("Accept failed");
                System.exit(1);
            }
            System.out.println("Connection successful");
            System.out.println("Listening for input ...");
        }while(true);
    }
}

class ClientHandler extends Thread
{
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    public ClientHandler(Socket socket)
    {
        client = socket;
        try
        {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(),true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void run()
    {
        try
        {
            String received;
            do
            {
                received = in.readLine();
                out.println("ECHO: " + received);
                System.out.println(received);

            }while (!received.equals("BYE"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(client!=null)
                {
                    System.out.println("Closing down connection...");
                    client.close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
