package domain;

import domain.Adres;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {

    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> OVChipkaarten;

    public Reiziger(int Rid, String Vl, String Tv, String An, Date Gd){
        this.reiziger_id = Rid;
        this.voorletters = Vl;
        this.tussenvoegsel = Tv;
        this.achternaam = An;
        this.geboortedatum = Gd;
        this.OVChipkaarten = new ArrayList<>();
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

    public Adres getAdres(){
        return this.adres;
    }

    public void addAdres(Adres adres){
        this.adres = adres;
    }

    public List<OVChipkaart> getOVChipkaarten(){
        return this.OVChipkaarten;
    }
    public void addOVChipkaart(OVChipkaart ovChipkaart){
        OVChipkaarten.add(ovChipkaart);
    }

    public void removeOVChipkaarten(){
        OVChipkaarten.clear();
    }

    public String getNaam(){
        return this.voorletters + ". " + this.tussenvoegsel + " " + this.achternaam;
    }

    public String toString(){

        String adresString = "{ Geen adres beschikbaar }";
        if(this.adres != null){
            adresString = this.adres.toString();
        }

        return "Reiziger {#" + this.reiziger_id + " " + getNaam() + " " + this.geboortedatum + "." + adresString + OVChipkaarten.toString();
    }

}
