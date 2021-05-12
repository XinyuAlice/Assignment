import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Extendd {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc1 = new Scanner(new File("inputfile1.txt"));
        ArrayList<Boolean> permit = new ArrayList<>();
        ArrayList<String> sip = new ArrayList<>();
        ArrayList<String> dip = new ArrayList<>();
        ArrayList<String> smask = new ArrayList<>();
        ArrayList<String> dmask = new ArrayList<>();
        ArrayList<String> port = new ArrayList<>();
        ArrayList<String> protocol = new ArrayList<>();
        while (sc1.hasNextLine()) {
            String line = sc1.nextLine();
            String[] sp = line.split(" ");
            if (!sp[0].equals("access-list")) {
                break;
            }
            permit.add(sp[2].equals("permit"));
            protocol.add(sp[3]);
            sip.add(sp[4]);
            smask.add(sp[5]);
            dip.add(sp[6]);
            dmask.add(sp[7]);
            dmask.add(sp[7]);
            if (sp.length > 8) {
                port.add(sp[9]);
            }else{
                port.add("0");
            }
        }
        Scanner sc2 = new Scanner(new File("inputfile2.txt"));
        while (sc2.hasNextLine()) {
            Boolean getResult = false;
            String line = sc2.nextLine();
            String[] ssp = line.split(" ")[0].split("\\.");
            for (int i = 0; i < permit.size(); i++) {
                String[] smk = smask.get(i).split("\\.");
                for (int j = 0; j < 4; j++) {
                    if (smk[j].equals("255")) {
                        ssp[j] = "0";
                    }
                }
                String sipResult = ssp[0];
                for (int k = 1; k < ssp.length; k++) {
                    sipResult = sipResult + "." + ssp[k];
                }
                boolean smatch = sip.get(i).equals(sipResult);
                String[] dsp = line.split(" ")[1].split("\\.");
                String[] dmk = dmask.get(i).split("\\.");
                for (int j = 0; j < 4; j++) {
                    if (dmk[j].equals("255")) {
                        dsp[j] = "0";
                    }
                }
                String dipResult = dsp[0];
                for (int k = 1; k < dsp.length; k++) {
                    dipResult = dipResult + "." + dsp[k];
                }
                boolean dmatch = dip.get(i).equals(dipResult);
                String inputPort = line.split(" ")[2];
                boolean portMatch = false;
                if (port.get(i).contains("-")) {
                    String[] portSp = port.get(i).split("-");
                    int start = Integer.valueOf(portSp[0]);
                    int end = Integer.valueOf(portSp[1]);
                    if (Integer.valueOf(inputPort) >= start && Integer.valueOf(inputPort) <= end) {
                        portMatch = true;
                    }
                } else if (!port.get(i).equals("0")) {
                    if (inputPort.equals(port.get(i))) {
                        portMatch = true;
                    }
                } else {
                    portMatch = true;
                }
                if (smatch && dmatch && portMatch) {
                    String[] reip = line.split(" ");
                    String re = permit.get(i) ? " permitted" : " denied";
                    System.out.println("Packet from " + reip[0] + " to " + reip[1]  + " on port " + reip[2] + re);
                    getResult = true;
                    break;
                }
            }
            if (!getResult) {
                String[] reip = line.split(" ");
                System.out.println("Packet from " + reip[0] + " to " + reip[1] + " on port " + reip[2] + " denied");
            }
        }

//        for(int i = 0; i < permit.size();i++){
//                System.out.println(permit.get(i));
//                System.out.println(sip.get(i));
//                System.out.println(smask.get(i));
//                System.out.println(dip.get(i));
//                System.out.println(dmask.get(i));
//        }
    }
}

