package ru.itmo.scs.models;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.itmo.scs.pages.BusRidesPage;
import ru.itmo.scs.pages.HomePage;

public class BusRideListModel implements ListModel {
    private HomePage homePageCached;
    private BusRidesPage busRidesPageCached;
    private final RouteModel trainRouteModel;
    private WebDriver webDriver;

    public BusRideListModel(RouteModel route) {
        trainRouteModel = route;
    }

    public void setWebDriver(WebDriver webDriver) {
        homePageCached = null;
        busRidesPageCached = null;
        this.webDriver = webDriver;
    }

    private HomePage getHomePage() {
        if (homePageCached == null || !homePageCached.isPageDisplayed()) {
            homePageCached = PageFactory.initElements(webDriver, HomePage.class);
            homePageCached.init();
        }
        return homePageCached;
    }

    private BusRidesPage getBusRidesPage() {
        if (busRidesPageCached == null || !busRidesPageCached.isPageDisplayed()) {
            busRidesPageCached = getHomePage().openBusRides(trainRouteModel);
            busRidesPageCached.init();
        }
        return busRidesPageCached;
    }

    @Override
    public int countRidesInList() {
        return getBusRidesPage().getRidesFromPage().size();
    }
}
