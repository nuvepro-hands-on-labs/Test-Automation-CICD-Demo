package org.nuvepro.common;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nuvepro.helperclasses.ClassFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTasks {

    private static Logger log = LogManager.getLogger(BaseTasks.class.getName());

    private WebDriver driver;

    public static String mvnBrowser, testBrowser;

    private ClassFactory classFactory = new ClassFactory(driver);

    private WebDriverWait wait;

    public WebDriver getDriver(String browserName) {

        getTestBrowser(browserName);

        driver = classFactory.getDriverClass(testBrowser);

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);

        return driver;

    }

    public WebDriver getRemoteDriver(URL remoteUrl, DesiredCapabilities capabilities) {

        System.out.println("Remote URL: " + remoteUrl);

        RemoteWebDriver driver = new RemoteWebDriver(remoteUrl, capabilities);

        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);

        return driver;

    }

    public DesiredCapabilities getChromeCapabilities() {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        ChromeOptions options = new ChromeOptions();

        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        return capabilities;

    }

    public DesiredCapabilities getFirefoxCapabilities() {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        FirefoxOptions options = new FirefoxOptions();

        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);

        return capabilities;

    }

    public String getTestBrowser(String browserName) {

        mvnBrowser = System.getProperty("browser");

        if (mvnBrowser == null) {

            testBrowser = browserName;

        } else {

            testBrowser = mvnBrowser;
        }

        return testBrowser;

    }

    public Properties properties() {

        Properties properties = new Properties();

        try (FileInputStream inputFile = new FileInputStream(
                System.getProperty("user.dir") + "/resources/properties.properties")) {

            properties.load(inputFile);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return properties;

    }

    public void sleepTime(String nodeName, int timeToWaitInMilliSecs) {

        try {

            System.out.println(nodeName + ": Pausing execution for " + timeToWaitInMilliSecs / 1000 + "s");

            Thread.sleep(timeToWaitInMilliSecs);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    }

    public void sleepTime_CountDownTimer(String nodeName, int timeToWaitInSeconds) {

        System.out.println(nodeName + ": Pausing execution for " + timeToWaitInSeconds + "s.");

        System.out.println(nodeName + ": Next execution count down timer started.");

        try {

            for (int i = timeToWaitInSeconds; i > 0; i--) {

                if (i % 60 == 0) {

                    System.out.println(nodeName + ": Execution resumes in " + i + "s...");

                }

                Thread.sleep(1000);

            }

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    }

    public void clearInSecurePage(WebDriver driver) {

        Actions actions = new Actions(driver);

        actions.sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys(Keys.ENTER).build().perform();

    }

    public WebElement getElement(WebElement element) {

        return element;

    }

}
