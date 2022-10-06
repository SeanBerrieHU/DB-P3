package application;

import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {

    boolean save(Product product) throws SQLException;
    boolean update(Product product);
    boolean delete(Product product);
    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    List<Product> findAll();

}
