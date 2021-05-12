/*CSCI2110-Lab8-Exercise2-HashMpDemo
the program is to write a program that reads a file containing the full name,
username, and password for each user.
<Xinyu,Liu><B00783546><11.19>
*/

import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class HashMapDemo {
    public static void main(String[] args) throws IOException {   //Create a hashmap with the username as key and the password as value.
        HashMap<String, String> passRecord = new HashMap<String, String>();
        // Create another hashmap with the username as key and full name as value
        HashMap<String, String> nameRecord = new HashMap<String, String>();
        String p;//password
        String uname;//user name
        String fname;//full name
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the filename to read from: ");
        String filename = keyboard.nextLine();
        //read the file
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);

        while (inputFile.hasNext()) {
            fname = inputFile.next();
            fname += inputFile.next();
            uname = inputFile.next();
            p = inputFile.next();

            passRecord.put(uname, p);
            nameRecord.put(uname, fname);
        }

        inputFile.close();
        //System.out.println(studentRecord.values());
        //System.out.println(studentRecord.keySet());
        // prompt the user to enter the login name and password
        System.out.print("Login: ");
        String login = keyboard.nextLine();//user name
        System.out.print("Password: ");
        String passw = keyboard.nextLine();//password
        //Use the first hashmap to check the username and password match, and
        //use the second hashmap to print the welcome message.
        //If the login is
        //successful, print a welcome message.
        if (passRecord.containsKey(login)) {
            if (passRecord.get(login).equals(passw)) {//compare the entering password with saved password
                System.out.println("Login successful");
                System.out.println("Welcome " + nameRecord.get(login));
            } else {
                System.out.println("Either the username or password is incorrect. You have 2 more attempts.");
                System.out.print("Login: ");
                login = keyboard.nextLine();
                System.out.print("Password: ");
                passw = keyboard.nextLine();
                if (passRecord.containsKey(login)) {
                    if (passRecord.get(login).equals(passw)) {//compare the entering password with saved password
                        System.out.println("Login successful");
                        System.out.println("Welcome " + nameRecord.get(login));
                    }
                    //If the password is incorrect, give
                    //the user two more chances.
                    // If the password is incorrect all three times, the program quits.
                    else {
                        System.out.println("Either the username or password is incorrect. You have 1 more attempt.");
                        System.out.print("Login: ");
                        login = keyboard.nextLine();
                        System.out.print("Password: ");
                        passw = keyboard.nextLine();
                        if (passRecord.containsKey(login)) {
                            if (passRecord.get(login).equals(passw)) {//compare the entering password with saved password
                                System.out.println("Login successful");
                                System.out.println("Welcome " + nameRecord.get(login));
                            } else {
                                System.out.println("Sorry. Incorrect login. Please contact the system administrator.");
                            }

                        }
                    }
                }

            }
        }
    }
}

