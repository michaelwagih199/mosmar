/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

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
    
}
