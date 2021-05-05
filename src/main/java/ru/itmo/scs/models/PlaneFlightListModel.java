package ru.itmo.scs.models;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.itmo.scs.pages.HomePage;
import ru.itmo.scs.pages.PlaneFlightsPage;

public class PlaneFlightListModel implements ListModel{
    private HomePage homePageCached;
    private PlaneFlightsPage planeFlightsPageCached;
    private final RouteModel trainRouteModel;
    private WebDriver webDriver;

    public PlaneFlightListModel(RouteModel route) {
        trainRouteModel = route;
    }

    public void setWebDriver(WebDriver webDriver) {
        homePageCached = null;
        planeFlightsPageCached = null;
        this.webDriver = webDriver;
    }

    private HomePage getHomePage() {
        if (homePageCached == null || !homePageCached.isPageDisplayed()) {
            homePageCached = PageFactory.initElements(webDriver, HomePage.class);
            homePageCached.init();
        }
        return homePageCached;
    }

    private PlaneFlightsPage getBusRidesPage() {
        if (planeFlightsPageCached == null || !planeFlightsPageCached.isPageDisplayed()) {
            planeFlightsPageCached = getHomePage().openPlaneFlights(trainRouteModel);
            planeFlightsPageCached.init();
        }
        return planeFlightsPageCached;
    }

    @Override
    public int countRidesInList() {
        return getBusRidesPage().getRidesFromPage().size();
    }
}
