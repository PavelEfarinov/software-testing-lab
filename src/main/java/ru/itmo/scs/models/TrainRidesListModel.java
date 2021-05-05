package ru.itmo.scs.models;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.itmo.scs.pages.HomePage;
import ru.itmo.scs.pages.TrainCarPage;
import ru.itmo.scs.pages.TrainRidesPage;

import java.util.ArrayList;


public class TrainRidesListModel implements  ListModel{
    private HomePage homePageCached;
    private TrainRidesPage trainRidesPageCached;
    private final RouteModel routeModel;
    private WebDriver webDriver;

    public TrainRidesListModel(RouteModel route) {
        routeModel = route;
    }

    public void setWebDriver(WebDriver webDriver) {
        homePageCached = null;
        trainRidesPageCached = null;
        this.webDriver = webDriver;
    }

    public TrainRidesListModel(RouteModel route, WebDriver driver) {
        webDriver = driver;
        routeModel = route;
    }

    private HomePage getHomePage() {
        if (homePageCached == null || !homePageCached.isPageDisplayed()) {
            homePageCached = PageFactory.initElements(webDriver, HomePage.class);
            homePageCached.init();
        }
        return homePageCached;
    }

    private TrainRidesPage getTrainRidesPage() {
        if (trainRidesPageCached == null || !trainRidesPageCached.isPageDisplayed()) {
            trainRidesPageCached = getHomePage().openTrainRides(routeModel);
            trainRidesPageCached.init();
        }
        return trainRidesPageCached;
    }

    public ArrayList<TrainRideModel> getRoutesFromPage() {
        return getTrainRidesPage().getRideModelsFromPage(routeModel);
    }

    public TrainCarPage pickSeatsForRide(TrainRideModel ride) {
        var page = getTrainRidesPage();
        var models = getRoutesFromPage();
        var index = 0;
        for (index = 0; index < models.size(); index++) {
            var model = models.get(index);
            if (model.trainName.equals(ride.trainName)) {
                break;
            }
        }
        page.getRidesFromPage().get(index).findElement(By.className("action_button")).click();
        webDriver.switchTo().window((String) webDriver.getWindowHandles().toArray()[1]);
        var cartsPage = PageFactory.initElements(webDriver, TrainCarPage.class);
        cartsPage.init();
        return cartsPage;
    }

    public void sortByTrainNameAsc() {
        getTrainRidesPage().getRideDurationHeader().click();
        getTrainRidesPage().getTrainNameHeader().click();
    }

    public void sortByTrainNameDesc() {
        getTrainRidesPage().getRideDurationHeader().click();
        var header = getTrainRidesPage().getTrainNameHeader();
        header.click();
        header.click();
    }

    public void sortByDepartureAsc() {
        getTrainRidesPage().getTrainNameHeader().click();
        getTrainRidesPage().getRideDepartureHeader().click();
    }

    public void sortByDepartureDesc() {
        getTrainRidesPage().getTrainNameHeader().click();
        var header = getTrainRidesPage().getRideDepartureHeader();
        header.click();
        header.click();
    }

    public void sortByArrivalAsc() {
        getTrainRidesPage().getTrainNameHeader().click();
        getTrainRidesPage().getRideArrivalHeader().click();
    }

    public void sortByArrivalDesc() {
        getTrainRidesPage().getTrainNameHeader().click();
        var header = getTrainRidesPage().getRideArrivalHeader();
        header.click();
        header.click();
    }

    public void sortByDurationAsc() {
        getTrainRidesPage().getTrainNameHeader().click();
        getTrainRidesPage().getRideDurationHeader().click();
    }

    public void sortByDurationDesc() {
        getTrainRidesPage().getTrainNameHeader().click();
        var header = getTrainRidesPage().getRideDurationHeader();
        header.click();
        header.click();
    }

    @Override
    public int countRidesInList() {
        return getTrainRidesPage().getRidesFromPage().size();
    }
}