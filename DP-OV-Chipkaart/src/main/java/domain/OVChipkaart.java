package domain;

import java.sql.Date;

public class OVChipkaart {

    private int kaartNummer;
    private Date geldigTot;
    private int klasse;
    private double saldo;
    private int reizigerId;

    public OVChipkaart(int Kn, Date Gt, int Kl, double Sd, int Rid){
        this.kaartNummer = Kn;
        this.geldigTot = Gt;
        this.klasse = Kl;
        this.saldo = Sd;
        this.reizigerId = Rid;
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

    public String toString(){
        return "{ #" +  kaartNummer + ", " +geldigTot + ", " + klasse + ", €" + saldo + " #" + reizigerId + "}";
    }
}
