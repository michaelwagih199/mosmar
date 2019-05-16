
package Controls;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dao.ExpensessDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.OrderPaymentDAO;
import entities.Custom_OrderDetails;
import entities.Expenses;
import entities.OrderDetail;
import entities.custom_orders;
import helper.CalculasHelper;
import helper.Helper;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author OM EL NOUR
 */
public class AcountsCenterController implements Initializable {

    @FXML
    private TableView<custom_orders> orderTable;
    @FXML
    private TableColumn<custom_orders, Integer> col_orderId;
    @FXML
    private TableColumn<custom_orders, Date> col_orderDate;
    @FXML
    private TableColumn<custom_orders, String> col_customerName;
    @FXML
    private TableColumn<custom_orders, Float> col_totalCost;
    @FXML
    private TableColumn<custom_orders, Float> col_paid;
    @FXML
    private TableColumn<custom_orders, Float> col_remainning;
    @FXML
    private TableColumn<custom_orders, Float> col_discount;
    @FXML
    private TableColumn<custom_orders, String> col_orderType;
    
    ObservableList<custom_orders> row = FXCollections.observableArrayList();
    ObservableList<Custom_OrderDetails> orderDetailrow = FXCollections.observableArrayList();
    
    OrderDAO orderDAO = new OrderDAO();
    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    CalculasHelper calculasHelper = new CalculasHelper();
    OrderPaymentDAO orderPaymentDAO = new OrderPaymentDAO();
    ExpensessDAO expensessDAO = new ExpensessDAO();
    
    @FXML
    private DatePicker orderDate;
    @FXML
    private ComboBox<String> compoOrdeType;
    
    Helper help = new Helper();
    @FXML
    private Pane paneOrderDetail;
    @FXML
    private TableView<Custom_OrderDetails> tableOrderDetails;
    @FXML
    private TableColumn<Custom_OrderDetails, String> col_productName_orderDetails;
    @FXML
    private TableColumn<Custom_OrderDetails, Float> col_price_orderDetail;
    @FXML
    private TableColumn<Custom_OrderDetails, Float> col_quantity_orderDetail;
    @FXML
    private TableColumn<Custom_OrderDetails, Float> colAllCostOrderDetail;
    @FXML
    private Label lableOrderDetailId;
    @FXML
    private Pane pane_addPaid;
    @FXML
    private Label txt_sales;
    @FXML
    private Label txt_treasury;
    @FXML
    private Label txt_net_profit_before_paid;
    @FXML
    private Label txt_net_profit_after_paid;
    @FXML
    private DatePicker dateExpenes;
    @FXML
    private JFXTextField et_expenseContrext;
    @FXML
    private JFXTextField et_expenceValue;
    @FXML
    private JFXTextArea et_expenceNotes;
    @FXML
    private TableView<Expenses> tableExpense;
    @FXML
    private TableColumn<Expenses, Date> col_expenseDate;
    @FXML
    private TableColumn<Expenses, String> col_expenseContext;
    @FXML
    private TableColumn<Expenses, Float> col_expenseValue;
    @FXML
    private TableColumn<Expenses, String> col_expenseNotes;
    @FXML
    private Label labelOrderType;
    @FXML
    private Button btnShow;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        compoOrdeType.getItems().addAll("قطاعى");
        compoOrdeType.getItems().addAll("جملة");
        compoOrdeType.getItems().addAll("جملة الجملة");

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
        orderDate.setConverter(converter);
        orderDate.setPromptText(pattern.toLowerCase());
        
        dateExpenes.setConverter(converter);
        dateExpenes.setPromptText(pattern.toLowerCase());
          
    }

    @FXML
    private void homeClick(MouseEvent event) throws IOException {
        help.start("/mosmar/main.fxml", "الصفحة الرئيسية");
        help.closeC(compoOrdeType);
    }

    public void loadTabData() throws ParseException {
        col_orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        col_orderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        col_customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        col_totalCost.setCellValueFactory(new PropertyValueFactory<>("totslCost"));
        col_paid.setCellValueFactory(new PropertyValueFactory<>("paid"));
        col_remainning.setCellValueFactory(new PropertyValueFactory<>("remaining"));
        col_discount.setCellValueFactory(new PropertyValueFactory<>("orderDiscount"));
        col_orderType.setCellValueFactory(new PropertyValueFactory<>("orderType"));
        formateDate();
       // row.addAll(orderDAO.getOrderByDate());
        orderTable.setItems(row);
    }
    
    public void loadorderDetailTabData() throws ParseException {
        col_productName_orderDetails.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        col_price_orderDetail.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_quantity_orderDetail.setCellValueFactory(new PropertyValueFactory<>("quantity"));    
        colAllCostOrderDetail.setCellValueFactory(new PropertyValueFactory<>("total"));    
        tableOrderDetails.setItems(orderDetailrow);
    }
    
    public void formateDate() {
        col_orderDate.setCellFactory(column -> {
            TableCell<custom_orders, Date> cell = new TableCell<custom_orders, Date>() {
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
        
        col_expenseDate.setCellFactory(column -> {
            TableCell<Expenses, Date> cell = new TableCell<Expenses, Date>() {
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

    public void yarab(Date orderDate , String orderType) throws ParseException {
        String paymentType = null;
          //formatting date in Java using SimpleDateFormat
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
     for (int i = 0; i < orderDAO.getOrderByDate(orderDate,orderType).size(); i++) {
            int orderId = (int) orderDAO.getOrderByDate(orderDate,orderType).get(i)[0];
            //Date date = (Date) orderDAO.getOrderByDate().get(i)[1];
            String date = DATE_FORMAT.format(orderDAO.getOrderByDate(orderDate,orderType).get(i)[1]);
            Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(date);  
            String customerName = (String) orderDAO.getOrderByDate(orderDate,orderType).get(i)[2];
            float totslCost = (float) orderDAO.getOrderByDate(orderDate,orderType).get(i)[3];
            float paid = (float) orderDAO.getOrderByDate(orderDate,orderType).get(i)[4];
            float remaining = (float) orderDAO.getOrderByDate(orderDate,orderType).get(i)[5];
            float orderDiscount = (float) orderDAO.getOrderByDate(orderDate,orderType).get(i)[6];
            int paymentId = (int) orderDAO.getOrderByDate(orderDate,orderType).get(i)[7];
            if (paymentId == 1) {
                paymentType = "اجل";
            }else if (paymentId == 2) {
                paymentType = "نقدى";

         }
            
            //paid,remaining,orderDiscount;;
            row.add(new custom_orders(orderId,
                    date1,
                    customerName,
                    paymentType,
                    totslCost,
                    paid,
                    remaining,
                    orderDiscount));
        }

    }

    @FXML
    private void btnShowClick(ActionEvent event) throws ParseException { 

        try {
            orderTable.getItems().clear();
            String knownUsFrom = compoOrdeType.getSelectionModel().getSelectedItem().toString();
            java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(orderDate.getValue());
            yarab(gettedDatePickerDate, knownUsFrom);
            loadTabData();
            txt_sales.setText("");
            
           
          
           
            
           
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());

        }

    }

    @FXML
    private void orderTable_click(MouseEvent event) {
         if(event.getButton().equals(MouseButton.PRIMARY)){
            if(event.getClickCount() == 2){
                
                try{
                    custom_orders list = orderTable.getSelectionModel().getSelectedItem();
                    paneOrderDetail.setVisible(true);
                    lableOrderDetailId.setText(String.valueOf(list.getOrderId()));
                    
                    yaraborderTe(list.getOrderId());
                    loadorderDetailTabData();
                                     
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }

    }
    
    public void yaraborderTe(int orderId) throws ParseException {

        for (int i = 0; i < orderDetailDAO.getOrderDetailByOrderId(orderId).size(); i++) {
          
            String ProductName = (String) orderDetailDAO.getOrderDetailByOrderId(orderId).get(i)[0];
            float price = (float) orderDetailDAO.getOrderDetailByOrderId(orderId).get(i)[1];
            float quantity = (float) orderDetailDAO.getOrderDetailByOrderId(orderId).get(i)[2];
            float total = (float) orderDetailDAO.getOrderDetailByOrderId(orderId).get(i)[3];
         
            //paid,remaining,orderDiscount;;
            orderDetailrow.add(new Custom_OrderDetails(ProductName, price, quantity, total));
        }
           
    }

    @FXML
    private void paneOrderDetail_close(MouseEvent event) {
        tableOrderDetails.getItems().clear();
        paneOrderDetail.setVisible(false);
    }

    @FXML
    private void addExpesessClick(MouseEvent event) {
        pane_addPaid.setVisible(true);
    }

    @FXML
    private void btn_save_expenceClick(ActionEvent event) throws ParseException {
        try {
            java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(dateExpenes.getValue());
            calculasHelper.insertExpensess(gettedDatePickerDate,
                    et_expenseContrext.getText().toString(),
                    Float.parseFloat(et_expenceValue.getText().toString()),
                    et_expenceNotes.getText().toString());
            //

            clearExpenses();
            loadExpensesData(gettedDatePickerDate);
            ProfitCalulas();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        pane_addPaid.setVisible(false);

    }
    
    public void clearExpenses(){
        et_expenseContrext.clear();
        et_expenceValue.clear();
        et_expenceNotes.setText("");
    }
    public void trasury(){
        
       
    }

    @FXML
    private void closeExpencesClick(MouseEvent event) {
        pane_addPaid.setVisible(false);
        clearExpenses();
    }

    public void loadExpensesData(Date date) throws ParseException {
        
        col_expenseDate.setCellValueFactory(new PropertyValueFactory<>("expensesDate"));
        col_expenseContext.setCellValueFactory(new PropertyValueFactory<>("expensesContext"));
        col_expenseValue.setCellValueFactory(new PropertyValueFactory<>("expensesValue"));
        col_expenseNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        formateDate();
        tableExpense.setItems(calculasHelper.getExpencseByDate(date));
        
    }

    @FXML
    private void netProfiteClick(Event event) throws ParseException {
        compoOrdeType.setVisible(false);
        labelOrderType.setVisible(false);
        btnShow.setVisible(false);
        clearProfit();

    }
    
    public void clearProfit(){
        tableExpense.getItems().clear();
        txt_sales.setText("");
        txt_treasury.setText("");
        txt_net_profit_before_paid.setText("");
        txt_net_profit_after_paid.setText("");
    }

    @FXML
    private void TapordersClick(Event event) {
        compoOrdeType.setVisible(true);
         labelOrderType.setVisible(true);
        btnShow.setVisible(true);
    }

    @FXML
    private void btnProfitClick(ActionEvent event) throws ParseException {
        ProfitCalulas();

    }
    
    public void ProfitCalulas(){
        try {
             tableExpense.getItems().clear();
             java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(orderDate.getValue());
             //culculas methods
            txt_sales.setText(calculasHelper.getDaySales(gettedDatePickerDate) + "");
            loadExpensesData(gettedDatePickerDate);  
            
            txt_treasury.setText(String.valueOf(
            calculasHelper.getDaySales(gettedDatePickerDate) - calculasHelper.getDayExpenses(gettedDatePickerDate)
            ));
        } catch (Exception e) {
        }
    }


}
