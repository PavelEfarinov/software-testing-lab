package ru.itmo.scs.pages;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.itmo.scs.Constants;
import ru.itmo.scs.Context;
import ru.itmo.scs.exceptions.InvalidPropertiesException;
import ru.itmo.scs.utils.Properties;

import java.util.ArrayList;
import java.util.List;

public abstract class WebTest {
    protected static final Logger logger = Logger.getLogger(LoginTest.class);

    public Context context;
    public List<WebDriver> driverList;

    @BeforeEach
    public void setUp() {
        context = new Context();
        driverList = new ArrayList<>();
        try {
            Properties.getInstance().reading(context);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        }

//        if (context.getGeckodriver() != null) {
//            System.setProperty(Constants.WEBDRIVER_FIREFOX_DRIVER, context.getGeckodriver());
//            driverList.add(new FirefoxDriver());
//        }
        if (context.getChromedriver() != null) {
            System.setProperty(Constants.WEBDRIVER_CHROME_DRIVER, context.getChromedriver());
            driverList.add(new ChromeDriver());
        }
        if (driverList.isEmpty()) throw new InvalidPropertiesException();
    }

    @AfterEach
    public void tearDown() {
        driverList.forEach(WebDriver::quit);
    }
}
