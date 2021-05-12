/*CSCI2110-Assignment2-Exercise1-Team
the program is to write team object that holds the team name,total number of game winning goals	and
the	total number of	penalty	minutes	of	players	belonging to this team
player(Name,Team,GP,G,A...ATOI).
<Xinyu,Liu><B00783546><10.23>
*/
public class Team {
    //attibutes
    private String name;
    private int totalGWG;
    private int totalPIM;
    private List<PlayerRecord> teamList;

    //constructor
    public Team(PlayerRecord p, String n) {
        teamList = new List<PlayerRecord>();
        name = n;
    }

    //get methods
    public String getName() {
        return name;
    }

    public int getTotalGWG() {
        PlayerRecord p = teamList.first();
        int sum = 0;
        if (p == null) {
            return 0;
        } else {
            while (p != null) {
                sum += p.getGWG();
                p = teamList.next();
            }
        }
        return sum;


    }

    public int getTotalPIM() {
        PlayerRecord p = teamList.first();
        int sum = 0;
        if (p == null) {
            return 0;
        } else {
            while (p != null) {
                sum += p.getPIM();
                p = teamList.next();
            }
        }
        return sum;


    }
    //set method
    public void setName(String n) {
        name = n;
    }

    public void setTotalGWG(int tgwg) {
        totalGWG = tgwg;
    }

    public void setTotalPIM(int tpim) {
        totalPIM = tpim;
    }
    //toString method
    public String toString() {
        return name;
    }
    //add method
    public void addPlayer(PlayerRecord playerRecord) {
        teamList.add(playerRecord);
    }
}
