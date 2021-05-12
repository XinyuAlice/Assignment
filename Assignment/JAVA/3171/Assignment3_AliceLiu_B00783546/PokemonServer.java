import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class PokemonServer{
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
        String[] words = {"RAYQUAZA", "PIKACHU", "MEWTWO", "GENGAR", "CHARMANDER", "SNORLAX","DITTO","RAICHU","LGGLYBUFF","BUTTERFEE","ALBATINE"
                ,"KANGACOON" , "MOSQAZA" , "HORROMADILLO" , "ELECTRIFLY" ,"STUNYBARA" ,"BELLITAR" ,"SCORPINE" ,"KARTANA","SCORPIMISH" };
        int number = new Random().nextInt(21);
        String pokemon=words[number];
        int position=0,i;
        String time = "";
        ArrayList<String> hint = new ArrayList<>();
        if((inputLine = input.readLine()).equals("Play Pokemon game.") ) {
            System.out.println("Message from client: " + inputLine);
            //get the length of pokemon
            for (i = 0; i < pokemon.length(); i++) {
                time += "- ";
                hint.add("- ");
            }
            output.println(time);
            while ((inputLine = input.readLine()) != null) {
                //if contain, then check its position
                if (pokemon.contains(inputLine)) {
                    for (i = 0; i < pokemon.length(); i++) {
                        if (inputLine.equals(String.valueOf(pokemon.charAt(i)))) {

                            position = i;
                            hint.set(position, inputLine);
                            break;
                        }

                    }
                }

                    if (hint.toString().equals(pokemon)) {
                        break;
                    }

                System.out.println("Message from client: " + inputLine);
                output.println(hint.toString());
                if (inputLine.equals("Quit")) {//exit the program
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
