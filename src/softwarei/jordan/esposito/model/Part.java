/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarei.jordan.esposito.model;

import javafx.beans.property.*;

/**
 *
 * @author jorda
 */
public abstract class Part {

    
    private StringProperty name = new SimpleStringProperty("");
    private IntegerProperty id = new SimpleIntegerProperty(0);
    private DoubleProperty price = new SimpleDoubleProperty(0.00);
    private IntegerProperty stock = new SimpleIntegerProperty(0);
    private IntegerProperty min = new SimpleIntegerProperty(0);
    private IntegerProperty max = new SimpleIntegerProperty(0);

    //Contructor
    
    public Part() {
    }
    
    
    public String getName() {
        return name.getValue();
    }
    
    public StringProperty getNameCol(){
        return name;
    }

    public void setName(StringProperty name) {
        this.name = name;
    }

    public int getId() {
        return this.id.get();
    }
    
    public IntegerProperty getIdCol(){
        return id;
    }

    public void setId(IntegerProperty id) {
        this.id = id;
    }

    public DoubleProperty getPriceCol() {
        return price;
    }
    
    public double getPrice() {
        return this.price.get();
    }

    public void setPrice(DoubleProperty price) {
        this.price = price;
    }

    public int getStock() {
        return this.stock.get();
    }
    
    public IntegerProperty getStockCol() {
        return stock;
    }

    public void setStock(IntegerProperty stock) {
        this.stock = stock;
    }

    public IntegerProperty getMinCol() {
        return min;
    }
    
    public int getMin() {
        return this.min.get();
    }

    public void setMin(IntegerProperty min) {
        this.min = min;
    }

    public IntegerProperty getMaxCol() {
        return max;
    }
    public int getMax() {
        return this.max.get();
    }

    public void setMax(IntegerProperty max) {
        this.max = max;
    }

    
}
