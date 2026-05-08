package com.phonebook.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class RegisterSeleniumTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    static void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeEach
    void navigateToRegisterPage() {
        driver.get("http://localhost:8080/phonebook/register");
    }

    @Test
    void testRegisterNewUser() {
        String uniqueUser = "testuser_" + System.currentTimeMillis();

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement registerButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys(uniqueUser);
        passwordField.sendKeys("Test1234!");
        emailField.sendKeys(uniqueUser + "@test.com");
        registerButton.click();

        wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue(driver.getCurrentUrl().contains("login"));
    }

    @Test
    void testUsernameAlreadyExists() {
        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.sendKeys("admin");

        // Ждём AJAX-ответа
        wait.until(d -> d.findElement(By.id("usernameStatus")).getText().contains("taken"));

        WebElement status = driver.findElement(By.id("usernameStatus"));
        assertTrue(status.getText().contains("taken"));
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}