/*CSCI2110-Assignment2-Exercise2-Demo
the program is to read a list of names from a file and constructs an ordered list.
<Xinyu,Liu><B00783546><10.23>
*/

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MergeLists {
    public static void main(String[] args) throws IOException {
        //two orderedlist
        OrderedList<String> list1 = new OrderedList<String>();
        OrderedList<String> list2 = new OrderedList<String>();
        OrderedList<String> list3 = null;
        File file1 = new File("input1.txt");
        Scanner input1File = new Scanner(file1);
        while (input1File.hasNext()) {
            String word = input1File.nextLine();
            list1.insert(word);

        }

        input1File.close();
        File file2 = new File("input2.txt");
        Scanner input2File = new Scanner(file2);
        while (input2File.hasNext()) {
            String word2 = input2File.nextLine();
            list2.insert(word2);
        }

        input2File.close();
        list3 = merge(list1, list2);
        list3.enumerate();

    }

    // takes as input two ordered lists and returns a third list that is a
    //merger of the two ordered lists
    public static <T extends Comparable<T>> OrderedList<T> merge(OrderedList<T> list1, OrderedList<T> list2) {
        int f1 = 0, f2 = 0;
        OrderedList<T> list3 = new OrderedList<T>();
        while (f1 < list1.size() && f2 < list2.size()) {
            if (list1.binarySearch(list1.get(f1)) < list2.binarySearch(list2.get(f2))) {
                list3.add(list1.get(f1));
                f1++;
            } else if (list1.binarySearch(list1.get(f1)) > list2.binarySearch(list2.get(f2))) {
                list3.add(list2.get(f2));
                f2++;
            } else {
                list3.add(list1.get(f1));
                f1++;
                f2++;
            }
        }
        if (f1 == list1.size()) {

            list3.add(list2.get(f2));
            f2++;
        }
        if (f2 == list2.size()) {

            list3.add(list1.get(f1));
            f1++;
        }
        return list3;
    }
}
