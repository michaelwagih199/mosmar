package dao;

import entities.Orders;
import entities.Productmappping;
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
    
     public String getSubProduct1(int productmainId) {
        List<String> cars = new ArrayList<String>();
        String result;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            //SELECT p.ProductName from Products p JOIN Productmappping pm ON p.Product_id = pm.subProductId_1 WHERE pm.productmainId = 39
            String sql = "SELECT p.productName from Products p JOIN Productmappping pm "
                    + "ON p.productid = pm.subProductId1 WHERE pm.productmainId = :productmainId";
            Query query = eman.createQuery(sql);
            query.setParameter("productmainId", productmainId);
            cars = query.getResultList();
            result = cars.get(0);
        } finally {

            eman.close();
            emf.close();
        }
        return result;
    }
    public String getSubProduct2(int productmainId) {
        List<String> cars = new ArrayList<String>();
        String result;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            //SELECT p.ProductName from Products p JOIN Productmappping pm ON p.Product_id = pm.subProductId_1 WHERE pm.productmainId = 39
            String sql = "SELECT p.productName from Products p JOIN Productmappping pm "
                    + "ON p.productid = pm.subProduct2 WHERE pm.productmainId = :productmainId";
            Query query = eman.createQuery(sql);
            query.setParameter("productmainId", productmainId);
            cars = query.getResultList();
            result = cars.get(0);
        } finally {

            eman.close();
            emf.close();
        }
        return result;
    }
     public String getSubProduct3(int productmainId) {
        List<String> cars = new ArrayList<String>();
        String result;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            //SELECT p.ProductName from Products p JOIN Productmappping pm ON p.Product_id = pm.subProductId_1 WHERE pm.productmainId = 39
            String sql = "SELECT p.productName from Products p JOIN Productmappping pm "
                    + "ON p.productid = pm.subProduct3 WHERE pm.productmainId = :productmainId";
            Query query = eman.createQuery(sql);
            query.setParameter("productmainId", productmainId);
            cars = query.getResultList();
            result = cars.get(0);
        } finally {

            eman.close();
            emf.close();
        }
        return result;
    }
     
     public String getSubProduct4(int productmainId) {
        List<String> cars = new ArrayList<String>();
        String result;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            //SELECT p.ProductName from Products p JOIN Productmappping pm ON p.Product_id = pm.subProductId_1 WHERE pm.productmainId = 39
            String sql = "SELECT p.productName from Products p JOIN Productmappping pm "
                    + "ON p.productid = pm.subProduct4 WHERE pm.productmainId = :productmainId";
            Query query = eman.createQuery(sql);
            query.setParameter("productmainId", productmainId);
            cars = query.getResultList();
            result = cars.get(0);
        } finally {

            eman.close();
            emf.close();
        }
        return result;
    }
     
     public int getIdFromMain(int productmainId) {
        List<Integer> cars = new ArrayList<Integer>();
        int result;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {

            String sql = "SELECT p.productMappingId from Productmappping p WHERE p.productmainId = :productmainId";                
            Query query = eman.createQuery(sql);
            query.setParameter("productmainId", productmainId);
            cars = query.getResultList();
            result = cars.get(0);
            
        } finally {
            eman.close();
            emf.close();
        }
        return result;
    }
     
     
}
