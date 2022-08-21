import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Bu sınıf Utils sınıfından miras almıştır.
 * properties dosyası içerisinde bulunan testimiz için dışarıdan aldığımız parametreleri kullanmak için get fonksiyonlarımız bu sınıf içerisindedir.
 */

//Bu sınıf tüm gerekli path'leri verdiğimiz ChromeDriver'a bağlantı sağladığımız fonksiyonu barındıran sınıf
public class Connection extends Utils{
    Utils util=new Utils();

    public Connection(){

        util.ConfigFileReader();
    }
    
    public String url() {

        //ChromeDriver.exe yolu ve gerekli url veriliyor
        String url = "https://www.enuygun.com/";
        return url;
    }
    public String path(){
        Path resourceDirectory = Paths.get("src","test","resources","resources","binary");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        return absolutePath;
    }

    public String getorigin(){
        return util.origin();
    }
    public String getdepartureDay(){

        return util.departureDay();
    }

    public String getReturnDay(){

        return util.returnday();
    }

    public String getDestination(){

        return util.destination();
    }


    public String getProvider(){
        return util.provider();
    }

    public boolean getIsDirect(){
        return util.isDirect();
    }
}

