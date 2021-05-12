//QuickSort Algorithm
//Recursively sorts an ArrayList of positive integers in ascending order
//The first item in each sub-array is selected as the pivot
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
public class Exercise2
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
    {
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
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        ArrayList<Integer> list2 = new ArrayList<Integer>();
        ArrayList<Integer> list3= new ArrayList<Integer>();
        ArrayList<Integer> list4 = new ArrayList<Integer>();
        Scanner kb = new Scanner(System.in);
        long startTime, endTime, executionTime;
        startTime = System.currentTimeMillis();

        System.out.println("Enter the number of keys:");
        int key=kb.nextInt();
        Random random=new Random();
        for( int i=0;i<key;i++) {
            i = random.nextInt(key);
            if(i<5000&&i>1) {//A
                list1.add(i);
            }
            else if(i<10000&&i>5001){//B
                list2.add(i);
            }
            else if(i>10001&&i<15000){//C
                list3.add(i);
            }
            else if (i>15001&&i<20000){//D
                list4.add(i);
            }
        }
        //System.out.println(quickSort(list));
        quickSort(list1);
        quickSort(list2);
        quickSort(list3);
        quickSort(list4);
        endTime = System.currentTimeMillis();
        executionTime = endTime-startTime;
        System.out.println("Sorting time: "+executionTime);
    }
}

