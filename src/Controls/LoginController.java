/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import helper.Helper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author OM EL NOUR
 */
public class LoginController implements Initializable {

    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXTextField etUser;
    @FXML
    private JFXPasswordField etpass;
    @FXML
    private Label lbllogin;
    
    String userNmae= "123";
    String Password = "123";
    String checkUser, checkPw;
    Helper helper =  new Helper();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginClick(ActionEvent event) throws IOException {
        
        checkUser = etUser.getText().toString();
        checkPw = etpass.getText().toString();
        
       if(checkUser.equals(userNmae) && checkPw.equals(Password)){
        helper.start("/mosmar/main.fxml","الصفحة الرئيسية");
        helper.close(btnLogin);
                
          }
       
          else{
           lbllogin.setText("كلمة السر غير صحيحية");
           System.out.println("no");
         
          }
       
    }

    @FXML
    private void pressed(KeyEvent event) throws IOException {
        
         if(event.getCode().equals(KeyCode.ENTER)) {
         login();
              
        }
        
    }
 
      public void login() throws IOException{
            checkUser = etUser.getText().toString();
        checkPw = etpass.getText().toString();
        
       if(checkUser.equals(userNmae) && checkPw.equals(Password)){
        helper.start("/mosmar/main.fxml","الصفحة الرئيسية");
        helper.close(btnLogin);
                
          }
       
          else{
           lbllogin.setText("كلمة السر غير صحيحية");
           System.out.println("no");
         
          }
    }
    
}
