package grades;

import material.Material;

//Material class, under RecipeMaterialList, contains individual material, 
//Inherits material number, contains quantity
public class TagMaterial extends Material{

	private double quantity;
	
	
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	@Override
	public boolean display() {
		
		System.out.println("MaterialNumber: " + getMatNum() + ", quantity " + getQuantity());
		return false;
	}
	
	
	
	
}
