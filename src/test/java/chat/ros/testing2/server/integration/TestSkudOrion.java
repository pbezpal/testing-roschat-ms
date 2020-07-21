package chat.ros.testing2.server.integration;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.TestSuiteBase;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import chat.ros.testing2.server.settings.integration.SKUDPage;
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
@Feature(value = "Интеграция->Орион")
public class TestSkudOrion implements IntegrationPage, TestSuiteBase {

    private SKUDPage skudPage;
    private boolean status = true;
    private Map<String, String> mapInputValueConnectOrion = new HashMap() {{
        put("IP адрес", INTEGRATION_SERVICE_ORION_IP_ADDRESS);
        put("Порт", INTEGRATION_SERVICE_ORION_PORT);
        put("Исходящий порт", INTEGRATION_SERVICE_ORION_OUTGOING_PORT);
    }};

    @BeforeEach
    public void beforeTest(){
        String method = new Object(){}.getClass().getEnclosingMethod().getName();
        if( ! method.contains("test_Add_Service")){
            assertTrue(status,"СКУД ОРИОН не был добавлен");
        }
        getInstanceTestBase().openMS("Настройки","Интеграция");
    }

    @Story(value = "Добавляем сервис СКУД ОРИОН")
    @Description(value = "Переходим в раздел Интеграция, добавляем и настраиваем сервис СКУД ОРИОН и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test
    @Order(1)
    void test_Add_Service(){
        status = false;
        skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_ORION_TYPE);
        assertTrue(skudPage.settingsSKUD(mapInputValueConnectOrion, INTEGRATION_SERVICE_ORION_TYPE),
                "После добавления сервис СКУД ОРИОН не найден в тиблице 'Подключенные сервисы'");
        status = true;
    }

    @Story(value = "Синхронизация контактов со СКУД ОРИОН")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис СКУД ОРИОН и нажимаем Синхронизировать")
    @Test
    @Order(2)
    void test_Sync_Contacts(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_ORION_TYPE);
        assertTrue(skudPage.syncContacts(), "Ошибка при сихронизации контактов со СКУД ОРИОН");
    }

    @Story(value = "Удаляем СКУД ОРИОН")
    @Description(value = "Переходим в раздел Настройки -> Интеграция, переходим в сервис СКУД ОРИОН, нажимаем" +
            " кнопку удалить, подтвержаем жействие и перезагружаем сервисы. Проверяем, что сервис СКУД ОРИОН" +
            " успешно удалён.")
    @Test
    @Order(3)
    void test_Delete_Orion(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_ORION_TYPE);
        assertTrue(skudPage.deleteSKUD(INTEGRATION_SERVICE_ORION_TYPE),
                "После удаления, сервис СКУД ОРИОН найден в таблице Подключенные сервисы");
    }
}
