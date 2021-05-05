package ru.itmo.scs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class EtrainRidesPage extends Page {
    public EtrainRidesPage(WebDriver driver) {
        super(driver);
    }

    private final By ridesTableLocator = new By.ByXPath("//div[contains(@class,'l-content')]//div[@id='timetable']//table");
    private WebElement ridesTable;

    @Override
    public void init() {
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(ridesTableLocator));
        ridesTable = driver.findElement(ridesTableLocator);
    }

    public List<WebElement> getRidesFromPage(){
        return ridesTable.findElements(new By.ByXPath("//tr"));
    }
    public boolean isPageDisplayed() {
        return driver.getCurrentUrl().contains("/rasp.php");
    }

}
