import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page.page.*;

public class FirstTest {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    protected Page page;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create(); //driver
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false).setSlowMo(1000)); //browser
        context = browser.newContext(); //context si ptr screenshot
        page = context.newPage();
    }

    @Test
    public void test() throws InterruptedException {
        page.navigate("https://automationintesting.online/admin");
        Thread.sleep(5000);
        System.out.println("This is my first test");
    }

    @Test
    public void test2() throws InterruptedException {
        page.navigate("https://google.com");
        Thread.sleep(5000);
        System.out.println("This is my first test");
    }

    @Test
    public void test3() throws InterruptedException {
        AdminPage admin = new AdminPage(page);
        AdminDashboardPage adminDashboard = new AdminDashboardPage(page);
        admin.navigateToAdminPage();
        admin.adminLogin();
        //Thread.sleep(5000);
        adminDashboard.clickOnBrandingLink();
    }

    @Test
    public void test4() throws InterruptedException {
        AdminPage admin = new AdminPage(page);
        AdminDashboardPage adminDashboardPage = new AdminDashboardPage(page);
        admin.navigateToAdminPage();
        admin.adminLogin();
        adminDashboardPage.clickOnRoomsLink();

        RoomsPage roomsPage = new RoomsPage(page);
        roomsPage.addRoom("101","100", true);
    }

    @Test
    public void test5() throws InterruptedException {
        AdminPage admin = new AdminPage(page);
        AdminDashboardPage adminDashboardPage = new AdminDashboardPage(page);
        admin.navigateToAdminPage();
        admin.adminLogin();
        adminDashboardPage.clickOnReportsLink();

        ReportPage reportPage = new ReportPage(page);
        reportPage.clickOnTodayButton();
        reportPage.clickOnBackButton();
        reportPage.clickOnNextButton();
        reportPage.getDisplayedMonth();
    }

    @Test
    public void test6() throws InterruptedException {
        AdminPage admin = new AdminPage(page);
        AdminDashboardPage adminDashboardPage = new AdminDashboardPage(page);
        admin.navigateToAdminPage();
        admin.adminLogin();
        adminDashboardPage.clickOnMessagesLink();

        MessagePage messagePage = new MessagePage(page);
        int messageCount = messagePage.getMessageCount();
        System.out.println("Messages on page: " + messageCount);

        if (messagePage.hasMessageToView()) {
            messagePage.deleteMessage();
        }
    }

    @Test
    public void test7() throws InterruptedException {
        AdminPage admin = new AdminPage(page);
        AdminDashboardPage adminDashboardPage = new AdminDashboardPage(page);
        admin.navigateToAdminPage();
        admin.adminLogin();
        adminDashboardPage.clickOnBrandingLink();

        BrandingPage brandingPage = new BrandingPage(page);
        brandingPage.fillMapLatitude("45.123456");
        brandingPage.fillMapLongitude("25.123456");
        brandingPage.clickOnMapDirections();
    }

    @AfterClass
    public void tearDown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
