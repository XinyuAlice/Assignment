/*CSCI2110-Assignment2-Exercise1-TeamStats
the program is to hold an unordered	list of	Team objects.
<Xinyu,Liu><B00783546><10.23>
*/
public class TeamStats {
    //attribute
    private List<Team> teamList;

    //constructor
    public TeamStats() {
        teamList = new List<Team>();
    }

    public void add(Team t) {
        teamList.add(t);
    }

    public Team first() {
        return teamList.first();
    }

    public Team next() {
        return teamList.next();
    }

    //displays the	name of	the	team
    //that	had	the	most penalty minutes
    public List<Team> maxtotalPIM() {
        int pim = 0;
        Team tl = teamList.first();
        List<Team> list = new List<Team>();

        while (tl != null) {
            if (tl.getTotalPIM() > pim) {
                list.clear();
                list.add(tl);
                pim = tl.getTotalPIM();
            } else if (tl.getTotalPIM() == pim) {
                list.add(tl);
            }

            tl = teamList.next();
        }


        return list;
    }

    //display the name of the team who has the most numbers of game winning goals
    public List<Team> maxtotalGWG() {
        int tgwg = 0;
        Team tl = teamList.first();
        List<Team> list = new List<Team>();
        while (tl != null) {
            if (tl.getTotalGWG() > tgwg) {
                list.clear();
                list.add(tl);
                tgwg = tl.getTotalGWG();
            } else if (tl.getTotalGWG() == tgwg) {
                list.add(tl);
            }

            tl = teamList.next();
        }

        return list;

    }

    //determine if contain the record
    public boolean contains(PlayerRecord p) {
        boolean result = false;
        Team t = teamList.first();
        while (t != null) {
            if (t.getName().equals(p.getTeam())) {
                result = true;
                break;
            }

            t = teamList.next();
        }
        return result;

    }

}

