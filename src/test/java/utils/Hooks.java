package utils;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Hooks {
    WebDriver driver;

    @Before(value = "@gui")
    public void configureWebdriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        System.out.println("configureWebdriver");
    }
}
