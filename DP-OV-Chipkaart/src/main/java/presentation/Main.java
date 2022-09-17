package presentation;

import application.AdresDAO;
import application.OVChipkaartDAO;
import application.ReizigerDAO;
import data.AdresDAOPsql;
import data.OVChipkaartDAOPsql;
import data.ReizigerDAOPsql;
import domain.Adres;
import domain.OVChipkaart;
import domain.Reiziger;

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
            AdresDAOPsql adao = new AdresDAOPsql(connection);
            OVChipkaartDAOPsql ovdao = new OVChipkaartDAOPsql(connection);

            rdao.setAdao(adao);
            rdao.setOvdao(ovdao);
            adao.setRdao(rdao);
            ovdao.setRdao(rdao);

            testReizigerDAO(rdao);
            testAdresDAO(adao, rdao);
            testOVKaartDAO(ovdao,rdao);

        } catch (SQLException sqlex){
            System.err.println("domain.Reiziger informatie kan niet worden opgehaald: " + sqlex.getMessage());
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


        System.out.println("\n---------- Test application.ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] application.ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na application.ReizigerDAO.save() ");
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


    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {

        System.out.println("------------------ TEST application.AdresDAO ---------------");


        String gbdatumUpdate = "2002-03-14";
        Reiziger reiziger = new Reiziger(100, "B", "", "Willemsen", java.sql.Date.valueOf(gbdatumUpdate));

        // bewerk
        rdao.save(reiziger);

        Adres adres_1 = new Adres(100, "6367BB", "20", "Hogeweg", "Voerendaal", reiziger);

        reiziger.addAdres(adres_1);

        System.out.println("------------------ TEST Save  ---------------");

        boolean response_save = adao.save(adres_1);
        System.out.println("Save successfull: " + response_save);


        System.out.println("------------------ TEST Findby reiziger id  ---------------");
        Adres adresByReizigerId = adao.findByReiziger(reiziger);
        System.out.print("[Test] Zoek reiziger met reiziger_id nummer 2: " + adresByReizigerId + "\n");


        System.out.println("------------------ TEST Update  ---------------");
        Adres adres_1_update = new Adres(100, "3553EW", "29", "Sybrantshof", "Utrecht", reiziger);
        boolean response_update = adao.update(adres_1_update);
        System.out.println("Update successfull: " + response_update);


        System.out.println("------------------ TEST Delete  ---------------");
        boolean adres_1_delete = adao.delete(adres_1_update);
        System.out.println("Delete adres successfull: " + adres_1_delete);


        List<Adres> alleAdressen = adao.findAll();
        for (Adres a : alleAdressen) {
            System.out.println(a.toString());
        }

        List<Reiziger> alleReizigers = rdao.findAll();
        for (Reiziger r : alleReizigers) {
            System.out.println(r.toString());
        }
    }

    private static void testOVKaartDAO(OVChipkaartDAO ovdao, ReizigerDAO rdao) throws SQLException {

        Reiziger reiziger = new Reiziger(200, "P", "van", "Willemsen", java.sql.Date.valueOf("2025-01-25"));
        rdao.save(reiziger);

        System.out.println("------------------ TEST Save OVCHIPKAART ---------------");
        OVChipkaart nieuweOVChipkaart = new OVChipkaart(1234567890, java.sql.Date.valueOf("2030-01-15"), 1, 30.25, 200);
        boolean save = ovdao.save(nieuweOVChipkaart);
        OVChipkaart nieuweOVChipkaart2 = new OVChipkaart(100000000, java.sql.Date.valueOf("2030-12-15"), 2, 12, 200);
        OVChipkaart nieuweOVChipkaart3 = new OVChipkaart(200000000, java.sql.Date.valueOf("2022-07-30"), 1, 950, 200);
        OVChipkaart nieuweOVChipkaart4 = new OVChipkaart(300000000, java.sql.Date.valueOf("2030-05-12"), 1, 0, 200);
        boolean save2 = ovdao.save(nieuweOVChipkaart2);
        boolean save3 = ovdao.save(nieuweOVChipkaart3);
        boolean save4 = ovdao.save(nieuweOVChipkaart4);

        System.out.println("OV-Kaart successvol opgeslagen: " + save);
        System.out.println("OV-Kaart successvol opgeslagen: " + save2);

        System.out.println("------------------ TEST Update OVCHIPKAART  ---------------");
        OVChipkaart nieuweOVChipkaart_update = new OVChipkaart(1234567890, java.sql.Date.valueOf("2025-01-25"), 2, 50, 200);
        boolean update = ovdao.update(nieuweOVChipkaart_update);
        System.out.println("OV-Kaart successvol geupdate: " + update);

        System.out.println("------------------ TEST Delete OVCHIPKAART  ---------------");
        boolean delete = ovdao.delete(nieuweOVChipkaart_update);
        System.out.println("OV-Kaart successvol verwijderd: " + delete);

        System.out.println("------------------ TEST FindByReiziger ---------------");

        List<OVChipkaart> kaarten = ovdao.findByReiziger(reiziger);
        for(OVChipkaart ovkaart: kaarten){
            System.out.println(ovkaart);
        }

        rdao.update(reiziger);
        System.out.println(reiziger);

    }


}
