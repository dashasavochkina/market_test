package utils;

import org.openqa.selenium.WebDriver;

public abstract class AbstractTestClass {
    protected WebDriver driver = DriverManager.getInstance();
}
