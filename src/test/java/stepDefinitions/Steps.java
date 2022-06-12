package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.junit.Assert;
import objectRepositories.TGNewsPageObjects;
import org.openqa.selenium.WebElement;
import supportLibraries.BaseClass;
import supportLibraries.Log4jProperties;

import java.util.List;

public class Steps extends BaseClass{
	
	TGNewsPageObjects tgnPage = new TGNewsPageObjects();
	public String strFNP = null;
	public List<WebElement> searchResultsData;
	public static Logger logger = Logger.getLogger(Log4jProperties.class.getName());

	@Before
	public void setup() {
		logger.info("Starting execution....");

	}
	@Given("^user launches browser$")
	public void user_launches_browser() throws Throwable {
		tgnPage.openBrowser();
		logger.info("Browser launched successfully");
	}

	@Then("navigates to news portal and verifies the {string}page title")
	public void navigatesToNewsPortalAndVerifiesThePageTitle(String title) throws InterruptedException {
		String strTitle = tgnPage.validateTGNewsPage();
		Assert.assertEquals(strTitle, title);
		boolean strResult = tgnPage.newsPageValidation();
	  	Assert.assertTrue(strResult);
		logger.info("Validated page title & page is successfully");
	}

	@When("user retrieves the first story from the news portal")
	public void user_retrieves_the_first_story_from_the_news_portal() throws InterruptedException {
		strFNP= tgnPage.retrieveFirstNewsData();
	}
	@Then("user navigates to the google page and enters the retrieved text")
	public void user_navigates_to_the_google_page_and_enters_the_retrieved_text() throws Exception {
		searchResultsData = tgnPage.getDataFromGooglePage();
		Thread.sleep(2000);

	}

	@Then("verifies the news is valid or not")
	public void verifies_the_news_is_valid_or_not() {
		tgnPage.validateNewsIsValid(searchResultsData);
	}
	@After
	public void tearDown(){
		driver.quit();
		logger.info("Browser closed successfully");
	}
}