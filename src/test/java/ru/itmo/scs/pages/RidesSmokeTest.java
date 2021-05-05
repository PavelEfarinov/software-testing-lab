package ru.itmo.scs.pages;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.itmo.scs.models.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RidesSmokeTest extends WebTest {
    public static RouteModel userRoute= new RouteModel();

    @BeforeAll
    static void initRoute()
    {
        userRoute.destination = "Москва";
        userRoute.origin = "Санкт-Петербург";
        userRoute.setRandomDate();
    }

    public static Stream<ListModel> ridesTestSource() {
        return Stream.of(
                new BusRideListModel(userRoute),
                new PlaneFlightListModel(userRoute),
                new EtrainRideListModel(userRoute),
                new TrainRidesListModel(userRoute)
        );
    }

    @ParameterizedTest
    @MethodSource("ridesTestSource")
    public void ridesSmokeTest(ListModel model) {
        driverList.forEach(webDriver -> {
            webDriver.manage().window().maximize();
            model.setWebDriver(webDriver);
            assertTrue(model.countRidesInList() > 0, "Expected to find any available routes.");
        });
    }
}
