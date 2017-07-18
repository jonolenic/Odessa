package tests;

import org.junit.Test;

import grades.GetGradeInfo;
import grades.Grade;
import grades.TagMaterial;
import grades.TagMaterialList;
import grades.TagOption;

import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.Before;

public class TestGrade {

	//Tests setting up a grade and retreiving mat lists with the GetGradeInfo class
	
	@Test
	public void testGrNum() {
		
		Grade grade = new Grade("12345");
		Assert.assertEquals(String.valueOf(grade.getGradeNum()), "12345");	
	}
	
	@Test
	public void testGr(){
		Grade grade = new Grade("12345");
		TagOption tag = new TagOption();
		tag.setTagNum(12);
		TagMaterialList matList = new TagMaterialList();
		TagMaterial tagMat = new TagMaterial();
		tagMat.setMatNum(2);
		matList.addItem(tagMat);
		tag.addItem(matList);
		grade.addOption(tag);
		TagMaterialList testTag = GetGradeInfo.getMatList(grade, 12);
		Assert.assertEquals(testTag, matList);
		
		
		
		
	}
	
	
	
}
