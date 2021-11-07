/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarei.jordan.esposito.controllers;

import java.io.IOException;
import softwarei.jordan.esposito.model.Part;
import softwarei.jordan.esposito.model.Product;
import softwarei.jordan.esposito.main.inventoryProgram;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import softwarei.jordan.esposito.model.Inventory;
import static softwarei.jordan.esposito.model.Inventory.doesPartExistProd;
import static softwarei.jordan.esposito.model.Inventory.removePart;

import softwarei.jordan.esposito.model.Part;
import softwarei.jordan.esposito.model.Product;
import static softwarei.jordan.esposito.model.Inventory.getAllParts;
import static softwarei.jordan.esposito.model.Inventory.getAllProducts;

/**
 * FXML Controller class
 *
 * @author jorda
 */
public class main_inventoryProgram implements Initializable {
    
    @FXML
        private TableView<Part> partsTable;
    @FXML
        private TableColumn<Part, String> nameCol;
    @FXML
        private TableColumn<Part, Double> priceCol;
    @FXML
        private TableColumn<Part, Integer> invCol;    
    @FXML
        private TableColumn<Part, Integer> idCol;
    
    @FXML
        private TableView<Product> productsTable;
    @FXML
        private TableColumn<Product, String> prodNameCol;
    @FXML
        private TableColumn<Product, Double> prodPriceCol;
    @FXML
        private TableColumn<Product, Integer> prodInvCol;    
    @FXML
        private TableColumn<Product, Integer> prodIdCol;
    
    private static Part modifyPart;
    private static int partIndex;
    private static int productIndex;
    private static Part deletePart;
    private static Product modifyProd;
    
    @FXML
            ButtonBar productButtonBar;
    @FXML        
            Button addProduct;
    @FXML        
            Button addParts;
    @FXML
            Button modifyParts;
    @FXML
            Button addProducts;
    @FXML
            Button deleteProd;
    @FXML   
            Button modifyProds;
    
    
    public static int getPartIndex() {
        return partIndex;
    }
    public static int getProdIndex() {
        return productIndex;
    }
    

    
    @FXML
        void modifyPartsClick(ActionEvent event) throws IOException{
            modifyPart = partsTable.getSelectionModel().getSelectedItem();
            partIndex = getAllParts().indexOf(modifyPart);
            
            Parent modifyInstance = FXMLLoader.load(getClass().getResource("/softwarei/jordan/esposito/fxml/modifyPartScreen.fxml"));
            Scene scene = new Scene(modifyInstance);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show(); 
            
        }
        
    @FXML
        void modifyProductClick(ActionEvent event) throws IOException{
            modifyProd = productsTable.getSelectionModel().getSelectedItem();
            productIndex = getAllProducts().indexOf(modifyProd);
            
            Parent modifyInstance = FXMLLoader.load(getClass().getResource("/softwarei/jordan/esposito/fxml/modifyProdScreen.fxml"));
            Scene scene = new Scene(modifyInstance);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show(); 
            
        }
    

    @FXML 
        void addProductScreen(ActionEvent event) throws Exception{
            Parent addProd = FXMLLoader.load(getClass().getResource("/softwarei/jordan/esposito/fxml/AddProdScreen.fxml"));
            Scene AddProdScene = new Scene(addProd);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(AddProdScene);
            window.show();   
    }
    @FXML 
        void addPartScreen(ActionEvent event) throws Exception{
            Parent addPart = FXMLLoader.load(getClass().getResource("/softwarei/jordan/esposito/fxml/addPartScreen.fxml"));
            Scene AddPartScene = new Scene(addPart);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(AddPartScene);
            window.show();
        }
        
    @FXML
        void deleteSelectedPart(ActionEvent event) throws IOException{
            Part part = partsTable.getSelectionModel().getSelectedItem();
            if(!doesPartExistProd(part)){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Part Deletion Error");
                alert.setHeaderText("Part can't be removed!");
                alert.setContentText("This part is part of a product, please remove the product or part.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle("Product Delete");
                alert.setHeaderText("Confirm?");
                alert.setContentText("Are you sure you want to delete " + part.getName() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                
                if(result.get() == ButtonType.OK)
                {
                    removePart(part);
                    updatePartsTable();
                    
                } else
                    return;
            }
            
        }
        
    @FXML
        void deleteSelectedProduct(ActionEvent event) throws IOException{
            Product selectedProd = productsTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Are you sure you want to delete " + selectedProd.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if(result.get() == ButtonType.OK){
                Inventory.removeProduct(selectedProd);
                updateProductTable();
            }
        }
    
    public void startStaging(Stage stage) {
        stage.setTitle("Inventory Management System");
        stage.setResizable(false);
        stage.show();
    }
    
    public void updatePartsTable() {
        partsTable.setItems(getAllParts());
    }
    
    public void updateProductTable(){
        productsTable.setItems(getAllProducts());
    }
    
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idCol.setCellValueFactory(cData -> cData.getValue().getIdCol().asObject());
        priceCol.setCellValueFactory(cData -> cData.getValue().getPriceCol().asObject());
        invCol.setCellValueFactory(cData -> cData.getValue().getStockCol().asObject());
        nameCol.setCellValueFactory(cData -> cData.getValue().getNameCol());
        
        prodIdCol.setCellValueFactory(cData -> cData.getValue().getIdCol().asObject());
        prodPriceCol.setCellValueFactory(cData -> cData.getValue().getPriceCol().asObject());
        prodInvCol.setCellValueFactory(cData -> cData.getValue().getStockCol().asObject());
        prodNameCol.setCellValueFactory(cData -> cData.getValue().getNameCol());
            
        updatePartsTable();
        updateProductTable();
    }
    
    
}