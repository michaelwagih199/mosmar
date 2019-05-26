package Controls;

import com.jfoenix.controls.JFXComboBox;
import dao.CustomerDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import dao.ProductMappingDAO;
import dao.ProductNumbersDAO;
import entities.Customers;
import entities.Products;
import entities.Productsnumber;
import entities.custom_BuyTable;
import helper.FxDialogs;
import helper.Helper;
import helper.SubmitBuy_helper;
import helper.UsefulCalculas;
import helper.UsefulCalculasNumberProduct;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;

public class BuyController implements Initializable {

    @FXML
    private TextField etBuyType;
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
    OrderDAO orderDAO = new OrderDAO();
    ProductNumbersDAO productNumbersDAO = new ProductNumbersDAO();
    ProductMappingDAO productMappingDAO = new ProductMappingDAO();
    UsefulCalculasNumberProduct usefulCalculasNumberProduct = new UsefulCalculasNumberProduct();

    Helper help = new Helper();
    UsefulCalculas usefullCalculas = new UsefulCalculas();
    SubmitBuy_helper submitBuy_helper = new SubmitBuy_helper();

    float totalCounter = 0;
    int paymentId = 0;
    public int customerId = 0;

    public int OrderId = 0;
    String uuidUniq;

    DecimalFormat df = new DecimalFormat("#.###");

    @FXML
    private Label txtDate;

    ObservableList<custom_BuyTable> row = FXCollections.observableArrayList();
    @FXML
    private TextField etDiscount1;
    @FXML
    private TextField orderId;
    @FXML
    private TextField et_sub_quantity;
    @FXML
    private Label txt_subProduct;
    @FXML
    private JFXComboBox<String> compo_product_Type;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //System.out.println(orderDAO.getOrderByDate().get(1).getTotslCost());
        compo_priceType.getItems().addAll("قطاعى");
        compo_priceType.getItems().addAll("جملة");
        compo_priceType.getItems().addAll("جملة الجملة");
        compoFunctionType.getItems().addAll("نقدى");
        compoFunctionType.getItems().addAll("آجل");
        compo_product_Type.getItems().addAll("منتجات بكجم");
        compo_product_Type.getItems().addAll("منتجات بالوحدة");

        TextFields.bindAutoCompletion(etClientName, getAllCustomerName());
        txtDate.setText(help.getDate());

        addButtonDeleteToTable();

        // orderId.setText(String.valueOf(orderDAO.getCountOrder()));
        /*
        System.out.println(usefullCalculas.getProductPartitionPriceforunit(27));
        System.out.println(usefullCalculas.allowOfunit(28));
        usefullCalculas.is_allow(100, 28);
         */
    }

    @FXML
    private void btn_submit_buy(ActionEvent event) {
        try {
            String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
            String compo_productType = compo_product_Type.getSelectionModel().getSelectedItem().toString();
            if (knownUsFrom.equals("آجل") && etClientName.getText().toString().isEmpty()) {
                FxDialogs.showInformation("من فضلك", "ادخل اسم العميل والمدفوع");
            } else {

                if (etClientName.getText().toString().isEmpty()) {
                    customerId = 0;
                } else if (!etClientName.getText().toString().isEmpty()) {
                    customerId = customerDAO.getcCustomerId(etClientName.getText().toString());
                }

                generateCode();
                // insert order 
                submitBuy_helper.insert_order(customerId, paymentId, etBuyType.getText().toString(), uuidUniq);

                // insert order detail 
                getAllDetail();
                //insert order payment 
                submitBuy_helper.insertOrderPayment((orderDAO.getLastOrderId(uuidUniq)),
                        Float.parseFloat(etDiscount1.getText().toString()),
                        Float.parseFloat(et_paid_up.getText().toString()),
                        Float.parseFloat(et_remaining.getText().toString()),
                        "notes",
                        Float.parseFloat(etDiscount.getText().toString()));

                //ubdate stock
                if (compo_productType.equals("منتجات بكجم")) {
                    UpdateStock();
                } else if (compo_productType.equals("منتجات بالوحدة")) {
                    UpdateNumberStock();
                }

                FxDialogs.showInformation("نجاح العملية", "تم توريد الطلب بنجاح");
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        clear_afterSubmit();

    }

    public void getAllDetail() {
        for (custom_BuyTable o : row) {
            submitBuy_helper.insertOrderDetails((orderDAO.getLastOrderId(uuidUniq)),
                    o.getIdProduct(),
                    o.getPrice(),
                    o.getQuantity(),
                    o.getTotal());
        }
    }

    public void UpdateStock() {
        // System.out.println(productDAO.getWeightInStock(28));
        for (custom_BuyTable o : row) {
            float unitWeight = productDAO.getProductById(o.getIdProduct()).getProductWeight();
            float allWeightInStock = productDAO.getProductById(o.getIdProduct()).getUnitsWeightInStock();
            float weighofOrder = usefullCalculas.getwightofUnitsToUpdate(o.getQuantity(), unitWeight);
            productDAO.updateWeight(allWeightInStock - weighofOrder, o.getIdProduct());
            //  System.out.println(allWeightInStock + "\n" +weighofOrder+"\n"+o.getQuantity() );
        }
    }

    public void UpdateNumberStock() {

        for (custom_BuyTable o : row) {
            float unitsInStock = productNumbersDAO.getProductsnumberById(o.getIdProduct()).getUnitsInStock();
            float quantityOfOrder = o.getQuantity();
            productNumbersDAO.updateStock(unitsInStock - quantityOfOrder, o.getIdProduct());
        }
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
        clear_afterSubmit();
        String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
        if (knownUsFrom.equals("آجل")) {
            paymentId = 1;
            et_paid_up.setDisable(false);
            FxDialogs.showInformation("من فضلك", "ادخل اسم العميل والمدفوع");
            compoFunctionType.setDisable(true);

        } else if (knownUsFrom.equals("نقدى")) {
            et_paid_up.setDisable(true);
            paymentId = 2;

            compoFunctionType.setDisable(true);

        }

    }

    @FXML
    private void btn_add_product_click(ActionEvent event) {

        boolean isMyComboBoxEmpty = compoFunctionType.getSelectionModel().isEmpty();
        boolean isMyComboBoxproduct_Type = compo_product_Type.getSelectionModel().isEmpty();
        String product_Type = compo_product_Type.getSelectionModel().getSelectedItem().toString();
        generateCode();

        //validate of input
        if (etBuyType.getText().toString().isEmpty()) {
            FxDialogs.showInformation("من فضلك", "اختر نوع عملية البيع");
        } else if (etProductName.getText().toString().isEmpty()) {
            FxDialogs.showInformation("من فضلك", "ادخل اسم المنتج");
        } else if (etQuantity.getText().toString().isEmpty()) {
            FxDialogs.showInformation("من فضلك", "ادخل الكمية المباعة");
        } else if (isMyComboBoxEmpty) {
            FxDialogs.showInformation("من فضلك", "اختر نوع العملية");
        } else if (isMyComboBoxproduct_Type) {
            FxDialogs.showInformation("من فضلك", "تصنيف المنتج");
        } else {
            //check type of product 
            if (product_Type.equals("منتجات بكجم")) {

                if (etBuyType.getText().toString().equals("قطاعى")) {
                    addProductforPartion();

                } else if (etBuyType.getText().toString().equals("جملة")) {
                    addProductforGomla();

                } else if (etBuyType.getText().toString().equals("جملة الجملة")) {
                    addProductforGomla_Gomla();

                }
            } else if (product_Type.equals("منتجات بالوحدة")) {

                if (etBuyType.getText().toString().equals("قطاعى")) {
                    addProductNumberforPartion();

                } else if (etBuyType.getText().toString().equals("جملة")) {
                    addProductNumberforGomla();

                } else if (etBuyType.getText().toString().equals("جملة الجملة")) {
                    addProductNumberforGomlaGomla();
                }

            }

        }

    }

    /**
     * generate code id
     */
    public void generateCode() {
        // Creating a random UUID (Universally unique identifier).
        UUID uuid = UUID.randomUUID();
        uuidUniq = uuid.toString();
    }

    public void addProductforPartion() {
        List<Products> items = productDAO.getProductId(etProductName.getText().toString());

        String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
        for (Products product : items) {
            // check allow of items 
            if (usefullCalculas.is_allow(Integer.parseInt(etQuantity.getText().toString()), product.getProductid())) {
                addPartion(product.getProductid(), product.getProductName());
            } else {
                float numberUnit = productDAO.getProductById(product.getProductid()).getUnitsWeightInStock();
                if (FxDialogs.showConfirm("احزر\n " + "يوجد فى المخزن" + "\n" + numberUnit + "\t" + "كجم\n",
                        "هل تريد تكملة البيع ؟", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {

                    if (numberUnit <= Float.parseFloat(etQuantity.getText().toString())) {

                        FxDialogs.showWarning("احزر", "الكمية اقل من المخزن");

                    } else {

                        addPartion(product.getProductid(), product.getProductName());
                    }
                }

            }
        }
        loadTabData();
        clearText();
    }

    public void addProductNumberforPartion() {
        List<Productsnumber> items = productNumbersDAO.getProducNumbertId(etProductName.getText().toString());

        for (Productsnumber productsnumber : items) {
            // check allow of items 
            if (usefulCalculasNumberProduct.is_allow(Integer.parseInt(etQuantity.getText().toString()), productsnumber.getProductnumberid())) {
                addPartionNumber(productsnumber.getProductnumberid(), productsnumber.getProductnumberName());
            } else {
                float numberUnit = productNumbersDAO.getProductsnumberById(productsnumber.getProductnumberid()).getUnitsInStock();
                if (FxDialogs.showConfirm("احزر\n " + "يوجد فى المخزن" + "\n" + numberUnit + "\t" + "كجم\n",
                        "هل تريد تكملة البيع ؟", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {

                    if (numberUnit <= Float.parseFloat(etQuantity.getText().toString())) {

                        FxDialogs.showWarning("احزر", "الكمية اقل من المخزن");

                    } else {

                        addPartionNumber(productsnumber.getProductnumberid(), productsnumber.getProductnumberName());
                    }
                }

            }
        }
        loadTabData();
        clearText();
    }

    public void addSubProductforPartion() {
        List<Products> subItems = productDAO.getProductId(txt_subProduct.getText().toString());

        for (Products product : subItems) {
            // check allow of items 
            if (usefullCalculas.is_allow(Integer.parseInt(et_sub_quantity.getText().toString()), product.getProductid())) {
                addSubPartion(product.getProductid(), product.getProductName());
            } else {
                float numberUnit = productDAO.getProductById(product.getProductid()).getUnitsWeightInStock();
                if (FxDialogs.showConfirm("احزر\n " + "يوجد فى المخزن" + "\n" + numberUnit + "\t" + "كجم\n",
                        "هل تريد تكملة البيع ؟", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
                    if (numberUnit <= Float.parseFloat(et_sub_quantity.getText().toString())) {
                        FxDialogs.showWarning("احزر", "الكمية اقل من المخزن");
                    } else {
                        addSubPartion(product.getProductid(), product.getProductName());
                    }
                }

            }
        }
        loadTabData();
        clearText();
    }

    public void addProductforGomla() {

        List<Products> items = productDAO.getProductId(etProductName.getText().toString());
        for (Products product : items) {
            float product_gomlaPrice = productDAO.getProductById(product.getProductid()).getGomlaBuyPrice();
            // check allow of items 
            if (usefullCalculas.isKG_allow(Float.parseFloat(etQuantity.getText().toString()), product.getProductid())) {
                addGomlaProduct(product.getProductid(), product.getProductName());
            } else {

                float numberUnit = productDAO.getProductById(product.getProductid()).getUnitsWeightInStock();
                if (FxDialogs.showConfirm("احزر\n " + "يوجد فى المخزن" + "\n" + numberUnit + "\t" + "كجم\n",
                        "هل تريد تكملة البيع ؟", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
                    if (numberUnit <= Float.parseFloat(etQuantity.getText().toString())) {
                        FxDialogs.showWarning("احزر", "الكمية اقل من المخزن");
                    } else {
                        addGomlaProduct(product.getProductid(), product.getProductName());
                    }
                }
            }
        }
        loadTabData();
        clearText();
    }

    public void addProductNumberforGomla() {
        List<Productsnumber> items = productNumbersDAO.getProducNumbertId(etProductName.getText().toString());

        for (Productsnumber productsnumber : items) {
            // check allow of items 
            if (usefulCalculasNumberProduct.is_allow(Integer.parseInt(etQuantity.getText().toString()), productsnumber.getProductnumberid())) {
                addGomlaNumber(productsnumber.getProductnumberid(), productsnumber.getProductnumberName());
            } else {
                float numberUnit = productNumbersDAO.getProductsnumberById(productsnumber.getProductnumberid()).getUnitsInStock();
                if (FxDialogs.showConfirm("احزر\n " + "يوجد فى المخزن" + "\n" + numberUnit + "\t" + "كجم\n",
                        "هل تريد تكملة البيع ؟", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {

                    if (numberUnit <= Float.parseFloat(etQuantity.getText().toString())) {

                        FxDialogs.showWarning("احزر", "الكمية اقل من المخزن");

                    } else {

                        addGomlaNumber(productsnumber.getProductnumberid(), productsnumber.getProductnumberName());
                    }
                }

            }
        }
        loadTabData();
        clearText();

    }

    public void addProductNumberforGomlaGomla() {
        List<Productsnumber> items = productNumbersDAO.getProducNumbertId(etProductName.getText().toString());
        for (Productsnumber productsnumber : items) {
            // check allow of items 
            if (usefulCalculasNumberProduct.is_allow(Integer.parseInt(etQuantity.getText().toString()), productsnumber.getProductnumberid())) {
                addGomlaGomlaNumber(productsnumber.getProductnumberid(), productsnumber.getProductnumberName());
            } else {
                float numberUnit = productNumbersDAO.getProductsnumberById(productsnumber.getProductnumberid()).getUnitsInStock();
                if (FxDialogs.showConfirm("احزر\n " + "يوجد فى المخزن" + "\n" + numberUnit + "\t" + "كجم\n",
                        "هل تريد تكملة البيع ؟", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {

                    if (numberUnit <= Float.parseFloat(etQuantity.getText().toString())) {
                        FxDialogs.showWarning("احزر", "الكمية اقل من المخزن");
                    } else {

                        addGomlaGomlaNumber(productsnumber.getProductnumberid(), productsnumber.getProductnumberName());
                    }
                }
            }
        }
        loadTabData();
        clearText();
    }

    public void addSubProductforGomla() {
        List<Products> items = productDAO.getProductId(txt_subProduct.getText().toString());
        for (Products product : items) {
            float product_gomlaPrice = productDAO.getProductById(product.getProductid()).getGomlaBuyPrice();
            // check allow of items 
            if (usefullCalculas.isKG_allow(Float.parseFloat(et_sub_quantity.getText().toString()), product.getProductid())) {
                addSubGomlaProduct(product.getProductid(), product.getProductName());
            } else {

                float numberUnit = productDAO.getProductById(product.getProductid()).getUnitsWeightInStock();
                if (FxDialogs.showConfirm("احزر\n " + "يوجد فى المخزن" + "\n" + numberUnit + "\t" + "كجم\n",
                        "هل تريد تكملة البيع ؟", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
                    if (numberUnit <= Float.parseFloat(et_sub_quantity.getText().toString())) {
                        FxDialogs.showWarning("احزر", "الكمية اقل من المخزن");
                    } else {
                        addSubGomlaProduct(product.getProductid(), product.getProductName());
                    }
                }

            }
        }
        loadTabData();
        clearText();
    }

    public void addProductforGomla_Gomla() {
        List<Products> items = productDAO.getProductId(etProductName.getText().toString());
        for (Products product : items) {
            // check allow of items 
            if (usefullCalculas.isKG_allow(Float.parseFloat(etQuantity.getText().toString()), product.getProductid())) {
                addGomla_GomlaProduct(product.getProductid(), product.getProductName());
            } else {
                float numberUnit = productDAO.getProductById(product.getProductid()).getUnitsWeightInStock();
                if (FxDialogs.showConfirm("احزر\n " + "يوجد فى المخزن" + "\n" + numberUnit + "\t" + "كجم\n",
                        "هل تريد تكملة البيع ؟", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
                    if (numberUnit <= Float.parseFloat(etQuantity.getText().toString())) {
                        FxDialogs.showWarning("احزر", "الكمية اقل من المخزن");
                    } else {
                        addGomla_GomlaProduct(product.getProductid(), product.getProductName());
                    }
                }

            }
        }
        loadTabData();
        clearText();
    }

    public void addSubProductforGomla_Gomla() {
        List<Products> items = productDAO.getProductId(txt_subProduct.getText().toString());
        for (Products product : items) {
            // check allow of items 
            if (usefullCalculas.isKG_allow(Float.parseFloat(et_sub_quantity.getText().toString()), product.getProductid())) {
                addSubGomla_GomlaProduct(product.getProductid(), product.getProductName());
            } else {
                float numberUnit = productDAO.getProductById(product.getProductid()).getUnitsWeightInStock();
                if (FxDialogs.showConfirm("احزر\n " + "يوجد فى المخزن" + "\n" + numberUnit + "\t" + "كجم\n",
                        "هل تريد تكملة البيع ؟", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
                    if (numberUnit <= Float.parseFloat(et_sub_quantity.getText().toString())) {
                        FxDialogs.showWarning("احزر", "الكمية اقل من المخزن");
                    } else {
                        addSubGomla_GomlaProduct(product.getProductid(), product.getProductName());
                    }
                }

            }
        }
        loadTabData();
        clearText();
    }

    public void addGomla_GomlaProduct(int productId, String productName) {
        String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
        float product_Gomla_GomlaPrice = productDAO.getProductById(productId).getGomelGomlaBuyPrice();
        float total = product_Gomla_GomlaPrice * Float.parseFloat(etQuantity.getText().toString());
        row.add(new custom_BuyTable(productId,
                productName,
                Float.parseFloat(etQuantity.getText().toString()),
                product_Gomla_GomlaPrice,
                total));
        totalCounter += total;
        txtTotal.setText(String.valueOf(df.format(totalCounter)));
        //new 
        if (knownUsFrom.equals("آجل")) {
            et_remaining.setText(String.valueOf(df.format(totalCounter)));
        } else if (knownUsFrom.equals("نقدى")) {
            et_paid_up.setText(String.valueOf(df.format(totalCounter)));
        }
        etDiscount1.setText(String.valueOf(df.format(totalCounter)));
    }

    public void addSubGomla_GomlaProduct(int productId, String productName) {
        String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
        float product_Gomla_GomlaPrice = productDAO.getProductById(productId).getGomelGomlaBuyPrice();
        float total = product_Gomla_GomlaPrice * Float.parseFloat(et_sub_quantity.getText().toString());
        row.add(new custom_BuyTable(productId,
                productName,
                Float.parseFloat(et_sub_quantity.getText().toString()),
                product_Gomla_GomlaPrice,
                total));
        totalCounter += total;
        txtTotal.setText(String.valueOf(df.format(totalCounter)));
        //new 
        if (knownUsFrom.equals("آجل")) {
            et_remaining.setText(String.valueOf(df.format(totalCounter)));
        } else if (knownUsFrom.equals("نقدى")) {
            et_paid_up.setText(String.valueOf(df.format(totalCounter)));
        }
        etDiscount1.setText(String.valueOf(df.format(totalCounter)));
    }

    public void addGomlaProduct(int productId, String productName) {
        String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
        float product_gomlaPrice = productDAO.getProductById(productId).getGomlaBuyPrice();
        // check allow of items 
        if (usefullCalculas.isKG_allow(Float.parseFloat(etQuantity.getText().toString()), productId)) {
            float total = product_gomlaPrice * Float.parseFloat(etQuantity.getText().toString());
            row.add(new custom_BuyTable(productId,
                    productName,
                    Float.parseFloat(etQuantity.getText().toString()),
                    product_gomlaPrice,
                    total));
            totalCounter += total;
            txtTotal.setText(String.valueOf(df.format(totalCounter)));
            //new 
            if (knownUsFrom.equals("آجل")) {
                et_remaining.setText(String.valueOf(df.format(totalCounter)));
            } else if (knownUsFrom.equals("نقدى")) {
                et_paid_up.setText(String.valueOf(df.format(totalCounter)));
            }
            etDiscount1.setText(String.valueOf(df.format(totalCounter)));
        }
    }

    public void addSubGomlaProduct(int productId, String productName) {
        String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
        float product_gomlaPrice = productDAO.getProductById(productId).getGomlaBuyPrice();
        // check allow of items 
        if (usefullCalculas.isKG_allow(Float.parseFloat(et_sub_quantity.getText().toString()), productId)) {
            float total = product_gomlaPrice * Float.parseFloat(et_sub_quantity.getText().toString());
            row.add(new custom_BuyTable(productId,
                    productName,
                    Float.parseFloat(et_sub_quantity.getText().toString()),
                    product_gomlaPrice,
                    total));
            totalCounter += total;
            txtTotal.setText(String.valueOf(df.format(totalCounter)));
            //new 
            if (knownUsFrom.equals("آجل")) {
                et_remaining.setText(String.valueOf(df.format(totalCounter)));
            } else if (knownUsFrom.equals("نقدى")) {
                et_paid_up.setText(String.valueOf(df.format(totalCounter)));
            }
            etDiscount1.setText(String.valueOf(df.format(totalCounter)));
        }
    }

    /**
     * method to add product to table
     *
     * @param productId
     * @param productName
     *
     */
    public void addPartion(int productId, String productName) {
        String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
        float total = usefullCalculas.getProductPartitionPriceforunit(productId)
                * Float.parseFloat(etQuantity.getText().toString());
        row.add(new custom_BuyTable(productId,
                productName,
                Float.parseFloat(etQuantity.getText().toString()),
                usefullCalculas.getProductPartitionPriceforunit(productId),
                total));
        totalCounter += total;
        txtTotal.setText(String.valueOf(df.format(totalCounter)));
        //new 
        if (knownUsFrom.equals("آجل")) {
            et_remaining.setText(String.valueOf(df.format(totalCounter)));
        } else if (knownUsFrom.equals("نقدى")) {
            et_paid_up.setText(String.valueOf(df.format(totalCounter)));
        }
        etDiscount1.setText(String.valueOf(df.format(totalCounter)));
    }

    public void addPartionNumber(int productId, String productName) {
        String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
        float price = productNumbersDAO.getProductsnumberById(productId).getPartitionnumberBuyPrice();

        float total = price * Float.parseFloat(etQuantity.getText().toString());

        row.add(new custom_BuyTable(productId,
                productName,
                Float.parseFloat(etQuantity.getText().toString()),
                price,
                total));
        totalCounter += total;
        txtTotal.setText(String.valueOf(df.format(totalCounter)));
        //new 
        if (knownUsFrom.equals("آجل")) {
            et_remaining.setText(String.valueOf(df.format(totalCounter)));
        } else if (knownUsFrom.equals("نقدى")) {
            et_paid_up.setText(String.valueOf(df.format(totalCounter)));
        }
        etDiscount1.setText(String.valueOf(df.format(totalCounter)));
    }

    public void addGomlaNumber(int productId, String productName) {

        String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
        float price = productNumbersDAO.getProductsnumberById(productId).getGomlaBuynumberPrice();

        float total = price * Float.parseFloat(etQuantity.getText().toString());

        row.add(new custom_BuyTable(productId,
                productName,
                Float.parseFloat(etQuantity.getText().toString()),
                price,
                total));
        totalCounter += total;
        txtTotal.setText(String.valueOf(df.format(totalCounter)));
        //new 
        if (knownUsFrom.equals("آجل")) {
            et_remaining.setText(String.valueOf(df.format(totalCounter)));
        } else if (knownUsFrom.equals("نقدى")) {
            et_paid_up.setText(String.valueOf(df.format(totalCounter)));
        }
        etDiscount1.setText(String.valueOf(df.format(totalCounter)));
    }

    public void addGomlaGomlaNumber(int productId, String productName) {

        String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
        float price = productNumbersDAO.getProductsnumberById(productId).getGomelGomlanumberBuyPrice();
        float total = price * Float.parseFloat(etQuantity.getText().toString());

        row.add(new custom_BuyTable(productId,
                productName,
                Float.parseFloat(etQuantity.getText().toString()),
                price,
                total));
        totalCounter += total;
        txtTotal.setText(String.valueOf(df.format(totalCounter)));
        //new 
        if (knownUsFrom.equals("آجل")) {
            et_remaining.setText(String.valueOf(df.format(totalCounter)));
        } else if (knownUsFrom.equals("نقدى")) {
            et_paid_up.setText(String.valueOf(df.format(totalCounter)));
        }
        etDiscount1.setText(String.valueOf(df.format(totalCounter)));
    }

    /**
     * method to add product to table
     *
     * @param productId
     * @param productName
     *
     */
    public void addSubPartion(int productId, String productName) {
        String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
        float total = usefullCalculas.getProductPartitionPriceforunit(productId)
                * Float.parseFloat(et_sub_quantity.getText().toString());
        row.add(new custom_BuyTable(productId,
                productName,
                Float.parseFloat(et_sub_quantity.getText().toString()),
                usefullCalculas.getProductPartitionPriceforunit(productId),
                total));
        totalCounter += total;
        txtTotal.setText(String.valueOf(df.format(totalCounter)));
        //new 
        if (knownUsFrom.equals("آجل")) {
            et_remaining.setText(String.valueOf(df.format(totalCounter)));
        } else if (knownUsFrom.equals("نقدى")) {
            et_paid_up.setText(String.valueOf(df.format(totalCounter)));
        }
        etDiscount1.setText(String.valueOf(df.format(totalCounter)));
    }

    public void clearText() {
        //etClientName.clear();
        etProductName.clear();
        etQuantity.clear();
    }

    public void clear_afterSubmit() {
        table.getItems().clear();
        txtTotal.setText("0");
        et_paid_up.setText("0");
        et_remaining.setText("0");
        etDiscount.setText("0");
        etDiscount1.setText("0");
        etClientName.setText("");
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
     * @return all product name
     */
    public ArrayList<String> getAllProductNumberName() {
        ArrayList<String> result = new ArrayList<String>();
        for (Productsnumber o : productNumbersDAO.getAllProductsnumber()) {
            result.add(o.getProductnumberName());
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
                                    // clearTT();

                                    table.getItems().remove(data);
                                    totalCounter -= data.getTotal();
                                    txtTotal.setText(String.valueOf(totalCounter));
                                    et_remaining.setText(String.valueOf(totalCounter));
                                    etDiscount1.setText(String.valueOf(totalCounter));
                                    et_paid_up.setText(String.valueOf(totalCounter));
                                    etDiscount.clear();

                                    if (table.getItems().isEmpty()) {
                                        clear_afterSubmit();

                                    }

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

    public void clearTT() {
        txtTotal.setText("");
        et_remaining.setText("");
        etDiscount1.setText("");
        et_paid_up.setText("");
        etDiscount.clear();
    }

    @FXML
    private void homeClick(MouseEvent event) throws IOException {
        help.start("/mosmar/main.fxml", "الصفحة الرئيسية");
        help.closeC(compo_priceType);

    }

    @FXML
    private void etDiscount_pressedMouse(MouseEvent event) {
        try {
            String knownUsFrom = compoFunctionType.getSelectionModel().getSelectedItem().toString();
            String answer = FxDialogs.showTextInput("اكتب قيمة الخصم", "جنية", "0");

            if (!answer.equals(null)) {
                etDiscount.setText(answer);
                if (knownUsFrom.equals("آجل")) {
                    float totalAfterDiscount = Float.parseFloat(et_remaining.getText().toString()) - Float.parseFloat(answer);
                    etDiscount1.setText(String.valueOf(df.format(totalAfterDiscount)));
                    et_remaining.setText(String.valueOf(df.format(totalAfterDiscount)));
                } else if (knownUsFrom.equals("نقدى")) {
                    float totalAfterDiscount = Float.parseFloat(et_paid_up.getText().toString()) - Float.parseFloat(answer);
                    etDiscount1.setText(String.valueOf(df.format(totalAfterDiscount)));
                    et_paid_up.setText(String.valueOf(df.format(totalAfterDiscount)));
                }

            }

        } catch (Exception e) {
        }

    }

    @FXML
    private void et_paid_up_pressedMouse(MouseEvent event) {
        try {
            String answer = FxDialogs.showTextInput("اكتب القيمة المدفوعة", "جنية", "0");
            et_paid_up.setText(answer);
            float remainin = Float.parseFloat(txtTotal.getText().toString()) - Float.parseFloat(answer);
            et_remaining.setText(String.valueOf(df.format(remainin)));
            etDiscount1.setText(String.valueOf(df.format(remainin)));
        } catch (Exception e) {
        }
    }

    @FXML
    private void compo_product_Type_click(ActionEvent event) {

        String knownUsFrom = compo_product_Type.getSelectionModel().getSelectedItem().toString();
        if (knownUsFrom.equals("منتجات بكجم")) {
            TextFields.bindAutoCompletion(etProductName, getAllProductName());
        } else {
            TextFields.bindAutoCompletion(etProductName, getAllProductNumberName());
        }

    }

    @FXML
    private void sub_product(MouseEvent event) {
        try {
            List<Products> items = productDAO.getProductId(etProductName.getText().toString());
            txt_subProduct.setText(productMappingDAO.getSubProduct(items.get(0).getProductid()));
        } catch (Exception e) {
        }

    }

    @FXML
    private void btnAddSubProductClick(ActionEvent event) {
        try {

            if (etBuyType.getText().toString().equals("قطاعى")) {
                addSubProductforPartion();

            } else if (etBuyType.getText().toString().equals("جملة")) {
                addSubProductforGomla();

            } else if (etBuyType.getText().toString().equals("جملة الجملة")) {
                addSubProductforGomla_Gomla();

            }
        } catch (Exception e) {
        }

    }

    @FXML
    private void etProductNameKeyClick(KeyEvent event) {
        try {

            if (event.getCode().equals(KeyCode.ENTER)) {
                List<Products> items = productDAO.getProductId(etProductName.getText().toString());
                txt_subProduct.setText(productMappingDAO.getSubProduct(items.get(0).getProductid()));
            }

        } catch (Exception e) {
        }

    }

    @FXML
    private void etProductNameMouseClick(MouseEvent event) {
        try {

            //  System.out.println("double : " + df.format(3.22245782)); 
            txt_subProduct.setText("");
            et_sub_quantity.clear();
            // clear_afterSubmit();
        } catch (Exception e) {
        }
    }

}
