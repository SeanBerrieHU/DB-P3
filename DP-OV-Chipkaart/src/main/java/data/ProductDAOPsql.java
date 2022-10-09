package data;

import application.ProductDAO;
import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {

    private Connection conn;
    private OVChipkaartDAOPsql ovdao;
    public ProductDAOPsql(Connection conn){
        this.conn = conn;
    }

    public void setOvdao(OVChipkaartDAOPsql ovdao) {
        this.ovdao = ovdao;
    }

    @Override
    public boolean save(Product product) throws SQLException {

        try {

            PreparedStatement st = conn.prepareStatement("INSERT INTO product(product_nummer,naam,beschrijving,prijs) VALUES(?,?,?,?)");
            st.setInt(1, product.getProductNummer());
            st.setString(2, product.getNaam());
            st.setString(3, product.getBeschrijving());
            st.setDouble(4, product.getPrijs());
            st.executeUpdate();


            List<OVChipkaart> OVChipkaarten = product.getOVChipkaarten();
            for(OVChipkaart ovChipkaart: OVChipkaarten){
                PreparedStatement st2 = conn.prepareStatement("INSERT INTO ov_chipkaart_product(kaart_nummer,product_nummer) VALUES(?,?)");
                st2.setInt(1, ovChipkaart.getKaartNummer());
                st2.setInt(2, product.getProductNummer());
                st2.executeUpdate();

                ovChipkaart.removeOVProduct(product);
            }

            st.close();
            return true;

        } catch(SQLException sqlex){
            System.err.println("Product niet successvol opgeslagen: " + sqlex);
            return false;
        }

    }

    @Override
    public boolean update(Product product){

        try {

            String query = "UPDATE product SET naam=?, beschrijving=?, prijs=? WHERE product_nummer=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, product.getNaam());
            st.setString(2, product.getBeschrijving());
            st.setDouble(3, product.getPrijs());
            st.setInt(4, product.getProductNummer());
            st.executeUpdate();
            st.close();


            List<Integer> OVChipkaartLijstDB = new ArrayList<>();
            String query2 = "SELECT * FROM ov_chipkaart_product WHERE product_nummer=?";
            PreparedStatement st2 = conn.prepareStatement(query2);
            st2.setInt(1, product.getProductNummer());
            ResultSet rs = st2.executeQuery();

            while (rs.next()) {
                int OVChipkaart = rs.getInt("kaart_nummer");
                OVChipkaartLijstDB.add(OVChipkaart);
            }

            st2.close();



            List<OVChipkaart> OVChipkaarten = product.getOVChipkaarten();
            for(OVChipkaart ovChipkaart: OVChipkaarten){

                ovChipkaart.addOVProduct(product);
                int OVChipkaartNummer = ovChipkaart.getKaartNummer();

                // Database doesn't contain OV Card -> Add card to database
                if(!OVChipkaartLijstDB.contains(OVChipkaartNummer)){
                    PreparedStatement st3 = conn.prepareStatement("INSERT INTO ov_chipkaart_product(kaart_nummer,product_nummer) VALUES(?,?)");
                    st3.setInt(1, ovChipkaart.getKaartNummer());
                    st3.setInt(2, product.getProductNummer());
                    st3.executeUpdate();
                    st3.close();
                }


                // Checks if card in database also is in OVChipkaartList -> (FALSE) -> delete product
                boolean cardFoundInOVList = false;
                for(int OVCkNr: OVChipkaartLijstDB){
                    if(OVCkNr == ovChipkaart.getKaartNummer()){
                        cardFoundInOVList = true;
                    }
                }

                if(!cardFoundInOVList){
                    PreparedStatement st4 = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE kaart_nummer=? AND product_nummer=?");
                    st4.setInt(1, ovChipkaart.getKaartNummer());
                    st4.setInt(2, product.getProductNummer());
                    st4.executeUpdate();
                    st4.close();
                }

            }

            return true;

        } catch(SQLException sqlex){
            System.err.println("Product niet successvol geupdate: " + sqlex);
            return false;
        }

    }

    @Override
    public boolean delete(Product product){

        try {

            List<OVChipkaart> OVChipkaarten = product.getOVChipkaarten();
            for(OVChipkaart ovChipkaart: OVChipkaarten){
                ovChipkaart.removeOVProduct(product);
            }

            int productNummer = product.getProductNummer();

            String OVProductQuery = "DELETE FROM ov_chipkaart_product o WHERE o.product_nummer = ?;";
            PreparedStatement OVProductQuerySt = conn.prepareStatement(OVProductQuery);
            OVProductQuerySt.setInt(1, productNummer);
            OVProductQuerySt.executeUpdate();
            OVProductQuerySt.close();

            String productQuery = "DELETE FROM product p WHERE p.product_nummer = ?;";
            PreparedStatement productSt = conn.prepareStatement(productQuery);
            productSt.setInt(1, productNummer);
            productSt.executeUpdate();
            productSt.close();

            return true;

        } catch(SQLException sqlex){
            System.err.println("Product niet successvol verwijderd: " + sqlex);
            return false;
        }

    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {

        try {

            List<Product> producten = new ArrayList<>();

            String query = "SELECT p.* FROM ov_chipkaart_product o\n" +
                    "INNER JOIN product p ON p.product_nummer = o.product_nummer\n" +
                    "WHERE o.kaart_nummer = ?;";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, ovChipkaart.getKaartNummer());
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int Pn = rs.getInt("product_nummer");
                String Nm = rs.getString("naam");
                String Bs = rs.getString("beschrijving");
                double Pr = rs.getDouble("prijs");

                Product product = new Product(Pn, Nm, Bs, Pr);
                producten.add(product);
                ovChipkaart.addOVProduct(product);
            }

            st.close();

            return producten;

        } catch(SQLException sqlex){
            System.err.println("Product niet successvol opgehaald met OV Chipkaart: " + sqlex);
            return null;
        }

    }

    @Override
    public List<Product> findAll() {
        try {

            List<Product> producten = new ArrayList<>();

            String query = "SELECT * FROM product";
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int Pn = rs.getInt("product_nummer");
                String Nm = rs.getString("naam");
                String Bs = rs.getString("beschrijving");
                double Pr = rs.getDouble("prijs");

                Product product = new Product(Pn, Nm, Bs, Pr);
                producten.add(product);

                List<OVChipkaart> OVChipkaarten = product.getOVChipkaarten();
                for(OVChipkaart ovChipkaart: OVChipkaarten){
                    ovChipkaart.addOVProduct(product);
                }
            }

            st.close();

            return producten;

        } catch(SQLException sqlex){
            System.err.println("Find all Product niet successvol uitgevoerd: " + sqlex);
            return null;
        }
    }

}
