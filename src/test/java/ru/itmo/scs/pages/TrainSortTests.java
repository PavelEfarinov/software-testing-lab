package ru.itmo.scs.pages;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.itmo.scs.models.RouteModel;
import ru.itmo.scs.models.TrainRideModel;
import ru.itmo.scs.models.TrainRidesListModel;
import ru.itmo.scs.models.filters.TrainRideFilter;
import ru.itmo.scs.models.filters.arrival.TrainRideArrivalSortAscFilter;
import ru.itmo.scs.models.filters.arrival.TrainRideArrivalSortDescFilter;
import ru.itmo.scs.models.filters.departure.TrainRideDepartureSortAscFilter;
import ru.itmo.scs.models.filters.departure.TrainRideDepartureSortDescFilter;
import ru.itmo.scs.models.filters.duration.TrainRideDurationSortAscFilter;
import ru.itmo.scs.models.filters.duration.TrainRideDurationSortDescFilter;
import ru.itmo.scs.models.filters.name.TrainRideTrainNameSortAscFilter;
import ru.itmo.scs.models.filters.name.TrainRideTrainNameSortDescFilter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainSortTests extends WebTest {
    public static Stream<TrainRideFilter> trainRidesSortsTrainsTestSource() {
        return Stream.of(
                new TrainRideTrainNameSortAscFilter(),
                new TrainRideTrainNameSortDescFilter(),
                new TrainRideDepartureSortAscFilter(),
                new TrainRideDepartureSortDescFilter(),
                new TrainRideArrivalSortAscFilter(),
                new TrainRideArrivalSortDescFilter(),
                new TrainRideDurationSortAscFilter(),
                new TrainRideDurationSortDescFilter()
        );
    }

    @ParameterizedTest
    @MethodSource("trainRidesSortsTrainsTestSource")
    public void TrainRidesSortsTrainsTest(TrainRideFilter filter) {
        driverList.forEach(webDriver -> {
            webDriver.manage().window().maximize();

            var route = new RouteModel();
            route.origin = "Санкт-Петербург";
            route.destination = "Москва";
            route.setRandomDate();

            var trainRides = new TrainRidesListModel(route, webDriver);

            filter.applyFilter(trainRides);
            var models = trainRides.getRoutesFromPage();
            assertTrue(filter.isFiltered(models),
                    "Was expecting the collection to be filtered, but it was not:\n" +
                            models.stream()
                                    .map(TrainRideModel::toString)
                                    .collect(Collectors.joining("\n")));
        });
    }
}
