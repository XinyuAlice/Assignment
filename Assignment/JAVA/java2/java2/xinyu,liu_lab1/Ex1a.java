/*CSCI1101-Lab1-exercise1a
the program is to use a for loop to determine the factors of the number in order to determine whether the number is a perfect number
<Xinyu,Liu><B00783546><1.16>*/
import java.util.Scanner;
public class Ex1a{
	public static void main(String[] args){
		Scanner kb=new Scanner(System.in);
      int n;
      System.out.println("Enter a number:");//input a number
      n=kb.nextInt();
      int i;
      int sum=0;
       for(i=1;i<=n/2;i++)//initialize i,test i,upadate i
       {
         if(n%i==0)///check if i is the factor of n
          sum+=i;
       }
      
       if(sum==n){//check if n is the sum of its factor
       System.out.print(n+" is a perfect number");
	    }
      
       if(sum!=n){//check if n is the sum of its factor
       System.out.print(n+" is not a perfect number");
	    }
  }//end method 
}//end class 

