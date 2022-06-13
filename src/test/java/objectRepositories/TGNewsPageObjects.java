package objectRepositories;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import supportLibraries.BaseClass;
import supportLibraries.Log4jProperties;
import supportLibraries.Utility;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class TGNewsPageObjects{
	public WebDriver driver;
	public String strFirstNewsPost=null;
	public static Logger logger = Logger.getLogger(Log4jProperties.class.getName());

	public TGNewsPageObjects(){
		driver= BaseClass.getDriver();
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[contains(@class,'inline-the-guardian-logo')]")
	private WebElement ele_GuardianLogo;

	@FindBy(xpath = "//button[contains(text(),'Yes')]")
	private WebElement btn_Cookies;
	
	@FindBy(xpath = "(//li[contains(@class,'lice__item l-row')]//h3/a)[1]")
	public WebElement lnk_FirstNewsPost;

	@FindBy(xpath = "//iframe[@title='Iframe title']")
	private WebElement ele_iframe;

	@FindBy(xpath = "//input[@name='q']")
	private WebElement edtSearchLoct;

	@FindBy(xpath = "//div[@data-gu-name='headline']//h1")
	public WebElement lnk_NewsHeader;

	@FindBy(xpath = "//div[@id ='rso']/div/following::div[contains(@class,'tF2Cxc')]")
	private List<WebElement> searchResultsData;

	By verify_SearchResults = By.xpath("//div[@id='search']//a");

	/********This Method is used to launch browser with url  ****/
	public void openBrowser(){
		String url = Utility.getPropertiesFromConfig("ApplicationUrl");
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Utility.IMPLICIT_WAIT, TimeUnit.SECONDS);
	}

	/********This Method is used to get the page title
	 * @return strPageTitle***/
	public String validateTGNewsPage(){
		return driver.getTitle();
	}

	/********This Method is used verify guardian new page is launched
	 * @return *****/
	public boolean newsPageValidation(){
		driver.switchTo().frame(ele_iframe);
		btn_Cookies.click();
		driver.switchTo().defaultContent();
		return Utility.verifyElementExists(ele_GuardianLogo);
	}
	/********This Method is used to retrieve data for the first post from news page
	 * @return strFirstNewsPost*****/
	public String retrieveFirstNewsData(){
		 Utility.clickOnElement(lnk_FirstNewsPost);
		 strFirstNewsPost = Utility.retrieveGetText(lnk_NewsHeader);
		 logger.info("first post news data is successfully retrieved");
		 return strFirstNewsPost;
	}
	/********This Method is used to launch google page and enter the retrieved search result & Captures the search result to list
	 * @return lstAllResults *****/
	public List<String> getDataFromGooglePage(){
		String url = Utility.getPropertiesFromConfig("GoogleUrl");
		driver.get(url);
		driver.manage().window().maximize();
		edtSearchLoct.sendKeys(strFirstNewsPost);
		edtSearchLoct.sendKeys(Keys.ENTER);
		WebDriverWait wait = new WebDriverWait(driver, Utility.PAGE_LOAD_TIMEOUT);
		wait.until(ExpectedConditions.presenceOfElementLocated(verify_SearchResults));
		List<WebElement> allLinks = driver.findElements(verify_SearchResults);
		List<String> lstAllResults = new ArrayList<>();
		for (WebElement webElement : searchResultsData) {
			lstAllResults.add(webElement.getText());
		}
		logger.info("Results Data:"+ lstAllResults);
		return lstAllResults;
	}
	/*******This method will return true if at least one result exist in Google search results
	* @return testResult ******/
	public boolean verifyIfArticleIsPresent(List<String> searchResultsData, String firstArticleHeadline) {
		boolean testResult = false;
		int count = 0;
		String[] strLstData = firstArticleHeadline.split("\\W+");
		count = getCount(searchResultsData, strLstData, count);
		if (count != 0) testResult = true;
		return testResult;
	}
	/*******This has all the results from search engine as first parameter and retrieved text from news page as second parameter.
	 This method returns true if the news is valid and false if the news is not valid
	 * @return testResult ******/
	public boolean validateNewsIsValid(List<String> searchResultsData, String strFNP) {
		String[] strWordsFromArticle = strFNP.split("\\W+");
		int count = 0;
		boolean compareResult = false;
		count = getCount(searchResultsData, strWordsFromArticle, count);
		if (count == 0){
			compareResult = false;
		}else if (count > 0) {
			count = count / searchResultsData.size();
			if (count >= 5){
				compareResult = true;
			}
		}
		return compareResult;
	}
	/*******This method will return true if at least one result exist in Google search results
	 * @return testResult ******/
	private int getCount(List<String> allSearchResults, String[] strWordsFromArticle, int count) {
		for (String allSearchResult : allSearchResults) {
			for (String s : strWordsFromArticle) {
				if (allSearchResult.contains(s)){
					count++;
				}
			}
		}
		return count;
	}
}