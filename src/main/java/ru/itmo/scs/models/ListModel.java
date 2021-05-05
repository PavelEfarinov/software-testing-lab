package ru.itmo.scs.models;

import org.openqa.selenium.WebDriver;

public interface ListModel {
    int countRidesInList();
    void setWebDriver(WebDriver webDriver);
}
