package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends PageObject {

    @FindBy(css = "#center_column h1")
    private WebElement myAccountMessage;

    public WebElement getMyAccountMessage() {
        return myAccountMessage;
    }

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }
}
