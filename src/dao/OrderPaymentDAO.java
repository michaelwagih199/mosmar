package dao;

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
    
    
     public void updateStock(float unitsInStock, int productId) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            eman.getTransaction().begin();
            //"SELECT p FROM Productsnumber p WHERE p.unitsInStock = :unitsInStock"
            String sql = "update Productsnumber set unitsInStock = :unitsInStock WHERE productnumberid = :productnumberid";
            Query query = eman.createQuery(sql);
            query.setParameter("unitsInStock", unitsInStock);
            query.setParameter("productnumberid", productId);
            query.executeUpdate();
            eman.getTransaction().commit();

        } finally {
            eman.close();
            emf.close();
        }

    }
    


}
