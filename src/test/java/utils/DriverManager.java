package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class DriverManager {
    private static final int IMPLICITLY_WAIT_TIMEOUT = 5;

    static WebDriver driver;

    public static WebDriver getInstance(){
        if (driver != null){
            return driver;
        }
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdrivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_TIMEOUT));
        return driver;
    }

    public static void destroyInstance() {
        driver = null;
    }
}
