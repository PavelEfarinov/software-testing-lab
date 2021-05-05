package ru.itmo.scs.models.filters.arrival;

import ru.itmo.scs.models.TrainRideModel;
import ru.itmo.scs.models.TrainRidesListModel;
import ru.itmo.scs.models.filters.TrainRideFilter;

import java.util.ArrayList;
import java.util.Comparator;

public class TrainRideArrivalSortDescFilter implements TrainRideFilter {

    @Override
    public ArrayList<TrainRideModel> applyFilter(ArrayList<TrainRideModel> inputCollection) {
        var result = new ArrayList<>(inputCollection);
        result.sort(Comparator.comparing(a -> ((TrainRideModel) a).arrivalTime).reversed().thenComparing(a -> ((TrainRideModel) a).trainName));
        return result;
    }

    @Override
    public void applyFilter(TrainRidesListModel ridesPage) {
        ridesPage.sortByArrivalDesc();
    }
}
