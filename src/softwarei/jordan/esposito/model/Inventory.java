/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarei.jordan.esposito.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author jorda
 */
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partCount = 0;
    private static int prodCount = 0;
    
    
    public Inventory(){    
    }
    
    public static boolean doesPartExistProd(Part part) {
        boolean found = false;

        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getAllAssociatedParts().contains(part)) {
                found = true;
            }
        }
        return found;
    }
    
    public static void removePart(Part selectedPart){
        allParts.remove(selectedPart);
    }
    
    public static void removeProduct(Product selectedProd){
        allProducts.remove(selectedProd);
    }
    
    public static int getPartCount() {
        partCount++;
        return partCount;
    }
    public static int setPartCount() {
        partCount--;
        return partCount;
    }
    
    public static int getProdCount() {
        prodCount++;
        return prodCount;
    }
    public static int setProdCount() {
        prodCount--;
        return prodCount;
    }


    public static void addPart(Part newPart){
        if(newPart != null)
        {
            allParts.add(newPart);
        }
    }
    
    public static void addProduct(Product newProduct){
        if(newProduct != null)
        {
            allProducts.add(newProduct);
        }
        
    }
    
    public static Part lookupPart(int PartId){
        if (!allParts.isEmpty()) { 
            for (int i = 0; i < allParts.size(); i++ ){
                if (allParts.get(i).getId()== PartId)
                    return(allParts.get(i));
            }
        }
        return null;
    }
    
    
    public static Product lookupProduct(int productId){
        
        boolean tmp_bool;
        
        if(allProducts.isEmpty()){
            tmp_bool = true;
        } else {
            tmp_bool = false;
        }
        
        if (!tmp_bool)
        {
            for(int i = 0; i < allProducts.size();i++){
                if(allProducts.get(i).getId() == productId)
                    return(allProducts.get(i));
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) { 
        if (!allParts.isEmpty()){
            ObservableList searchPartsList = FXCollections.observableArrayList();
            
            for (Part part : getAllParts()){
                if(part.getName().contains(partName))
                    searchPartsList.add(part);      
            } return searchPartsList; 
        } return null;}
    
    public static ObservableList<Product> lookupProduct(String productName){
        if (!allProducts.isEmpty()){
            ObservableList searchProductsList = FXCollections.observableArrayList();
            
            for (Product product : getAllProducts()){
                if(product.getName().contains(productName))
                    searchProductsList.add(product);
            } return searchProductsList;
        } return null;
    }
    
    public static void updatePart(int index, Part selectedPart){
        for(index =  0; index < allParts.size();index++){
            if (allParts.get(index).getId() == selectedPart.getId()){
                allParts.set(index, selectedPart);
                break;
            }
        }
    }    
    
    public static void updateProduct(int index, Product selectedProduct){ 
        for(index = 0; index < allProducts.size(); index++){
            if(allProducts.get(index).getId() == selectedProduct.getId()){
                allProducts.set(index, selectedProduct);
            }
        }
    }
    
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    
    public static boolean deletePart(Part selectedPart){ 
        for (int i = 0; i < allParts.size(); i++){
            if (allParts.get(i).getId() == selectedPart.getId()){
                allParts.remove(i);
                return true;
            }
        } return false;
    }
    
    public boolean deleteProduct(Product selectedProduct){ 
        for(int i = 0 ; i < allProducts.size(); i++){
            if(allProducts.get(i).getId() == selectedProduct.getId()){
                allProducts.remove(i);
                return true;
            }
        } return false;
    }
}
