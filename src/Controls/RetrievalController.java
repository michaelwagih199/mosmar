package Controls;

import com.jfoenix.controls.JFXComboBox;
import dao.CustomerDAO;
import dao.ProductDAO;
import dao.ProductNumbersDAO;
import dao.RetrievalsDAO;
import dao.RetrivaldetailsDAO;
import entities.BillsDetails;
import entities.Customers;
import entities.Expenses;
import entities.Products;
import entities.Productsnumber;
import entities.Retrievals;
import entities.Retrivaldetails;
import entities.custom_orders;
import helper.Helper;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
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
    private Label txttotatalValue;
    @FXML
    private TableColumn<Retrivaldetails, Integer> colId_Details;
    @FXML
    private TableColumn<Retrivaldetails, Integer> colProductName_details;
    @FXML
    private TableColumn<Retrivaldetails, Float> colQuantity_details;
    @FXML
    private TableColumn<Retrivaldetails, Float> colValue_details;
    @FXML
    private TableView<Retrivaldetails> tableBilsdetail;

    //our var
    DecimalFormat df = new DecimalFormat("#.###");
    RetrievalsDAO retrievalsDAO = new RetrievalsDAO();
    RetrivaldetailsDAO retrivaldetailsDAO = new RetrivaldetailsDAO();
    ProductDAO productDAO = new ProductDAO();
    ProductNumbersDAO productNumbersDAO = new ProductNumbersDAO();
    Helper helper = new Helper();
    CustomerDAO customerDAO = new CustomerDAO();
    ObservableList<Retrivaldetails> rowProduct = FXCollections.observableArrayList();
    float totalValue = 0;
    @FXML
    private Label totalValues;
    @FXML
    private Pane paneDetailsShow;
    @FXML
    private TableView<?> tableBilsdetail1;
    @FXML
    private TableColumn<?, ?> colId_Details1;
    @FXML
    private TableColumn<?, ?> colProductName_details1;
    @FXML
    private TableColumn<?, ?> colQuantity_details1;
    @FXML
    private TableColumn<?, ?> colValue_details1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        compo_product_Type.getItems().addAll("منتجات بكجم");
        compo_product_Type.getItems().addAll("منتجات بالوحدة");
        loadoRetrivalTabData();
        loadoRetrivalDetailsTable();
        TextFields.bindAutoCompletion(etClientName, getAllCustomerName());
        formateDate();
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
            TextFields.bindAutoCompletion(etProductName, getAllProductrName(etProductName.getText().toString()));
        } else {
            TextFields.bindAutoCompletion(etProductName, getAllProductNumberName(etProductName.getText().toString()));
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
                paneDetailsShow.setVisible(true);
            }
        }
        
    }
    
    @FXML
    private void addProductClick(ActionEvent event) {
        
        try {
            Retrivaldetails retrivaldetails = new Retrivaldetails();
            int productId = 0;
            String knownUsFrom = compo_product_Type.getSelectionModel().getSelectedItem().toString();
            if (knownUsFrom.equals("منتجات بكجم")) {
                retrivaldetails.setProductCategoryId(1);
                productId = productDAO.getProductId(etProductName.getText().toString()).get(0).getProductid();
            } else if (knownUsFrom.equals("منتجات بالوحدة")) {
                retrivaldetails.setProductCategoryId(2);
                productId = productNumbersDAO.getProducNumbertId(etProductName.getText().toString()).get(0).getProductnumberid();
            }

            retrivaldetails.setExpenseValue(Float.parseFloat(etValue.getText().toString()));
            retrivaldetails.setProductID(productId);
            retrivaldetails.setQuantity(Float.parseFloat(etQuantity.getText().toString()));
            rowProduct.addAll(retrivaldetails);
            totalValue += Float.parseFloat(etValue.getText().toString());
            txttotatalValue.setText(String.valueOf(totalValue));

        } catch (Exception e) {
        }
        
    }

    @FXML
    private void saveBillsClick(ActionEvent event) throws Exception {
        
        try {
            // 1 insert retrival then insert to retrivalDetails
            String UUid = helper.generateCode();
            Retrievals retrievals = new Retrievals();
            retrievals.setCustomerId(customerDAO.getcCustomerId(etClientName.getText().toString()));
            retrievals.setUuid(UUid);
            retrievals.setBillsValue(Float.parseFloat(txttotatalValue.getText().toString()));
            retrievalsDAO.addRetrievals(retrievals);

            // insert retrival Detail
            for (Retrivaldetails retrivaldetails : rowProduct) {
                Retrivaldetails r = new Retrivaldetails();
                r.setExpenseValue(retrivaldetails.getExpenseValue());
                r.setProductCategoryId(retrivaldetails.getProductCategoryId());
                r.setProductID(retrivaldetails.getProductID());
                r.setQuantity(retrivaldetails.getQuantity());
                r.setRetrivalsId(retrievalsDAO.getLastOrderId(UUid));
                retrivaldetailsDAO.addRetrivaldetails(r);

            }
            
        } catch (Exception e) {
        }
        clearData();
        loadoRetrivalTabData();
    }

    @FXML
    private void closeAddBillsPane(MouseEvent event) {
        paneAddBillsR.setVisible(false);
        clearData();
    }
    
    public void loadoRetrivalTabData() {
        
        colClient.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("retrievalDate"));
        colId.setCellValueFactory(new PropertyValueFactory<>("retrievalId"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("retrievalTime"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("billsValue"));
        retrivalTable.setItems(retrievalsDAO.getAllRetrievals());
        
        totalValues.setText(df.format(retrievalsDAO.getTotalRetrive()));
    }
    
    public void loadoRetrivalDetailsTable() {        
        colId_Details.setCellValueFactory(new PropertyValueFactory<>("retrivalDetailsId"));
        colProductName_details.setCellValueFactory(new PropertyValueFactory<>("productID"));
        colQuantity_details.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colValue_details.setCellValueFactory(new PropertyValueFactory<>("expenseValue"));
        ubdateProduct();
        tableBilsdetail.setItems(rowProduct);
        
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

    /**
     *
     * updateProduct
     */
    public void ubdateProduct() {
        
        colProductName_details.setCellFactory(new Callback<TableColumn<Retrivaldetails, Integer>, TableCell<Retrivaldetails, Integer>>() {
            @Override
            public TableCell<Retrivaldetails, Integer> call(TableColumn<Retrivaldetails, Integer> param) {
                return new TableCell<Retrivaldetails, Integer>() {
                    
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {

                            //Retrivaldetails index = getTableView().getItems().get(getIndex());
                            for (Retrivaldetails retrivaldetails : rowProduct) {
                                String name = "m";
                                if (retrivaldetails.getProductCategoryId() == 1) {
                                    name = productDAO.getProductById(retrivaldetails.getProductID()).getProductName();
                                } else if (retrivaldetails.getProductCategoryId() == 2) {
                                    name = productNumbersDAO.getProductsnumberById(retrivaldetails.getProductID()).getProductnumberName();
                                }
                                setText(name);
                                
                            }
                            
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        
    }

    public void formateDate() {
        
        colDate.setCellFactory(column -> {
            TableCell<Retrievals, Date> cell = new TableCell<Retrievals, Date>() {
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
        
          colTime.setCellFactory(column -> {
            TableCell<Retrievals, Date> cell = new TableCell<Retrievals, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");

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
    
    public void clearData(){
        etProductName.clear();
        etQuantity.clear();
        etValue.clear();
        etClientName.clear();
        txttotatalValue.setText("0");
        tableBilsdetail.getItems().clear();
    }

    @FXML
    private void closeDetailsPane(MouseEvent event) {
        paneDetailsShow.setVisible(false);
    }
    
    
}
