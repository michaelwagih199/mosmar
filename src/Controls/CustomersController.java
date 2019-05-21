package Controls;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import dao.CustomerDAO;
import entities.Customers;
import entities.Products;
import helper.FxDialogs;
import helper.Helper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author OM EL NOUR
 */
public class CustomersController implements Initializable {

    @FXML
    private TableView<Customers> table;
    @FXML
    private TableColumn<Customers, String> col_ClientName;
    @FXML
    private TableColumn<Customers, String> col_phone;
    @FXML
    private TableColumn<Customers, String> col_notes;
    @FXML
    private TableColumn<Customers, Integer> col_id;

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

    private final CustomerDAO customerDAO = new CustomerDAO();
    @FXML
    private AnchorPane anchorPaid;
    @FXML
    private Label labelClientName;
    @FXML
    private Label lablRemainingCost;
    @FXML
    private JFXTextField etTotalCost;
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
    Helper help = new Helper();
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
    private TableColumn<?, ?> colDateDetail;
    @FXML
    private TableColumn<?, ?> colProductDetails;
    @FXML
    private TableColumn<?, ?> colPrice;
    @FXML
    private TableColumn<?, ?> colTotalDetails;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTabData();
        addButtonDeleteToTable();
        addButtonModfyToTable();
        addButtonAccountsToTable();

    }

    @FXML
    private void add_click(MouseEvent event) {
        anchorAdd.setVisible(true);
    }

    @FXML
    private void home_Click(MouseEvent event) throws IOException {
        help.start("/mosmar/main.fxml", "الصفحة الرئيسية");
        help.close(btn_save);
    }

    @FXML
    private void btn_save_click(ActionEvent event) {
        try {
            Customers customers = new Customers();
            customers.setCustomerName(etCustomerName.getText().toString());
            customers.setCustomerPhone(etCustomerPhone.getText().toString());
            customers.setNotes(etNotes.getText().toString());

            customerDAO.addCustomer(customers);
            txtNotify.setText("تم الحفظ");
            clearText();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        loadTabData();
    }

    public void loadTabData() {
        col_ClientName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        col_id.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        col_notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        table.setItems(customerDAO.getAllCustomer());
    }

    //delete row
    private void addButtonDeleteToTable() {
        TableColumn<Customers, Void> colBtn = new TableColumn();

        Callback<TableColumn<Customers, Void>, TableCell<Customers, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<Customers, Void>, TableCell<Customers, Void>>() {
            @Override
            public TableCell<Customers, Void> call(final TableColumn<Customers, Void> param) {
                final TableCell<Customers, Void> cell = new TableCell<Customers, Void>() {

                    private final Button btn = new Button("مسح");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                            if (FxDialogs.showConfirm("مسح المنتج", "هل تريد مسح المنتج?", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
                                Customers data = getTableView().getItems().get(getIndex());
                                try {
                                    customerDAO.removeCustomer(data.getCustomerId());
                                    loadTabData();
                                } catch (Exception ex) {
                                    Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.getStyleClass().add("button_Small");
                            setGraphic(btn);

                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        table.getColumns().add(colBtn);
    }

    //delete row
    private void addButtonModfyToTable() {
        TableColumn<Customers, Void> colBtn = new TableColumn();

        Callback<TableColumn<Customers, Void>, TableCell<Customers, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<Customers, Void>, TableCell<Customers, Void>>() {
            @Override
            public TableCell<Customers, Void> call(final TableColumn<Customers, Void> param) {
                final TableCell<Customers, Void> cell = new TableCell<Customers, Void>() {

                    private final Button btn = new Button("تعديل");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.getStyleClass().add("button_Small");
                            setGraphic(btn);

                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        table.getColumns().add(colBtn);
    }

    //delete row
    private void addButtonAccountsToTable() {
        TableColumn<Customers, Void> colBtn = new TableColumn();

        Callback<TableColumn<Customers, Void>, TableCell<Customers, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<Customers, Void>, TableCell<Customers, Void>>() {
            @Override
            public TableCell<Customers, Void> call(final TableColumn<Customers, Void> param) {
                final TableCell<Customers, Void> cell = new TableCell<Customers, Void>() {

                    private final Button btn = new Button("الحساب");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            anchorPaid.setVisible(true);

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.getStyleClass().add("button_Small");
                            setGraphic(btn);

                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        table.getColumns().add(colBtn);
    }

    @FXML
    private void closeAdd_click(MouseEvent event) {
        anchorAdd.setVisible(false);
    }

    private void clearText() {
        etCustomerName.clear();
        etCustomerPhone.clear();
        etNotes.clear();
    }

    @FXML
    private void etclient_click(ActionEvent event) {
        txtNotify.setText("");
    }

    @FXML
    private void ClosePaidAnchor(MouseEvent event) {
           anchorPaid.setVisible(false);
    }

    @FXML
    private void addCostClick(ActionEvent event) {
    }

    @FXML
    private void btnCustomersDetailsClick(ActionEvent event) {
    }
}
