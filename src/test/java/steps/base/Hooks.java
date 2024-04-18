package steps.base;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.DriverManager;

public class Hooks {
    @Before(value="@gui")
    public void before() {
        DriverManager.getInstance();
    }

    @After(value="@gui")
    public void after() {
        DriverManager.getInstance().close();
        DriverManager.destroyInstance();
    }
}
