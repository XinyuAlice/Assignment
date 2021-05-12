import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class Stand {
    public static void main(String[] args) {
        try {
            Scanner sc1 = new Scanner(new File("input1.txt"));
            ArrayList<Boolean> permit = new ArrayList<>();
            ArrayList<String> ip = new ArrayList<>();
            ArrayList<String> mask = new ArrayList<>();
            while (sc1.hasNextLine()) {
                String line = sc1.nextLine();
                String[] sp = line.split(" ");
                if (!sp[0].equals("access-list")) {
                    break;
                }
                permit.add(sp[2].equals("permit"));
                ip.add(sp[3]);
                mask.add(sp[4]);
            }
            Scanner sc2 = new Scanner(new File("input2.txt"));
            while (sc2.hasNextLine()) {
                Boolean getResult = false;
                String line = sc2.nextLine();
                String[] sp = line.split("\\.");
                for (int i = 0; i < ip.size(); i++) {
                    String[] mk = mask.get(i).split("\\.");

                    for (int j = 0; j < 4; j++) {
//                        System.out.println(mk[i]);
                        if (mk[j].equals("255")) {
                            sp[j] = "0";
                        }
                    }
                    String ipResult =sp[0];
                    for(int k = 1; k < sp.length; k ++){
                        ipResult=ipResult + "." + sp[k];
                    }
                    if (ip.get(i).equals(ipResult)) {
                        String re = permit.get(i) ? " permitted" : " denied";
                        System.out.println("Packet from " + line + re);
                        getResult = true;
                        break;
                    }
                }
                if(!getResult){
                    System.out.println("Packet from " + line + " denied");
                }

            }
//            for(int i = 0; i < permit.size();i++){
//                System.out.println(permit.get(i));
//                System.out.println(ip.get(i));
//                System.out.println(mask.get(i));
//            }
        } catch (
                IOException e) {

        }
    }
}

