//QuickSort Algorithm
//Recursively sorts an ArrayList of positive integers in ascending order
//The first item in each sub-array is selected as the pivot
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
public class QuickSort
{
    public static <T extends Comparable<T>> void quickSort(ArrayList<T> list, int from, int to)
    {
        int index = partition(list, from, to);
        if (from<index -1)
            quickSort(list,from, index-1);
        if (index<to)
            quickSort(list,index,to);
    }

    public static <T extends Comparable<T>> int partition(ArrayList<T> list, int left, int right)
    {   Random ran=new Random();
        left=ran.nextInt(20000);
        int i=left, j=right;
        T tmp;
        T pivot=list.get(i); //pivot is the first element of the sub-array
        while(i<=j)
        {
            while(list.get(i).compareTo(pivot)<0)
                i++;
            while(list.get(j).compareTo(pivot)>0)
                j--;
            if (i<=j)
            {
                tmp=list.get(i);
                list.set(i,list.get(j));
                list.set(j,tmp);
                i++;
                j--;
            }
        }
        return i;
    }
    public static <T extends Comparable<T>> ArrayList<T> quickSort(ArrayList<T> list)
    {
        quickSort(list, 0, list.size()-1);
        return list;
    }
    public static void main(String[] args)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        Scanner kb = new Scanner(System.in);
        long startTime, endTime, executionTime;
        startTime = System.currentTimeMillis();

        System.out.println("Enter the number of keys:");
        int key=kb.nextInt();
        Random random=new Random();
       for( int i=0;i<20000;i++) {
           i = random.nextInt(20000);
           list.add(i);
       }
        //System.out.println(quickSort(list));
        quickSort(list);
        endTime = System.currentTimeMillis();
        executionTime = endTime-startTime;
        System.out.println("Sorting time: "+executionTime);

    }

}

