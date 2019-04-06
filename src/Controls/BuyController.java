/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import com.jfoenix.controls.JFXComboBox;
import helper.FxDialogs;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author OM EL NOUR
 */
public class BuyController implements Initializable {

    @FXML
    private TextField etBuyType;

    @FXML
    private TextField txtInvoiceNomber;
    @FXML
    private TextField etClientName;
    @FXML
    private TextField etProductName;
    @FXML
    private TextField etQuantity;
    @FXML
    private Label txtUnite;
    @FXML
    private TextField txtTotal;
    @FXML
    private TextField et_paid_up;
    @FXML
    private TextField et_remaining;
    @FXML
    private TextField etDiscount;
    @FXML
    private JFXComboBox<String> compo_priceType;
    @FXML
    private TableView<?> table;
    @FXML
    private TableColumn<?, ?> col_total;
    @FXML
    private TableColumn<?, ?> col_quantity;
    @FXML
    private TableColumn<?, ?> col_product_price;
    @FXML
    private TableColumn<?, ?> col_product_name;
    @FXML
    private TableColumn<?, ?> col_id;
    @FXML
    private JFXComboBox<String> compoFunctionType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        compo_priceType.getItems().addAll("قطاعى");
        compo_priceType.getItems().addAll("جملة");
        compo_priceType.getItems().addAll("شبة جملة");
        
        compoFunctionType.getItems().addAll("نقدى");
        compoFunctionType.getItems().addAll("آجل");
        
    }    

    @FXML
    private void btn_submit_buy(ActionEvent event) {
    }

    @FXML
    private void btn_totalBuy_click(ActionEvent event) {
    }

    @FXML
    private void compo_priceType_click(ActionEvent event) {
        String knownUsFrom = compo_priceType.getSelectionModel().getSelectedItem().toString();
        etBuyType.setText(knownUsFrom);
    }

    @FXML
    private void compoFunctionTypeClick(ActionEvent event) {
          String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
          if (knownUsFrom.equals("آجل")) {
              etClientName.setDisable(false);
              et_paid_up.setDisable(false);
              et_remaining.setDisable(false); 
              FxDialogs.showInformation("من فضلك", "ادخل اسم العميل والمدفوع والباقى ");
        }else if (knownUsFrom.equals("نقدى")) {
              etClientName.setDisable(true);
              et_paid_up.setDisable(true);
              et_remaining.setDisable(true);           
        }
          
          
    }
    
}
