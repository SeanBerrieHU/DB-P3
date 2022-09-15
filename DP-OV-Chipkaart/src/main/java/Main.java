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

            rdao.setAdao(adao);
            adao.setRdao(rdao);

            testReizigerDAO(rdao);
            testAdresDAO(adao, rdao);

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


    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {

        System.out.println("------------------ TEST AdresDAO ---------------");


        String gbdatumUpdate = "2002-03-14";
        Reiziger reiziger = new Reiziger(100, "B", "", "Willemsen", java.sql.Date.valueOf(gbdatumUpdate));

        // bewerk
        rdao.save(reiziger);

        Adres adres_1 = new Adres(100,"6367BB", "20", "Hogeweg", "Voerendaal", reiziger);

        reiziger.addAdres(adres_1);

        System.out.println("------------------ TEST Save  ---------------");

        boolean response_save = adao.save(adres_1);
        System.out.println("Save successfull: " + response_save);


        System.out.println("------------------ TEST Findby reiziger id  ---------------");
        Adres adresByReizigerId = adao.findByReiziger(reiziger);
        System.out.print("[Test] Zoek reiziger met reiziger_id nummer 2: " + adresByReizigerId + "\n");


        System.out.println("------------------ TEST Update  ---------------");
        Adres adres_1_update = new Adres(100,"3553EW", "29", "Sybrantshof", "Utrecht", reiziger);
        boolean response_update = adao.update(adres_1_update);
        System.out.println("Update successfull: " + response_update);


        System.out.println("------------------ TEST Delete  ---------------");
        boolean adres_1_delete = adao.delete(adres_1_update);
        System.out.println("Delete adres successfull: " + adres_1_delete);


        List<Adres> alleAdressen = adao.findAll();
        for(Adres a: alleAdressen){
            System.out.println(a.toString());
        }

        List<Reiziger> alleReizigers = rdao.findAll();
        for(Reiziger r: alleReizigers){
            System.out.println(r.toString());
        }



    }


}
