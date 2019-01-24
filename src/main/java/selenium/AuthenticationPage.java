package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthenticationPage extends PageObject {

    @FindBy(id = "email_create")
    private WebElement emailCreateInput;

    @FindBy(id = "SubmitCreate")
    private WebElement createAccountButton;

    @FindBy(xpath = "//*[@id=\"create_account_error\"]/ol/li")
    private WebElement createAccountError;

    @FindBy(css = ".alert-danger")
    private WebElement createAccountErrorMessages;

    @FindBy(id = "email")
    private WebElement loginAccountEmail;

    @FindBy(id = "passwd")
    private WebElement password;

    @FindBy(id = "SubmitLogin")
    private WebElement loginAccountButton;

    public AuthenticationPage(WebDriver driver) {
        super(driver);
    }

    public void submit(String email) {
        emailCreateInput.clear();
        emailCreateInput.sendKeys(email);
        createAccountButton.click();
    }

    public WebElement getCreateAccountError() {
        return createAccountError;
    }

    public boolean hasErrorMessage(String errorMessage) {
        return createAccountError.getText().contains(errorMessage);
    }
}
