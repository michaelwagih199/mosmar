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
  
  public double getDaySales(Date ordderDate) {
        List<Double> cars = new ArrayList<Double>();
        double result = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT SUM(o.paid) FROM OrderPayment o WHERE o.date = :date";
            Query query = eman.createQuery(sql);
            query.setParameter("date", ordderDate);
            cars = query.getResultList();
            result = cars.get(0);
        } finally {
            eman.close();
            emf.close();
        }
        return result;
    }
   public ObservableList<Expenses> getExpencseByDate(Date expenceDate) {
        List<Expenses> cars = new ArrayList<Expenses>();
        ObservableList<Expenses> row = FXCollections.observableArrayList();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT e FROM Expenses e WHERE e.expensesDate = :expensesDate";
            Query query = eman.createQuery(sql);
            query.setParameter("expensesDate", expenceDate);
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
    
    
      public double getDayExpenses(Date expensesDate) {
        List<Double> cars = new ArrayList<Double>();
        double result = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT SUM(e.expensesValue) FROM Expenses e WHERE e.expensesDate = :expensesDate";
            Query query = eman.createQuery(sql);
            query.setParameter("expensesDate", expensesDate);
            cars = query.getResultList();
            result = cars.get(0);
        } finally {
            eman.close();
            emf.close();
        }
        return result;
    }

}
