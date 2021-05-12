/*CSCI2110-Assignment2-Exercise1-playStats
the program is to hold an unordered list of PlayerRecord objects.
<Xinyu,Liu><B00783546><10.23>
*/
public class PlayStats {
    //attribute
    private List<PlayerRecord> playerList;

    //constructor
    public PlayStats() {
        playerList = new List<PlayerRecord>();
    }

    //add method
    public void add(PlayerRecord pr) {
        playerList.add(pr);
    }

    public PlayerRecord first() {
        return playerList.first();
    }

    public PlayerRecord next() {
        return playerList.next();
    }

    //display the player's name and his team's name who scored most GWG
    public List<PlayerRecord> maxGWG() {
        int gwg = 0;
        PlayerRecord pr = playerList.first();
        List<PlayerRecord> list = new List<PlayerRecord>();
        while (pr != null) {

            if (pr.getGWG() > gwg) {
                list.clear();
                list.add(pr);
                gwg = pr.getGWG();
            } else if (pr.getGWG() == gwg) {
                list.add(pr);
            }
            pr = playerList.next();
        }
        return list;
    }

    //display the player's name and his team's name who scored most ATOI
    public List<PlayerRecord> maxATOI() {
        String atoi = " ";
        List<PlayerRecord> list = new List<PlayerRecord>();

        PlayerRecord pr = playerList.first();
        while (pr != null) {
            if (pr.getATOI().compareTo(atoi) > 0) {

                list.clear();
                list.add(pr);
                atoi = pr.getATOI();
            } else if (pr.getATOI().compareTo(atoi) == 0) {
                list.add(pr);
            }

            pr = playerList.next();
        }
        return list;

    }

    //display the player's name and his team's name who scored most PIM
    public List<PlayerRecord> maxPIM() {
        int pim = 0;

        List<PlayerRecord> list = new List<PlayerRecord>();
        PlayerRecord pr = playerList.first();
        while (pr != null) {
            if (pr.getPIM() > pim) {
                list.clear();
                list.add(pr);
                pim = pr.getPIM();
            }
            pr = playerList.next();
        }
        return list;
    }

    //display the player's name and his team's name who scored most SOG
    public List<PlayerRecord> maxSOG() {
        int sog = 0;
        List<PlayerRecord> list = new List<PlayerRecord>();
        PlayerRecord pr = playerList.first();
        while (pr != null) {

            if (pr.getSOG() > sog) {
                list.clear();
                list.add(pr);
                sog = pr.getSOG();
            } else if (pr.getSOG() == sog) {
                list.add(pr);
            }
            pr = playerList.next();
        }
        return list;
    }

    public List<PlayerRecord> maxA() {
        int a = 0;

        List<PlayerRecord> list = new List<PlayerRecord>();
        PlayerRecord pr = playerList.first();
        while (pr != null) {

            if (pr.getA() > a) {
                list.clear();
                list.add(pr);
                a = pr.getA();
            } else if (pr.getA() == a) {
                list.add(pr);
            }
            pr = playerList.next();
        }
        return list;

    }

}
