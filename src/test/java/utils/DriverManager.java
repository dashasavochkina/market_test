package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class DriverManager {
    private static final int IMPLICITLY_WAIT_TIMEOUT = 5;

    static WebDriver driver;

    public static WebDriver getInstance(){
        if (driver != null){
            return driver;
        }
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_TIMEOUT, TimeUnit.SECONDS);
        return driver;
    }

    public static void destroyInstance() {
        driver = null;
    }
}
