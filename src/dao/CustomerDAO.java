package dao;

import entities.Customers;
import entities.Products;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaConntroller.CustomersJpaController;

/**
 *
 * @author OM EL NOUR
 */
public class CustomerDAO {

    private final CustomersJpaController customersJpaController;
    private EntityManagerFactory emf;

    public CustomerDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        customersJpaController = new CustomersJpaController(emf);
    }

    public void addCustomer(Customers customers) {
        customersJpaController.create(customers);
    }

    public void editCustomer(Customers customers) throws Exception {

        customersJpaController.edit(customers);
    }

    public void removeCustomer(int id) throws Exception {
        customersJpaController.destroy(id);
    }

    public ObservableList<Customers> getAllCustomer() {
        ObservableList<Customers> row = FXCollections.observableArrayList();
        row.addAll(customersJpaController.findCustomersEntities());
        return row;
    }
     public Customers getCustomersById(int Id) {
        return customersJpaController.findCustomers(Id);
    }
    
    
        public int getcCustomerId(String CustomerName) {
        List<Integer> cars = new ArrayList<Integer>();
        int result = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();

        try {

            String sql = "SELECT c.customerId FROM Customers c WHERE c.customerName = :customerName";
            Query query = eman.createQuery(sql);
            query.setParameter("customerName", CustomerName);
            cars = query.getResultList();
            result = cars.get(0);
        } finally {

            eman.close();
            emf.close();
        }
        return result;
    }

    public boolean getAllCustomer(String michael) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
