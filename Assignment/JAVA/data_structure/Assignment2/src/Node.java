/*CSCI2110-Assignment2-Node
the program is to write a node for linkedlist
<Xinyu,Liu><B00783546><10.23>
*/
//Node class
//Defines a generic node
//Used by the generic linked list (LinkedList<T>) class
public class Node<T>
{
    private T data;
    private Node<T> next;
    public Node(T data, Node<T> next)
    {
        this.data = data;
        this.next = next;
    }
    public T getData()
    {
        return data;
    }
    public Node<T> getNext()
    {
        return this.next;
    }
    public void setData(T data)
    {
        this.data = data;
    }
    public void setNext(Node<T> next)
    {
        this.next = next;
    }

}
