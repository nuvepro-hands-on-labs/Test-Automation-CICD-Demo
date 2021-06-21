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

    @BeforeClass
    public void setupLocalDriver() {

        // Only to test the setup, local driver will be used.

        System.out.println(ChromeNode1.class.getSimpleName() + ": Initializing DRIVER in local system.");

        driver = getDriver(properties().getProperty("browser"));

        System.out.println(ChromeNode1.class.getSimpleName() + ": Loading: " + getHTMLLinks().get(link));

        driver.get(getHTMLLinks().get(link));

    }


    public void setupRemoteDriver() {

        System.out.println(ChromeNode1.class.getSimpleName() +
                ": Initializing REMOTE WEBDRIVER to run the tests remotely in Docker Containers.");

        try {

            driver = getRemoteDriver(new URL(properties().getProperty("huburl")), getChromeCapabilities());

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }

        System.out.println(ChromeNode1.class.getSimpleName() + ": Loading: " + getHTMLLinks().get(link));

        driver.get(getHTMLLinks().get(link));

    }

    @Test(enabled = true)
    public void chromeNode1() {

        System.out.println(ChromeNode1.class.getSimpleName() + ": Waiting for Base URL to load.");

        // System.out.println(ChromeNode1.class.getSimpleName() + ": Base URL Title: " + driver.getTitle());

        System.out.println(ChromeNode1.class.getSimpleName() + ": Checking base URL response.");

        if (checkSiteLinks(driver, ChromeNode1.class.getSimpleName(), getHTMLLinks().get(link))) {

            System.out.println(ChromeNode1.class.getSimpleName() + ": Opening link: "
                    + driver.findElement(By.xpath("//a[contains(@href,'http://')]")).getText());

            driver.findElement(By.tagName("a")).click();

            System.out.println(ChromeNode1.class.getSimpleName()
                    + ": Checking response of other links in Nuvepro page.");

            checkSiteLinks(driver, ChromeNode1.class.getSimpleName(), driver.getCurrentUrl());

        } else {

            System.out.println(ChromeNode1.class.getSimpleName() + ": Failed to load base URL.");

            Assert.fail();

        }

        sleepTime(ChromeNode1.class.getSimpleName(), 10);


    }

    @AfterTest
    public void tearDown() {

        System.out.println(ChromeNode1.class.getSimpleName() + ": After running test, closing the driver.");

        driver.quit();

    }

}
