package tests;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

import calculations.CostCalculations;
import calculations.RecipeDisplay;
import grades.GetGradeInfo;
import grades.Grade;
import grades.TagMaterial;
import grades.TagMaterialList;
import grades.TagOption;
import inventory.Inventory;
import market.Market;


public class GradesTest {

	public static void main(String[] args) {
		
		/* 
		 * Reads in user values: The number of grades is entered first, then the program
		 * is run through that many times. A grade file and the number of heats from it are
		 * entered in. The grade file is instantiated as a new grade and this data is passed
		 * to the class that loads in the inventory/market and calculates the grade's cost.
		 */
		System.out.println("********************************");
		Scanner scanner = new Scanner (System.in);
		System.out.print("Enter number of grades: ");
		String numOfGrades = scanner.next();
		int runThroughGrades = Integer.parseInt(numOfGrades);
		
		
		for (int i = 0; i < runThroughGrades; i++) {
			System.out.print("Enter grade file name (five character string): ");
			String test = scanner.next();
			System.out.print("Enter number of heats: ");
			String tempTest = scanner.next();

			int mixTest = Integer.parseInt(tempTest);
	
			Grade grade = new Grade(test);

		
			try {
				grade.loadGrades(test);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			CostCalculations.chooseFunction(grade, mixTest, test);
		}
		scanner.close();
		/* 
		 * After all grades have been entered and had their costs calculated, they are sent to
		 * this class to display the results to the user
		 */
		RecipeDisplay.printResults(runThroughGrades);
	}
}
