import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Demo {

    private WebDriver driver = null;

    public void run() {

    }


    public void init() {
        Path chromeDriver = Paths.get(System.getProperty("user.dir"), "lib", "chromedriver");
        System.out.println(chromeDriver);
        System.setProperty("webdriver.chrome.driver", chromeDriver.toString());
        this.driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        this.driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        this.driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
        this.driver.manage().window().maximize();
        this.driver.get("https://z.cn");
    }


    public void search() {
        this.driver.findElement(By.id("twotabsearchtextbox")).click();
        this.driver.findElement(By.id("twotabsearchtextbox")).clear();
        this.driver.findElement(By.id("twotabsearchtextbox")).sendKeys("书包");
        this.driver.findElement(By.xpath("//input[@type='submit']")).click();


        JavascriptExecutor executor = (JavascriptExecutor)driver;
        for(int i=0; i<12; i++) {
            executor.executeScript("window.scrollBy(0,500)");
        }




    }

    public void ShowDetail(int i) {


    }




}
