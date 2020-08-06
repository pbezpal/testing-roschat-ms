package chat.ros.testing2;

import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.LoginPage;
import chat.ros.testing2.server.settings.MailPage;
import chat.ros.testing2.server.settings.UserPage;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import chat.ros.testing2.server.settings.integration.SKUDPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.HelperData.commandDBCheckSKUD;
import static chat.ros.testing2.data.LoginData.HOST_SERVER;
import static chat.ros.testing2.data.LoginData.PORT_SERVER;
import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourcesTests extends UserPage implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback, IntegrationPage {

    private WebDriver driver = null;
    private String classTest = null;
    private SKUDPage skudPage = null;
    private final String hostServer = "https://" + HOST_SERVER + ":" + PORT_SERVER;
    private final String hostClient = "https://" + HOST_SERVER;
    private LoginPage loginPage = new LoginPage();
    private TestsBase testsBase = new TestsBase();
    private final String sshCommandIsContact = "sudo -u roschat psql -c \"select cid, login from users;\" | grep %1$s";
    private final Map<String, String> mapInputValueConnectOM = new HashMap() {{
        put("IP адрес", INTEGRATION_SERVICE_OM_IP_ADDRESS);
        put("Порт БД", INTEGRATION_SERVICE_OM_PORT_BD);
        put("Имя пользователя БД", INTEGRATION_SERVICE_OM_LOGIN_DB);
    }};

    @Override
    public void beforeAll(ExtensionContext context) {

        classTest = context.getTestClass().toString();

        testsBase.init();

        if(classTest.contains("TestParametersServer")) testsBase.openMS("Настройки","Сервер");

        if(classTest.contains("TestParametersTelephony")) testsBase.openMS("Настройки", "Телефония");

        if(classTest.contains("TestParametersMail")) testsBase.openMS("Настройки","Почта");

        if((classTest.contains("Skud") || classTest.contains("TestParametersIntegrationOMPage")) && SSHManager.isCheckQuerySSH(commandDBCheckSKUD)){
            testsBase.openMS("Настройки","Интеграция");
            skudPage = (SKUDPage) clickServiceType("СКУД");
            assertTrue(skudPage.deleteSKUD("СКУД"),
                    "После удаления, сервис СКУД найден в таблице Подключенные сервисы");
        }

        if(classTest.contains("TestParametersIntegrationOMPage")){
            testsBase.openMS("Настройки","Интеграция");
            skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_OM_TYPE);
            assertTrue(skudPage.settingsSKUD(mapInputValueConnectOM, INTEGRATION_SERVICE_OM_TYPE),
                    "Сервис СКУД Офис-Монитор не найден в тиблице 'Подключенные сервисы'");
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        String method = context.getTestMethod().toString();
        if(method.contains("IntegrationPage")) {
            if (method.contains("Open_Page")) testsBase.openMS("/settings/integration");
            else {
                testsBase.openMS("Настройки", "Интеграция");
                sleep(1000);
            }
        } else if (method.contains("TestGeozonesPage")) {
            if (method.contains("Open_Page")) testsBase.openMS("/settings/geozones");
            else testsBase.openMS("Настройки", "Геозоны");
        } else if (method.contains("TestMailPage")) {
            if (method.contains("Open_Page")) testsBase.openMS("/settings/mail");
            else testsBase.openMS("Настройки", "Почта");
        } else if (method.contains("TestMonitorSkud")) {
            if (method.contains("Status")) testsBase.openMS("Монитор");
            else testsBase.openMS("Настройки", "Интеграция");
        } else if (method.contains("TestServerPage")) {
            if (method.contains("Open_Page")) testsBase.openMS("/settings/web-server");
            else testsBase.openMS("Настройки", "Сервер");
        } else if (classTest.contains("TestTetraPage")) testsBase.openMS("Настройки","Интеграция");
        else if (method.contains("TestSNMPPage")) {
            if (method.contains("Open_Page")) testsBase.openMS("/settings/snmp");
            else testsBase.openMS("Настройки", "SNMP");
        } else if (method.contains("TestTelephonyPage")) {
            if (method.contains("Open_Page")) testsBase.openMS("/settings/telephony");
            testsBase.openMS("Настройки", "Телефония");
        } else if (method.contains("TestUserPage")) {
            if (method.contains("Login") || method.contains("Delete")) {
                Configuration.baseUrl = hostServer;
                open("/");
                logoutMS();
            } else if (method.contains("Open")) {
                testsBase.openMS();
                open("/settings/users");
            } else testsBase.openMS("Настройки", "Настройка СУ");
        } else if (method.contains("TestServicePage")) testsBase.openMS("Справочник");
        else if (method.contains("TestContactsPage")) testsBase.openMS("Справочник");
        else if(method.contains("TestParametersIntegrationOMPage")) {
            skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_OM_TYPE);
            clickButtonActionService(SETTINGS_BUTTON_SETTING);
        }
    }

    @Override
    public void afterEach(ExtensionContext context){
        String method = context.getTestMethod().toString();
        if(method.contains("TestParameters")){
            refresh();
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        classTest = context.getTestClass().toString();
        if(classTest.contains("TestParameters")){
            Selenide.close();
        }
    }
}
