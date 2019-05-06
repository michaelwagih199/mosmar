package dao;

import entities.Customers;
import entities.Orders;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
    
    public int getLastOrderId() {
        
        List<Integer> cars = new ArrayList<Integer>();
        int result = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();

        try {

            String sql = "SELECT o.orderId FROM Orders o order by o.orderId";
            Query query = eman.createQuery(sql);
         
            cars = query.getResultList();
            result = cars.get(0);
        } finally {

            eman.close();
            emf.close();
        }

        return result;
    }

}
