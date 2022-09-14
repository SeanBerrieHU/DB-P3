public class Adres {
    private int id;
    private String postcode;

    public Adres(int id, String postcode){
        this.id = id;
        this.postcode = postcode;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString(){
        return this.id + " " + this.postcode;
    }
}
