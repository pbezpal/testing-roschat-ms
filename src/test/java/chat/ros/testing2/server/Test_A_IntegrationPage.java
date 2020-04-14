package chat.ros.testing2.server;

import chat.ros.testing2.RecourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.integration.ActiveDirectoryPage;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import chat.ros.testing2.server.settings.integration.OfficeMonitorPage;
import chat.ros.testing2.server.settings.integration.TetraPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic(value = "Настройки")
@Feature(value = "Интеграция")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(RecourcesTests.class)
@ExtendWith(WatcherTests.class)
public class Test_A_IntegrationPage implements IntegrationPage {

    private TetraPage tetraPage;
    private OfficeMonitorPage officeMonitorPage;
    private ActiveDirectoryPage activeDirectoryPage;
    private Map<String, String> mapInputValueOM = new HashMap() {{
        put("IP адрес", INTEGRATION_SERVICE_OM_IP_ADDRESS);
        put("Порт БД", INTEGRATION_SERVICE_OM_PORT_BD);
        put("Имя пользователя БД", INTEGRATION_SERVICE_OM_LOGIN_DB);
    }};
    private Map<String, String> mapInputValueTetra = new HashMap() {{
        put("Название", INTEGRATION_SERVICE_TETRA_NAME);
        put("Описание", INTEGRATION_SERVICE_TETRA_DESCRIPTION);
    }};
    private Map<String, String> mapInputValueAD = new HashMap() {{
        put("Имя сервера", INTEGRATION_SERVICE_AD_SERVER);
        put("Порт", INTEGRATION_SERVICE_AD_PORT);
        put("Корневой элемент (Base DN)", INTEGRATION_SERVICE_AD_BASE_DN);
        put("Имя пользователя (Bind DN)", INTEGRATION_SERVICE_AD_USERNAME);
        put("Пароль", INTEGRATION_SERVICE_AD_PASSWORD);
    }};

    @Story(value = "Добавляем сервис МиниКом TETRA")
    @Description(value = "Переходим в раздел Интеграция, добавляем сервис МиниКом TETRA и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test
    @Order(1)
    void test_Add_Service_Tetra(){
        tetraPage = (TetraPage) addIntegrationService(INTEGRATION_SERVICE_TETRA_TYPE);
        assertTrue(tetraPage.addTetraServer(mapInputValueTetra), "Сервис тетра не был добавлен");
    }

    @Story(value = "Добавляем сервис Офис-Монитор")
    @Description(value = "Переходим в раздел Интеграция, добавляем и настраиваем сервис Офис-Монитор и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test
    @Order(2)
    void test_Add_Service_Office_Monitor(){
        officeMonitorPage = (OfficeMonitorPage) addIntegrationService(INTEGRATION_SERVICE_OM_TYPE);
        assertTrue(officeMonitorPage.settingsOfficeMonitor(mapInputValueOM), "Сервис Офис-Монитор не был добавлен");
    }

    @Story(value = "Добавляем сервис Active Directory")
    @Description(value = "Переходим в раздел Интеграция, добавляем и настраиваем сервис Active Directory и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test
    @Order(3)
    void test_Add_Service_Active_Directory(){
        activeDirectoryPage = (ActiveDirectoryPage) addIntegrationService(INTEGRATION_SERVICE_AD_TYPE);
        assertTrue(activeDirectoryPage.settingsActiveDirectory(mapInputValueAD), "Сервис Active Directory не был добавлен");
    }

    @Story(value = "Синхронизация контактов c Active Directory")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис Active Directory и нажимаем Синхронизировать")
    @Test
    @Order(4)
    void test_Sync_Contacts_Active_Directory(){
        activeDirectoryPage = (ActiveDirectoryPage) clickServiceType(INTEGRATION_SERVICE_AD_TYPE);
        assertTrue(activeDirectoryPage.syncContacts(), "Ошибка при сихронизации контактов");
    }

    @Story(value = "Синхронизация контактов в Офис-Монитор")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис Офис-Монитор и нажимаем Синхронизировать")
    @Test
    @Order(4)
    void test_Sync_Contacts_Office_Monitor(){
        officeMonitorPage = (OfficeMonitorPage) clickServiceType(INTEGRATION_SERVICE_OM_TYPE);
        assertTrue(officeMonitorPage.syncContacts(), "Ошибка при сихронизации контактов");
    }
}
