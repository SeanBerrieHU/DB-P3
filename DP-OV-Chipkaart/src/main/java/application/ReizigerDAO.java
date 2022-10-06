package application;

import domain.Reiziger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {

    boolean save(Reiziger reiziger) throws SQLException;
    boolean update(Reiziger reiziger);
    boolean delete(Reiziger reiziger);
    Reiziger findById(int id);
    List<Reiziger> findByGbDatum(Date gbdatum);
    List<Reiziger> findAll();

}
