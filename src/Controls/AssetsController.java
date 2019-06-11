
package Controls;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dao.ProductDAO;
import dao.ProductNumbersDAO;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;



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
    @FXML
    private JFXTextArea assetValue;
    
    DecimalFormat df = new DecimalFormat("#.###");
     private final ProductDAO productDAO = new ProductDAO();
    ProductNumbersDAO productNumbersDAO = new ProductNumbersDAO();
    
    @FXML
    private TableView<?> tablAssets;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colAssetsName;
    @FXML
    private TableColumn<?, ?> colValue;
    @FXML
    private TableColumn<?, ?> colNotes;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   

    
    /**
     * helper methods
     */
    
      public void calcCapital(){
        try {
            capitalWeight.setText(df.format(productDAO.getUnitsCapital()));
            capitalUnits.setText(df.format(productNumbersDAO.getUnitsCapital()));
            
        } catch (Exception e) {
        }
    }

    @FXML
    private void addClick(MouseEvent event) {
    }

    @FXML
    private void homeClick(MouseEvent event) {
    }

    @FXML
    private void saveAssetClick(ActionEvent event) {
    }
    
    
}
