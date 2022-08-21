import io.qameta.allure.Description;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import java.util.List;


@Slf4j
public class TestRun {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestRun.class);
    public static WebDriver driverChrome, driverFirefox;
    Browser browser = new Browser();
    Method method = new Method();
    Connection connection=new Connection();



    //Teste başlamadan önceki adımı burada yapıyoruz, Browserları burada açıyoruz ve düzgün yükelnemsi için 15 saniye bekletiyoruz

    @BeforeClass
    public void Browser() throws Exception {
        log.info("Browser bağlantıları sağlandı.");
        driverChrome = browser.OpenBrowser("Chrome", browser.url(), browser.path());
        //Firefox için çalışmasını istersek aşağıda bulunan satırı açabiliriz.
        // driverFirefox = browser.OpenBrowser("Firefox", browser.url(), browser.path());

        Assert.assertNotNull(driverChrome.findElement(By.id("jumbotron-tab")));

    }

    //Testleri bu adımda koşuyoruz, method sınıdından uygun fonksiyonları çağırıyoruz
    @Test(priority = 0)
    @Step("ilgili sayfa açılıyor")
    public void  departureAdd() throws Exception {
        log.info("Deperture day parametresi alındı.");
        method.depertureDayAdd(driverChrome);

    }


    @Test(priority = 1)
    @Description("nasıl yani")
    public void departureSelectInput()throws Exception {
        log.info("Deperture Day için listeden ilk eleman seçildi.");
        driverChrome.findElement(By.id("react-autowhatever-OriginInput-section-0-item-0")).click();
    }


    @Test(priority = 2)
    public void  returnDayAdd() throws Exception {
        log.info("Return day parametresi alındı.");
        method.returnDayAdd(driverChrome);
        method.wait(driverChrome);

    }

    @Test(priority = 3)
    public void returnSelectInput(){
        log.info("return day için listenin ilk elemanı seçildi.");
        driverChrome.findElement(By.id("react-autowhatever-DestinationInput-section-0-item-0")).click();
    }

    @Test(priority = 4)
    public void  departureDayClick() throws InterruptedException {

        log.info("Datepicker üzerinden bugünün tarihinin seçilmesi.");
        WebElement input=driverChrome.findElement(By.id("DepartureDate"));
        input.click();
        String[] datalist=method.getDate(driverChrome,Integer.parseInt(connection.getdepartureDay()));
        String month=method.getMonth(datalist[1]);
        List<WebElement> elements = driverChrome.findElements(By.xpath("//td[@class=\"CalendarDay CalendarDay_1 CalendarDay__default CalendarDay__default_2\"]"));
        List<WebElement> elements2 = driverChrome.findElements(By.xpath("//td[@class=\"CalendarDay CalendarDay_1 CalendarDay__default CalendarDay__default_2 CalendarDay__firstDayOfWeek CalendarDay__firstDayOfWeek_3\"]"));
        List<WebElement> elements3 = driverChrome.findElements(By.xpath("//td[@class=\"CalendarDay CalendarDay_1 CalendarDay__default CalendarDay__default_2 CalendarDay__lastDayOfWeek CalendarDay__lastDayOfWeek_3\"]\n"));

        //Hafta içi günler için
        method.getDay(elements, datalist[0],month);

        //Haftanın ilk günlerini kontrol
        method.getDay(elements2,datalist[0],month);

        //Haftanın son gününü kontrol
        method.getDay(elements3,datalist[0],month);


    }


    @Test(priority = 5)
    public void returnDayClick() throws InterruptedException {

        log.info("Datepicker üzeirnden dönüş günün seçilmesi.");
        WebElement input=driverChrome.findElement(By.id("ReturnDate"));
        input.click();
        int day= Integer.parseInt(connection.getdepartureDay())+Integer.parseInt(connection.getReturnDay());
        String[] datalist=method.getDate(driverChrome,day);

        WebElement returndate=driverChrome.findElement(By.xpath("//input[@class=\"DateInput_input DateInput_input_1 DateInput_input__readOnly DateInput_input__readOnly_2\"]"));
        String date[]=returndate.getAttribute("value").split(",");
        String month=method.getMonth(datalist[1]);
        List<WebElement> elements = driverChrome.findElements(By.xpath("//td[@class=\"CalendarDay CalendarDay_1 CalendarDay__default CalendarDay__default_2\"]"));
        List<WebElement> elements2 = driverChrome.findElements(By.xpath("//td[@class=\"CalendarDay CalendarDay_1 CalendarDay__default CalendarDay__default_2 CalendarDay__firstDayOfWeek CalendarDay__firstDayOfWeek_3\"]"));
        List<WebElement> elements3 = driverChrome.findElements(By.xpath("//td[@class=\"CalendarDay CalendarDay_1 CalendarDay__default CalendarDay__default_2 CalendarDay__lastDayOfWeek CalendarDay__lastDayOfWeek_3\"]\n"));

        //Hafta içi günler için
        method.getDay(elements, datalist[0],month);

        //Haftanın ilk günlerini kontrol
        method.getDay(elements2,datalist[0],month);

        //Haftanın son gününü kontrol
        method.getDay(elements3,datalist[0],month);

    }

    @Test(priority = 6)
    public void flightSearchButtonClick() throws InterruptedException {
        driverChrome.findElement(By.xpath("//button[@class=\"primary-btn block\"]")).click();
        method.waitelement(driverChrome,"//div[@class=\"flight-list-header combine roundTripHeader desktopHeader false\"]");
    }




    @Test(priority = 7)
    public void clickTransferTypeFlight() throws InterruptedException {

        log.info("Uygun transfer tipinin seçilmesi.");
        Assert.assertNotNull( driverChrome.findElement(By.xpath("//div[@class=\"flight-list-header combine roundTripHeader desktopHeader false\"]")));
        Boolean isDirect=connection.getIsDirect();
        WebElement hoverElement= driverChrome.findElement(By.xpath("//*[@id=\"SearchRoot\"]/div/div[2]/div[1]/div[4]/div/div[2]/div[2]/div/label[1]/span[2]"));
        WebElement isDirectElement=driverChrome.findElement(By.xpath("//*[@id=\"SearchRoot\"]/div/div[2]/div[1]/div[4]/div/div[2]/div[2]/div/label[1]/button"));
        WebElement notDirectElement=driverChrome.findElement(By.xpath("//*[@id=\"SearchRoot\"]/div/div[2]/div[1]/div[4]/div/div[2]/div[2]/div/label[1]"));
        method.transferTypeControl(isDirect,hoverElement,isDirectElement,driverChrome, notDirectElement);

    }

    @Test(priority = 8)
    public void clickDepertureFlight() throws InterruptedException {

        log.info("Uygun deperture uçuşunun seçilmesi");
        String provider= connection.getProvider();
        method.chooseProvider(driverChrome, provider);
        method.wait(driverChrome);
        method.upScroll(driverChrome);
        method.wait(driverChrome);
    }

    @Test(priority = 9)
    public void clickReturnFlights() throws InterruptedException {

        driverChrome.findElement(By.xpath("//*[@id=\"SearchRoot\"]/div/div[2]/div[2]/div/div[2]/div/div[2]/div[1]/div[1]/div/div/div[1]/div[2]/label[1]/div[1]/div/span")).click();
        method.wait(driverChrome);

    }

    @Test(priority = 10)
    public void clickChooseButton() throws InterruptedException {

        log.info("Uygun return day uçuşunun seçilmesi.");
        method.wait(driverChrome);
        Thread.sleep(1000);
        WebElement searchbutton=driverChrome.findElement(By.id("tooltipTarget_0"));
        searchbutton.click();
        method.wait(driverChrome);
        //Search "Seç" butonuna basılıp reservasyon sayfasına geçilmesinin kontrolü
        WebElement reservationcontrol=driverChrome.findElement(By.xpath("//*[@id=\"passenger-form\"]/div[2]/div[1]"));
        Assert.assertNotNull(reservationcontrol);
    }

    //Son olarak açtığımız sayfamızı driver.quit diyerek kapatıyoruz
   @Test(priority = 11)
    public void Down() throws Exception{
        log.info("Browser kapatılması.");
        driverChrome.quit();
        //driverFirefox.quit();
    }






    }

