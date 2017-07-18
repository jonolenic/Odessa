package calculations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import inventory.Inventory;
import market.Market;
import grades.*;
import odessaInterface.ProdCost;
import odessaInterface.ExeStatus;

public class CostCalculations {
	static int test = 0;
	static Inventory inventory = new Inventory();
	static Inventory searchInventory = new Inventory();
	static Inventory realInventory = new Inventory();
	static Market market = new Market();
	static ExeStatus console = new ExeStatus();
	
	// Returns a reference to the console
	public static ExeStatus getConsole() {
		return console;
	}

	/* 
	 * This function determines whether the inventory and market need to be loaded or not.
	 * The first time it is run, it will load the inventory and market with raw materials.
	 * For all future iterations, it will go straight to cost calculation.
	 */
	public static void chooseFunction(Grade testGrade, int testMix, String gradeFile) {
		if (test == 0) {
			testRun(testGrade, testMix, gradeFile);
		}
		else {
			runCosts(testGrade, testMix, gradeFile, inventory, market);
		}
	}
	
	public static void updateRealInventory() {
		realInventory.updateInventory(inventory);
	}
	
	public static void updateSearchInventory() {
		searchInventory.updateInventory(inventory);
	}
	
	/* 
	 * This function loads the inventory and market linked lists with raw materials. This data
	 * is read in from a text file that is included with the workspace. This function is only
	 * run once for the very first grade iteration during execution. After the inventory is loaded,
	 * the data is passed to the cost calculation function. Future iterations go directly to
	 * cost calculation.
	 */
	public static void testRun(Grade testGrade, int testMix, String gradeFile) {

		if (test == 1) {
			runCosts(testGrade, testMix, gradeFile, inventory, market);
		}

		try {
			File file = new File("files/mcode.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			
			// Values stored in a raw material
			double matNum;  		
			String description;		
			String scrap;				
			String category;			
			double cost;					
			float usage;	
			double quantity;	
			double tolerance;

			
			// Read in inventory and market values
			String line;
			line = bufferedReader.readLine();
			stringBuffer.append(line);
			stringBuffer.append("\n");
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				matNum = Integer.parseInt(line);
				stringBuffer.append("\n");
				line = bufferedReader.readLine();
				stringBuffer.append(line);
				description = line;
				stringBuffer.append("\n");
				line = bufferedReader.readLine();
				stringBuffer.append(line);
				scrap = line;
				stringBuffer.append("\n");
				line = bufferedReader.readLine();
				stringBuffer.append(line);
				category = line;
				stringBuffer.append("\n");
				line = bufferedReader.readLine();
				stringBuffer.append(line);
				cost = Double.parseDouble(line);
				stringBuffer.append("\n");
				line = bufferedReader.readLine();
				stringBuffer.append(line);
				quantity = Double.parseDouble(line);
				stringBuffer.append("\n");
				line = bufferedReader.readLine();
				stringBuffer.append(line);
				usage = Float.parseFloat(line);
				stringBuffer.append("\n");
				line = bufferedReader.readLine();
				stringBuffer.append(line);
				tolerance = Double.parseDouble(line);
				stringBuffer.append("\n");
				
				inventory.addInvItem(matNum, description, scrap, category, cost, quantity, usage, tolerance);
				realInventory.addInvItem(matNum, description, scrap, category, cost, quantity, usage, tolerance);
				market.addItem(matNum, description, scrap, category, cost, tolerance);
			}
			fileReader.close();
			System.out.println("\n\n");
			
			//Increment value to ensure inventory and market are only loaded in once
			test++;
			runCosts(testGrade, testMix, gradeFile, inventory, market);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
			/* 
			 * This function calculates the cost for the given grade through the given number of
			 * heats (iterations). Arrays are used to store various values, such as each mix and their cost.
			 * For each iteration, mixes are pulled from the grade one at a time. Each requested raw
			 * material is pulled from the mix and is sent to the inventory to determine if the desired
			 * quantity of that material is available for use. Its cost is returned and the process is
			 * repeated for all materials in a mix. The total cost of this mix is stored in an array.
			 * Once all mixes for a grade have had their costs calculated, the cheapest mix from each
			 * iteration is found and stored. The cycle then repeats until the number of heats requested
			 * has been reached. The total cost for all heats requested for the grade is then calculated
			 * and sent to the final display class.
			 */
			public static int runCosts(Grade testGrade, int testMix, String gradeFile, Inventory inventory, Market market) {
			Grade grade;
			grade = testGrade;
			int numOfHeats = testMix;
			Document doc = console.getText().getDocument();
			console.setVisible(true);
			
			TagMaterialList tagList = new TagMaterialList();
			ProdCost prodCosts = new ProdCost();

			int timesRan = 0;
			

			double chosenMixTagNum;
			double chosenMixCost; 
			double chosenMixPower;
			double chosenMixOxygen;
			double chosenMixTime;
			
			System.out.println("Number of mixes in grade: " + grade.getSize());
			try { 
			console.getText().getDocument().insertString(doc.getLength(), "\n\nNumber of mixes in grade: " + grade.getSize(), null);
			   } catch(BadLocationException exc) {
				      exc.printStackTrace();
				   }
				
			double holdCost = 0;
			double[] holdMix = new double[grade.getSize()];
			double[] holdCheapestMix = new double [numOfHeats];
			double totalHeatCost = 0;
			
			// Run through total number of requested heats (iterations)
			for (int x = 0; x < numOfHeats; x++) {
				timesRan++;
				TagOption chosenMix = findViableMixes(testGrade)[0];
				if (chosenMix.getCost() == 0) {
					oldRunCosts(testGrade, testMix, gradeFile, inventory, market);
					return 0;
				}
				chosenMixTagNum = chosenMix.getTagNum();
				chosenMixCost = chosenMix.getCost();
				chosenMixPower= chosenMix.getPower();
				chosenMixOxygen = chosenMix.getOxygen();
				chosenMixTime = chosenMix.getTime();
				// Perform cost calculations for one mix at a time
					System.out.println("Mix: " + chosenMixTagNum);
					try { 
						console.getText().getDocument().insertString(doc.getLength(), "\n\nMix: " + chosenMixTagNum, null);
						   } catch(BadLocationException exc) {
							      exc.printStackTrace();
							   }


			
					tagList = GetGradeInfo.getMatList(grade, chosenMixTagNum);
					tagList.display();
					System.out.println("\n");
					double totalCost = 0;
					
					// Perform cost calculations for one raw material in a mix at a time
					for (int y = 0; y < tagList.getSize(); y++) {

						System.out.println("MatNum: " + tagList.getMatNum(y) + "  Quantity: " + tagList.getMatQuantity(y));
						System.out.println("\n");
						try {
						console.getText().getDocument().insertString(doc.getLength(), "\n\nMatNum: " + tagList.getMatNum(y) + "  Quantity: " + tagList.getMatQuantity(y), null);
					   } catch(BadLocationException exc) {
						      exc.printStackTrace();
						   }
						double mixNum = tagList.getMatNum(y);
						double mixQuant = tagList.getMatQuantity(y);
					
						double testCost = inventory.obtainMaterial(mixNum, mixQuant, market);
						System.out.println("Cost per 1000 lbs: $" + testCost);
						try {
						console.getText().getDocument().insertString(doc.getLength(), "\nCost per 1000 lbs: $" + testCost, null);
					   } catch(BadLocationException exc) {
						      exc.printStackTrace();
						   }
			
						double testQuant = mixQuant / 1000;
						System.out.println("Cost for material: $" + testCost * testQuant);
						try {
						console.getText().getDocument().insertString(doc.getLength(), "\nCost for material: $" + testCost * testQuant, null);
					   } catch(BadLocationException exc) {
						      exc.printStackTrace();
						   }
						totalCost += testCost * testQuant;
						}
					System.out.println("\nTotal cost: $" + totalCost);
					try {
					console.getText().getDocument().insertString(doc.getLength(), "\nTotal cost: $" + totalCost + "\n", null);
				   } catch(BadLocationException exc) {
					      exc.printStackTrace();
					   }
					holdCost = totalCost;
					
			
				System.out.println("\n");
				
				// Display total cost of one mix
					System.out.println("\nPower cost: $" + chosenMixPower);
					System.out.println("Oxygen cost: $" + chosenMixOxygen);
					System.out.println("Time cost: $" + chosenMixTime);
					try {
						console.getText().getDocument().insertString(doc.getLength(), "\n\nPower cost: $" + chosenMixPower + "\nOxygen cost: $" + chosenMixOxygen + "\nFurnace cost: $" + chosenMixTime, null);
					   } catch(BadLocationException exc) {
						      exc.printStackTrace();
						   }
					holdCost = holdCost + chosenMixPower + chosenMixOxygen + chosenMixTime;
					//grade.setMixCost(i, holdCost);
					System.out.println("\n\nTotal cost for mix " + chosenMixTagNum + ": $" + holdCost);
					try {
						console.getText().getDocument().insertString(doc.getLength(), "\n\nTotal cost for mix " + chosenMixTagNum + ": $" + holdCost, null);
					   } catch(BadLocationException exc) {
						      exc.printStackTrace();
						   }
					

			
				System.out.println("\n");

				totalHeatCost = totalHeatCost + holdCost;
				}
			
			// Display the results for each heat (iteration), including min, average, and max cost
			System.out.println("Times ran: " + timesRan);
			try {
				console.getText().getDocument().insertString(doc.getLength(), "\nTimes ran: " + timesRan, null);
			   } catch(BadLocationException exc) {
				      exc.printStackTrace();
				   }
			/*for (int z = 0; z < numOfHeats; z++) {
				System.out.println("Cheapest mix for iteration " + (z + 1) + " is " + holdCheapestMix[z]);
				try {
					console.getText().getDocument().insertString(doc.getLength(), "\n\nCheapest mix for iteration " + (z + 1) + " is " + holdCheapestMix[z], null);
				   } catch(BadLocationException exc) {
					      exc.printStackTrace();
					   }
			}*/
			
			
			System.out.println("The total cost for the requested number of heats for this grade is $" + totalHeatCost);
			try {
				console.getText().getDocument().insertString(doc.getLength(), "\nThe total cost for the requested number of heats for this grade is $" + totalHeatCost, null);
			   } catch(BadLocationException exc) {
				      exc.printStackTrace();
				   }
			System.out.println("The average cost for the requested number of heats for this grade is $" + (totalHeatCost / numOfHeats + "\n"));
			try {
				console.getText().getDocument().insertString(doc.getLength(), "\nThe average cost for the requested number of heats for this grade is $" + (totalHeatCost / numOfHeats + "\n"), null);
			   } catch(BadLocationException exc) {
				      exc.printStackTrace();
				   }
			
			//Send a grade's ID, its number of heats, and its cost to the final display function
			RecipeDisplay.updateTotal(gradeFile, totalHeatCost, numOfHeats);
			return 0;
			}
			
			/*
			 * Runs cost analysis to see if a mix is viable or not, stops when an inventory request cannot be made
			 */
			public static TagOption[] findViableMixes(Grade testGrade) {
				Grade grade;
				grade = testGrade;
				
				TagMaterialList tagList = new TagMaterialList();
				ProdCost prodCosts = new ProdCost();
				int viabilityCheck = 0;
				
				double[] holdCost = new double[grade.getSize()];
				double[] holdMix = new double[grade.getSize()];
				

				for (int i = 0; i < grade.getSize(); i++) {

					// Inventory is reloaded with fresh values for every mix test
					if (test <= 1) {
						loadMixes();
					}
					else if (test >= 2) {
						updateSearchInventory();
					}
					double totalCost = 0;
					double newMix = grade.getTagNum(i);
					holdMix[i] = grade.getTagNum(i);
					tagList = GetGradeInfo.getMatList(grade, newMix);
					// Perform cost calculations for one raw material in a mix at a time
					for (int y = 0; y < tagList.getSize(); y++) {
						double mixNum = tagList.getMatNum(y);
						double mixQuant = tagList.getMatQuantity(y);
					
						// Clone of usual inventory request function, but returns 0 if material cannot be found
						double testCost = searchInventory.checkMixViability(mixNum, mixQuant);
						// If material cannot be found, set mix viability to false
						if  (testCost == 0) {
							grade.setMixViability(i, false);
							viabilityCheck++;
							break;
						}
			
						double testQuant = mixQuant / 1000;

						totalCost += testCost * testQuant;
						grade.setMixViability(i, true);
						
						}
						holdCost[i] = totalCost;
				}
				if (grade.getSize() - viabilityCheck == 0) {
					TagOption[] noMixes = new TagOption[1];
					TagOption badTag = new TagOption();
					badTag.setCost(0);
					noMixes[0] = badTag;
					return noMixes;
				}
				TagOption[] viableMixes = new TagOption[grade.getSize()-viabilityCheck];
				int removeNull = 0;
				for (int i = 0; i < grade.getSize(); i++) {
					if (grade.getMixViability(i) == true) {
						double powerCost = prodCosts.getMixPower() * grade.getTagList().get(i).getPower();
						double oxygenCost = prodCosts.getMixOxygen() * grade.getTagList().get(i).getOxygen();
						double timeCost = prodCosts.getMixTime() * 1440 * grade.getTagList().get(i).getTime();
						holdCost[i] = holdCost[i] + powerCost + oxygenCost + timeCost;
						grade.setMixCost(i, holdCost[i]);
						TagOption tagO = new TagOption();					
						tagO.setTagNum(grade.getTagList().get(i).getTagNum());
						tagO.setCost(holdCost[i]);
						tagO.setViability(true);
						tagO.setPower(powerCost);
						tagO.setOxygen(oxygenCost);
						tagO.setTime(timeCost);
						viableMixes[removeNull] = tagO;
						removeNull++;
					}
				}
				
				TagOption[] sortedMixes = viableMixes.clone();
				for (int i = 0; i < sortedMixes.length; i++) {
					for (int j = i+1; j < sortedMixes.length; j++) {
						if ( (sortedMixes[i].getCost() > sortedMixes[j].getCost()) && (i != j) ) {
							TagOption temp = sortedMixes[j];
							sortedMixes[j] = sortedMixes[i];
							sortedMixes[i] = temp;
						}
					}
				}
				return sortedMixes;
				/*for (int i = 0; i < grade.getSize(); i++) {
					double powerCost = prodCosts.getMixPower() * grade.getTagList().get(i).getPower();
					double oxygenCost = prodCosts.getMixOxygen() * grade.getTagList().get(i).getOxygen();
					double timeCost = prodCosts.getMixTime() * 1440 * grade.getTagList().get(i).getTime();
					holdCost[i] = holdCost[i] + powerCost + oxygenCost + timeCost;
					grade.setMixCost(i, holdCost[i]);

				}*/
			}

			/*
			 * Clone of usual inventory loading function
			 */
			public static void loadMixes() {
				try {
					searchInventory.clearInventory();
					market.clearMarket();
					File file = new File("files/mcode.txt");
					FileReader fileReader = new FileReader(file);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					StringBuffer stringBuffer = new StringBuffer();
					
					// Values stored in a raw material
					double matNum;  		
					String description;		
					String scrap;				
					String category;			
					double cost;					
					float usage;	
					double quantity;	
					double tolerance;

					
					// Read in inventory and market values
					String line;
					line = bufferedReader.readLine();
					stringBuffer.append(line);
					stringBuffer.append("\n");
					while ((line = bufferedReader.readLine()) != null) {
						stringBuffer.append(line);
						matNum = Integer.parseInt(line);
						stringBuffer.append("\n");
						line = bufferedReader.readLine();
						stringBuffer.append(line);
						description = line;
						stringBuffer.append("\n");
						line = bufferedReader.readLine();
						stringBuffer.append(line);
						scrap = line;
						stringBuffer.append("\n");
						line = bufferedReader.readLine();
						stringBuffer.append(line);
						category = line;
						stringBuffer.append("\n");
						line = bufferedReader.readLine();
						stringBuffer.append(line);
						cost = Double.parseDouble(line);
						stringBuffer.append("\n");
						line = bufferedReader.readLine();
						stringBuffer.append(line);
						quantity = Double.parseDouble(line);
						stringBuffer.append("\n");
						line = bufferedReader.readLine();
						stringBuffer.append(line);
						usage = Float.parseFloat(line);
						stringBuffer.append("\n");
						line = bufferedReader.readLine();
						stringBuffer.append(line);
						tolerance = Double.parseDouble(line);
						stringBuffer.append("\n");
						
						searchInventory.addInvItem(matNum, description, scrap, category, cost, quantity, usage, tolerance);
						//realInventory.addInvItem(matNum, description, scrap, category, cost, quantity, usage, tolerance);
						market.addItem(matNum, description, scrap, category, cost, tolerance);
					}
					fileReader.close();
					//Increment value to ensure inventory and market are only loaded in once
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/*
			 * Copy of the original runCosts
			 */
			public static void oldRunCosts(Grade testGrade, int testMix, String gradeFile, Inventory inventory, Market market) {
			Grade grade;
			grade = testGrade;
			int numOfHeats = testMix;
			Document doc = console.getText().getDocument();
			console.setVisible(true);
			
			TagMaterialList tagList = new TagMaterialList();
			ProdCost prodCosts = new ProdCost();

			int timesRan = 0;

			System.out.println("Number of mixes in grade: " + grade.getSize());
			try { 
			console.getText().getDocument().insertString(doc.getLength(), "\n\nNumber of mixes in grade: " + grade.getSize(), null);
			   } catch(BadLocationException exc) {
				      exc.printStackTrace();
				   }
				
			double[] holdCost = new double[grade.getSize()];
			double[] holdMix = new double[grade.getSize()];
			double[] holdCheapestMix = new double [numOfHeats];
			double[] holdExpensiveMix = new double [numOfHeats];
			double totalHeatCost = 0;
			
			// Run through total number of requested heats (iterations)
			for (int x = 0; x < numOfHeats; x++) {
				timesRan++;
				
				// Perform cost calculations for one mix at a time
				for (int i = 0; i < grade.getSize(); i++) {
					System.out.println("Mix: " + grade.getTagNum(i));
					try { 
						console.getText().getDocument().insertString(doc.getLength(), "\n\nMix: " + grade.getTagNum(i), null);
						   } catch(BadLocationException exc) {
							      exc.printStackTrace();
							   }

				
					double newMix = grade.getTagNum(i);
					holdMix[i] = grade.getTagNum(i);
			
					tagList = GetGradeInfo.getMatList(grade, newMix);
					tagList.display();
					System.out.println("\n");
					double totalCost = 0;
					
					// Perform cost calculations for one raw material in a mix at a time
					for (int y = 0; y < tagList.getSize(); y++) {

						System.out.println("MatNum: " + tagList.getMatNum(y) + "  Quantity: " + tagList.getMatQuantity(y));
						System.out.println("\n");
						try {
						console.getText().getDocument().insertString(doc.getLength(), "\n\nMatNum: " + tagList.getMatNum(y) + "  Quantity: " + tagList.getMatQuantity(y), null);
					   } catch(BadLocationException exc) {
						      exc.printStackTrace();
						   }
						double mixNum = tagList.getMatNum(y);
						double mixQuant = tagList.getMatQuantity(y);
					
						double testCost = inventory.obtainMaterial(mixNum, mixQuant, market);
						System.out.println("Cost per 1000 lbs: $" + testCost);
						try {
						console.getText().getDocument().insertString(doc.getLength(), "\nCost per 1000 lbs: $" + testCost, null);
					   } catch(BadLocationException exc) {
						      exc.printStackTrace();
						   }
			
						double testQuant = mixQuant / 1000;
						System.out.println("Cost for material: $" + testCost * testQuant);
						try {
						console.getText().getDocument().insertString(doc.getLength(), "\nCost for material: $" + testCost * testQuant, null);
					   } catch(BadLocationException exc) {
						      exc.printStackTrace();
						   }
						totalCost += testCost * testQuant;
						}
					System.out.println("\nTotal cost: $" + totalCost);
					try {
					console.getText().getDocument().insertString(doc.getLength(), "\nTotal cost: $" + totalCost + "\n", null);
				   } catch(BadLocationException exc) {
					      exc.printStackTrace();
					   }
					holdCost[i] = totalCost;
					}
			
				System.out.println("\n");
				
				// Display total cost of one mix
				for (int i = 0; i < grade.getSize(); i++) {
					double powerCost = prodCosts.getMixPower() * grade.getTagList().get(i).getPower();
					double oxygenCost = prodCosts.getMixOxygen() * grade.getTagList().get(i).getOxygen();
					double timeCost = prodCosts.getMixTime() * 1440 * grade.getTagList().get(i).getTime();
					System.out.println("\nPower cost: $" + powerCost);
					System.out.println("Oxygen cost: $" + oxygenCost);
					System.out.println("Time cost: $" + timeCost);
					try {
						console.getText().getDocument().insertString(doc.getLength(), "\n\nPower cost: $" + powerCost + "\nOxygen cost: $" + oxygenCost + "\nFurnace cost: $" + timeCost, null);
					   } catch(BadLocationException exc) {
						      exc.printStackTrace();
						   }
					holdCost[i] = holdCost[i] + powerCost + oxygenCost + timeCost;
					grade.setMixCost(i, holdCost[i]);
					System.out.println("\n\nTotal cost for mix " + holdMix[i] + ": $" + holdCost[i]);
					try {
						console.getText().getDocument().insertString(doc.getLength(), "\n\nTotal cost for mix " + holdMix[i] + ": $" + holdCost[i], null);
					   } catch(BadLocationException exc) {
						      exc.printStackTrace();
						   }
					}

			
				System.out.println("\n");
				
				// Calculate the cheapest and most expensive mixes for the given grade
				double min = holdCost[0];
				double max = holdCost[0];
				double cheapestMix = holdMix[0];
				double mostExpensiveMix = holdMix[0];
				for (int i = 0; i < holdCost.length; i++) {
					if (holdCost[i] < min) {
						min = holdCost[i];
						cheapestMix = holdMix[i];
					}
					if (holdCost[i] > max) {
						max = holdCost[i];
						mostExpensiveMix = holdMix[i];
					}
				}
			
				System.out.println("The cheapest mix in this grade is " + cheapestMix);
				try {
					console.getText().getDocument().insertString(doc.getLength(), "\n\nThe cheapest mix in this grade is " + cheapestMix, null);
				   } catch(BadLocationException exc) {
					      exc.printStackTrace();
					   }
				System.out.println("The most expensive mix in this grade is " + mostExpensiveMix);
				try {
					console.getText().getDocument().insertString(doc.getLength(), "\nThe most expensive mix in this grade is " + mostExpensiveMix, null);
				   } catch(BadLocationException exc) {
					      exc.printStackTrace();
					   }
				holdCheapestMix[x] = cheapestMix;
				holdExpensiveMix[x] = mostExpensiveMix;
				totalHeatCost = totalHeatCost + min;
				}
			
			// Display the results for each heat (iteration), including min, average, and max cost
			System.out.println("Times ran: " + timesRan);
			try {
				console.getText().getDocument().insertString(doc.getLength(), "\nTimes ran: " + timesRan, null);
			   } catch(BadLocationException exc) {
				      exc.printStackTrace();
				   }
			for (int z = 0; z < numOfHeats; z++) {
				System.out.println("Cheapest mix for iteration " + (z + 1) + " is " + holdCheapestMix[z]);
				try {
					console.getText().getDocument().insertString(doc.getLength(), "\n\nCheapest mix for iteration " + (z + 1) + " is " + holdCheapestMix[z], null);
				   } catch(BadLocationException exc) {
					      exc.printStackTrace();
					   }
			}
			
			for (int z = 0; z < numOfHeats; z++) {
				System.out.println("Most expensive mix for iteration " + (z + 1) + " is " + holdExpensiveMix[z]);
				try {
					console.getText().getDocument().insertString(doc.getLength(), "\n\nMost expensive mix for iteration " + (z + 1) + " is " + holdExpensiveMix[z], null);
				   } catch(BadLocationException exc) {
					      exc.printStackTrace();
					   }
			}
			
			System.out.println("The total cost for the requested number of heats for this grade is $" + totalHeatCost);
			try {
				console.getText().getDocument().insertString(doc.getLength(), "\nThe total cost for the requested number of heats for this grade is $" + totalHeatCost, null);
			   } catch(BadLocationException exc) {
				      exc.printStackTrace();
				   }
			System.out.println("The average cost for the requested number of heats for this grade is $" + (totalHeatCost / numOfHeats + "\n"));
			try {
				console.getText().getDocument().insertString(doc.getLength(), "\nThe average cost for the requested number of heats for this grade is $" + (totalHeatCost / numOfHeats + "\n"), null);
			   } catch(BadLocationException exc) {
				      exc.printStackTrace();
				   }
			
			//Send a grade's ID, its number of heats, and its cost to the final display function
			RecipeDisplay.updateTotal(gradeFile, totalHeatCost, numOfHeats);

			}
			
			
			
			
			
			
	
}
