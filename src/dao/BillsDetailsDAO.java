package dao;

import entities.BillsDetails;
import entities.Customers;
import entities.Products;
import entities.Pyment;
import entities.Suppliers;
import entities.SuppliersBills;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaConntroller.BillsDetailsJpaController;
import jpaConntroller.CustomersJpaController;
import jpaConntroller.SuppliersBillsJpaController;
import jpaConntroller.SuppliersJpaController;


public class BillsDetailsDAO {

    private final BillsDetailsJpaController billsDetailsJpaController;
    private EntityManagerFactory emf;

    public BillsDetailsDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        billsDetailsJpaController = new BillsDetailsJpaController(emf);
    }

    public void addBillsDetails(BillsDetails billsDetails) {
        billsDetailsJpaController.create(billsDetails);
    }

    public void editBillsDetails(BillsDetails billsDetails) throws Exception {

        billsDetailsJpaController.edit(billsDetails);
    }

    public void removeBillsDetails(int id) throws Exception {
        billsDetailsJpaController.destroy(id);
    }

    public ObservableList<BillsDetails> getSuppliersBills() {
        ObservableList<BillsDetails> row = FXCollections.observableArrayList();
        row.addAll(billsDetailsJpaController.findBillsDetailsEntities());
        return row;
    }

   public BillsDetails getBillsDetailsById(int Id) {
        return billsDetailsJpaController.findBillsDetails(Id);
    }
   
   
     public ObservableList<BillsDetails> getBillsDetailsId(int suppliersBilsId) {
        List<BillsDetails> cars = new ArrayList<BillsDetails>();
        ObservableList<BillsDetails> row = FXCollections.observableArrayList();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();

        try {
            //SELECT o FROM Orders o WHERE o.customerId = :customerId
            String sql = "SELECT b FROM BillsDetails b WHERE b.suppliersBilsId = :suppliersBilsId";
            Query query = eman.createQuery(sql);
            query.setParameter("suppliersBilsId", suppliersBilsId);
            cars = query.getResultList();
            row.addAll(cars);
        } finally {

            eman.close();
            emf.close();
        }
        return row;
    }

}
