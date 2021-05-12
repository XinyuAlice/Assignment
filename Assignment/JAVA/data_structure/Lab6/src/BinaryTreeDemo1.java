/*CSCI2110-Lab6-BinaryTreeDemo1
the program is to write a program that reads a number of words (Strings) from the keyboard
<Xinyu,Liu><B00783546><10.23>
*/
import java.util.ArrayList;
import java.util.Scanner;
public class BinaryTreeDemo1 {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        ArrayList<BinaryTree<String>> a = new ArrayList<BinaryTree<String>>();//create a arraylist to save records
        BinaryTree<String> tree = new BinaryTree<>();

        while (true) {
            System.out.println("Enter name or done");
            String name = kb.next();
            if (name.equals("done")) {
                break;
            } else {
                BinaryTree t = new BinaryTree();
                t.makeRoot(name);
                a.add(t);//add all the records into arraylist
            }
        }

        buildTree(tree, a);//call the method buildtree


        System.out.println("Height of the tree is:" + tree.findHeight(tree));
        System.out.println("Number of nodes in the tree is:" + tree.NumberOfNodes(tree));
        System.out.println("Inorder: \n");
        BinaryTree.inorder(tree);
        System.out.println();
        System.out.println("Preorder:\n ");
        BinaryTree.preorder(tree);
        System.out.println();
        System.out.println("Postorder: \n");
        BinaryTree.postorder(tree);
        System.out.println();
        System.out.println("levelorder: \n");
        BinaryTree.levelOrder(tree);
    }
    //read the records in the arraylist to create tree
    public static void buildTree(BinaryTree<String> tree, ArrayList<BinaryTree<String>> a) {

        int i = 0, j = 1;

        while (j < a.size()) {//when not out of bound
            if (tree.getData() == null) {
                tree.makeRoot(a.get(i).getData());
            }
            if (tree.getLeft() == null) {
                tree.attachLeft(a.get(j));
                j++;
            }
            while (j < a.size()) {//check if j is equal to the size of arraylist
                if (tree.getRight() == null) {
                    tree.attachRight(a.get(j));
                    j++;
                } else {
                    i++;
                    tree = a.get(i);
                }
            }

        }
    }
}

