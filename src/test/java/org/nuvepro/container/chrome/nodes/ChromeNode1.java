package org.nuvepro.container.chrome.nodes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nuvepro.common.BaseTasksClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ChromeNode1 extends BaseTasksClass {

    private static Logger log = LogManager.getLogger(ChromeNode1.class.getName());

    private WebDriver driver = null;

    private int link = 0;


    public void setupLocalDriver() {

        // Only to test the setup, local driver will be used.

        System.out.println(ChromeNode1.class.getSimpleName() + ": Initializing DRIVER in local system.");

        driver = getDriver(properties().getProperty("browser"));

        System.out.println(ChromeNode1.class.getSimpleName() + ": Loading: " + getHTMLLinks().get(link));

        driver.get(getHTMLLinks().get(link));

    }

    @BeforeClass
    public void setupRemoteDriver() {

        System.out.println(ChromeNode1.class.getSimpleName() +
                ": Initializing REMOTE WEBDRIVER to run the tests remotely in Docker Containers.");

        try {

            driver = getRemoteDriver(new URL(properties().getProperty("huburl")), getChromeCapabilities());

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }

    }

    @Test(enabled = true)
    public void chromeNode1_BaseURL() {

        System.out.println(ChromeNode1.class.getSimpleName() + ": Loading: " + properties().getProperty("baseurl"));

        driver.get(properties().getProperty("baseurl"));

        System.out.println(ChromeNode1.class.getSimpleName() + ": Checking base URL response.");

        driver.findElement(By.tagName("a")).click();

        System.out.println("Opened Nuvepro URL.");

    }

    @Test(enabled = true)
    public void chromeNode1_NuveURL() {

        for (int i = 0; i < getHTMLLinks().size(); i++) {

            System.out.println(ChromeNode1.class.getSimpleName() + ": Loading: " + getHTMLLinks().get(i));

            driver.get(getHTMLLinks().get(i));

            System.out.println(ChromeNode1.class.getSimpleName() + ": Checking Nuvepro URL response.");

            checkSiteLinks(driver, ChromeNode1.class.getSimpleName(), getHTMLLinks().get(i));

        }

    }

    @AfterTest
    public void tearDown() {

        System.out.println(ChromeNode1.class.getSimpleName() + ": After running test, closing the driver.");

        driver.quit();

    }

}
