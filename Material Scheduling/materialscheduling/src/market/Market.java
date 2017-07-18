package market;

import java.util.LinkedList;

import inventory.InventoryMaterial;
import market.MarketMaterial;;


/*
 * The market is a linked list of MarketMaterials. The market serves as a countermeasure when
 * the inventory is unable to provide a requested material. It passes that request onto the market who
 * fulfills it at a higher price. The market does not track material quantity as it never runs out of materials.
 */
public class Market {

	private LinkedList<MarketMaterial> marketList;		
		
			
	public Market() {
		this.marketList = new LinkedList<MarketMaterial>();
	}


	
	//Adds a new material to inventory with quantity and cost
	public void addItem(double matNum, double cost) {

		MarketMaterial newMaterial = new MarketMaterial();

		newMaterial.setCost(cost);
		newMaterial.setMatNum(matNum);
		
		marketList.addLast(newMaterial);
		
	}
	
	public void addItem(double matNum, String description, String scrap, String category, double cost, double tolerance) {
		
		MarketMaterial newMaterial = new MarketMaterial();
		
		newMaterial.setMatNum(matNum);
		newMaterial.setDescription(description);
		newMaterial.setScrap(scrap);
		newMaterial.setCategory(category);
		newMaterial.setCost(cost);
		newMaterial.setTolerance(tolerance);
		
		marketList.add(newMaterial);
}

	
	public double passCost(int index) {
		return marketList.get(index).getCost() * 2;
	}
	
	public void clearMarket() {
		marketList.clear();
	}
	

	public void display() {

		for(Object o: marketList){
			o.toString();

		}

	}

}
