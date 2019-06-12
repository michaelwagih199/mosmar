package Controls;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RetrievalController implements Initializable {

    @FXML
    private JFXComboBox<?> compo_product_Type;
    @FXML
    private TextField etProductName;
    @FXML
    private TextField etClientName;
    @FXML
    private TextField etValue;
    @FXML
    private TableView<?> retrivalTable;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colTime;
    @FXML
    private TableColumn<?, ?> colProduct;
    @FXML
    private TableColumn<?, ?> colValue;
    @FXML
    private TableColumn<?, ?> colClient;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void homeClick(MouseEvent event) {
        
    }

    @FXML
    private void compo_product_Type_click(ActionEvent event) {
    }

    @FXML
    private void addProduct(ActionEvent event) {
    }
    

}
