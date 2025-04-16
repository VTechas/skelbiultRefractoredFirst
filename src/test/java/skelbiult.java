import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class skelbiult {

    @Test
    public void openWebsite() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://skelbiu.lt");
        Thread.sleep(500);
        WebElement acceptBtn = driver.findElement(By.id("onetrust-accept-btn-handler"));
        acceptBtn.click();
        driver.findElement(By.id("searchKeyword")).sendKeys("kiaune");
        driver.findElement(By.id("searchButton")).click();
        Thread.sleep(500);
        String searchUrl = driver.getCurrentUrl();
        boolean hasNextPage = true;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        while (hasNextPage) {
            WebElement container = driver.findElement(By.className("standard-list-container"));
            List<WebElement> contentBlocks = container.findElements(By.className("content-block"));

            for (int i = 0; i < contentBlocks.size(); i++) {

                try {
                    Thread.sleep(1500);
                    contentBlocks.get(i).click();
                    String nickname = driver.findElement(By.xpath("//*[@id='user-info-container']//div[@class='name']")).getText();
                    String sellerUrl = driver.getCurrentUrl();
                    System.out.println("Seller: " + nickname + " | URL: " + sellerUrl);
                } catch(Exception e){
                    System.out.println("Could not find seller info on item: " + (i + 1));
                }

                try {
                    driver.get(searchUrl);
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.className("standard-list-container")));
                    Thread.sleep(1000);
                    container = driver.findElement(By.className("standard-list-container"));
                    contentBlocks = container.findElements(By.className("content-block"));

                } catch(Exception e){
                    break;
                }
            }

            try {
                WebElement nextBtn = driver.findElement(By.xpath("//a[@rel='next']"));
                nextBtn.click();
                Thread.sleep(1500);
                searchUrl = driver.getCurrentUrl();

            } catch (Exception e) {
                hasNextPage = false;
                System.out.println("No more pages. Done.");
            }
        }
    }


}

//public class skelbiult {
//
//    WebDriver driver;
//    List<String> allPrices = new ArrayList<>();
//    int pageNumber = 1;
//    boolean hasMorePages = true;
//    double sum = 0;
//    int priceCount = 0;
//
//    @Test
//    public void runTest() throws InterruptedException {
//        openDriver();
//        acceptCookies();
//        searchForItem();
//        scanPages();
//        calculatePrices();
//        showSummary();
//    }
//
//    public void openDriver() {
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//    }
//
//    public void acceptCookies() throws InterruptedException {
//        driver.get("https://www.skelbiu.lt");
//        Thread.sleep(1000);
//        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
//    }
//
//    public void searchForItem() throws InterruptedException {
//        driver.findElement(By.id("searchKeyword")).sendKeys("verpimo ratelis");
//        driver.findElement(By.id("searchButton")).click();
//        Thread.sleep(1500);
//    }
//
//    public void scanPages() throws InterruptedException {
//        while (hasMorePages) {
//            scanCurrentPage();
//            goToNextPage();
//        }
//    }
//
//    public void scanCurrentPage() {
//        System.out.println("Scanning page " + pageNumber);
//            WebElement container = driver.findElement(By.className("standard-list-container"));
//            List<WebElement> blocks = container.findElements(By.className("content-block"));
//
//            for (int i = 0; i < blocks.size(); i++) {
//                try {
//                    WebElement price = blocks.get(i).findElement(By.className("price"));
//                    String priceText = price.getText();
//                    allPrices.add(priceText);
//                    System.out.println("Kaina: " + priceText);
//                } catch (Exception e) {
//                    System.out.println("Kainos nebuvo");
//                }
//            }
//     }
//
//    public void goToNextPage() throws InterruptedException {
//        try {
//            WebElement nextPage = driver.findElement(By.xpath("//div[@id='pagination']//a[@rel='next']"));
//            nextPage.click();
//            pageNumber++;
//            Thread.sleep(1500);
//        } catch (Exception e) {
//            hasMorePages = false;
//            System.out.println("No more pages.");
//        }
//    }
//
//    public void calculatePrices() {
//        for (int i = 0; i < allPrices.size(); i++) {
//            String raw = allPrices.get(i);
//            String cleaned = raw.replaceAll("[^0-9,\\.]", "").replace(",", ".");
//
//            if (!cleaned.isEmpty()) {
//                    double price = Double.parseDouble(cleaned);
//                    sum = sum + price;
//                    priceCount = priceCount + 1;
//            }
//        }
//    }
//
//    public void showSummary() {
//        double avg = 0;
//        if (priceCount > 0) {
//            avg = sum / priceCount;
//        }
//
//        System.out.println("====== Rezultatai ======");
//        System.out.println("Skelbimu su kaina: " + priceCount);
//        System.out.println("Visu prekiu kainu suma: " + sum);
//        System.out.println("Visu prekiu vidutine kaina: " + avg);
//    }
//
//}

