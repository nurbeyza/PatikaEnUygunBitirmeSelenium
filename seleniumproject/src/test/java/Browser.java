import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;


/**
 * *Bu sınıf Connection sınıfından miras almıştır.
 * *Connection sınıfında bulunan url ve path fonksiyonlarını rahatlıkla kullanabilir.
 */

public class Browser extends Connection{
    public static WebDriver driver;

    //Bu fonksiyon açılmak istenilen browserın ne olduğu anlayıp URL ve driiver yolunu alarak ilgili sayfayı açıyor
    //Fonksiyon driver döndürüyor
    public WebDriver OpenBrowser(String Browser, String url, String path) {
        if(Browser.equals("Chrome")){
            System.setProperty("webdriver.chrome.driver", path+ "/binary/chromedriver.exe");
            driver = new ChromeDriver();
            driver.get(url);
            driver.manage().window().maximize();
            return driver;
        }
        else if (Browser.equals("Firefox")) {
            System.setProperty("webdriver.gecko.driver", path+ "/binary/geckodriver.exe");
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.get(url);
            return driver;
        }
        return null;
    }


}
