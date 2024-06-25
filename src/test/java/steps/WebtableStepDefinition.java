package steps;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Base64;
import java.util.List;

public class WebtableStepDefinition
{
    // WebDriver instance
    public static WebDriver driver;

    WebDriverWait wait ;
    public JavascriptExecutor js;

    @Given("I navigate to the webtable page")
    public void i_navigate_to_the_webtable_page() {
        // Set up ChromeOptions to handle remote origins
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        // Set the system property for ChromeDriver
        System.setProperty("webdriver.chrome.driver","src/main/resources/drivers/chromedriver.exe");

        // Initialize WebDriver with ChromeDriver and options
        driver = new ChromeDriver(options);

        // Initialize WebDriverWait with a timeout of 10 seconds
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Initialize JavascriptExecutor
        js = (JavascriptExecutor) driver;

        //Maximize the window
        driver.manage().window().maximize();

        // Navigate to the webtable page
        driver.get("https://www.way2automation.com/angularjs-protractor/webtables/");
    }

    @When("I click Add User link")
    public void i_click_add_user_link() {
        // Click on the "Add User" button
        driver.findElement(By.xpath("//button[contains(text(),'Add User')]")).click();
    }

    @Then("I should see Add User tab")
    public void i_should_see_add_user_tab() {
        // Get the text of the h3 element and assert it is "Add User"
        String h3Text = driver.findElement(By.tagName("h3")).getText();
        Assert.assertEquals("Add User",h3Text);
    }

    boolean isUsernameFoundAfterAdding = false;

    @When("I add user data and click Save button")
    public void i_add_user_data_and_click_button() throws InterruptedException {
        // Decode base64 encoded password
        String inputString = "aGVsbG8gd29ybGR+";
        byte[] base64DecodedBytes = Base64.getDecoder().decode(inputString);
        String password = new String(base64DecodedBytes);

        // Fill in the user details in the form
        driver.findElement(By.name("FirstName")).sendKeys("Tushar");
        driver.findElement(By.name("LastName")).sendKeys("DemoLN");
        String username = "tslDemoUser";
        driver.findElement(By.name("UserName")).sendKeys(username);
        driver.findElement(By.name("Password")).sendKeys(password);

        // Select Company AAA radio button
        driver.findElement(By.xpath("//tr[@ng-repeat='column in columns']//td[text()='Customer']//parent::tr//label[text()='Company AAA']//input[@type='radio']")).click();

        // Assert that the Company AAA radio button is selected
        Assert.assertEquals(true,driver.findElement(By.xpath("//tr[@ng-repeat='column in columns']//td[text()='Customer']//parent::tr//label[text()='Company AAA']//input[@type='radio']")).isSelected());

        // Select "Customer" from Role dropdown
        Select objSelect =new Select(driver.findElement(By.xpath("//select[@name='RoleId']")));
        objSelect.selectByVisibleText("Customer");

        // Fill in the email and mobile phone fields
        driver.findElement(By.name("Email")).sendKeys("demo@email.com");
        driver.findElement(By.name("Mobilephone")).sendKeys("+91-9999999999"+ Keys.TAB);

        // Wait for 2 seconds
        Thread.sleep(2000);

        // Click the Save button using JavaScriptExecutor
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[.='Save']")));

        // Wait for 2 seconds
        Thread.sleep(2000);

        // Verify if the username is added to the webtable
        List<WebElement> userNames = driver.findElements(By.xpath("//table//tbody//td[3]"));
        for (int i=0;i<userNames.size();i++)
        {
            if(userNames.get(i).getText().equalsIgnoreCase(username))
            {
                isUsernameFoundAfterAdding = true;
                break;
            }
        }
    }

    @Then("User should be added in the webtable")
    public void user_should_be_added_in_the_webtable() {
        // Assert that the username is found in the webtable
        Assert.assertEquals(true,isUsernameFoundAfterAdding);
    }

    @When("I delete the novak user")
    public void i_delete_the_user() {
        // Click the delete button for the user 'novak'
        driver.findElement(By.xpath("//table//tbody//td[3 and (text()='novak')]//parent::tr//td//button[@ng-click='delUser()']")).click();

        // Wait until the confirmation dialog is visible
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h3[.='Confirmation Dialog']"))));

        // Click the OK button in the confirmation dialog
        driver.findElement(By.xpath("//button[.='OK']")).click();
    }

    boolean isUsernameFoundAfterDeleting = false;

    @Then("User should get deleted from the webtable")
    public void user_should_get_deleted_from_the_webtable() {
        // Verify if the username 'novak' is deleted from the webtable
        List<WebElement> userNames = driver.findElements(By.xpath("//table//tbody//td[3]"));
        for (int i=0;i<userNames.size();i++)
        {
            if(userNames.get(i).getText().equalsIgnoreCase("novak"))
            {
                isUsernameFoundAfterAdding = true;
                break;
            }
        }
        // Assert that the username is not found in the webtable
        Assert.assertEquals(false,isUsernameFoundAfterDeleting);
    }

    @After
    public void TearDown()
    {
        // Quit the WebDriver instance
        driver.quit();
    }
}
