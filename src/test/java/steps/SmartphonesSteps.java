package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import pageobjects.SmartphonesPage;
import steps.base.BaseSteps;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SmartphonesSteps extends BaseSteps {
    SmartphonesPage smartphonesPage;

    @Given("открыта страница Смартфонов")
    public void openSmartphonesPage() {
        driver.get("https://market.yandex.ru/catalog--smartfony/26893750/list");
        smartphonesPage = new SmartphonesPage(driver);
    }

    @When("выбираем сортировку подешевле")
    public void chooseClickSortAsc() {
        smartphonesPage.clickSortAsc();
    }

    @When("выбираем сортировку подороже")
    public void chooseSortDesc() {
        smartphonesPage.clickSortDesc();
    }

    @Then("товары отображаются отсортированными по цене по возрастанию")
    public void checkProductsOrderedAsc() {
        assertTrue(smartphonesPage.isProductsOrdered(SmartphonesPage.ASC));
    }

    @Then("товары отображаются отсортированными по цене по убыванию")
    public void checkProductsOrderedDesc() {
        assertTrue(smartphonesPage.isProductsOrdered(SmartphonesPage.DESC));
    }

    @When("фильтруем товары по цене меньше первого + {float}")
    public void filterPriceLessFirstProduct(float increment) {
        int firstProductPrice = smartphonesPage.getProductPriceByRowIndex(0);
        smartphonesPage.setMaxPriceFilter(String.valueOf(firstProductPrice + increment));
    }

    @When("фильтруем товары по цене больше первого + {float}")
    public void filterPriceMoreFirstProduct(float increment) {
        int firstProductPrice = smartphonesPage.getProductPriceByRowIndex(0);
        smartphonesPage.setMinPriceFilter(String.valueOf(firstProductPrice + increment));
    }

    @And("фильтруем товары по цене больше первого и меньше второго")
    public void filterPriceMoreFirstLessSecondProduct() {
        int firstProductPrice = smartphonesPage.getProductPriceByRowIndex(1);
        int secondProductPrice = smartphonesPage.getProductPriceByRowIndex(2);
        smartphonesPage.setMinPriceFilter(String.valueOf(firstProductPrice));
        smartphonesPage.setMaxPriceFilter(String.valueOf(secondProductPrice));
    }

    @Then("нашлось более {int} товаров")
    public void isProductCntMore(int cnt) {
        assertTrue(smartphonesPage.getProductsCount() > cnt);
    }

    @Then("нашлось {int} товаров")
    public void isProductCntEqual(int cnt) {
        assertEquals(cnt, smartphonesPage.getProductsCount());
    }

    @And("дожидаемся загрузки {int} секунд")
    public void waitReload(int sec) {
        smartphonesPage.waitPreload(sec);
    }

    @Given("открыли браузер Google Chrome")
    public void isBrowserOpened() {
        assertNotNull(driver);
    }
}