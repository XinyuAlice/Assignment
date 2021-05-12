/*CSCI2110-Lab4-Student
the program is to create a student object
<Xinyu,Liu><B00783546><10.11>
 */
public class Student {
    //attributes
    private int StudentID;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Major;
    private String faculty;
    //constructor
    public Student(int id,String fn,String ln,String email,String m,String f){
        StudentID=id;
        FirstName=fn;
        LastName=ln;
        Email=email;
        Major=m;
        faculty=f;
    }
    //get method
    public int getStudentID(){ return StudentID; }
    public String getFirstName(){
        return FirstName;
    }
    public String getLastName(){
        return LastName;
    }
    public String getEmail(){
        return Email;
    }
    public String getMajor(){
        return Major;
    }
    public String getFaculty(){ return faculty; }
    //toString method
    public String toString(){
        return StudentID+"\t"+FirstName+"\t"+LastName+"\t"+Email+"\t"+Major+"\t"+faculty;
    }
}
