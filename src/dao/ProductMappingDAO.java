package dao;

import entities.Orders;
import entities.Productmappping;
import entities.Pyment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaConntroller.ProductmapppingJpaController;
import jpaConntroller.PymentJpaController;

public class ProductMappingDAO {
    
    private final ProductmapppingJpaController productmapppingJpaController;
    private EntityManagerFactory emf;
    
    public ProductMappingDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        productmapppingJpaController = new ProductmapppingJpaController(emf);
    }
    
    public void addProductmappping(Productmappping productmappping) throws Exception {
        productmapppingJpaController.create(productmappping);
    }
    
    public void editProductmappping(Productmappping productmappping) throws Exception {
        productmapppingJpaController.edit(productmappping);
    }
    
    public void removeProductmappping(int id) throws Exception {
        productmapppingJpaController.destroy(id);
    }
    
    public ObservableList<Productmappping> getAllProductmappping() {
        ObservableList<Productmappping> row = FXCollections.observableArrayList();
        row.addAll(productmapppingJpaController.findProductmapppingEntities());
        return row;
    }
    
    public Productmappping getProductmapppingById(int Id) {
        return productmapppingJpaController.findProductmappping(Id);
    }
    
}
