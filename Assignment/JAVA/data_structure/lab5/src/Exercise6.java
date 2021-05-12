/*CSCI2110-Lab5-Exercise6
the program is to write a recursive method to take one non-negative integer
and displays the sum of squares of all integers up to n
<Xinyu,Liu><B00783546><10.16>
*/
import java.util.Scanner;
public class Exercise6 {
    // takes one non-negative integer
    //and displays the sum of squares of all integers up to n.
    public static int squares(int n) {
      int result=0;
      if(n==1){
        result=1;}
      else {
          result = n * n + squares(n - 1);
      }
      return result;

    }

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter a positive n ");
        int n = kb.nextInt();
        System.out.print(squares(n)+" ");
    }
}
