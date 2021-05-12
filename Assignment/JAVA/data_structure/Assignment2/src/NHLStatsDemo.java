/*CSCI2110-Assignment2-Exercise1-Demo
the program is the demo of the exercise1
<Xinyu,Liu><B00783546><10.23>
*/

import java.io.*;
import java.util.Scanner;

public class NHLStatsDemo {
    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        //read nhlstats.txt
        System.out.print("Enter the filename to read from: ");
        String filename = keyboard.nextLine();
        File file = new File(filename);
        String n, t, atoi, gp, g, a, p, rating, ppg, ppp, shg, shp, gwg, pim, sog, ptsg, sp;
        PlayerRecord pr = null;
        Team team = null;
        PlayStats playStats = new PlayStats();
        TeamStats teamStats = new TeamStats();
        Scanner inputFile = new Scanner(file);
        while (inputFile.hasNext()) {
            n = inputFile.nextLine();
            t = inputFile.nextLine();
            gp = inputFile.nextLine();
            g = inputFile.nextLine();
            a = inputFile.nextLine();
            p = inputFile.nextLine();
            rating = inputFile.nextLine();
            ppg = inputFile.nextLine();
            ppp = inputFile.nextLine();
            ptsg = inputFile.nextLine();
            shg = inputFile.nextLine();
            shp = inputFile.nextLine();
            gwg = inputFile.nextLine();
            pim = inputFile.nextLine();
            sog = inputFile.nextLine();
            sp = inputFile.nextLine();
            atoi = inputFile.nextLine();
            pr = new PlayerRecord(n, t, Integer.parseInt(gp), Integer.parseInt(g), Integer.parseInt(a), Integer.parseInt(p), Integer.parseInt(rating), Integer.parseInt(ppg), Integer.parseInt(ppp), Double.parseDouble(ptsg), Integer.parseInt(shg), Integer.parseInt(shp), Integer.parseInt(gwg), Integer.parseInt(pim), Integer.parseInt(sog), Double.parseDouble(sp), atoi);
            playStats.add(pr);//add the player record to the list
        }

        inputFile.close();
        PlayerRecord playerRecord = playStats.first();
        int tgwg = 0, tpim = 0;
        //add the record to team list
        while (playerRecord != null) {
            Team t1 = teamStats.first();
            if (t1 == null) {
                Team newTeam = new Team(playerRecord, playerRecord.getTeam());
                newTeam.addPlayer(playerRecord);
                teamStats.add(newTeam);
            } else {
                boolean find = false;//find if the teamStats has included the team
                while (t1 != null) {
                    if (t1.getName().equals(playerRecord.getTeam())) {
                        t1.addPlayer(playerRecord);
                        find = true;
                    }
                    t1 = teamStats.next();
                }
                if (!find) {
                    Team newTeam = new Team(playerRecord, playerRecord.getTeam());
                    newTeam.addPlayer(playerRecord);
                    teamStats.add(newTeam);


                }
            }


            playerRecord = playStats.next();
        }

        //write the output to the output file
        PrintWriter outputFile = new PrintWriter("nhlstatsoutput.txt");

        outputFile.println("NHL Results Summary");
        //highest gwg players and their teams
        outputFile.println("Players with the highest game winning goals and their teams: \n");
        List<PlayerRecord> list1 = null;
        list1 = playStats.maxGWG();
        PlayerRecord playerRecord1 = list1.first();
        while (playerRecord1 != null) {
            outputFile.println(playerRecord1.getName());
            outputFile.println(playerRecord1.getTeam());

            playerRecord1 = list1.next();
        }
        outputFile.println();
        outputFile.println("Players with the highest average time on ice and their teams: \n ");
        //highest atoi players and their teams
        List<PlayerRecord> list2 = null;
        list2 = playStats.maxATOI();
        PlayerRecord playerRecord2 = list2.first();
        while (playerRecord2 != null) {
            outputFile.println(playerRecord2.getName());
            outputFile.println(playerRecord2.getTeam());

            playerRecord2 = list2.next();
        }

        outputFile.println();
        outputFile.println("Players with the maximum number of penalty minutes and their teams: \n");
        //maximum pim players and their team
        List<PlayerRecord> list3 = null;
        list3 = playStats.maxPIM();
        PlayerRecord playerRecord3 = list3.first();
        while (playerRecord3 != null) {
            outputFile.println(playerRecord3.getName());
            outputFile.println(playerRecord3.getTeam());

            playerRecord3 = list3.next();
        }

        outputFile.println();
        outputFile.println("Player with the number of shots on goal (SOG): ");
        //maximum sog players and their team
        List<PlayerRecord> list4 = null;
        list4 = playStats.maxSOG();
        PlayerRecord playerRecord4 = list4.first();
        while (playerRecord4 != null) {
            outputFile.println(playerRecord4.getName());
            outputFile.println(playerRecord4.getTeam());

            playerRecord4 = list4.next();
        }
        outputFile.println();
        outputFile.println("Player with most number of assists: ");
        //highest assists players and their team
        List<PlayerRecord> list5 = null;
        list5 = playStats.maxA();
        PlayerRecord playerRecord5 = list5.first();
        while (playerRecord5 != null) {
            outputFile.println(playerRecord5.getName());
            outputFile.println(playerRecord5.getTeam());

            playerRecord5 = list5.next();
        }

        outputFile.println();
        outputFile.println("Teams that had the most number of penalty minutes:\n");
        //highest total pim
        List<Team> list6 = null;
        list6 = teamStats.maxtotalPIM();
        Team team1 = list6.first();
        while (team1 != null) {
            outputFile.println(team1.getName());


            team1 = list6.next();
        }

        outputFile.println();
        outputFile.println("Teams that had the most number of game winning goals:\n");
        //highest total gwg
        List<Team> list7 = null;
        list7 = teamStats.maxtotalGWG();
        Team team2 = list7.first();
        while (team2 != null) {
            outputFile.println(team2.getName());


            team2 = list7.next();
        }
        outputFile.close();
    }
}



