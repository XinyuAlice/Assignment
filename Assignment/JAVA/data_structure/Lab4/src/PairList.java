/*CSCI2110-Lab4-Exercise3-PairList
the program is to create an unordered Pair linkedlist
<Xinyu,Liu><B00783546><10.11>
 */
public class PairList {
    //attribute
    private List<Pair> PairList;
    //constructor
    public PairList() {
        PairList = new List<Pair>();
    }
    //add method
    public void add(Pair c) {

        PairList.add(c);
    }
    //size() method
    public int size() {


        return PairList.size();
    }
    //get method:get the item in the given index
    public Pair get(int index){
        if(index<0||index>=size()){
            throw new IndexOutOfBoundsException();

        }
            return PairList.get(index);

    }

}