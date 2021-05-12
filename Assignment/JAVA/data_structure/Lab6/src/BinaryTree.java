/*CSCI2110-Lab6-BinaryTree
the program is to write four methods of binarytree
<Xinyu,Liu><B00783546><10.23>
*/
import java.util.ArrayList;
public class BinaryTree<T>
{
    private T data;
    private BinaryTree<T> parent;
    private BinaryTree<T> left;
    private BinaryTree<T> right;

    public BinaryTree()
    {
        parent = left = right = null;
        data = null;
    }


    public void makeRoot(T data)
    {
        if (!isEmpty())
        {
            System.out.println("Can't make root. Already exists");
        }
        else
            this.data = data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public void setLeft(BinaryTree<T> tree)
    {
        left = tree;
    }
    public void setRight(BinaryTree<T> tree)
    {
        right = tree;
    }
    public void setParent(BinaryTree<T> tree)
    {
        parent = tree;
    }

    public T getData()
    {
        return data;
    }
    public BinaryTree<T> getParent()
    {
        return parent;
    }
    public BinaryTree<T> getLeft()
    {
        return left;
    }
    public BinaryTree<T> getRight()
    {
        return right;
    }


    public void attachLeft(BinaryTree<T> tree)
    {
        if (tree==null) return;
        else if (left!=null || tree.getParent()!=null)
        {
            System.out.println("Can't attach");
            return;
        }
        else
        {

            tree.setParent(this);
            this.setLeft(tree);
        }
    }

    public void attachRight(BinaryTree<T> tree)
    {
        if (tree==null) return;
        else if (right!=null || tree.getParent()!=null)
        {
            System.out.println("Can't attach");
            return;
        }
        else
        {

            tree.setParent(this);
            this.setRight(tree);
        }
    }

    public BinaryTree<T> detachLeft()
    {
        if (this.isEmpty()) return null;
        BinaryTree<T> retLeft = left;
        left = null;
        if (retLeft!=null) retLeft.setParent(null);
        return retLeft;
    }
    public BinaryTree<T> detachRight()
    {
        if (this.isEmpty()) return null;
        BinaryTree<T> retRight = right;
        right =null;
        if (retRight!=null) retRight.setParent(null);
        return retRight;
    }
    public boolean isEmpty()
    {
        if (data == null)
            return true;
        else
            return false;
    }
    public void clear()
    {
        left = right = parent =null;
        data = null;
    }

    public BinaryTree<T> root()
    {
        if (parent == null)
            return this;
        else
        {
            BinaryTree<T> next = parent;
            while (next.getParent()!=null)
                next = next.getParent();
            return next;
        }
    }

    public static <T> void preorder(BinaryTree<T> t)
    {
        if (t!=null)
        {
            System.out.print(t.getData()+"\t");
            preorder(t.getLeft());
            preorder(t.getRight());
        }
    }

    public static <T> void inorder(BinaryTree<T> t)
    {
        if (t!=null)
        {
            inorder(t.getLeft());
            System.out.print(t.getData() + "\t");
            inorder(t.getRight());
        }
    }

    public static <T> void postorder(BinaryTree<T> t)
    {
        if (t!=null)
        {
            postorder(t.getLeft());
            postorder(t.getRight());
            System.out.print(t.getData() + "\t");
        }
    }
    //find the number of nodes
    public int  NumberOfNodes(BinaryTree<T>t){
        if(t==null) return 0;
        else return 1+NumberOfNodes(t.getLeft())+NumberOfNodes(t.getRight());
    }
    //find the height of a binary tree
    public int findHeight(BinaryTree<T>t){
        if(t==null) return -1;
        else return 1+Math.max(findHeight(t.getRight()),findHeight(t.getLeft()));
    }
    //determine if the binarytree is balanced
    public boolean balance(BinaryTree<T>t){
      if (t==null)
          return true;

      else return  (Math.abs(findHeight(t.getLeft()) - findHeight(t.getRight())) < 2) && balance(t.getLeft()) && balance(t.getRight());
    }
    //perform a level order traversal of the specified tree
    public static<T> void levelOrder(BinaryTree<T> t){
        ArrayList<BinaryTree<T>> a = new ArrayList<BinaryTree<T>>();
        if (t != null)
            a.add(t);
        while (!a.isEmpty()) {
            BinaryTree<T> temp = a.remove(0);
            if (temp.getLeft() != null)
                a.add(temp.getLeft());
            if (temp.getRight() != null)
                a.add(temp.getRight());
            System.out.print(temp.getData() + "\t");
        }
        System.out.println();


    }


}