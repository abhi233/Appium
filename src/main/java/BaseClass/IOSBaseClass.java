package BaseClass;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pageObjects.ios.HomePage;
import utils.AppiumUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class IOSBaseClass  {

	public IOSDriver driver;
	public AppiumDriverLocalService service;
	public HomePage homePage;
	
	@BeforeClass
	public void ConfigureAppium() throws IOException
	{
		
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"src/test/resources/data.properties");
				
		prop.load(fis);
		String ipAddress = prop.getProperty("ipAddress");
		String port = prop.getProperty("port");
			
		service = AppiumUtils.startAppiumServer(ipAddress,Integer.parseInt(port));
			
				XCUITestOptions	 options = new XCUITestOptions();	
				options.setDeviceName("iPhone 13 Pro");
				options.setApp(System.getProperty("user.dir")+"/src/main/resources/UIKitCatalog.app");
				options.setPlatformVersion("15.5");
				//Appium- Webdriver Agent -> IOS Apps.
				options.setWdaLaunchTimeout(Duration.ofSeconds(20));
				
			 driver = new IOSDriver(service.getUrl(), options);
			 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			 homePage = new HomePage(driver);
			 
	}
	
	
	

	
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
        service.stop();
		}
	
}
