/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import java.net.URL;
import java.util.ResourceBundle;
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
public class AcountsCenterController implements Initializable {

    @FXML
    private TableView<?> orderTable;
    @FXML
    private TableColumn<?, ?> col_orderId;
    @FXML
    private TableColumn<?, ?> col_orderDate;
    @FXML
    private TableColumn<?, ?> col_customerName;
    @FXML
    private TableColumn<?, ?> col_totalCost;
    @FXML
    private TableColumn<?, ?> col_paid;
    @FXML
    private TableColumn<?, ?> col_remainning;
    @FXML
    private TableColumn<?, ?> col_discount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void homeClick(MouseEvent event) {
    }
    
}
