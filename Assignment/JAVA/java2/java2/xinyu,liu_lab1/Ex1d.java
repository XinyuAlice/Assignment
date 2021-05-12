/*CSCI1101-Lab1-exercise1d
the program is to determine the execution time for printing all perfect numbers from 1 to 10,000 
<Xinyu,Liu><B00783546><1.16>*/
import java.util.Scanner;
public class Ex1d{
	public static void main(String[] args){
   
   long startTime, endTime, executionTime;
   startTime = System.currentTimeMillis();
   int n;
   System.out.println("all perfect numbers from 1 to 10,000:" );
   isPerfect();
   endTime = System.currentTimeMillis();
   executionTime = endTime ¨C startTime;
   System.out.print("time for executing the code segment in milliseconds:"+executionTime);
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