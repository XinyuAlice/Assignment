/*CSCI1101-Lab1-exercise2
the program is to keep track of the sales of one item in the store 
<Xinyu,Liu><B00783546><1.16>*/
public class Sales {
    //instance variables
    //attributes
    private String name;
    private double cost;
    private int bulkQuantity;
    private double discount;
    private double numberSold;
    private double totalAmount;
    private double totalDiscount;
    //methods(operations)
    //construtors
    public Sales(double c, int b, double d) {
        cost = c;
        bulkQuantity = b;
        discount = d;
    }
    //get methods
    public double getCost() {//getCost
        return cost;
    }

    public double getbulkQuantity() {//getbulkQuantity
        return bulkQuantity;
    }

    public double getDiscount() {//getDiscount
        return discount;
    }
    //set methods 
    public void setCost(double cos) {//setCost
        cost = cos;
    }

    public void setBulkQuantity(int bul) {//setBulkQuantity
        bulkQuantity = bul;
    }

    public void setDiscount(double dis) {//setDiscount
        discount = dis;
    }

    //registerSale(int n) records the sale of n items. If n is larger than the bulk quantity, the cost will be reduced by the discount.
    public void registerSale(int n) {
        if (n > bulkQuantity) {
            cost = cost * discount;
        }
    }
    //displaySales displays the name of the item, number sold, the total amount, and total discount.

    public void displaySales() {
        System.out.println("item:" + name);
        System.out.println("Number sold: " + numberSold);
        totalAmount = cost * numberSold;
        System.out.println("TotalAmount: $" + totalAmount);
        registerSale((int) numberSold);
        double changetotalAmount = cost * numberSold;
        System.out.println("totalDiscount: $" + (totalAmount - changetotalAmount));
    }

   
}//end Sales