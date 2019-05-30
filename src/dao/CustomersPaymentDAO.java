package dao;
import entities.CustomersPayment;
import entities.OrderPayment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

    public ObservableList<CustomersPayment> getCustomersPayment() {
        ObservableList<CustomersPayment> row = FXCollections.observableArrayList();
        row.addAll(customersPaymentJpaController.findCustomersPaymentEntities());
        return row;
    }

    public CustomersPayment getCustomersPaymentById(int Id) {
        return customersPaymentJpaController.findCustomersPayment(Id);
    }
    
}
