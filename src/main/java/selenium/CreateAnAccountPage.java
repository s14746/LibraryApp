package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class CreateAnAccountPage extends PageObject {

    @FindBy(id = "id_gender1")
    private WebElement genderMrRadioButton;

    @FindBy(id = "id_gender2")
    private WebElement genderMrsRadioButton;

    @FindBy(id = "customer_firstname")
    private WebElement firstname;

    @FindBy(id = "customer_lastname")
    private WebElement lastname;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "passwd")
    private WebElement password;

    @FindBy(id = "address1")
    private WebElement address;

    @FindBy(id = "city")
    private WebElement city;

    @FindBy(id = "id_state")
    private WebElement stateSelectList;

    @FindBy(id = "postcode")
    private WebElement postcode;

    @FindBy(id = "id_country")
    private WebElement countrySelectList;

    @FindBy(id = "phone_mobile")
    private WebElement phoneMobile;

    @FindBy(id = "submitAccount")
    private WebElement registerButton;

    @FindBy(css = ".alert-danger")
    private WebElement createAccountErrorMessages;

    public CreateAnAccountPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getGenderMrRadioButton() {
        return genderMrRadioButton;
    }

    public void setFirstname(String firstname) {
        this.firstname.sendKeys(firstname);
    }

    public void setLastname(String lastname) {
        this.lastname.sendKeys(lastname);
    }

    public void setEmail(String email) {
        this.email.sendKeys(email);
    }

    public void setPassword(String password) {
        this.password.sendKeys(password);
    }

    public void setAddress(String address) {
        this.address.sendKeys(address);
    }

    public void setCity(String city) {
        this.city.sendKeys(city);
    }

    public void setStateSelectList(String stateSelectList) {
        new Select(this.stateSelectList).selectByVisibleText(stateSelectList);
    }

    public void setPostcode(String postcode) {
        this.postcode.sendKeys(postcode);
    }

    public void setCountrySelectList(String countrySelectList) {
        new Select(this.countrySelectList).selectByVisibleText(countrySelectList);
    }

    public void setPhoneMobile(String phoneMobile) {
        this.phoneMobile.sendKeys(phoneMobile);
    }

    public void submit() {
        registerButton.click();
    }

    public boolean hasErrorMessage(String errorMessage) {
        return createAccountErrorMessages.getText().contains(errorMessage);
    }
}
