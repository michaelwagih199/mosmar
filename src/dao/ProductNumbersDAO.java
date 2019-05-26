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

    /**
     * select list of product name
     *
     * @param ProductName
     * @return
     */
    public List<Productsnumber> getProducNumbertId(String productnumberName) {

        List<Productsnumber> cars = new ArrayList<Productsnumber>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();

        try {
            //SELECT p FROM Productsnumber p WHERE p.productnumberid = :productnumberid
            String sql = "SELECT p FROM Productsnumber p WHERE p.productnumberName = :productnumberName";
            Query query = eman.createQuery(sql);
            query.setParameter("productnumberName", productnumberName);
            cars = query.getResultList();

        } finally {

            eman.close();
            emf.close();
        }

        return cars;
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

}
