package ru.itmo.scs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PlaneFlightsPage extends Page {
    private final By flightsListLocator = new By.ByXPath("//div[@data-ti='offers_result']");
    private final By flightsListItemLocator = new By.ByXPath("//div[@data-ti='offer-card']");
    private WebElement flightsList;

    public PlaneFlightsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void init() {
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(flightsListLocator));
        flightsList = driver.findElement(flightsListLocator);
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(flightsListItemLocator));
    }

    public List<WebElement> getRidesFromPage() {
        return flightsList.findElements(flightsListItemLocator);
    }

    public boolean isPageDisplayed() {
        return driver.getCurrentUrl().contains("https://www.avia.tutu.ru/offers/");
    }
}
