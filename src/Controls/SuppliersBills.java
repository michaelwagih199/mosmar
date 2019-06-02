/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author OM EL NOUR
 */
public class SuppliersBills implements Initializable {

    @FXML
    private TableView<?> table;
    @FXML
    private TableColumn<?, ?> col_all_quantity;
    @FXML
    private TableColumn<?, ?> col_paid;
    @FXML
    private TableColumn<?, ?> col_rest;
    @FXML
    private TableColumn<?, ?> col_quantity;
    @FXML
    private TableColumn<?, ?> col_all_quantity1;
    @FXML
    private TableColumn<?, ?> col_date;
    @FXML
    private TableColumn<?, ?> col_date1;
    @FXML
    private JFXButton btn_add;
    @FXML
    private JFXTextField etSuppliersId;
    @FXML
    private JFXTextField etTotalBils;
    @FXML
    private JFXTextField etPaidBils;
    @FXML
    private JFXTextField etRemainig;
    @FXML
    private DatePicker bilsDate;
    @FXML
    private JFXTextArea etNotes;
    @FXML
    private TableView<?> tableProducts;
    @FXML
    private TableColumn<?, ?> col_productId;
    @FXML
    private TableColumn<?, ?> col_productName;
    @FXML
    private TableColumn<?, ?> colproductQuantity;
    @FXML
    private TableColumn<?, ?> colPrice;
    @FXML
    private TableColumn<?, ?> coltotal;
    @FXML
    private TableColumn<?, ?> colProducttype;
    @FXML
    private JFXTextField etProduct;
    @FXML
    private JFXTextField etQuantity;
    @FXML
    private JFXTextField etPrice;
    @FXML
    private JFXComboBox<?> comboCategory;
    @FXML
    private Pane paneAddBils;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btn_addclick(ActionEvent event) {
    }

    @FXML
    private void home_Click(MouseEvent event) {
    }

    @FXML
    private void btnClosePurchaseBils(MouseEvent event) {
        paneAddBils.setVisible(false);
    }

    @FXML
    private void addProductClick(ActionEvent event) {
    }

    @FXML
    private void btnSaveBilsClick(ActionEvent event) {
    }
    
}
