package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageObject {

    @FindBy(className = "login")
    private WebElement signInLink;

    @FindBy(className = "logout")
    private WebElement logOutLink;

    @FindBy(className = "account")
    private WebElement accountLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public WebElement getSignInLink() {
        return signInLink;
    }

    public void setSignInLink(WebElement signInLink) {
        this.signInLink = signInLink;
    }

    public WebElement getLogOutLink() {
        return logOutLink;
    }

    public void setLogOutLink(WebElement logOutLink) {
        this.logOutLink = logOutLink;
    }

    public WebElement getAccountLink() {
        return accountLink;
    }

    public void setAccountLink(WebElement accountLink) {
        this.accountLink = accountLink;
    }
}
