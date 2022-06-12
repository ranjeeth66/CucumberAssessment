package supportLibraries;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BaseClass{
	
	public static WebDriver driver;	
	public static String browser = Utility.getPropertiesFromConfig("Browser");
	public static String chromePath = Utility.getPropertiesFromConfig("chromepath");
	public static String iePath = Utility.getPropertiesFromConfig("internetExplorer");
	public static WebDriver getDriver() {
		switch(browser){
		case "Chrome":
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+chromePath);
			driver = new ChromeDriver() ;
			break;
		case "IE":
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+iePath);
			driver = new InternetExplorerDriver();
			break;
		case "Firefox":
			driver = new FirefoxDriver() ;
			break;
		}
		return driver;
	}
	
}
