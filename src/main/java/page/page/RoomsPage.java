package page.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class RoomsPage {
    Locator roomNumber;
    Locator roomPrice;
    Locator roomWifi;
    Locator roomTV;
    Locator roomRadio;
    Locator roomRefreshments;
    Locator roomSafe;
    Locator roomViews;
    Locator createButton;


    public RoomsPage(Page page) {
        this.roomNumber = page.locator("#roomName");
        this.roomPrice = page.locator("#roomPrice");
        this.roomWifi = page.locator("#wifiCheckbox");
        this.roomTV = page.locator("#tvCheckbox");
        this.roomRadio = page.locator("#radioCheckbox");
        this.roomRefreshments = page.locator("#refreshmentsCheckbox");
        this.roomSafe = page.locator("#safeCheckbox");
        this.roomViews = page.locator("#viewsCheckbox");
        this.createButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create"));
    }

    public void addRoom(String number, String price, boolean roomWifi) {
        roomNumber.fill(number);
        roomPrice.fill(price);
        createButton.click();
    }
}
