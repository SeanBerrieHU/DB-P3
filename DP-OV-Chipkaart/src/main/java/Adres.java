public class Adres {
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reizigerId;

    public Adres(int id, String Pc, String Hn, String Str, String Wp, int Rid){
        this.id = id;
        this.postcode = Pc;
        this.huisnummer = Hn;
        this.straat = Str;
        this.woonplaats = Wp;
        this.reizigerId = Rid;
    }


    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }


    public int getId() {
        return id;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public int getReizigerId() {
        return reizigerId;
    }


    @Override
    public String toString(){
        return "Id: " + this.id + ", Postcode: " + this.postcode + ", Huisnummer: " + this.huisnummer + ", Straat: " + this.straat + ", Woonplaats: " + this.woonplaats + ", Reiziger_id: " + this.reizigerId;
    }
}
