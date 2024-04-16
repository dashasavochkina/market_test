package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmartphonesPage extends AbstractPage{
    public WebDriver driver;

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
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickSortAsc() {
        sortAscButton.click();
    }

    public void clickSortDesc() {
        sortDescButton.click();
    }

    public void changeMinPriceFilter(String value) {
        minPriceFilterInput.sendKeys(value);
    }

    public void clearMinPriceFilter() {
        minPriceFilterInput.clear();
    }

    public void changeMaxPriceFilter(String value) {
        maxPriceFilterInput.sendKeys(value);
    }

    public void clearMaxPriceFilter() {
        maxPriceFilterInput.clear();
    }

    public void waitPreload() {
        WebElement preloaderElement = driver.findElement(By.xpath(preloaderScreen));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.invisibilityOf(preloaderElement));
    }

    public boolean isProductsOrdered(String order) {
        ArrayList<Integer> productPrices = this.getProductPrices();
        Integer prevPrice = productPrices.get(0);
        for (Integer curPrice : productPrices) {
            if (
                (order.equals(SORT_DIR_ASC) && curPrice < prevPrice)
                || (order.equals(SORT_DIR_DESC) && curPrice > prevPrice)
            ) {
                return false;
            }
            prevPrice = curPrice;
        }

        return true;
    }

    public int getProductCnt() {
        return driver.findElements(By.xpath(this.productsPrices)).size();
    }

    public int getNthProductPrice(int num) {
        int i = 0;
        for (WebElement element : driver.findElements(By.xpath(this.productsPrices))) {
            if (++i == num) {
                return this.textPriceToInt(element.getText());
            }
        }

        throw new RuntimeException("There are no " + num + "th product on page");
    }

    private ArrayList<Integer> getProductPrices() {
        ArrayList<Integer> result = new ArrayList<>();
        for (WebElement element : driver.findElements(By.xpath(this.productsPrices))) {
            result.add(this.textPriceToInt(element.getText()));
        }

        return result;
    }

    private int textPriceToInt(String textPrice) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(textPrice);
        String elementPrice = "";
        while (matcher.find()) {
            elementPrice = String.format("%s%s", elementPrice, matcher.group());
        }
        return Integer.parseInt(elementPrice);
    }
}