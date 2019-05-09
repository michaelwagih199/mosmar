
package dao;

import entities.OrderDetail;
import entities.Orders;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaConntroller.OrderDetailJpaController;


public class OrderDetailDAO {

    private final OrderDetailJpaController orderDetailJpaController;
    private EntityManagerFactory emf;

    public OrderDetailDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        orderDetailJpaController = new OrderDetailJpaController(emf);
    }

    public void addOrders(OrderDetail orderDetail) {
        orderDetailJpaController.create(orderDetail);
    }

    public void editOrders(OrderDetail orderDetail) throws Exception {
        orderDetailJpaController.edit(orderDetail);
    }

    public void removeOrders(int id) throws Exception {
        orderDetailJpaController.destroy(id);
    }

    public ObservableList<OrderDetail> getAllOrders() {
        ObservableList<OrderDetail> row = FXCollections.observableArrayList();
        row.addAll(orderDetailJpaController.findOrderDetailEntities());
        return row;
    }

    public OrderDetail getOrderById(int Id) {
        return orderDetailJpaController.findOrderDetail(Id);
    }

    public ObservableList<OrderDetail> getOrdersFromOrderId(int orderId) {
        List<OrderDetail> cars = new ArrayList<OrderDetail>();
        ObservableList<OrderDetail> row = FXCollections.observableArrayList();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();

        try {
            //SELECT o FROM Orders o WHERE o.customerId = :customerId
            String sql = "SELECT o FROM OrderDetail o WHERE o.orderId = :orderId";
            Query query = eman.createQuery(sql);
            query.setParameter("orderId", orderId);
            cars = query.getResultList();
            row.addAll(cars);
        } finally {

            eman.close();
            emf.close();
        }
        return row;
    }
        

}
