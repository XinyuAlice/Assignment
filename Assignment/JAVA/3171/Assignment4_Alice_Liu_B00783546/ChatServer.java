import java.io.*;
import java.net.*;
import java.util.Hashtable;


public class ChatServer
{
    //Hash table for Client Names and corresponding PrintWriter objects
    //Hash table for Client IDs and corresponding message received
    private static Hashtable<String, PrintWriter> writers = new Hashtable<String, PrintWriter>();
    private static Hashtable<Integer, String> clientNames = new Hashtable<Integer, String>();
    //declare the ServerSocket variable and port number for the server
    private static ServerSocket serverSock;
    private static final int PORT = 1234;
    //Create ServerSocket object and listens to inputs from multiple clients
    public static void main(String[] args) throws IOException
    {

        try{
            serverSock=new ServerSocket(PORT);
        }
        //In case the connection was unsuccessful, return error message
        catch (IOException e)
        {
            System.out.println("Can't listen on " + PORT);
            System.exit(1);
        }
        do
        {

            Socket client = null;
            System.out.println("Listening for connection...");
            try{
                /* If the client sends requests, accept the connection */
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
            //Return success message
            System.out.println("Connection successful");
            System.out.println("Listening for input ...");
        }while(true);
    }//End class main

    private static class ClientHandler extends Thread
    {
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private static final int numClients = 4;
        //private String[] clientNames = new String[numClients];
        //Constructor class of creating new client info
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

        //Run the client messages
        public void run()
        {
            try
            {
                String received;
                int message = 1;
                do {
                    int index = 0;
                    received = in.readLine();
                    if(message == 1) {
                        String clientName = getName().substring(getName().length() - 1);
                        int clientNum = Integer.parseInt(clientName);
                        //add client ID and message received to the clientnames hash table
                        clientNames.put(clientNum, received);
                        System.out.println(clientNames.get(clientNum) + " has joined");
                        //add client name and corresponding PrintWriter to the writers hash table
                        writers.put(clientNames.get(clientNum), out);
                        for(PrintWriter writer : writers.values()){
                            writer.println(clientNames.get(clientNum) + " has joined");
                        }
                        message++;
                    }
                    else{
                        String clientName = getName().substring(getName().length() - 1);
                        int clientNum = Integer.parseInt(clientName);
                        //Loop through the writers keySet, that is, (for String client:writers.keySet())
                        //broadcast to all clients except this one
                        for(PrintWriter writer : writers.values()){
                            if(!writer.equals(writers.get(clientNames.get(clientNum)))){
                                writer.println("Message from " + clientNames.get(clientNum) + ": " + received);
                            }
                        }
                        System.out.println("Message from " + clientNames.get(clientNum) + ": " + received);
                    }
                }while (!received.equals("BYE"));
            }
            //Unsuccessful message receiving
            catch(IOException e)
            {
                e.printStackTrace();
            }
            //Close the connection
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
}
