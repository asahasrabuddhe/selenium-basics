import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;


public class FirstTest  {
    public static void main(String[] args) {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        WebDriver driver = new ChromeDriver();

        // And now use this to visit Google
        driver.get("http://www.google.com");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.name("q"));

        // Enter something to search for
        element.sendKeys("Cheese!");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

        // Capture Screenshot
        captureScreenshot(driver, "/home/ajitem/Desktop/test.png");

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());

        //Close the browser
        driver.quit();
    }

    public static void captureScreenshot(WebDriver driver, String destinationPath) {
        // Initialize Javascript Executor to allow us to execute JS code on the webpage.
        JavascriptExecutor je = (JavascriptExecutor)driver;
        // Return the actual height of the page using JS
        Long height = (Long)je.executeScript("return document.body.scrollHeight;");
        // Backup the existing dimension to reset before function exist
        Dimension oldDimension = driver.manage().window().getSize();
        // Set new dimension = full height x 1280
        Dimension d = new Dimension(1280, height.intValue());
        driver.manage().window().setSize(d);

        // Initialize Screenshot Driver
        TakesScreenshot ts = (TakesScreenshot)driver;
        // Take a screenshot and store as a file
        File source = ts.getScreenshotAs(OutputType.FILE);
        // Create destination file
        File destination = new File(destinationPath);
        // Copy file to destination
        try {
            FileUtils.copyFile(source, destination);
        } catch( Exception e) {
            System.out.println(e.getMessage());
        }
        // Reset dimension
        driver.manage().window().setSize(oldDimension);
    }
}