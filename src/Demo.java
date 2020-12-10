import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Demo {

    private WebDriver driver = null;
    private String title = null;

    public void init() throws Exception {

        Path chromeDriver = null;
        String OS = System.getProperty("os.name");
        System.out.println(OS);

        switch (OS) {
            case "Windows 10":
                chromeDriver=Paths.get(System.getProperty("user.dir"), "lib", "chromedriver.exe");
                break;
            case "":
                chromeDriver=Paths.get(System.getProperty("user.dir"), "lib", "chromedriver");
                break;
            default:
                throw new Exception("Chrome Driver Path not found");
        }

        System.out.println(chromeDriver);
        System.setProperty("webdriver.chrome.driver", chromeDriver.toString());
        this.driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        this.driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
        this.driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
        this.driver.manage().window().maximize();
        this.driver.get("https://z.cn");
    }


    public void search() throws InterruptedException, IOException {

        File file = new File(Paths.get(System.getProperty("user.dir"), "lib", "demo.txt").toString());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String content = br.readLine();
        br.close();
        System.out.println(content);

        //定位搜索框,并且输入
        this.driver.findElement(By.id("twotabsearchtextbox")).click();
        this.driver.findElement(By.id("twotabsearchtextbox")).clear();
        this.driver.findElement(By.id("twotabsearchtextbox")).sendKeys(content);
        Thread.sleep(1000);
        //点击搜索
        this.driver.findElement(By.xpath("//input[@type='submit']")).click();
        Thread.sleep(5000);
        //获取商品列表
        WebElement list = this.driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[3]/div[2]"));
        //获取商品的行数
        int rows = (list.findElements(By.xpath("./div")).size()-3)/4;

        System.out.println(rows);
        //scroll down
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        for(int i=0; i<rows; i++) {
            executor.executeScript("window.scrollBy(0,500)");
            Thread.sleep(1000);
        }
        //scroll up
        for(int i=0; i<rows; i++) {
            executor.executeScript("window.scrollBy(0,-500)");
            Thread.sleep(1000);
        }


    }

    public void showDetail(int i) throws InterruptedException {
        //获取第i个商品的内容作为句柄验证
        this.title = this.driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[3]/div[2]")).findElements(By.xpath("./div")).get(i).findElement(By.xpath("./div/span/div/div/span/a/div/img")).getAttribute("alt");
        System.out.println(this.title);
        //点击输入的第i个商品
        this.driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[3]/div[2]")).findElements(By.xpath("./div")).get(i).click();

        Thread.sleep(10000);


    }

    public void addCart() throws InterruptedException {
        //切换tab
        Set<String> handles=driver.getWindowHandles();
        System.out.println(handles.size());
        for(String handle:handles) {
            System.out.println(handle);
            if(driver.switchTo().window(handle).getTitle().contains(this.title)) {
                System.out.println("FOUND Relevant Handle -> "+ this.title);
                break;
            }
        }

        //添加购物车
        this.driver.findElement(By.xpath("//*[@id='add-to-cart-button']")).click();
        Thread.sleep(5000);
    }

    public void viewCart() throws InterruptedException {
        this.driver.findElement(By.xpath("//*[@id=\"nav-cart\"]")).click();
        Thread.sleep(5000);
    }

    public void Quit() {
        this.driver.quit();
    }




}