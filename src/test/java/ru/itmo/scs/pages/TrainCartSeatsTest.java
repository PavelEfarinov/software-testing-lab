package ru.itmo.scs.pages;

import org.junit.jupiter.api.Test;
import ru.itmo.scs.models.TrainRidesListModel;
import ru.itmo.scs.models.RouteModel;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainCartSeatsTest extends WebTest {
    @Test
    public void SeatsSelectionAvailable() {
        driverList.forEach(webDriver -> {
            webDriver.manage().window().maximize();

            var route = new RouteModel();
            route.origin = "Санкт-Петербург";
            route.destination = "Москва";
            route.setRandomDate();
            var trainRides = new TrainRidesListModel(route, webDriver);

            var models = trainRides.getRoutesFromPage();
            trainRides.pickSeatsForRide(models.get(0));
            assertTrue(webDriver.getCurrentUrl().contains("poezda/wizard/seats/"), "Was expecting the seats selection tab to be open.");
        });
    }
}
