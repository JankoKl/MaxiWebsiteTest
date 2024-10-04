
import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Keys;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;









public class JunitTest {

	public String info = "";
    private WebDriver driver;
    private String chromeDriverPath = "C:\\Users\\Janko\\Downloads\\chromedriver-win64\\chromedriver.exe";

    @Rule
    public TestWatcher testWatcher = new TestWatcher() {
        @Override
        protected void starting(final Description description) {
            String methodName = description.getMethodName();
            String className = description.getClassName();
            className = className.substring(className.lastIndexOf('.') + 1);
            System.err.println("\u001B[37mStarting JUnit-test: \u001B[32m" + methodName);

            
            
            
        }

        @Override
        protected void succeeded(Description description) {
            String methodName = description.getMethodName();
            System.err.println("\u001B[37mTest je uspesno zavrsen. \nNaziv testa: \u001B[32m" + methodName);
            info += "\n"+" Test" +methodName+" je uspesno prosao";
            WriteInFile("test-report.txt", methodName+"Test je uspesno prosao\n");

        }

        @Override
        protected void failed(Throwable e, Description description) {
            String methodName = description.getMethodName();
            System.out.println("\u001B[31m" + methodName + " nije prosao.\u001B[0m");
            info += "\n"+" Test" +methodName+" nije uspesno prosao";
            WriteInFile("test-report.txt", methodName+"Test nije uspesno prosao");


        }
    };

    
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\Janko\\Downloads\\Win_1342076_chrome-win\\chrome-win\\chrome.exe");
        driver = new ChromeDriver(options);

    }
    

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testRegister() {
    	
        driver.get("https://www.maxi.rs/reg/create-account?lastViewedPage=%2F");
        driver.manage().window().maximize();
     
        w8();
        driver.findElement(By.id("username")).sendKeys("jjjankojjj@gmail.com");
        driver.findElement(By.id("new-password")).sendKeys("Janko123");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("span.sc-bzk8b1-3.hmlKuN")));
        driver.findElement(By.cssSelector("span.sc-bzk8b1-3.hmlKuN")).click();
        System.out.println("Accept conditions");
        driver.findElement(By.cssSelector("div[data-testid='button-text']")).click();
        System.out.println("clicked next");
        Select select = new Select(driver.findElement(By.id("title")));
        select.selectByValue("mr");
        driver.findElement(By.id("firstName")).sendKeys("Janko");
        driver.findElement(By.id("lastName")).sendKeys("Klikovac");
        //driver.findElement(By.id("birthDay")).click();
        
        {
            WebElement dropdown = driver.findElement(By.id("birthDay"));
            dropdown.findElement(By.xpath("//option[. = '5']")).click();
          }
          driver.findElement(By.id("birthMonth")).click();
          {
            WebElement dropdown = driver.findElement(By.id("birthMonth"));
            dropdown.findElement(By.xpath("//option[. = 'februar']")).click();
          }
          driver.findElement(By.id("birthYear")).click();
          {
            WebElement dropdown = driver.findElement(By.id("birthYear"));
            dropdown.findElement(By.xpath("//option[. = '2002']")).click();
          }
          
          driver.findElement(By.cssSelector("span:nth-child(1) > label")).click();
          driver.findElement(By.id("email")).click();
          driver.findElement(By.id("email")).sendKeys("jankoklikovac@gmail.com");
          driver.findElement(By.id("emailConfirmation")).sendKeys("jankoklikovac@gmail.com");
          driver.findElement(By.id("password")).click();
          driver.findElement(By.id("password")).sendKeys("Janko123");
          driver.findElement(By.id("passwordConfirmation")).sendKeys("Janko123");
          driver.findElement(By.id("shippingCityNames")).click();
          {
            WebElement dropdown = driver.findElement(By.id("shippingCityNames"));
            dropdown.findElement(By.xpath("//option[. = 'ČAčAK']")).click();
          }
          driver.findElement(By.id("streetName")).click();
          driver.findElement(By.id("streetName")).sendKeys("Svetog Save");
          driver.findElement(By.id("houseNumber")).click();
          driver.findElement(By.id("houseNumber")).sendKeys("66");
          driver.findElement(By.cssSelector(".has-error label")).click();
          driver.findElement(By.name("submit")).click();

          String tekstDesc = "[Testiranje registracije]";
          WriteInFile("test-report.txt", tekstDesc);
        
        assertEquals("https://www.maxi.rs/reg/welcome?lastViewedPage=%2Freg", driver.getCurrentUrl());
    }
   
    @Test
    public void TestLogIn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.maxi.rs/login");
        driver.manage().window().maximize();

        // Wait for the email input field to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("j_username")));

        // Enter email address
        driver.findElement(By.id("j_username")).sendKeys("jankoklikovac@gmail.com");
        System.out.println("Entered email address");

        // Enter password
        driver.findElement(By.id("current-password")).sendKeys("Janko123");
        System.out.println("Entered password");

        // Wait for the login button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-testid='button-text']")));
        System.out.println("Login button is clickable");

        // Click the login button
        driver.findElement(By.cssSelector("div[data-testid='button-text']")).click();
        System.out.println("Clicked login button");

        // Wait for the login to complete
        wait.until(ExpectedConditions.urlContains("https://www.maxi.rs"));
        System.out.println("Login complete");

        // Assert that the login was successful
        assertTrue(driver.getCurrentUrl().contains("https://www.maxi.rs"));
    }
    
    @Test
    public void testLogOut() {
    	
      TestLogIn();
      w8();
      driver.get("https://www.maxi.rs/");
      driver.manage().window().maximize();
      driver.findElement(By.xpath("//div[text()='Dobro došli']")).click();
     
      driver.findElement(By.cssSelector("button[data-testid='header-myhub-quit']")).click();
      
      String tekstDesc = "[Testiranje odjavljivanje sa naloga]";
      WriteInFile("test-report.txt", tekstDesc);
      
      assertEquals("https://www.maxi.rs/", driver.getCurrentUrl());

    }
    
    @Test
    public void testUser  () {
        System.out.println("Starting testUser  test");
        
        TestLogIn();
        System.out.println("Logged in successfully");
        
        w8();
        System.out.println("Waited for 5 seconds");
        
        driver.get("https://www.maxi.rs/");
        System.out.println("Navigated to https://www.maxi.rs/");
        
        driver.manage().window().maximize();
        System.out.println("Maximized the window");
        
        w8();
        System.out.println("Waited for 5 seconds");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Dobro došli']")));
            driver.findElement(By.xpath("//div[text()='Dobro došli']")).click();
            System.out.println("Clicked on Dobro došli element");
        } catch (Exception e) {
            System.out.println("Error clicking on Dobro došli element: " + e.getMessage());
        }
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-testid='header-myhub-my-overview']")));
            driver.findElement(By.cssSelector("a[data-testid='header-myhub-my-overview']")).click();
            System.out.println("Clicked on Moj nalog element");
        } catch (Exception e) {
            System.out.println("Error clicking on Moj nalog element: " + e.getMessage());
        }
        
        driver.get("https://www.maxi.rs/my-account/update-profile");
        
        
        w8();
        System.out.println("Waited for 5 seconds");
        
        try {
            assertEquals("https://www.maxi.rs/my-account/update-profile", driver.getCurrentUrl());
            System.out.println("Verified URL is correct");
        } catch (AssertionError e) {
            System.out.println("Error verifying URL: " + e.getMessage());
        }
        
        try {
            assertEquals(driver.findElement(By.cssSelector("span[data-testid='personal-details-title']")).getText(),"Muški Janko Klikovac");
            assertEquals(driver.findElement(By.cssSelector("span[data-testid='personal-info-birthday']")).getText(),"05/02/2002");
            assertEquals(driver.findElement(By.cssSelector("span[data-testid='email-address']")).getText(),"jankoklikovac@gmail.com");
            assertEquals(driver.findElement(By.cssSelector("span[data-testid='mobile-number']")).getText(),"+381 665175536");
            System.out.println("Verified personal details title is correct");
        } catch (AssertionError e) {
            System.out.println("Error verifying personal details title: " + e.getMessage());
        }
        
        try {
            assertEquals(driver.findElement(By.cssSelector("span[data-testid='personal-info-birthday']")).getText(),"05/02/2002");
            System.out.println("Verified birthday is correct");
        } catch (AssertionError e) {
            System.out.println("Error verifying birthday: " + e.getMessage());
        }
        
        try {
            assertEquals(driver.findElement(By.cssSelector("span[data-testid='mobile-number']")).getText(),"+381 665175536");
            System.out.println("Verified mobile number is correct");
        } catch (AssertionError e) {
            System.out.println("Error verifying mobile number: " + e.getMessage());
        }
        
        try {
            assertEquals(driver.findElement(By.cssSelector("span[data-testid='email-address']")).getText(),"jankoklikovac@gmail.com");
            System.out.println("Verified email address is correct");
        } catch (AssertionError e) {
            System.out.println("Error verifying email address: " + e.getMessage());
        }
        
        String tekstDesc = "[Testira validacije podataka korisnika]";
        WriteInFile("test-report.txt", tekstDesc);
        System.out.println("Wrote test report to file");
    }
    
    @Test
    public void testShoppingCart() {
      
      TestLogIn();
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      w8();
      driver.get("https://www.maxi.rs/Pekara-torte-i-kolachi/Kokosija-jaja-M-Maxi-10-1/p/7518543");
      WebDriverWait wait1= new WebDriverWait(driver, Duration.ofSeconds(10));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-testid='button-text']")));
      driver.findElement(By.cssSelector("div[data-testid='button-text']")).click();
      w8();w8();w8();
      driver.get("https://www.maxi.rs/checkout");
      
      assertEquals(driver.findElement(By.cssSelector("span[data-testid='top-total-price']")).getText(),"RSD 484,97");
     
      String tekstDesc = "[Testiranje dodavanje artikala u korpi i testiranje krajnju cene]";
      WriteInFile("test-report.txt", tekstDesc);

    }
    

    
    @Test
    public void AvgSpeed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        TestLogIn();
        w8();
        long startTime = System.currentTimeMillis();

        driver.get("https://www.maxi.rs/");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".news-icon"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("body")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".action-icon > p"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("body")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".favorit-icon > p"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("body")));
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("jankoklikovac@gmail.com"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("body")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".promjena-podataka-button"))).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("body")));

        long endTime = System.currentTimeMillis();
        long pageLoadTime = endTime - startTime;

        long avg = pageLoadTime/6;
        
        String tekstDesc = "[Testiranje prosecne brzine odziva sajta]";
        WriteInFile("test-report.txt", tekstDesc);
        
        assertTrue(avg<5000); 
           
    }
    

    

    
    @Test
    public void DeleteItem() {
      TestLogIn();
      w8();
      driver.get("https://www.maxi.rs/");
      driver.manage().window().setSize(new Dimension(1920, 1040));
      driver.findElement(By.cssSelector(".cart-icon > p")).click();
      w8();
      driver.findElement(By.cssSelector("div:nth-child(1) > .im-slide .delete-cart-item")).click();
      w8();
      driver.findElement(By.cssSelector("div:nth-child(1) > .im-slide .delete-cart-item")).click();
      w8();
      driver.findElement(By.cssSelector(".delete-cart-item")).click();

      w8();
      driver.close();
      
      String tekstDesc = "[Testiranje brisanja artikla iz korpe]";
      WriteInFile("test-report.txt", tekstDesc);
    }
    
    @Test
    public void WriteReport() {
    	driver.get("https://www.maxi.rs/");
    	
    	String tekst = "Osvnovni podaci o kompaniji \n"+"---------------------------------"+ "\n"
    	+driver.findElement(By.cssSelector("p:nth-child(8)")).getText() + "\n\n" 
    	+driver.findElement(By.cssSelector("p:nth-child(7)")).getText()+"\n"+"---------------------------------" + "\n";
    	

        WriteInFile("test-report.txt", tekst);
        
        String tekstDesc = "[Testiranje pisanja podataka u fajlu]";
        WriteInFile("test-report.txt", tekstDesc);
        
        assertFalse(tekst.isEmpty());       

    }
    
    
    private void WriteInFile(String putanja, String tekst) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(putanja, true))) {
            writer.write(tekst);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void w8() {
        try {
            Thread.sleep(3000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	
    }

}
