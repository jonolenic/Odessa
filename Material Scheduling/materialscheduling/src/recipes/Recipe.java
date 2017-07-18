package recipes;

import java.util.LinkedList;


/*
 * Recipes hold the data for a completed grade of steel. These grades are later displayed to the user.
 */

public class Recipe {
	
	private int recipeTag;
	//private RecipeOption recipeOption;
	private LinkedList<Object> recipeList;
	
	
	public Recipe(){
		
		//this.recipeTag = recipeTag;
		this.recipeList = new LinkedList<Object>();
		
	}
	
	public void addItem(Object recipeOption){
		
		recipeList.addLast(recipeOption);
		
	}
	
	public void display() {
		
		System.out.println("Tag: " + recipeTag);
		
		try {
			for(Object o: recipeList){
				
				((RecipeOption) o).display();
							}
		} catch (Exception e) {
			System.out.println("empty list");
		}
		
	}
	
}
