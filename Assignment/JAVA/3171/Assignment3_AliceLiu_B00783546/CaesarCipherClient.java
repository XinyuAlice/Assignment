import java.io.*;
import java.net.*;
public class CaesarCipherClient{
    public static void main(String[] args) throws IOException{
        //initialize a socket object that we can lnk
        Socket link = null;
        PrintWriter output = null;
        BufferedReader input = null;
        //create a link object
        try{
            link= new Socket("127.0.0.1", 50000);
            //output object is an output stream from the socket
            output = new PrintWriter(link.getOutputStream(), true);
            //input object gets an input stream from the socket
            input = new BufferedReader(new InputStreamReader(link.getInputStream()));
        }
        //create exception
        catch(UnknownHostException e)
        {
            System.out.println("Unknown Host");
            System.exit(1);
        }
        catch (IOException ie) {
            System.out.println("Cannot connect to host");
            System.exit(1);
        }
        //receive message sent by the printwriter object at server side
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String usrInput;
        while ((usrInput = stdIn.readLine())!=null) {
            output.println(usrInput);
            System.out.println("Echo from Server: " + input.readLine());
        }
        //close connections
        output.close();
        input.close();
        stdIn.close();
        link.close();
    }
}