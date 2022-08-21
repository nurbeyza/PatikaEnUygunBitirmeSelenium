import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.junit.experimental.theories.Theories;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import java.util.List;
import org.junit.jupiter.api.DisplayName;


/** @noinspection Lombok*/
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
    @Test(priority = 0, description = "Parametrik alınan -origin- verisi ilgili inputa eklendi")
    public void  departureAdd() throws Exception {
        log.info("Deperture day parametresi alındı.");
        method.depertureDayAdd(driverChrome);

        Assert.assertNotNull(driverChrome.findElement(By.id("OriginInput")));

    }


    @Test(priority = 1, description = "Deperture Day için listeden ilk eleman seçildi")
    public void departureSelectInput()throws Exception {
        log.info("Deperture Day için listeden ilk eleman seçildi.");
        WebElement element=driverChrome.findElement(By.id("react-autowhatever-OriginInput-section-0-item-0"));
        Assert.assertNotNull(element);
        element.click();
    }


    @Test(priority = 2, description = "Parametrik alınan -destination- verisi ilgili inputa eklendi")
    public void  returnDayAdd() throws Exception {
        log.info("Return day parametresi alındı.");
        method.returnDayAdd(driverChrome);
        method.wait(driverChrome);

        Assert.assertNotNull(driverChrome.findElement(By.id("react-autowhatever-DestinationInput-section-0-item-0")));

    }

    @Test(priority = 3, description = "Return day için listenin ilk elemanı seçildi")
    public void returnSelectInput(){
        log.info("return day için listenin ilk elemanı seçildi.");
        WebElement element=driverChrome.findElement(By.id("react-autowhatever-DestinationInput-section-0-item-0"));
        Assert.assertNotNull(element);
        element.click();
    }

    @Test(priority = 4, description = "Parametrik olarak -depertureDay- verisi kullanılarak datepicker üzerinde seçim yapıldı")
    public void  departureDayClick() throws InterruptedException {

        log.info("Datepicker üzerinden bugünün tarihinin seçilmesi.");
        WebElement input=driverChrome.findElement(By.id("DepartureDate"));
        input.click();
        int day=Integer.parseInt(connection.getdepartureDay());

        String[] datalist=method.getDate(driverChrome,day);

        String month=method.getMonth(datalist[1]);

        List<WebElement> elements = driverChrome.findElements(By.xpath("//td[@class=\"CalendarDay CalendarDay_1 CalendarDay__default CalendarDay__default_2\"]"));
        List<WebElement> elements2 = driverChrome.findElements(By.xpath("//td[@class=\"CalendarDay CalendarDay_1 CalendarDay__default CalendarDay__default_2 CalendarDay__firstDayOfWeek CalendarDay__firstDayOfWeek_3\"]"));
        List<WebElement> elements3 = driverChrome.findElements(By.xpath("//td[@class=\"CalendarDay CalendarDay_1 CalendarDay__default CalendarDay__default_2 CalendarDay__lastDayOfWeek CalendarDay__lastDayOfWeek_3\"]\n"));

        //Listelerin doluluğunu kontrol ediyoruz.
        Assert.assertNotNull(elements);
        Assert.assertNotNull(elements2);
        Assert.assertNotNull(elements3);

        //Hafta içi günler için
        method.getDay(elements, datalist[0],month);

        //Haftanın ilk günlerini kontrol
        method.getDay(elements2,datalist[0],month);

        //Haftanın son gününü kontrol
        method.getDay(elements3,datalist[0],month);


        WebElement depertureElement=driverChrome.findElement(By.xpath("//*[@id=\"DepartureDate\"]"));
        String [] deperture=depertureElement.getAttribute("value").split(",");
        String [] depertureDay=deperture[0].split(" ");
        Assert.assertEquals(datalist[0],depertureDay[0]);
    }


    @Test(priority = 5 ,description = "Parametrik olarak -returnDay- verisi kullanılarak datepicker üzerinde seçim yapıldı")
    public void returnDayClick() throws InterruptedException {

        log.info("Datepicker üzeirnden dönüş günün seçilmesi.");
        WebElement input=driverChrome.findElement(By.id("ReturnDate"));
        input.click();
        int day= Integer.parseInt(connection.getdepartureDay())+Integer.parseInt(connection.getReturnDay());
        String[] datalist=method.getDate(driverChrome,day);


        String month=method.getMonth(datalist[1]);
        List<WebElement> elements = driverChrome.findElements(By.xpath("//td[@class=\"CalendarDay CalendarDay_1 CalendarDay__default CalendarDay__default_2\"]"));
        List<WebElement> elements2 = driverChrome.findElements(By.xpath("//td[@class=\"CalendarDay CalendarDay_1 CalendarDay__default CalendarDay__default_2 CalendarDay__firstDayOfWeek CalendarDay__firstDayOfWeek_3\"]"));
        List<WebElement> elements3 = driverChrome.findElements(By.xpath("//td[@class=\"CalendarDay CalendarDay_1 CalendarDay__default CalendarDay__default_2 CalendarDay__lastDayOfWeek CalendarDay__lastDayOfWeek_3\"]\n"));

        //Listelerin doluluğunu kontrol ediyoruz.
        Assert.assertNotNull(elements);
        Assert.assertNotNull(elements2);
        Assert.assertNotNull(elements3);

        //Hafta içi günler için
        method.getDay(elements, datalist[0],month);


        //Haftanın ilk günlerini kontrol
        method.getDay(elements2,datalist[0],month);


        //Haftanın son gününü kontrol
        method.getDay(elements3,datalist[0],month);

        WebElement returndate=driverChrome.findElement(By.xpath("//*[@id=\"ReturnDate\"]"));
        String [] returndatelist=returndate.getAttribute("value").split(",");
        String [] returndaylist=returndatelist[0].split(" ");
        Assert.assertEquals(datalist[0],returndaylist[0]);

    }

    @Test(priority = 6 ,description = "Ucuz bilet bul butonuna tıklandı")
    public void flightSearchButtonClick() throws InterruptedException {
        WebElement searchButton=driverChrome.findElement(By.xpath("//button[@class=\"primary-btn block\"]"));
        Assert.assertNotNull(searchButton);
        searchButton.click();
        method.waitelement(driverChrome,"//div[@class=\"flight-list-header combine roundTripHeader desktopHeader false\"]");
    }




    @Test(priority = 7, description = "Parametrik olarak alınan -isDireckt- verisine göre filtreleme yapıldı")
    public void clickTransferTypeFlight() throws InterruptedException {
        String type=null;
        log.info("Uygun transfer tipinin seçilmesi.");
        Assert.assertNotNull( driverChrome.findElement(By.xpath("//div[@class=\"flight-list-header combine roundTripHeader desktopHeader false\"]")));
        Boolean isDirect=connection.getIsDirect();
        WebElement hoverElement= driverChrome.findElement(By.xpath("//*[@id=\"SearchRoot\"]/div/div[2]/div[1]/div[4]/div/div[2]/div[2]/div/label[1]/span[2]"));
        WebElement isDirectElement=driverChrome.findElement(By.xpath("//*[@id=\"SearchRoot\"]/div/div[2]/div[1]/div[4]/div/div[2]/div[2]/div/label[1]/button"));
        WebElement notDirectElement=driverChrome.findElement(By.xpath("//*[@id=\"SearchRoot\"]/div/div[2]/div[1]/div[4]/div/div[2]/div[2]/div/label[1]"));
        method.transferTypeControl(isDirect,hoverElement,isDirectElement,driverChrome, notDirectElement);


        //Uçuş kartı üzerinden doğru filtreleme yapıldı mı? Kontrolü bu assert üzerinden yapılıyor.
        WebElement transfertype=driverChrome.findElement(By.xpath("//*[@id=\"SearchRoot\"]/div/div[2]/div[2]/div/div[2]/div/div[2]/div[1]/div[1]/div/div/div[1]/div[1]/label[1]/div[2]/div[2]/div/div[3]/div[3]"));
        if(isDirect.equals(Boolean.FALSE)){
             type="Aktarma";
             String [] Type=transfertype.getText().split(" ");
            Assert.assertEquals(Type[0], type);
        }
        else{
            type="Direkt Uçuş";
            Assert.assertEquals(transfertype.getText(),type);
        }


    }

    @Test(priority = 8, description = "Parametrik olarak alınan -provider- bilgisine göre deperture uçuşu seçildi")
    public void clickDepertureFlight() throws InterruptedException {

        log.info("Uygun deperture uçuşunun seçilmesi");
        String provider= connection.getProvider();
        method.chooseProvider(driverChrome, provider);
        method.wait(driverChrome);

        //Uygun uçuş bulunduktan sonra sayfanın başına scroll yapılıyor.
        method.upScroll(driverChrome);
        method.wait(driverChrome);
    }

    @Test(priority = 9,description = "Seçilen deperture uçuşuyla aynı pakette bulunan return uçuşu seçildi")
    public void clickReturnFlights() throws InterruptedException {

        WebElement returnfligt =driverChrome.findElement(By.xpath("//*[@id=\"SearchRoot\"]/div/div[2]/div[2]/div/div[2]/div/div[2]/div[1]/div[1]/div/div/div[1]/div[2]/label/div[2]"));
        returnfligt.click();
        Thread.sleep(1000);
        WebElement returnFlightActive=driverChrome.findElement(By.xpath("//*[@id=\"SearchRoot\"]/div/div[2]/div[2]/div/div[2]/div/div[2]/div[1]/div[1]/div/div/div[1]/div[2]/label/div[2]"));

        //Uçuş seçildiğinde sınıf isminin yanına "active" ekleniyor bu sayede kontrolü sağlanıyor.
        Assert.assertEquals(returnFlightActive.getAttribute("class"),"flight-item round-trip tr  active");
        method.wait(driverChrome);

    }

    @Test(priority = 10, description ="Seç butonuna tıklandı")
    public void clickChooseButton() throws InterruptedException {

        method.wait(driverChrome);
        //projede bazı noktalarda doğru olmadığı bilincinde olsamda "Thread.sleep" kullandım. Bunun sebebi diğer ait fonksiyonlarından istediğim verimi alamadım.
        Thread.sleep(1000);
        WebElement searchbutton=driverChrome.findElement(By.id("tooltipTarget_0"));
        searchbutton.click();
        method.wait(driverChrome);
        //Search "Seç" butonuna basılıp reservasyon sayfasına geçilmesinin kontrolü
        WebElement reservationcontrol=driverChrome.findElement(By.xpath("//*[@id=\"passenger-form\"]/div[2]/div[1]"));
        Assert.assertNotNull(reservationcontrol);
    }

    //Son olarak açtığımız sayfamızı driver.quit diyerek kapatıyoruz
   @Test(priority = 11, description = "Browser kapatıldı")
    public void Down() throws Exception{
        log.info("Browser kapatılması.");
        driverChrome.quit();

        //Firefox seçmek istersek üstte ki Chrome adımını kapatıp alttaki adım açılır.
        //driverFirefox.quit();
    }






    }

