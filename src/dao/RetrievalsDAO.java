
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

}
