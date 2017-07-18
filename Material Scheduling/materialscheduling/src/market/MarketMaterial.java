package market;

import java.util.Arrays;

import material.Material;

//Material class to be used in the market, 
//Inherits material number, contains cost
public class MarketMaterial extends Material {



		@Override
		public boolean display() {
			System.out.println("MaterialNumber: " + getMatNum() + ", description: " + getDescription() + ", scrap: " + getScrap() + ", category: " 
			        + getCategory() + ", cost: " + getCost() + ", tolerance: " +getTolerance());
			return false;
		}
		
}
