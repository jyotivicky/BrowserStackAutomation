package com.TestCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.BaseClass.TestBase;
import com.Pages.LoginPage;
import com.Pages.ProductDetailsPage;
import com.Pages.ProductPage;
import com.Pages.SettingPage;
import com.Report.TestReport;
import com.aventstack.extentreports.Status;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;
import org.json.JSONTokener;

    public class ProductInformationTest extends TestBase {
	LoginPage loginPage;
	ProductPage productPage;
	JSONObject loginUsers;
	SettingPage settingPage;
	ProductDetailsPage detailsPage;

	@BeforeClass
	public void beforeClass() throws IOException {
		InputStream datais = null;
		try {
			String dataFileName = "TestData/TestData.json";
			datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
			JSONTokener tokener = new JSONTokener(datais);
			loginUsers = new JSONObject(tokener);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (datais != null) {
				datais.close();
			}
		}
		closeApp();
		launchApp();
	}

	@BeforeMethod
	public void beforeMethod() {
		loginPage = new LoginPage();
		productPage = new ProductPage();
		loginPage.userLogin(loginUsers.getJSONObject("validUser").getString("username"),loginUsers.getJSONObject("validUser").getString("password"));	
	}

	@AfterMethod
	public void afterMethod() {	
		settingPage = productPage.pressSettingsBtn();
		loginPage = settingPage.pressLogOutBtn();
	}

	@Test(priority = 1,groups = "regression")
	public void validateProductsOnProductPage() throws Exception {
		SoftAssert sft = new SoftAssert();
		String expectedBagTitle = productPage.getBagTitle();
		sft.assertEquals(expectedBagTitle, getStrings().get("products_page_Bag_title"));	
		TestReport.getTest().log(Status.INFO, getStrings().get("products_page_Bag_title_status"));	
		String expectedBagPrice = productPage.getBagPrice();
		sft.assertEquals(expectedBagPrice, getStrings().get("products_page_Bag_price"));
		TestReport.getTest().log(Status.INFO, getStrings().get("products_page_Bag_price_status"));
		sft.assertAll();
	}
	
	@Test(priority = 2,groups = "regression")
	public void validateProductsOnProductDetailsPage() throws Exception {
		SoftAssert sft = new SoftAssert();
		detailsPage = productPage.pressBagTitle();		
		String expectedBagTitle = detailsPage.getBagTitle();
		sft.assertEquals(expectedBagTitle, getStrings().get("products_page_Bag_title"));
		TestReport.getTest().log(Status.INFO, getStrings().get("products_details_page_Bag_title_status_msg"));
		detailsPage.scrollToBagPrice();
		String expectedBagDesc = detailsPage.getBagDescription();
		sft.assertEquals(expectedBagDesc, getStrings().get("product_details_page_Bag_description"));
		TestReport.getTest().log(Status.INFO, getStrings().get("product_details_page_Bag_description_status_msg"));
		String expectedBagPrice = detailsPage.getBagPrice();
		sft.assertEquals(expectedBagPrice, getStrings().get("product_details_page_Bag_price"));
		TestReport.getTest().log(Status.INFO, getStrings().get("product_details_page_Bag_price_status_msg"));
		productPage = detailsPage.pressBackToProductButton();
		sft.assertAll();
	}
  }

    
    
    
    
    
    
    
    
    
    
    
    
    