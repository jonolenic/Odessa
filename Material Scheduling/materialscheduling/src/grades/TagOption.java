package grades;

import java.util.LinkedList;

import org.apache.poi.util.SystemOutLogger;


//List that is under the Grade Class
//Contains TagMaterialLists
//can add Tag Materials Lists to this list
public class TagOption implements Comparable<TagOption> {
	
	private double tagNum;
	private double power;
	private double oxygen;
	private double tapWgt;
	private double time;
	private double cost;
	private boolean viability;
	private LinkedList<TagMaterialList> tagMaterialList;
		
	
	public TagOption(){
		this.tagMaterialList = new LinkedList<TagMaterialList>();
	}
	
	
	public void addItem(TagMaterialList gradeMaterialList) {
		tagMaterialList.addLast(gradeMaterialList);		
	}
	
	
	
	public void display() {
		
		System.out.println("-Grade Option");
		System.out.println("Tag: " + getTagNum() + ", Power: " + getPower() + ", Oxygen: " + getOxygen() + 
							"Tap Weight: " + getTapWgt() + ", Time: " + getTime()	);
		try {
			for(Object o: tagMaterialList){
				
				((TagMaterialList) o).display();
							}
		} catch (Exception e) {
			System.out.println("empty list");
		}
		
		
	}


	public double getTagNum() {
		return tagNum;
	}


	public void setTagNum(double tagNum) {
		this.tagNum = tagNum;
	}


	public double getPower() {
		return power;
	}


	public void setPower(double power) {
		this.power = power;
	}


	public double getOxygen() {
		return oxygen;
	}


	public void setOxygen(double oxygen) {
		this.oxygen = oxygen;
	}


	public double getTapWgt() {
		return tapWgt;
	}


	public void setTapWgt(double tapWgt) {
		this.tapWgt = tapWgt;
	}


	public double getTime() {
		return time;
	}


	public void setTime(double time) {
		this.time = time;
	}


	public LinkedList<TagMaterialList> getTagOption() {
		return tagMaterialList;
	}


	public void setTagOption(LinkedList<TagMaterialList> tagOption) {
		this.tagMaterialList = tagOption;
	}


	public boolean getViability() {
		return viability;
	}


	public void setViability(boolean viability) {
		this.viability = viability;
	}


	public double getCost() {
		return cost;
	}


	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public int compareTo(TagOption e) {
		return (int) (this.cost - e.cost);
	}
	
	
		
}
