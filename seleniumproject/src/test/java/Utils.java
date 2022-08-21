import java.io.*;
import java.util.Properties;

public class Utils {

    public Properties properties;

    public String propertyFilePath = System.getProperty("user.dir") + "/src/test/resources/resources/properties/config.properties";

    //reading properties file
    public void ConfigFileReader() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("configuration.properties not found at " + propertyFilePath);
        }
    }


    public String origin(){
        String origin=properties.getProperty("origin");
        return origin;
    }

    public String departureDay(){
        String departureDay=properties.getProperty("departureDay");
        return departureDay;
    }

    public String returnday(){
        String returnDay=properties.getProperty(("returnDay"));
        return returnDay;
    }

    public String destination(){
        String destination=properties.getProperty("destination");
        return destination;
    }

    public String provider(){
        String provider=properties.getProperty("provider");
        return provider;
    }

    public boolean isDirect(){
        Boolean isDirect= Boolean.valueOf(properties.getProperty("isDirect"));
        return isDirect;
    }

}
