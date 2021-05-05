package ru.itmo.scs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TrainCarPage extends Page {

    public final By carsListLocator = new By.ByXPath("//div[@data-ti='seats-page']/div/div[@data-ti='categories_list']/div/div[@data-ti='cars_list']");
    public final By passengersSettingsLocator = new By.ByXPath("//div[@data-ti='seats-page']/div/div[@data-ti='passengers-type']");
    public final By adultSettingsLocator = new By.ByXPath("./div[@data-ti='passengers-type-content']/div[@data-ti='passengers-type-full']");
    public final By childSettingsLocator = new By.ByXPath("./div[@data-ti='passengers-type-content']/div[@data-ti='passengers-type-child']");
    public final By babySettingsLocator = new By.ByXPath("./div[@data-ti='passengers-type-content']/div[@data-ti='passengers-type-baby']");

    private WebElement carsList;
    private WebElement passengersSettings;

    public TrainCarPage(WebDriver driver) {
        super(driver);
    }

    public void init() {
        (new WebDriverWait(driver, 120)).until(
                ExpectedConditions.urlContains("poezda/wizard/seats"));
        (new WebDriverWait(driver, 120)).until(
                ExpectedConditions.presenceOfElementLocated(carsListLocator));
        (new WebDriverWait(driver, 10)).until(
                ExpectedConditions.presenceOfElementLocated(passengersSettingsLocator));
        passengersSettings = driver.findElement(passengersSettingsLocator);
        carsList = driver.findElement(carsListLocator);
        (new WebDriverWait(driver, 10)).until(
                ExpectedConditions.visibilityOf(passengersSettings.findElement(childSettingsLocator)));
        (new WebDriverWait(driver, 10)).until(
                ExpectedConditions.visibilityOf(passengersSettings.findElement(adultSettingsLocator)));
        (new WebDriverWait(driver, 10)).until(
                ExpectedConditions.visibilityOf(passengersSettings.findElement(babySettingsLocator)));
        (new WebDriverWait(driver, 10)).until(
                ExpectedConditions.visibilityOf(carsList));
    }
}
