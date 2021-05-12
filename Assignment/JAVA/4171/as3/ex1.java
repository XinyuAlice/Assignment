/*CSCI4171-Assignment3-Excersice 1
 the program is to implement CRC
<Xinyu,Liu><B00783546><11.7>
*/
import java.io.IOException;
import java.util.Scanner;


public class ex1 {
    public static void main(String args[]) throws IOException {
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter the Message M(x):");
        String Mx=kb.nextLine();

        System.out.println("Enter the value G(x):");
        String Gx=kb.nextLine();

        CRC(Mx,Gx);
        System.out.println("Enter CRC bits:");
       String crc=kb.nextLine();
       receive(crc,Gx);
    }




// function to convert binary string to decimal
    public static long  toDec(String str){
        long  num = 0;
        for (int i=0; i<str.length(); i++){
            if (str.charAt(i)=='1')
                num += 1 << (str.length() - i - 1);
        }
        return num;
    }
   public static String CRC(String mx, String gx){
        int l_gen = gx.length();
        long gen = toDec(gx);

        long dword = toDec(mx);

        // append 0s to dividend
        long dividend = dword << (l_gen-1);

        // shft specifies the no. of least
        // significant bits not being XORed
        long shft = (int) Math.ceil(Math.log(dividend+1)/Math.log(2)) - l_gen;
        long  rem;

        while ((dividend >= gen) || (shft >= 0)){

            // bitwise XOR the MSBs of dividend with Gx
            // replace the operated MSBs from the dividend with
            // remainder generated
            rem = (dividend >> shft) ^ gen;
            dividend = (dividend & ((1 << shft) - 1)) | (rem << shft);

            // change shft variable
            shft = (int) Math.ceil(Math.log(dividend+1)/Math.log(2)) - l_gen;
        }

        // finally, AND the initial dividend with the remainder (=dividend)
        long codeword = (dword << (l_gen - 1)) | dividend;
       // System.out.println("Remainder: "+Long.toBinaryString(dividend));
        System.out.println("Codeword: "+Long.toBinaryString(codeword));
        String remain=Long.toBinaryString(dividend);

        return remain;

    }
//Method 2 (Receiver): Given a bit string with CRC remainder appended, this method should
//divide the bit string by G(x) and determine if the message is error-free or not.
    static void receive(String data, String divisor) {
String remain=CRC(data, divisor);
        System.out.println(remain);

            if(remain.equals("0")) {
                // If remainder is not zero then there is an error
                System.out.println("Data transmitted has no error");
            }else {
                System.out.println("There is an error in received data...");
            }
        }

    }





