package helper;

import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.OrderPaymentDAO;
import entities.OrderDetail;
import entities.OrderPayment;
import entities.Orders;
import java.util.Date;

public class SubmitBuy_helper {

    OrderDAO orderDAO = new OrderDAO();
    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    OrderPaymentDAO orderPaymentDAO = new OrderPaymentDAO();
    
    public void insert_order(int customerId,int supliersId,int paymentId,Date orderDate,String orderType) {
        // insert data to order
        try {           
            Orders order = new Orders();
            order.setCustomerId(customerId);
            order.setOrderDate(orderDate);
            order.setOrderType(orderType);
            order.setPaymentId(paymentId);
            order.setSuppliersId(supliersId);
            orderDAO.addOrders(order);
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
     public void insertOrderDetails(int orderId, int productId , float price, float quantity , float total, float discount) {
        // insert data to OrderDetails
        
        try {
            
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setDiscount(discount);
            orderDetail.setOrderId(orderId);
            orderDetail.setPrice(price);
            orderDetail.setQuantity(quantity);
            orderDetail.setProductId(productId);
            orderDetail.setTotal(total);
            orderDetail.setDiscount(discount);
 
            orderDetailDAO.addOrders(orderDetail);
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
     
      public void insertOrderPayment(int orderId, float totalCost,float paid , float remaining, String notes,Date date) {
        // insert data to OrderPayment
        try { 
            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setDate(date);
            orderPayment.setNotes(notes);
            orderPayment.setOrderId(orderId);
            orderPayment.setPaid(paid);
            orderPayment.setRemaining(remaining);
            orderPayment.setTotslCost(totalCost);
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
      
}
