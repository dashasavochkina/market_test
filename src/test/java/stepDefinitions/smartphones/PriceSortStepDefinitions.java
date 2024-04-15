package stepDefinitions.smartphones;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObject.SmartphonesPage;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PriceSortStepDefinitions {
    WebDriver driver;
    SmartphonesPage currentPage;

    private final int IMPLICITLY_WAIT_TIMEOUT = 5;

    @Given("пользователь находится на странице Смартфонов")
    public void openSmartphonesPage() {
        driver = new ChromeDriver();
        driver.manage().window().fullscreen();
        driver.get("https://market.yandex.ru/catalog--smartfony/26893750/list");
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_TIMEOUT, TimeUnit.SECONDS);
        currentPage = new SmartphonesPage(driver);
    }

    @When("пользователь выбирает сортировку подешевле")
    public void chooseClickSortAsc() {
        currentPage.clickSortAsc();
    }

    @When("пользователь выбирает сортировку подороже")
    public void chooseSortDesc() {
        currentPage.clickSortDesc();
    }

    @Then("товары отображаются отсортированными по цене по возрастанию")
    public void checkProductsOrderedAsc() {
        assertTrue(currentPage.isProductsOrdered(SmartphonesPage.SORT_DIR_ASC));
    }

    @Then("товары отображаются отсортированными по цене по убыванию")
    public void checkProductsOrderedDesc() {
        assertTrue(currentPage.isProductsOrdered(SmartphonesPage.SORT_DIR_DESC));
    }

    @When("фильтруем товары по цене меньше первого + {float}")
    public void filterPriceLessFirstProduct(float diff) {
        int firstProductPrice = currentPage.getNthProductPrice(1);
        currentPage.changeMaxPriceFilter(String.valueOf(firstProductPrice + diff));
    }

    @When("фильтруем товары по цене больше первого + {float}")
    public void filterPriceMoreFirstProduct(float diff) {
        int firstProductPrice = currentPage.getNthProductPrice(1);
        currentPage.changeMinPriceFilter(String.valueOf(firstProductPrice + diff));
    }

    @And("фильтруем товары по цене больше первого и меньше второго")
    public void filterPriceMoreFirstLessSecondProduct() {
        int firstProductPrice = currentPage.getNthProductPrice(1);
        int secondProductPrice = currentPage.getNthProductPrice(2);
        currentPage.changeMinPriceFilter(String.valueOf(firstProductPrice));
        currentPage.changeMaxPriceFilter(String.valueOf(secondProductPrice));
    }

    @Then("нашлось более {int} товаров")
    public void isProductCntMore(int cnt) {
        assertTrue(currentPage.getProductCnt() > cnt);
    }

    @Then("нашлось {int} товаров")
    public void isProductCntEqual(int cnt) {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        assertEquals(cnt, currentPage.getProductCnt());
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_TIMEOUT, TimeUnit.SECONDS);
    }

    @And("дожидаемся загрузки")
    public void waitReload() {
        currentPage.waitPreload();
    }

    @After
    public void closeBrowser() {
        if (driver != null) {
            driver.close();
        }
    }
}