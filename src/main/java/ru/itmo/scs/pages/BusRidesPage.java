package ru.itmo.scs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BusRidesPage extends Page {
    public BusRidesPage(WebDriver driver) {
        super(driver);
    }

    private final By ridesListLocator = new By.ByXPath("//div[@data-ti='offers_list']");
    private WebElement ridesList;

    @Override
    public void init() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignore) {
        }
        (new WebDriverWait(driver, 120)).until(d->!isPageDisplayed());
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(ridesListLocator));
        ridesList = driver.findElement(ridesListLocator);
    }

    public List<WebElement> getRidesFromPage() {
        return ridesList.findElements(new By.ByXPath("//div[contains(@class,'index__offer___1pMh_')]"));
    }

    public boolean isPageDisplayed() {
        return driver.getCurrentUrl().contains("https://bus.tutu.ru/расписание_автобусов/");
    }

}
