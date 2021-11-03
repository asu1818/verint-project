import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class action {
    //variable
    public static WebDriver webDriver;
    public static String chromeDriver = "chromedriver77.exe";
    public static String linkUrl = "http://Verint.com";
    //page object model
    public static By objectButtonSearch =  By.xpath("//*[@id='nav-toggle']");;
    public static By objectFieldSearch = By.xpath("//*[@id='downshift-0-input']");
    public static By objectButtonGo = By.xpath("//*[@class='sj-input__button css-1k4nm74']");
    public static By objectFieldSearchResult = By.xpath("//*[@class='main-content rxbodyfield']/h1");
    public static By objectFieldValidasiText = By.xpath("//*[@class='sj-summary css-19dcgav']/following:: h3[1]");
    //number of screenshot
    public static String number1 = "1";
    public static String number2 = "2";
    public static String number3 = "3";
    public static String number4 = "4";

    public static void main(String[] args) throws InterruptedException {
        //load chrome driver
        loadDriver();

        //GOTO URL
        webDriver.get(linkUrl);
        Thread.sleep(4000);
        screenShotPage(number1);

        //click search button
        waitUntil(objectButtonSearch);
        webDriver.findElement(objectButtonSearch).click();
        Thread.sleep(500);
        screenShotPage(number2);

        //settext field and apply to search
        waitUntil(objectFieldSearch);
        webDriver.findElement(objectFieldSearch).sendKeys("customer solution");
        Thread.sleep(500);
        screenShotPage(number3);
        webDriver.findElement(objectButtonGo).click();

        //validasi search page
        waitUntil(objectFieldSearchResult);
        screenShotPage(number4);
        int sizeElement1 = webDriver.findElements(objectFieldSearchResult).size();
        for(int i=1; i <= 10; i++){
            if(sizeElement1 > 0){
                System.out.println("Result Page Found");
                continue;
            }else{
                System.out.println("Object Not Found");
                break;
            }
        }

        //validasi article title contains
        String sizeElement2 = webDriver.findElement(objectFieldValidasiText).getText().trim().toLowerCase();
        for(int j=1; j <=10; j++){
            if(sizeElement2.contains("customer solution")){
                System.out.println("Article Found");
            }else{
                System.out.println("Article Not Found");
                break;
            }
        }

        webDriver.close();
        webDriver.quit();

    }

    public static void loadDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\externalDriver\\" + chromeDriver);
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("Chrome browser launched successfully");
    }

    public static void waitUntil(By objectWait) throws InterruptedException {
        int intTotalElement;
        do {
            intTotalElement = webDriver.findElements(objectWait).size();
            Thread.sleep(4000);
        }while (intTotalElement == 0);
    }

    private static void screenShotPage(String number) throws InterruptedException {
        Thread.sleep(500);
        String filePath = System.getProperty("user.dir") + "\\src\\screenShot\\";
        String fileImageName = "Verint_00" + number;

        Shutterbug.shootPage(webDriver, ScrollStrategy.VIEWPORT_ONLY, 500).withName(fileImageName).save(filePath);
    }
}
