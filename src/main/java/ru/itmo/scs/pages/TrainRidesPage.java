package ru.itmo.scs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.itmo.scs.models.TrainRideModel;
import ru.itmo.scs.models.RouteModel;
import ru.itmo.scs.utils.RideDurationConverter;
import ru.itmo.scs.utils.RussianMonthConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TrainRidesPage extends Page {

    public static final By trainRouteListLocator = new By.ByXPath("//div/div//div[contains(@class,'l-train_list')]");
    public static final By trainRouteListHeaderLocator = new By.ByXPath("./div/div/div/div/div[contains(@class,'table_header')]");

    public static final By trainRouteTrainHeaderLocator = new By.ByXPath("./div[contains(@class,'col-1')]/span/span");
    public static final By trainRouteDepartureHeaderLocator = new By.ByXPath("./div[contains(@class,'col-3')]/div/span");
    public static final By trainRouteArrivalHeaderLocator = new By.ByXPath("./div[contains(@class,'col-4')]/div/span");
    public static final By trainRouteDurationHeaderLocator = new By.ByXPath("./div[contains(@class,'col-5')]/span/span");

    public static final By trainRouteTrainLocator = new By.ByXPath("./div[contains(@class,'col-1')]//span[contains(@class,'train_number_link')]");
    public static final By trainRouteTrainRatingLocator = new By.ByXPath("./div[contains(@class,'col-2')]//div[@data-ti='user_rating_value']");
    public static final By trainRouteDepartureStationLocator = new By.ByXPath("./div[contains(@class,'col-2')]//span[@data-ti='route_departure_station']/a");
    public static final By trainRouteArrivalStationLocator = new By.ByXPath("./div[contains(@class,'col-2')]//span[@data-ti='route_arrival_station']/a");
    public static final By trainRouteDepartureTimeLocator = new By.ByXPath("./div[contains(@class,'col-3')]//div[contains(@class,'departure_time')]");
    public static final By trainRouteArrivalTimeLocator = new By.ByXPath("./div[contains(@class,'col-4')]//span[contains(@class,'schedule_time')]");
    public static final By trainRouteArrivalDateLocator = new By.ByXPath("./div[contains(@class,'col-4')]/div/div[1]/span");
    public static final By trainRouteDurationLocator = new By.ByXPath("./div[contains(@class,'col-5')]//span[contains(@class,'route_time')]");

    private WebElement trainRouteList;
    private WebElement trainRouteListHeader;

    public TrainRidesPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getTrainNameHeader() {
        return trainRouteListHeader.findElement(trainRouteTrainHeaderLocator);
    }

    public WebElement getRideDepartureHeader() {
        return trainRouteListHeader.findElement(trainRouteDepartureHeaderLocator);
    }

    public WebElement getRideArrivalHeader() {
        return trainRouteListHeader.findElement(trainRouteArrivalHeaderLocator);
    }

    public WebElement getRideDurationHeader() {
        return trainRouteListHeader.findElement(trainRouteDurationHeaderLocator);
    }

    public void init() {
        (new WebDriverWait(driver, 120)).until(
                ExpectedConditions.urlContains("poezda"));
        (new WebDriverWait(driver, 120)).until(
                ExpectedConditions.presenceOfElementLocated(trainRouteListLocator));
        trainRouteList = driver.findElement(trainRouteListLocator);
        trainRouteListHeader = trainRouteList.findElement(trainRouteListHeaderLocator);
    }

    public List<WebElement> getRidesFromPage(){
        return trainRouteList.findElements(new By.ByXPath("//div/div/div[contains(@class,'b-train__schedule__train_card')]"));
    }

    public ArrayList<TrainRideModel> getRideModelsFromPage(RouteModel route) {
        var rideDate = LocalDateTime.ofInstant(route.date.toInstant(), ZoneId.systemDefault());
        var elements = getRidesFromPage();
        var models = new ArrayList<TrainRideModel>(elements.size());

        for (WebElement element : elements) {
            var model = new TrainRideModel();
            model.rideDuration = RideDurationConverter.rideDurationStringToDuration(element.findElement(trainRouteDurationLocator).getText());

            var departureString = element.findElement(trainRouteDepartureTimeLocator).getText();
            model.departureTime = LocalDateTime.of(
                    rideDate.getYear(),
                    rideDate.getMonth(),
                    rideDate.getDayOfMonth(),
                    Integer.parseInt(departureString.split(":")[0]),
                    Integer.parseInt(departureString.split(":")[1]));

            var arrivalString = element.findElement(trainRouteArrivalTimeLocator).getText();
            try {
                var arrivalDate = element.findElement(trainRouteArrivalDateLocator).getText();
                model.arrivalTime = LocalDateTime.of(
                        rideDate.getYear(),
                        RussianMonthConverter.convertMonthToMonthNumber(arrivalDate.split(" ")[1]),
                        Integer.parseInt(arrivalDate.split(" ")[0]),
                        Integer.parseInt(arrivalString.split(":")[0]),
                        Integer.parseInt(arrivalString.split(":")[1]));
            } catch (NoSuchElementException e) {
                // arrives at the same day
                model.arrivalTime = LocalDateTime.of(
                        rideDate.getYear(),
                        rideDate.getMonth(),
                        rideDate.getDayOfMonth(),
                        Integer.parseInt(arrivalString.split(":")[0]),
                        Integer.parseInt(arrivalString.split(":")[1]));
            }

            model.trainName = element.findElement(trainRouteTrainLocator).getText();
            try {
                model.trainRating = element.findElement(trainRouteTrainRatingLocator).getText();
            } catch (NoSuchElementException e) {
                // no train rating
                model.trainRating = null;
            }
            model.destination = element.findElement(trainRouteArrivalStationLocator).getText();
            model.origin = element.findElement(trainRouteDepartureStationLocator).getText();
            models.add(model);
        }
        return models;
    }

    public boolean isPageDisplayed() {
        return driver.getCurrentUrl().contains("https://www.tutu.ru/poezda/");
    }
}
