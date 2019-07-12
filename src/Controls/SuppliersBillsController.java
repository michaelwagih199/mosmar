package Controls;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dao.BillsDetailsDAO;
import dao.ProductDAO;
import dao.ProductNumbersDAO;
import dao.SuppliersBillsDAO;
import dao.SuppliersDAO;
import entities.BillsDetails;
import entities.Products;
import entities.Productsnumber;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
    private JFXComboBox<String> comboCategory;
    @FXML
    private TableView<BillsDetails> tableProducts;
    @FXML
    private TableColumn<BillsDetails, Integer> col_productId;
    @FXML
    private TableColumn<BillsDetails, Integer> col_productName;
    @FXML
    private TableColumn<BillsDetails, Float> colproductQuantity;
    @FXML
    private TableColumn<BillsDetails, Float> colPrice;
    @FXML
    private TableColumn<BillsDetails, Float> coltotal;
    @FXML
    private TableColumn<BillsDetails, Integer> colProducttype;
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
    ProductDAO productDAO = new ProductDAO();
    ProductNumbersDAO productNumbersDAO = new ProductNumbersDAO();
    BillsDetailsDAO billsDetailsDAO = new BillsDetailsDAO();
    int categoryId = 0;
    int billsId = 0;
    @FXML
    private TableView<SuppliersBills> table;
    @FXML
    private TableColumn<SuppliersBills, String> colNotes;
    @FXML
    private TableColumn<SuppliersBills, Float> col_paidBils;
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
    @FXML
    private Label labelBillsId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TextFields.bindAutoCompletion(etSuppliersId, getSupplierName(etSuppliersId.getText().toString()));
        loadTabBillsData();
        formatDate();
        ubdateSuppliers();
        //loadTabProductData();
        comboCategory.getItems().addAll("منتجات بكجم");
        comboCategory.getItems().addAll("منتجات بالوحدة");
        //System.out.println(billsDetailsDAO.getBillsDetailsId(8));
        addButtonDeleteToTable();
    }

    @FXML
    private void tableClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                try {
                    SuppliersBills index = table.getSelectionModel().getSelectedItem();
                    labelBillsId.setText(index.getSuppliersBilsId().toString());
                    PanebilsDetails.setVisible(true);
                    billsId = index.getSuppliersBilsId();

                } catch (Exception e) {
                }

                loadTabProductData();
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
        try {

            BillsDetails billsDetails = new BillsDetails();
            billsDetails.setCategoryId(categoryId);
            billsDetails.setPrice(Float.parseFloat(etPrice.getText().toString()));
            billsDetails.setQuantity(Float.parseFloat(etQuantity.getText().toString()));
            billsDetails.setSuppliersBilsId(Integer.parseInt(labelBillsId.getText().toString()));
            billsDetails.setTotal(Float.parseFloat(etPrice.getText().toString())
                    * Float.parseFloat(etQuantity.getText().toString()));
            billsDetails.setProductId(getProductId());
            billsDetailsDAO.addBillsDetails(billsDetails);

            updateStockDetails();

        } catch (Exception e) {

        }
        loadTabProductData();
        clearTextBillsDetails();

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

    @FXML
    private void comboCategoryClick(ActionEvent event) {
        String knownUsFrom = comboCategory.getSelectionModel().getSelectedItem().toString();
        if (knownUsFrom.equals("منتجات بكجم")) {
            TextFields.bindAutoCompletion(etProduct, getAllProductrName(etProduct.getText().toString()));
            categoryId = 1;
        } else {
            TextFields.bindAutoCompletion(etProduct, getAllProductNumberName(etProduct.getText().toString()));
            categoryId = 2;
        }
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

    public void clearTextBills() {
        etPaidBils.clear();
        etRemainig.clear();
        etTotalBils.clear();
        etSuppliersId.clear();
    }

    public void clearTextBillsDetails() {
        etProduct.clear();
        etQuantity.clear();
        etPrice.clear();
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
        ubdateProduct();
        table.setItems(suppliersBillsDAO.getSuppliersBills());

    }

    public void loadTabProductData() {
        col_productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        col_productName.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colproductQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        coltotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tableProducts.setItems(billsDetailsDAO.getBillsDetailsId(billsId));
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

    /**
     *
     * updateProduct
     */
    public void ubdateProduct() {

        col_productName.setCellFactory(new Callback<TableColumn<BillsDetails, Integer>, TableCell<BillsDetails, Integer>>() {
            @Override
            public TableCell<BillsDetails, Integer> call(TableColumn<BillsDetails, Integer> param) {
                return new TableCell<BillsDetails, Integer>() {

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {

                            BillsDetails index = getTableView().getItems().get(getIndex());
                            String name = "m";
                            if (index.getCategoryId() == 1) {
                                name = productDAO.getProductById(index.getProductId()).getProductName();
                            } else if (index.getCategoryId() == 2) {
                                name = productNumbersDAO.getProductsnumberById(index.getProductId()).getProductnumberName();
                            }
                            setText(name);
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        colProducttype.setCellFactory(new Callback<TableColumn<BillsDetails, Integer>, TableCell<BillsDetails, Integer>>() {
            @Override
            public TableCell<BillsDetails, Integer> call(TableColumn<BillsDetails, Integer> param) {
                return new TableCell<BillsDetails, Integer>() {

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {

                            BillsDetails index = getTableView().getItems().get(getIndex());
                            String name = "m";
                            if (index.getCategoryId() == 1) {
                                name = "وزن";
                            } else if (index.getCategoryId() == 2) {
                                name = "قطعة";
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

    public List<String> getAllProductrName(String start) {
        ArrayList<String> result = new ArrayList<String>();
        for (Products o : productDAO.getAllProducts()) {
            result.add(o.getProductName());
        }

        return result;
    }

    public ArrayList<String> getAllProductNumberName(String start) {
        ArrayList<String> result = new ArrayList<String>();
        for (Productsnumber o : productNumbersDAO.getAllProductsnumber()) {
            result.add(o.getProductnumberName());
        }
        return result;
    }

    private int getProductId() {
        int productId = 0;
        if (categoryId == 1) {
            productId = productDAO.getProductId(etProduct.getText().toString()).get(0).getProductid();

        } else if (categoryId == 2) {
            productId = productNumbersDAO.getProducNumbertId(etProduct.getText().toString()).get(0).getProductnumberid();
        }

        return productId;

    }

    private void updateStockDetails() {
        String knownUsFrom = comboCategory.getSelectionModel().getSelectedItem().toString();
        if (knownUsFrom.equals("منتجات بكجم")) {
            List<BillsDetails> items = billsDetailsDAO.getBillsDetailsId(billsId);
            for (BillsDetails item : items) {
                int productid = item.getProductId();
                float quantityofBils = item.getQuantity();
                float numberUnit = productDAO.getProductById(productid).getUnitsWeightInStock();
                float newQuantity = quantityofBils + numberUnit;
                productDAO.updateWeight(newQuantity, productid);
            }
        } else if (knownUsFrom.equals("منتجات بالوحدة")) {
            List<BillsDetails> items = billsDetailsDAO.getBillsDetailsId(billsId);
            for (BillsDetails item : items) {
                int productid = item.getProductId();
                float quantityofBils = item.getQuantity();
                float numberUnit = productNumbersDAO.getProductsnumberById(productid).getUnitsInStock();
                float newQuantity = quantityofBils + numberUnit;
                productNumbersDAO.updateStock(newQuantity, productid);
            }

        }

    }

    //delete row
    private void addButtonDeleteToTable() {
        TableColumn<BillsDetails, Void> colBtn = new TableColumn();

        Callback<TableColumn<BillsDetails, Void>, TableCell<BillsDetails, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<BillsDetails, Void>, TableCell<BillsDetails, Void>>() {
            @Override
            public TableCell<BillsDetails, Void> call(final TableColumn<BillsDetails, Void> param) {
                final TableCell<BillsDetails, Void> cell = new TableCell<BillsDetails, Void>() {

                    private final Button btn = new Button("مسح");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                            if (FxDialogs.showConfirm("مسح المنتج", "هل تريد مسح المنتج?", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
                                BillsDetails data = getTableView().getItems().get(getIndex());
                                try {
                                    billsDetailsDAO.removeBillsDetails(data.getBilsDetailsId());
                                    // remove from stock
                                    //1 number
                                    if (data.getCategoryId().equals(2)) {
                                        float unitsInStock = productNumbersDAO.getProductsnumberById(data.getProductId()).getUnitsInStock();
                                        float quantityOfOrder = data.getQuantity();
                                        productNumbersDAO.updateStock(unitsInStock - quantityOfOrder, data.getProductId());
                                    } else if (data.getCategoryId().equals(1)) {
                                        float unitWeight = productDAO.getProductById(data.getProductId()).getProductWeight();
                                        float allWeightInStock = productDAO.getProductById(data.getProductId()).getUnitsWeightInStock();
                                        float weighofOrder = 0;
                                        productDAO.updateWeight(allWeightInStock - data.getQuantity(), data.getProductId());
                                    }
                                    loadTabProductData();
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
        tableProducts.getColumns().add(colBtn);
    }

}
