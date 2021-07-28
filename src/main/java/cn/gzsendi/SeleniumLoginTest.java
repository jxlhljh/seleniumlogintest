package cn.gzsendi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeleniumLoginTest {
	
	private static final Logger logger = LoggerFactory.getLogger(SeleniumLoginTest.class);
	
	public static void main(String[] args) {
		
        WebDriver webDriver = null;
        
		try {
			// 设置 chromedirver 的存放位置
			System.getProperties().setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
			ChromeOptions chromeOptions = new ChromeOptions();
			
	        chromeOptions.addArguments("--no-sandbox");
	        chromeOptions.addArguments("--disable-dev-shm-usage");
	        chromeOptions.addArguments("blink-settings=imagesEnabled=false");
	        chromeOptions.addArguments("--disable-gpu");
	        
	        //使用后台打开chrome的方式
	        chromeOptions.addArguments("--headless");
			
			webDriver = new ChromeDriver(chromeOptions);
			
			//1.模拟打开登陆页面
			String url = "http://192.168.56.101:5000/#/login";
			logger.info("open login page,url is {}",url);
			webDriver.get(url);
			
			//2.等3秒钟响应后再操作，不然内容可能还没有返回
			Thread.sleep(3000l);
			logger.info("login page title is : {}", webDriver.getTitle());
			
			//3.找到账号的输入框，并模拟输入账号
			WebElement accountInput = webDriver.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/form/div[1]/div/div[1]/input"));
			accountInput.sendKeys("admin");
			logger.info("now sendKeys to accountInput");
			Thread.sleep(1000l);
			
			//4.找到密码的输入框，并模拟输入密码
			WebElement passwordInput = webDriver.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/form/div[2]/div/div[1]/input"));
			passwordInput.sendKeys("Sd123456");
			logger.info("now sendKeys to passwordInput");
			Thread.sleep(1000l);
			
			//5.找到验证码的输入框，并模拟验证码
			WebElement codeInput = webDriver.findElement(By.xpath("/html/body/div/div/div[2]/div[3]/form/div[3]/div/div[1]/div/input"));
			codeInput.sendKeys("8888");
			logger.info("now sendKeys to checkCodeInput");
			Thread.sleep(1000l);
			
			//6.找到登陆的按钮，并模拟点击登陆
			WebElement loginButton = webDriver.findElement(By.xpath("/html/body/div/div/div[2]/div[4]/button"));
			loginButton.click();
			logger.info("now click the loginButton");
			Thread.sleep(3000l);
			
			//7.登陆后，通过localStorage获取token信息
	        WebStorage webStorage = (WebStorage) new Augmenter().augment(webDriver);
	        LocalStorage localStorage = webStorage.getLocalStorage();
	        logger.info("login success, get login result , token is :  " + localStorage.getItem("token"));
	        
		} catch (Exception e) {
			logger.error("",e);
			
		} finally {
			
			if(webDriver != null) webDriver.close();
			
		}
		
	}

}
