package TestCases.Android;


import BaseClass.AndroidBaseClass;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import pageObjects.android.CartPage;
import pageObjects.android.FormPage;
import pageObjects.android.ProductCatalogue;

import static utils.AppiumUtils.getJsonData;

public class TestCase2 extends AndroidBaseClass {

	//emulator -avd Pixel
	@Test(dataProvider="getData", groups= {"Smoke"})
	public void FillForm(HashMap<String,String> input) throws InterruptedException
	{
		FormPage formPage=new FormPage(driver);
		formPage.setNameField(input.get("name"));
		formPage.setGender(input.get("gender"));
		formPage.setCountrySelection(input.get("country"));
		ProductCatalogue productCatalogue = formPage.submitForm();
		productCatalogue.addItemToCartByIndex(0);
		productCatalogue.addItemToCartByIndex(0);
		CartPage cartPage = productCatalogue.goToCartPage();
		double totalSum = cartPage.getProductsSum();
		double displayFormattedSum = cartPage.getTotalAmountDisplayed();
		AssertJUnit.assertEquals(totalSum, displayFormattedSum);
		cartPage.acceptTermsConditions();
		cartPage.submitOrder();
	}

	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		List<HashMap<String, String>>	data =getJsonData(System.getProperty("user.dir")+"/src/test/java/testData/eCommerce.json");
		return new Object[][] { {data.get(0)},{data.get(1)}  };
	}

	
}
