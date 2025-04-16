import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class skelbiult {

    WebDriver driver;
    List<String> allPrices = new ArrayList<>();
    int pageNumber = 1;
    boolean hasMorePages = true;
    double sum = 0;
    int priceCount = 0;

    @Test
    public void runTest() throws InterruptedException {
        openDriver();
        acceptCookies();
        searchForItem();
        scanPages();
        calculatePrices();
        showSummary();
    }

    public void openDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void acceptCookies() throws InterruptedException {
        driver.get("https://www.skelbiu.lt");
        Thread.sleep(1000);
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
    }

    public void searchForItem() throws InterruptedException {
        driver.findElement(By.id("searchKeyword")).sendKeys("verpimo ratelis");
        driver.findElement(By.id("searchButton")).click();
        Thread.sleep(1500);
    }

    public void scanPages() throws InterruptedException {
        while (hasMorePages) {
            scanCurrentPage();
            goToNextPage();
        }
    }

    public void scanCurrentPage() {
        System.out.println("Scanning page " + pageNumber);
            WebElement container = driver.findElement(By.className("standard-list-container"));
            List<WebElement> blocks = container.findElements(By.className("content-block"));

            for (int i = 0; i < blocks.size(); i++) {
                try {
                    WebElement price = blocks.get(i).findElement(By.className("price"));
                    String priceText = price.getText();
                    allPrices.add(priceText);
                    System.out.println("Kaina: " + priceText);
                } catch (Exception e) {
                    System.out.println("Kainos nebuvo");
                }
            }
     }

    public void goToNextPage() throws InterruptedException {
        try {
            WebElement nextPage = driver.findElement(By.xpath("//div[@id='pagination']//a[@rel='next']"));
            nextPage.click();
            pageNumber++;
            Thread.sleep(1500);
        } catch (Exception e) {
            hasMorePages = false;
            System.out.println("No more pages.");
        }
    }

    public void calculatePrices() {
        for (int i = 0; i < allPrices.size(); i++) {
            String raw = allPrices.get(i);
            String cleaned = raw.replaceAll("[^0-9,\\.]", "").replace(",", ".");

            if (!cleaned.isEmpty()) {
                    double price = Double.parseDouble(cleaned);
                    sum = sum + price;
                    priceCount = priceCount + 1;
            }
        }
    }

    public void showSummary() {
        double avg = 0;
        if (priceCount > 0) {
            avg = sum / priceCount;
        }

        System.out.println("====== Rezultatai ======");
        System.out.println("Skelbimu su kaina: " + priceCount);
        System.out.println("Visu prekiu kainu suma: " + sum);
        System.out.println("Visu prekiu vidutine kaina: " + avg);
    }

}

