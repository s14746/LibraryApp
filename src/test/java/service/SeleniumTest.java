package service;

import org.junit.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import selenium.AuthenticationPage;
import selenium.CreateAnAccountPage;
import selenium.HomePage;
import selenium.MyAccountPage;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class SeleniumTest {

    private static WebDriver driver;

    String randomEmail = "randomowyemail" + ThreadLocalRandom.current().nextInt(1, 100 + 1) + "@testowy.com";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\Dane\\PJWSTK\\VII semestr\\TAU\\TAU-Selenium\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://automationpractice.com/index.php");
    }

    @Test
    public void fieldValidationTestOfTheRegistrationForm() {
        HomePage homePage = new HomePage(driver);
        homePage.getSignInLink().click();

        AuthenticationPage authenticationPage = new AuthenticationPage(driver);
        authenticationPage.submit("testowyEmail111@testowy.com");

        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(driver);
        createAnAccountPage.getGenderMrRadioButton().click();
        createAnAccountPage.setFirstname("Jan123");
        createAnAccountPage.setLastname("Kowalski123");
        createAnAccountPage.setPassword("123");
        createAnAccountPage.setAddress("");
        createAnAccountPage.setCity("@!#$%^&*");
        createAnAccountPage.setStateSelectList("-");
        createAnAccountPage.setPostcode("123");
        createAnAccountPage.setCountrySelectList("United States");
        createAnAccountPage.setPhoneMobile("123abc");
        createAnAccountPage.submit();

        Assert.assertTrue(createAnAccountPage.hasErrorMessage("firstname is invalid."));
        Assert.assertTrue(createAnAccountPage.hasErrorMessage("lastname is invalid."));
        Assert.assertTrue(createAnAccountPage.hasErrorMessage("passwd is invalid."));
        Assert.assertTrue(createAnAccountPage.hasErrorMessage("address1 is required."));
        Assert.assertTrue(createAnAccountPage.hasErrorMessage("city is invalid."));
        Assert.assertTrue(createAnAccountPage.hasErrorMessage("This country requires you to choose a State."));
        Assert.assertTrue(createAnAccountPage.hasErrorMessage("The Zip/Postal code you've entered is invalid. It must follow this format: 00000"));
        Assert.assertTrue(createAnAccountPage.hasErrorMessage("phone_mobile is invalid."));
    }

    @Test
    public void testValidationFieldCountryOfTheRegistrationForm() {
        HomePage homePage = new HomePage(driver);
        homePage.getSignInLink().click();

        AuthenticationPage authenticationPage = new AuthenticationPage(driver);
        authenticationPage.submit("testowyEmail222@testowy.com");

        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(driver);
        createAnAccountPage.getGenderMrRadioButton().click();
        createAnAccountPage.setFirstname("Jan");
        createAnAccountPage.setLastname("Kowalski");
        createAnAccountPage.setPassword("123456");
        createAnAccountPage.setAddress("Testowa ulica 123");
        createAnAccountPage.setCity("Sopot");
        createAnAccountPage.setStateSelectList("Alaska");
        createAnAccountPage.setPostcode("12345");
        createAnAccountPage.setCountrySelectList("-");
        createAnAccountPage.setPhoneMobile("123456789");
        createAnAccountPage.submit();

        Assert.assertTrue(createAnAccountPage.hasErrorMessage("id_country is required."));
    }

    @Test
    public void testOfSuccessfulRegistration() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.getSignInLink().click();

        AuthenticationPage authenticationPage = new AuthenticationPage(driver);
        authenticationPage.submit(randomEmail);

        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(driver);
        createAnAccountPage.getGenderMrRadioButton().click();
        createAnAccountPage.setFirstname("Jan");
        createAnAccountPage.setLastname("Kowalski");
        createAnAccountPage.setPassword("123456");
        createAnAccountPage.setAddress("Ulica Testowa 123/123");
        createAnAccountPage.setCity("Miasto testowe");
        createAnAccountPage.setStateSelectList("Alaska");
        createAnAccountPage.setPostcode("12345");
        createAnAccountPage.setCountrySelectList("United States");
        createAnAccountPage.setPhoneMobile("123 456 789");
        createAnAccountPage.submit();

        MyAccountPage myAccountPage = new MyAccountPage(driver);
        Assert.assertTrue(myAccountPage.getMyAccountMessage().isDisplayed());
    }

    @Test
    public void testOfSuccessfulRegistration768x1024() {
        driver.manage().window().setSize(new Dimension(768, 1024));
        HomePage homePage = new HomePage(driver);
        homePage.getSignInLink().click();

        AuthenticationPage authenticationPage = new AuthenticationPage(driver);
        authenticationPage.submit(randomEmail);

        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(driver);
        createAnAccountPage.getGenderMrRadioButton().click();
        createAnAccountPage.setFirstname("Jan");
        createAnAccountPage.setLastname("Kowalski");
        createAnAccountPage.setPassword("123456");
        createAnAccountPage.setAddress("Ulica Testowa 123/123");
        createAnAccountPage.setCity("Miasto testowe");
        createAnAccountPage.setStateSelectList("Alaska");
        createAnAccountPage.setPostcode("12345");
        createAnAccountPage.setCountrySelectList("United States");
        createAnAccountPage.setPhoneMobile("123 456 789");
        createAnAccountPage.submit();

        MyAccountPage myAccountPage = new MyAccountPage(driver);

        Assert.assertTrue(myAccountPage.getMyAccountMessage().isDisplayed());
    }

    @Test
    public void testOfSuccessfulRegistration480x800() throws InterruptedException {
        driver.manage().window().setSize(new Dimension(480, 800));
        HomePage homePage = new HomePage(driver);
        homePage.getSignInLink().click();

        AuthenticationPage authenticationPage = new AuthenticationPage(driver);
        authenticationPage.submit(randomEmail);

        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(driver);
        createAnAccountPage.getGenderMrRadioButton().click();
        createAnAccountPage.setFirstname("Jan");
        createAnAccountPage.setLastname("Kowalski");
        createAnAccountPage.setPassword("123456");
        createAnAccountPage.setAddress("Ulica Testowa 123/123");
        createAnAccountPage.setCity("Miasto testowe");
        createAnAccountPage.setStateSelectList("Alaska");
        createAnAccountPage.setPostcode("12345");
        createAnAccountPage.setCountrySelectList("United States");
        createAnAccountPage.setPhoneMobile("123 456 789");
        createAnAccountPage.submit();

        MyAccountPage myAccountPage = new MyAccountPage(driver);
        Thread.sleep(4000);
        Assert.assertTrue(myAccountPage.getMyAccountMessage().isDisplayed());
    }

    @Test
    public void testOfFailedRegistration() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.getSignInLink().click();

        AuthenticationPage authenticationPage = new AuthenticationPage(driver);
        authenticationPage.submit("testowy666@email");

        //Assert.assertTrue(authenticationPage.hasErrorMessage("Invalid email address."));
        Assert.assertEquals(true, authenticationPage.hasErrorMessage("Invalid email address."));
    }

    @After
    public void cleanUp() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
