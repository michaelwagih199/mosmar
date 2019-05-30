package helper;

import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.OrderPaymentDAO;
import dao.ProductDAO;
import entities.OrderDetail;
import entities.OrderPayment;
import entities.Orders;
import entities.Products;
import java.util.Date;

public class SubmitBuy_helper {

    OrderDAO orderDAO = new OrderDAO();
    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    OrderPaymentDAO orderPaymentDAO = new OrderPaymentDAO();
    ProductDAO productDAO = new ProductDAO();
    
    public void insert_order(int customerId, int paymentId,int categoryId, String orderType,String uuid) {
        // insert data to order
        
        try {
            Orders order = new Orders();
            order.setCustomerId(customerId);
            //order.setOrderDate(orderDate);
            order.setOrderType(orderType);
            order.setPaymentId(paymentId);
            order.setUuid(uuid);
            order.setCategoryId(categoryId);
            //order.setSuppliersId(supliersId);
            orderDAO.addOrders(order);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
    public void getOrderDetail(int customerId, int paymentId, String orderType) {

    }
    
     public void insertOrderDetails(int orderId, int productId , float price, float quantity , float total) {
             // insert data to OrderDetails        
        try {
            
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setPrice(price);
            orderDetail.setQuantity(quantity);
            orderDetail.setProductId(productId);
            orderDetail.setTotal(total);
  
            orderDetailDAO.addOrders(orderDetail);
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
   
     
      public void insertOrderPayment(int orderId, float totalCost,float paid , float remaining, String notes,float discount) {
        // insert data to OrderPayment
        try { 
            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setNotes(notes);
            orderPayment.setOrderId(orderId);
            orderPayment.setPaid(paid);
            orderPayment.setRemaining(remaining);
            orderPayment.setTotslCost(totalCost);
            orderPayment.setOrderDiscount(discount);
            
            orderPaymentDAO.addOrderPayment(orderPayment);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
      
           
}
