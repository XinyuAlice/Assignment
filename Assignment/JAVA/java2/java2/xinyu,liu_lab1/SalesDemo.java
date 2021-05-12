/*CSCI1101-Lab1-exercise2
the program is the demo of sales to keep track of the sales of one item in the store 
<Xinyu,Liu><B00783546><1.16>*/
public class SalesDemo{
   public static void main(String[] args) {
        Sales item1;//item1
        item1 = new Sales(2.50, 4, 0.90);//item1
        item1.cost = 2.50;//cost of item1
        item1.bulkQuantity = 4;//bulkQuantity of item1
        item1.discount = 0.90;//discount of item1
        item1.numberSold = 10;//number sold of item1
        item1.name = "Shampoo";
        item1.displaySales();
        Sales item2;//item2
        item2 = new Sales(1.99, 20, 0.85);//item2
        item2.cost = 1.99;//cost of item2
        item2.bulkQuantity = 20;//bulkQuantity of item2
        item2.discount = 0.85;//discount of item2
        item2.numberSold = 10;//number sold of item2
        item2.name = "Toothpaste";
        item2.displaySales();

    }

}