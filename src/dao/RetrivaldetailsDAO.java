package dao;

import entities.BillsDetails;
import entities.Expenses;
import entities.OrderDetail;
import entities.OrderPayment;

import entities.Retrivaldetails;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaConntroller.ExpensesJpaController;
import jpaConntroller.OrderPaymentJpaController;
import jpaConntroller.RetrievalsJpaController;
import jpaConntroller.RetrivaldetailsJpaController;

public class RetrivaldetailsDAO {

    private final RetrivaldetailsJpaController retrivaldetailsJpaController;
    private EntityManagerFactory emf;

    public RetrivaldetailsDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        retrivaldetailsJpaController = new RetrivaldetailsJpaController(emf);
    }

    public void addRetrivaldetails(Retrivaldetails retrivaldetails) throws Exception {
        retrivaldetailsJpaController.create(retrivaldetails);
    }

    public void editRetrivaldetails(Retrivaldetails retrivaldetails) throws Exception {
        retrivaldetailsJpaController.edit(retrivaldetails);
    }

    public void removeRetrivaldetails(int id) throws Exception {
        retrivaldetailsJpaController.destroy(id);
    }

    public ObservableList<Retrivaldetails> getAllRetrivaldetails() {
        ObservableList<Retrivaldetails> row = FXCollections.observableArrayList();
        row.addAll(retrivaldetailsJpaController.findRetrivaldetailsEntities());
        return row;
    }

    public Retrivaldetails getRetrivaldetailsById(int Id) {
        return retrivaldetailsJpaController.findRetrivaldetails(Id);
    }

    public ObservableList<Retrivaldetails> getRetrivaldetailsId(int retrivalsId) {
        List<Retrivaldetails> cars = new ArrayList<Retrivaldetails>();
        ObservableList<Retrivaldetails> row = FXCollections.observableArrayList();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();

        try {
            //SELECT o FROM Orders o WHERE o.customerId = :customerId
            String sql = "SELECT r FROM Retrivaldetails r WHERE r.retrivalsId = :retrivalsId";
            Query query = eman.createQuery(sql);
            query.setParameter("retrivalsId", retrivalsId);
            cars = query.getResultList();
            row.addAll(cars);
        } finally {

            eman.close();
            emf.close();
        }
        return row;
    }

}
