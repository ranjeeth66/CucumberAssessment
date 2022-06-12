package supportLibraries;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Utility extends BaseClass{

	public static int PAGE_LOAD_TIMEOUT = 30;
	public static int IMPLICIT_WAIT = 20;

	public static String getPropertiesFromConfig(String stKey) {
		String configData = null;
		try {
			File file = new File(System.getProperty("user.dir") + File.separator +"src" + File.separator +"test" + File.separator + "resources" + File.separator + "Settings.properties");
			FileInputStream fis = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fis);
			configData = properties.getProperty(stKey);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		return configData;
	}

	public static boolean verifyElementExists(WebElement element) {
		boolean isResultDispayed = false;
		try {
			isResultDispayed = element.isDisplayed();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return isResultDispayed;
	}

	public static void clickOnElement(WebElement lnk_FirstNewsPost) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", lnk_FirstNewsPost);
//			lnk_FirstNewsPost.click();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	public static String retrieveGetText(WebElement lnk_NewsHeader) {
		String strFirstNewsPost = null;
		try {
			strFirstNewsPost = lnk_NewsHeader.getText();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return strFirstNewsPost;
	}
}
