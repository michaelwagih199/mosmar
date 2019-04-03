/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import com.jfoenix.controls.JFXButton;

import helper.Helper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author OM EL NOUR
 */
public class MainController implements Initializable {
    Helper helper = new Helper();
    
    @FXML
    private JFXButton btn_buy;
    @FXML
    private JFXButton btnBuy_not_payment;
    @FXML
    private JFXButton btn_accounts;
    @FXML
    private JFXButton btn_burshes;
    @FXML
    private JFXButton btn_Recall;
    @FXML
    private JFXButton btn_store;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnBuyClick(ActionEvent event) throws Exception {
//        Strore Store = new Strore();
//        Store.setProductName("test");
//        StoreDAO store = new StoreDAO();
//        store.addStore(Store);
    }

    @FXML
    private void btnBuy_not_paymentClick(ActionEvent event) throws Exception {
//        Strore Store = new Strore(1);
//        Store.setProductName("michael");
//        StoreDAO store = new StoreDAO();
//        store.editStore(Store);
    }

    @FXML
    private void btnAccountsClick(ActionEvent event) {
    }

    @FXML
    private void btnBurshesClick(ActionEvent event) {
        
    }

    @FXML
    private void btnRecallClick(ActionEvent event) {
    }

    @FXML
    private void btnStoreClick(ActionEvent event) throws IOException {
        
        helper.start("/mosmar/main.fxml","الصفحة الرئيسية");
        helper.close(btn_buy);
        
    }
    
}
