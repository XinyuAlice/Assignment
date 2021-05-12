//import java.io pakage
import java.io.*;
//import java.net package
import java.net.*;
public class ChatClient
{
    private static InetAddress host;
    private static final int PORT = 1234;
    private static Socket link;
    private static BufferedReader in;
    private static PrintWriter out;
    private static BufferedReader keyboard;
    private static BufferedReader readname;

    public static void main(String[] args) throws Exception
    {
        try
        {
            //InetAddress to handle both IP addr and domain names
            InetAddress host = InetAddress.getLocalHost();
            //initialize the socket obejct
            link = new Socket(host, PORT);
            //link = new Socket("127.0.0.1",PORT);
            //input obejct gets an input stream from the socket
            in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            //output is an output stream from the socket
            out = new PrintWriter(link.getOutputStream(), true);
            readname = new BufferedReader(new InputStreamReader(System.in));
            keyboard = new BufferedReader(new InputStreamReader(System.in));

            String name, message, response;
            System.out.println("Enter name: ");
            //read name
            name=readname.readLine();
            //print name to output
            out.println(name);
            do
            {

                System.out.print("Enter message (BYE to exit): ");
                message = keyboard.readLine();
                out.println(message);
                response = in.readLine();
                System.out.println(response);
            }while(!message.equals("BYE"));
        }
        //catch exception
        catch(UnknownHostException e)
        {
            System.out.println("Unknown host");
            System.exit(1);
        }
        catch(IOException e)
        {
            System.out.println("Cannot connect to host");
            System.exit(1);
        }
        finally
        {
            try
            {
                if (link!=null)
                {
                    System.out.println("Closing down connection ...");
                    link.close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
