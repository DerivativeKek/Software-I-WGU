/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarei.jordan.esposito.model;

/**
 *
 * @author jorda
 */


public class InHousePart extends Part{
    
    private int machineId;
    
    public InHousePart(int machineId) {
        super();
        this.machineId = machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
    public int getMachineId(){
        return machineId;
    }
    
    
    
}
