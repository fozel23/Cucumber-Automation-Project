package MyStepDef1;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.en.*;
import static org.junit.Assert.assertTrue;

import java.util.List;

public class StepDefEbay {

    WebDriver driver;

    @Given("I open Ebay")
    public void i_open_ebay() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.ebay.com");
        driver.manage().window().maximize(); // Tarayıcıyı tam ekran yapma
    }

    @When("I search for {string}")
    public void i_search_for(String product) {
        WebElement searchBox = driver.findElement(By.id("gh-ac"));
        searchBox.sendKeys(product);
        searchBox.sendKeys(Keys.ENTER);
    }

    @Then("I should see products related to {string}")
    public void i_should_see_products_related_to(String product) {
        List<WebElement> products = driver.findElements(By.cssSelector(".s-item"));
        assertTrue("No products found for: " + product, products.size() > 0);
    }

    @And("I close the browser")
    public void i_close_the_browser() {
        driver.quit();
    }
}
