package chat.ros.testing2;

import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.LoginPage;
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
import static chat.ros.testing2.data.LoginData.*;
import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourcesTests extends UserPage implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback, IntegrationPage {

    private WebDriver driver = null;
    private String classTest = null;
    private SKUDPage skudPage = null;
    private final String hostServer = "https://" + System.getProperty("hostserver") + ":" + System.getProperty("portms");
    private final String hostClient = "https://" + System.getProperty("hostserver");
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

        if(isProgressBar()) refresh();

        if(classTest.contains("TestUser")){
            refresh();
            if(loginPage.isLoginMS()) logoutMS();
        }

        if(classTest.contains("TestParametersServer")) testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки","Сервер");

        if(classTest.contains("TestParametersTelephony")) testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки", "Телефония");

        if(classTest.contains("TestParametersMail")) testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки","Почта");

        if((classTest.contains("Skud") || classTest.contains("TestParametersIntegrationOMPage")) && SSHManager.isCheckQuerySSH(commandDBCheckSKUD)){
            testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки","Интеграция");
            skudPage = (SKUDPage) clickServiceType("СКУД");
            assertTrue(skudPage.deleteSKUD("СКУД"),
                    "После удаления, сервис СКУД найден в таблице Подключенные сервисы");
        }

        if(classTest.contains("TestParametersIntegrationOMPage")){
            testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки","Интеграция");
            skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_OM_TYPE);
            assertTrue(skudPage.settingsSKUD(mapInputValueConnectOM, INTEGRATION_SERVICE_OM_TYPE),
                    "Сервис СКУД Офис-Монитор не найден в тиблице 'Подключенные сервисы'");
        }

        if(classTest.contains("TestReservationPage")) testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Обслуживание","Резервирование");
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        String method = context.getTestMethod().toString();
        if(method.contains("IntegrationPage")) {
            if (method.contains("Open_Page")) testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"/settings/integration");
            else {
                testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки", "Интеграция");
                sleep(1000);
            }
        } else if (method.contains("TestGeozonesPage")) {
            if (method.contains("Open_Page")) testsBase.openMS(USER_LOGIN_ADMIN,
                    USER_PASSWORD_ADMIN,
                    "/settings/geozones");
            else testsBase.openMS(USER_LOGIN_ADMIN,
                    USER_PASSWORD_ADMIN,
                    "Настройки", "Геозоны");
        } else if (method.contains("TestMailPage")) {
            if (method.contains("Open_Page")) testsBase.openMS(USER_LOGIN_ADMIN,
                    USER_PASSWORD_ADMIN,
                    "/settings/mail");
            else testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки", "Почта");
        } else if (method.contains("TestMonitorSkud")) {
            if (method.contains("Status")) testsBase.openMS(USER_LOGIN_ADMIN,
                    USER_PASSWORD_ADMIN,"Монитор");
            else testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки", "Интеграция");
        } else if (method.contains("TestServerPage")) {
            if (method.contains("Open_Page")) testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"/settings/web-server");
            else testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки", "Сервер");
        } else if (classTest.contains("TestTetraPage")) testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки","Интеграция");
        else if (method.contains("TestSNMPPage")) {
            if (method.contains("Open_Page")) testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"/settings/snmp");
            else testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки", "SNMP");
        } else if (method.contains("TestTelephonyPage")) {
            if (method.contains("Open_Page")) testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"/settings/telephony");
            testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки", "Телефония");
        } else if (method.contains("TestUser")) {
            if (method.contains("Login") || method.contains("Delete")) {
                Configuration.baseUrl = hostServer;
                open("/");
                logoutMS();
            }else{
                if (method.contains("Open_Page")) testsBase.openMS(LOGIN_AS_MS,
                        PASSWORD_AS_MS,
                        "/settings/users");
                else testsBase.openMS(LOGIN_AS_MS, PASSWORD_AS_MS,"Настройки");
            }
        } else if (method.contains("TestServicePage")) testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Справочник");
        else if (method.contains("TestContactsPage")) testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Справочник");
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
        if(classTest.contains("TestUser")){
            if(loginPage.isLoginMS()) logoutMS();
        }
        if(classTest.contains("TestParameters") || classTest.contains("Channel")){
            Selenide.close();
        }
    }
}
