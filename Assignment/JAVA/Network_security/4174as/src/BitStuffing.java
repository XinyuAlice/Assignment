/*CSCI4171-Assignment2-Bit Stuffing
 the program is to use bit stuffing to convert text file to ascii code and then reconvert back
<Xinyu,Liu><B00783546><10.17>
*/

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BitStuffing{
    public static void main( String args[] ) throws IOException{
      try {
          //create a path which point to the directory file
          //learn from
          // http://tutorials.jenkov.com/java-nio/path.html
          Path path1 = Paths.get("Pokeman.txt");
          //learn from
          //https://attacomsian.com/blog/java-files-readallbytes-example#reading-text-files
          //use Files.readAllBytes to read a file; read all bytes
          byte[] file1Data = Files.readAllBytes(path1);
          String content1 = "";
          for(byte character : file1Data){
              //learn from https://docs.oracle.com/javase/7/docs/api/java/lang/Integer.html
              //use Integer.toBinaryString
              //returns a string representation of the integer argument as an unsigned integer in base 2.
              //This value is converted to a string of ASCII digits in binary (base 2) with no extra leading 0s.
              String temp = Integer.toBinaryString(character);
              while(temp.length()!=8){
                  //add 0 in the front
                  temp = "0" + temp;
              }
              //add to content1
              content1 += temp;
          }
          // every five consecutive ones add one 0.
          content1 = content1.replace("11111", "111110");
          System.out.println(content1);
          //print to file
          PrintWriter outputFile1 = new PrintWriter("Encoded.txt");
          outputFile1.print(content1);
          outputFile1.close();

          //create another path to get 'Encoded.txt'
          Path path2=Paths.get("Encoded.txt");
          //read all bytes
          byte[]file2Data=Files.readAllBytes(path2);
          //convert bytes to string
          String content2=new String(file2Data);
          //for every five consecutive ones, remove 0
          content2=content2.replace("111110", "11111");
          //for the output
          String out="";
          for (int i = 0; i < content2.length()/8; i++) {
              //convert String to integer for the rest
              int res = Integer.parseInt(content2.substring(8*i,(i+1)*8),2);
              out += (char)(res);
          }
          //print the output
          System.out.println(out);
          PrintWriter outputFile2 = new PrintWriter("output.txt");
          outputFile2.println(out);
          outputFile2.close();
      }catch (IOException e){
          e.printStackTrace();
      }
    }


}