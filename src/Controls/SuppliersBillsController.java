package Controls;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dao.SuppliersBillsDAO;
import dao.SuppliersDAO;
import entities.Suppliers;
import entities.SuppliersBills;
import entities.custom_orders;
import helper.FxDialogs;
import helper.Helper;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;

public class SuppliersBillsController implements Initializable {

   
    @FXML
    private JFXButton btn_add;
    @FXML
    private Pane PanebilsDetails;
    @FXML
    private JFXTextField etProduct;
    @FXML
    private JFXTextField etQuantity;
    @FXML
    private JFXTextField etPrice;
    @FXML
    private JFXComboBox<?> comboCategory;
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
    private Pane paneAddBils;
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

    //
    Helper helper = new Helper();
    SuppliersDAO suppliersDAO = new SuppliersDAO();
    SuppliersBillsDAO suppliersBillsDAO = new SuppliersBillsDAO();
    @FXML
    private TableView<SuppliersBills> table;
    @FXML
    private TableColumn<SuppliersBills, String> colNotes;
    @FXML
    private TableColumn<SuppliersBills,Float> col_paidBils;
    @FXML
    private TableColumn<SuppliersBills, Float> col_remainingBils;
    @FXML
    private TableColumn<SuppliersBills, Float> col_totalBils;
    @FXML
    private TableColumn<SuppliersBills, Integer> col_suppliersName;
    @FXML
    private TableColumn<SuppliersBills, Date> col_dateBils;
    @FXML
    private TableColumn<SuppliersBills, Integer> colBilsId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TextFields.bindAutoCompletion(etSuppliersId, getSupplierName(etSuppliersId.getText().toString()));
        loadTabBillsData();
        formatDate();
        ubdateSuppliers();
    }

    @FXML
    private void tableClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                PanebilsDetails.setVisible(true);
            }
        }
    }

    @FXML
    private void btn_addclick(ActionEvent event) {
        paneAddBils.setVisible(true);
    }

    @FXML
    private void home_Click(MouseEvent event) throws IOException {
        helper.start("/mosmar/suppliers.fxml", "المخزن");
        helper.close(btn_add);
    }

    @FXML
    private void addProductClick(ActionEvent event) {
    }

    @FXML
    private void btnClosePurchaseBils(MouseEvent event) {
        paneAddBils.setVisible(false);
    }

    @FXML
    private void btnSaveBilsClick(ActionEvent event) {
        //insert suppliersBills
        try {
            java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(bilsDate.getValue());
            SuppliersBills suppliersBills = new SuppliersBills();
            suppliersBills.setBilsDate(gettedDatePickerDate);
            suppliersBills.setBilsPaid(Float.parseFloat(etPaidBils.getText().toString()));
            suppliersBills.setBilsRemaining(Float.parseFloat(etRemainig.getText().toString()));
            suppliersBills.setBilsTotal(Float.parseFloat(etTotalBils.getText().toString()));
            int suppliersId = suppliersDAO.getSupplierId(etSuppliersId.getText().toString());
            suppliersBills.setSuppliersId(suppliersId);
            suppliersBills.setUuid(generateCode());
            suppliersBills.setNotes(etNotes.getText().toString());
            suppliersBillsDAO.addSuppliersBills(suppliersBills);

        } catch (Exception e) {
        }
        loadTabBillsData();
        clearTextBills();
        paneAddBils.setVisible(false);
    }

    @FXML
    private void PanebilsDetailsClose(MouseEvent event) {
        PanebilsDetails.setVisible(false);
    }

    /**
     * helper method
     */
    public List<String> getSupplierName(String start) {
        ArrayList<String> result = new ArrayList<String>();
        for (Suppliers o : suppliersDAO.getAllSuppliers()) {
            result.add(o.getSupplierName());
        }

        return result;
    }

    /**
     * generate code id
     */
    public String generateCode() {
        // Creating a random UUID (Universally unique identifier).
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     *
     */
    public void clearTextBills() {
        etPaidBils.clear();
        etRemainig.clear();
        etTotalBils.clear();
        etSuppliersId.clear();
    }

    @FXML
    private void userTypingPaid(MouseEvent event) {
        try {
            String answer = FxDialogs.showTextInput("اكتب القيمة المدفوعة", "جنية", "0");
            etPaidBils.setText(answer);
            Float remaining = Float.parseFloat(etTotalBils.getText().toString())
                    - Float.parseFloat(answer);
            etRemainig.setText(String.valueOf(remaining));
        } catch (Exception e) {
        }

    }

    public void formatDate() {
         col_dateBils.setCellFactory(column -> {
            TableCell<SuppliersBills, Date> cell = new TableCell<SuppliersBills, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };
            return cell;
        });
    }

    public void loadTabBillsData() {       
        col_dateBils.setCellValueFactory(new PropertyValueFactory<>("bilsDate"));
        col_paidBils.setCellValueFactory(new PropertyValueFactory<>("bilsPaid"));
        col_remainingBils.setCellValueFactory(new PropertyValueFactory<>("bilsRemaining"));
        col_totalBils.setCellValueFactory(new PropertyValueFactory<>("bilsTotal"));
        //col_suppliersName.setCellValueFactory(new PropertyValueFactory<>("suppliersId"));
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        colBilsId.setCellValueFactory(new PropertyValueFactory<>("SuppliersBilsId"));
        table.setItems(suppliersBillsDAO.getSuppliersBills());
        
    }
    
    public void ubdateSuppliers() {
        col_suppliersName.setCellFactory(new Callback<TableColumn<SuppliersBills, Integer>, TableCell<SuppliersBills, Integer>>() {
            @Override
            public TableCell<SuppliersBills, Integer> call(TableColumn<SuppliersBills, Integer> param) {
                return new TableCell<SuppliersBills, Integer>() {

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                           
                            SuppliersBills index = getTableView().getItems().get(getIndex());
                            String name = suppliersDAO.getSuppliersById(index.getSuppliersId()).getSupplierName();
                            setText(name);
                          
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

}
