import java.io.IOException;
import java.util.Scanner;

/*
CSCI4174-Assignment6-Excersice 1
 the program is to implement RSA algorithm
<Alice Liu><B00783546><4.10>
 */
public class rsa {
    public static void main(String args[]) throws IOException {
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter the prime numbers, p and q: ");
        int p=kb.nextInt();
        int q=kb.nextInt();
        System.out.println("Calculating RSA values ...");
        System.out.println("Enter the plaintext message m (an integer): ");
        int m=kb.nextInt();
        System.out.println("Encrypting m...");
        System.out.println("Decrypting c ...");
        getRSA(p,q,m);
    }
    public static void getRSA(int p, int q, int m){
        int n=p*q;
        int b=(p-1)*(q-1);
        int e=1;
        for(int i=2;i<n;i++){
            if(recursiveGCD(i,b)==1){
                e=i;
                break;
            }
        }
        int d=modInverse(e,b);
        System.out.println("Public RSA key is (" +e+", "+n+" )");
        System.out.println("Private RSA key is (" +d+", "+n+" )");
        int c=(fastModular(m,e,n));

        System.out.println("The ciphertext c is "+c);

        System.out.println("The plaintext m is "+ fastModular(c,d,n));


    }
    //find greatest common factor
    static int  recursiveGCD(int a, int b) {
        if (b == 0) {
            return a;
        }
        if (a < b) {
            return recursiveGCD(b, a);
        }
        return recursiveGCD(b, a % b);
    }
    //find integer d such that ed=1 mod m
    static int modInverse(int a, int m)
    {

        for (int x = 1; x < m; x++)
            if (((a%m) * (x%m)) % m == 1)
                return x;
        return 1;
    }
    //m=c^d mod n
    //example: 5^13 mod 11
    //split up bigger power into smaller
    static int fastModular(int c,int d, int n) {
        int b=c;
        int a=d;

        int z = 1;
        if(a==0){
            z=1;
        }
        while (a > 0) {
            //if even, divide by 2
            if(a%2==0){
                a=a/2;
                b=(b*b)%n;

            }
            else{
                a=a-1;
                z=(z*b)%n;
            }

        }
        return z;
    }

}
