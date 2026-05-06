package page.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AdminPage {

    private final Page page;
    Locator adminUsernameInputField;
    Locator adminPasswordInputField;
    Locator loginButton;


    public AdminPage(Page page) {
        this.page = page;
        //this.adminUsernameInputField = page.getByLabel("Username");
        this.adminUsernameInputField = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Username"));
        this.adminPasswordInputField = page.locator("#password");
        this.loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
    }

    public void navigateToAdminPage() {
        page.navigate("https://automationintesting.online/admin");
    }

    public void adminLogin(){
        adminUsernameInputField.fill("admin");
        adminPasswordInputField.fill("password");
        loginButton.click();
    }
}
