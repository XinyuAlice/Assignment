/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xinyu_liu_b00783546;

import java.sql.*;

/**
 *
 * @author csali
 */
public class Student {
    private MysqlDatabaseConnection connection=null;
    
    Student(MysqlDatabaseConnection conn){
        this.connection=conn;
    }
    
    ResultSet getAllStudnets(){
        return connection.executeQuery("SELECT * FROM Student");
    }
    
    ResultSet getStudnetBySsn(String Ssn){
        return connection.executeQuery("SELECT * FROM Student Where Ssn="+Ssn);
    }
    
    ResultSet getStudentGrade(String Ssn){
        return connection.executeQuery( "SELECT  Grade.Ssn, Grade.Grade, Grade.Cnum\n" +
                                        "FROM Course\n" +
                                        "INNER JOIN Grade\n" +
                                        "ON Course.Cnum=Grade.Cnum\n");
    }
           
    int UpdateStudent(
            String Ssn, 
            String updatedBnum,
            String updatedFName, 
            String updatedLName, 
            String updatedMajor_code, 
            String updatedMinor_code){
        return connection.executeUpdate("UPDATE Student SET Lname='"+updatedLName+"',Fname='"+updatedFName+"',  Major_code='"+updatedMajor_code+"', Minor_code='"+updatedMinor_code+"' WHERE Ssn="+Ssn);
    }
}


