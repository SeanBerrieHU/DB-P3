package application;

import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {

    boolean save(OVChipkaart ovChipkaart) throws SQLException;
    boolean update(OVChipkaart ovChipkaart);
    boolean delete(OVChipkaart ovChipkaart);
    List<OVChipkaart> findById(int r);
    List<OVChipkaart> findByGbdatum(Date gbdatum);
    List<OVChipkaart> findByReiziger(Reiziger reiziger);
    List<OVChipkaart> findAll();
}
