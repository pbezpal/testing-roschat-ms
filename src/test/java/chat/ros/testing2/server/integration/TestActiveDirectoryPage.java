package chat.ros.testing2.server.integration;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.TestsBase;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.integration.ActiveDirectoryPage;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static org.testng.Assert.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Интеграция")
public class TestActiveDirectoryPage implements IntegrationPage {

    private ActiveDirectoryPage activeDirectoryPage = null;
    private final TestsBase testsBase = new TestsBase();
    private static boolean status_Add;
    private final Map<String, String> mapInputValueAD = new HashMap() {{
        put("Имя сервера", INTEGRATION_SERVICE_AD_SERVER);
        put("Порт", INTEGRATION_SERVICE_AD_PORT);
        put("Корневой элемент (Base DN)", INTEGRATION_SERVICE_AD_BASE_DN);
        put("Имя пользователя (Bind DN)", INTEGRATION_SERVICE_AD_USERNAME);
        put("Пароль", INTEGRATION_SERVICE_AD_PASSWORD);
    }};

    @BeforeAll
    static void setUp(){
        status_Add = false;
    }

    @Story(value = "Добавляем сервис Active Directory")
    @Description(value = "Переходим в раздел Интеграция, добавляем и настраиваем сервис Active Directory и проверяем," +
            " что сервис появился в табюлице Подключенные сервисы")
    @Test
    @Order(1)
    void test_Add_Service_Active_Directory(){
        testsBase.openMS("Настройки","Интеграция");
        activeDirectoryPage = (ActiveDirectoryPage) addIntegrationService(INTEGRATION_SERVICE_AD_TYPE);
        assertTrue(activeDirectoryPage.settingsActiveDirectory(mapInputValueAD), "Сервис Active Directory не был добавлен");
        status_Add = true;
    }

    @Story(value = "Синхронизация контактов c Active Directory")
    @Description(value = "Переходим в раздел Интеграция, переходим в сервис Active Directory и нажимаем Синхронизировать." +
            "Проверяем, что контакты синхронизировались.")
    @Test
    @Order(2)
    void test_Sync_Contacts_Active_Directory(){
        assertTrue(status_Add, "Не удалось добавить сервис AD");
        testsBase.openMS("Настройки","Интеграция");
        activeDirectoryPage = (ActiveDirectoryPage) clickServiceType(INTEGRATION_SERVICE_AD_TYPE);
        assertTrue(activeDirectoryPage.syncContacts(), "Ошибка при сихронизации контактов");
    }

    @Story(value = "Удаление Active Directory")
    @Description(value = "Переходим в раздел Интеграция, переходим в сервис Active Directory и нажимаем кнопку Удалить. " +
            "Проверяем, что сервис Active Directory пропал из таблицы Подключенные сервисы")
    @Test
    @Order(3)
    void test_Delete_Active_Directory(){
        assertTrue(status_Add, "Не удалось добавить сервис AD");
        testsBase.openMS("Настройки","Интеграция");
        activeDirectoryPage = (ActiveDirectoryPage) clickServiceType(INTEGRATION_SERVICE_AD_TYPE);
        assertTrue(activeDirectoryPage.deleteActiveDirectory(), "Сервис Active Directory не удалён");
    }
}
