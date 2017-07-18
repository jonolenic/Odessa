package processing;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;


public class FileControl {

	public void loadList(String filename, Object listName) {		

		try {

			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			listName = (LinkedList<?>) in.readObject();
			in.close();
			fileIn.close();

			/*
	    		System.out.println();
	    	 	InventoryList.printForwardLinkedList();
			 */
		}		
		catch(IOException e) {

			e.printStackTrace();
			return;
		}		
		catch(ClassNotFoundException c) {

			System.out.println("class not found");
			c.printStackTrace();
			return;
		}

	}

	//Accepts a list name to save to a filename with the .dat extension
	public void saveList(Object listName, String filename){

		try {

			FileOutputStream fileOut = new FileOutputStream(filename + ".dat" );
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(listName);
			out.close();
			fileOut.close();
			//System.out.printf("data saved");

		}
		catch(IOException i) {

			i.printStackTrace();

		}	

	}
		
}
