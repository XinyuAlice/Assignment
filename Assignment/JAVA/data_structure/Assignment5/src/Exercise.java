import java.io.*;
import java.util.Scanner;

public class Exercise {
    public static void main(String args[]) throws IOException {
        Scanner kb = new Scanner(System.in);
        //read a file
        System.out.println("Enter the filename:");
        String filename = kb.nextLine();
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);
        int num = Integer.parseInt(inputFile.nextLine());
        int[][] adjMatrix = new int[num][num];//create an adjMatrix
        while (inputFile.hasNext()) {

            String firstVertex = inputFile.next();
            String secondVertex = inputFile.next();

            int x = firstVertex.charAt(0) - 65;//x=4:E
            int y = secondVertex.charAt(0) - 65;//y=0:A
            adjMatrix[x][y] = 1;
            adjMatrix[y][x] = 1;
        }
        inputFile.close();

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                System.out.print(adjMatrix[i][j]);
            }

            System.out.println();
        }
        //next:traverse the graph
        //use DFS
        //start from v1
        //pick a neighbour of v1,say v2.Go to v2
        //pick a neighbour of v2,say v3.Go to v3
        //continue and mark each vertex that has been visited
        boolean[] visit = new boolean[num];
        System.out.println("Enter the starting vertex :");
        int v=kb.nextInt();
        GraphTraversal(v,adjMatrix,visit);
    }

    public static void GraphTraversal(int i, int[][] adjMatrix, boolean[] visit) {
        if (!visit[i]) {
            visit[i] = true;
            //visit each vertex and mark
            System.out.print((char) (i + 65) + " ");
            for (int j = 0; j < adjMatrix[i].length; j++) {
                //go to its neighbourhood who has not been visited


                if (adjMatrix[i][j] == 1 && !visit[j]) {
                    GraphTraversal(j, adjMatrix, visit);
                }
            }
        }
    }
}



