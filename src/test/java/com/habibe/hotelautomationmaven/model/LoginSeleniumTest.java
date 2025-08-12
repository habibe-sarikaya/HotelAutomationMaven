package com.habibe.hotelautomationmaven.model;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginSeleniumTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Habibe\\Desktop\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void testLogin() {
        driver.get("http://localhost:8080/HotelAutomationMaven/login.jsp");

        // Kullanıcı adı ve şifreyi gir
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        
        Assertions.assertTrue(driver.getPageSource().contains("Dashboard")
                || driver.getCurrentUrl().contains("dashboard")
                || driver.getPageSource().contains("logout") 
                , "Login başarısız veya beklenen sayfa gelmedi!");

       
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }
}
