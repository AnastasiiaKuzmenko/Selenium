import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = initChromeDriver();

        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");

        loginToTheSite(driver);

        waitSomeTime(500);

        ExitFromTheSite(driver);

        loginToTheSite(driver);

        waitSomeTime(500);

        checkThatUrlIsTheSameAfterRefreshing(driver);

        driver.close();
    }

    private static void ExitFromTheSite(WebDriver driver) {
        WebElement avatar = driver.findElement(By.xpath("//li[@id='employee_infos']/a[@class='employee_name dropdown-toggle']"));
        avatar.click();

        WebElement exit = driver.findElement(By.id("header_logout"));
        exit.click();
    }

    private static void checkThatUrlIsTheSameAfterRefreshing(WebDriver driver) {
        int itemsCount = driver.findElements(By.xpath("//ul[@class=\"menu\"]/li/a")).size();

        for (int i = 1; i <= itemsCount; i++){

            WebElement item;

            waitSomeTime(500);

            if (i == 4 | i == 8) {
                item = driver.findElement(By.xpath("(//ul[@class='main-menu']/li/a/span)[" + i + "]"));
            }
            else{
                item = driver.findElement(By.xpath("(//ul[@class='menu']/li/a/span)[" + i + "]"));
            }

            String title = item.getText();

            clickMenuItem(driver, title);

            System.out.println(title);

            driver.navigate().refresh();

            waitSomeTime(1000);

            //it is possible to check using url and assert that it contains title
            String currentURL = driver.getCurrentUrl();
        }
    }

    private static void waitSomeTime(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void clickMenuItem(WebDriver driver, String itemTitle) {
        WebElement item = driver.findElement(By.xpath("//span[contains(text(),\"" + itemTitle + "\")]"));
        item.click();
    }

    private static void loginToTheSite(WebDriver driver) {
        WebElement login = driver.findElement(By.name("email"));
        login.sendKeys("webinar.test@gmail.com");
        WebElement password = driver.findElement(By.name("passwd"));
        password.sendKeys("Xcg7299bnSmMuRLp9ITw\n");
    }

    public static WebDriver initChromeDriver(){
        File file = new File("drivers/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        return new ChromeDriver();
    }
}
