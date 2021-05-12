/*CSCI2110-Lab4-StudentList
the program is to create an unordered student linkedlist
<Xinyu,Liu><B00783546><10.11>
 */
public class StudentList {
    //attribute
    private List<Student> Stu;
    //constructor
    public StudentList() {
        Stu = new List<Student>();
    }

    //Add a student record to the list
    public void addRecord(Student s) {
        Stu.add(s);

    }

    //Delete a student record with the specified ID number:
    public void deleteRecord(int ID) {
        Student s = Stu.first();
        while (s != null) {
            if (s.getStudentID() == ID) {
                Stu.remove(s);
            }
           s=Stu.next();
        }
    }
    //Display records of all the students taking a specifiied major
    public void displayMajors(String Major){
        Student s=Stu.first();
        while(s!=null){
            if(s.getMajor().equals(Major)){
                System.out.println(s.toString());
            }
            s=Stu.next();
        }
    }
    //Display records of all students belonging to a particular faculty:
    public void displayFaculty(String faculty){
        Student s=Stu.first();
        while(s!=null){
            if(s.getFaculty().equals(faculty)){
                System.out.println(s.toString());
            }
            s=Stu.next();
        }
    }
    //Display records of all students with the specified last name
    public void displayName(String lName){
        Student s=Stu.first();
        while(s!=null){
            if(s.getLastName().equals(lName)){
                System.out.println(s.toString());
            }
            s=Stu.next();
        }
    }
    //Search for a student’s record given an ID number
    public void searchID(int ID) {
        boolean find=false;
        //Student result=null;
        Student s = Stu.first();
        while (s != null) {
            if (s.getStudentID() == (ID)) {
               System.out.println(s.toString());
               find=true;

            }


            s =Stu.next();
        }
        if(find==false){
            System.out.println(ID+"not found");
        }

    }

}
