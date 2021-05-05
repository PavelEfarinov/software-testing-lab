package ru.itmo.scs.pages;

import org.junit.jupiter.api.Test;
import ru.itmo.scs.models.UserModel;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by i.isaev on 14.04.2021.
 */
public class LoginTest extends  WebTest{

    @Test
    public void loginTest() {
        driverList.forEach(webDriver -> {
            webDriver.manage().window().maximize();
            UserModel user = new UserModel(webDriver);
            user.email = "pavelefarinov@gmail.com";
            user.password = "Ce8LQrXcSXhn6wA";
            user.login();
            assertTrue(user.isLoggedIn(), "The user was not logged in");
            user.logout();
            assertFalse(user.isLoggedIn(), "The user was logged in");
        });
    }
}
