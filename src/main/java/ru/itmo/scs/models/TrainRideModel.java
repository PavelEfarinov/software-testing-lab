package ru.itmo.scs.models;

import java.time.Duration;
import java.time.LocalDateTime;

public class TrainRideModel extends RouteModel {
    public Duration rideDuration;
    public String trainName;
    public LocalDateTime departureTime;
    public LocalDateTime arrivalTime;
    public String trainRating;

    @Override
    public String toString() {
        return trainName + " | " + trainRating + " | " + origin + " " + departureTime + " -> " + destination + " " + arrivalTime;
    }
}
