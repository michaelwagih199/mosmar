package Controls;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import dao.CustomerDAO;
import dao.CustomersPaymentDAO;
import entities.Customers;
import entities.CustomersPayment;
import entities.Products;
import entities.custom_orders;
import helper.CustumersCalculas;
import helper.FxDialogs;
import helper.Helper;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
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
import org.controlsfx.control.textfield.TextFields;


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
    CustomersPaymentDAO customersPaymentDAO = new CustomersPaymentDAO();
    
    @FXML
    private AnchorPane anchorPaid;
    @FXML
    private Label labelClientName;
    @FXML
    private Label lablRemainingCost;
    @FXML
    private Label etTotalCost;
    @FXML
    private JFXTextField etPaidValue;
    @FXML
    private DatePicker dateBicerPaidDate;
    @FXML
    private JFXTextArea etComment;
    @FXML
    private JFXButton btnAddCost;
   
    Helper help = new Helper();
    CustumersCalculas custumersCalculas = new CustumersCalculas();
    
    @FXML
    private TableView<CustomersPayment> tablePayment;
    @FXML
    private TableColumn<CustomersPayment, Date> colDatePaid;
    @FXML
    private TableColumn<CustomersPayment, Float> colpaidValuePaid;
    @FXML
    private TableColumn<CustomersPayment, String> colNotesPaid;
    @FXML
    private JFXButton btnCustomersDetails;
    @FXML
    private AnchorPane anchorDetails;

    DecimalFormat df = new DecimalFormat("#.###");
    @FXML
    private Label txtCustomerId;
    @FXML
    private Label txtCustomersPayment;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTabData();
        addButtonDeleteToTable();
        //addButtonModfyToTable();
        addButtonAccountsToTable();
        formatDate();
    }
    
    public void formatDate(){
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
                            txtCustomersPayment.setText("0");
                            lablRemainingCost.setText("0");
                            Customers data = getTableView().getItems().get(getIndex());
                            anchorPaid.setVisible(true);
                            customerAccountsCalc(data.getCustomerName(), data.getCustomerId());
                            txtCustomerId.setText(data.getCustomerId().toString());
                            loadtablePaymentData(data.getCustomerId());
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
        try {

            java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(dateBicerPaidDate.getValue());
            if (Float.parseFloat(etPaidValue.getText().toString())  >  Float.parseFloat(lablRemainingCost.getText().toString())) {
                System.out.println("Controls.CustomersController.addCostClick()");
            }else{
                
                  customerPaymentAdd(Integer.parseInt(txtCustomerId.getText().toString()), gettedDatePickerDate,
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
    public void customerPaymentAdd(int customerId, Date paymentDate, float paidVaue, String notes) {

        try {
            CustomersPayment customersPayment = new CustomersPayment();
            customersPayment.setCustomerId(customerId);         
            customersPayment.setPaymentDate(paymentDate);
            customersPayment.setPaymentValue(paidVaue);
            customersPayment.setNotes(notes);
            customersPaymentDAO.addCustomersPayment(customersPayment);
        } catch (Exception ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnCustomersDetailsClick(ActionEvent event) {
        
       anchorDetails.setVisible(true);
    }
    
    public void customerAccountsCalc(String customerName, int customerId) {
        try {
            labelClientName.setText(customerName);
            double customerRemaining = 0;
            customerRemaining = custumersCalculas.getCustomersRemaining(customerId);
            double customerRetrival = 0;
            try {
                customerRetrival = custumersCalculas.getCustomersPaymentRetrive(customerId);
            } catch (Exception e) {
            }

            double remain = customerRemaining - customerRetrival;
            etTotalCost.setText(df.format(remain));
            lablRemainingCost.setText(etTotalCost.getText().toString());

            txtCustomersPayment.setText(df.format(custumersCalculas.getCustomersPayment(customerId)));
            float RemainingCost = Float.parseFloat(etTotalCost.getText().toString()) - Float.parseFloat(txtCustomersPayment.getText().toString());
            lablRemainingCost.setText(df.format(RemainingCost));
        } catch (Exception e) {
        }

    }
 
//      public void yarab(Date startDate, Date endDate) throws ParseException {
//        String paymentType = null;
//        String productType = null;
//        //formatting date in Java using SimpleDateFormat
//        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
//        for (int i = 0; i < orderDAO.getOrderByDate(startDate, endDate).size(); i++) {
//            int orderId = (int) orderDAO.getOrderByDate(startDate, endDate).get(i)[0];
//            //Date date = (Date) orderDAO.getOrderByDate().get(i)[1];
// 
//            //paid,remaining,orderDiscount;;
//            row.add(new custom_orders(orderId,
//                    date1,
//                    customerName,
//                    paymentType,
//                    productType,
//                    totslCost,
//                    paid,
//                    remaining,
//                    orderDiscount));
//        }
//
//    }

    @FXML
    private void closeOrderDetails(MouseEvent event) {
        anchorDetails.setVisible(false);
    }

        public void loadtablePaymentData(int customerId) {
        colDatePaid.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colpaidValuePaid.setCellValueFactory(new PropertyValueFactory<>("paymentValue"));
        colNotesPaid.setCellValueFactory(new PropertyValueFactory<>("notes"));
        //updateStatusColor();
        formateDate();
        tablePayment.setItems(customersPaymentDAO.getCustomersPayments(customerId));
    }
        
        
      public void formateDate() {
        colDatePaid.setCellValueFactory(new PropertyValueFactory<CustomersPayment, Date>("paymentDate"));
        colDatePaid.setCellFactory(column -> {
            TableCell<CustomersPayment, Date> cell = new TableCell<CustomersPayment, Date>() {
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


          
    
        

     
}
