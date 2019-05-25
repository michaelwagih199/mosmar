package dao;

import entities.Orders;
import entities.Productmappping;
import entities.Products;
import entities.Productsnumber;
import entities.Pyment;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaConntroller.ProductmapppingJpaController;
import jpaConntroller.ProductsnumberJpaController;
import jpaConntroller.PymentJpaController;

public class ProductNumbersDAO {
    
    private final ProductsnumberJpaController productsnumberJpaController;
    private EntityManagerFactory emf;
    
    public ProductNumbersDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        productsnumberJpaController = new ProductsnumberJpaController(emf);
    }
    
    public void addProductsnumber(Productsnumber productsnumber) throws Exception {
        productsnumberJpaController.create(productsnumber);
    }
    
    public void editProductsnumber(Productsnumber productsnumber) throws Exception {
        productsnumberJpaController.edit(productsnumber);
    }
    
    public void removeProductsnumber(int id) throws Exception {
        productsnumberJpaController.destroy(id);
    }
    
    public ObservableList<Productsnumber> getAllProductsnumber() {
        ObservableList<Productsnumber> row = FXCollections.observableArrayList();
        row.addAll(productsnumberJpaController.findProductsnumberEntities());
        return row;
    }
    
    public Productsnumber getProductsnumberById(int Id) {
        return productsnumberJpaController.findProductsnumber(Id);
    }
    

}