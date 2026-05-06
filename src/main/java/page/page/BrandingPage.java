package page.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BrandingPage {
    Locator mapLatitude;
    Locator mapLongitude;
    Locator mapDirections;

    public BrandingPage(Page page) {
        this.mapLatitude = page.locator("#latitude");
        this.mapLongitude = page.locator("#longitude");
        this.mapDirections = page.locator("#directions");
    }

    public void fillMapLatitude(String latitude) {
        mapLatitude.fill(latitude);
    }

    public void fillMapLongitude(String longitude) {
        mapLongitude.fill(longitude);
    }

    public void clickOnMapDirections() {
        mapDirections.click();
    }
}
