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
}
