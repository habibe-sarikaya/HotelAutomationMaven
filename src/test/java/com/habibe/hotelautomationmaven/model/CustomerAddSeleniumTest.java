package com.habibe.hotelautomationmaven.model;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CustomerAddSeleniumTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Habibe\\Desktop\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void testAddCustomer() throws Exception {
        driver.get("http://localhost:8080/HotelAutomationMaven/login.jsp");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        driver.get("http://localhost:8080/HotelAutomationMaven/customers.jsp");

        String name = "TestAd" + System.currentTimeMillis();
        String surname = "TestSoyad";
        String phone = "555" + ((int) (Math.random() * 9000) + 1000);
        String email = "test" + System.currentTimeMillis() + "@example.com";

        // Sadece yeni müşteri ekleme formunu bulalım:
        WebElement form = driver.findElement(By.xpath("//form[input[@name='action' and @value='add']]"));
        form.findElement(By.name("name")).sendKeys(name);
        form.findElement(By.name("surname")).sendKeys(surname);
        form.findElement(By.name("phone")).sendKeys(phone);
        form.findElement(By.name("email")).sendKeys(email);

        WebElement addBtn = form.findElement(By.cssSelector("button.btn-success[type='submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);

        Thread.sleep(4000); // 4 saniye bekle, tarayıcıyı izle!

        // Sayfada müşteri ismini ve emailini ara
        String page = driver.getPageSource();
        Assertions.assertTrue(page.contains(name) && page.contains(email),
                "Yeni müşteri listede bulunamadı veya eklenemedi!");
    }




    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }
}
