package chat.ros.testing2;

import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.LoginPage;
import chat.ros.testing2.server.BasePage;
import chat.ros.testing2.server.administration.ChannelsPage;
import chat.ros.testing2.server.contacts.ContactsPage;
import client.ClientPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;
import ru.stqa.selenium.factory.WebDriverPool;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.logging.Level;

import static chat.ros.testing2.TestsParallelBase.commandDBCheckChannel;
import static chat.ros.testing2.data.ContactsData.*;
import static chat.ros.testing2.data.LoginData.*;
import static chat.ros.testing2.helpers.AttachToReport.*;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertTrue;

public class TestsBase {

    public final String hostServer = "https://" + HOST_SERVER + ":" + PORT_SERVER;
    public final String hostClient = "https://" + HOST_SERVER;
    public final String sshCommandIsContact = "sudo -u roschat psql -c \"select cid, login from users;\" | grep %1$s";
    public String nameChannel;
    public String newNameChannel;
    public String channel;
    public SoftAssert softAssert = null;
    public boolean resultCreate;
    public boolean resultChange;

    public TestsBase(){}

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

    public void getChannelName(String classTest){
        if(classTest.contains("TestClosedChannelChange")){
            nameChannel = "CHCCCH" + System.currentTimeMillis();
            newNameChannel = nameChannel + System.currentTimeMillis();
        }else if(classTest.contains("TestClosedChannel")) {
            nameChannel = "CHC" + System.currentTimeMillis();
            newNameChannel = nameChannel + System.currentTimeMillis();
        }else if(classTest.contains("TestPublicChannelChange")){
            nameChannel = "CHPCCH" + System.currentTimeMillis();
            newNameChannel = nameChannel + System.currentTimeMillis();
        }else if(classTest.contains("TestPublicChannel")){
            nameChannel = "CHP" + System.currentTimeMillis();
            newNameChannel = nameChannel + System.currentTimeMillis();
        }else if(classTest.contains("TestPublicProvenChannelChange")){
            nameChannel = "CHPPCH" + System.currentTimeMillis();
            newNameChannel = nameChannel + System.currentTimeMillis();
        }else if(classTest.contains("TestPublicProvenChannel")){
            nameChannel = "CHPP" + System.currentTimeMillis();
            newNameChannel = nameChannel + System.currentTimeMillis();
        }
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

    public void afterTestMethod(Method m, ITestResult testResult, String testClass){
        Method filename = m;
        ITestResult result = testResult;
        if(testClass.contains("Channel")){
            if(m.toString().contains("test_Create_Closed_Channel")) resultCreate = testResult.isSuccess();
            if(m.toString().contains("test_Change_Name_And_Description_Channel")) resultChange = testResult.isSuccess();
        }
        if(!result.isSuccess()){
            AScreenshot(filename.toString());
            ABrowserLogNetwork();
            ABrowserLogConsole();
        }
    }

    public void openClient(String login, boolean staySystem) {
        Configuration.baseUrl = hostClient;
        open("/");
        if (ClientPage.isLoginWindow()) {
            assertTrue(ClientPage.loginClient(login, USER_ACCOUNT_PASSWORD, staySystem), "Ошибка при " +
                    "авторизации");
        }
    }

    public void openMS(String... itemMenu){
        sleep(1000);
        Configuration.baseUrl = hostServer;
        LoginPage loginPage = new LoginPage();
        open("/");
        if( ! loginPage.isLoginMS()) loginPage.loginOnServer(LOGIN_ADMIN_MS, PASSWORD_ADMIN_MS);
        if(itemMenu.length == 2){
            BasePage.clickItemMenu(itemMenu[0]);
            BasePage.clickItemSettings(itemMenu[1]);
        }else if(itemMenu.length == 1) BasePage.clickItemMenu(itemMenu[0]);
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

    @Story(value = "Удаляем канал и проверяем отображается ли канал в СУ после удаления")
    @Description(value = "1. Авторизуемся под пользователем администратором канала и удаляем канал. " +
            "2. Проверяем на СУ, что канал не отображается после удаления канала")
    public void deleteChannel(String testClass){
        if(testClass.contains("Channel")){
            ChannelsPage channelsPage = new ChannelsPage();
            if(resultCreate || resultChange) {
                if(resultChange) channel = newNameChannel;
                else channel = newNameChannel;
                openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
                softAssert = new SoftAssert();
                softAssert.assertTrue(
                        channelsPage.deleteChannel(channel).isExistComments(channel, false),
                        "Канал найден в списке бесед после удаления");
                softAssert.assertFalse(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, channel)),
                        "Запись о канале " + channel + " осталась в БД postgres после удаления");
                softAssert.assertAll();

                openMS("Администрирование","Каналы");
                assertTrue(channelsPage.isShowChannel(channel, false),
                        "Закрытый канал " + channel + " отображается в СУ после удаления");
            }
            WebDriverRunner.closeWebDriver();
        }
    }
}
