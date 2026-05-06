package page.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AdminDashboardPage {
    private final Page page;
    Locator reportsLink;
    Locator brandingLink;
    Locator roomsLink;

    public AdminDashboardPage(Page page) {
        this.page = page;
        this.reportsLink = page.locator("#reportLink");

        this.brandingLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("branding"));
        this.roomsLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("rooms"));
    }

    public void clickOnReportsLink() {
        reportsLink.click();
    }

    public void clickOnBrandingLink() {
        brandingLink.click();
    }

    public void clickOnRoomsLink() {
        roomsLink.click();
    }

    public void clickOnMessagesLink() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("messages")).click();
    }
}
