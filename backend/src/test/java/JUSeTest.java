import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JUSeTest {

    public WebDriver initDriver() {
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\ChromeDrive\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;

    }

    @Test
    public void correctUserLogin() {
        WebDriver driver = initDriver();
        driver.get("http://localhost:5173/login");
        WebElement element = driver.findElement(By.id("username"));
        element.sendKeys("ive");

        element = driver.findElement(By.id("password"));
        element.sendKeys("12345678");

        element = driver.findElement(By.xpath("//*[@id=\"login\"]/div[3]/div/div/div/div/button"));

        element.click();
        Boolean isPresent = driver.findElements(By.xpath("//*[@id=\"root\"]/div/div/main/div[2]/button[1]")).size() > 0;

        assertTrue(isPresent);

        driver.quit();
    }

    @Test
    public void incorrectUserLogin() {
        WebDriver driver = initDriver();
        driver.get("http://localhost:5173/login");
        WebElement element = driver.findElement(By.id("username"));
        element.sendKeys("mateolujicc");

        element = driver.findElement(By.id("password"));
        element.sendKeys("123456789");

        element = driver.findElement(By.xpath("//*[@id=\"login\"]/div[3]/div/div/div/div/button"));

        element.click();
        Boolean isPresent = driver.findElements(By.xpath("//*[@id=\"root\"]/div/div/main/div[2]/button[1]")).size() > 0;

        assertTrue(!isPresent);

        driver.quit();
    }

    @Test
    public void correctAddPrijava() {
        WebDriver driver = initDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://localhost:5173/report");
        WebElement element = driver.findElement(By.id("reportName"));
        element.sendKeys("nazivPrijave");
        element = driver.findElement(By.xpath("//*[@id=\"ostecenja\"]"));
        element.click();
        element = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div/div/div/div[1]"));
        element.click();
        element = driver.findElement(By.xpath("//*[@id=\"ured\"]"));
        element.click();
        element = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/div/div/div/div"));
        element.click();
        element = driver.findElement(By.xpath("//*[@id=\"lokacija\"]/div[1]/label[3]"));
        element.click();
        element = driver.findElement(By.xpath("//*[@id=\"addPrijava\"]/div[7]/div/div/div/div/button"));
        element.click();
        Boolean isPresent = driver.findElements(By.xpath("/html/body/div[4]/div/div[2]/div/div[2]/div[2]/button")).size() > 0;
        if (isPresent) {
            element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div/div[2]/div/div[2]/div[2]/button")));
            element.click();
        }
        isPresent = driver.findElements(By.xpath("//*[@id=\"root\"]/div/main/h2")).size() > 0;
        assertTrue(isPresent);
        driver.quit();
    }

    @Test
    public void incorrectAddPrijava() {
        WebDriver driver = initDriver();
        driver.get("http://localhost:5173/report");
        
        WebElement element = driver.findElement(By.xpath("//*[@id=\"addPrijava\"]/div[7]/div/div/div/div/button"));
        element.click();

        Boolean isPresent = driver.findElements(By.xpath("//*[@id=\"root\"]/div/main/h2")).size() > 0;

        assertTrue(!isPresent);
        driver.quit();

    }


}
