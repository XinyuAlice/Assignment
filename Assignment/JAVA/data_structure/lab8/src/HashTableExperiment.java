/*CSCI2110-Lab8-Exercise1-HashtableDemo
the program is to build and test hash table
<Xinyu,Liu><B00783546><11.19>
*/
//Hash Table Experiment
//This is a simple demo program that
//creates an ArrayList of LinkedList of Integer objects
//It first displays the empty linked lists
//It then hashes some keys and displays the linked lists again
//It uses the generic LinkedList class and the generic Node class

//MODIFY THIS PROGRAM TO DO EXERCISE 1
import java.util.ArrayList;
import java.util.Scanner;
public class HashTableExperiment
{
    public static void main(String[] args)
    {    int c=0;//number of collisions
         int l=0;//length of longest list
        //prompt the user to enter the table size
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the hash table size: ");
        int n = keyboard.nextInt();//n is the size of the table
        //create an Arraylist of size n
        //The ArrayList holds a LinkedList object
        //The LinkedList consists of nodes that hold integer objects
        ArrayList<LinkedList<Integer>> hashtable = new ArrayList<LinkedList<Integer>>();
        for (int i=0; i<n; i++)
        {
            hashtable.add(i, new LinkedList<Integer>());
        }
        System.out.println("Enter the number of the keys:");
        int m=keyboard.nextInt();
        int[] random = Random(1, 10000, m);

        //Add random keys to the appropriate linked list
        //the key will be added to the linked list at index key%n
        for(int j=0;j<random.length;j++){
            int index=random[j]%n;
            hashtable.get(index).add(random[j]);
        }
        //Display the arraylist of linked lists
        // For large number of keys, you don’t need to display the hash table
        System.out.println("Hash Table created:");

        for (int i=0; i<n; i++)
        {
            System.out.print(i + ": ");
            hashtable.get(i).enumerate();
        }
        //loop to calculate collisions and longest linkedlist length
        for(int k=0;k<n;k++){
            if (l < hashtable.get(k).size())
                l = hashtable.get(k).size();
            if (hashtable.get(k).size() > 1)
                c += hashtable.get(k).size() -1;
        }
        System.out.println("Statistics:");
        System.out.println("Table size: "+n);
        System.out.println("Number of keys: "+m);
        double lf=(double)m/n;//load factor
        lf=(double) Math.round(lf * 100) / 100;
        System.out.println("Load factor: "+lf);
        System.out.println("Number of collisions: "+c);
        System.out.println("Longest list: "+l);
    }
    //method to have non-duplicate random keys
    public static int[] Random(int min, int max, int n) {
        int[] result = new int[n];//size n, n is the number of keys
        int count = 0;
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        while (count < n) {

            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < count; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

}


