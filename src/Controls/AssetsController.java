package Controls;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dao.AssetsDAO;
import dao.ProductDAO;
import dao.ProductNumbersDAO;
import entities.Assets;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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
    }

    @FXML
    private void addClick(MouseEvent event) {
        addPane.setVisible(true);
    }

    @FXML
    private void homeClick(MouseEvent event) {
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
}
