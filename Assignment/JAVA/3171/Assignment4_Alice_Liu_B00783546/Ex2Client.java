import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Ex2Client {
    private static InetAddress host;
    private static final int port = 3456;
    private static Socket link;
    private static BufferedReader in;
    private static PrintWriter out;
    private static BufferedReader keyboard;
    private String userName;

    public static void main(String[] args) throws Exception {
        try {
            InetAddress host = InetAddress.getLocalHost();
            Socket link = new Socket(host, port);

            System.out.println("Connected to the chat server");

            new ReadThread2(link).start();
            new WriteThread2(link).start();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }
}

class ReadThread2 extends Thread {
    private BufferedReader reader;
    private Socket socket;
    char c;
    int message=1;
    String decrypted="";
    int key;
    public ReadThread2(Socket socket) {
        this.socket = socket;
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                System.out.println("\n" + response);
                //decrpt the message
              /*  if(message==1){
                    key= Integer.parseInt(response);
                    System.out.println("\n" + response);
                }
                 else{
                     for (int i = 0; i < response.length(); i++) {
                        c=response.charAt(i);
                        decrypted += Character.toString((char) (((c + 'a' - key) % 26) -'a'));

                    }
                }
                System.out.println("\n" + decrypted);
            */

            } catch (IOException ex) {
                System.out.println("Connection shut down ");
                break;
            }
        }
    }
}

class WriteThread2 extends Thread {
    private PrintWriter writer;
    private Socket socket;

    public WriteThread2(Socket socket) {
        this.socket = socket;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
           Console console = System.console();
            //Scanner kb=new Scanner(System.in);
            String userName = console.readLine("Enter name: ");
            System.out.println("Enter name: ");
           // String userName=kb.nextLine();
            writer.println(userName);

            String text;

            do {
                text = console.readLine();
               // text=kb.nextLine();
                writer.println(text);

            } while (!text.equals("BYE"));


            socket.close();
        } catch (IOException ex) {

            System.out.println("Connection shut down ");
        }
    }
}
