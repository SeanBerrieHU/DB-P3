package domain;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private int productNumber;
    private String naam;
    private String beschrijving;

    private double prijs;
    private List<OVChipkaart> OVChipkaartList;

    public Product(int Pn, String Nm, String Bs, double Pr){
        this.productNumber = Pn;
        this.naam = Nm;
        this.beschrijving = Bs;
        this.prijs = Pr;
        this.OVChipkaartList = new ArrayList<>();
    }

    public int getProductNummer() {
        return productNumber;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public void addOVChipkaart(OVChipkaart ovChipkaart){
        if(!OVChipkaartList.contains(ovChipkaart)){
            OVChipkaartList.add(ovChipkaart);
        }
    }

    public List<OVChipkaart> getOVChipkaarten(){
        return this.OVChipkaartList;
    }

    public String toString(){
        return "#"+ this.productNumber + ", Naam: " + this.naam + " Beschrijving: " + this.beschrijving + " Prijs: " + this.prijs;
    }




}
