package domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {

    private int kaartNummer;
    private Date geldigTot;
    private int klasse;
    private double saldo;
    private int reizigerId;
    private List<Product> productenLijst;

    public OVChipkaart(int Kn, Date Gt, int Kl, double Sd, int Rid){
        this.kaartNummer = Kn;
        this.geldigTot = Gt;
        this.klasse = Kl;
        this.saldo = Sd;
        this.reizigerId = Rid;
        this.productenLijst = new ArrayList<>();
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getReizigerId() {
        return reizigerId;
    }

    public void addOVProduct(Product product){
        if(!productenLijst.contains(product)){
            productenLijst.add(product);
        }
    }

    public void removeOVProduct(Product product){
        productenLijst.remove(product);
    }


    public String toString(){
        return "{ #" +  kaartNummer + ", " +geldigTot + ", " + klasse + ", €" + saldo + " #" + reizigerId + "}";
    }
}
