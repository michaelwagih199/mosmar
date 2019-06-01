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

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnBuyClick(ActionEvent event) throws Exception {

        helper.start("/mosmar/buy.fxml", "صفحة البيع");
        helper.close(btn_buy);
    }


    @FXML
    private void btnAccountsClick(ActionEvent event) throws IOException{
         helper.start("/mosmar/acountsCenter.fxml", "المخزن");
         helper.close(btn_buy);
    }

    @FXML
    private void btnBurshesClick(ActionEvent event) throws IOException {
        
        helper.start("/mosmar/suppliers.fxml", "المخزن");
        helper.close(btn_buy);
        
    }

    @FXML
    private void btnRecallClick(ActionEvent event) {
    }

    @FXML
    private void btnStoreClick(ActionEvent event) throws IOException {

        helper.start("/mosmar/stock.fxml", "المخزن");
        helper.close(btn_buy);

    }

    @FXML
    private void btn_customersClick(ActionEvent event) throws IOException {
        helper.start("/mosmar/customers.fxml", "حساب العملاء");
        helper.close(btn_buy);
    }

}
