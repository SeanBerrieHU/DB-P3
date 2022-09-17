package data;

import application.AdresDAO;
import application.OVChipkaartDAO;
import application.ReizigerDAO;
import domain.Adres;
import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {

    private Connection conn;
    private ReizigerDAO rdao;

    public OVChipkaartDAOPsql(Connection connection) {
        this.conn = connection;
    }

    public void setRdao(ReizigerDAO rdao){
        this.rdao = rdao;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {

        try {

            PreparedStatement st = conn.prepareStatement("INSERT INTO ov_chipkaart (kaart_nummer,geldig_tot,klasse,saldo,reiziger_id) VALUES(?,?,?,?,?)");
            st.setInt(1, ovChipkaart.getKaartNummer());
            st.setDate(2, ovChipkaart.getGeldigTot());
            st.setInt(3, ovChipkaart.getKlasse());
            st.setDouble(4, ovChipkaart.getSaldo());
            st.setInt(5, ovChipkaart.getReizigerId());
            st.executeUpdate();
            st.close();

            return true;

        } catch(SQLException sqlex){
            System.err.println("OV Chipkaart niet successvol opgeslagen: " + sqlex);
            return false;
        }

    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {

            String query = "UPDATE ov_chipkaart SET geldig_tot=?,klasse=?,saldo=?,reiziger_id=? WHERE kaart_nummer=?";
            PreparedStatement st = conn.prepareStatement(query);

            st.setDate(1, ovChipkaart.getGeldigTot());
            st.setInt(2, ovChipkaart.getKlasse());
            st.setDouble(3, ovChipkaart.getSaldo());
            st.setDouble(4, ovChipkaart.getReizigerId());
            st.setInt(5, ovChipkaart.getKaartNummer());
            System.out.println(st);
            st.executeUpdate();

            st.close();
            return true;

        } catch(SQLException sqlex){
            System.err.println("OV Chipkaart niet successvol geupdate: " + sqlex);
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {

        try {

            String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, ovChipkaart.getKaartNummer());
            st.executeUpdate();
            st.close();

            return true;

        } catch(SQLException sqlex){
            System.err.println("OV Chipkaart niet successvol verwijderd: " + sqlex);
            return false;
        }

    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {

        try {

            List<OVChipkaart> OVChipkaarten = new ArrayList<>();

            String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, reiziger.getId());
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int kNr = rs.getInt("kaart_nummer");
                Date Gt = rs.getDate("geldig_tot");
                int Kl = rs.getInt("klasse");
                double Sd = rs.getDouble("saldo");
                int Gd = rs.getInt("reiziger_id");
                OVChipkaart ovChipkaart = new OVChipkaart(kNr,Gt,Kl,Sd,Gd);
                OVChipkaarten.add(ovChipkaart);
            }

            st.close();
            return OVChipkaarten;

        } catch(SQLException sqlex){
            System.err.println("Kon OV Chipkaart niet vinden met reiziger id: " + sqlex);
            return null;
        }
    }


}
