
//ExpenseListDemo
//Program reads a text file containing list of expenses
//File contains date, description and amount one on each line:
//Sep 21, 2018
//Lunch
//4.51
//Program then processes the file and displays expense data such as
//max expense amount, min expense amount, etc.

import java.util.Scanner;
import java.io.*;
public class ExpenseListDemo
{
    public static void main(String[] args) throws IOException
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the filename to read from: ");
        String filename = keyboard.nextLine();

        File file = new File(filename);
        Scanner inputFile = new Scanner(file);

        ExpenseList sepexpenses = new ExpenseList();
        String date, desc, cost;
        Expense exp=null;

        while (inputFile.hasNext())
        {
            date = inputFile.nextLine();
            desc = inputFile.nextLine();
            cost = inputFile.nextLine();
            exp = new Expense(date, desc, Double.parseDouble(cost));
            sepexpenses.add(exp);
        }

        inputFile.close();

        System.out.println("September Expenses");

        System.out.println("Max expense: $" + sepexpenses.maxExpense());
        System.out.println("Min expense: $" + sepexpenses.minExpense());
        System.out.println("Average expense: $" + sepexpenses.avgExpense());
        System.out.println("The amount spent on groceries: $" + sepexpenses.amountSpentOn("Groceries"));
        System.out.println("Total expense: $" + sepexpenses.totalExpense());


    }
}