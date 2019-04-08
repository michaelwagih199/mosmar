package Controls;

import com.jfoenix.controls.JFXComboBox;
import dao.CustomerDAO;
import dao.ProductDAO;
import entities.Customers;
import entities.Products;
import entities.custom_BuyTable;
import helper.FxDialogs;
import helper.Helper;
import helper.UsefulCalculas;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;

public class BuyController implements Initializable {

    @FXML
    private TextField etBuyType;
    @FXML
    private TextField txtInvoiceNomber;
    @FXML
    private TextField etClientName;
    @FXML
    private TextField etProductName;
    @FXML
    private TextField etQuantity;
    @FXML
    private Label txtUnite;
    @FXML
    private TextField txtTotal;
    @FXML
    private TextField et_paid_up;
    @FXML
    private TextField et_remaining;
    @FXML
    private TextField etDiscount;
    @FXML
    private JFXComboBox<String> compo_priceType;
    @FXML
    private TableView<custom_BuyTable> table;
    @FXML
    private TableColumn<custom_BuyTable, Float> col_total;
    @FXML
    private TableColumn<custom_BuyTable, Float> col_quantity;
    @FXML
    private TableColumn<custom_BuyTable, Float> col_product_price;
    @FXML
    private TableColumn<custom_BuyTable, String> col_product_name;
    @FXML
    private TableColumn<custom_BuyTable, Integer> col_id;
    @FXML
    private JFXComboBox<String> compoFunctionType;

    ProductDAO productDAO = new ProductDAO();
    CustomerDAO customerDAO = new CustomerDAO();
    Helper help = new Helper();
    UsefulCalculas usefullCalculas = new UsefulCalculas();
    @FXML
    private Label txtDate;
    
    ObservableList<custom_BuyTable> row = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        compo_priceType.getItems().addAll("قطاعى");
        compo_priceType.getItems().addAll("جملة");
        compo_priceType.getItems().addAll("شبة جملة");

        compoFunctionType.getItems().addAll("نقدى");
        compoFunctionType.getItems().addAll("آجل");

        TextFields.bindAutoCompletion(etProductName, getAllProductName());
        TextFields.bindAutoCompletion(etClientName, getAllCustomerName());
        txtDate.setText(help.getDate());
        
        addButtonDeleteToTable();
        
        System.out.println(usefullCalculas.getProductPartitionPriceforunit(27));
 
    }

    @FXML
    private void btn_submit_buy(ActionEvent event) {
    }

    @FXML
    private void btn_totalBuy_click(ActionEvent event) {
    }

    @FXML
    private void compo_priceType_click(ActionEvent event) {

        String knownUsFrom = compo_priceType.getSelectionModel().getSelectedItem().toString();
        etBuyType.setText(knownUsFrom);
        if (knownUsFrom.equals("قطاعى")) {
            txtUnite.setText("وحدة");
        } else {
            txtUnite.setText("كجم");
        }

    }

    @FXML
    private void compoFunctionTypeClick(ActionEvent event) {
        String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
        if (knownUsFrom.equals("آجل")) {

            et_paid_up.setDisable(false);

            FxDialogs.showInformation("من فضلك", "ادخل اسم العميل والمدفوع");
        } else if (knownUsFrom.equals("نقدى")) {

            et_paid_up.setDisable(true);

        }

    }

    @FXML
    private void btn_add_product_click(ActionEvent event) {
        boolean isMyComboBoxEmpty = compoFunctionType.getSelectionModel().isEmpty();
        try {
            //validate of input
            if (etBuyType.getText().toString().isEmpty()) {
                FxDialogs.showInformation("من فضلك", "اختر نوع عملية البيع");
            } else if (etProductName.getText().toString().isEmpty()) {
                FxDialogs.showInformation("من فضلك", "ادخل اسم المنتج");
            } else if (etQuantity.getText().toString().isEmpty()) {
                FxDialogs.showInformation("من فضلك", "ادخل الكمية المباعة");
            } else if (isMyComboBoxEmpty) {
                FxDialogs.showInformation("من فضلك", "اختر نوع العملية");
            } else {
                // add to table
                addProduct();
            }
        } catch (Exception e) {
        }
    }
    
    public void addProduct() {
        
        List<Products> items = productDAO.getProductId(etProductName.getText().toString());
        for (Products product : items) {
//            row.add(new custom_BuyTable(product.getProductid(),
//                    product.getProductName(),
//                    Float.parseFloat(etQuantity.getText().toString()),
//                    product.get,
//                    0));
        }
        loadTabData();
    }


    /**
     *
     * @return all product name
     */
    public ArrayList<String> getAllProductName() {

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

    public void loadTabData() {
       
        col_id.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        col_product_name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        col_product_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        table.setItems(row);
    }

    
        //delete row
    private void addButtonDeleteToTable() {
        TableColumn<custom_BuyTable, Void> colBtn = new TableColumn();

        Callback<TableColumn<custom_BuyTable, Void>, TableCell<custom_BuyTable, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<custom_BuyTable, Void>, TableCell<custom_BuyTable, Void>>() {
            @Override
            public TableCell<custom_BuyTable, Void> call(final TableColumn<custom_BuyTable, Void> param) {
                final TableCell<custom_BuyTable, Void> cell = new TableCell<custom_BuyTable, Void>() {

                    private final Button btn = new Button("مسح");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                            if (FxDialogs.showConfirm("مسح المنتج", "هل تريد مسح المنتج?", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
                                custom_BuyTable data = getTableView().getItems().get(getIndex());
                                try {
                                    table.getItems().remove(data);
                                } catch (Exception ex) {
                                    System.out.println(ex.getLocalizedMessage());
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

}
