package recipes;

import java.util.LinkedList;

import inventory.InventoryMaterial;


//List that is under RecipeOptionsList
//contains materials
//can add Materials to this list - (materialNum, Quantity)
public class RecipeMaterialList {

	private LinkedList<RecipeMaterial> recipeMaterialList;
	
	public RecipeMaterialList(){
		this.recipeMaterialList = new LinkedList<RecipeMaterial>();
	}
	
	
	//Adds a new material to inventory with quantity and cost
	public void addItem(String gradeNum, double gradeCost, int heatNum) {
		RecipeMaterial newMaterial = new RecipeMaterial();
		
		newMaterial.setGradeNum(gradeNum);
		newMaterial.setGradeCost(gradeCost);
		newMaterial.setNumOfHeats(heatNum);
		recipeMaterialList.addLast(newMaterial);
	}
	
	// Get grade num for the requested grade
	public String testGradeNum(int index) {
		return recipeMaterialList.get(index).getGradeNum();
	}
	
	// Get cost for the requested grade
	public double testGradeCost(int index) {
		return recipeMaterialList.get(index).getGradeCost();
	}
	
	// Get heats for the requested grade
	public int testHeatNum(int index) {
		return recipeMaterialList.get(index).getNumOfHeats();
	}
 	
		
	public void display() {
		System.out.println("--Recipe Material List");
		for(Object o: recipeMaterialList){
	
			o.toString();
		}

	}

}
