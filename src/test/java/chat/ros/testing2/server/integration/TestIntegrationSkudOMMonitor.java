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
public class TestIntegrationSkudOMMonitor implements IntegrationPage, TestSuiteBase {

    private String classStatusServiceActive = "status active";
    private String classStatusServiceInactive = "status inactive";
    private SKUDPage skudPage;
    private Map<String, String> mapInputValueConnectOM = new HashMap() {{
        put("IP адрес", INTEGRATION_SERVICE_OM_IP_ADDRESS);
        put("Порт БД", INTEGRATION_SERVICE_OM_PORT_BD);
        put("Имя пользователя БД", INTEGRATION_SERVICE_OM_LOGIN_DB);
    }};

    private Map<String, String> mapInputValueDisconnectOM = new HashMap() {{
        put("IP адрес", INTEGRATION_SERVICE_OM_WRONG_IP_ADDRESS);
        put("Порт БД", INTEGRATION_SERVICE_OM_PORT_BD);
        put("Имя пользователя БД", INTEGRATION_SERVICE_OM_LOGIN_DB);
    }};

    @BeforeMethod
    public void beforeTest(Method method){
        if(method.toString().contains("Status")) TestsBase.getInstance().openMS("Монитор");
        else TestsBase.getInstance().openMS("Настройки","Интеграция");
    }

    @Story(value = "Состояние СКУД, перед добавлением СКУД Офис-Монитор")
    @Description(value = "Переходим в раздел Монитор и проверяем, что состояние сервиса СКУД - неактивно," +
            "до настройки СКУД в разделе Интеграция. Красный кружок.")
    @Test(priority = 1)
    void test_Status_Skud_Before_Add_Skud(){
        assertTrue(MonitoringPage.isStatusService(MONITORING_SERVICE_SKUD, classStatusServiceInactive),
                "Состояни СКУД - активно, либо отсуствут сервис СКУД");
    }

    @Story(value = "Добавляем сервис Офис-Монитор")
    @Description(value = "Переходим в раздел Интеграция, добавляем и настраиваем сервис СКУД Офис-Монитор и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test(priority = 2)
    void test_Add_Service(){
        skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_OM_TYPE);
        assertTrue(skudPage.settingsSKUD(mapInputValueConnectOM, INTEGRATION_SERVICE_OM_TYPE),
                "Сервис СКУД Офис-Монитор не найден в тиблице 'Подключенные сервисы'");
    }

    @Story(value = "Состояние СКУД Офис-Монитор, при корректных настройках подключения")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Вместо надписи СКУД появилась надпись Офис-Монитор \n" +
            "2. Состояние Офис-Монитор - активно. Зелённый кружок.")
    @Test(priority = 1, dependsOnMethods = {"test_Add_Service"})
    void test_Status_OM_Active(){
        assertTrue(MonitoringPage.isStatusService(INTEGRATION_SERVICE_OM_TYPE, classStatusServiceActive),
                "Состояни СКУД Офис-Монитор - неактивно, либо отсутсвтует сервис Офис-Монитор");
    }

    @Story(value = "Синхронизация контактов со СКУД Офис-Монитор")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис Офис-Монитор и нажимаем Синхронизировать")
    @Test(groups = {"Sync"},dependsOnMethods = {"test_Add_Service"})
    void test_Sync_Contacts(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_OM_TYPE);
        assertTrue(skudPage.syncContacts(), "Ошибка при сихронизации контактов со СКУД Офис-Монитор");
    }

    @Story(value = "Настраиваем СКУД Офис-Монитор с некорректными данными")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис Офис-Монитор и вводим некорректные " +
            "данные для подключения.")
    @Test(priority = 2, dependsOnMethods = {"test_Add_Service"})
    void test_Change_Data_Disconnect_SKUD(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_OM_TYPE);
        assertTrue(skudPage.settingsSKUD(mapInputValueDisconnectOM, INTEGRATION_SERVICE_OM_TYPE),
                "После редактирования настроек сервис СКУД Офис-Монитор не найден в таблице Подключенные сервисы");
    }

    @Story(value = "Состояние СКУД Офис-Монитор, при некорректных настройках подключения")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Осталась надпись Офис-Монитор \n" +
            "2. Состояние Офис-Монитор - неактивно. Красный кружок.")
    @Test(dependsOnMethods = {"test_Change_Data_Disconnect_SKUD"})
    void test_Status_OM_Inactive(){
        assertTrue(MonitoringPage.isStatusService(INTEGRATION_SERVICE_OM_TYPE, classStatusServiceInactive),
                "Состояни СКУД Офис-Монитор - активно, либо отсутсвтует сервис Офис-Монитор");
    }

    @Story(value = "Удаляем СКУД Офис-Монитор")
    @Description(value = "Переходим в раздел Настройки -> Интеграция, переходим в сервис СКУД Офис-Монитор, нажимаем" +
            " кнопку удалить, подтвержаем жействие и перезагружаем сервисы. Проверяем, что сервис СКУД Офис-Монитор" +
            " успешно удалён.")
    @Test(priority = 3,dependsOnMethods = {"test_Add_Service"},groups = {"Delete_OM"})
    void test_Delete_OM(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_OM_TYPE);
        assertTrue(skudPage.deleteSKUD(INTEGRATION_SERVICE_OM_TYPE),
                "После удаления, сервис СКУД Офис-Мониторинг найден в таблице Подключенные сервисы");
    }

    @Story(value = "Состояние СКУД, после удаления СКУД Офис-Мониторинг")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Вместо надписи Офис-Монитор появилась надпись СКУД \n" +
            "2. Состояние СКУД - неактивно. Красный кружок.")
    @Test(dependsOnMethods = {"test_Delete_OM"})
    void test_Status_SKUD_After_Delete_SKUD(){
        assertTrue(MonitoringPage.isStatusService(MONITORING_SERVICE_SKUD, classStatusServiceInactive),
                "Состояни СКУД - активно, либо отсутсвтует сервис СКУД");
    }

}
