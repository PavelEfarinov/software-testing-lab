package ru.itmo.scs.models.filters;

import ru.itmo.scs.models.TrainRideModel;
import ru.itmo.scs.models.TrainRidesListModel;

public interface TrainRideFilter extends Filter<TrainRideModel>{
    void applyFilter(TrainRidesListModel ridesModel);
}
