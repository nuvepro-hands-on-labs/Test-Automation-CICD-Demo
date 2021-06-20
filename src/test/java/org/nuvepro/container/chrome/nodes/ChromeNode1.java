package org.nuvepro.container.chrome.nodes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nuvepro.common.BaseTasks;
import org.nuvepro.common.ManageUtilities;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ChromeNode1 extends BaseTasks {

    private static Logger log = LogManager.getLogger(ChromeNode1.class.getName());

    private WebDriver driver = null;

    private ManageUtilities manage = new ManageUtilities();

    private int link = 0;

    public void setupLocalDriver() {

        // Only to test the setup, local driver will be used.

        System.out.println(ChromeNode1.class.getSimpleName() + ": Initializing DRIVER in local system.");

        driver = getDriver(properties().getProperty("browser"));

        driver.get(properties().getProperty("testURL"));

    }

    @BeforeClass
    public void setupRemoteDriver() {

        System.out.println(ChromeNode1.class.getSimpleName() + ": Initializing REMOTE WEBDRIVER to run the tests remotely in Docker Containers.");

        try {

            driver = getRemoteDriver(new URL(manage.getHTMLLinks().get(link)), getChromeCapabilities());

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }

        System.out.println(ChromeNode1.class.getSimpleName() + ": URL to load: " + manage.getHTMLLinks().get(link));

        driver.get(manage.getHTMLLinks().get(link));


    }

    @Test(enabled = true)
    public void chromeNode1() {

        System.out.println(ChromeNode1.class.getSimpleName() + ": Waiting for URL to load in ChromeNode1.");

        System.out.println(ChromeNode1.class.getSimpleName() + ": " + driver.getTitle());

        // Modify the link here. It should be the clickable link given here.

        manage.checkSiteLinks(driver, ChromeNode1.class.getSimpleName(), manage.getHTMLLinks().get(link));

        sleepTime(ChromeNode1.class.getSimpleName(), 10);


    }

    @AfterTest
    public void tearDown() {

        System.out.println(ChromeNode1.class.getSimpleName() + ": After running test, closing the driver.");

        driver.quit();

    }

}
