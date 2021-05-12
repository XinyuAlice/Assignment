/*CSCI2110-Lab4-Expense
the program is to create an expense object
<Xinyu,Liu><B00783546><10.11>
*/
//Expense class
//Models an Expense object
//The data in an Expense object consists of amount, description and date
//Has an equals method that overrides the Object class equals method
import java.text.DecimalFormat;
public class Expense
{   //attributes
    private double amount;
    private String item;
    private String date;
    private static final DecimalFormat dollar = new DecimalFormat("0.00");
    //constructor
    public Expense(String date, String item, double amount)
    {
        this.date = date;
        this.item = item;
        this.amount = amount;
    }
    //toString method
    public String toString()
    {
        return date + "\t" + item + "\t\t" + dollar.format(amount) + "\n";
    }
    //equal method
    public boolean equals(Expense other)
    {
        return (item.equals(other.getItem())&&date.equals(other.getDate())&&amount==other.getAmount());

    }
    //get method
    public String getItem()
    {
        return item;
    }
    public String getDate()
    {
        return date;
    }
    public double getAmount()
    {
        return amount;
    }


}