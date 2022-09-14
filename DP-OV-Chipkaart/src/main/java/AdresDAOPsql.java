import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {

    private Connection conn;
    private ReizigerDAO rdao;

    public AdresDAOPsql(Connection conn){
        this.conn = conn;
    }

    public void setRdao(ReizigerDAO rdao){
        this.rdao = rdao;
    }

    @Override
    public boolean save(Adres adres){

        try {

            String query = "INSERT INTO adres (adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id) VALUES(?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, adres.getId());
            st.setString(2, adres.getPostcode());
            st.setString(3, adres.getHuisnummer());
            st.setString(4, adres.getStraat());
            st.setString(5, adres.getWoonplaats());
            st.setInt(6, adres.getReizigerId());

            st.executeUpdate();
            return true;

        } catch(SQLException sqlex){
            System.err.println("Adres niet opgeslagen: " + sqlex);
            return false;
        }

    }

    @Override
    public boolean update(Adres adres) {

        try {

            String query = "UPDATE adres SET adres_id=?, postcode=?, huisnummer=?, straat=?,woonplaats=?, reiziger_id=? WHERE adres_id=? AND reiziger_id=?";
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, adres.getId());
            st.setString(2, adres.getPostcode());
            st.setString(3, adres.getHuisnummer());
            st.setString(4, adres.getStraat());
            st.setString(5, adres.getWoonplaats());
            st.setInt(6, adres.getReizigerId());
            st.setInt(7, adres.getId());
            st.setInt(8, adres.getReizigerId());

            st.executeUpdate();
            return true;

        } catch(SQLException sqlex){
            System.err.println("Adres niet geupdate: " + sqlex);
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {

            String query = "DELETE FROM adres WHERE adres_id=? AND reiziger_id=?";
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, adres.getId());
            st.setInt(2, adres.getReizigerId());
            st.executeUpdate();
            return true;

        } catch(SQLException sqlex){
            System.err.println("Adres data niet successvol verwijderd: " + sqlex);
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {

        try {

            String query = "SELECT * FROM adres WHERE reiziger_id=?";
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, reiziger.getId());
            ResultSet rs = st.executeQuery();

            while (rs.next()){

                int id = rs.getInt("adres_id");
                String Pc = rs.getString("postcode");
                String Hn = rs.getString("huisnummer");
                String Str = rs.getString("straat");
                String Wp = rs.getString("woonplaats");
                int Rid = rs.getInt("reiziger_id");
                Adres adr = new Adres(id,Pc,Hn,Str,Wp,Rid);
                return adr;

            }

            return null;

        } catch(SQLException sqlex){
            System.err.println("Reiziger niet gevonden met id: " + sqlex);
            return null;
        }
    }


    @Override
    public Adres findById(int adresId) {

        try {

            String query = "SELECT * FROM adres WHERE adres_id=?";
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, adresId);
            ResultSet rs = st.executeQuery();


            Adres adres = null;

            while (rs.next()){
                int id = rs.getInt("adres_id");
                String Pc = rs.getString("postcode");
                String Hn = rs.getString("huisnummer");
                String Str = rs.getString("straat");
                String Wp = rs.getString("woonplaats");
                int Rid = rs.getInt("reiziger_id");

                adres = new Adres(id,Pc,Hn,Str,Wp,Rid);
            }

            return adres;

        } catch(SQLException sqlex){
            System.err.println("Adres niet gevonden met id: " + sqlex);
            return null;
        }
    }

    @Override
    public List<Adres> findAll() {

        List<Adres> adressen = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM adres");

            while (rs.next()) {
                int id = rs.getInt("adres_id");
                String Pc = rs.getString("postcode");
                String Hn = rs.getString("huisnummer");
                String Str = rs.getString("straat");
                String Wp = rs.getString("woonplaats");
                int Rid = rs.getInt("reiziger_id");
                Adres adres = new Adres(id, Pc, Hn, Str, Wp, Rid);
                adressen.add(adres);
            }

            return adressen;

        } catch (SQLException sqlex) {
            System.err.println("No adresses found: " + sqlex);
            return null;
        }
    }
}


