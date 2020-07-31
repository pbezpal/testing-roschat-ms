package chat.ros.testing2;

import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.LoginPage;
import chat.ros.testing2.server.settings.MailPage;
import chat.ros.testing2.server.settings.ServerPage;
import chat.ros.testing2.server.settings.UserPage;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import chat.ros.testing2.server.settings.integration.SKUDPage;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.HelperData.commandDBCheckSKUD;
import static chat.ros.testing2.data.LoginData.HOST_SERVER;
import static chat.ros.testing2.data.LoginData.PORT_SERVER;
import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourcesTests extends UserPage implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, IntegrationPage {

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
    private final Map<String, String> mapInputValueConnect = new HashMap() {{
        //put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, HOST_SERVER);
        put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, "testing2.ros.chat");
        put(SERVER_CONNECT_INPUT_HTTP_PORT, SERVER_CONNECT_HTTP_PORT);
        put(SERVER_CONNECT_INPUT_HTTPS_PORT, SERVER_CONNECT_HTTPS_PORT);
        put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, SERVER_CONNECT_WEBSOCKET_PORT);
    }};
    private final Map<String, String> mapInputValuePush = new HashMap() {{
        put(SERVER_PUSH_INPUT_HOST, SERVER_PUSH_HOST_SERVER);
        put(SERVER_PUSH_INPUT_LOGIN, SERVER_PUSH_LOGIN_SERVER);
        put(SERVER_PUSH_INPUT_PORT, SERVER_PUSH_PORT_SERVER);
        put(SERVER_PUSH_INPUT_PASSWORD, SERVER_PUSH_PASSWORD_SERVER);
    }};
    private final Map<String, String> mapInputValueNetwork = new HashMap() {{
        put(TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS, TELEPHONY_NETWORK_PUBLIC_ADDRESS);
        put(TELEPHONY_NETWORK_INPUT_FRONT_DEV, TELEPHONY_NETWORK_FRONT_IP);
        put(TELEPHONY_NETWORK_INPUT_INSIDE_DEV, TELEPHONY_NETWORK_INSIDE_IP);
    }};
    private final Map<String, String> mapInputValueTurnServer = new HashMap() {{
        put(TELEPHONY_TURN_INPUT_MIN_PORT, TELEPHONY_TURN_MIN_PORT);
        put(TELEPHONY_TURN_INPUT_MAX_PORT, TELEPHONY_TURN_MAX_PORT);
        put(TELEPHONY_TURN_INPUT_SECRET, TELEPHONY_TURN_SECRET);
    }};

    @Override
    public void beforeAll(ExtensionContext context) {

        classTest = context.getTestClass().toString();

        testsBase.init();

        if(classTest.contains("TestParametersServerConnectPage")){
            testsBase.openMS("Настройки","Сервер");
            ServerPage serverPage = new ServerPage();
            setSettingsServer(mapInputValueConnect, SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
            clickButtonSave();
            clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        }

        if(classTest.contains("TestParametersServerPushPage")){
            testsBase.openMS("Настройки","Сервер");
            setSettingsServer(mapInputValuePush, SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
            clickButtonSave();
            clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        }

        if(classTest.contains("TestParametersTelephonyPage")){
            testsBase.openMS("Настройки", "Телефония");
            setSettingsServer(mapInputValueNetwork, TELEPHONY_NETWORK_TITLE_FORM, SETTINGS_BUTTON_SETTING);
            clickButtonSave();
            clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
            refresh();
            setSettingsServer(mapInputValueTurnServer, TELEPHONY_TURN_TITLE_FORM, SETTINGS_BUTTON_SETTING);
            clickButtonSave();
            clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        }

        if(classTest.contains("TestParametersMailPage")){
            testsBase.openMS("Настройки","Почта");
            MailPage mailPage = new MailPage();
            mailPage.settingsMailServer(mailPage.getSettingsMailServer(MAIL_INFOTEK_SERVER, MAIL_INFOTEK_USERNAME,
                    MAIL_INFOTEK_PASSWORD, MAIL_PORT_NO_SECURITY, MAIL_INFOTEK_FROM_USER, MAIL_INFOTEK_FROM_MAIL),
                    MAIL_TYPE_SECURITY_NO);
        }

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
}
