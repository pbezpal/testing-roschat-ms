package chat.ros.testing2;

import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.BasePage;
import chat.ros.testing2.server.LoginPage;
import chat.ros.testing2.server.contacts.ContactsPage;
import client.ClientPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.net.NetworkUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.stqa.selenium.factory.WebDriverPool;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.logging.Level;

import static chat.ros.testing2.data.ContactsData.USER_ACCOUNT_ITEM_MENU;
import static chat.ros.testing2.data.ContactsData.USER_ACCOUNT_PASSWORD;
import static chat.ros.testing2.data.LoginData.HOST_HUB;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsBase implements ClientPage {

    private final String hostServer = "https://" + System.getProperty("hostserver") + ":" + System.getProperty("portms");
    private final String hostClient = "https://" + System.getProperty("hostserver");
    private final String sshCommandIsContact = "sudo -u roschat psql -c \"select cid, login from users;\" | grep %1$s";
    private RemoteWebDriver driver = null;
    private String ipAddress = new NetworkUtils().getIp4NonLoopbackAddressOfThisMachine().getHostAddress();

    public TestsBase () {}

    public void init(){

        Configuration.remote = "http://" + HOST_HUB + ":4444/wd/hub";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("88.0");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("acceptInsecureCerts", true);

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        capabilities.setCapability("goog:loggingPrefs", logPrefs);
        Configuration.browserCapabilities = capabilities;
        Configuration.browserSize = "1920x1080";
        Configuration.browserPosition = "2x2";
        Configuration.fileDownload = FileDownloadMode.FOLDER;
        Configuration.screenshots = false;

        /*DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("88.0");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("acceptInsecureCerts", true);

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        capabilities.setCapability("goog:loggingPrefs", logPrefs);

        try {
            driver = new RemoteWebDriver(URI.create("http://" + HOST_HUB + ":4444/wd/hub").toURL(), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().window().setPosition(new Point(2,2));
        driver.setFileDetector(new LocalFileDetector());
        WebDriverRunner.setWebDriver(driver);
        Configuration.browserSize = "1920x1080";
        Configuration.browserPosition = "2x2";
        Configuration.fileDownload = FileDownloadMode.FOLDER;

        Configuration.screenshots = false;*/
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


    public void openClient(String login, boolean staySystem) {
        Configuration.baseUrl = hostClient;
        open("/");
        assertTrue(ClientPage.isLoginWindow(), "Не появилась форма авторизации на WEB клиенте");
        ClientPage.loginClientClickButtonOrEnter(login, USER_ACCOUNT_PASSWORD, staySystem, true);
        assertTrue(isSuccessAuthClient(), "Ошибка при " +
                    "авторизации");
    }

    public void openClient(String host, String login, boolean staySystem){
        Configuration.baseUrl = host;
        open("/");
        assertTrue(ClientPage.isLoginWindow(), "Не появилась форма авторизации на WEB клиенте");
        ClientPage.loginClientClickButtonOrEnter(login, USER_ACCOUNT_PASSWORD, staySystem, true);
        assertTrue(isSuccessAuthClient(), "Ошибка при " +
                    "авторизации");
    }

    public void openMS(String login, String password, String... navigation){
        sleep(1000);
        Configuration.baseUrl = hostServer;
        LoginPage loginPage = new LoginPage();
        open("/");
        if( ! loginPage.isLoginMS()) loginPage.loginOnServer(login, password);
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
            //openMS("Справочник");
            if(contactsPage.isNotExistsTableText(number)) {
                contactsPage.actionsContact(number).addUserAccount(number, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU);
            }
        }
    }

    public boolean isContactAndAccount(String number){
        return SSHManager.isCheckQuerySSH(String.format(sshCommandIsContact, number));
    }
}
