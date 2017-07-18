package recipes;

import material.Material;

//Material class, under RecipeMaterialList, contains individual material, 
//Inherits material number, contains quantity
public class RecipeMaterial extends Material{

	private String gradeNum;
	private double gradeCost;
	private int numOfHeats;
	
	public int getNumOfHeats() {
		return numOfHeats;
	}

	public void setNumOfHeats(int numOfHeats) {
		this.numOfHeats = numOfHeats;
	}

	public double getGradeCost() {
		return gradeCost;
	}

	public void setGradeCost(double gradeCost) {
		this.gradeCost = gradeCost;
	}

	public String getGradeNum() {
		return gradeNum;
	}

	public void setGradeNum(String gradeNum) {
		this.gradeNum = gradeNum;
	}

	@Override
	public boolean display() {
		
		System.out.println("MaterialNumber: " + getMatNum() + ", quauntity " + getGradeNum());
		return false;
	}

}
