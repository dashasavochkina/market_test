package utils;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Hooks extends AbstractTestClass {
    @After
    public void closeBrowser() {
        driver.close();
    }

    @After
    public void destroyDriver() {
        DriverManager.destroyInstance();
    }
}
