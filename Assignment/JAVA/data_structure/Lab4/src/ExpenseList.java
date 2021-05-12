/*CSCI2110-Lab4-ExpenseList
 the program is to create an unordered list of expense objects
<Xinyu,Liu><B00783546><10.11>
 */
//ExpenseList class
//Defines an unordered list of Expense objects
//Uses the generic unordered list (List<T>) class and Expense class
public class ExpenseList
{   //attributes
    private List<Expense> expenses;

    //constructor
    public ExpenseList()
    {
        expenses = new List<Expense>();
    }
    //add method
    public void add(Expense exp)
    {
        expenses.add(exp);
    }
    //isEmpty
    public boolean isEmpty()
    {
        return expenses.isEmpty();
    }
    //contains method
    public boolean contains(Expense exp)
    {
        return expenses.contains(exp);
    }
    //first method
    public Expense first()
    {
        return expenses.first();
    }
    //next method
    public Expense next()
    {
        return expenses.next();
    }
    //scan the whole list
    public void enumerate()
    {
        expenses.enumerate();
    }
    //max expense method
    public double maxExpense()
    {
        double max =0.0, amt;
        Expense exp = expenses.first();
        while (exp!=null)
        {
            amt = exp.getAmount();
            if (amt>max)
                max = amt;
            exp = expenses.next();
        }
        return max;
    }
    //min expense method
    public double minExpense()
    {
        double min =Double.MAX_VALUE, amt;
        Expense exp = expenses.first();
        if (exp==null) return 0.0;
        else
        {

            while (exp!=null)
            {
                amt = exp.getAmount();
                if (amt<min)
                    min = amt;
                exp = expenses.next();
            }
        }
        return min;
    }
    //Finds the average of all expenses
	public double avgExpense()
	{

        double total=0;
        Expense exp=expenses.first();
        while (exp!=null) {
            total += exp.getAmount();
            exp=expenses.next();
        }
        if (expenses.size()==0)
            return 0.0;
        else
            return (total/expenses.size());
	}
    //Finds the sum of all expenses
	public double totalExpense()
	{   double total=0;
		Expense exp=expenses.first();
		while (exp!=null) {
            total += exp.getAmount();
            exp=expenses.next();
        }
        return total;
	}
    //Finds the amount spent on a particular item
	public double amountSpentOn(String expItem)
    {
        double total=0;
        Expense exp = expenses.first();
        while(exp!=null)
        {
            if (expItem.equals(exp.getItem()))
                total+=exp.getAmount();
            exp = expenses.next();
        }
        return total;
	}


}
