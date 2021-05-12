/*CSCI2110-Assignment4-Demo
the program is to test process
<Xinyu,Liu><B00783546><11.21>
*/

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class Demo {
    public static void main(String args[]) throws IOException {
        ArrayList<Process> list1 = new ArrayList<Process>();
        Heap heap = new Heap();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the filename:");
        String filename = keyboard.nextLine();
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);
        int id;
        int timeReqd;
        int priority;
        int timeArrival;
        //read the file
        while (inputFile.hasNext()) {
            id = inputFile.nextInt();
            timeReqd = inputFile.nextInt();
            priority = inputFile.nextInt();
            timeArrival = inputFile.nextInt();
            Process p = new Process(id, timeReqd, priority, timeArrival);
            list1.add(p);
        }
        inputFile.close();
        Process newP;
        int time = 1;//mean the time unit
        while (!heap.isEmpty() || !list1.isEmpty()) {
            System.out.println("Time Unit:" + time);//time unit
            while (!list1.isEmpty() && list1.get(0).getTimeArrival() == time) {
                heap.add(list1.remove(0));
            }
            //before process
            System.out.println("Heap before process:  " + heap.toString());
            System.out.println("CPU:  ");
            newP = heap.deleteMax();
            //during the process
            System.out.println("Heap during process: " + heap.toString());
            System.out.println("CPU: " + newP);
            //if the remaining require time >1, return back to the heao
            if (newP.getTimeReqd() > 1) {
                newP.setTimeReqd(newP.getTimeReqd() - 1);
                heap.add(newP);
            }
            //after the process
            System.out.println("Heap after process:" + heap.toString());
            System.out.println("CPU: ");
            time++;
        }
    }
}
