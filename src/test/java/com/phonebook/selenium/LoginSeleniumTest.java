package com.phonebook.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginSeleniumTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // для CI/CD, можно убрать для отладки
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeEach
    void navigateToLoginPage() {
        driver.get("http://localhost:8080/phonebook/login");
    }

    @Test
    @Order(1)
    void testLoginPageLoads() {
        String title = driver.getTitle();
        assertTrue(title.contains("Login") || title.contains("login"));
    }

    @Test
    @Order(2)
    void testLoginWithValidCredentials() {
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("admin");
        passwordField.sendKeys("admin123");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("/userList"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("userList"));
    }

    @Test
    @Order(3)
    void testLoginWithInvalidCredentials() {
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("wrongUser");
        passwordField.sendKeys("wrongPass");
        loginButton.click();

        WebElement errorMessage = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".error"))
        );
        assertNotNull(errorMessage);
        assertTrue(errorMessage.getText().contains("Invalid"));
    }

    @Test
    @Order(4)
    void testRememberMeCheckboxExists() {
        WebElement rememberCheckbox = driver.findElement(By.name("remember"));
        assertNotNull(rememberCheckbox);
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}