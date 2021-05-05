package ru.itmo.scs.models.filters.name;

import ru.itmo.scs.models.TrainRideModel;
import ru.itmo.scs.models.TrainRidesListModel;
import ru.itmo.scs.models.filters.TrainRideFilter;

import java.util.ArrayList;
import java.util.Comparator;

public class TrainRideTrainNameSortAscFilter implements TrainRideFilter {

    @Override
    public ArrayList<TrainRideModel> applyFilter(ArrayList<TrainRideModel> inputCollection) {
        var result = new ArrayList<>(inputCollection);
        result.sort(Comparator.comparing(a -> a.trainName));
        return result;
    }

    @Override
    public void applyFilter(TrainRidesListModel ridesPage) {
        ridesPage.sortByTrainNameAsc();
    }
}
