package chat.ros.testing2;

import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.LoginPage;
import chat.ros.testing2.server.contacts.ContactsPage;
import client.ClientPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;
import ru.stqa.selenium.factory.WebDriverPool;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.logging.Level;

import static chat.ros.testing2.data.ContactsData.USER_ACCOUNT_PASSWORD;
import static chat.ros.testing2.data.ContactsData.USER_ACOUNT_ITEM_MENU;
import static chat.ros.testing2.data.LoginData.*;
import static chat.ros.testing2.helpers.AttachToReport.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsBase {

    protected final String hostServer = "https://" + HOST_SERVER + ":" + PORT_SERVER;
    protected final String hostClient = "https://" + HOST_SERVER;
    protected final String sshCommandIsContact = "sudo -u roschat psql -c \"select cid, login from users;\" | grep %1$s";

    public TestsBase(){}

    protected void init(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("80.0");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("acceptInsecureCerts", true);

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        capabilities.setCapability("goog:loggingPrefs", logPrefs);

        WebDriver driver = null;
        try {
            driver = WebDriverPool.DEFAULT.getDriver(URI.create("http://" + HOST_HUB + ":4444/wd/hub").toURL(), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().window().setPosition(new Point(2,2));
        WebDriverRunner.setWebDriver(driver);

        Configuration.screenshots = false;
    }

    protected void afterTestMethod(Method m, ITestResult testResult){
        Method filename = m;
        ITestResult result = testResult;
        if(!result.isSuccess()){
            AScreenshot(filename.toString());
            ABrowserLogNetwork();
            ABrowserLogConsole();
        }
    }

    protected void openClient(String login, boolean staySystem) {
        Configuration.baseUrl = hostClient;
        open("/");
        if (ClientPage.isLoginWindow()) {
            assertTrue(ClientPage.loginClient(login, USER_ACCOUNT_PASSWORD, staySystem), "Ошибка при " +
                    "авторизации");
        }
    }

    protected void openMS(String page){
        sleep(1000);
        LoginPage loginPage = new LoginPage();
        Configuration.baseUrl = hostServer;
        if( ! WebDriverRunner.getWebDriver().getCurrentUrl().contains(hostServer)) open("/");
        if( ! loginPage.isLoginMS()) loginPage.loginOnServer(LOGIN_ADMIN_MS, PASSWORD_ADMIN_MS);
        open(page);
    }

    protected void addContactAndAccount(String number){
        sleep(1000);
        if (!SSHManager.isCheckQuerySSH(String.format(sshCommandIsContact, number))) {
            ContactsPage contactsPage = new ContactsPage();
            openMS("/contacts");
            contactsPage.actionsContact(number).addUserAccount(number, USER_ACCOUNT_PASSWORD, USER_ACOUNT_ITEM_MENU);
        }
    }
}
