package helper;

import dao.ExpensessDAO;
import entities.Expenses;
import entities.Orders;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CalculasHelper {

    ExpensessDAO expensessDAO = new ExpensessDAO();

    public Double getDaySales(Date startDate, Date endDate) {
       Double  result;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT SUM(o.paid) FROM OrderPayment o WHERE o.date BETWEEN :startDate AND :endDate";
            Query query = eman.createQuery(sql);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            result = (Double) query.getSingleResult();

        } finally {
            eman.close();
            emf.close();
        }
        return result;
    }
    
     public Double getSumCustomerPaid() {
        Double  result;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT SUM(c.paymentValue) FROM CustomersPayment c";
            Query query = eman.createQuery(sql);
            result = (Double) query.getSingleResult();
            //System.out.println(result);
            
            
        } finally {
            eman.close();
            emf.close();
        }
        return result;
    }

    public ObservableList<Expenses> getExpencseByDate(Date startDate, Date endDate) {      
        List<Expenses> cars = new ArrayList<Expenses>();
        ObservableList<Expenses> row = FXCollections.observableArrayList();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT e FROM Expenses e WHERE e.expensesDate BETWEEN :startDate AND :endDate";
            Query query = eman.createQuery(sql);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            cars = query.getResultList();
            row.addAll(cars);
            
        } finally {
            eman.close();
            emf.close();
        }
        return row;
    }

    public void insertExpensess(Date expensesDate, String expensesContext, float expensesValue, String notes) {
        // insert data to order
        try {
            Expenses expenses = new Expenses();
            expenses.setExpensesContext(expensesContext);
            expenses.setExpensesDate(expensesDate);
            expenses.setExpensesValue(expensesValue);
            expenses.setNotes(notes);
            expensessDAO.addexpenses(expenses);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Float> getDayExpenses(Date startDate, Date endDate) {
        List<Float> cars = new ArrayList<Float>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT SUM(e.expensesValue) FROM Expenses e WHERE e.expensesDate BETWEEN :startDate AND :endDate";
            Query query = eman.createQuery(sql);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            cars = query.getResultList();

        } finally {
            eman.close();
            emf.close();
        }
        return cars;
    }

    public List<Float> getAccountsRevenue(Date startDate, Date endDate) {

        List<Float> cars = new ArrayList<Float>();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT SUM(o.remaining) FROM OrderPayment o WHERE o.date BETWEEN :startDate AND :endDate";
            Query query = eman.createQuery(sql);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            cars = query.getResultList();

        } finally {
            eman.close();
            emf.close();
        }
        return cars;
    }

}
