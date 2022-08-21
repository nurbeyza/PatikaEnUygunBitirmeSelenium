import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * proje içerisinde kullanılan methodlar bu sınıf içerisinde yazıldı.
 */
public class Method extends Connection {
    Connection connection = new Connection();

    //Driver açıldıktan sonra ilgili elemente tıklıyor. Ve dışarıdan verdiğimiz origin parametresini inputa ekliyor.
    public void depertureDayAdd(WebDriver driver) {
        driver.findElement(By.id("OriginInput")).click();
        driver.findElement(By.id("OriginInput")).sendKeys(connection.getorigin());
    }

    //Driver açıldıktan sonra ilgili elemente tıklıyor. Ve dışarıdan verdiğimiz origin parametresini inputa ekliyor.
    public void returnDayAdd(WebDriver driver) {
        driver.findElement(By.id("DestinationInput")).click();
        driver.findElement(By.id("DestinationInput")).sendKeys(connection.getDestination());
    }


    //İlgili url açıldıktan sonra 15 saniye bekletip tüm sayfanın düzgün yüklenmesini bekliyoruz
    public void waitelement(WebDriver driver, String element) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
    }

    public void wait(WebDriver driver){

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
    }

    //Sistem üzerinden bugünün tarihini alıp bunu return ediyor.
    public String[] getDate(WebDriver driver, int day) {
        SimpleDateFormat data_format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, day);
        String date = data_format.format(calendar.getTime());
        String[] datalist = date.split("/");
        return datalist;
    }


    //Origin inputundan alınan ay verisini uygun formata çeviriyor.
    public String getMonth(String mont) {
        String month = null;
        switch (Integer.parseInt(mont)) {
            case 1:
                month = "Ocak";
                break;
            case 2:
                month = "Şubat";
                break;
            case 3:
                month = "Mart";
                break;
            case 4:
                month = "Nisan";
                break;
            case 5:
                month = "Mayıs";
                break;
            case 6:
                month = "Haziran";
                break;
            case 7:
                month = "Temmuz";
                break;
            case 8:
                month = "Ağustos";
                break;
            case 9:
                month = "Eylül";
                break;
            case 10:
                month = "Ekim";
                break;
            case 11:
                month = "Kasım";
                break;
            case 12:
                month = "Aralık";
                break;
            default:
                System.out.println("Bugün hafta içi");
        }

        return month;
    }

    //Gelen liste üzerinde gezinerek aradığımız tarihi datepicker üzerinden seçiyor.
    public void getDay(List<WebElement> elements, String day, String month) throws InterruptedException {

        for (int i = 0; i < elements.size(); i++) {

            try {
                String element = elements.get(i).getText();
                String element1 = elements.get(i).getAttribute("aria-label");
                if (element.contains(day) && element1.contains(month)) {
                    elements.get(i).click();
                }
            } catch (StaleElementReferenceException e) {


            }
        }

    }

    //Properties dosyasında bulunan isDirect bilgisine göre gerekli filtrelemeyi yapıyor.
    public void transferTypeControl(Boolean isDirect, WebElement hoverElement, WebElement isDirectElement, WebDriver driver, WebElement notDirectElement) {

        if (isDirect.equals(Boolean.TRUE)) {
            Actions action = new Actions(driver);
            action.moveToElement(hoverElement).moveToElement(isDirectElement).click().build().perform();

        } else {
            notDirectElement.click();
        }
    }

    public void downScroll(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)", "");
    }

    public void upScroll(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,-1000)", "");
    }

    //Provider vilgisine uygun uçuşu ve yine bununla aynı pakette bulunan dönüş uçuşunu seçiyor.
    public void chooseProvider(WebDriver driver, String provider) throws InterruptedException {
        for (int i = 1; i < 58; i++) {
            // WebElement element=driver.findElement(By.xpath("//*[@id=\"SearchRoot\"]/div/div[2]/div[1]/div[4]/div/div[9]/div[2]/div[1]/label"+"["+i+"]"));
            WebElement element = driver.findElement(By.xpath("//*[@id=\"SearchRoot\"]/div/div[2]/div[2]/div/div[2]/div/div[2]/div[1]/div" + "[" + i + "]" + "/div/div/div[1]/div[1]/label/div[2]"));
            if (element.getAttribute("data-booking-provider").contains(provider)) {
                Thread.sleep(1000);
                element.click();
                Thread.sleep(1000);

                break;
            }

        }


    }

}