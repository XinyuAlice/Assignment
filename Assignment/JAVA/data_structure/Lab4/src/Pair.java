/*CSCI2110-Lab4-Exercise3-Pair
the program is to create a pair object
<Xinyu,Liu><B00783546><10.11>
 */public class Pair {
     //attribute
    private String Capital;
    private String Country;
    //constructor
    public Pair(String country,String capital){
        Country=country;
        Capital=capital;
    }
    //get method
    public String getCapital(){
        return Capital;

    }
    public String getCountry(){
        return Country;
    }
    //toString method
    public String toString(){
        return "Country"+Country+"Capital"+Capital;
    }

}
