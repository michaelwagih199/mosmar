package dao;

import entities.Expenses;
import entities.OrderDetail;
import entities.OrderPayment;
import entities.Retrievals;
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

public class RetrievalsDAO {

    private final RetrievalsJpaController retrievalsJpaController;
    private EntityManagerFactory emf;

    public RetrievalsDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        retrievalsJpaController = new RetrievalsJpaController(emf);
    }

    public void addRetrievals(Retrievals Retrievals) throws Exception {
        retrievalsJpaController.create(Retrievals);
    }

    public void editRetrievals(Retrievals Retrievals) throws Exception {
        retrievalsJpaController.edit(Retrievals);
    }

    public void removeRetrievals(int id) throws Exception {
        retrievalsJpaController.destroy(id);
    }

    public ObservableList<Retrievals> getAllRetrievals() {
        ObservableList<Retrievals> row = FXCollections.observableArrayList();
        row.addAll(retrievalsJpaController.findRetrievalsEntities());
        return row;
    }

    public Retrievals getRetrievalsById(int Id) {
        return retrievalsJpaController.findRetrievals(Id);
    }

    public int getLastOrderId(String UUID) {
        List<Integer> cars = new ArrayList<Integer>();
        int result = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();

        try {
            //SELECT o FROM Orders o WHERE o.customerId = :customerId
            String sql = "SELECT r.retrievalId FROM Retrievals r WHERE r.uuid = :uuid";
            Query query = eman.createQuery(sql);
            query.setParameter("uuid", UUID);
            cars = query.getResultList();
            result = cars.get(0);
        } finally {

            eman.close();
            emf.close();
        }
        return result;
    }

    public Double getTotalRetrive() {
        Double result;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            //SELECT sum(r.billsValue)from retrievals r;
            String sql = "SELECT SUM(r.billsValue)from retrievals r";
            Query query = eman.createQuery(sql);
          
            result = (Double) query.getSingleResult();

        } finally {
            eman.close();
            emf.close();
        }
        return result;
    }

}
