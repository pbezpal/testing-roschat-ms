package chat.ros.testing2.server.integration;

import chat.ros.testing2.ResourcesTests;
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
@Feature(value = "Интеграция->PERCo")
public class TestSkudPerco implements IntegrationPage, TestSuiteBase {

    private boolean status = false;
    private SKUDPage skudPage;
    private Map<String, String> mapInputValueConnectPerco = new HashMap() {{
        put("Адрес модуля интеграции с PERCo", INTEGRATION_SERVICE_PERCO_IP_MODULE);
        put("Порт модуля интеграции с PERCo", INTEGRATION_SERVICE_PERCO_PORT_MODULE);
        put("Адрес PERCo сервера", INTEGRATION_SERVICE_PERCO_IP_SERVER);
        put("Порт PERCo сервера", INTEGRATION_SERVICE_PERCO_PORT_SERVER);
        put("Имя пользователя", INTEGRATION_SERVICE_PERCO_USERNAME);
        put("Ключ шифрования", INTEGRATION_SERVICE_PERCO_KEY);
    }};

    @BeforeEach
    public void beforeTest(){
        String method = new Object(){}.getClass().getEnclosingMethod().getName();
        if( ! method.contains("test_Add_Service")){
            assertTrue(status,"СКУД PERCo не был добавлен");
        }
        getInstanceTestBase().openMS("Настройки","Интеграция");
    }

    @Story(value = "Добавляем сервис СКУД PERCo")
    @Description(value = "Переходим в раздел Интеграция, добавляем и настраиваем сервис СКУД PERCo и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test
    @Order(1)
    void test_Add_Service(){
        skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_PERCO_TYPE);
        assertTrue(skudPage.settingsSKUD(mapInputValueConnectPerco, INTEGRATION_SERVICE_PERCO_TYPE),
                "После добавления сервис СКУД PERCo не найден в тиблице 'Подключенные сервисы'");
    }

    @Story(value = "Синхронизация контактов со СКУД PERCo")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис СКУД PERCo и нажимаем Синхронизировать")
    @Test
    @Order(2)
    void test_Sync_Contacts(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_PERCO_TYPE);
        assertTrue(skudPage.syncContacts(), "Ошибка при сихронизации контактов со СКУД PERCo");
    }

    @Story(value = "Удаляем СКУД PERCo")
    @Description(value = "Переходим в раздел Настройки -> Интеграция, переходим в сервис СКУД PERCo, нажимаем" +
            " кнопку удалить, подтвержаем жействие и перезагружаем сервисы. Проверяем, что сервис СКУД PERCo" +
            " успешно удалён.")
    @Test
    @Order(3)
    void test_Delete_Perco(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_PERCO_TYPE);
        assertTrue(skudPage.deleteSKUD(INTEGRATION_SERVICE_PERCO_TYPE),
                "После удаления, сервис СКУД PERCo найден в таблице Подключенные сервисы");
    }
}
