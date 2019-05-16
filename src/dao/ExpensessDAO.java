package dao;

import entities.Expenses;
import entities.OrderDetail;
import entities.OrderPayment;
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

public class ExpensessDAO {

    private final ExpensesJpaController expensesJpaController;
    private EntityManagerFactory emf;

    public ExpensessDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        expensesJpaController = new ExpensesJpaController(emf);
    }

    public void addexpenses(Expenses expenses) throws Exception {
        expensesJpaController.create(expenses);
    }

    public void editexpenses(Expenses expenses) throws Exception {
        expensesJpaController.edit(expenses);
    }

    public void removexpenses(int id) throws Exception {
        expensesJpaController.destroy(id);
    }

    public ObservableList<Expenses> getAllExpenses() {
        ObservableList<Expenses> row = FXCollections.observableArrayList();
        row.addAll(expensesJpaController.findExpensesEntities());
        return row;
    }

    public Expenses getExpensesById(int Id) {
        return expensesJpaController.findExpenses(Id);
    }
    
    


}
