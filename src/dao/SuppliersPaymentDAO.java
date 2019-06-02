package dao;
import entities.CustomersPayment;
import entities.Expenses;
import entities.OrderPayment;
import entities.Orders;
import entities.Products;
import entities.Supplierspayment;
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
import jpaConntroller.SupplierspaymentJpaController;

public class SuppliersPaymentDAO {
    private final SupplierspaymentJpaController supplierspaymentJpaController;
    private EntityManagerFactory emf;

    public SuppliersPaymentDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        supplierspaymentJpaController = new SupplierspaymentJpaController(emf);
    }

    public void addSupplierspayment(Supplierspayment supplierspayment) throws Exception {
        supplierspaymentJpaController.create(supplierspayment);
    }

    public void editSupplierspayment(Supplierspayment supplierspayment) throws Exception {
        supplierspaymentJpaController.edit(supplierspayment);
    }

    public void removeSupplierspayment(int id) throws Exception {
        supplierspaymentJpaController.destroy(id);
    }

  public ObservableList<Supplierspayment> getSupplierspayments(int suppliersId) {
        List<Supplierspayment> cars = new ArrayList<Supplierspayment>();
        ObservableList<Supplierspayment> row = FXCollections.observableArrayList();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT s FROM Supplierspayment s WHERE s.suppliersId = :suppliersId";
            Query query = eman.createQuery(sql);
            query.setParameter("suppliersId", suppliersId);
            cars = query.getResultList();
            row.addAll(cars);
        } finally {
            eman.close();
            emf.close();
        }
        return row;
    }

    public Supplierspayment getCustomersPaymentById(int Id) {
        return supplierspaymentJpaController.findSupplierspayment(Id);
    }
    
}
