package com.TestCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.BaseClass.TestBase;
import com.Pages.LoginPage;
import com.Pages.ProductPage;
import com.Report.TestReport;
import com.aventstack.extentreports.Status;
import com.utility.TestUtility;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;
import org.json.JSONTokener;

    public class LoginTest extends TestBase {
	LoginPage loginPage;
	ProductPage productPage;
	JSONObject loginUsers;

	@BeforeClass
	public void jsonDataInitialaization() throws IOException {
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
	public void Initialaization() {
		loginPage = new LoginPage();
		productPage = new ProductPage();
	}

	@Test(priority = 1, groups = "smoke")
	public void invalidUsernameTest() throws Exception {
		SoftAssert sft = new SoftAssert();	
		loginPage.enterUsername(loginUsers.getJSONObject("invalidusername").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("invalidusername").getString("password"));
		loginPage.clickLoginButton();
		String ActualMessage = loginPage.getErrorTxt();
		sft.assertEquals(ActualMessage, getStrings().get("err_invalid_username_or_password"));
		TestReport.getTest().log(Status.INFO, getStrings().get("invalid_credential_status_msg"));
		sft.assertAll();
	}

	@Test(priority = 2, groups = "smoke")
	public void invalidPasswordTest() {
		SoftAssert sft = new SoftAssert();
		loginPage.enterUsername(loginUsers.getJSONObject("invalidPassword").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
		loginPage.clickLoginButton();
		String ActualMessage = loginPage.getErrorTxt();
		sft.assertEquals(ActualMessage, getStrings().get("err_invalid_username_or_password"));
		TestReport.getTest().log(Status.INFO, getStrings().get("invalid_credential_status_msg"));
		sft.assertAll();
	}

	@Test(priority = 3,groups = "smoke")
	public void validUsernameTest() throws InterruptedException {
		SoftAssert sft = new SoftAssert();
		loginPage.enterUsername(loginUsers.getJSONObject("validUser").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
		loginPage.clickLoginButton();
		String actualPageTitle = productPage.getPageTitle();
		sft.assertEquals(actualPageTitle, getStrings().get("page_title"));
		TestReport.getTest().log(Status.INFO, getStrings().get("Valid_credential_status_msg"));
		sft.assertAll();
	}

}
