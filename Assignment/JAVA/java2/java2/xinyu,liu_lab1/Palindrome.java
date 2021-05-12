/*CSCI1101-Lab1-exercise2
the program is to determine a string whether or not the string is palindrome
<Xinyu,Liu><B00783546><1.16>*/

import java.util.Scanner;

public class Palindrome {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        String line;
        System.out.print("Enter a string:");//input a string
        line = kb.next();

        if (isReverse(line) == true) {//boolean true
            System.out.print(line + " is a palindrome");
        }
        if (isReverse(line) == false) {//boolean false
            System.out.print(line + " is not a palindrome");
        }
    }

    /**
     *
     * @param line
     * @return
     */
    public static boolean isReverse(String line1) {
        String line;
        line = line1.replaceAll("[^a-z^A-Z^0-9]","");//Ignore whitespace and punctuation (space, tab, single quote, double quote, exclamation, comma, question mark, hyphen, semi-colon, colon, period)
        int low = 0;
        int high = line.length() - 1;
        while (low < high) {
            if (line.charAt(low) != line.charAt(high)) {//check if line is not a palindrome
                return false;
            }
            low++;
            high--;
        }//end while
        return true;
    }//end inReverse
}//end Palindrome