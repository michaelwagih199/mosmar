package Controls;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dao.SuppliersDAO;
import dao.SuppliersPaymentDAO;
import entities.Customers;
import entities.CustomersPayment;
import entities.Suppliers;
import entities.Supplierspayment;
import helper.CustumersCalculas;
import helper.FxDialogs;
import helper.Helper;
import helper.SuppliersCalculas;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import javafx.util.StringConverter;

public class SuppliersController implements Initializable {

    Helper helper = new Helper();

    private final SuppliersDAO suppliersDAO = new SuppliersDAO();
    DecimalFormat df = new DecimalFormat("#.###");
    SuppliersCalculas suppliersCalculas = new SuppliersCalculas();
    SuppliersPaymentDAO suppliersPaymentDAO = new SuppliersPaymentDAO();

    @FXML
    private ImageView purchase;
    @FXML
    private TableView<Suppliers> table;
    @FXML
    private TableColumn<Suppliers, Integer> col_id;
    @FXML
    private TableColumn<Suppliers, String> col_ClientName;
    @FXML
    private TableColumn<Suppliers, String> col_phone;
    @FXML
    private TableColumn<Suppliers, String> col_companyName;
    @FXML
    private TableColumn<Suppliers, String> col_adress;
    @FXML
    private TableColumn<Suppliers, String> col_notes;
    @FXML
    private AnchorPane anchorAdd;

    @FXML
    private JFXTextField etSuppliersPhone;
    @FXML
    private JFXTextArea etNotes;
    @FXML
    private Label txtNotify;
    @FXML
    private JFXButton btn_save;
    @FXML
    private ImageView closeAdd;
    @FXML
    private JFXTextField etSuppliersCompany;
    @FXML
    private JFXTextArea etSuppliersAddress;
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
    private TableView<Supplierspayment> tablePayment;
    @FXML
    private TableColumn<Supplierspayment, Date> colDatePaid;
    @FXML
    private TableColumn<Supplierspayment, Float> colpaidValuePaid;
    @FXML
    private TableColumn<Supplierspayment, String> colNotesPaid;
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
    private JFXTextField etSuppliersName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadTabData();
        addButtonDeleteToTable();
        addButtonModfyToTable();
        addButtonAccountsToTable();
        formatDate();

    }

    @FXML
    private void add_click(MouseEvent event) {
        anchorAdd.setVisible(true);
    }

    @FXML
    private void home_Click(MouseEvent event) throws IOException {
        helper.start("/mosmar/main.fxml", "الصفحة الرئيسية");
        helper.close(btn_save);
    }

    @FXML
    private void btn_save_click(ActionEvent event) {
        try {
            Suppliers suppliers = new Suppliers();
            suppliers.setSupplierName(etSuppliersName.getText().toString());
            suppliers.setSupplierPhone(etSuppliersPhone.getText().toString());
            suppliers.setSupplierAddress(etSuppliersAddress.getText().toString());
            suppliers.setCompanyName(etSuppliersCompany.getText().toString());
            suppliers.setNotes(etNotes.getText().toString());
            suppliersDAO.addCustomer(suppliers);
            txtNotify.setText("تم الحفظ");
            clearText();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        loadTabData();
    }

    @FXML
    private void closeAdd_click(MouseEvent event) {
        anchorAdd.setVisible(false);
    }

    @FXML
    private void ClosePaidAnchor(MouseEvent event) {
        anchorPaid.setVisible(false);
    }

    @FXML
    private void addCostClick(ActionEvent event) {
        try {

            java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(dateBicerPaidDate.getValue());
            if (Float.parseFloat(etPaidValue.getText().toString()) > Float.parseFloat(lablRemainingCost.getText().toString())) {

            } else {
                SuppPaymentAdd(Integer.parseInt(txtCustomerId.getText().toString()), gettedDatePickerDate,
                        Float.parseFloat(etPaidValue.getText().toString()), etComment.getText().toString());
            }

            loadtablePaymentData(Integer.parseInt(txtCustomerId.getText().toString()));
            customerAccountsCalc(labelClientName.getText().toString(),
                    Integer.parseInt(txtCustomerId.getText().toString()));

            etPaidValue.clear();
            etComment.clear();
        } catch (Exception e) {
        }

    }

    @FXML
    private void btnCustomersDetailsClick(ActionEvent event) {
    }

    @FXML
    private void closeOrderDetails(MouseEvent event) {
    }

    @FXML
    private void purchaseClick(MouseEvent event) throws IOException {
        helper.start("/mosmar/sho7nat.fxml", "المخزن");
        helper.closeI(purchase);
    }

    /**
     * function for help
     */
    public void loadTabData() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("supplierid"));
        col_ClientName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("supplierPhone"));
        col_companyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        col_adress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        col_notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        table.setItems(suppliersDAO.getAllSuppliers());
    }

    private void clearText() {
        etSuppliersName.clear();
        etSuppliersPhone.clear();
        etSuppliersAddress.clear();
        etSuppliersCompany.clear();
        etNotes.clear();
    }

    public void formatDate() {
        String pattern = "yyyy-MM-dd";
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter
                    = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        dateBicerPaidDate.setConverter(converter);
        dateBicerPaidDate.setPromptText(pattern.toLowerCase());
    }

    /**
     * *
     * FUNCTION FOR BUTTON TO TABLES
     */
    //delete row
    private void addButtonDeleteToTable() {
        TableColumn<Suppliers, Void> colBtn = new TableColumn();

        Callback<TableColumn<Suppliers, Void>, TableCell<Suppliers, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<Suppliers, Void>, TableCell<Suppliers, Void>>() {
            @Override
            public TableCell<Suppliers, Void> call(final TableColumn<Suppliers, Void> param) {
                final TableCell<Suppliers, Void> cell = new TableCell<Suppliers, Void>() {

                    private final Button btn = new Button("مسح");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                            if (FxDialogs.showConfirm("مسح المنتج", "هل تريد مسح المنتج?", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
                                Suppliers data = getTableView().getItems().get(getIndex());
                                try {
                                    suppliersDAO.removeCustomer(data.getSupplierid());
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
                            //btn.getStyleClass().add("button_Small");
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

    private void addButtonModfyToTable() {
        TableColumn<Suppliers, Void> colBtn = new TableColumn();

        Callback<TableColumn<Suppliers, Void>, TableCell<Suppliers, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<Suppliers, Void>, TableCell<Suppliers, Void>>() {
            @Override
            public TableCell<Suppliers, Void> call(final TableColumn<Suppliers, Void> param) {
                final TableCell<Suppliers, Void> cell = new TableCell<Suppliers, Void>() {

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
                            //btn.getStyleClass().add("button_Small");
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
        TableColumn<Suppliers, Void> colBtn = new TableColumn();
        Callback<TableColumn<Suppliers, Void>, TableCell<Suppliers, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<Suppliers, Void>, TableCell<Suppliers, Void>>() {
            @Override
            public TableCell<Suppliers, Void> call(final TableColumn<Suppliers, Void> param) {
                final TableCell<Suppliers, Void> cell = new TableCell<Suppliers, Void>() {
                    private final Button btn = new Button("الحساب");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                            Suppliers data = getTableView().getItems().get(getIndex());
                            txtCustomersPayment.setText("0");
                            lablRemainingCost.setText("0");
                            anchorPaid.setVisible(true);

                            customerAccountsCalc(data.getSupplierName(), data.getSupplierid());
                            txtCustomerId.setText(data.getSupplierid().toString());
                            loadtablePaymentData(data.getSupplierid());

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            //btn.getStyleClass().add("button_Small");
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

    // helper methods
    public void customerAccountsCalc(String customerName, int customerId) {
        try {
            labelClientName.setText(customerName);
            etTotalCost.setText(df.format(suppliersCalculas.getCustomersRemaining(customerId).get(0)));
            lablRemainingCost.setText(etTotalCost.getText().toString());
            txtCustomersPayment.setText(df.format(suppliersCalculas.getSuppliersPayment(customerId).get(0)));
            float RemainingCost = Float.parseFloat(etTotalCost.getText().toString()) - Float.parseFloat(txtCustomersPayment.getText().toString());
            lablRemainingCost.setText(df.format(RemainingCost));
            
        } catch (Exception e) {

        }

    }

    public void loadtablePaymentData(int supplierId) {
        colDatePaid.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colpaidValuePaid.setCellValueFactory(new PropertyValueFactory<>("paymentValue"));
        colNotesPaid.setCellValueFactory(new PropertyValueFactory<>("notes"));
        //updateStatusColor();
        formateDate();
        tablePayment.setItems(suppliersPaymentDAO.getSupplierspayments(supplierId));
    }

    public void formateDate() {
        colDatePaid.setCellValueFactory(new PropertyValueFactory<Supplierspayment, Date>("paymentDate"));
        colDatePaid.setCellFactory(column -> {
            TableCell<Supplierspayment, Date> cell = new TableCell<Supplierspayment, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(item));
                    }
                }
            };
            return cell;
        });
    }

    public void SuppPaymentAdd(int customerId, Date paymentDate, float paidVaue, String notes) {
        try {
            Supplierspayment supplierspayment = new Supplierspayment();
            supplierspayment.setSuppliersId(customerId);
            supplierspayment.setPaymentDate(paymentDate);
            supplierspayment.setPaymentValue(paidVaue);
            supplierspayment.setNotes(notes);
            suppliersPaymentDAO.addSupplierspayment(supplierspayment);
        } catch (Exception ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
