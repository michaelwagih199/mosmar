package dao;

import entities.Products;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaConntroller.ProductsJpaController;
import jpaConntroller.exceptions.NonexistentEntityException;

/**
 *
 * @author OM EL NOUR
 */
public class ProductDAO {

    private final ProductsJpaController productsController;
    private EntityManagerFactory emf;

    public ProductDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        productsController = new ProductsJpaController(emf);
    }

    public void addProduct(Products products) {

        productsController.create(products);
    }

    public void editProduct(Products products) throws Exception {
        productsController.edit(products);

    }

    public void removeProduct(int id) throws Exception {
        productsController.destroy(id);
    }

    public ObservableList<Products> getAllProducts() {
        ObservableList<Products> row = FXCollections.observableArrayList();
        row.addAll(productsController.findProductsEntities());
        return row;
    }

    public void selectProduct() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();

        try {

            String sql = "SELECT p FROM Products p";

            Query query = eman.createQuery(sql);
            List<Products> cars = query.getResultList();

//            for (Products product : cars) {
//                System.out.print( product.getProductName());
//                System.out.print( product.getGomelGomlaBuyPrice());
//                System.out.println(product.getPerchusePrice());
//            }
        } finally {

            eman.close();
            emf.close();
        }
        float cars;

    }

    /**
     * select list of product name
     *
     * @param ProductName
     * @return
     */
    public List<Products> getProductId(String ProductName) {

        List<Products> cars = new ArrayList<Products>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();

        try {

            String sql = "SELECT p FROM Products p WHERE p.productName = :productName";
            Query query = eman.createQuery(sql);
            query.setParameter("productName", ProductName);
            cars = query.getResultList();

        } finally {

            eman.close();
            emf.close();
        }

        return cars;
    }

    public Products getProductById(int Id) {
        return productsController.findProducts(Id);
    }

    public void updateWeight(float unitsWeightInStock, int productId) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();

        try {
            eman.getTransaction().begin();
            String sql = "update Products set unitsWeightInStock = :unitsWeightInStock WHERE productid = :productid";
            Query query = eman.createQuery(sql);
            query.setParameter("unitsWeightInStock", unitsWeightInStock);
            query.setParameter("productid", productId);
            query.executeUpdate();
            eman.getTransaction().commit();

        } finally {

            eman.close();
            emf.close();
        }

    }

    public Double getUnitsCapital() {
        Double result;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT SUM(p.unitsWeightInStock * p.perchusePrice) as Result FROM Products p";
            Query query = eman.createQuery(sql);
           
            result = (Double) query.getSingleResult();

        } finally {
            eman.close();
            emf.close();
        }
        return result;
    }
    
     public List<String> getProductsName() {
        List<String> cars = new ArrayList<String>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();

        try {

            String sql = "SELECT p.productName FROM Products p";
            Query query = eman.createQuery(sql);          
            cars = query.getResultList();
        } finally {

            eman.close();
            emf.close();
        }

        return cars;
    }

}
