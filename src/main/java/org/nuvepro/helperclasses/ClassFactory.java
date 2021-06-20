package org.nuvepro.helperclasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

public class ClassFactory {

    private static Logger log = LogManager.getLogger(ClassFactory.class.getName());

    private WebDriver driver;

    public ClassFactory(WebDriver driver) {

        this.driver = driver;

    }

    public WebDriver getDriverClass(String driverName) {

        log.info("Requested driver is for: " + driverName);

        if (driverName.equalsIgnoreCase("chrome")) {

            System.setProperty("webdriver.chrome.driver",
                    System.getProperty("user.dir") + "/resources/drivers/chromedriver.exe");

            ChromeOptions options = new ChromeOptions();

            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

            // options.addArguments("--headless");

            options.setAcceptInsecureCerts(true);

            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

            options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);



            log.info("Returning ChromeDriver.");

            return new ChromeDriver(options);

        }

        if (driverName.equalsIgnoreCase("firefox")) {

            System.setProperty("webdriver.gecko.driver",
                    System.getProperty("user.dir") + "/resources/drivers/geckodriver.exe");

            FirefoxOptions options = new FirefoxOptions();

            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

            log.info("Returning FirefoxDriver.");

            return new FirefoxDriver(options);

        }

        if (driverName.equalsIgnoreCase("msedge")) {

            System.setProperty("webdriver.edge.driver",
                    System.getProperty("user.dir") + "/resources/drivers/msedgedriver.exe");

            EdgeOptions options = new EdgeOptions();

            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

            log.info("Returning EdgeDriver.");

            return new EdgeDriver(options);

        }

        return null;

    }

}

