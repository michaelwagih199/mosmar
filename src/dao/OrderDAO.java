package dao;

import entities.Customers;
import entities.Orders;
import entities.custom_orders;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jpaConntroller.OrdersJpaController;

public class OrderDAO {

    private final OrdersJpaController ordersJpaController;
    private EntityManagerFactory emf;

    public OrderDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        ordersJpaController = new OrdersJpaController(emf);
    }

    public void addOrders(Orders orders) {
        ordersJpaController.create(orders);
    }

    public void editOrders(Orders orders) throws Exception {

        ordersJpaController.edit(orders);
    }

    public void removeOrders(int id) throws Exception {
        ordersJpaController.destroy(id);
    }

    public ObservableList<Orders> getAllOrders() {
        ObservableList<Orders> row = FXCollections.observableArrayList();
        row.addAll(ordersJpaController.findOrdersEntities());
        return row;
    }

    public Orders getOrderById(int orderId) {
        return ordersJpaController.findOrders(orderId);
    }

    public int getCountOrder() {
        return ordersJpaController.getOrdersCount();
    }

    public int getLastOrderId(String UUID) {

        List<Integer> cars = new ArrayList<Integer>();
        int result = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();

        try {
            //SELECT o FROM Orders o WHERE o.customerId = :customerId
            String sql = "SELECT o.orderId FROM Orders o WHERE o.uuid = :uuid";
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

    public List<Object[]> getOrderByDate(Date startDate , Date endDate) {      
        List<Object[]> results;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        
        try {
            // select b.fname, b.lname from Users b JOIN Groups c where c.groupName = :groupName
            String sql = "SELECT o.orderId, o.orderDate, c.customerName ,o_p.totslCost,o_p.paid,o_p.remaining,o_p.orderDiscount ,o.paymentId,o.categoryId\n"
                    + " FROM Orders o left OUTER JOIN Customers c ON o.customerId = c.customerId JOIN OrderPayment o_p ON o.orderId=o_p.orderId\n"
                    + "  WHERE o.orderDate BETWEEN :startDate AND :endDate ";
            Query query = eman.createQuery(sql);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            results = query.getResultList();
            
        } finally {
            
            eman.close();
            emf.close();
        }
        return results;
    }

}
