package MyStepDef;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.assertTrue;
import java.util.List;

public class StepDef {

    WebDriver driver;
    WebDriverWait wait;

    public StepDef() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Bekleme süresi artırıldı
    }

    @Given("I open Trendyol")
    public void i_open_trendyol() {
        driver.manage().window().maximize();
        driver.get("https://www.trendyol.com/");
        closePopupIfExists(By.id("onetrust-reject-all-handler"), "Cookies");
        closePopupIfExists(By.cssSelector("div[aria-label='Bildirimleri Kapat']"), "Notifications");
        System.out.println("Trendyol sayfası açıldı.");
    }

    @When("I search for {string} on Trendyol")
    public void i_search_for_on_trendyol(String searchTerm) {
        try {
            System.out.println("Arama kutusunu kontrol ediyorum...");

            // Sayfadaki tüm input'ları listeleyip yazdıralım (debugging amaçlı)
            List<WebElement> inputs = driver.findElements(By.tagName("input"));
            System.out.println("Sayfada toplam input sayısı: " + inputs.size());

            for (WebElement input : inputs) {
                System.out.println("Input HTML: " + input.getAttribute("outerHTML"));
            }

            // Buradan doğru input'u kontrol et
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-testid='suggestion']")));
            System.out.println("Arama kutusu bulundu.");

            searchBox.sendKeys(searchTerm, Keys.ENTER);
            System.out.println(searchTerm + " araması yapıldı.");

            // Sonuçların yüklenmesini bekle
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[data-testid='product-card']")));
            System.out.println("Arama sonuçları yüklendi.");
        } catch (TimeoutException e) {
            throw new RuntimeException("Arama yapılamadı: Element bulunamadı veya bekleme süresi doldu.");
        }
    }

    @Then("I should see results related to {string}")
    public void i_should_see_results_related_to(String searchTerm) {
        try {
            // Sayfadaki ürün başlıklarını bulma
            List<WebElement> productTitles = driver.findElements(By.cssSelector("div[data-testid='product-card']"));

            // Başlıkların sayısını kontrol etme
            System.out.println("Bulunan ürün sayısı: " + productTitles.size());

            // Debug amaçlı başlıkları yazdırma
            boolean found = false;
            for (WebElement title : productTitles) {
                String productText = title.getText();
                System.out.println("Ürün Başlığı: " + productText);

                if (productText.toLowerCase().contains(searchTerm.toLowerCase())) {
                    found = true;
                    break;  // Bir tane bulunduysa yeterli
                }
            }

            assertTrue(searchTerm + " kelimesi arama sonuçlarında bulunamadı.", found);
        } catch (Exception e) {
            throw new RuntimeException("Sonuç doğrulaması yapılamadı: " + e.getMessage());
        }
    }

    @Then("I close the browser")
    public void i_close_the_browser() {
        if (driver != null) {
            driver.quit();
            System.out.println("Tarayıcı kapatıldı.");
        }
    }

    // Popup'ları kontrol edip kapatma
    private void closePopupIfExists(By by, String popupName) {
        try {
            WebElement popup = wait.until(ExpectedConditions.elementToBeClickable(by));
            popup.click();
            System.out.println(popupName + " pop-up kapatıldı.");
        } catch (Exception e) {
            System.out.println(popupName + " pop-up bulunamadı veya zaten kapalı.");
        }
    }
}
