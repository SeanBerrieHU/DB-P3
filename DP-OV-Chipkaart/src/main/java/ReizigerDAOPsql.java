import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


 public class ReizigerDAOPsql implements ReizigerDAO {

    private Connection conn;
    private AdresDAO rdao;

    public ReizigerDAOPsql(Connection conn){
        this.conn = conn;
    }

    public void setRdao(AdresDAO rdao){
        this.rdao = rdao;
    }

    @Override
    public boolean save(Reiziger r) throws SQLException {

        try {

            PreparedStatement st = conn.prepareStatement("INSERT INTO reiziger (reiziger_id,voorletters,tussenvoegsel,achternaam,geboortedatum) VALUES(?,?,?,?,?)");
            st.setInt(1, r.getId());
            st.setString(2, r.getVoorletters());
            st.setString(3, r.getTussenvoegsel());
            st.setString(4, r.getAchternaam());
            st.setDate(5, r.getGeboorteDatum());
            st.executeUpdate();
            return true;

        } catch(SQLException sqlex){
            System.err.println("Reiziger data niet successvol opgeslagen: " + sqlex);
            return false;
        }

    }

    @Override
    public boolean update(Reiziger r){

        try {

            PreparedStatement st = conn.prepareStatement("UPDATE reiziger SET reiziger_id=?, voorletters=?, tussenvoegsel=?, achternaam=?,geboortedatum=? WHERE reiziger_id=?");
            st.setInt(1, r.getId());
            st.setString(2, r.getVoorletters());
            st.setString(3, r.getTussenvoegsel());
            st.setString(4, r.getAchternaam());
            st.setDate(5, r.getGeboorteDatum());
            st.setInt(6, r.getId());
            st.executeUpdate();
            return true;

        } catch(SQLException sqlex){
            System.err.println("Reiziger data niet successvol geupdate: " + sqlex);
            return false;
        }

    }

    @Override
    public boolean delete(Reiziger r){

        try {

            PreparedStatement st = conn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id=?");
            st.setInt(1, r.getId());
            st.executeUpdate();
            return true;

        } catch(SQLException sqlex){
            System.err.println("Reiziger data niet successvol verwijderd: " + sqlex);
            return false;
        }

    }

    @Override
    public Reiziger findById(int id){

        try {

            Reiziger reiziger = null;

            String query = "SELECT * FROM reiziger WHERE reiziger_id=" + id;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                int Rid = rs.getInt("reiziger_id");
                String Vl = rs.getString("voorletters");
                String Tv = rs.getString("tussenvoegsel");
                String An = rs.getString("achternaam");
                Date Gd = rs.getDate("geboortedatum");

                reiziger = new Reiziger(Rid, Vl, Tv, An, Gd);

            }

            return reiziger;

        } catch(SQLException sqlex){
            System.err.println("Reiziger niet gevonden met ID: " + sqlex);
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbDatum(String datum){

        List<Reiziger> reizigerLijst = new ArrayList<>();

        try {


            String query = "SELECT * FROM reiziger WHERE geboortedatum=" + "\'" + java.sql.Date.valueOf(datum) + "\'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){

                int Rid = rs.getInt("reiziger_id");
                String Vl = rs.getString("voorletters");
                String Tv = rs.getString("tussenvoegsel");
                String An = rs.getString("achternaam");
                Date Gd = rs.getDate("geboortedatum");

                Reiziger reiziger = new Reiziger(Rid, Vl, Tv, An, Gd);
                reizigerLijst.add(reiziger);

            }

            return reizigerLijst;

        } catch(SQLException sqlex){
            System.err.println("Reiziger niet gevonden met datum: " + sqlex);
            return null;
        }

    }

    @Override
    public List<Reiziger> findAll(){

        List<Reiziger> reizigerLijst = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM reiziger");

            while (rs.next()){
                int id = rs.getInt("reiziger_id");
                String Vl = rs.getString("voorletters");
                String Tv = rs.getString("tussenvoegsel");
                String An = rs.getString("achternaam");
                Date Gd = rs.getDate("geboortedatum");
                Reiziger reiziger = new Reiziger(id, Vl, Tv, An, Gd);
                reizigerLijst.add(reiziger);
            }

            return reizigerLijst;

        } catch(SQLException sqlex){
            System.err.println("Reiziger niet gevonden met ID: " + sqlex);
            return null;
        }

    }

}
