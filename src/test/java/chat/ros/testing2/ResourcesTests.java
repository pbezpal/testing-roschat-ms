package chat.ros.testing2;

import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.LoginPage;
import chat.ros.testing2.server.settings.UserPage;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import chat.ros.testing2.server.settings.integration.SKUDPage;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;

import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7012;
import static chat.ros.testing2.data.HelperData.commandDBCheckSKUD;
import static chat.ros.testing2.data.LoginData.*;
import static com.codeborne.selenide.Selenide.open;
import static org.testng.Assert.assertTrue;

public class ResourcesTests extends UserPage implements BeforeAllCallback, BeforeEachCallback, IntegrationPage {

    private WebDriver driver;
    private String classTest;
    private SKUDPage skudPage;
    private TestsBase testsBase = new TestsBase();
    private final String hostServer = "https://" + HOST_SERVER + ":" + PORT_SERVER;
    private final String hostClient = "https://" + HOST_SERVER;
    private LoginPage loginPage = new LoginPage();

    @Override
    public void beforeAll(ExtensionContext context) {

        classTest = String.valueOf(context.getTestClass());

        testsBase.init();

        if(classTest.contains("Skud") && SSHManager.isCheckQuerySSH(commandDBCheckSKUD)){
            testsBase.openMS("Настройки","Интеграция");
            skudPage = (SKUDPage) clickServiceType("СКУД");
            assertTrue(skudPage.deleteSKUD("СКУД"),
                    "После удаления, сервис СКУД найден в таблице Подключенные сервисы");
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        String method = context.getTestMethod().toString();
        if(classTest.contains("Integration")) if(method.contains("Open_Page")) testsBase.openMS("/settings/integration");
        else if(classTest.contains("TestGeozonesPage")){
            if(method.contains("Open_Page")) testsBase.openMS("/settings/geozones");
            else testsBase.openMS("Настройки","Геозоны");
        }else if(classTest.contains("TestMailPage")){
            if(method.contains("Open_Page")) testsBase.openMS("/settings/mail");
            else testsBase.openMS("Настройки","Почта");
        }else if(classTest.contains("TestMonitorSkud")){
            if(method.contains("Status")) testsBase.openMS("Монитор");
            else testsBase.openMS("Настройки","Интеграция");
        }else if(classTest.contains("TestServerPage")){
            testsBase.addContactAndAccount(CONTACT_NUMBER_7012);
            if (method.contains("Open_Page")) testsBase.openMS("/settings/web-server");
            else testsBase.openMS("Настройки", "Сервер");
        }else if(classTest.contains("TestSNMPPage")){
            if(method.contains("Open_Page")) testsBase.openMS("/settings/snmp");
            else testsBase.openMS("Настройки","SNMP");
        }else if(classTest.contains("TestTelephonyPage")){
            if(method.contains("Open_Page")) testsBase.openMS("/settings/telephony");
            testsBase.openMS("Настройки","Телефония");
        }else if(classTest.contains("TestUserPage")){
            if(method.contains("Login") || method.contains("Delete")){
                Configuration.baseUrl = testsBase.getHostMS();
                open("/");
                logoutMS();
            }else if(method.contains("Open")){
                testsBase.openMS();
                open("/settings/users");
            }else testsBase.openMS("Настройки","Настройка СУ");
        }else if(classTest.contains("TestServicePage")){
            testsBase.addContactAndAccount(CONTACT_NUMBER_7012);
            testsBase.openMS("Справочник");
        }
    }
}
