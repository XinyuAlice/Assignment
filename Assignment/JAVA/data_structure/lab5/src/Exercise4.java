/*CSCI2110-Lab5-Exercise4
the program is to write a recursive method to display the first m multiples of a positive integer n
<Xinyu,Liu><B00783546><10.16>
*/
import java.util.Scanner;
public class Exercise4 {
    public static int multiply(int n, int m) {
        if (m == 1) return n;
        else
            System.out.print(m * n+" ");
        return multiply(n, m - 1);
    }

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter a positive n ");
        int n = kb.nextInt();
        System.out.println("Enter a m");
        int m=kb.nextInt();
        System.out.print(multiply(n,m));
    }
}
