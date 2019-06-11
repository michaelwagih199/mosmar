package dao;

import entities.Assets;
import entities.Expenses;
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
import jpaConntroller.AssetsJpaController;
import jpaConntroller.ExpensesJpaController;
import jpaConntroller.OrderPaymentJpaController;

public class AssetsDAO {

    private final AssetsJpaController assetsJpaController;
    private EntityManagerFactory emf;

    public AssetsDAO() {
        emf = Persistence.createEntityManagerFactory("MOSMARPU");
        assetsJpaController = new AssetsJpaController(emf);
    }

    public void addAssets(Assets assets) throws Exception {
        assetsJpaController.create(assets);
    }

    public void editAssets(Assets Assets) throws Exception {
        assetsJpaController.edit(Assets);
    }

    public void removAssets(int id) throws Exception {
        assetsJpaController.destroy(id);
    }

    public ObservableList<Assets> getAllAssets() {
        ObservableList<Assets> row = FXCollections.observableArrayList();
        row.addAll(assetsJpaController.findAssetsEntities());
        return row;
    }

    public Assets getExpensesById(int Id) {
        return assetsJpaController.findAssets(Id);
    }

    public Double getAssetsValueCapital() {
        Double result;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MOSMARPU");
        EntityManager eman = emf.createEntityManager();
        try {
            String sql = "SELECT SUM(a.assetsValue)from Assets a";
            Query query = eman.createQuery(sql);
            result = (Double) query.getSingleResult();

        } finally {
            eman.close();
            emf.close();
        }
        return result;
    }

}
