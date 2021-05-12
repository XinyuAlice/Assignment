/*CSCI2110-Assignment2-Exercise1-playerRecord
the program is to write all	the 17 instance	variables for one
player(Name,Team,GP,G,A...ATOI).
<Xinyu,Liu><B00783546><10.23>
*/
public class PlayerRecord {
    //17 attributes
    private String Name;
    private String Team;
    private int GP;//Games Played
    private int G;//Goals scored
    private int A;//Assists
    private int P;//Points
    private int Rating;//+- Rating
    private int PPG;//Power Play Goals
    private int PPP;//Power Play Points
    private double PTSG;//Points Per Game
    private int SHG;//Short Handed Goals
    private int SHP;//Short Handed Points
    private int GWG;//Game Winning Goals
    private int PIM;//Penalty Minutes
    private int SOG;//Shots on Goal
    private double SP;//Shooting Percentage
    private String ATOI;//(Average	Time on	Ice
    //constructor
    public PlayerRecord(String n,String t,int gp,int g,int a, int p,int rating,int ppg,int ppp,double ptsg,int shg,int shp,int gwg,int pim,int sog,double sp,String atoi ){
     Name=n;
     Team=t;
     GP=gp;
     G=g;
     A=a;
     P=p;
     Rating=rating;
     PPG=ppg;
     PPP=ppp;
     PTSG=ptsg;
     SHG=shg;
     SHP=shp;
     GWG=gwg;
     PIM=pim;
     SOG=sog;
     SP=sp;
     ATOI=atoi;
    }
    //get methods
    public int getGWG(){
        return GWG;
    }
    public String getATOI(){
        return ATOI;
    }
    public int getPIM(){
        return PIM;
    }
    public int getSOG(){
        return SOG;
    }
    public int getA(){
        return A;
    }
    public String getName(){
        return Name;
    }
    public String getTeam(){
        return Team;
    }
    //toString method
  public String toString(){

        return Name+Team;
  }


}
