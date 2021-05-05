package ru.itmo.scs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.itmo.scs.models.RouteModel;

/**
 * Created by i.isaev on 14.04.2021.
 */
public class HomePage extends Page {

    private final By authContainerLocator = By.className("auth");

    private WebElement authContainer;

    private WebElement trainTicketsButton;
    private WebElement planeTicketsButton;
    private WebElement busTicketsButton;
    private WebElement etrainTicketsButton;

    public static final By trainTicketsButtonLocator = new By.ByXPath("//div/div/div[@data-content='train']");
    public static final By planeTicketsButtonLocator = new By.ByXPath("//div/div/div[@data-content='avia']");
    public static final By busTicketsButtonLocator = new By.ByXPath("//div/div/div[@data-content='bus']");
    public static final By etrainTicketsButtonLocator = new By.ByXPath("//div/div/div[@data-content='etrain']");
    public static final By toursTicketsButtonLocator = new By.ByXPath("//div/div/a[@data-type='tours']");
    public static final By eventsTicketsButtonLocator = new By.ByXPath("//div/div/a[@data-type='trip']");
    public static final By hotelsButtonLocator = new By.ByXPath("//div/div/a[@data-type='hotels']");

    public WebElement getLoggedUserEmail() {
        return authContainer.findElement(By.className("j-logged-user"));
    }

    public WebElement getLoginButton() {
        return authContainer.findElement(By.className("j-link-login"));
    }

    public WebElement getLogoutButton() {
        return authContainer.findElement(By.className("j-link-logout"));
    }

    public WebElement getLoginBox() {
        return driver.findElement(By.className("j-login-login"));
    }

    public WebElement getEmailTextField() {
        return getLoginBox().findElement(By.name("login"));
    }

    public WebElement getPasswordTextField() {
        return getLoginBox().findElement(By.name("password"));
    }

    private void fillCommonTrainRouteInfo(RouteModel route, WebElement searchForm) {
        var departure = searchForm.findElement(By.className("j-station_input_from"));
        var arrival = searchForm.findElement(By.className("j-station_input_to"));
        var date = searchForm.findElement(By.className("j-date_to"));

        departure.click();
        departure.sendKeys(route.origin);
        arrival.sendKeys(route.destination);

        date.sendKeys(route.getStringFromDate());

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        date.submit();
    }

    public TrainRidesPage openTrainRides(RouteModel route) {
        trainTicketsButton.click();
        var searchForm = driver.findElement(new By.ByXPath("//div/form/div[@Class='form']"));

        fillCommonTrainRouteInfo(route, searchForm);
        return PageFactory.initElements(driver, TrainRidesPage.class);
    }

    public PlaneFlightsPage openPlaneFlights(RouteModel route) {
        planeTicketsButton.click();
        var searchForm = driver.findElement(new By.ByXPath("//div[contains(@class,'j-avia_search_form')]/div"));
        var departure = searchForm.findElement(By.className("j-city_from"));
        var arrival = searchForm.findElement(By.className("j-city_to"));
        var date = searchForm.findElement(By.className("j-date_from"));
        var submitButton = searchForm.findElement(By.className("j-button-submit"));

        (new WebDriverWait(driver, 120)).until(ExpectedConditions.elementToBeClickable(departure));

        departure.sendKeys(route.origin);
        arrival.sendKeys(route.destination);

        date.sendKeys(route.getStringFromDate());

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        submitButton.click();

        return PageFactory.initElements(driver, PlaneFlightsPage.class);
    }

    public EtrainRidesPage openEtrainRides(RouteModel route) {
        etrainTicketsButton.click();

        var searchForm = driver.findElement(new By.ByXPath("//div[contains(@class, 'j-etrain_search_form')]//div/form/div[@Class='form']"));

        fillCommonTrainRouteInfo(route, searchForm);

        return PageFactory.initElements(driver, EtrainRidesPage.class);
    }

    public BusRidesPage openBusRides(RouteModel route) {
        busTicketsButton.click();

        var searchForm = driver.findElement(new By.ByXPath("//div[contains(@class,'j-bus_search_form')]/div[@data-ti='bus-form']"));
        var departure = searchForm.findElement(new By.ByXPath("//div[@data-ti='departure-suggest']//input"));
        var arrival = searchForm.findElement(new By.ByXPath("//div[@data-ti='arrival-suggest']//input"));
        var date = searchForm.findElement(new By.ByXPath("//input[@data-ti='date-input']"));
        var submitButton = searchForm.findElement(new By.ByXPath("//button[@data-ti='submit-button']"));

        arrival.sendKeys(route.destination);

        departure.sendKeys(route.origin);

        date.sendKeys(route.getStringFromDate());

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        submitButton.click();

        return PageFactory.initElements(driver, BusRidesPage.class);
    }

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void init() {
        driver.get("https://www.tutu.ru");
        authContainer = driver.findElement(authContainerLocator);
        planeTicketsButton = driver.findElement(planeTicketsButtonLocator);
        busTicketsButton = driver.findElement(busTicketsButtonLocator);
        etrainTicketsButton = driver.findElement(etrainTicketsButtonLocator);
        trainTicketsButton = driver.findElement(trainTicketsButtonLocator);
        (new WebDriverWait(driver, 120)).until(ExpectedConditions.visibilityOf(authContainer));
    }

    public boolean isPageDisplayed() {
        return driver.getCurrentUrl().equals("https://www.tutu.ru");
    }

}
