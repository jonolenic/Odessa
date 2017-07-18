package grades;

import java.util.LinkedList;


//List that is under RecipeOptionsList
//contains materials
//can add Materials to this list - (materialNum, Quantity)
public class TagMaterialList {

	private LinkedList<TagMaterial> tagMaterialList;
	
	public LinkedList<TagMaterial> getTagMaterialList() {
		return tagMaterialList;
	}


	public void setTagMaterialList(LinkedList<TagMaterial> tagMaterialList) {
		this.tagMaterialList = tagMaterialList;
	}


	public TagMaterialList(){
		this.tagMaterialList = new LinkedList<TagMaterial>();
	}
	
	
	//Adds a new material to inventory with quantity and cost
	public void addItem(TagMaterial newMaterial) {

		tagMaterialList.addLast(newMaterial);
		
	}
	
	// Return the material number of the selected material
	public double getMatNum(int index) {
		
		return tagMaterialList.get(index).getMatNum();
	}
	
	// Return the quantity of the selected material
	public double getMatQuantity(int index) {
		return tagMaterialList.get(index).getQuantity();
	}
	
	public int getSize() {
		return tagMaterialList.size();
	}
		
	public void display() {
		System.out.println("--Grade Material List");
		for(Object o: tagMaterialList){
			
			o.toString();

		}

	}
	
	
	
}

