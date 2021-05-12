/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xinyu_liu_b00783546;

/**
 *
 * @author csali
 */
public class UserInterface {
     void printWelcomeGreetings(){
        System.out.println("Welcome to Sample Java MYSQL Connection Application!");
    }
    
    void printMainMenu(){
        System.out.println("Select Operation from List:");
        System.out.println("1: View Student(s)");
        System.out.println("2: Update Student");
        System.out.println("3: Grades of Student");
        System.out.println("999: Exit Application");
    }
    
    void printViewStudentMenu(){
        System.out.println("\tSelect Operation from List:");
        System.out.println("\t1: View All Student");
        System.out.println("\t2: View Student by Student Social Insurance Number");
        System.out.println("\t999: Back");
    }
    
    void printEnterStudentSsnText(){
        System.out.print("Enter Student Social Insurance Number: ");
    }
    
    void printEnterStudentNewFname(){
        System.out.print("Enter Updated First Name: ");
    }
    
    void printEnterStudentNewLname(){
        System.out.print("Enter Updated Last Name: ");
    }
   
    
    void printEnterStudentNewBnum(){
        System.out.print("Enter Updated Student Bnumber: ");
    }
    
    void printEnterStudentNewMajor_code(){
        System.out.print("Enter Updated Major_code: ");
    }
        void printEnterStudentNewMinor_code(){
        System.out.print("Enter Updated Minor_code: ");
    }
    
    void printInitializationError(){
        System.out.println("Application Cannot Initialize!");
    }
    

}
