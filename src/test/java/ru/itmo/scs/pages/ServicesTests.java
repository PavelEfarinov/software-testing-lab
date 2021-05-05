package ru.itmo.scs.pages;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServicesTests extends WebTest {

    public static Stream<By> ticketsTestSource() {
        return Stream.of(
                HomePage.busTicketsButtonLocator,
                HomePage.etrainTicketsButtonLocator,
                HomePage.planeTicketsButtonLocator,
                HomePage.trainTicketsButtonLocator
        );
    }

    public static Stream<Arguments> serviceTestSource() {
        return Stream.of(
                Arguments.of(HomePage.toursTicketsButtonLocator, "https://tours.tutu.ru/"),
                Arguments.of(HomePage.eventsTicketsButtonLocator, "https://go.tutu.ru/"),
                Arguments.of(HomePage.hotelsButtonLocator, "https://hotel.tutu.ru/")
        );
    }

    @ParameterizedTest
    @MethodSource("ticketsTestSource")
    public void ticketsTest(By buttonLocator) {
        driverList.forEach(webDriver -> {
            webDriver.manage().window().maximize();
            var homePage = PageFactory.initElements(webDriver, HomePage.class);
            homePage.init();
            var button = webDriver.findElement(buttonLocator);
            button.click();
            checkActive(button);
        });
    }

    public static void checkActive(WebElement element) {
        var className = element.getAttribute("class");
        assertTrue(className.contains("tab_active"), "Expected element to be active, but it's class was " + className);
    }

    @ParameterizedTest
    @MethodSource("serviceTestSource")
    public void serviceTest(By buttonLocator, String url) {
        driverList.forEach(webDriver -> {
            webDriver.manage().window().maximize();
            var homePage = PageFactory.initElements(webDriver, HomePage.class);
            homePage.init();
            var button = webDriver.findElement(buttonLocator);
            button.click();
            assertEquals(url, webDriver.getCurrentUrl(), "Was expecting different URL");
        });
    }
}
