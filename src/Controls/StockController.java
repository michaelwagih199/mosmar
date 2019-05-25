package Controls;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.ProductDAO;
import dao.ProductMappingDAO;
import dao.ProductNumbersDAO;
import entities.Productmappping;
import entities.Products;
import entities.Productsnumber;
import helper.FxDialogs;
import helper.Helper;
import helper.UsefulCalculas;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class StockController implements Initializable {

    @FXML
    private TableView<Products> table;
    @FXML
    private TableColumn<Products, String> col_product;
    @FXML
    private TableColumn<Products, Integer> col_weight;
    @FXML
    private TableColumn<Products, Float> col_unitInStock;
    @FXML
    private TableColumn<Products, Float> col_purchase_price;
    @FXML
    private TableColumn<Products, Float> col_gomla_price;
    @FXML
    private TableColumn<Products, Float> col_kata3y_price;
    @FXML
    private TableColumn<Products, Float> col_gomel_gomla;
    @FXML
    private Pane paneAddProduct;
    @FXML
    private JFXTextField et_product_weight;
    @FXML
    private JFXTextField et_product_name;
    @FXML
    private JFXTextField et_unitInStock;
    @FXML
    private JFXTextField et_purchase_price;
    @FXML
    private JFXTextField et_kata3y_price;
    @FXML
    private JFXTextField et_gomla_price;
    @FXML
    private JFXTextField et_gomlet_gomla;
    @FXML
    private Label etNotify;

    String defult = "0";
    String clickBool = "m";
    boolean isUnit = false;
    Helper helper = new Helper();
    ProductMappingDAO productMappingDAO = new ProductMappingDAO();

    private final ObservableList<Products> productList = FXCollections.observableArrayList();
    private final ProductDAO productDAO = new ProductDAO();
    ProductNumbersDAO productNumbersDAO = new ProductNumbersDAO();
    Products data;
    Productsnumber dataN;

    @FXML
    private JFXTextField etAlert;
    @FXML
    private Label txt_alert_unit;

    @FXML
    private TableColumn<Products, Integer> col_id;
    @FXML
    private JFXComboBox<String> compoCategory;
    @FXML
    private Pane paneAddProductNumber;
    @FXML
    private JFXTextField et_product_nameNumber;
    @FXML
    private JFXTextField et_unitInStockNumber;
    @FXML
    private JFXTextField et_purchase_priceNumber;
    @FXML
    private JFXTextField et_kata3y_priceNumber;
    @FXML
    private JFXTextField et_gomla_priceNumber;
    @FXML
    private JFXTextField et_gomlet_gomlaNumber;
    @FXML
    private Label etNotifyNutify;
    @FXML
    private JFXTextField etAlertNumber;
    @FXML
    private Label txt_alert_unit1;
    @FXML
    private Pane pane_add_choice;
    @FXML
    private Pane paneMapping;
    @FXML
    private ListView<String> listProducts;
    @FXML
    private Label mainProductName;
    @FXML
    private Label mainProductId;
    @FXML
    private Label subProductName;
    @FXML
    private Label subProductId;
    @FXML
    private TableView<Productsnumber> tableNumber;
    @FXML
    private TableColumn<Productsnumber, Integer> col_idNumber;
    @FXML
    private TableColumn<Productsnumber, String> col_productNumber;
    @FXML
    private TableColumn<Productsnumber, Float> col_unitInStockNumber;
    @FXML
    private TableColumn<Productsnumber, Float> col_purchase_priceNumber;
    @FXML
    private TableColumn<Productsnumber, Float> col_gomla_priceNumber;
    @FXML
    private TableColumn<Productsnumber, Float> col_kata3y_priceNumber;
    @FXML
    private TableColumn<Productsnumber, Float> col_gomel_gomlaNumber;
    @FXML
    private JFXButton btn_edit_product;
    @FXML
    private JFXButton btn_edit_product_number;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTabData();
        addButtonDeleteToTable();
        addButtonUbdateToTable();
        addProductListItems();
        addButtonProductMapppingToTable();
        compoCategory.getItems().addAll("منتجات بكجم");
        compoCategory.getItems().addAll("منتجات بالوحدة");
    }

    @FXML
    private void homeClick(MouseEvent event) throws IOException {
        helper.start("/mosmar/main.fxml", "الصفحة الرئيسية");
        helper.closePane(paneAddProduct);
    }

    @FXML
    private void addClick(MouseEvent event) {
        
        btn_edit_product.setVisible(false);
        btn_edit_product_number.setVisible(false);
        pane_add_choice.setVisible(true);
        
    }

    @FXML
    private void close_anchor_add(MouseEvent event) {

        paneAddProduct.setVisible(false);

    }

    @FXML
    private void btn_add_product_click(ActionEvent event) {
        // insert data to products 
        try {
            Products products = new Products();
            UsefulCalculas calc = new UsefulCalculas();
            products.setProductName(et_product_name.getText().toString());
            products.setProductWeight(Float.parseFloat(et_product_weight.getText().toString()));
            products.setPerchusePrice(Float.parseFloat(et_purchase_price.getText().toString()));
            products.setPartitionBuyPrice(Float.parseFloat(et_kata3y_price.getText().toString()));
            products.setGomelGomlaBuyPrice(Float.parseFloat(et_gomlet_gomla.getText().toString()));
            products.setGomlaBuyPrice(Float.parseFloat(et_gomla_price.getText().toString()));
            products.setUnitsWeightInStock(Float.parseFloat(et_unitInStock.getText().toString()));
            products.setAllertWeight(Float.parseFloat(etAlert.getText().toString()));
            productDAO.addProduct(products);
            etNotify.setText("تم الحفظ");
            clearText();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        loadTabData();

    }

    private void addButtonDeleteToTable() {
        TableColumn<Products, Void> colBtn = new TableColumn();

        Callback<TableColumn<Products, Void>, TableCell<Products, Void>> cellFactory;

        cellFactory = new Callback<TableColumn<Products, Void>, TableCell<Products, Void>>() {
            @Override
            public TableCell<Products, Void> call(final TableColumn<Products, Void> param) {
                final TableCell<Products, Void> cell = new TableCell<Products, Void>() {

                    private final Button btn = new Button("مسح");

                    {

                        btn.setOnAction((ActionEvent event) -> {

                            if (FxDialogs.showConfirm("مسح المنتج", "هل تريد مسح المنتج?", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
                                Products data = getTableView().getItems().get(getIndex());
                                try {
                                    productDAO.removeProduct(data.getProductid());
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

        TableColumn<Productsnumber, Void> colBtnNumber = new TableColumn();
        Callback<TableColumn<Productsnumber, Void>, TableCell<Productsnumber, Void>> cellFactoryN;
        cellFactoryN = new Callback<TableColumn<Productsnumber, Void>, TableCell<Productsnumber, Void>>() {
            @Override
            public TableCell<Productsnumber, Void> call(final TableColumn<Productsnumber, Void> param) {
                final TableCell<Productsnumber, Void> cell = new TableCell<Productsnumber, Void>() {

                    private final Button btn = new Button("مسح");

                    {

                        btn.setOnAction((ActionEvent event) -> {

                            if (FxDialogs.showConfirm("مسح المنتج", "هل تريد مسح المنتج?", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
                                Productsnumber data = getTableView().getItems().get(getIndex());
                                try {
                                    productNumbersDAO.removeProductsnumber(data.getProductnumberid());
                                    loadTabDataNumber();
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
        colBtnNumber.setCellFactory(cellFactoryN);
        table.getColumns().add(colBtn);
        tableNumber.getColumns().add(colBtnNumber);

    }

    public void addProductListItems() {
        ObservableList<String> productNames = FXCollections.observableArrayList();
        productList.addAll(productDAO.getAllProducts());

        for (Products products : productList) {
            productNames.add(products.getProductName());
        }
        listProducts.setItems(productNames);
    }

    private void addButtonProductMapppingToTable() {
        TableColumn<Products, Void> colBtn = new TableColumn();

        Callback<TableColumn<Products, Void>, TableCell<Products, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<Products, Void>, TableCell<Products, Void>>() {
            @Override
            public TableCell<Products, Void> call(final TableColumn<Products, Void> param) {
                final TableCell<Products, Void> cell = new TableCell<Products, Void>() {

                    private final Button btn = new Button("الملحقة");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                Products data = getTableView().getItems().get(getIndex());
                                mainProductName.setText(data.getProductName());
                                mainProductId.setText(String.valueOf(data.getProductid()));
                                paneMapping.setVisible(true);

                            } catch (Exception e) {
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

    private void addButtonUbdateToTable() {
        TableColumn<Products, Void> colBtn = new TableColumn();

        Callback<TableColumn<Products, Void>, TableCell<Products, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<Products, Void>, TableCell<Products, Void>>() {
            @Override
            public TableCell<Products, Void> call(final TableColumn<Products, Void> param) {
                final TableCell<Products, Void> cell = new TableCell<Products, Void>() {
                    
                    private final Button btn = new Button("تعديل");
                    
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            
                            data = getTableView().getItems().get(getIndex());
                            paneAddProduct.setVisible(true);
                            btn_edit_product.setVisible(true);
                            
                            try {
                                
                                et_product_name.setText(data.getProductName());
                                et_product_weight.setText(String.valueOf(data.getProductWeight()));
                                et_unitInStock.setText(String.valueOf(data.getUnitsWeightInStock()));
                                et_purchase_price.setText(String.valueOf(data.getPerchusePrice()));
                                et_kata3y_price.setText(String.valueOf(data.getPartitionBuyPrice()));
                                et_gomla_price.setText(String.valueOf(data.getGomlaBuyPrice()));
                                et_gomlet_gomla.setText(String.valueOf(data.getGomelGomlaBuyPrice()));
                                etAlert.setText(String.valueOf(data.getAllertWeight()));
                              
                            } catch (Exception ex) {
                                
                                Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        TableColumn<Productsnumber, Void> colBtnN = new TableColumn();
        Callback<TableColumn<Productsnumber, Void>, TableCell<Productsnumber, Void>> cellFactoryN;
        cellFactoryN = new Callback<TableColumn<Productsnumber, Void>, TableCell<Productsnumber, Void>>() {
            @Override
            public TableCell<Productsnumber, Void> call(final TableColumn<Productsnumber, Void> param) {
                final TableCell<Productsnumber, Void> cell = new TableCell<Productsnumber, Void>() {
                    
                    private final Button btn = new Button("تعديل");
                    
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            
                            dataN = getTableView().getItems().get(getIndex());
                            paneAddProductNumber.setVisible(true);
                            btn_edit_product_number.setVisible(true);
                            
                            try {
                                
                                et_product_nameNumber.setText(dataN.getProductnumberName());                               
                                et_unitInStockNumber.setText(String.valueOf(dataN.getUnitsInStock()));                               
                                et_purchase_priceNumber.setText(String.valueOf(dataN.getPerchusenumberPrice()));
                                et_kata3y_priceNumber.setText(String.valueOf(dataN.getPartitionnumberBuyPrice()));
                                et_gomla_priceNumber.setText(String.valueOf(dataN.getGomlaBuynumberPrice()));
                                et_gomlet_gomlaNumber.setText(String.valueOf(dataN.getGomelGomlanumberBuyPrice()));
                                etAlertNumber.setText(String.valueOf(dataN.getAllertNumber()));
                              
                            } catch (Exception ex) {
                                
                                Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
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
        colBtnN.setCellFactory(cellFactoryN);
        tableNumber.getColumns().add(colBtnN);
        
        
    }

    public void loadTabData() {

        col_product.setCellValueFactory(new PropertyValueFactory<>("productName"));
        col_weight.setCellValueFactory(new PropertyValueFactory<>("productWeight"));
        col_unitInStock.setCellValueFactory(new PropertyValueFactory<>("unitsWeightInStock"));
        col_gomel_gomla.setCellValueFactory(new PropertyValueFactory<>("gomelGomlaBuyPrice"));
        col_purchase_price.setCellValueFactory(new PropertyValueFactory<>("perchusePrice"));
        col_kata3y_price.setCellValueFactory(new PropertyValueFactory<>("partitionBuyPrice"));
        col_gomla_price.setCellValueFactory(new PropertyValueFactory<>("gomlaBuyPrice"));
        col_id.setCellValueFactory(new PropertyValueFactory<>("productid"));

        //updateStatusColor();
        table.setItems(productDAO.getAllProducts());

    }

    public void loadTabDataNumber() {

        col_productNumber.setCellValueFactory(new PropertyValueFactory<>("productnumberName"));
        col_unitInStockNumber.setCellValueFactory(new PropertyValueFactory<>("unitsInStock"));
        col_gomel_gomlaNumber.setCellValueFactory(new PropertyValueFactory<>("gomelGomlanumberBuyPrice"));
        col_purchase_priceNumber.setCellValueFactory(new PropertyValueFactory<>("perchusenumberPrice"));
        col_kata3y_priceNumber.setCellValueFactory(new PropertyValueFactory<>("partitionnumberBuyPrice"));
        col_gomla_priceNumber.setCellValueFactory(new PropertyValueFactory<>("gomlaBuynumberPrice"));
        col_idNumber.setCellValueFactory(new PropertyValueFactory<>("productnumberid"));
        //updateStatusColor();
        tableNumber.setItems(productNumbersDAO.getAllProductsnumber());

    }

    @FXML
    private void et_product_name_click(MouseEvent event) {
        etNotify.setText("");
    }

    public void clearText() {
        et_product_name.clear();
        et_gomla_price.clear();
        et_gomlet_gomla.clear();
        et_kata3y_price.clear();
        et_product_name.clear();
        et_product_weight.clear();
        et_purchase_price.clear();
        et_unitInStock.clear();
    }

    public void clearNumberText() {
        et_product_nameNumber.clear();
        et_purchase_priceNumber.clear();
        et_kata3y_priceNumber.clear();
        et_gomlet_gomlaNumber.clear();
        et_gomla_priceNumber.clear();
        et_unitInStockNumber.clear();
        etAlertNumber.clear();

    }

    public void updateStatusColor() {
        col_unitInStock.setCellFactory(column -> {
            return new TableCell<Products, Float>() {
                @Override
                protected void updateItem(Float item, boolean empty) {
                    super.updateItem(item, empty);

                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow<Products> currentRow = getTableRow();

                    if (!isEmpty()) {
                        for (Products o : productDAO.getAllProducts()) {
                            if (Float.compare(o.getUnitsWeightInStock(), o.getAllertWeight()) == 0) {
                                currentRow.setStyle("-fx-background-color:#FC0000");
                            } else {
                                currentRow.setStyle("-fx-background-color:#57B846");
                            }

                        }

                    }
                }
            };
        });

    }

    @FXML
    private void compoCategoryClick(ActionEvent event) {

        String knownUsFrom = compoCategory.getSelectionModel().getSelectedItem().toString();
        if (knownUsFrom.equals("منتجات بالوحدة")) {
            loadTabDataNumber();
            tableNumber.setVisible(true);
        } else {
            tableNumber.setVisible(false);
        }
    }

    @FXML
    private void close_anchor_add_number(MouseEvent event) {

    }

    @FXML
    private void btn_add_product_click_number(ActionEvent event) {
        // insert data to products 
        try {
            Productsnumber productsnumber = new Productsnumber();
            productsnumber.setProductnumberName(et_product_nameNumber.getText().toString());
            productsnumber.setPerchusenumberPrice(Float.parseFloat(et_purchase_priceNumber.getText().toString()));
            productsnumber.setPartitionnumberBuyPrice(Float.parseFloat(et_kata3y_priceNumber.getText().toString()));
            productsnumber.setGomelGomlanumberBuyPrice(Float.parseFloat(et_gomlet_gomlaNumber.getText().toString()));
            productsnumber.setGomlaBuynumberPrice(Float.parseFloat(et_gomla_priceNumber.getText().toString()));
            productsnumber.setUnitsInStock(Float.parseFloat(et_unitInStockNumber.getText().toString()));
            productsnumber.setAllertNumber(Float.parseFloat(etAlertNumber.getText().toString()));
            productNumbersDAO.addProductsnumber(productsnumber);
            etNotifyNutify.setText("تم الحفظ");
            clearNumberText();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        loadTabDataNumber();

    }

    @FXML
    private void KG_product(ActionEvent event) {
        paneAddProduct.setVisible(true);
        paneAddProductNumber.setVisible(false);
        pane_add_choice.setVisible(false);
    }

    @FXML
    private void UnitsProducts(ActionEvent event) {
        paneAddProduct.setVisible(false);
        paneAddProductNumber.setVisible(true);
        pane_add_choice.setVisible(false);
    }

    @FXML
    private void closeChoicePaneClick(MouseEvent event) {
        pane_add_choice.setVisible(false);
    }

    @FXML
    private void paneAddProductNumberClose(MouseEvent event) {
        paneAddProductNumber.setVisible(false);
    }

    @FXML
    private void closeMapping(MouseEvent event) {
        paneMapping.setVisible(false);
    }

    @FXML
    private void btnMappingSave(ActionEvent event) throws Exception {
        Productmappping productMapping = new Productmappping();
        try {

            productMapping.setProductmainId(Integer.parseInt(mainProductId.getText().toString()));
            productMapping.setSubProductId(Integer.parseInt(subProductId.getText().toString()));
            productMappingDAO.addProductmappping(productMapping);

        } catch (Exception e) {

        }
        paneMapping.setVisible(false);

    }

    @FXML
    private void selectItemList(MouseEvent event) {

        String mainProductNam = listProducts.getSelectionModel().getSelectedItem();
        subProductName.setText(mainProductNam);
        subProductId.setText(String.valueOf(productDAO.getProductId(mainProductNam).get(0).getProductid()));

    }

    @FXML
    private void btn_edit_productClick(ActionEvent event) throws Exception {
        try {
            data.setAllertWeight(Float.parseFloat(etAlert.getText().toString()));
            data.setGomelGomlaBuyPrice(Float.parseFloat(et_gomlet_gomla.getText().toString()));
            data.setGomlaBuyPrice(Float.parseFloat(et_gomla_price.getText().toString()));
            data.setPartitionBuyPrice(Float.parseFloat(et_kata3y_price.getText().toString()));
            data.setPerchusePrice(Float.parseFloat(et_purchase_price.getText().toString()));
            data.setProductName(et_product_name.getText().toString());
            data.setProductWeight(Float.parseFloat(et_purchase_price.getText().toString()));
            data.setUnitsWeightInStock(Float.parseFloat(et_unitInStock.getText().toString()));
            productDAO.editProduct(data);
            table.refresh();
        
        } catch (Exception e) {
        }
          
    }

    @FXML
    private void btn_edit_product_click_number(ActionEvent event) {
        
         try {             
            dataN.setGomelGomlanumberBuyPrice(Float.parseFloat(et_gomlet_gomlaNumber.getText().toString()));
            dataN.setGomlaBuynumberPrice(Float.parseFloat(et_gomla_priceNumber.getText().toString()));
            dataN.setPartitionnumberBuyPrice(Float.parseFloat(et_kata3y_priceNumber.getText().toString()));
            dataN.setPerchusenumberPrice(Float.parseFloat(et_purchase_priceNumber.getText().toString()));
            dataN.setProductnumberName(et_product_nameNumber.getText().toString());
            dataN.setAllertNumber(Float.parseFloat(etAlertNumber.getText().toString()));
            dataN.setUnitsInStock(Float.parseFloat(et_unitInStockNumber.getText().toString()));           
            productNumbersDAO.editProductsnumber(dataN);
            tableNumber.refresh();
            
        } catch (Exception e) {
            
        }
        
    }
    
   
}
