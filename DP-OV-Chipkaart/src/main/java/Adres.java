public class Adres {
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private Reiziger reiziger;

    public Adres(int id, String Pc, String Hn, String Str, String Wp, Reiziger Rid){
        this.id = id;
        this.postcode = Pc;
        this.huisnummer = Hn;
        this.straat = Str;
        this.woonplaats = Wp;
        this.reiziger = Rid;
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

    public Reiziger getReiziger() {
        return reiziger;
    }

    @Override
    public String toString(){
        return "Adres {#" + this.id + " " + this.postcode + " " + this.huisnummer + " " + this.straat + " " + this.woonplaats + "}";
    }
}
