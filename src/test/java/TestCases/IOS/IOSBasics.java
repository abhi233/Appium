package TestCases.IOS;

import BaseClass.IOSBaseClass;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import pageObjects.ios.AlertViews;

public class IOSBasics extends IOSBaseClass {

	
	@Test
	public void IOSBasicsTest()
	{
		//Xpath, classname, IOS, iosClassCHain, IOSPredicateString, accessibility id, id
		
		AlertViews alertViews = homePage.selectAlertViews();
		alertViews.fillTextLabel("hello");
		String actualMessage = alertViews.getConfirmMessage();
		AssertJUnit.assertEquals(actualMessage, "A message should be a short, complete sentence.");

		
		
		
		
		
		
		
	}
}
