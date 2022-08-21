<h1 align="center"> Beyzanur Özer - Enuygun Selenium Projesi </h1>

> >##  ``` EnUygun Sitesi üzerinde aşağıda belirtilen case için bir web otomasyon projesi yapıldı.  ```

>Otomatize edilen test case ayrıntısız ve genel adımlar halinde aşağıda verilmiştir.:

* Adım 1: Paremetrik olarak alınan origin ve destination verileri ile nereden nereye bilgileri seçilir.
* Adım 2: Parametrik olarak alınan departureDay ve returnDay verileri ile uçuş tarihleri seçilir.
* Adım 3: "Ucuz Bilet Bul" butonuna tıklanır.
* Adım 4: Parametrik olarak alınan provider ve isDirect verileri ile bulunan uçuşlar seçilir.
* Adım 5: "Seç" butonuna tıklanır.

## Proje için kurulan yapı aşağıda belirtilmiştir.
> Test package altında oluşturulan yapı aşağıdaki gibidir.
# Java
* [Browser](#Browser)
* [Connection](#Connection)
* [Method](#Method)
* [TestRun](#TestRun)
* [Utils](#Utils)

# Resources
* [Binary](#Binary)
* [Properties](#Properties)

## Browser
Bu sınıf içerisinde kullanılan browser ile ilgili path ve property ayarlamaları yapıldı.

## Connection
Bu sınıf içerisinde genel olarak <b>Utils</b> sınıfı içerisinde okuduğumuz properties dosyasında bulunan verilerin get methodları bulunmaktadır. Connection sınıfı Utils sınıfından extends olmuştur. Bir yapıcı oluşturularak gerekli işlemler yapılmıştır.

💬<details>
<summary><h4>Örnek bir get methodunu görüntülemek için tıklayın</h4></summary>

```java
  public String getorigin(){
        return util.origin();
    }
```
</details>

## Method
Bu sınıf içeirisinde testlerimizde kullandığımız tüm methodlar bulunmaktadır. Kullanılan methodlar yorum satırlarıyla proje içerisinde detaylıca açıklanmıştır. örnek olarka bir kaç fonksiyon aşağıya bırakılmıştır.

💬<details>
<summary><h4>Parametrik olarak alınan "isDirect" bilgisine göre filtreleme yapan fonksiyonu görüntülemek için tıklayın</h4></summary>

```java
 public void transferTypeControl(Boolean isDirect, WebElement hoverElement, WebElement isDirectElement, WebDriver driver, WebElement notDirectElement) {

        if (isDirect.equals(Boolean.TRUE)) {
            Actions action = new Actions(driver);
            action.moveToElement(hoverElement).moveToElement(isDirectElement).click().build().perform();

        } else {
            notDirectElement.click();
        }
    }
```
</details>

💬<details>
<summary><h4>Parametrik olarak alınan "depertureDay" bilgisine göre istenilen gün kadar sonrasını seçen fonksiyonu görüntülemek için tıklayın</h4></summary>

```java
 public String[] getDate(WebDriver driver, int day) {
        SimpleDateFormat data_format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, day);
        String date = data_format.format(calendar.getTime());
        String[] datalist = date.split("/");
        return datalist;
        }
```
</details>


## TestRun
Tüm test adımlarıı bu sınıf içeirisinde yazıldı. Toplamda BeforeClass dahil bir şekilde 13 tane testimiz bulunuyor. Detaylı açıklamalar proje içerisinde verildi.

💬<details>
<summary><h4>Örnek olarak bir testi görüntülemek için tıklayın</h4></summary>

```java
@Test(priority = 7)
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
```
</details>


## Utils
Bu sınıf içerisinde properties doyasının okunmasını sağlanır.

## Binary
Bu package altında kullandığımız browsera ait exe dosyaları bulunmaktadır.

## Properties
Bu package içerisinde parametrik olarak aldığımız verilerin olduğu dosya bulunmaktadır.

##Ek bilgiler

> #Not1: 
Raporlama için allure report kullanılmıştır. Test çalıştıktan sonra otomatik olarak "Seleniumproject" altında <b>Allure-results</b> dosyası oluşacaktır. Terminalden projesenin bulunduğu dizinde <b>Allure serve allure-results</b> yazarak test raporlarına ulaşılabilir.

> #Not2: 
Dışarıdan parametrik olarak alınan "provider" verisini stabil etmek oldukça zordu. Verilen test case üzerinde havayolu ismi ile aynı iken projede çok farklı veriler ile karşılaştım. Kontrol edilirken boşluk ve - işaretlerine uygun şekilde verinşn verilmesi oldukça önemlidir.

