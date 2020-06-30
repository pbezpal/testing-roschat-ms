package chat.ros.testing2;

import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.LoginPage;
import chat.ros.testing2.server.BasePage;
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
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import ru.stqa.selenium.factory.WebDriverPool;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.logging.Level;

import static chat.ros.testing2.data.ContactsData.*;
import static chat.ros.testing2.data.LoginData.*;
import static chat.ros.testing2.helpers.AttachToReport.*;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertTrue;

public class TestsBase {

    private final String hostServer = "https://" + HOST_SERVER + ":" + PORT_SERVER;
    private final String hostClient = "https://" + HOST_SERVER;
    private final String sshCommandIsContact = "sudo -u roschat psql -c \"select cid, login from users;\" | grep %1$s";
    private WebDriver driver = null;

    public TestsBase () {} ;

    public void init(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("80.0");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("acceptInsecureCerts", true);

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        capabilities.setCapability("goog:loggingPrefs", logPrefs);

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

    public String getSshCommandIsContact(){
        return sshCommandIsContact;
    }

    public String getHostMS(){
        return hostServer;
    }

    public String getHostClient(){
        return hostClient;
    }

    public void afterTestMethod(Method m, ITestResult testResult){
        Method filename = m;
        ITestResult result = testResult;
        if(!result.isSuccess()){
            AScreenshot(filename.toString());
            ABrowserLogNetwork();
            ABrowserLogConsole();
        }
    }


    public void openClient(String login, boolean staySystem) {
        Configuration.baseUrl = hostClient;
        open("/");
        assertTrue(ClientPage.isLoginWindow(), "Не появилась форма авторизации на WEB клиенте");
        assertTrue(ClientPage.loginClient(login, USER_ACCOUNT_PASSWORD, staySystem), "Ошибка при " +
                    "авторизации");
    }

    public void openClient(String host, String login, boolean staySystem){
        Configuration.baseUrl = host;
        open("/");
        assertTrue(ClientPage.isLoginWindow(), "Не появилась форма авторизации на WEB клиенте");
        assertTrue(ClientPage.loginClient(login, USER_ACCOUNT_PASSWORD, staySystem), "Ошибка при " +
                    "авторизации");
    }

    public void openMS(String... navigation){
        sleep(1000);
        Configuration.baseUrl = hostServer;
        LoginPage loginPage = new LoginPage();
        open("/");
        if( ! loginPage.isLoginMS()) loginPage.loginOnServer(LOGIN_ADMIN_MS, PASSWORD_ADMIN_MS);
        if(navigation.length == 2){
            BasePage.clickItemMenu(navigation[0]);
            BasePage.clickItemSettings(navigation[1]);
        }else if(navigation.length == 1) {
            if(navigation[0].contains("/")) open(navigation[0]);
            else BasePage.clickItemMenu(navigation[0]);
        }
    }

    public void addContactAndAccount(String number){
        if (!SSHManager.isCheckQuerySSH(String.format(sshCommandIsContact, number))) {
            ContactsPage contactsPage = new ContactsPage();
            openMS("Справочник");
            if(contactsPage.isNotExistsTableText(number)) {
                contactsPage.actionsContact(number).addUserAccount(number, USER_ACCOUNT_PASSWORD, USER_ACOUNT_ITEM_MENU);
            }
        }
    }

    public void dismissWebDriver(){
        WebDriverPool.DEFAULT.dismissAll();
    }
}
