package ru.itmo.scs.models;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.itmo.scs.pages.EtrainRidesPage;
import ru.itmo.scs.pages.HomePage;

public class EtrainRideListModel implements ListModel {

    private HomePage homePageCached;
    private EtrainRidesPage trainRidesPageCached;
    private final RouteModel trainRouteModel;
    private WebDriver webDriver;

    public EtrainRideListModel(RouteModel route) {
        trainRouteModel = route;
    }

    public void setWebDriver(WebDriver webDriver) {
        homePageCached = null;
        trainRidesPageCached = null;
        this.webDriver = webDriver;
    }

    private HomePage getHomePage() {
        if (homePageCached == null || !homePageCached.isPageDisplayed()) {
            homePageCached = PageFactory.initElements(webDriver, HomePage.class);
            homePageCached.init();
        }
        return homePageCached;
    }

    private EtrainRidesPage getEtrainRidesPage() {
        if (trainRidesPageCached == null || !trainRidesPageCached.isPageDisplayed()) {
            trainRidesPageCached = getHomePage().openEtrainRides(trainRouteModel);
            trainRidesPageCached.init();
        }
        return trainRidesPageCached;
    }

    @Override
    public int countRidesInList() {
        return getEtrainRidesPage().getRidesFromPage().size();
    }
}
