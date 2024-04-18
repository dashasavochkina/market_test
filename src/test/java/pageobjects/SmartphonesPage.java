package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmartphonesPage extends AbstractPage {
    @FindBy(xpath = "//button[@data-autotest-id=\"aprice\"]")
    private WebElement sortAscButton;

    @FindBy(xpath = "//button[@data-autotest-id=\"dprice\"]")
    private WebElement sortDescButton;

    @FindBy(xpath = "//div[@data-auto=\"filter-range-glprice\"]//span[@data-auto=\"filter-range-min\"]//input")
    private WebElement minPriceFilterInput;

    @FindBy(xpath = "//div[@data-auto=\"filter-range-glprice\"]//span[@data-auto=\"filter-range-max\"]//input")
    private WebElement maxPriceFilterInput;

    private final String productsPrices = "//div[@data-apiary-widget-name='@light/Organic']//span[@data-auto='snippet-price-current']/span[1]";
    private final String preloaderScreen = "//div[@data-zone-name='searchPage']//div[@data-auto='preloader']";

    public SmartphonesPage(WebDriver driver) {
        super(driver);
    }

    public void clickSortAsc() {
        sortAscButton.click();
    }

    public void clickSortDesc() {
        sortDescButton.click();
    }

    public void setMinPriceFilter(String value) {
        minPriceFilterInput.sendKeys(value);
    }

    public void setMaxPriceFilter(String value) {
        maxPriceFilterInput.sendKeys(value);
    }

    public void waitPreload(int sec) {
        WebElement preloaderElement = driver.findElement(By.xpath(preloaderScreen));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
        wait.until(ExpectedConditions.invisibilityOf(preloaderElement));
    }

    public boolean isProductsOrdered(String order) {
        ArrayList<Integer> productPrices = this.getProductPrices();
        Integer prevPrice = productPrices.get(0);
        for (Integer curPrice : productPrices) {
            if (
                (order.equals(ASC) && curPrice < prevPrice)
                || (order.equals(DESC) && curPrice > prevPrice)
            ) {
                return false;
            }
            prevPrice = curPrice;
        }

        return true;
    }

    public int getProductsCount() {
        return (new WebDriverWait(driver, Duration.ofSeconds(1)))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(productsPrices)))
                .size();
    }

    public int getProductPriceByRowNumber(int index) {
        return this.convertStringPriceToInt(driver.findElements(By.xpath(productsPrices)).get(index).getText());
    }

    private ArrayList<Integer> getProductPrices() {
        ArrayList<Integer> result = new ArrayList<>();
        for (WebElement element : driver.findElements(By.xpath(productsPrices))) {
            result.add(this.convertStringPriceToInt(element.getText()));
        }

        return result;
    }

    private int convertStringPriceToInt(String textPrice) {

        Pattern pattern = Pattern.compile("^(\\ d+).*;(\\d+)$");
        Matcher matcher = pattern.matcher(textPrice);
        return Integer.parseInt(matcher.group(1)+matcher.group(2));
    }
}