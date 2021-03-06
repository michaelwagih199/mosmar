package dao;

import entities.Products;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    public ObservableList<Products> selectProductByName(String productName) {
        List<Products> cars = new ArrayList<Products>();
        ObservableList<Products> row = FXCollections.observableArrayList();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();

        try {
            //SELECT o FROM Orders o WHERE o.customerId = :customerId
            String sql = "SELECT p FROM Products p WHERE p.productName = :productName";
            Query query = eman.createQuery(sql);
            query.setParameter("productName", productName);
            cars = query.getResultList();
            row.addAll(cars);
        } finally {

            eman.close();
            emf.close();
        }
        return row;

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
    
     public List<String> getProductsName(String productName) {
        List<String> cars = new ArrayList<String>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            //Query q = em.createQuery("Select O from Organization O where O.orgName LIKE '%:orgName%'");
            String sql = "SELECT p.productName FROM Products p WHERE p.productName LIKE '%:productName%'";
            Query query = eman.createQuery(sql);
            query.setParameter("productName", productName);
            cars = query.getResultList();
        } finally {

            eman.close();
            emf.close();
        }

        return cars;
    }
     

     
     

}
