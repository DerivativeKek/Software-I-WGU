/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarei.jordan.esposito.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import softwarei.jordan.esposito.model.InHousePart;
import softwarei.jordan.esposito.model.Inventory;
import softwarei.jordan.esposito.model.OutsourcedPart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author jorda
 */
public class AddPartScreenController implements Initializable {

    private boolean isOutsourced;
    private int partId;
    private String exception = new String();
    
    
    @FXML
        private Button CancelButton;
    @FXML
        private RadioButton inHouseButton;
    @FXML
        private RadioButton outsourceButton;
    @FXML
        private Text changeText;
    @FXML
        private TextField partsNameTextField; 
    @FXML
        private TextField partsInvTextField;
    @FXML
        private TextField partsPriceTextField;
    @FXML
        private TextField partsMaxTextField;
    @FXML
        private TextField partsMinTextField;
    @FXML
        private TextField partsMachineIDTextField;
    @FXML
        private TextField partsIDTextField;
   
    
    public static String isPartValid(String nameOfPart, String error, double price, int partInv, int max, int min, String companyNameString) {
        if (nameOfPart == null) {
            error = error + ("Name field is blank. \n");
        }
        if (partInv < 1) {
            error = error + ("The inventory must be greater than 0. \n");
        }
        if (price < 1) {
            error = error + ("The price must be greater than $0 \n");
        }
        if (min > max) {
            error = error + ("The inventory MIN must be less than the MAX. \n");
        }
        if (partInv < min || partInv > max) {
            error = error + ("Part inventory must be between MIN and MAX values. \n");
        }
        return error;
    }
        
    
    public static String isPartValid(String nameOfPart, String error, double price, int partInv, int max, int min, int machineId) {
        
        if (nameOfPart == null) {
            error = error + ("Name field is blank. \n");
        }
        if (partInv < 1) {
            error = error + ("The inventory must be greater than 0. \n");
        }
        if (price < 1) {
            error = error + ("The price must be greater than $0 \n");
        }
        if (min > max) {
            error = error + ("The inventory MIN must be less than the MAX. \n");
        }
        if (partInv < min || partInv > max) {
            error = error + ("Part inventory must be between MIN and MAX values. \n");
        }
        return error;
    }
    
    
    @FXML
        void onCancel(ActionEvent event) throws Exception{
            Parent mainScreen = FXMLLoader.load(getClass().getResource("/softwarei/jordan/esposito/fxml/IMS_main.fxml"));
            Scene returnToMain = new Scene(mainScreen);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(returnToMain);
            window.show();
            partId = Inventory.setPartCount();
        
        }
        
        
    
    
    @FXML
        void onOutsourceClick(ActionEvent event) throws Exception {
            this.isOutsourced = true;
            if(inHouseButton.isSelected()){
                inHouseButton.setSelected(false);
                this.outsourceButton.setSelected(true);
            } else {
                this.outsourceButton.setSelected(true);
            }
            changeText.textProperty().set("Company Name");
        }
        
    @FXML
        void onInHouseClick(ActionEvent event) throws Exception {
            this.isOutsourced = false;
            if(outsourceButton.isSelected()){
                outsourceButton.setSelected(false);
                this.inHouseButton.setSelected(true);
            } else {
                this.inHouseButton.setSelected(true);
            }
            changeText.textProperty().set("Machine ID");
            
        }
       
    @FXML
        void savePart(ActionEvent event) throws IOException{
            
            String nameOfPart = partsNameTextField.getText();
            String partInv = partsInvTextField.getText();
            String price = partsPriceTextField.getText();
            String min = partsMinTextField.getText();
            String max = partsMaxTextField.getText();
            String machineId = partsMachineIDTextField.getText();
            String companyNameString = partsNameTextField.getText();
            String partString= "";
            
                try {
                    if(isOutsourced == true){
                       exception = isPartValid(nameOfPart, exception, Double.parseDouble(price), Integer.parseInt(partInv),Integer.parseInt(max) , Integer.parseInt(min), companyNameString);
                        }

                    else{
                        exception = isPartValid(nameOfPart, exception, Double.parseDouble(price), Integer.parseInt(partInv),Integer.parseInt(max) , Integer.parseInt(min), Integer.parseInt(machineId));
                    }

                    if(exception.length() > 0){
                        Alert exceptionAlert = new Alert(Alert.AlertType.INFORMATION);
                        exceptionAlert.setTitle("Unable to add part");
                        exceptionAlert.setContentText(exception);
                        exceptionAlert.setHeaderText("Error: ");
                        exceptionAlert.showAndWait();
                        exception = "";
                    } else {
                        if(isOutsourced == true)
                        {
                            OutsourcedPart outPart = new OutsourcedPart(companyNameString);
                            outPart.setName(new SimpleStringProperty(nameOfPart));
                            outPart.setId(new SimpleIntegerProperty(partId));
                            outPart.setMax(new SimpleIntegerProperty(Integer.parseInt(max)));
                            outPart.setMin(new SimpleIntegerProperty(Integer.parseInt(min)));
                            outPart.setPrice(new SimpleDoubleProperty(Double.parseDouble(price)));
                            outPart.setStock(new SimpleIntegerProperty(Integer.parseInt(partInv)));
                            Inventory.addPart(outPart);

                        }
                        else
                        {
                            InHousePart inhousePart = new InHousePart(Integer.parseInt(machineId));
                            inhousePart.setName(new SimpleStringProperty(nameOfPart));
                            inhousePart.setId(new SimpleIntegerProperty(partId));
                            inhousePart.setMax(new SimpleIntegerProperty(Integer.parseInt(max)));
                            inhousePart.setMin(new SimpleIntegerProperty(Integer.parseInt(min)));
                            inhousePart.setPrice(new SimpleDoubleProperty(Double.parseDouble(price)));
                            inhousePart.setStock(new SimpleIntegerProperty(Integer.parseInt(partInv)));
                            Inventory.addPart(inhousePart);
                        }
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("New part added successfully!");
                        alert.setHeaderText("Part Added");
                        alert.setContentText("The part " + partString + " was added successfully!");
                        Parent mainScreen = FXMLLoader.load(getClass().getResource("/softwarei/jordan/esposito/fxml/IMS_main.fxml"));
                        Scene returnToMain = new Scene(mainScreen);
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(returnToMain);
                        window.show();
                    }
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Adding Part");
                    alert.setHeaderText("Error");
                    alert.setContentText("Form contains blank fields, or you have entered incorrect values. Please verify and try again.");
                    alert.showAndWait();
            }
        }
    /**
     * Initializes the controller class.
     */
        
        
    public void initialize(URL url, ResourceBundle rb) {
        partId = Inventory.getPartCount();
        partsIDTextField.setText("Auto Gen: " + partId);
    }    

    
}
