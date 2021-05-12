/*CSCI2110-Lab5-Exercise5
the program is to write a recursive method to takes one non-negative
integer and displays that integer with the digits going down the screen one per line.
<Xinyu,Liu><B00783546><10.16>
*/
import java.util.Scanner;
public class Exercise5 {
    // takes one non-negative integer and
    // displays that integer with the digits going down the screen one per line.
    public static void writeVertical(int n){
       if(n<10) {
           System.out.println(n);
       }
       else {
           writeVertical(n / 10);
           System.out.println(n % 10);
       }
    }
    public static void main(String[]args){
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter a positive n ");
        int n = kb.nextInt();
        writeVertical(n);
    }
}
