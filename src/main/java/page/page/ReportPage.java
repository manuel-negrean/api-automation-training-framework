package page.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class ReportPage {
    private final Page page;
    private static final DateTimeFormatter DISPLAYED_MONTH_FORMAT = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
    Locator todayButton;
    Locator backButton;
    Locator nextButton;
    Locator monthLabel;


    public ReportPage(Page page) {
        this.page = page;
        this.todayButton = page.locator("//div[contains(@class,'rbc-toolbar')]//button[normalize-space()='Today']");
        this.backButton = page.locator("//div[contains(@class,'rbc-toolbar')]//button[normalize-space()='Back']");
        this.nextButton = page.locator("//div[contains(@class,'rbc-toolbar')]//button[normalize-space()='Next']");
        this.monthLabel = page.locator(".rbc-toolbar-label");
    }

    public void clickOnTodayButton() {
        todayButton.click();
    }

    public void clickOnBackButton() {
        backButton.click();
    }

    public String getDisplayedMonth() {
        return monthLabel.innerText();
    }

    public void clickOnNextButton() {
        nextButton.click();
    }
}
