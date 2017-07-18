package grades;

import java.lang.reflect.Array;
import java.util.LinkedList;

public class GetGradeInfo {
	
	//function to get the material list from a specified tagOption
	public static TagMaterialList getMatList(Grade grade, double tagNum){
		LinkedList<TagMaterialList> tagMatList = null;
		TagMaterialList tagMatList2 = new TagMaterialList();
		
		//get the list of tagOptions from grade
		LinkedList<TagOption> tagOptions = grade.getTagList();		
		
		//Iterate through the tag options in tagOptions
		for(TagOption o: tagOptions){
			//get the tagMaterialList from the Option
			if(o.getTagNum() == tagNum){
			
				tagMatList = o.getTagOption();
				tagMatList2 = tagMatList.getLast();
			}
			
		}		
		
		return tagMatList2;
		
		
		
	}


	
	
}
