package com.habibe.hotelautomationmaven.model;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationAddSeleniumTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Habibe\\Desktop\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void testAddReservation() throws Exception {
        // 1. Giriş yap
        driver.get("http://localhost:8080/HotelAutomationMaven/login.jsp");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // 2. Rezervasyonlar sayfasına git
        driver.get("http://localhost:8080/HotelAutomationMaven/reservations.jsp");

        // 3. Yeni rezervasyon formunda müşteri ve oda select
        WebElement customerSelect = driver.findElement(By.name("customer_id"));
        String selectedCustomer = customerSelect.findElements(By.tagName("option")).get(0).getText();

        WebElement roomSelect = driver.findElement(By.name("room_id"));
        String selectedRoom = roomSelect.findElements(By.tagName("option")).get(0).getText();

        // Tarihleri bugünden başlat, çıkış ertesi gün
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        String checkin = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String checkout = tomorrow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 4. Formu doldur
        customerSelect.findElements(By.tagName("option")).get(0).click();
        roomSelect.findElements(By.tagName("option")).get(0).click();

        driver.findElement(By.name("checkin")).sendKeys(checkin);
        driver.findElement(By.name("checkout")).sendKeys(checkout);

        WebElement statusSelect = driver.findElement(By.name("status"));
        statusSelect.findElement(By.cssSelector("option[value='reserved']")).click();

        // 5. Submit butonuna tıklamadan önce görünür yap
        WebElement addBtn = driver.findElement(By.cssSelector("button.btn-warning[type='submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);

        Thread.sleep(3000); // Bekle

        // 6. Rezervasyon tablosunda müşteri ve oda adı görünüyor mu kontrol et
        String page = driver.getPageSource();
        Assertions.assertTrue(page.contains(selectedCustomer) && page.contains(selectedRoom),
                "Yeni rezervasyon listede bulunamadı!");

        
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }
}
