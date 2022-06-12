package objectRepositories;

import org.apache.log4j.Logger;
import org.junit.Assert;
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

	By lst_SearchValues= By.xpath("(//div[@id='search']//a/../../..//*[contains(@style,'webkit-line')]//span[2])[1]/em");
	By lst_SearchValues1= By.xpath("(//div[@id='search']//a/../../..//*[contains(@style,'webkit-line')]//span[2])[2]/em");

	By verify_SearchResults = By.xpath("//div[@id='search']//a");

	/********This Method is used to launch browser with url  ****/
	public void openBrowser() throws Exception{
		String url = Utility.getPropertiesFromConfig("ApplicationUrl");
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Utility.IMPLICIT_WAIT, TimeUnit.SECONDS);
	}

	/********This Method is used to get the page title
	 * @return strPageTitle***/
	public String validateTGNewsPage() throws InterruptedException {
		String strPageTitle = driver.getTitle();
		return strPageTitle;
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
	public String retrieveFirstNewsData() throws InterruptedException{
		 Thread.sleep(3000);
		 Utility.clickOnElement(lnk_FirstNewsPost);
		 strFirstNewsPost = Utility.retrieveGetText(lnk_NewsHeader);
		 logger.info("first post news data is successfully retrieved");
		 return strFirstNewsPost;
	}
	/********This Method is used to launch google page and enter the retrieved search result
	 * @return *****/
	public List<WebElement> getDataFromGooglePage() throws InterruptedException {
		String url = Utility.getPropertiesFromConfig("GoogleUrl");
		driver.get(url);
		driver.manage().window().maximize();
		edtSearchLoct.sendKeys(strFirstNewsPost);
		edtSearchLoct.sendKeys(Keys.ENTER);
		WebDriverWait wait = new WebDriverWait(driver, Utility.PAGE_LOAD_TIMEOUT);
		wait.until(ExpectedConditions.presenceOfElementLocated(verify_SearchResults));
		List<WebElement> allLinks = driver.findElements(verify_SearchResults);
		logger.info("All links found on web page are: " + allLinks.size() + " links");
		for (WebElement link : allLinks) {
			logger.info(link.getAttribute("href"));
			logger.info(link.getText());
		}
		return allLinks;
	}
	/********This Method is used to verify the retrieved search result is valid or not
	 * @return *****/
	public void validateNewsIsValid(List<WebElement> allLinks) {
		String[] strLst = strFirstNewsPost.split(":");
		for(int i=0;i<allLinks.size()-1;i++){
			if(!allLinks.get(i).getText().contains("https://www.theguardian.com")){
				String strActRes = allLinks.get(i).getText();
				if(strActRes.contains(strLst[0])){
					Assert.assertTrue("Data is valid",true);
					break;
				}else{
					Assert.assertFalse("Data is InValid", false);
				}
			}
		}
	}
}
