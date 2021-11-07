/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarei.jordan.esposito.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static softwarei.jordan.esposito.controllers.main_inventoryProgram.getPartIndex;
import softwarei.jordan.esposito.model.InHousePart;
import softwarei.jordan.esposito.model.Inventory;
import softwarei.jordan.esposito.model.OutsourcedPart;
import softwarei.jordan.esposito.model.Part;
import static softwarei.jordan.esposito.model.Inventory.getAllParts;
import static softwarei.jordan.esposito.controllers.AddPartScreenController.isPartValid;
/**
 * FXML Controller class
 *
 * @author jdschildt
 */
public class modifyPartScreenController implements Initializable {
    private int partIndex = getPartIndex();
    private int PID;
    public String exceptString = new String();
    
    @FXML
    private RadioButton inHouseButton;
    @FXML
    private RadioButton outsourceButton;
    @FXML
    private Text changeText;
    @FXML
    private Button CancelButton;
    @FXML
    private TextField partsInvTextField;
    @FXML
    private TextField partsPriceTextField;
    @FXML
    private TextField partsNameTextField;
    @FXML
    private TextField partsMaxTextField;
    @FXML
    private TextField partsMachineIDTextField;
    @FXML
    private TextField partsMinTextField;
    @FXML
    private TextField partsPartIdTextField;

    private boolean isOutsourced;
    /**
     * Initializes the controller class.
     */
    @FXML
    private void onCancel(ActionEvent event) throws Exception{
        Parent mainScreen = FXMLLoader.load(getClass().getResource("/softwarei/jordan/esposito/fxml/IMS_main.fxml"));
        Scene returnToMain = new Scene(mainScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(returnToMain);
        window.show();
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
            String partInv = partsInvTextField.getText();
            String nameOfPart = partsNameTextField.getText();
            String partPrice = partsPriceTextField.getText();
            String partMax = partsMaxTextField.getText();
            String partMin = partsMinTextField.getText();
            String partSourceString = partsMachineIDTextField.getText();
            
            try {
                if(isOutsourced == true){
                    exceptString = isPartValid(nameOfPart, exceptString, Double.parseDouble(partPrice), Integer.parseInt(partInv),Integer.parseInt(partMax) , Integer.parseInt(partMin), partSourceString);
                    }

                else{
                    exceptString = isPartValid(nameOfPart, exceptString, Double.parseDouble(partPrice), Integer.parseInt(partInv),Integer.parseInt(partMax) , Integer.parseInt(partMin), Integer.parseInt(partSourceString));
                    }
                
                
                if(exceptString.length() > 0){
                        Alert exceptionAlert = new Alert(Alert.AlertType.INFORMATION);
                        exceptionAlert.setTitle("Unable to add part");
                        exceptionAlert.setContentText(exceptString);
                        exceptionAlert.setHeaderText("Error: ");
                        exceptionAlert.showAndWait();
                        exceptString = "";
                } else {
                    
                    if (isOutsourced == false) {
                        
                        InHousePart inPart = new InHousePart(Integer.parseInt(partSourceString));
                        inPart.setName(new SimpleStringProperty(nameOfPart));
                        inPart.setId(new SimpleIntegerProperty(PID));
                        inPart.setMax(new SimpleIntegerProperty(Integer.parseInt(partMax)));
                        inPart.setMin(new SimpleIntegerProperty(Integer.parseInt(partMin)));
                        inPart.setPrice(new SimpleDoubleProperty(Double.parseDouble(partPrice)));
                        inPart.setStock(new SimpleIntegerProperty(Integer.parseInt(partInv)));
                        Inventory.updatePart(partIndex, inPart);
                    } else {
                        
                        OutsourcedPart outPart = new OutsourcedPart(partSourceString);
                        outPart.setName(new SimpleStringProperty(nameOfPart));
                        outPart.setId(new SimpleIntegerProperty(PID));
                        outPart.setMax(new SimpleIntegerProperty(Integer.parseInt(partMax)));
                        outPart.setMin(new SimpleIntegerProperty(Integer.parseInt(partMin)));
                        outPart.setPrice(new SimpleDoubleProperty(Double.parseDouble(partPrice)));
                        outPart.setStock(new SimpleIntegerProperty(Integer.parseInt(partInv)));
                        Inventory.updatePart(partIndex, outPart);
                    }
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Existing part modified successfully!");
                    alert.setHeaderText("Part modified");
                    alert.setContentText("The part " + nameOfPart + " was modified successfully!");
                    Parent mainScreen = FXMLLoader.load(getClass().getResource("/softwarei/jordan/esposito/fxml/IMS_main.fxml"));
                    Scene returnToMain = new Scene(mainScreen);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(returnToMain);
                    window.show();
                
                }
                
            } catch (NumberFormatException ex){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Modifying Part");
                alert.setHeaderText("Error");
                alert.setContentText("Form contains blank fields, or you have entered incorrect values. Please verify and try again.");
                alert.showAndWait();
            }
        }
        
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boolean tmp_bool = false;
        int boolLogic = 0;
        
        Part selectedPart = getAllParts().get(partIndex);
        PID = selectedPart.getId();
 
        partsPartIdTextField.setText(""+PID);
        partsNameTextField.setText(selectedPart.getName());
        partsInvTextField.setText(Integer.toString(selectedPart.getStock()));
        partsMaxTextField.setText(Integer.toString(selectedPart.getMax()));
        partsMinTextField.setText(Integer.toString(selectedPart.getMin()));
        partsPriceTextField.setText(Double.toString(selectedPart.getPrice()));

        
        if(selectedPart instanceof InHousePart){
            tmp_bool = true;
            boolLogic = tmp_bool ? 1 : 0;
            isOutsourced = false;
        }else{
            tmp_bool = false;
            boolLogic = tmp_bool ? 1 : 0;
            isOutsourced = true;
        }            
        
        
        switch(boolLogic){
            case 1:
                if(inHouseButton.isSelected())
                    partsMachineIDTextField.setText(Integer.toString(((InHousePart) getAllParts().get(partIndex)).getMachineId()));
                else{
                    outsourceButton.setSelected(false);
                    inHouseButton.setSelected(true);
                    partsMachineIDTextField.setText(Integer.toString(((InHousePart) getAllParts().get(partIndex)).getMachineId()));
                }
                break;
            case 0: 
                if(outsourceButton.isSelected())
                    partsMachineIDTextField.setText(((OutsourcedPart) getAllParts().get(partIndex)).getCompanyName());
                else{
                    outsourceButton.setSelected(true);
                    inHouseButton.setSelected(false);
                    partsMachineIDTextField.setText(((OutsourcedPart) getAllParts().get(partIndex)).getCompanyName());
                }
                break;
        }
        
        
    }  

}
