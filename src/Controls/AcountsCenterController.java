/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import dao.OrderDAO;
import entities.custom_orders;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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

    ObservableList<custom_orders> row = FXCollections.observableArrayList();
    OrderDAO orderDAO = new OrderDAO();
    @FXML
    private DatePicker orderDate;
    @FXML
    private ComboBox<String> compoOrdeType;
    
      Helper help = new Helper();

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
        formateDate();
       // row.addAll(orderDAO.getOrderByDate());
        orderTable.setItems(row);

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
    }

    public void yarab(Date orderDate , String orderType) throws ParseException {
        
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
            //paid,remaining,orderDiscount;;
            row.add(new custom_orders(orderId,
                    date1,
                    customerName,
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
            System.out.println(gettedDatePickerDate);
            yarab(gettedDatePickerDate, knownUsFrom);
            loadTabData();

        } catch (Exception e) {
            
        }


         
    }

}
