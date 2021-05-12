import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Ex3Client {
    private static InetAddress host;
    private static final int port = 2345;
    private static Socket link;
    private static BufferedReader in;
    private static PrintWriter out;
    private static BufferedReader keyboard;
    private String userName;
    private String communicate;
    public static void main(String[] args) throws Exception {
        try {
            InetAddress host = InetAddress.getLocalHost();
            Socket link = new Socket(host, port);

            System.out.println("Connected to the chat server");

            new ReadThread3(link).start();
            new WriteThread3(link).start();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }
}
//use class set key to give key for read thread and write thread
class setkey{
    private static int key = 0;
    public setkey() {
    }
    public int getkey() {
        return key;
    }
    public void setkey(int key) {
        this.key = key;
    }
}

class ReadThread3 extends Thread {
    private BufferedReader reader;
    private Socket socket;
    //give key
    private setkey secret=new setkey();
    //decrypt
    public ReadThread3(Socket socket) {
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
        String key;
        int num=0;
        int message=1;
        while (true) {
            try {
                String response = reader.readLine();

                //decrpt the message
             if(message==1){
                 System.out.println("\n" + response);
                  if (response.contains("key")) {
                     String [] split = response.split(":");
                     //seprate the response
                     key = split[1].toString();
                     num = Integer.parseInt(key);//convert to integer
                      //set key
                     secret.setkey(num);
                     message++;
                  }

                }
                //if BYE, then print BYE
                else if(response.equals("BYE")){
                 System.out.println("\n" + response);
             }
                 else{
                     char chr;//convert str
                     char c; //separate
                     String txt="";//final txt
                 System.out.println(response);
                 String [] split = response.split(":");
                     for (int i = 0; i < split[1].toString().length(); i++) {
                         //separate
                         c=split[1].toString().charAt(i);
                         //for letter from A to Z
                         if (c >= 'A' && c <= 'Z') {
                             if (((int)c- num) < 65)
                                 chr = (char)(((int)c- num -65 + 26) % 26 + 65);
                             else
                                 chr = (char)(((int)c- num -65) % 26 + 65);
                             txt +=chr;
                         }
                         //for letter from a-z
                         else if (c >= 'a' && c <='z') {
                             if (((int)c - num) < 97)
                                 chr= (char)(((int)c - num -97 + 26) % 26 + 97);
                             else
                                 chr = (char)(((int)c - num -97) % 26 + 97);
                             txt  += chr;
                         }

                         //if empty space, then keep
                         else if (c == ' ') {
                             txt += c;
                         }
                     }
                    System.out.println("Decrypted: "+txt);

                }

            } catch (IOException ex) {
                System.out.println("Connection shut down ");
                break;
            }
        }
    }
}
//encrypt
class WriteThread3 extends Thread {
    private PrintWriter writer;
    private Socket socket;
    //give key
    private setkey secret = new setkey();

    public WriteThread3(Socket socket) {
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
            //Scanner kb = new Scanner(System.in);
            String userName = console.readLine("Enter name: ");
            System.out.println("Enter name: ");
           // String userName = kb.nextLine();
            writer.println(userName);

            String text;

            do {
                char chr;//get from readline
                char str;//convert str
                 text = console.readLine();
                //text = kb.nextLine();
                if(text.equals("BYE")){
                    writer.println(text);
                }
                int num = 0;
                //get key
                num = secret.getkey();
                String txt = "";
                if (num != 0) {
                    for (int i = 0; i < text.length(); i++) {

                        chr= text.charAt(i);
                        //for letter from A to Z
                        if (chr >= 'A' && chr <= 'Z') {
                            str = (char)(((int)chr + num - 65) % 26 + 65);
                            txt +=str;
                        }
                        //for letter from a to z
                        else if (chr>='a' && chr<='z') {
                            str = (char)(((int)chr + num - 97) % 26 + 97);
                            txt += str;
                        }

                        //if empty space, keep
                        else if (chr == ' ') {
                            txt +=chr;
                        }
                    }
                        writer.println(txt);
                    }
                    //if num=0;no difference
                else {
                    writer.println(text);

                }

            } while (!text.equals("BYE"));

            socket.close();
        } catch (IOException ex) {

            System.out.println("Connection shut down ");
        }
    }
}

