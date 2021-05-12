/*CSCI2110-Lab4-Node
the program is to create a generic node with data and next
<Xinyu,Liu><B00783546><10.11>
*/
//Node class
//Defines a generic node
//Used by the generic linked list (LinkedList<T>) class
public class Node<T>
{
    //attributes
    private T data;
    private Node<T> next;
    //constructor
    public Node(T data, Node<T> next)
    {
        this.data = data;
        this.next = next;
    }
    //get method
    public T getData()
    {
        return data;
    }
    public Node<T> getNext()
    {
        return this.next;
    }
    //set method
    public void setData(T data)
    {
        this.data = data;
    }
    public void setNext(Node<T> next)
    {
        this.next = next;
    }

}