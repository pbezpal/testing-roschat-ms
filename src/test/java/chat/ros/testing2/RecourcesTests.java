package chat.ros.testing2;

import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.LoginPage;
import chat.ros.testing2.server.contacts.ContactsPage;
import chat.ros.testing2.server.contacts.UserPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.stqa.selenium.factory.WebDriverPool;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.logging.Level;

import static chat.ros.testing2.data.ContactsData.*;
import static chat.ros.testing2.data.LoginData.*;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecourcesTests implements BeforeAllCallback, BeforeEachCallback {

    private String hostServer = "https://" + HOST_SERVER + ":" + PORT_SERVER;
    private String hostClient = "https://" + HOST_SERVER;
    private String classTest = "";
    private StringBuilder logs = new StringBuilder();
    private LogEntries logEntries;
    private String sshCommandIsContact = "/var/db/roschat-db/userlist.sh | grep ";

    @Override
    public void beforeAll(ExtensionContext context){

        classTest = String.valueOf(context.getTestClass());

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

        if (classTest.contains("Test_A_ServerPage")) openMS("/settings/web-server");
        else if (classTest.contains("Test_A_TelephonyPage")) openMS("/settings/telephony");
        else if (classTest.contains("Test_A_SNMPPage")) openMS("/settings/snmp");
        else if (classTest.contains("Test_A_UserPage")) openMS("/settings/users");
    }

    @Override
    public void beforeEach(ExtensionContext context){
        if (classTest.contains("Test_A_MailPage")) openMS("/settings/mail");
        else if (classTest.contains("Test_B_ServicePage") ||
                String.valueOf(context.getRequiredTestMethod()).contains(("test_Add_Service_Tetra_Contact_7012"))) {
            addContactAndAccount(CONTACT_NUMBER_7012);
            openMS("/contacts");
        }
        else if (String.valueOf(context.getRequiredTestMethod()).contains("test_Count_Contacts_After_Sync_Integrations")) {
            openMS("/contacts");
        }
        else if (classTest.contains("Test_A_IntegrationPage")) openMS("/settings/integration");
        else if(classTest.contains("Test_A_ChannelsPage")) {
            if (String.valueOf(context.getRequiredTestMethod()).contains("Channel_7012")) {
                addContactAndAccount(CONTACT_NUMBER_7012);
            } else if(String.valueOf(context.getRequiredTestMethod()).contains("Do_Tested_Channel")) {
                openMS("/admin/channels");
            }else if(String.valueOf(context.getRequiredTestMethod()).contains("Channel_7013")){
                addContactAndAccount(CONTACT_NUMBER_7013);
            }
        }
    }

    private void openMS(String page){
        LoginPage loginPage = new LoginPage();
        Configuration.baseUrl = hostServer;
        if( ! WebDriverRunner.getWebDriver().getCurrentUrl().contains(hostServer)) open("/");
        if( ! loginPage.isLoginMS()) loginPage.loginOnServer(LOGIN_ADMIN_MS, PASSWORD_ADMIN_MS);
        assertTrue(loginPage.isLoginMS(), "Не удалось авторизоваться");
        open(page);
    }

    private void addContactAndAccount(String number){
        if (!SSHManager.isCheckQuerySSH(sshCommandIsContact + number)) {
            ContactsPage contactsPage = new ContactsPage();
            openMS("/contacts");
            contactsPage.addContact(number).addUserAccount(number, USER_ACCOUNT_PASSWORD, USER_ACOUNT_ITEM_MENU);
        }
    }
}
