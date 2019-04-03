/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import com.jfoenix.controls.JFXTextField;

import dao.ProductDAO;
import entities.Products;
import helper.FxDialogs;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author OM EL NOUR
 */
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
    @FXML
    private Pane paneCalc;
    @FXML
    private Label txt_calc_result;
    
    String defult = "0";
    String clickBool = "m";
    
    private final ObservableList<Products> productList = FXCollections.observableArrayList();
    private final ProductDAO productDAO  = new ProductDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTabData();
        addButtonDeleteToTable();
        addButtonUbdateToTable();
       // productDAO.selectProduct();
    }    

    @FXML
    private void homeClick(MouseEvent event) {
    }

    @FXML
    private void addClick(MouseEvent event) {
        paneAddProduct.setVisible(true);
    }

    @FXML
    private void close_anchor_add(MouseEvent event) {
          paneAddProduct.setVisible(false);
          paneCalc.setVisible(false);
    }

    @FXML
    private void btn_add_product_click(ActionEvent event) {
        // insert data
        try {
                Products products = new Products();
                products.setProductName(et_product_name.getText().toString());
                products.setProductWeight(Integer.parseInt(et_product_weight.getText().toString()));
                products.setPerchusePrice(Float.parseFloat(et_purchase_price.getText().toString()));
                products.setPartitionBuyPrice(Float.parseFloat(et_kata3y_price.getText().toString()));
                products.setGomelGomlaBuyPrice(Float.parseFloat(et_gomlet_gomla.getText().toString()));
                products.setGomlaBuyPrice(Float.parseFloat(et_gomla_price.getText().toString()));
                products.setUnitInStock(Float.parseFloat(et_unitInStock.getText().toString()));
                //insert
                
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
                                    
                                    Products data = getTableView().getItems().get(getIndex());
                                    System.out.println("selectedData: " + data);
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

   
    //calculator
    
    
    @FXML
    private void btn_0_click(ActionEvent event) {
        
    }

    @FXML
    private void btn_opoint_click(ActionEvent event) {
      
    }

    @FXML
    private void btn_7_click(ActionEvent event) {
        
    }

    @FXML
    private void btn_8_click(ActionEvent event) {
      
    }

    @FXML
    private void btn_9_click(ActionEvent event) {
        
    }

    @FXML
    private void btn_4_click(ActionEvent event) {
       
    }

    @FXML
    private void btn_5_click(ActionEvent event) {
          
    }

    @FXML
    private void btn_6_click(ActionEvent event) {
        
    }

    @FXML
    private void btn_1_click(ActionEvent event) {
         
    }

    @FXML
    private void btn_2_click(ActionEvent event) {
       
    }

    @FXML
    private void btn_3_click(ActionEvent event) {
         
    }

    @FXML
    private void btn_cancel_calc_click(ActionEvent event) {
      
    }

    @FXML
    private void btn_ok_calc_click(ActionEvent event) {
      
       
    }
    
    
      public void loadTabData(){
          
      col_product.setCellValueFactory(new PropertyValueFactory<>("productName"));
      col_weight.setCellValueFactory(new PropertyValueFactory<>("productWeight"));
      col_unitInStock.setCellValueFactory(new PropertyValueFactory<>("unitInStock"));
      col_gomel_gomla.setCellValueFactory(new PropertyValueFactory<>("gomelGomlaBuyPrice"));
      col_purchase_price.setCellValueFactory(new PropertyValueFactory<>("perchusePrice")); 
      col_kata3y_price.setCellValueFactory(new PropertyValueFactory<>("partitionBuyPrice"));
      col_gomla_price.setCellValueFactory(new PropertyValueFactory<>("gomlaBuyPrice"));
     
      table.setItems(productDAO.getAllProducts()); 
    }
      

    
    @FXML
    private void et_product_name_click(MouseEvent event) {
        etNotify.setText("");
    }
    
    public void clearText(){
        et_product_name.clear();
        et_gomla_price.clear();
        et_gomlet_gomla.clear();
        et_kata3y_price.clear();
        et_product_name.clear();
        et_product_weight.clear();
        et_purchase_price.clear();
        et_unitInStock.clear();
    }
      
      
    
}
