package inventory;

import java.util.Arrays;

import material.Material;

//Material class to be used in the inventory, 
//Inherits material number, contains quantity
public class InventoryMaterial extends Material {
	
	
	private double quantity;	//Quantity in inventory in pounds
	private double usage;		//Maximum percentage that can be used
	
	public InventoryMaterial() {		
	}
	
	// Creates a material with a material number, description, category, scrap, cost, usage, tolerance, and quantity
	public InventoryMaterial(int matNum, String desc, String scrap, String catagory, int cost, int quantity, int usage, int tolerance) {
		this.setMatNum(matNum);
		this.setDescription(desc);
		this.setCategory(catagory);
		this.setScrap(scrap);
		this.setCost(cost);
		this.setUsage(usage);
		this.setTolerance(tolerance);
		this.setQuantity(quantity);
		
	}

	
	public double getQuantity() {
		return quantity;
	}


	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public double getUsage() {
		return usage;
	}


	public void setUsage(double usage) {
		this.usage = usage;
	}


	public boolean display(){
		System.out.println("MaterialNumber: " + getMatNum() + ", description: " + getDescription() + ", scrap: " + getScrap() + ", category: " 
        + getCategory() + ", cost: " + getCost() + ", quantity: " + quantity + ", usage: " + usage + ", tolerance: " + getTolerance());
		return false;
		
	}
	
	

}
