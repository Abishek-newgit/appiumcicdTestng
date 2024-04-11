package debugging;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class InstallIOS {

    static Properties props;

    public static void main(String[] args) throws IOException {

        props = new Properties();
        String propFileName = "./resources/config.properties";
        props.load(new FileInputStream(propFileName));

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName", "iOS");
        caps.setCapability("udid", "BE48A664-5468-430D-9EE0-4E3C5BCB22C9");
        URL appUrl = InstallIOS.class.getResource(props.getProperty("iOSAppLocation"));
        System.out.println(appUrl);
        caps.setCapability("app", appUrl);
        caps.setCapability("bundleId", props.getProperty("iOSBundleId"));


        }

}
