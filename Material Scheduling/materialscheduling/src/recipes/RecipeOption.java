package recipes;

import java.util.LinkedList;


//List that is under the Recipe Class
//Contains RecipeMaterialLists
//can add Recipe Material Lists to this list
public class RecipeOption {
	
	private LinkedList<RecipeMaterialList> recipeOptionList;
	//private RecipeMaterialList recipeMaterialList;
	
	
	public RecipeOption(){
		this.recipeOptionList = new LinkedList<RecipeMaterialList>();
		
	}
	
	
	public void addItem(RecipeMaterialList recipeMaterialList) {

		recipeOptionList.addLast(recipeMaterialList);		

	}
	
	
	
	public void display() {
		
		System.out.println("-Recipe Option");
		try {
			for(Object o: recipeOptionList){
				
				((RecipeMaterialList) o).display();
							}
		} catch (Exception e) {
			System.out.println("empty list");
		}
		
		
	}
	
}
