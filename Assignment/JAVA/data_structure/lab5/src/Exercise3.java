/*CSCI2110-Lab5-Exercise3
the program is to write a recursive method so that if n is even, it displays only even numbers in the countdown,
whereas if n is odd, it displays only odd numbers in the countdown.
<Xinyu,Liu><B00783546><10.16>
*/
import java.util.Scanner;
public class Exercise3 {
    public static void doublecountdown(int n){
        if(n<=0){
            System.out.print("BlastOff!");
        }
        else if(n>0) {
            System.out.print(n+" ");
            doublecountdown(n-2);

            }
        }

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter a positive n ");
        int n = kb.nextInt();
        doublecountdown(n);
    }
}
