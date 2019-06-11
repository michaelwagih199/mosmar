
package helper;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class SuppliersCalculas {
    
      public List<Float> getCustomersRemaining(int suppliersId) {
        List<Float> cars = new ArrayList<Float>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT sum(s.bilsRemaining)from SuppliersBills s WHERE s.suppliersId = :suppliersId";
            Query query = eman.createQuery(sql);
            query.setParameter("suppliersId", suppliersId);
            cars = query.getResultList();

        } finally {
            eman.close();
            emf.close();
        }
        return cars;
    }

    
    public List<Object[]> getCustomerOrders(int customerId) {
        List<Object[]> results;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql ="select o.orderId,o.orderDate,o_p.totslCost,o_p.remaining,o_p.paid,o_p.orderDiscount FROM Orders o JOIN OrderPayment o_p ON o.orderId = o_p.orderId WHERE o.customerId = :customerId";
            Query query = eman.createQuery(sql);
            query.setParameter("customerId", customerId);
            results = query.getResultList();
        } finally {
            eman.close();
            emf.close();
        }
        return results;
    }
       
    
     public List<Float> getSuppliersPayment(int suppliersId) {
        List<Float> cars = new ArrayList<Float>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = " SELECT SUM (s.paymentValue) FROM Supplierspayment s WHERE s.suppliersId =  :suppliersId ";
            Query query = eman.createQuery(sql);
            query.setParameter("suppliersId", suppliersId);
            cars = query.getResultList();

        } finally {
            eman.close();
            emf.close();
        }
        return cars;
    }
     
    
}
