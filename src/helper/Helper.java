/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author NozomProgrammers
 */
public class Helper {
    
     public void start(String xml,String title) throws IOException{
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(xml));
         Parent root1 = (Parent) fxmlLoader.load();
         Stage stage = new Stage();
         stage.setTitle(title);
         stage.setScene(new Scene(root1));  
         stage.setResizable(false);
         stage.getIcons().add(new Image("/res/logo.png"));
         stage.show();
  }
     /**
      * 
      * @param btnId
      * @throws IOException 
      */
       public void close(Button btnId) throws IOException{
        Stage stage = (Stage) btnId.getScene().getWindow();
        // do what you have to do
       stage.close();
  }
       
     public void closeC(ComboBox combo) throws IOException{
        Stage stage = (Stage) combo.getScene().getWindow();
        // do what you have to do
       stage.close();
  }
     
        public void closeI(ImageView imge) throws IOException{
        Stage stage = (Stage) imge.getScene().getWindow();
        // do what you have to do
       stage.close();
  }
     
  public void validateInput(JFXTextField txtField){
      RequiredFieldValidator validator = new RequiredFieldValidator();
        
        txtField.getValidators().add(validator);
        validator.setMessage("مطلوب قيمتة");
        txtField.focusedProperty().addListener(new ChangeListener<Boolean>()
         {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
              
             if (!newValue) {
                    txtField.validate();
                }
            }
        }
        );
  }
  
  
  public boolean isDouble( String input )
{
   try
   {
      Double.parseDouble(input );
      return true;
   }
   catch( Exception e )
   {
      return false;
   }
}
  
  public String getDate(){
      
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
        
  }
  
  
  

       
}
