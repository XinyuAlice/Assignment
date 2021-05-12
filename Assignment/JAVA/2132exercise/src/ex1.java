import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class ex1 {
    public static void main(String[] args) throws IOException {
        Scanner kb = new Scanner(System.in);
        ArrayList<String> input = new ArrayList<>();
        String inputLine;
        while (kb.hasNext()) {
            inputLine = kb.nextLine();
            input.add(inputLine);
        }
        ArrayList<String> output = new ArrayList<>();
        for (String line : input) {
            int index = line.length();
            while (index != 0 && line.charAt(index - 1) != ',') {
                index--;
            }
            String result = "";
            if (index != 0) {
                String firsthaf = line.substring(0, index - 1);
                String secondhaf = line.substring(index - 1);
                result = secondhaf + "," + firsthaf;
            } else {
                result = line;
            }
            output.add(result);
        }
        if(!output.isEmpty()){
            for(String curr:output){
                System.out.println(curr);
            }
        }
    }

}
