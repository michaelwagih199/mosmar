package dao;

import entities.Orders;
import entities.Pyment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaConntroller.PymentJpaController;

public class PaymentDAO {
    
    private final PymentJpaController pymentJpaController;
    private EntityManagerFactory emf;
    
    public PaymentDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        pymentJpaController = new PymentJpaController(emf);
    }
    
    public void addOrders(Pyment pyment) {
        pymentJpaController.create(pyment);
    }
    
    public void editOrders(Pyment pyment) throws Exception {
        pymentJpaController.edit(pyment);
    }
    
    public void removeOrders(int id) throws Exception {
        pymentJpaController.destroy(id);
    }
    
    public ObservableList<Pyment> getAllOrders() {
        ObservableList<Pyment> row = FXCollections.observableArrayList();
        row.addAll(pymentJpaController.findPymentEntities());
        return row;
    }
    
    public Pyment getOrderById(int Id) {
        return pymentJpaController.findPyment(Id);
    }
    
}
