package org.nuvepro.container.chrome.nodes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nuvepro.common.BaseTasksClass;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ChromeNode3 extends BaseTasksClass {

    private static Logger log = LogManager.getLogger(ChromeNode3.class.getName());

    private WebDriver driver = null;

    public void setupLocalDriver() {

        // Only to test the setup, local driver will be used.

        System.out.println(ChromeNode3.class.getSimpleName() + ": Initializing DRIVER in local system.");

        driver = getDriver(properties().getProperty("browser"));

    }

    @BeforeClass
    public void setupRemoteDriver() {

        System.out.println(ChromeNode3.class.getSimpleName() +
                ": Initializing REMOTE WEBDRIVER to run the tests remotely in Docker Containers.");

        try {

            driver = getRemoteDriver(new URL(properties().getProperty("huburl")), getChromeCapabilities());

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }

    }

    @Test(enabled = true)
    public void checkAllUrls() {

        System.out.println("\n\nNumber of sites to check: " + getHTMLLinks().size());

        for (int i = 0; i < getHTMLLinks().size(); i++) {

            System.out.println("\n\n" + ChromeNode3.class.getSimpleName() + ": Loading Main URL: " + getHTMLLinks().get(i));

            driver.get(getHTMLLinks().get(i));

            System.out.println(ChromeNode3.class.getSimpleName() + ": Checking different URL in Main for response.");

            checkSiteLinks(driver, ChromeNode3.class.getSimpleName(), getHTMLLinks().get(i));

        }

    }

    @AfterTest
    public void tearDown() {

        System.out.println(ChromeNode3.class.getSimpleName() + ": After running test, closing the driver.");

        driver.quit();

    }

}
