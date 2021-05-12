/*CSCI1101-Lab1-exercise1c
the program is to print all perfect numbers from 1 to 10,000 
<Xinyu,Liu><B00783546><1.16>*/
import java.util.Scanner;
public class Ex1c{
	public static void main(String[] args){
   
   int n;
   System.out.println("all perfect numbers from 1 to 10,000:" );
   isPerfect();
   }//end main

	public static void isPerfect(){
    for(int n=1;n<=10000;n++){//initialize n,test n,update n
        int sum=0;//initialize sum
        for(int i=1;i<n/2+1;i++){//initialize i,test i,update i
          if(n%i==0)//if i is the factor of n  
            sum+=i;
        }
        if(sum==n)//check if n is the sum of its factor
        System.out.println(n+""); 
        
    }
   }//end isPerfect
 }
   
    
	 
   	
