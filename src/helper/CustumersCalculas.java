package helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class CustumersCalculas {

    public Double getCustomersRemaining(int customerId) {
        Double result;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT SUM(o_p.remaining) from OrderPayment o_p JOIN Orders o on o_p.orderId = o.orderId JOIN Customers c on o.customerId = c.customerId WHERE c.customerId = :customerId";
            Query query = eman.createQuery(sql);
            query.setParameter("customerId", customerId);
            result = (Double) query.getSingleResult();
        } finally {
            eman.close();
            emf.close();
        }
        return result;
    }

    public List<Object[]> getCustomerOrders(int customerId) {
        List<Object[]> results;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "select o.orderId,o.orderDate,o_p.totslCost,o_p.remaining,o_p.paid,o_p.orderDiscount FROM Orders o JOIN OrderPayment o_p ON o.orderId = o_p.orderId WHERE o.customerId = :customerId";
            Query query = eman.createQuery(sql);
            query.setParameter("customerId", customerId);
            results = query.getResultList();
        } finally {
            eman.close();
            emf.close();
        }
        return results;
    }

    public Double getCustomersPayment(int customerId) {
        Double result;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT SUM(c.paymentValue) FROM CustomersPayment c WHERE c.customerId =  :customerId ";
            Query query = eman.createQuery(sql);
            query.setParameter("customerId", customerId);
            result = (Double) query.getSingleResult();

        } finally {
            eman.close();
            emf.close();
        }
        return result;
    }

    public Double getCustomersPaymentRetrive(int customerId) {
        Double result;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            //SELECT sum(r.billsValue)from retrievals r;
            String sql = "SELECT SUM(r.billsValue)from Retrievals r WHERE r.customerId = :customerId";
            Query query = eman.createQuery(sql);
            query.setParameter("customerId", customerId);
            result = (Double) query.getSingleResult();

        } finally {
            eman.close();
            emf.close();
        }
        return result;
    }

}
