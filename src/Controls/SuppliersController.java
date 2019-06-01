/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OM EL NOUR
 */
public class SuppliersController implements Initializable {

    @FXML
    private TableView<?> table;
    @FXML
    private TableColumn<?, ?> col_id;
    @FXML
    private TableColumn<?, ?> col_ClientName;
    @FXML
    private TableColumn<?, ?> col_phone;
    @FXML
    private TableColumn<?, ?> col_notes;
    @FXML
    private AnchorPane anchorAdd;
    @FXML
    private JFXTextField etCustomerName;
    @FXML
    private JFXTextField etCustomerPhone;
    @FXML
    private JFXTextArea etNotes;
    @FXML
    private Label txtNotify;
    @FXML
    private JFXButton btn_save;
    @FXML
    private ImageView closeAdd;
    @FXML
    private AnchorPane anchorPaid;
    @FXML
    private Label labelClientName;
    @FXML
    private Label lablRemainingCost;
    @FXML
    private Label txtCustomerId;
    @FXML
    private JFXTextField etPaidValue;
    @FXML
    private DatePicker dateBicerPaidDate;
    @FXML
    private JFXTextArea etComment;
    @FXML
    private JFXButton btnAddCost;
    @FXML
    private TableView<?> tablePayment;
    @FXML
    private TableColumn<?, ?> colDatePaid;
    @FXML
    private TableColumn<?, ?> colpaidValuePaid;
    @FXML
    private TableColumn<?, ?> colNotesPaid;
    @FXML
    private JFXButton btnCustomersDetails;
    @FXML
    private AnchorPane anchorDetails;
    @FXML
    private TableColumn<?, ?> colIdCOrder;
    @FXML
    private TableColumn<?, ?> colDateCorder;
    @FXML
    private TableColumn<?, ?> colPaidCOrder;
    @FXML
    private TableColumn<?, ?> colRemainingCorder;
    @FXML
    private TableColumn<?, ?> colDiscountOrder;
    @FXML
    private Label etTotalCost;
    @FXML
    private Label txtCustomersPayment;
    @FXML
    private ImageView purchase;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void add_click(MouseEvent event) {
    }

    @FXML
    private void home_Click(MouseEvent event) {
    }

    @FXML
    private void etclient_click(ActionEvent event) {
    }

    @FXML
    private void btn_save_click(ActionEvent event) {
    }

    @FXML
    private void closeAdd_click(MouseEvent event) {
    }

    @FXML
    private void ClosePaidAnchor(MouseEvent event) {
    }

    @FXML
    private void addCostClick(ActionEvent event) {
    }

    @FXML
    private void btnCustomersDetailsClick(ActionEvent event) {
    }

    @FXML
    private void closeOrderDetails(MouseEvent event) {
    }

    @FXML
    private void purchaseClick(MouseEvent event) {
    }
    
}
