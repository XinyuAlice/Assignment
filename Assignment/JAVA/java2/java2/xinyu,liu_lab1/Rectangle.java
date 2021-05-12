/*CSCI1101-Lab1-exercise1(a)
the program is to determine a rectangle
<Xinyu,Liu><B00783546><1.16>*/
//Rectangle.java
public class Rectangle{
  //instance variables
  //attributes
  private int width;
  private  int length;
  //methods(operations)
  //construtors
  public Rectangle(){
  }
  public Rectangle(int l,int w){
  width=w;
  length=l;
  }
  //get  methods(accessed)
  public int getWidth(){
  return width;
  }
  public int getLength(){
  return length;
  }
  //set method(mutator)
  public void setWidth(int w){
  width=w;
  }
  public void setLength(int len){
  length=len;
  }
  //final Area
  public int findArea(){
  return length*width;
  }
  }//end Rectangle

