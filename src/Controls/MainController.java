
package Controls;

import com.jfoenix.controls.JFXButton;
import helper.Helper;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;


public class MainController implements Initializable {

    Helper helper = new Helper();

    @FXML
    private JFXButton btn_buy;
    @FXML
    private JFXButton btnBuy_not_payment;
    @FXML
    private JFXButton btn_accounts;
    @FXML
    private JFXButton btn_burshes;
    @FXML
    private JFXButton btn_Recall;
    @FXML
    private JFXButton btn_store;

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnBuyClick(ActionEvent event) throws Exception {

        helper.start("/mosmar/buy.fxml", "البيع");
        helper.close(btn_buy);
    }


    @FXML
    private void btnAccountsClick(ActionEvent event) throws IOException{
         helper.start("/mosmar/acountsCenter.fxml", "الحسابات");
         helper.close(btn_buy);
    }

    @FXML
    private void btnBurshesClick(ActionEvent event) throws IOException {
        
        helper.start("/mosmar/suppliers.fxml", "الموردين");
        helper.close(btn_buy);
        
    }

    @FXML
    private void btnRecallClick(ActionEvent event) throws IOException {
        helper.start("/mosmar/retrieval.fxml", "المخزن");
        helper.close(btn_buy);
    }

    @FXML
    private void btnStoreClick(ActionEvent event) throws IOException {

        helper.start("/mosmar/stock.fxml", "المخزن");
        helper.close(btn_buy);

    }

    @FXML
    private void btn_customersClick(ActionEvent event) throws IOException {
        helper.start("/mosmar/customers.fxml", "حساب العملاء");
        helper.close(btn_buy);
    }

    @FXML
    private void assetsClick(ActionEvent event) throws IOException {
        helper.start("/mosmar/Assets.fxml", "الاصول");
        helper.close(btn_buy);
    }

    
    

}
