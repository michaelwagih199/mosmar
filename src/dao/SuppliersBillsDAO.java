package dao;

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
import jpaConntroller.CustomersJpaController;
import jpaConntroller.SuppliersBillsJpaController;
import jpaConntroller.SuppliersJpaController;


public class SuppliersBillsDAO {

    private final SuppliersBillsJpaController suppliersBillsJpaController;
    private EntityManagerFactory emf;

    public SuppliersBillsDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        suppliersBillsJpaController = new SuppliersBillsJpaController(emf);
    }

    public void addSuppliersBills(SuppliersBills suppliersBills) {
        suppliersBillsJpaController.create(suppliersBills);
    }

    public void editSuppliersBills(SuppliersBills suppliersBills) throws Exception {

        suppliersBillsJpaController.edit(suppliersBills);
    }

    public void removeSuppliersBills(int id) throws Exception {
        suppliersBillsJpaController.destroy(id);
    }

    public ObservableList<SuppliersBills> getSuppliersBills() {
        ObservableList<SuppliersBills> row = FXCollections.observableArrayList();
        row.addAll(suppliersBillsJpaController.findSuppliersBillsEntities());
        return row;
    }

   public SuppliersBills getSuppliersBillsById(int Id) {
        return suppliersBillsJpaController.findSuppliersBills(Id);
    }

}
