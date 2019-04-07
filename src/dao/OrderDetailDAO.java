
package dao;

import entities.OrderDetail;
import entities.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

}
