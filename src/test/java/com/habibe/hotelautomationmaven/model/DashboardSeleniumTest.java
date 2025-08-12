package com.habibe.hotelautomationmaven.model;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class DashboardSeleniumTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Habibe\\Desktop\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void testDashboardVisibleAfterLogin() throws Exception {
        // Go to login page and log in
        driver.get("http://localhost:8080/HotelAutomationMaven/login.jsp");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Thread.sleep(1500); // Short wait for redirection

        String pageSource = driver.getPageSource();

        // Welcome message and role panel check
        Assertions.assertTrue(pageSource.contains("Welcome, admin!"), "Welcome message not found!");
        Assertions.assertTrue(
            pageSource.contains("You are in the admin panel.") ||
            pageSource.contains("You are in the receptionist panel."),
            "Role description not found!"
        );

        // Dashboard card titles check
        Assertions.assertTrue(pageSource.contains("Room Management"), "Room Management card not found!");
        Assertions.assertTrue(pageSource.contains("Customer Management"), "Customer Management card not found!");
        Assertions.assertTrue(pageSource.contains("Reservation Management"), "Reservation Management card not found!");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }
}
