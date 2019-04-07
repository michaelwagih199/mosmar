package dao;

import entities.Customers;
import entities.Orders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

}
