//TODO: Get inputs from the user. Do not hardcode them.
/*CSCI2110-Lab6-BinaryTreeDemo
the program is to test four steps which are done in the BinaryTree
<Xinyu,Liu><B00783546><10.23>
*/
import java.util.Scanner;
public class BinaryTreeDemo
{
    public static void main(String[] args)
    {   Scanner kb=new Scanner(System.in);
        BinaryTree<String> A = new BinaryTree<String>();
        BinaryTree<String> B = new BinaryTree<String>();
        BinaryTree<String> C = new BinaryTree<String>();
        BinaryTree<String> D = new BinaryTree<String>();
        BinaryTree<String> E = new BinaryTree<String>();
        BinaryTree<String> F = new BinaryTree<String>();
        BinaryTree<String> G = new BinaryTree<String>();
        BinaryTree<String> H = new BinaryTree<String>();
        System.out.println("Enter a binary tree: ");
        String name = kb.next();
        System.out.println("Enter a binary tree: ");
        String name2 = kb.next();
        System.out.println("Enter a binary tree: ");
        String name3 = kb.next();
        System.out.println("Enter a binary tree: ");
        String name4 = kb.next();
        System.out.println("Enter a binary tree: ");
        String name5 = kb.next();
        System.out.println("Enter a binary tree: ");
        String name6 = kb.next();
        System.out.println("Enter a binary tree: ");
        String name7 = kb.next();
        System.out.println("Enter a binary tree: ");
        String name8 = kb.next();
        //make root
        A.makeRoot(name);
        B.makeRoot(name2);
        C.makeRoot(name3);
        D.makeRoot(name4);
        E.makeRoot(name5);
        F.makeRoot(name6);
        G.makeRoot(name7);
        H.makeRoot(name8);
        //attach node
        A.attachLeft(B);
        A.attachRight(C);
        B.attachLeft(D);
        B.attachRight(E);
        D.attachLeft(F);
        D.attachRight(G);
        F.attachLeft(H);
        System.out.println("Numbers of nodes:"+A.NumberOfNodes(A));
        System.out.println("the height of the binary tree"+A.findHeight(A));
        System.out.println("Whether the binary tree is balanced: "+A.balance(A));
        System.out.println("Levelorder:\t");
        BinaryTree.levelOrder(A);
        System.out.println();
        System.out.print("Preorder:\t");
        BinaryTree.preorder(A);
        System.out.println();

        System.out.print("Inorder:\t");
        BinaryTree.inorder(A);
        System.out.println();

        System.out.print("Postorder:\t");
        BinaryTree.postorder(A);
        System.out.println();


    }

}
