package Controls;

import com.jfoenix.controls.JFXComboBox;
import dao.CustomerDAO;
import dao.ProductDAO;
import dao.ProductNumbersDAO;
import dao.RetrievalsDAO;
import dao.RetrivaldetailsDAO;
import entities.Customers;
import entities.Products;
import entities.Productsnumber;
import entities.Retrievals;
import entities.Retrivaldetails;
import entities.custom_orders;
import helper.Helper;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.controlsfx.control.textfield.TextFields;

public class RetrievalController implements Initializable {

    @FXML
    private JFXComboBox<String> compo_product_Type;
    @FXML
    private TextField etProductName;
    @FXML
    private TextField etClientName;
    @FXML
    private TextField etValue;
    @FXML
    private TableView<Retrievals> retrivalTable;
    @FXML
    private TableColumn<Retrievals, Integer> colId;
    @FXML
    private TableColumn<Retrievals, Date> colDate;
    @FXML
    private TableColumn<Retrievals, Date> colTime;
    @FXML
    private TableColumn<Retrievals, Float> colValue;
    @FXML
    private TableColumn<Retrievals, Integer> colClient;
    @FXML
    private Pane paneAddBillsR;
    @FXML
    private TextField etQuantity;
    @FXML
    private TableView<Retrivaldetails> tableBilsdetail;
    @FXML
    private TableColumn<Retrivaldetails, Integer> colProductName;
    @FXML
    private TableColumn<Retrivaldetails, Float> colQuantity;
    @FXML
    private Label txttotatalValue;
    
    
    //our var
    DecimalFormat df = new DecimalFormat("#.###");
    RetrievalsDAO retrievalsDAO = new RetrievalsDAO();
    RetrivaldetailsDAO retrivaldetailsDAO = new RetrivaldetailsDAO();
    ProductDAO productDAO = new ProductDAO();
    ProductNumbersDAO productNumbersDAO = new ProductNumbersDAO();
    Helper helper = new Helper();
    CustomerDAO customerDAO = new CustomerDAO();
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        compo_product_Type.getItems().addAll("منتجات بكجم");
        compo_product_Type.getItems().addAll("منتجات بالوحدة");
        loadoRetrivalTabData();
        TextFields.bindAutoCompletion(etClientName, getAllCustomerName());
    }

    @FXML
    private void homeClick(MouseEvent event) throws IOException {
        helper.start("/mosmar/main.fxml", "الصفحة الرئيسية");
        helper.closePane(paneAddBillsR);  
    }

    @FXML
    private void compo_product_Type_click(ActionEvent event) {
        
        String knownUsFrom = compo_product_Type.getSelectionModel().getSelectedItem().toString();
        if (knownUsFrom.equals("منتجات بكجم")) {      
            TextFields.bindAutoCompletion(etProductName , getAllProductrName(etProductName.getText().toString()));
        } else {
            TextFields.bindAutoCompletion(etProductName , getAllProductNumberName(etProductName.getText().toString()));
        }
        
    }

    @FXML
    private void addRetrivalClick(MouseEvent event) {
          paneAddBillsR.setVisible(true);
    }

    @FXML
    private void retrivalTableClick(MouseEvent event) {
         if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                
            }
        }
         
    }

    @FXML
    private void addProductClick(ActionEvent event) {
      
    }

    @FXML
    private void saveBillsClick(ActionEvent event) {
    }

    @FXML
    private void closeAddBillsPane(MouseEvent event) {
          paneAddBillsR.setVisible(false);
    }


    
    public void loadoRetrivalTabData(){
        
        colClient.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("retrievalDate"));
        colId.setCellValueFactory(new PropertyValueFactory<>("retrievalId"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("retrievalTime"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("billsValue"));
        retrivalTable.setItems(retrievalsDAO.getAllRetrievals());
      
    }

     /**
     *
     * @return all product name
     */
    public ArrayList<String> getAllProductNumberName(String start) {
        ArrayList<String> result = new ArrayList<String>();
        for (Productsnumber o : productNumbersDAO.getAllProductsnumber()) {
           result.add(o.getProductnumberName());
        }
        return result;
    }
     /**
     *
     * @return all product name
     */
    public List<String> getAllProductrName(String start) {
        ArrayList<String> result = new ArrayList<String>();
        for (Products o : productDAO.getAllProducts()) {
           result.add(o.getProductName());
        }
        return result;
    }

    /**
     *
     * @return list of all customers
     */
    public ArrayList<String> getAllCustomerName() {
        ArrayList<String> result = new ArrayList<String>();
        for (Customers o : customerDAO.getAllCustomer()) {
            
            result.add(o.getCustomerName());
        }
        return result;
    }


}
