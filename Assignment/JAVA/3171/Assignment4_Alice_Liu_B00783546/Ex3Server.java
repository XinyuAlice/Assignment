import java.io.*;
import java.net.*;
import java.util.Hashtable;

public class Ex3Server
{
    //Hash table for Client Names and corresponding PrintWriter objects
    //Hash table for Client IDs and corresponding message received
    private static Hashtable<String, PrintWriter> writers = new Hashtable<String, PrintWriter>();
    private static Hashtable<Integer, String> clientNames = new Hashtable<Integer, String>();
    //hash table for store the communicate pair
    private static Hashtable<String,String>communicate=new Hashtable<>();

    //declare the ServerSocket variable and port number for the server
    private static ServerSocket serverSock;
    private static final int PORT = 2345;
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
                String clientName;
                int clientNum=0;
                do {

                    received = in.readLine();
                    //first send message
                    if(message == 1) {
                        clientName = getName().substring(getName().length() - 1);
                        clientNum = Integer.parseInt(clientName);
                        //add client ID and message received to the clientnames hash table
                        clientNames.put(clientNum, received);
                        System.out.println(clientNames.get(clientNum) + " has joined");
                        //add client name and corresponding PrintWriter to the writers hash table
                        writers.put(clientNames.get(clientNum), out);
                        for(PrintWriter writer : writers.values()){
                            writer.println(clientNames.get(clientNum) + " has joined");
                        }
                        //loop through writer keyset
                        for (String user : writers.keySet()) {
                            if(user.equals(clientNames.get(clientNum))){
                                PrintWriter writer = writers.get(user);
                                writer.println("Who do you want to communicate with?: ");
                            }
                        }


                        message++;
                    }
                    //second time
                    else if (message == 2) {
                        clientName = getName().substring(getName().length() - 1);
                        clientNum = Integer.parseInt(clientName);
                        //add client name and corresponding PrintWriter to the writers hash table
                        writers.put(clientNames.get(clientNum), out);
                        if (communicate.contains(clientNames.get(clientNum))) {
                            //loop through communicate set
                            for (String client: communicate.keySet()) {

                                if (clientNames.get(clientNum).equals(client)) {

                                    PrintWriter writer = writers.get(communicate.get(client));

                                    writer.println("Message from " + clientNames.get(clientNum) +  ": " + received);

                                }
                            }
                        }
                        else{

                            PrintWriter writer = writers.get(clientNames.get(clientNum));
                            boolean exist = false;
                            for (String client: writers.keySet()) {
                                if (client.equals(received))
                                    exist = true;
                            }
                            //cannot talk to yourself
                            if (clientNames.get(clientNum).equals(received)) {
                                writer.println("Cannot talk to yourself");
                            }
                            //if not exist, no such client
                            if (exist == false) {
                                writer.println("No such client");
                            }
                            //cannot talk to someone has matched
                            if (communicate.contains(received)) {
                                writer.println("This client has already matched someone");
                            }
                            //if exist and communicate has had such client and pair match
                            if(exist == true && !communicate.contains(received) && !received.equals(clientNames.get(clientNum))) {
                                //add to communicate
                                communicate.put(clientNames.get(clientNum), received);
                                communicate.put(received, clientNames.get(clientNum));

                                int key = (int)(Math.random() * 25 + 1);
                                writer.println("Connect with " + received + ", The key is:" + key);
                                writer = writers.get(received);
                                writer.println("Connect with " + clientNames.get(clientNum) + ", The key is:" + key);

                            }
                        }
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