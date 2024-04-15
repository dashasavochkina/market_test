package pageObject;

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
    public static final String SORT_DIR_ASC = "ASC";
    public static final String SORT_DIR_DESC = "DESC";

    public WebDriver driver;

    @FindBy(xpath = "/html/body/div[1]/div/div[4]/div/div/div[1]/div/div/div[5]/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/noindex/div/button[2]")
    private WebElement sortAscButton;

    @FindBy(xpath = "/html/body/div[1]/div/div[4]/div/div/div[1]/div/div/div[5]/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/noindex/div/button[3]")
    private WebElement sortDescButton;

    @FindBy(xpath = "/html/body/div[1]/div/div[4]/div/div/div[1]/div/div/div[5]/div/div/div/div/div/aside/div/div[3]/div/div/div/div/div[1]/div/fieldset/div/div/div/div[1]/span/div/div/input")
    private WebElement minPriceFilterInput;

    @FindBy(xpath = "/html/body/div[1]/div/div[4]/div/div/div[1]/div/div/div[5]/div/div/div/div/div/aside/div/div[3]/div/div/div/div/div[1]/div/fieldset/div/div/div/div[2]/span/div/div/input")
    private WebElement maxPriceFilterInput;

    private final String productsPricesXPath = "//div[@data-apiary-widget-name='@light/Organic']//span[@data-auto='snippet-price-current']/span[1]";
    private final String preloaderScreenXPath = "//div[@data-zone-name='searchPage']//div[@data-auto='preloader']";

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
        WebElement preloaderElement = driver.findElement(By.xpath(preloaderScreenXPath));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
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
        return driver.findElements(By.xpath(this.productsPricesXPath)).size();
    }

    public int getNthProductPrice(int num) {
        int i = 0;
        for (WebElement element : driver.findElements(By.xpath(this.productsPricesXPath))) {
            if (++i == num) {
                return this.textPriceToInt(element.getText());
            }
        }

        throw new RuntimeException("There are no " + num + "th product on page");
    }

    private ArrayList<Integer> getProductPrices() {
        ArrayList<Integer> result = new ArrayList<>();
        for (WebElement element : driver.findElements(By.xpath(this.productsPricesXPath))) {
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