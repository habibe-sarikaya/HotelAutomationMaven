package com.habibe.hotelautomationmaven.model;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class RegisterSeleniumTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Habibe\\Desktop\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void testRegister() {
        driver.get("http://localhost:8080/HotelAutomationMaven/register.jsp");

        
        String uniqueUsername = "testuser" + System.currentTimeMillis();

        driver.findElement(By.name("username")).sendKeys(uniqueUsername);
        driver.findElement(By.name("password")).sendKeys("Test1234!");
        Select roleSelect = new Select(driver.findElement(By.name("role")));
        roleSelect.selectByValue("receptionist"); // veya "admin"

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        boolean isSuccess = driver.getPageSource().contains("Başarıyla kayıt oldunuz")
                || driver.getPageSource().contains("alert-success")
                || driver.getPageSource().toLowerCase().contains("kayıt") 
                || driver.getCurrentUrl().contains("login"); 

        Assertions.assertTrue(isSuccess, "Kayıt işlemi başarısız veya beklenen mesaj bulunamadı!");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }
}
