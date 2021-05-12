/*CSCI2110-Assignment3-HuffmanTree
 the program is to use huffman code to encode and decode
<Xinyu,Liu><B00783546><11.10>
  */

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class HuffmanTree {
    public static void main(String args[]) throws IOException {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter the filename to read from: ");
        String filename = kb.nextLine();
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);
        char l;//letter
        double p;//frequency of the letter
        Pair pair = null;
        //create a queue of Binary Tree nodes
        ArrayList<BinaryTree<Pair>> S = new ArrayList<>();
        //another queue type of pair
        ArrayList<BinaryTree<Pair>> T = new ArrayList<>();
        while (inputFile.hasNext()) {
            l = inputFile.next().charAt(0);
            p = inputFile.nextDouble();
            pair = new Pair(l, p);
            BinaryTree<Pair> tree = new BinaryTree<>();
            tree.setData(pair);
            //due to the items in txt has been sorted just add to the S
            S.add(0, tree);//add to the arraylist
        }

        //create huffmantree
        //pick two smallest weight tree from S and T
        BinaryTree<Pair> A = new BinaryTree<Pair>();
        BinaryTree<Pair> B = new BinaryTree<Pair>();

        char dummy = '&';
        double weight = 0;
        while (!S.isEmpty()) {
            if (T.isEmpty()) {//case1:T is empty
                //A and B are respectively the front
                // and next to front entries of S
                A = S.get(0);
                B = S.get(1);
                //dequeue them from S
                S.remove(0);
                S.remove(0);
            } else if (!T.isEmpty()) {//case2:T is not empty
                //find the smaller weight tree of trees in front of S
                // and in front of T
                if (S.get(0).getData().getProb() < T.get(0).getData().getProb()) {
                    A = S.get(0);//smaller weight of tree
                    S.remove(0);//dequeue it
                    // the smaller weight tree of the trees in front of S
                    // and in front of T.
                    B = T.get(0);
                    T.remove(0);

                } else {//the front of T is smaller
                    A = T.get(0);
                    T.remove(0);
                    B = S.get(0);
                    S.remove(0);
                }
            }
            // weight of the root is the combined weights
            // of the roots of A and B.
            weight = A.getData().getProb() + B.getData().getProb();
            Pair temp = new Pair(dummy, weight);
            //construct a new tree P
            BinaryTree P = new BinaryTree();
            // creating a root and attaching A
            // and B as the subtrees of this root.
            P.makeRoot(temp);
            P.attachLeft(A);
            A.setParent(P);
            P.attachRight(B);
            B.setParent(P);
            T.add(P);
        }
        while (T.size() > 1) {
            // dequeue two nodes at a time
            A = T.get(0);
            B = T.get(1);
            T.remove(0);
            T.remove(0);
            BinaryTree P = new BinaryTree();
            weight = A.getData().getProb() + B.getData().getProb();
            Pair temp = new Pair(dummy, weight);
            P.makeRoot(temp);
            P.attachLeft(A);
            A.setParent(P);
            P.attachRight(B);
            B.setParent(P);
            T.add(P);
        }

        //encode the pokeman.txt
        String encoding[] = findEncoding(T.get(0));
        System.out.println("Enter the filename to encode: ");
        String filename1 = kb.nextLine();
        String encode = "";
        file = new File(filename1);
        inputFile = new Scanner(file);
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            while (!line.equals("")) {
                char curr = line.charAt(0);//char at the beginning
                if (curr != ' ') {
                    encode += encoding[((byte) (curr)) - 65];
                    line = line.substring(1, line.length());//read the rest

                } else {//leave the spaces as they were
                    encode += " ";
                    line = line.substring(1, line.length());//read the rest
                }

            }

        }
        inputFile.close();

        //write output to file
        PrintWriter outputFile = new PrintWriter("Encoded.txt");
        outputFile.print(encode);
        outputFile.close();
        //decode the encode.txt
        System.out.println("Enter the file name to decode:");
        String filename2 = kb.nextLine();
        file = new File(filename2);
        inputFile = new Scanner(file);

        String decodeLetter = "";
        String decodeLine = "";
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();//read the file line by line
            char[] linearray = line.toCharArray();//convert the line of string to char array
            for (int j = 0; j < linearray.length; j++) {
                if (linearray[j] == ' ') {
                    decodeLine += " ";//leave the space
                  //decode byte to char
                } else {
                    decodeLetter += linearray[j];
                    for (int k = 0; k < encoding.length; k++) {
                        if (decodeLetter.equals(encoding[k])) {
                            char letter = (char) (k + 65);//turn it back to the ascii code
                            decodeLine += letter;
                            decodeLetter = "";
                        }
                    }
                }
            }

            decodeLine += "\n";
        }
        inputFile.close();
        //write the output to file
        PrintWriter output = new PrintWriter("Decoded.txt");
        output.println(decodeLine);
        output.close();
    }

    //methods for finding the encoding
    public static void findEncoding(BinaryTree<Pair> t, String[] a, String prefix) {
        if (t.getLeft() == null && t.getRight() == null) {
            a[((byte) (t.getData().getLetter())) - 65] = prefix;
        } else {
            findEncoding(t.getLeft(), a, prefix + "0");
            findEncoding(t.getRight(), a, prefix + "1");
        }

    }

    public static String[] findEncoding(BinaryTree<Pair> t) {
        String[] result = new String[26];
        findEncoding(t, result, "");
        return result;
    }


}

