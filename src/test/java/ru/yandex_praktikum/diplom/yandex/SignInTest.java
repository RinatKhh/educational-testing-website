package ru.yandex_praktikum.diplom.yandex;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex_praktikum.diplom.*;

import java.time.Duration;


public class SignInTest {
    WebDriver driver;
    private HomePage homePage;
    private InputForm inputForm;
    private RegisterForm registerForm;
    private PasswordRecoveryForm passwordRecoveryForm;
    String accessToken = "";
    @Before
    public void setUp() {
        ConfigDriver configDriver = new ConfigDriver();
        configDriver.driverSetup("Yandex");
        driver = configDriver.driver;
        homePage = new HomePage(driver);
        inputForm = new InputForm(driver);
        registerForm = new RegisterForm(driver);
        passwordRecoveryForm = new PasswordRecoveryForm(driver);
        homePage.waitForLoadHomePage();
        // регистрация пользователя
        User user = new User(registerForm.email,registerForm.password,registerForm.name);
        accessToken = UserMethodApi.createUser(user).then().extract().path("accessToken");

    }
    @After
    public void testDown(){
        if (accessToken!=null) {
            UserMethodApi.DeleteUser(accessToken);
        }
        driver.quit();
    }
    @Test
    @DisplayName("Check SigIn button from homepage form")
    public void checkSigInFromHomePageSuccessfully() throws InterruptedException {
        homePage.clickLogoButton();
        homePage.clickSignInButton();
        inputForm.waitForLoadInputForm();
        inputForm.signIn(registerForm.email,registerForm.password);
        homePage.waitOrderPlaceButtonVisibility();
    }
    @Test
    @DisplayName("Check SigIn button from personal cabinet form")
    public void checkSigInFromPersonalCabinetFormSuccessfully() throws InterruptedException {
        homePage.clickLogoButton();
        homePage.clickPersonalCabinetButton();
        inputForm.waitForLoadInputForm();
        inputForm.waitForLoadSignInButton();
        inputForm.signIn(registerForm.email,registerForm.password);
        homePage.waitOrderPlaceButtonVisibility();
    }
    @Test
    @DisplayName("Check SigIn button from register form")
    public void checkSigInFromRegisterFormSuccessfully() throws InterruptedException {
        homePage.clickPersonalCabinetButton();
        inputForm.clickRegisterButton();
        registerForm.clickSignInButton();
        inputForm.waitForLoadInputForm();
        inputForm.signIn(registerForm.email,registerForm.password);
        homePage.waitOrderPlaceButtonVisibility();
    }
    @Test
    @DisplayName("Check SigIn button from recovery password form")
    public void checkSigInFromRecoveryPasswordFormSuccessfully() throws InterruptedException {
        homePage.clickPersonalCabinetButton();
        inputForm.clickRecoverPasswordButton();
        passwordRecoveryForm.clickSignInButton();
        inputForm.waitForLoadInputForm();
        inputForm.signIn(registerForm.email,registerForm.password);
        homePage.waitOrderPlaceButtonVisibility();
    }
}
