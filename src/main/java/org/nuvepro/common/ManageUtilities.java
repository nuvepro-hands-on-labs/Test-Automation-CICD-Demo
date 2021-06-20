package org.nuvepro.common;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class ManageUtilities {

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
                System.getProperty("user.dir") + "/resources/datarepo/htmllinks.xlsx")) {

            workBook = new XSSFWorkbook(inputFile);

            XSSFSheet sheet = workBook.getSheet("links");

            int rowCount = sheet.getPhysicalNumberOfRows();

            for (int i = 0; i < rowCount - 1; i++) {

                activeLabUrls.add(formatter.formatCellValue(sheet.getRow(i + 1).getCell(0)));

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

        return activeLabUrls;

    }

    public void checkSiteLinks(WebDriver driver, String nodeName, String link) {

        driver.get(link);

        HttpURLConnection huc = null;

        try {

            System.out.println("Checking if the URL is active: " + link);

            huc = (HttpURLConnection) new URL(link).openConnection();

            huc.setRequestMethod("HEAD");

            huc.connect();

            if (huc.getResponseCode() >= 400) {

                System.out.println(
                        "\n\n" + nodeName + ": Link is BROKEN!! Response Code: " + huc.getResponseCode() + "\n\n");

            } else {

                System.out.println(
                        "\n\n" + nodeName + ": Link is WORKING. Response Code: " + huc.getResponseCode() + "\n\n");

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public DesiredCapabilities getChromeCapabilities() {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        ChromeOptions options = new ChromeOptions();

        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        return capabilities;

    }

    public DesiredCapabilities getFirefoxCapabilities() {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        FirefoxOptions options = new FirefoxOptions();

        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);

        return capabilities;

    }

}
