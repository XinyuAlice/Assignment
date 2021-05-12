/*CSCI2110-Lab5-Exercise7
the program is to write a recursive method to display the number
of moves for a given number of discs.
<Xinyu,Liu><B00783546><10.16>
*/

public class Exercise7 {
   private static int count=0;
   // solving the Tower of Hanoi problem given n, the number of discs.
    public static void movesDiscs(int n,int from,int to,int temp){

        if(n>0){

            movesDiscs(n-1,from,temp,to);
            //System.out.println("Move disc"+n+"from peg"+from+"to peg"+to);
            movesDiscs(n-1,temp,to,from);
            count++;
        }

    }
    public static void main(String[] args) {
        long startTime, endTime, executionTime;
        startTime = System.currentTimeMillis();
        movesDiscs(28,1,3,2);
        endTime = System.currentTimeMillis();
        executionTime = endTime-startTime;

        System.out.println("number of moves"+count);
        System.out.println("Execution time "+executionTime);
    }
}
