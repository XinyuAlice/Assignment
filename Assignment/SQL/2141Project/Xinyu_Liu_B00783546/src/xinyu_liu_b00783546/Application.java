/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xinyu_liu_b00783546;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author csali
 */
public class Application {
    private UserInterface UI=null;
    private MysqlDatabaseConnection dbConnection=null;
    private Scanner scanner = null;
    private Boolean isRunning=true;
    
    void start(){
        this.initialize();
        this.run();
    }
    
    void initialize(){
        this.scanner = new Scanner(System.in);
        this.UI = new UserInterface();
        MysqlDatabaseConnection dbConnection = new MysqlDatabaseConnection();
        if(dbConnection.connect())
        {
            this.dbConnection=dbConnection;
        }
        else{
            this.isRunning=false;
            this.UI.printInitializationError();
        }
    }
    
    void run(){
        UI.printWelcomeGreetings();
        while(this.isRunning){
            UI.printMainMenu();
            int mainOption = Integer.parseInt(scanner.nextLine());
            switch(mainOption){
                case 1: viewStudent();
                        break;
                case 2: updateStudent();
                        break;
                case 3: viewStudentGrade();
                        break;
                case 999: this.exit();
                        break;
                default: System.out.println("Incorrect Option selected");
            }
            System.out.flush();
        }
    }
    
    void exit(){
        this.dbConnection.disconnect();
        this.scanner.close();
        this.isRunning=false;
    }
    
    void viewStudent(){
        Boolean backPressed = false;
        while(!backPressed){
            this.UI.printViewStudentMenu();
            int customerOption = Integer.parseInt(scanner.nextLine());
            switch(customerOption){
                case 1: viewAllStudents();
                        break;
                case 2: viewStudentBySsn();
                        break;
                case 999: backPressed=true;
                        break;
                default: System.out.println("Incorrect Option selected");
            }
        }
    }
    
    void viewAllStudents(){
        Student student = new Student(dbConnection);
        ResultSet rs = student.getAllStudnets();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(rsmd.getColumnName(1) + "  " + rsmd.getColumnName(2) + "  " + rsmd.getColumnName(3));
            while(rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    void viewStudentBySsn(){
        Student student = new Student(dbConnection);
        UI.printEnterStudentSsnText();
        String ssn = scanner.nextLine();
        ResultSet rs = student.getStudnetBySsn(ssn);
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(rsmd.getColumnName(1) + "  " + rsmd.getColumnName(2) + "  " + rsmd.getColumnName(3));
            while(rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void updateStudent(){
       Student student = new Student(dbConnection);
        UI.printEnterStudentSsnText();
        String ssn = scanner.nextLine();
        ResultSet rs = student.getStudnetBySsn(ssn);
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(rsmd.getColumnName(1) + "  " + rsmd.getColumnName(2) + "  " + rsmd.getColumnName(3));
            while(rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
             UI.printEnterStudentNewBnum();
            String updatedBnum=scanner.nextLine();
            UI.printEnterStudentNewFname();
            String updatedFname=scanner.nextLine();
            UI.printEnterStudentNewLname();
            String updatedLname=scanner.nextLine();
            UI.printEnterStudentNewMajor_code();
            String updatedMajor_code=scanner.nextLine();
            UI.printEnterStudentNewMinor_code();
            String updatedMinor_code=scanner.nextLine();
            int updated = student.UpdateStudent(ssn,updatedBnum,updatedFname,updatedLname,updatedMajor_code,updatedMinor_code);
            if(updated==1)
                System.out.println("Student Updated!");
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void viewStudentGrade(){
        Student student = new Student(dbConnection);
        UI.printEnterStudentSsnText();
        String ssn = scanner.nextLine();
        ResultSet rs = student.getStudentGrade(ssn);
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(rsmd.getColumnName(1) + "  " + rsmd.getColumnName(2) + "  " + rsmd.getColumnName(3));
            while(rs.next())
                System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  $" + rs.getString(3));
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}


