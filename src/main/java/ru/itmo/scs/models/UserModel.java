package ru.itmo.scs.models;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.itmo.scs.pages.HomePage;

public class UserModel {
    public String email;
    public String password;
    private HomePage homePageCached;
    private final WebDriver webDriver;

    public UserModel(WebDriver driver) {
        webDriver = driver;
    }


    public HomePage getHomePage() {
        if (homePageCached == null || !homePageCached.isPageDisplayed()) {
            homePageCached = PageFactory.initElements(webDriver, HomePage.class);
            homePageCached.init();
        }
        return homePageCached;
    }


    public boolean isLoggedIn() {
        var loggedUserEmail = getHomePage().getLoggedUserEmail();
        return loggedUserEmail.isDisplayed() && loggedUserEmail.getText().equals(email);
    }

    public void login() {
        var homePage = getHomePage();
        var loginButton = homePage.getLoginButton();
        loginButton.click();
        var loginBox = homePage.getLoginBox();
        (new WebDriverWait(webDriver, 120)).until(ExpectedConditions.visibilityOf(loginBox));

        var emailTextBox = homePage.getEmailTextField();
        emailTextBox.clear();
        emailTextBox.sendKeys(email);

        var passwordTextBox = homePage.getPasswordTextField();
        passwordTextBox.clear();
        passwordTextBox.sendKeys(password);
        passwordTextBox.submit();
        (new WebDriverWait(webDriver, 120)).until(ExpectedConditions.invisibilityOf(loginBox));
    }

    public void logout() {
        var logoutButton = getHomePage().getLogoutButton();
        (new WebDriverWait(webDriver, 120)).until(ExpectedConditions.elementToBeClickable(logoutButton));
        logoutButton.click();
        (new WebDriverWait(webDriver, 10)).until((ExpectedCondition<Boolean>) d -> !logoutButton.isDisplayed());
    }
}
