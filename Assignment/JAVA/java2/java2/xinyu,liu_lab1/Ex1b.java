/*CSCI1101-Lab1-exercise1b
the program is to use a method called isPerfect to determine the factors of the number in order to determine whether the number is a perfect number
<Xinyu,Liu><B00783546><1.16>*/
import java.util.Scanner;
public class Ex1b{
	public static void main(String[] args){
   Scanner kb=new Scanner(System.in);
   int n;
   System.out.print("Enter a number:");//input a number
   n=kb.nextInt();
   //boolean isPerfect;
   if(isPerfect(n)==true){//boolean true 
   System.out.print(n+" is a perfect number");
   }
   if(isPerfect(n)==false){//boolean false
   System.out.print(n+" is not a perfect number");		
	}
   //isPerfect();
   }//end main

	public static boolean isPerfect(int n){
    int sum=0;//initialize sum
    
	 for(int i=1;i<=n/2;i++){//initialize i,test i,update i
     if(n%i==0){//check ifi is the factor of n
     sum+=i;
     }
    }
    if(sum==n){//check if n is the sum of its factor
     return true;
	 }
   
     return false;
	 
   }//end isPerfect  	
 }//end 
 
