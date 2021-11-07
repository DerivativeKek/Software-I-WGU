/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarei.jordan.esposito.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import softwarei.jordan.esposito.model.Part;

/**
 *
 * @author jorda
 */
public class Product {
    
    //List of variables for 
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private IntegerProperty id;
    private IntegerProperty min;
    private IntegerProperty stock;
    private IntegerProperty max;
    private StringProperty name;
    private DoubleProperty price;
    private int productIndex;
    
    //Constructor
    public Product(int id, int min, int max, int stock, String name, double price) {
        this.id = new SimpleIntegerProperty(id);
        this.min = new SimpleIntegerProperty(min);
        this.stock = new SimpleIntegerProperty(stock);
        this.max = new SimpleIntegerProperty(max);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    } 
    
    public IntegerProperty getIdCol() {
        return id;
    }
    
    public DoubleProperty getPriceCol()
    {
        return price;
    }
    
    public StringProperty getNameCol(){
        return name;
    }
    
    public IntegerProperty getStockCol(){
        return stock;
    }
    
    //Getters and setters
    public int getId() {
        return this.id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getMin() {
        return this.min.get();
    }

    public void setMin(int min) {
        this.min.set(min);
    }

    public int getStock() {
        return this.stock.get();
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public int getMax() {
        return this.max.get();
    }

    public void setMax(int max) {
        this.max.set(max);
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getPrice() {
        return this.price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }
    
    public ObservableList<Part> getAllAssociatedParts(){
        
        return associatedParts;
    }
    
    public void addAssociatedPart(Part selectedPart)
    {
       associatedParts.addAll(selectedPart);
    }
    public void deleteAssociatedPart(Part selectedPart)
    {
        associatedParts.remove(selectedPart);
    }
    public void setAssociatedParts(ObservableList<Part> part){
        this.associatedParts = part;
    }

    public static String isProductValid(String name, int min, int max, int inv, double price, ObservableList<Part> parts, String message){
        String errorMessage = new String();
        double sumParts = 0.00;
        for (int i = 0; i < parts.size(); i++) {
            sumParts = sumParts + parts.get(i).getPrice();
        }
        if (min < 0) {
            errorMessage = errorMessage + ("The inventory must be greater than 0.");
        }
        if (name.equals("")) {
            errorMessage = errorMessage + ("Name field is blank.");
        }
        if (inv < min || inv > max) {
            errorMessage = errorMessage + ("Part inventory must be between MIN and MAX values.");
        }
        if (price < 0) {
            errorMessage = errorMessage + ("The price must be greater than $0");
        }
        if (min > max) {
            errorMessage = errorMessage + ("The inventory MIN must be less than the MAX.");
        }
        if (sumParts > price) {
            errorMessage = errorMessage + ("Product price must be greater than cost of parts.");
        }
        if (parts.size() < 1) {
            errorMessage = errorMessage + ("There must be at least one part in the product.");
        }
       
        return errorMessage;
    }
}
