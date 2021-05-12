/*CSCI2110-Lab5-Exercise2
the program is to write a recursive method called countDown(int n) that takes a single int parameter, n>=0, and
displays a countdown
<Xinyu,Liu><B00783546><10.16>
*/
import java.util.Scanner;
public class Exercise2 {
    public static void countDown(int n){
        if(n==0){ System.out.print("BlastOff!");}
        else if(n>0){
            System.out.print(n+" ");
             countDown(n-1);}

    }
    public static void main(String[] args){
        Scanner kb=new Scanner(System.in);
        //prompts the user to enter a positive integer n and counts down to 1 from n.
        System.out.println("Enter a positive n ");
        int n=kb.nextInt();
        countDown(n);
    }
}
