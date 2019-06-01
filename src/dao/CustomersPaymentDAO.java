package dao;
import entities.CustomersPayment;
import entities.Expenses;
import entities.OrderPayment;
import entities.Orders;
import entities.Products;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaConntroller.CustomersPaymentJpaController;
import jpaConntroller.OrderPaymentJpaController;

public class CustomersPaymentDAO {
    private final CustomersPaymentJpaController customersPaymentJpaController;
    private EntityManagerFactory emf;

    public CustomersPaymentDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        customersPaymentJpaController = new CustomersPaymentJpaController(emf);
    }

    public void addCustomersPayment(CustomersPayment customersPayment) throws Exception {
        customersPaymentJpaController.create(customersPayment);
    }

    public void editCustomersPayment(CustomersPayment customersPayment) throws Exception {
        customersPaymentJpaController.edit(customersPayment);
    }

    public void removeCustomersPayment(int id) throws Exception {
        customersPaymentJpaController.destroy(id);
    }

  public ObservableList<CustomersPayment> getCustomersPayments(int customerId) {
        List<CustomersPayment> cars = new ArrayList<CustomersPayment>();
        ObservableList<CustomersPayment> row = FXCollections.observableArrayList();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT c FROM CustomersPayment c WHERE c.customerId = :customerId";
            Query query = eman.createQuery(sql);
            query.setParameter("customerId", customerId);
            cars = query.getResultList();
            row.addAll(cars);
        } finally {
            eman.close();
            emf.close();
        }
        return row;
    }

    public CustomersPayment getCustomersPaymentById(int Id) {
        return customersPaymentJpaController.findCustomersPayment(Id);
    }
    
}
