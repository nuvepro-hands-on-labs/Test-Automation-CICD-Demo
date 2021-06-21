package org.nuvepro.common;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.nuvepro.helperclasses.ClassFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTasksClass {

    private static Logger log = LogManager.getLogger(BaseTasksClass.class.getName());

    private WebDriver driver;

    public static String mvnBrowser, testBrowser;

    private ClassFactory classFactory = new ClassFactory(driver);

    private WebDriverWait wait;

    public WebDriver getDriver(String browserName) {

        getTestBrowser(browserName);

        driver = classFactory.getDriverClass(testBrowser);

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        return driver;

    }

    public WebDriver getRemoteDriver(URL remoteUrl, DesiredCapabilities capabilities) {

        System.out.println("Remote URL: " + remoteUrl);

        RemoteWebDriver driver = new RemoteWebDriver(remoteUrl, capabilities);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

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

    public void sleepTime(String nodeName, int timeToWaitInSeconds) {

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

    public ArrayList<String> getHTMLLinks() {

        XSSFWorkbook workBook = null;

        ArrayList<String> activeLabUrls = new ArrayList<String>();

        DataFormatter formatter = new DataFormatter();

        try (FileInputStream inputFile = new FileInputStream(
                System.getProperty("user.dir") + "/resources/datarepo/urls.xlsx")) {

            workBook = new XSSFWorkbook(inputFile);

            XSSFSheet sheet = workBook.getSheet("urls");

            int rowCount = sheet.getPhysicalNumberOfRows();

            for (int i = 0; i < rowCount - 1; i++) {

                activeLabUrls.add(formatter.formatCellValue(sheet.getRow(i + 1).getCell(0)));

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

        return activeLabUrls;

    }

    public boolean checkSiteLinks(WebDriver driver, String nodeName, String link) {

        driver.get(link);

        boolean linkStatus = false;

        int linkCount = 0;

        ArrayList<WebElement> urls = new ArrayList<WebElement>();

        urls.addAll(driver.findElements(By.tagName("a")));

        HttpURLConnection huc = null;

        System.out.println("\n\n" + nodeName + ": Total links to check response: " + urls.size());

        checkHTTPResponse:
        for (int i = 0; i < urls.size(); i++) {

            linkCount++;

            try {

                if (urls.get(i).getAttribute("href").startsWith("http")) {

                    System.out.println(nodeName + ": " + linkCount + ": Checking response of: "
                            + urls.get(i).getAttribute("href"));

                    huc = (HttpURLConnection) new URL(urls.get(i).getAttribute("href")).openConnection();

                    huc.setRequestMethod("HEAD");

                    huc.connect();

                    if (huc.getResponseCode() >= 400) {

                        System.err.println(nodeName + ": Response Failed!!");

                        linkStatus = false;

                        // break checkHTTPResponse;

                    } else {

                        System.out.println(nodeName + ": Response OK");

                        linkStatus = true;

                    }

                }

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        return linkStatus;

    }

    public void clearInSecurePage(WebDriver driver) {

        Actions actions = new Actions(driver);

        actions.sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys(Keys.ENTER).build().perform();

    }

    public WebElement getElement(WebElement element) {

        return element;

    }

}
