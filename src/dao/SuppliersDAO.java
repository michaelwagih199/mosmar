package dao;

import entities.Customers;
import entities.Products;
import entities.Suppliers;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaConntroller.CustomersJpaController;
import jpaConntroller.SuppliersJpaController;

/**
 *
 * @author OM EL NOUR
 */
public class SuppliersDAO {

    private final SuppliersJpaController suppliersJpaController;
    private EntityManagerFactory emf;

    public SuppliersDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        suppliersJpaController = new SuppliersJpaController(emf);
    }

    public void addCustomer(Suppliers suppliers) {
        suppliersJpaController.create(suppliers);
    }

    public void editCustomer(Suppliers suppliers) throws Exception {

        suppliersJpaController.edit(suppliers);
    }

    public void removeCustomer(int id) throws Exception {
        suppliersJpaController.destroy(id);
    }

    public ObservableList<Suppliers> getAllSuppliers() {
        ObservableList<Suppliers> row = FXCollections.observableArrayList();
        row.addAll(suppliersJpaController.findSuppliersEntities());
        return row;
    }

    public Suppliers getSuppliersById(int orderId) {
        return suppliersJpaController.findSuppliers(orderId);
    }


    public int getSupplierId(String supplierName) {
        List<Integer> cars = new ArrayList<Integer>();
        int result = 0;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();

        try {
            //SELECT s FROM Suppliers s WHERE s.supplierid = :supplierid
            String sql = "SELECT s.supplierid FROM Suppliers s WHERE s.supplierName = :supplierName";
            Query query = eman.createQuery(sql);
            query.setParameter("supplierName", supplierName);
            cars = query.getResultList();
            result = cars.get(0);
        } finally {

            eman.close();
            emf.close();
        }
        return result;
    }

}
