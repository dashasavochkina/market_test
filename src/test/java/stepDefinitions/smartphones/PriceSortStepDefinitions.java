package stepDefinitions.smartphones;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import pageobject.SmartphonesPage;
import utils.AbstractTestClass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PriceSortStepDefinitions extends AbstractTestClass {
    SmartphonesPage currentPage;

    @Given("пользователь находится на странице Смартфонов")
    public void openSmartphonesPage() {
        driver.get("https://market.yandex.ru/catalog--smartfony/26893750/list");
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
        assertTrue(currentPage.isProductsOrdered(SmartphonesPage.ASC));
    }

    @Then("товары отображаются отсортированными по цене по убыванию")
    public void checkProductsOrderedDesc() {
        assertTrue(currentPage.isProductsOrdered(SmartphonesPage.DESC));
    }

    @When("фильтруем товары по цене меньше первого + {float}")
    public void filterPriceLessFirstProduct(float increment) {
        int firstProductPrice = currentPage.getNthProductPrice(1);
        currentPage.setMaxPriceFilter(String.valueOf(firstProductPrice + increment));
    }

    @When("фильтруем товары по цене больше первого + {float}")
    public void filterPriceMoreFirstProduct(float increment) {
        int firstProductPrice = currentPage.getNthProductPrice(1);
        currentPage.setMinPriceFilter(String.valueOf(firstProductPrice + increment));
    }

    @And("фильтруем товары по цене больше первого и меньше второго")
    public void filterPriceMoreFirstLessSecondProduct() {
        int firstProductPrice = currentPage.getNthProductPrice(1);
        int secondProductPrice = currentPage.getNthProductPrice(2);
        currentPage.setMinPriceFilter(String.valueOf(firstProductPrice));
        currentPage.setMaxPriceFilter(String.valueOf(secondProductPrice));
    }

    @Then("нашлось более {int} товаров")
    public void isProductCntMore(int cnt) {
        assertTrue(currentPage.getProductsCount() > cnt);
    }

    @Then("нашлось {int} товаров")
    public void isProductCntEqual(int cnt) {
        assertEquals(cnt, currentPage.getProductsCount());
    }

    @And("дожидаемся загрузки {int} секунд")
    public void waitReload(int sec) {
        currentPage.waitPreload(sec);
    }
}