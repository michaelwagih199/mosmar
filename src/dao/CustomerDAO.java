
package dao;

import entities.Customers;
import entities.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaConntroller.CustomersJpaController;

/**
 *
 * @author OM EL NOUR
 */
public class CustomerDAO {
    private final CustomersJpaController customersJpaController;
    private EntityManagerFactory emf;
    
    public CustomerDAO() {
     emf = Persistence.createEntityManagerFactory("MOSMARPU");
     customersJpaController = new CustomersJpaController(emf);
    }
    
 public void addCustomer(Customers customers){
     customersJpaController.create(customers);
 }
     
 public void editCustomer(Customers customers) throws Exception{
     
     customersJpaController.edit(customers);
 }
 
 public void removeCustomer(int id) throws Exception{
      customersJpaController.destroy(id);
 }
 
 public ObservableList<Customers> getAllCustomer(){   
      ObservableList<Customers> row = FXCollections.observableArrayList();
      row.addAll(customersJpaController.findCustomersEntities());
      return row;
 }
 
 

      
        
 }
 
 
 
    
    
    
    
  
