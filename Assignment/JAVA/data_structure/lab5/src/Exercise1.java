/*CSCI2110-Lab5-Exercise1
the program is to write three recursive methods and test in the main method
<Xinyu,Liu><B00783546><10.16>
*/
import java.util.Scanner;
public class Exercise1 {
    // 1.a finds factorial of a non-negative integer n.
    public static int factorial(int n){
        if(n==0)return 1;
        else
            return(n*factorial(n-1));

    }
    //1.b finds the nth number in the Fibonacci series.
    public static int fib(int n){
        if (n==0) return 0;
        else if (n==1) return 1;
        else return fib(n-1)+fib(n-2);
    }
    //1.c finds the x to the power n, where
    //x and n are positive integers
    public static int power(int x,int n){
        if(n==0) return 1;
        else return x*power(x,n-1);
    }

    public static void main(String[] args){
        // print the factorials of 1 to 10.
        for(int i=1;i<=10;i++){
            System.out.print(factorial(i)+"  ");}
        System.out.println();
        //print the first 20 numbers in the Fibonacci series
        for (int j=0;j<=20;j++){
        System.out.print(fib(j)+"  ");}
        System.out.println();
        Scanner kb=new Scanner(System.in);
        //prompts the user to enter x
        //and n and prints x to the power of n.
        System.out.println("Enter the x");
        int x=kb.nextInt();
        System.out.println("Enter the n");
        int n=kb.nextInt();
        System.out.println(power(x,n));

    }
}
