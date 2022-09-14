import java.sql.Date;

public class Reiziger {

    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    public Reiziger(int Rid, String Vl, String Tv, String An, Date Gd){
        this.reiziger_id = Rid;
        this.voorletters = Vl;
        this.tussenvoegsel = Tv;
        this.achternaam = An;
        this.geboortedatum = Gd;
    }

    public int getId(){
        return this.reiziger_id;
    }

    public void setId(int id){
        this.reiziger_id = id;
    }

    public String getVoorletters(){
        return this.voorletters;
    }

    public String getTussenvoegsel(){
        return this.tussenvoegsel;
    }

    public String getAchternaam(){
        return this.achternaam;
    }

    public Date getGeboorteDatum(){
        return this.geboortedatum;
    }

    public String getNaam(){
        return this.voorletters + ". " + this.tussenvoegsel + " " + this.achternaam;
    }

    public String toString(){
        return "Reiziger id: " + this.reiziger_id + ", Naam: " + getNaam() + ", Geboortedatum: " + this.geboortedatum + ".";
    }

}
