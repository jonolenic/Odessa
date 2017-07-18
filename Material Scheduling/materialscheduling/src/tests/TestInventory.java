package tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.Before;

import inventory.Inventory;
import inventory.InventoryMaterial;



//test to verify inventory material creation

public class TestInventory {
	
	
	InventoryMaterial mat = new InventoryMaterial(123, "d", "s", "c", 2, 3, 4, 5);
	
	@Test
	public void testCat() {
		
		Assert.assertEquals(String.valueOf(mat.getCategory()), "c");
		
	}
	
	@Test
	public void testNum() {
	
		Assert.assertEquals(String.valueOf(mat.getMatNum()), "123.0");
		
	}
	
	@Test
	public void testDescription() {
		
		Assert.assertEquals(String.valueOf(mat.getDescription()), "d");
		
	}
	
	@Test
	public void testScrap() {
		
		Assert.assertEquals(String.valueOf(mat.getScrap()), "s");
		
	}
	
	@Test
	public void testCost() {
		
		Assert.assertEquals(String.valueOf(mat.getCost()), "2.0");
		
	}
	
	@Test
	public void testQuan() {
		
		Assert.assertEquals(String.valueOf(mat.getQuantity()), "3.0");
		
	}
	
	
	@Test
	public void tesUsage() {
		
		Assert.assertEquals(String.valueOf(mat.getUsage()), "4.0");
		
	}
	
	
	@Test
	public void testTol() {
		
		Assert.assertEquals(String.valueOf(mat.getTolerance()), "5.0");
		
	}
	
	
	
	
	
	
	

}
