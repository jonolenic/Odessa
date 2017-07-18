package grades;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import inventory.InventoryMaterial;


//List that contains tag parameters and a RecipeOptionList

public class Grade {
	
	private String gradeNum;
	private int mix;
	private int cost;
	
	private LinkedList<TagOption> tagList;
	
	
	public Grade(String gradeNum){
		this.gradeNum = gradeNum;
		this.tagList = new LinkedList<TagOption>();
	}
	
	public void addOption(TagOption tagOption){
		
		tagList.addLast(tagOption);
		
	}
	
	
	
	
	public void display() {
		
		System.out.println("Grade: " + gradeNum);
		
		try {
			for(Object o: tagList){
				
				((TagOption) o).display();
							}
		} catch (Exception e) {
			System.out.println("empty list");
		}
		
	}
	
	
	public String getGradeNum() {
		return gradeNum;
	}

	public void setGradeNum(String gradeNum) {
		this.gradeNum = gradeNum;
	}

	public int getMix() {
		return mix;
	}

	public void setMix(int mix) {
		this.mix = mix;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public LinkedList<TagOption> getTagList() {
		return tagList;
	}

	public void setTagList(LinkedList<TagOption> tagList) {
		this.tagList = tagList;
	}
	
	
	public int getSize() {
		return tagList.size();
	}
	
	public double getTagNum(int num) {
		return tagList.get(num).getTagNum();
	}
	
	// Sets the viability (can be made/can't be made) of a given mix
	public void setMixViability(int num, boolean viability) {
		tagList.get(num).setViability(viability);
	}
	
	// Returns the viability of a given mix
	public boolean getMixViability(int num) {
		return tagList.get(num).getViability();
	}
	
	// Sets the cost of a given mix
	public void setMixCost(int num, double cost) {
		tagList.get(num).setCost(cost);
	}
	
	// Returns the cost of a given mix
	public double getMixCost(int num) {
		return tagList.get(num).getCost();
	}
	
	/*
	 * Loads in a selected grade and its mixes from the ExcelFiles folder
	 * Apache POI is able to read in each grade's mixes and their respective materials and costs
	 * This data is stored in a series of linked lists: each grade holds a number of mixes, which each hold a number of materials
	 */
	public void loadGrades(String gradeNum) throws ClassNotFoundException {
		
		try {		
			
    		FileInputStream inputStream = new FileInputStream(new File("ExcelFiles/" + gradeNum + "(DAT).xlsx"));
    		Workbook workbook = new XSSFWorkbook(inputStream);
    		Sheet sheet = (Sheet) workbook.getSheetAt(0);
    		Iterator iterator = sheet.iterator();
    		
    		TagMaterialList tagMatList = new TagMaterialList();
    		TagOption tagOption = new TagOption();
    		while (iterator.hasNext()) {
    			
    			
    			Row nextRow = (Row) iterator.next();
    			//Ignore first row with headings
    			if(nextRow.getRowNum()==0)
    			    continue;
    			
    			Iterator cellIterator2 = nextRow.cellIterator();
    			Cell cellVal = (Cell) cellIterator2.next();
    			Iterator cellIterator = nextRow.cellIterator();
    			
    			
    			
    			
    			
    			if(cellVal.getNumericCellValue()>1000) { 				
    				tagOption.addItem(tagMatList);
    				tagOption = new TagOption();
    				tagMatList = new TagMaterialList();
    				
	    			while (cellIterator.hasNext()) {
	    					    				   				
	    			    Cell cell = (Cell) cellIterator.next();
	    			    
	    			    int columnIndex=cell.getColumnIndex();   
	    			    
		    			    switch (columnIndex+1) {    			    			    
			    			    case 1:
			    			    	tagOption.setTagNum(cell.getNumericCellValue());
			    			    	break;
			    			    case 2:
			    			    	break;
			    			    case 3:
			    			    	tagOption.setPower(cell.getNumericCellValue());
			    			    	break;
			    			    case 4:
			    			    	tagOption.setOxygen(cell.getNumericCellValue());
			    			    	break;
			    			    case 5:
			    			    	tagOption.setTapWgt(cell.getNumericCellValue());
			    			    	break;
			    			    case 6:
			    			    	tagOption.setTime(cell.getNumericCellValue());
			    			    	break;
			    					    
		    			}
	    			      			      
	    			}
	    			tagList.add(tagOption);	    			
    			}
    			
    			if(cellVal.getNumericCellValue() < 1000){
    				TagMaterial tagMat = new TagMaterial();
    				
    				while (cellIterator.hasNext()) {
		   				
	    			    Cell cell = (Cell) cellIterator.next();
	    			    
	    			    int columnIndex=cell.getColumnIndex();   
	    			    
		    			    switch (columnIndex+1) {    			    			    
			    			    case 1:
			    			    	tagMat.setMatNum(cell.getNumericCellValue());
			    			    	break;
			    			    case 2:
			    			    	tagMat.setQuantity(cell.getNumericCellValue());
			    			    	break;
		    			    }			    			    					    
	    			      			      
	    			}
    				if(tagMat.getQuantity() > 0){
    					tagMatList.addItem(tagMat);
    				}	
    			}
    		
    			
    		
    		} 
    		tagOption.addItem(tagMatList);
    		workbook.close();
    		inputStream.close();
    		 
    		 
    	}
		catch(IOException e) {
			
    		e.printStackTrace();
    		return;
    	}
		
	}
	
	
	
	
	
	
}