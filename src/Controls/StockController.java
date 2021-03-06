package Controls;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import dao.ProductDAO;
import dao.ProductMappingDAO;
import dao.ProductNumbersDAO;
import entities.Expenses;

import entities.Productmappping;
import entities.Products;
import entities.Productsnumber;
import helper.FxDialogs;
import helper.Helper;
import helper.UsefulCalculas;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;

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
    private Label capitalWeight;
  
    DecimalFormat df = new DecimalFormat("#.###");
    private Label capitalUnits;
    @FXML
    private Label subId1;
    @FXML
    private Label subId2;
    @FXML
    private Label subId3;
    @FXML
    private Label subId4;
    @FXML
    private Label lP1;
    @FXML
    private Label lP2;
    @FXML
    private Label lP3;
    @FXML
    private Label lp4;
    private Label index;
    @FXML
    private Label lableIndex;
    @FXML
    private JFXTextField etSearch;
    @FXML
    private JFXTextField etSearchMapping;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        loadTabData();
        addButtonDeleteToTable();
        addButtonUbdateToTable();
        updateStatusColor();
        addProductListItems();
        addButtonProductMapppingToTable();
        compoCategory.getItems().addAll("منتجات بكجم");
        compoCategory.getItems().addAll("منتجات بالوحدة");   
        TextFields.bindAutoCompletion(etSearchMapping , getAllProductrName(etSearch.getText().toString()));
     
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
                                clearMapping();
                                
                                Products data = getTableView().getItems().get(getIndex());
                              
                                mainProductName.setText(data.getProductName());
                                mainProductId.setText(String.valueOf(data.getProductid()));  
                                paneMapping.setVisible(true);
                                
                                lP1.setText(productMappingDAO.getSubProduct1(data.getProductid()));
                                lP2.setText(productMappingDAO.getSubProduct2(data.getProductid()));
                                lP3.setText(productMappingDAO.getSubProduct3(data.getProductid()));
                                lp4.setText(productMappingDAO.getSubProduct3(data.getProductid()));
                                
                               lableIndex.setText(String.valueOf(productMappingDAO.getIdFromMain(data.getProductid())));
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
        
        col_unitInStock.setCellFactory(new Callback<TableColumn<Products, Float>, TableCell<Products, Float>>() {
            @Override
            public TableCell<Products, Float> call(TableColumn<Products, Float> param) {
                return new TableCell<Products, Float>() {

                    @Override
                    protected void updateItem(Float item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                         Products p  = getTableView().getItems().get(getIndex());
                         setText(String.valueOf(item));
                         setAlignment(Pos.CENTER);
                         if (p.getUnitsWeightInStock() <= p.getAllertWeight()) {
                            setStyle("-fx-text-fill: red; -fx-font-weight:bold;");
                        }else
                             setStyle("-fx-text-fill: green; -fx-font-weight:bold;");;

                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        
         col_unitInStockNumber.setCellFactory(new Callback<TableColumn<Productsnumber, Float>, TableCell<Productsnumber, Float>>() {
            @Override
            public TableCell<Productsnumber, Float> call(TableColumn<Productsnumber, Float> param) {
                return new TableCell<Productsnumber, Float>() {

                    @Override
                    protected void updateItem(Float item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                         Productsnumber p  = getTableView().getItems().get(getIndex());
                         setText(String.valueOf(item));
                         setAlignment(Pos.CENTER);
                         if (p.getUnitsInStock() <= p.getAllertNumber()) {
                            setStyle("-fx-text-fill: red; -fx-font-weight:bold;");
                        }else
                             setStyle("-fx-text-fill: green; -fx-font-weight:bold;");;

                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }
    

    @FXML
    private void compoCategoryClick(ActionEvent event) {
        String knownUsFrom = compoCategory.getSelectionModel().getSelectedItem().toString();
        if (knownUsFrom.equals("منتجات بالوحدة")) {
            loadTabDataNumber();
            tableNumber.setVisible(true);
            TextFields.bindAutoCompletion(etSearch , getAllProductNumberName(etSearch.getText().toString()));
            
        } else {            
            tableNumber.setVisible(false);
            TextFields.bindAutoCompletion(etSearch , getAllProductrName(etSearch.getText().toString()));            
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
        productMapping.setProductmainId(Integer.parseInt(mainProductId.getText().toString()));
        if (subId1.getText().toString() == "") {
            subId1.setText("0");
        }
        productMapping.setSubProductId1(Integer.parseInt(subId1.getText().toString()));
         if (subId2.getText().toString() == "") {
            subId2.setText("0");
        }
        productMapping.setSubProduct2(Integer.parseInt(subId2.getText().toString()));
         if (subId3.getText().toString() == "") {
            subId3.setText("0");
        }
         
        productMapping.setSubProduct3(Integer.parseInt(subId3.getText().toString()));
         if (subId4.getText().toString() == "") {
            subId4.setText("0");
        }
        productMapping.setSubProduct4(Integer.parseInt(subId4.getText().toString()));
        productMappingDAO.addProductmappping(productMapping);

        // paneMapping.setVisible(false);
        //clearMapping();
    }

    @FXML
    private void selectItemList(MouseEvent event) {
        
        String mainProductNam = listProducts.getSelectionModel().getSelectedItem();
        if (subId1.getText().toString().isEmpty()) {
             subId1.setText(String.valueOf(productDAO.getProductId(mainProductNam).get(0).getProductid()));
             lP1.setText(mainProductNam);
        }else if (subId2.getText().toString().isEmpty()) {
           subId2.setText(String.valueOf(productDAO.getProductId(mainProductNam).get(0).getProductid())); 
           lP2.setText(mainProductNam);
        }else if (subId3.getText().toString().isEmpty()) {
            subId3.setText(String.valueOf(productDAO.getProductId(mainProductNam).get(0).getProductid()));
            lP3.setText(mainProductNam);
        }else if (subId4.getText().toString().isEmpty()) {
            subId4.setText(String.valueOf(productDAO.getProductId(mainProductNam).get(0).getProductid())); 
            lp4.setText(mainProductNam);
        }

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
            data.setProductWeight(Float.parseFloat(et_product_weight.getText().toString()));
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
    
   /**
    * helper methods
    */
  
    
    public void clearMapping() {       
        subId1.setText("");
        subId2.setText("");
        subId3.setText("");
        subId4.setText("");
        lP1.setText("");
        lP2.setText("");
        lP3.setText("");
        lp4.setText("");
        
    }

    @FXML
    private void btnMappingDelete(ActionEvent event) throws Exception {
        try {
            productMappingDAO.removeProductmappping(Integer.parseInt(lableIndex.getText().toString()));
            clearMapping();
            paneMapping.setVisible(false);
        } catch (Exception e) {
        }

    }

    @FXML
    private void etSearchKeeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String knownUsFrom = compoCategory.getSelectionModel().getSelectedItem().toString();
                if (knownUsFrom.equals("منتجات بالوحدة")) {
                    loadTabDataNumberSerach();
                     
                }else if (knownUsFrom.equals("منتجات بكجم")){
                    loadTabDataSearch();
                }

                }catch (Exception e) {
            }
             //compoCategory.setValue("نوع المنتج");
               
            }
        }

    @FXML
    private void etSearchMousePressed(MouseEvent event) {
        etSearch.clear();
        loadTabData();
        loadTabDataNumber();
       // compoCategory.setValue("نوع المنتج");
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
    
    
    public void loadTabDataSearch() {
        
        col_product.setCellValueFactory(new PropertyValueFactory<>("productName"));
        col_weight.setCellValueFactory(new PropertyValueFactory<>("productWeight"));
        col_unitInStock.setCellValueFactory(new PropertyValueFactory<>("unitsWeightInStock"));
        col_gomel_gomla.setCellValueFactory(new PropertyValueFactory<>("gomelGomlaBuyPrice"));
        col_purchase_price.setCellValueFactory(new PropertyValueFactory<>("perchusePrice"));
        col_kata3y_price.setCellValueFactory(new PropertyValueFactory<>("partitionBuyPrice"));
        col_gomla_price.setCellValueFactory(new PropertyValueFactory<>("gomlaBuyPrice"));
        col_id.setCellValueFactory(new PropertyValueFactory<>("productid"));
        table.setItems(productDAO.selectProductByName(etSearch.getText().toString()));
    }

    public void loadTabDataNumberSerach() {

        col_productNumber.setCellValueFactory(new PropertyValueFactory<>("productnumberName"));
        col_unitInStockNumber.setCellValueFactory(new PropertyValueFactory<>("unitsInStock"));
        col_gomel_gomlaNumber.setCellValueFactory(new PropertyValueFactory<>("gomelGomlanumberBuyPrice"));
        col_purchase_priceNumber.setCellValueFactory(new PropertyValueFactory<>("perchusenumberPrice"));
        col_kata3y_priceNumber.setCellValueFactory(new PropertyValueFactory<>("partitionnumberBuyPrice"));
        col_gomla_priceNumber.setCellValueFactory(new PropertyValueFactory<>("gomlaBuynumberPrice"));
        col_idNumber.setCellValueFactory(new PropertyValueFactory<>("productnumberid"));
        //updateStatusColor();
        ObservableList<Productsnumber> row = FXCollections.observableArrayList();
        row.addAll(productNumbersDAO.getProducNumbertId(etSearch.getText().toString()));
        tableNumber.setItems(row);
    }

    @FXML
    private void etSearchMappingKeeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                ObservableList<String> productNames = FXCollections.observableArrayList();
                productNames.add(productDAO.selectProductByName(etSearchMapping.getText().toString()).get(0).getProductName());          
                listProducts.setItems(productNames);

            } catch (Exception e) {
            }
        }

    }

    @FXML
    private void etSearchMappingMousePressed(MouseEvent event) {
        etSearchMapping.clear();
        listProducts.getItems().clear();
        //addProductListItems();
    }

   
    
}
