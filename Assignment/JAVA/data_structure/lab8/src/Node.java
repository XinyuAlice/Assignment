/*CSCI2110-Lab8-Exercise1-Node
the program is to build generic node
<Xinyu,Liu><B00783546><11.19>
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