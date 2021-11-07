/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarei.jordan.esposito.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static softwarei.jordan.esposito.controllers.main_inventoryProgram.getProdIndex;
import softwarei.jordan.esposito.model.Inventory;
import static softwarei.jordan.esposito.model.Inventory.getAllParts;
import static softwarei.jordan.esposito.model.Inventory.getAllProducts;
import softwarei.jordan.esposito.model.Part;
import softwarei.jordan.esposito.model.Product;

/**
 * FXML Controller class
 *
 * @author jdschildt
 */
public class modifyProductController implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button clearButton;
    @FXML
    private TableView<Part> currentPartsTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private Button addAssociatedPart;
    @FXML
    private TableView<Part> deletePartsTableView;
    @FXML
    private TableColumn<Part, Integer> dpartIdCol;
    @FXML
    private TableColumn<Part, String> dpartNameCol;
    @FXML
    private TableColumn<Part, Integer> dpartInvCol;
    @FXML
    private TableColumn<Part, Double> dpartPriceCol;
    @FXML
    private TextField productsIDField;
    @FXML
    private TextField productsNameField;
    @FXML
    private TextField productsInvField;
    @FXML
    private TextField productsPriceField;
    @FXML
    private TextField productsMaxField;
    @FXML
    private TextField productsMinField;
    @FXML
    private Button removeAssociatedPart;
    @FXML
    private Button saveProduct;
    @FXML
    private Button prodCancel;
    
    private int prodIndex = getProdIndex();
    private final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private int PID;
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();
    private boolean repeatAdd = false;
    private boolean deletedItem = false;
    private String exceptMessage = new String();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Product selectedProd = getAllProducts().get(prodIndex);
        PID = selectedProd.getId();
        assocParts = selectedProd.getAllAssociatedParts();
        productsIDField.setText(""+ PID);
        productsNameField.setText(selectedProd.getName());
        productsInvField.setText(Integer.toString(selectedProd.getStock()));
        productsMaxField.setText(Integer.toString(selectedProd.getMax()));
        productsMinField.setText(Integer.toString(selectedProd.getMin()));
        productsPriceField.setText(Double.toString(selectedProd.getPrice()));
        partIdCol.setCellValueFactory(cellData -> cellData.getValue().getIdCol().asObject());
        partNameCol.setCellValueFactory(cellData -> cellData.getValue().getNameCol());
        partInvCol.setCellValueFactory(cellData -> cellData.getValue().getStockCol().asObject());
        partPriceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceCol().asObject());
        dpartIdCol.setCellValueFactory(cellData -> cellData.getValue().getIdCol().asObject());
        dpartNameCol.setCellValueFactory(cellData -> cellData.getValue().getNameCol());
        dpartInvCol.setCellValueFactory(cellData -> cellData.getValue().getStockCol().asObject());
        dpartPriceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceCol().asObject());
        updatePartTable();
        updateDelPartTable();  
    }    

    @FXML 
        void clearSearch(ActionEvent event) {
            updatePartTable();
            searchField.setText("");
        }
    
    @FXML
        void searchButton(ActionEvent event){
            if(!searchField.getText().trim().isEmpty()){
                allParts.clear();
                currentPartsTableView.setItems(Inventory.lookupPart(searchField.getText().trim()));
                currentPartsTableView.refresh();
            }
            else if(searchField.getText().trim().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Error");
                alert.setHeaderText("Empty Search field");
                alert.setContentText("Please enter a search term to search");
                alert.showAndWait();
                
            }
        }

    @FXML
    private void addPart(ActionEvent event) {
        Part selectedPart = currentPartsTableView.getSelectionModel().getSelectedItem();
            
            if(selectedPart != null){
                int id = selectedPart.getId();
                for (int i = 0; i < assocParts.size(); i++){
                    if(assocParts.get(i).getId() == id){
                       Alert alert = new Alert(Alert.AlertType.ERROR);
                       alert.setTitle("Error adding part to product!");
                       alert.setHeaderText("The selected part is already associated to the product!");
                       repeatAdd = true;
                    }
                }
                
                if (!repeatAdd)
                    assocParts.add(selectedPart);
                
                deletePartsTableView.setItems(assocParts);
                    
            }
    }

    @FXML
    private void deletePart(ActionEvent event) {
        Part selectedPart = deletePartsTableView.getSelectionModel().getSelectedItem();
        String tmp_name = selectedPart.getName().trim();

        if(selectedPart != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete part");
            alert.setHeaderText("Are you sure you want to delete: " + tmp_name);
            alert.setContentText("Click ok to confirm");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK) {
                assocParts.remove(selectedPart);
                updatePartTable();
                deletedItem = true;
            } else{
                return;
            }

            if(deletedItem)
            {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Success");
                alert2.setHeaderText("Part Deleted");
                alert2.setContentText("Item: " + tmp_name + "was deleted successfully.");
            }
            else if(!deletedItem)
            {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Failure");
                alert2.setHeaderText("Part unable to be deleted.");
                alert2.setContentText("Item: " + tmp_name + "was not deleted successfully. Please check and try again.");
            }
        }
    }

    @FXML
    private void saveModifiedProduct(ActionEvent event) throws IOException{
        String prodName = productsNameField.getText();
        String prodStock = productsInvField.getText();
        String prodMax = productsMaxField.getText();
        String prodMin = productsMinField.getText();
        String prodPrice = productsPriceField.getText();

        try {
            exceptMessage = Product.isProductValid(prodName, Integer.parseInt(prodMin), Integer.parseInt(prodMax), Integer.parseInt(prodStock),
                                                   Double.parseDouble(prodPrice), assocParts, exceptMessage);

            if(exceptMessage.length() > 0 ) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Unable to add Product");
                alert.setHeaderText("Error: ");
                alert.setContentText(exceptMessage);
                alert.showAndWait();
            } else { 
                Product createdProd = new Product(PID, Integer.parseInt(prodMin), Integer.parseInt(prodMax), Integer.parseInt(prodStock), prodName, Double.parseDouble(prodPrice));
                createdProd.setAssociatedParts(assocParts);
                Inventory.updateProduct(prodIndex, createdProd);
                Parent main = FXMLLoader.load(getClass().getResource("/softwarei/jordan/esposito/fxml/IMS_main.fxml"));
                Scene scene = new Scene(main);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();

            }
        } catch(NumberFormatException ex) {
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Error Adding Part");
             alert.setHeaderText("Error");
             alert.setContentText("Form contains blank fields, or you have entered incorrect values. Please verify and try again.");
             alert.showAndWait();
        }
    }

    @FXML
    private void onCancel(ActionEvent event) throws IOException {
        Parent mainScreen = FXMLLoader.load(getClass().getResource("/softwarei/jordan/esposito/fxml/IMS_main.fxml"));
        Scene returnToMain = new Scene(mainScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(returnToMain);
        window.show();
        Inventory.setProdCount();
    }
    
    public void updatePartTable() {
        currentPartsTableView.setItems(getAllParts());
    }

    public void updateDelPartTable() {
        deletePartsTableView.setItems(assocParts);
    }
}
