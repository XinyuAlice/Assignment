/*CSCI2110-Assignment3-Pair
 the program is to defines the letter and its probability as an object
<Xinyu,Liu><B00783546><11.10>
  */

public class Pair {
    //attributes
    private char letter;
    private double prob;
    //constructor
public Pair(char l,double p){
    letter=l;
    prob=p;

}
    //get and set methods
    public char getLetter(){
    return letter;
    }
    public double getProb() {
    return prob;
    }
    public void setLetter(char letter){
    this.letter=letter;
    }
    public void setProb(double prob){
    this.prob=prob;
    }
    //toString method
    public String toString(){
    return "letter:"+letter+"probability:"+prob;
    }


}

