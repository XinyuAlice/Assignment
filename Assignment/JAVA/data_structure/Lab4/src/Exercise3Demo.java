/*CSCI2110-Lab4-Exercise3-demo
the program is to create a demo of the pairlist
<Xinyu,Liu><B00783546><10.11>*/
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Exercise3Demo {

    public static void main(String[] args) throws IOException {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter the filename to read from: ");//input filename
        String filename = kb.nextLine();
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);
        PairList pairList = new PairList();//create a new pairlist
        String country, capital;
        Pair c = null;
        int count=0,correct=0;

        while (inputFile.hasNext()) {//read the content of the file line by line
            country = inputFile.nextLine();
            capital = inputFile.nextLine();
            c = new Pair(country, capital);
            pairList.add(c);
        }
        System.out.println("Welcome to the Country-Capital Quiz\n");
        System.out.println("Play?");
        String answer = kb.nextLine();
        while (answer.equals("Yes")) {//loop to begin a geographic quiz
            count++;//calculate the times
            Random random = new Random();

            int value;
            value=random.nextInt(pairList.size()-1);//random number
            if (value%2==0) {//random choose the question : even number to ask for the capital
                System.out.println("What is the capital of "+pairList.get(value).getCountry()+ "?");
                String C=kb.nextLine();
                if(C.equals(pairList.get(value).getCapital())){//answer is right
                    System.out.println("Correct. Play?");
                    String play=kb.nextLine();
                    if (play.equals("Yes")){
                        correct++;
                        continue;
                    }
                    else{
                        correct++;
                        System.out.println("Game over.");
                    }
                }
                else{//answer is wrong
                    System.out.println("Incorrect. The correct answer is"+pairList.get(value).getCapital()+". Play?");
                    String playagain=kb.nextLine();
                    if (playagain.equals("Yes")){
                        continue;
                    }
                    else{
                        System.out.println("Game over.");
                        break;
                    }
                }

            }
            else{//odd number to ask for the country
                System.out.println("What country has "+pairList.get(value).getCapital()+" as its capital?");
                String Ct=kb.nextLine();
                if(Ct.equals(pairList.get(value).getCountry())){//answer is right
                    System.out.println("Correct. Play?");
                    String play=kb.nextLine();
                    if (play.equals("Yes")){
                        correct++;
                        continue;
                    }
                    else{
                        System.out.println("Game over.");
                        correct++;
                        break;
                    }
                }
                else{//answer is wrong
                    System.out.println("Incorrect. The correct answer is"+pairList.get(value).getCountry()+". Play?");
                    String playagain=kb.nextLine();
                    if (playagain.equals("Yes")){
                        continue;
                    }
                    else{
                        System.out.println("Game over.");
                        break;
                    }
                }

            }
        }
        System.out.println("Game Stats: ");
        int score=100*correct/count;//calculte the rate of correct answer
        System.out.println("Questions played: "+count+"; Correct answers:"+correct+"; Score:"+score+"%");
    }
}
