package material;


//Material class
// - provides material number to classes that inherit off of it
// - add other fields as required in implementation
// - must implement display() function to display all elements of inheriting classes.
public abstract class Material {	
	
	private double matNum;  		//material Number
	private String description;		// Material name
	private String scrap;				// On character, Y or N
	private String category;			// Generally "scrap" or "alloy"
	private double cost;					// Cost per 1000 pounds
	private float usage;				// Maximum percentage of inventory that can be used
	private double tolerance;           // Buffer for tolerance of material
	
	


	public Material() {
		
	}	

	
	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public double getMatNum() {
		return matNum;
	}

	public void setMatNum(double matNum) {
		this.matNum = matNum;
	}
	
	
	public abstract boolean display();
	
	//overrides java's to string to display all elements stated in display of an inheriting material class
	@Override
	public String toString(){
		
		display();
		return null;		
		
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getScrap() {
		return scrap;
	}


	public void setScrap(String scrap) {
		this.scrap = scrap;
	}


	public double getCost() {
		return cost;
	}


	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getTolerance() {
		return tolerance;
	}


	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}


	
}
