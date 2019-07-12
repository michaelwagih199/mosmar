package Controls;

import com.jfoenix.controls.JFXButton;
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
import helper.FxDialogs;
import helper.Helper;
import helper.UsefulCalculas;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
     UsefulCalculas usefullCalculas = new UsefulCalculas();
     
    ObservableList<Retrivaldetails> rowProduct = FXCollections.observableArrayList();
    float totalValue = 0;
    @FXML
    private Label totalValues;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXComboBox<String> compo_priceType;
    @FXML
    private Label txtUnite;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        compo_product_Type.getItems().addAll("منتجات بكجم");
        compo_product_Type.getItems().addAll("منتجات بالوحدة");
        compo_priceType.getItems().addAll("قطاعى");
        compo_priceType.getItems().addAll("جملة");
        compo_priceType.getItems().addAll("جملة الجملة");
        
        loadoRetrivalTabData();
        loadoRetrivalDetailsTable();
        TextFields.bindAutoCompletion(etClientName, getAllCustomerName());
        formateDate();
        //addButtonDeleteToTable();
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
            compo_priceType.setVisible(true);
            txtUnite.setText("كجم");
        } else {
            TextFields.bindAutoCompletion(etProductName, getAllProductNumberName(etProductName.getText().toString()));
            compo_priceType.setVisible(false);
            txtUnite.setText("وحدة");
        }

    }

    @FXML
    private void addRetrivalClick(MouseEvent event) {
        btnAdd.setDisable(false);
        btnSave.setDisable(false);
        paneAddBillsR.setVisible(true);
    }

    @FXML
    private void retrivalTableClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                Retrievals list = retrivalTable.getSelectionModel().getSelectedItem();
                btnAdd.setDisable(true);
                btnSave.setDisable(true);
                paneAddBillsR.setVisible(true);

                loadoRetrivalDetailsTableById(list.getRetrievalId());

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
            //update stock
            for (Retrivaldetails o : rowProduct) {
                if (o.getProductCategoryId().equals(2)) {

                    float unitsInStock = productNumbersDAO.getProductsnumberById(o.getProductID()).getUnitsInStock();
                    float quantityOfOrder = o.getQuantity();
                    productNumbersDAO.updateStock(unitsInStock + quantityOfOrder, o.getProductID());

                } else if (o.getProductCategoryId() == 1) {
                    float unitWeight = productDAO.getProductById(o.getProductID()).getProductWeight();
                    float allWeightInStock = productDAO.getProductById(o.getProductID()).getUnitsWeightInStock();
                    float weighofOrder = 0;
                    String knownUsFrom = compo_priceType.getSelectionModel().getSelectedItem().toString();
                    if (knownUsFrom.equals("قطاعى")) {
                        weighofOrder = usefullCalculas.getwightofUnitsToUpdate(o.getQuantity(), unitWeight);
                        productDAO.updateWeight(allWeightInStock + weighofOrder, o.getProductID());
                    } else {
//              weighofOrder = usefullCalculas.getwightofUnitsToUpdate(o.getQuantity(), unitWeight,true);
                        productDAO.updateWeight(allWeightInStock + o.getQuantity(), o.getProductID());
                    }

                }
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
        try {
            //colClient.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("retrievalDate"));
            colId.setCellValueFactory(new PropertyValueFactory<>("retrievalId"));
            colTime.setCellValueFactory(new PropertyValueFactory<>("retrievalTime"));
            colValue.setCellValueFactory(new PropertyValueFactory<>("billsValue"));
            retrivalTable.setItems(retrievalsDAO.getAllRetrievals());
            ubdateCustomers();
            totalValues.setText(df.format(retrievalsDAO.getTotalRetrive()));
            
        } catch (Exception e) {
        }

    }

    public void loadoRetrivalDetailsTable() {
        colId_Details.setCellValueFactory(new PropertyValueFactory<>("retrivalDetailsId"));
        colProductName_details.setCellValueFactory(new PropertyValueFactory<>("productID"));
        colQuantity_details.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colValue_details.setCellValueFactory(new PropertyValueFactory<>("expenseValue"));
        ubdateProduct();
        tableBilsdetail.setItems(rowProduct);
    }

    public void loadoRetrivalDetailsTableById(int id) {
        colId_Details.setCellValueFactory(new PropertyValueFactory<>("retrivalDetailsId"));
        colProductName_details.setCellValueFactory(new PropertyValueFactory<>("productID"));
        colQuantity_details.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colValue_details.setCellValueFactory(new PropertyValueFactory<>("expenseValue"));
        ubdateProduct2();
        tableBilsdetail.setItems(retrivaldetailsDAO.getRetrivaldetailsId(id));
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

    public void ubdateProduct2() {

        colProductName_details.setCellFactory(new Callback<TableColumn<Retrivaldetails, Integer>, TableCell<Retrivaldetails, Integer>>() {
            @Override
            public TableCell<Retrivaldetails, Integer> call(TableColumn<Retrivaldetails, Integer> param) {
                return new TableCell<Retrivaldetails, Integer>() {

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {

                            Retrivaldetails index = getTableView().getItems().get(getIndex());

                            String name = "m";
                            if (index.getProductCategoryId() == 1) {
                                name = productDAO.getProductById(index.getProductID()).getProductName();
                            } else if (index.getProductCategoryId() == 2) {
                                name = productNumbersDAO.getProductsnumberById(index.getProductID()).getProductnumberName();
                            }
                            setText(name);

                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

    }

    public void ubdateCustomers() {

        colClient.setCellFactory(new Callback<TableColumn<Retrievals, Integer>, TableCell<Retrievals, Integer>>() {
            @Override
            public TableCell<Retrievals, Integer> call(TableColumn<Retrievals, Integer> param) {
                return new TableCell<Retrievals, Integer>() {

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {

                            Retrievals index = getTableView().getItems().get(getIndex());

                            String name = "m";
                            name= customerDAO.getCustomersById(index.getCustomerId()).getCustomerName();
                                   
                            setText(name);

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

    public void clearData() {
        etProductName.clear();
        etQuantity.clear();
        etValue.clear();
        etClientName.clear();
        txttotatalValue.setText("0");
        tableBilsdetail.getItems().clear();
    }

    //delete row
    private void addButtonDeleteToTable() {
        TableColumn<Retrievals, Void> colBtn = new TableColumn();

        Callback<TableColumn<Retrievals, Void>, TableCell<Retrievals, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<Retrievals, Void>, TableCell<Retrievals, Void>>() {
            @Override
            public TableCell<Retrievals, Void> call(final TableColumn<Retrievals, Void> param) {
                final TableCell<Retrievals, Void> cell = new TableCell<Retrievals, Void>() {

                    private final Button btn = new Button("مسح");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                            if (FxDialogs.showConfirm("مسح المنتج", "هل تريد مسح المنتج?", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
                                Retrievals data = getTableView().getItems().get(getIndex());
                                try {
                                    retrievalsDAO.removeRetrievals(data.getRetrievalId());

                                    loadoRetrivalTabData();
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
        retrivalTable.getColumns().add(colBtn);
    }

    @FXML
    private void compo_priceType_click(ActionEvent event) {
          String knownUsFrom = compo_priceType.getSelectionModel().getSelectedItem().toString();
        if (knownUsFrom.equals("قطاعى")) {
            txtUnite.setText("وحدة");
        } else {
            txtUnite.setText("كجم");
        }
    }
    
   


}
