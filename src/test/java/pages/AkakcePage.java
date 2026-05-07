package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class AkakcePage {
    private WebDriverWait wait;
    private AndroidDriver driver;

    private void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("appium:platformName", "ANDROID");
        caps.setCapability("appium:platformVersion", "10.0");
        caps.setCapability("appium:deviceName", "pixel_4");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:appPackage", "com.akakce.akakce");
        caps.setCapability("appium:appActivity", "com.akakce.akakce.ui.main.mainactivity.MainActivity");
        //caps.setCapability("appium:app", "/src/main/resources/");
        caps.setCapability("appium:noReset", true);
        caps.setCapability("appium:fullReset", false);
        driver = new AndroidDriver(new URL("http://localhost:4723"), caps);
        driver.configuratorSetWaitForIdleTimeout(Duration.ofMillis(0));
        driver.configuratorSetWaitForSelectorTimeout(Duration.ofMillis(0));

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    private final By homePage = AppiumBy.id("com.akakce.akakce:id/headLogo");
    private final By searchButton = AppiumBy.id("com.akakce.akakce:id/searchTextView");
    private final By applyFilterButton = AppiumBy.id("com.akakce.akakce:id/apply");
    private final By sortingOptions = AppiumBy.id("com.akakce.akakce:id/sortText");
    private final By detailButton = AppiumBy.id("com.akakce.akakce:id/detailBtnTextView");
    private final String cellContainer = "(//android.widget.ImageView[@resource-id=\"com.akakce.akakce:id/image\"])[2]";

    public By sortName(String value) {
        return AppiumBy.xpath(String.format("//android.widget.TextView[@resource-id=\"com.akakce.akakce:id/sort_name\" and @text=\"%s\"]", value));
    }

    public By textLocator(String value) {
        return AppiumBy.xpath(String.format("//android.view.View[@text=\"%s\"]", value));
    }

    public boolean isHomePageDisplayed() throws MalformedURLException {
        setUp();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(homePage)).isDisplayed();
    }

    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void setSearchButton(String searchText) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton)).sendKeys(searchText);

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).perform();
    }

    public void clickFilterButton() {
        clickFilterOption("Filtrele");
    }

    public void swipeUp() {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), 540, 1600));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(800),
                PointerInput.Origin.viewport(), 540, 600));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));
    }

    public void clickFilterOption(String text) {
        for (int i = 0; i < 10; i++) {
            try {
                List<WebElement> elements = driver.findElements(
                        By.xpath("//android.widget.TextView[@text='" + text + "']"));

                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    elements.get(0).click();
                    return;
                }
            } catch (Exception ignored) {
            }

            swipeUp();
        }
        throw new RuntimeException("Filter option bulunamadı: " + text);
    }

    public void clickShowProductsButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(applyFilterButton)).click();
    }

    public void clickSortingOptions() {
        wait.until(ExpectedConditions.elementToBeClickable(sortingOptions)).click();
    }

    public void clickSortName(String sortText) {
        wait.until(ExpectedConditions.elementToBeClickable(sortName(sortText))).click();
    }

    public void scrollUntilElementInvisible(int maxSwipeCount) {
        for (int i = 0; i < maxSwipeCount; i++) {
            try {
                WebElement element = driver.findElement(By.xpath(cellContainer));
                if (!element.isDisplayed()) {
                    return;
                }
            } catch (NoSuchElementException e) {
            }
            swipeUp();
        }
    }

    public void clickProduct() {
        scrollUntilElementInvisible(5);
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(cellContainer))).click();
    }

    public void clickDetailButton() {
        wait.until(ExpectedConditions.elementToBeClickable(detailButton)).click();
    }

    public boolean isProductDisplayed(String text) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(textLocator(text))).isDisplayed();
    }

}
