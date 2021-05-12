/*CSCI1101-Lab1-exercise1(a)demo
the program is to create and use Rectangle objects 
<Xinyu,Liu><B00783546><1.16>*/
//RectangleDemo.java
public class RectangleDemo{
 public static void main(String[]args){
  Rectangle rect1,rect2;
  rect1=new Rectangle(10,30);//rect1
  rect2=new Rectangle(20,25);//rect2
  rect1.setLength(10);//set length
  rect1.setWidth(30);//set width
  rect2.setLength(20);//set length
  rect2.setWidth(25);//set width
  System.out.println("Area of rect1 is " +rect1.findArea());
  System.out.println("Area of rect2 is " +rect2.findArea());
 }
}
  