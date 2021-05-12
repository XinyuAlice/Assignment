/*CSCI2110-Lab7-BinarySearchTreeDemo
the program is to test BinarySearchTree
<Xinyu,Liu><B00783546><10.30>
*/

import java.util.Scanner;
public class BinarySearchTreeDemo {
    public static void main(String[] args) {
       //create an empty binary search tree of Integer object
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        //BLOCK 1
        // generate 10 random integers in the range 1 to 1000
        int[] random = Random(1, 1000, 10);

        //insert these into the binary search tree
        for (int i = 0; i < random.length; i++) {
            tree.insert(random[i]);
        }
        //do an inorder traversal and display the keys
        System.out.println("In order:");
        BinarySearchTree.inorder(tree.getTree());
        System.out.println();
        //display the minimum integer using the findMin method
        System.out.println("\n Largest key in the binary search tree:");
        System.out.println(tree.findMax());
        //display the maximum integer using the findMax method
        System.out.println("\n Smallest key in the binary search tree:");
        System.out.println(tree.findMin());
        //prompt the user to search for a key
        System.out.println("Enter the key to search:");
        Scanner kb = new Scanner(System.in);
        int k = kb.nextInt();
        //use the recursive search method to search
        //and display if the key was found or not

        BinaryTree<Integer> treenode = tree.recursiveSearch(k);
        if (treenode != null)
            System.out.println(treenode.getData() + " found");
        else
            System.out.println(" not found");


       //BLOCK 2:
        //generate 100 random integers (keys) in the range 1 to 1000
        int[] r = Random(1, 1000, 100);

        //create a binary search tree and insert the keys
        BinarySearchTree<Integer> Tree = new BinarySearchTree<>();
        for (int j = 0; j < r.length; j++) {
            Tree.insert(r[j]);
        }

        //find the height of this tree and determine if this tree
        //is height balanced.
        System.out.println("The height of the tree is " + Tree.findHeight(Tree.getTree()));
        System.out.println("Whether the tree is balanced  " + Tree.isHeightBalanced(Tree.getTree()));
        System.out.println();
        int count = 0;
        //Repeat BLOCK 2 (tree with 100 nodes)
        // 50 times using a while loop
        //Write the answers (height and if height balanced)
        //into a text file
        while (count < 50) {
            int[] list = Random(1, 1000, 100);
            BinarySearchTree<Integer> T = new BinarySearchTree<>();
            for (int n = 0; n < list.length; n++) {
                T.insert(list[n]);
            }
            System.out.println("The height of the tree is " + T.findHeight(T.getTree()));
            System.out.println("Whether the tree is balanced  " + T.isHeightBalanced(T.getTree()));
            count++;
        }
    }


    public static int[] Random(int min, int max, int n) {
        int[] result = new int[n];
        int count = 0;
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        while (count < n) {

            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < count; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

}

