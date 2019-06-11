package Controls;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dao.AssetsDAO;
import dao.ProductDAO;
import dao.ProductNumbersDAO;
import entities.Assets;
import entities.Customers;
import helper.FxDialogs;
import helper.Helper;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

public class AssetsController implements Initializable {

    @FXML
    private Label capitalWeight;
    @FXML
    private Label capitalUnits;
    @FXML
    private Label lableTotalAssets;
    @FXML
    private JFXTextArea etAssetName;
    @FXML
    private JFXTextField AssetValue;

    DecimalFormat df = new DecimalFormat("#.###");
    private final ProductDAO productDAO = new ProductDAO();
    ProductNumbersDAO productNumbersDAO = new ProductNumbersDAO();
    AssetsDAO assetsDAO = new AssetsDAO();
    Helper helper = new Helper();

    @FXML
    private TableView<Assets> tablAssets;
    @FXML
    private TableColumn<Assets, Integer> colId;
    @FXML
    private TableColumn<Assets, String> colAssetsName;
    @FXML
    private TableColumn<Assets, Float> colValue;
    @FXML
    private TableColumn<Assets, String> colNotes;
    @FXML
    private Pane addPane;
    @FXML
    private JFXTextArea assetnotes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadoAssetsTabData();
        calcCapital();
        addButtonDeleteToTable();
    }

    @FXML
    private void addClick(MouseEvent event) {
        addPane.setVisible(true);
    }

    @FXML
    private void homeClick(MouseEvent event) throws IOException {
        helper.start("/mosmar/main.fxml", "الصفحة الرئيسية");
        helper.closePane(addPane);
    }

    @FXML
    private void closeAddClick(MouseEvent event) {
        addPane.setVisible(false);
        clearAdd();
    }

    @FXML
    private void saveAssetClick(ActionEvent event) {
        try {
            Assets assets = new Assets();
            assets.setAssetsName(etAssetName.getText().toString());
            assets.setAssetsValue(Float.parseFloat(AssetValue.getText().toString()));
            assets.setNotes(assetnotes.getText().toString());
            assetsDAO.addAssets(assets);
        } catch (Exception e) {

        }
        clearAdd();
        addPane.setVisible(false);
        loadoAssetsTabData();
        calcCapital();
    }

    /**
     * helper methods
     */
    public void calcCapital() {
        try {
            capitalWeight.setText(df.format(productDAO.getUnitsCapital()));
            capitalUnits.setText(df.format(productNumbersDAO.getUnitsCapital()));
            lableTotalAssets.setText(df.format(assetsDAO.getAssetsValueCapital()));

        } catch (Exception e) {
        }
    }

    public void loadoAssetsTabData() {
        colId.setCellValueFactory(new PropertyValueFactory<>("assetsId"));
        colAssetsName.setCellValueFactory(new PropertyValueFactory<>("assetsName"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("assetsValue"));
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        tablAssets.setItems(assetsDAO.getAllAssets());

    }

    public void clearAdd() {
        etAssetName.clear();
        AssetValue.clear();
        assetnotes.clear();
    }

    //delete row
    private void addButtonDeleteToTable() {
        TableColumn<Assets, Void> colBtn = new TableColumn();

        Callback<TableColumn<Assets, Void>, TableCell<Assets, Void>> cellFactory;
        cellFactory = new Callback<TableColumn<Assets, Void>, TableCell<Assets, Void>>() {
            @Override
            public TableCell<Assets, Void> call(final TableColumn<Assets, Void> param) {
                final TableCell<Assets, Void> cell = new TableCell<Assets, Void>() {

                    private final Button btn = new Button("مسح");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                            if (FxDialogs.showConfirm("مسح المنتج", "هل تريد مسح المنتج?", FxDialogs.YES, FxDialogs.NO).equals(FxDialogs.YES)) {
                                Assets data = getTableView().getItems().get(getIndex());
                                try {
                                    assetsDAO.removAssets(data.getAssetsId());
                                    loadoAssetsTabData();
                                    calcCapital();
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
        tablAssets.getColumns().add(colBtn);
    }
}
