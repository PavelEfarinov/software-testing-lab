package ru.itmo.scs.models;

import com.github.javafaker.Faker;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

public class RouteModel {
    public static final String[] Cities = {
            "Москва",
            "Санкт-Петербург",
            "Казань",
            "Нижний Новгород",
            "Адлер",
            "Воронеж",
            "Екатеринбург",
            "Киев",
            "Минск"};
    public String origin;
    public String destination;
    public Date date;

    public void setRandomCities() {
        origin = Cities[new Random().nextInt(Cities.length)];
        do {
            destination = Cities[new Random().nextInt(Cities.length)];
        } while (origin.equals(destination));
    }

    public void setRandomDate() {
        var now = new Date();
        date = new Faker().date().between(Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(60 * 60 * 24 * 30 * 1)));
    }

    public String getStringFromDate() {
        var day = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).getDayOfMonth();
        var month = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).getMonthValue();
        var year = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).getYear();
        return (day < 10 ? "0" + day : day) + "." + (month < 10 ? "0" + month : month) + "." + year;
    }

}
