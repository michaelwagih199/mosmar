package dao;

import entities.OrderDetail;
import entities.OrderPayment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaConntroller.OrderPaymentJpaController;

public class OrderPaymentDAO {

    private final OrderPaymentJpaController orderPaymentJpaController;
    private EntityManagerFactory emf;

    public OrderPaymentDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        orderPaymentJpaController = new OrderPaymentJpaController(emf);
    }

    public void addOrderPayment(OrderPayment orderPayment) throws Exception {
        orderPaymentJpaController.create(orderPayment);
    }

    public void editOrderPayment(OrderPayment orderPayment) throws Exception {
        orderPaymentJpaController.edit(orderPayment);
    }

    public void removeOrderPayment(int id) throws Exception {
        orderPaymentJpaController.destroy(id);
    }

    public ObservableList<OrderPayment> getAllOrderPayment() {
        ObservableList<OrderPayment> row = FXCollections.observableArrayList();
        row.addAll(orderPaymentJpaController.findOrderPaymentEntities());
        return row;
    }

    public OrderPayment getOrderPaymentById(int Id) {
        return orderPaymentJpaController.findOrderPayment(Id);
    }

}
