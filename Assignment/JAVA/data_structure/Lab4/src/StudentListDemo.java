/*CSCI2110-Lab4-StudentListDemo
the program is to create a demo of the studentlist
<Xinyu,Liu><B00783546><10.11>
 */
import java.util.Scanner;
import java.io.*;
import java.util.StringTokenizer;
public class StudentListDemo {
    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the filename to read from: ");//input the filename
        String filename = keyboard.nextLine();

        File file = new File(filename);
        Scanner inputFile = new Scanner(file);

        StudentList StuRecord = new StudentList();
        int id;
        String fn, ln, em, m, f;
        Student s = null;

        while (inputFile.hasNext()) {//when the content of file has next
            id = inputFile.nextInt();
            fn = inputFile.next();
            ln = inputFile.next();
            em = inputFile.next();
            m = inputFile.next();
            f = inputFile.next();
            s = new Student(id, fn, ln, em, m, f);//create a new student object
            StuRecord.addRecord(s);//add to the studentlist
        }

        inputFile.close();
        while (true) {
            System.out.println("What would you like to do? (add, delete, display, search, quit):");
            String action = keyboard.nextLine();
            if (action.equals("add")) {//add item to the list
                System.out.println("Enter the record:");
                String record = keyboard.nextLine();
                StringTokenizer token;
                token = new StringTokenizer(record, " "); //use it to read the whole line input

                    String num = token.nextToken();
                    String lastN = token.nextToken();
                    String firstN = token.nextToken();
                    String email = token.nextToken();
                    String major = token.nextToken();
                    String faculty = token.nextToken();
                    Integer inum = Integer.valueOf(num);
                    Student s2 = new Student(inum, lastN, firstN, email, major, faculty);
                    StuRecord.addRecord(s2);
                    System.out.println("Record added.");


            }

            if(action.equals("delete")){//delete item
                System.out.println("Enter the ID number to be deleted:");
                int idnum=keyboard.nextInt();
                StuRecord.deleteRecord(idnum);
                System.out.println(idnum+"deleted");
            }
            if(action.equals("display")) {//display major
                System.out.println("Display what (major, faculty, student): ");
                String display=keyboard.nextLine();
                if(display.equals("major")){
                    System.out.println("Enter the faculty");
                    String fc=keyboard.nextLine();
                    StuRecord.displayFaculty(fc);
                }
                else if(display.equals("faculty")){//display faculty
                    System.out.println("Enter the faculty");
                     String fculty=keyboard.nextLine();
                     StuRecord.displayFaculty(fculty);
                }
                else if(display.equals("student")){//display student
                    System.out.println("Enter the last Name");
                    String lN=keyboard.nextLine();
                    StuRecord.displayName(lN);
                }
            }
            if(action.equals("search")){//search method
                System.out.println("Enter the ID Number:");
                int ID=keyboard.nextInt();
                StuRecord.searchID(ID);
            }
            if(action.equals("quit")){
                System.out.println("Program ended.");
                break;
            }

        }
    }
}
