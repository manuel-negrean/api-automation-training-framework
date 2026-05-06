package page.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MessagePage {
    Locator name;
    Locator subject;
    Locator deleteButton;

    public MessagePage(Page page) {
        this.name = page.locator("div.col-sm-2[data-testid^='message']").first();
        this.subject = page.locator("div.col-sm-9[data-testid^='messageDescription']").first();
        this.deleteButton = page.locator("[data-testid^='deleteMessage']").first();

    }

    public int getMessageCount() {
        return deleteButton.count();
    }

    public boolean hasMessageToView() {
        return getMessageCount() > 0
                && name.first().isVisible()
                && subject.first().isVisible();
    }

    public void deleteMessage() {
        if (!hasMessageToView()) {
            System.out.println("No messages available to delete.");
            return;
        }

        deleteButton.first().click();
    }
}
