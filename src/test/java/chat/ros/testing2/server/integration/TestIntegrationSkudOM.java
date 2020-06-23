package chat.ros.testing2.server.integration;

import chat.ros.testing2.TestSuiteBase;
import chat.ros.testing2.TestsBase;
import chat.ros.testing2.monitoring.MonitoringPage;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import chat.ros.testing2.server.settings.integration.SKUDPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.MonitoringData.MONITORING_SERVICE_SKUD;
import static chat.ros.testing2.data.SettingsData.*;
import static org.testng.Assert.assertTrue;

@Epic(value = "Настройки")
@Feature(value = "Интеграция->Офис-Монитор")
public class TestIntegrationSkudOM implements IntegrationPage, TestSuiteBase {

    private SKUDPage skudPage;
    private Map<String, String> mapInputValueConnectOM = new HashMap() {{
        put("IP адрес", INTEGRATION_SERVICE_OM_IP_ADDRESS);
        put("Порт БД", INTEGRATION_SERVICE_OM_PORT_BD);
        put("Имя пользователя БД", INTEGRATION_SERVICE_OM_LOGIN_DB);
    }};

    @BeforeMethod
    public void beforeTest(Method method){
        if(method.toString().contains("Status")) TestsBase.getInstance().openMS("Монитор");
        else TestsBase.getInstance().openMS("Настройки","Интеграция");
    }

    @Story(value = "Добавляем сервис Офис-Монитор")
    @Description(value = "Переходим в раздел Интеграция, добавляем и настраиваем сервис СКУД Офис-Монитор и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test
    void test_Add_Service(){
        skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_OM_TYPE);
        assertTrue(skudPage.settingsSKUD(mapInputValueConnectOM, INTEGRATION_SERVICE_OM_TYPE),
                "Сервис СКУД Офис-Монитор не найден в тиблице 'Подключенные сервисы'");
    }

    @Story(value = "Синхронизация контактов со СКУД Офис-Монитор")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис Офис-Монитор и нажимаем Синхронизировать")
    @Test(priority = 1, groups = {"Sync"},dependsOnMethods = {"test_Add_Service"})
    void test_Sync_Contacts(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_OM_TYPE);
        assertTrue(skudPage.syncContacts(), "Ошибка при сихронизации контактов со СКУД Офис-Монитор");
    }

    @Story(value = "Удаляем СКУД Офис-Монитор")
    @Description(value = "Переходим в раздел Настройки -> Интеграция, переходим в сервис СКУД Офис-Монитор, нажимаем" +
            " кнопку удалить, подтвержаем жействие и перезагружаем сервисы. Проверяем, что сервис СКУД Офис-Монитор" +
            " успешно удалён.")
    @Test(priority = 2, dependsOnMethods = {"test_Add_Service"},groups = {"Delete_OM"})
    void test_Delete_OM(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_OM_TYPE);
        assertTrue(skudPage.deleteSKUD(INTEGRATION_SERVICE_OM_TYPE),
                "После удаления, сервис СКУД Офис-Мониторинг найден в таблице Подключенные сервисы");
    }
}
