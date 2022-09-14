import java.sql.*;
import java.util.List;

public class Main {
    private static Connection connection;

    public static void main(String [] args) throws SQLException {

        try{

            String dbUrl = "jdbc:postgresql://localhost:5432/ovchip";
            String user = "postgres";
            String pass = "Kemker65!";

            connection = DriverManager.getConnection(dbUrl, user, pass);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from reiziger");

            while (rs.next()){
                System.out.println("#" + rs.getString("reiziger_id") +" "+ rs.getString("voorletters") +". "+ rs.getString("tussenvoegsel") +" "+ rs.getString("achternaam") +" ("+ rs.getString("geboortedatum")+ ")");
            }

           //rs.close();
           //st.close();
           //closeConnection();



            ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
            testReizigerDAO(rdao);

        } catch (SQLException sqlex){
            System.err.println("Reiziger informatie kan niet worden opgehaald: " + sqlex.getMessage());
        }

    }

    static Connection getConnection(){
        return connection;
    }

    private static void closeConnection() throws SQLException {
        try {
            connection.close();
        } catch (SQLException sqlex){
            System.err.println("Database connectie niet kunnen verbreken: " + sqlex.getMessage());
        }
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Update informatie in de database van bestaande reiziger
        String gbdatumUpdate = "2002-03-14";
        Reiziger sietskeUpdate = new Reiziger(77, "B", "", "Willemsen", java.sql.Date.valueOf(gbdatumUpdate));
        boolean r1 = rdao.update(sietskeUpdate);
        System.out.print("[Test] Updating id 77, update voltooid: " + r1 + "\n");

        // Verwijderd reiziger van de database
        boolean r2 = rdao.delete(sietskeUpdate);
        System.out.print("[Test] Deleting id 77, reiziger verwijderd: " + r2 + "\n");


        // Vind reiziger met reiziger_id
        Reiziger r4 = rdao.findById(3);
        System.out.print("[Test] Zoek reiziger met reiziger_id nummer 3: \n");
        System.out.println(r4);

        // Vind reiziger met geboortedatum
        List<Reiziger> r3 = rdao.findByGbDatum("2002-10-22");
        System.out.print("[Test] Zoek reiziger met geboortedatum 2002-10-22: \n");
        for(Reiziger reiziger: r3){
            System.out.println(reiziger);
        }
    }


    private void testAdresDAO(AdresDAO rdao) throws SQLException {

        System.out.println("------------------ TEST AdresDAO ---------------");


        System.out.println("------------------ TEST Save  ---------------");
        List<Adres> adressen = rdao.findAll();
        System.out.println("BEFORE SAVE:");
        System.out.println(adressen);

        Adres adres_1 = new Adres(1,"6367 BB");
        boolean response_save = rdao.save(adres_1);

        adressen = rdao.findAll();
        System.out.println("AFTER SAVE:");
        System.out.println(adressen);


        System.out.println("Save worked: " + response_save);

        //System.out.println("------------------ TEST Update  ---------------");
        //System.out.println("BEFORE SAVE:");
        //System.out.println(adressen);
        //boolean response_update = rdao.update();
//
//
        //System.out.println("------------------ TEST Delete  ---------------");
        //
        //boolean response_delete = rdao.delete();

    }


}
