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
@Feature(value = "Интеграция->Орион")
public class TestIntegrationSkudOrionMonitor implements IntegrationPage, TestSuiteBase {

    private String classStatusServiceActive = "status active";
    private String classStatusServiceInactive = "status inactive";
    private SKUDPage skudPage;
    private Map<String, String> mapInputValueConnectOrion = new HashMap() {{
        put("IP адрес", INTEGRATION_SERVICE_ORION_IP_ADDRESS);
        put("Порт", INTEGRATION_SERVICE_ORION_PORT);
        put("Исходящий порт", INTEGRATION_SERVICE_ORION_OUTGOING_PORT);
    }};
    private Map<String, String> mapInputValueDisconnectOrion = new HashMap() {{
        put("IP адрес", INTEGRATION_SERVICE_ORION_WRONG_IP_ADDRESS);
        put("Порт", INTEGRATION_SERVICE_ORION_PORT);
        put("Исходящий порт", INTEGRATION_SERVICE_ORION_OUTGOING_PORT);
    }};

    @BeforeMethod
    public void beforeTest(Method method){
        if(method.toString().contains("Status")) TestsBase.getInstance().openMS("Монитор");
        else TestsBase.getInstance().openMS("Настройки","Интеграция");
    }

    @Story(value = "Добавляем сервис СКУД ОРИОН")
    @Description(value = "Переходим в раздел Интеграция, добавляем и настраиваем сервис СКУД ОРИОН и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test(priority = 1,groups = {"ORION"})
    void test_Add_Service(){
        skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_ORION_TYPE);
        assertTrue(skudPage.settingsSKUD(mapInputValueConnectOrion, INTEGRATION_SERVICE_ORION_TYPE),
                "После добавления сервис СКУД ОРИОН не найден в тиблице 'Подключенные сервисы'");
    }

    @Story(value = "Состояние СКУД ОРИОН, при корректных настройках подключения")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Вместо надписи СКУД появилась надпись ОРИОН \n" +
            "2. Состояние ОРИОН - активно. Зелённый кружок.")
    @Test(priority = 1, dependsOnMethods = {"test_Add_Service"})
    void test_Status_Orion_Active(){
        assertTrue(MonitoringPage.isStatusService(INTEGRATION_SERVICE_ORION_TYPE, classStatusServiceActive),
                "Состояни СКУД ОРИОН - неактивно, либо отсутсвтует сервис ОРИОН");
    }

    @Story(value = "Синхронизация контактов со СКУД ОРИОН")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис СКУД ОРИОН и нажимаем Синхронизировать")
    @Test(groups = {"Sync"},dependsOnMethods = {"test_Add_Service"},enabled = false)
    void test_Sync_Contacts(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_ORION_TYPE);
        assertTrue(skudPage.syncContacts(), "Ошибка при сихронизации контактов со СКУД ОРИОН");
    }

    @Story(value = "Настраиваем СКУД ОРИОН с некорректными данными")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис ОРИОН и вводим некорректные " +
            "данные для подключения.")
    @Test(priority = 2, dependsOnMethods = {"test_Add_Service"})
    void test_Change_Data_Disconnect_SKUD(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_ORION_TYPE);
        assertTrue(skudPage.settingsSKUD(mapInputValueDisconnectOrion, INTEGRATION_SERVICE_ORION_TYPE),
                "После редактирования настроек сервис СКУД ОРИОН не найден в таблице Подключенные сервисы");
    }

    @Story(value = "Состояние СКУД ОРИОН, при некорректных настройках подключения")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Осталась надпись ОРИОН \n" +
            "2. Состояние СКУД ОРИОН - неактивно. Красный кружок.")
    @Test(dependsOnMethods = {"test_Change_Data_Disconnect_SKUD"})
    void test_Status_Orion_Inactive(){
        assertTrue(MonitoringPage.isStatusService(INTEGRATION_SERVICE_ORION_TYPE, classStatusServiceInactive),
                "Состояни СКУД ОРИОН - активно, либо отсутсвтует сервис ОРИОН");
    }

    @Story(value = "Удаляем СКУД ОРИОН")
    @Description(value = "Переходим в раздел Настройки -> Интеграция, переходим в сервис СКУД ОРИОН, нажимаем" +
            " кнопку удалить, подтвержаем жействие и перезагружаем сервисы. Проверяем, что сервис СКУД ОРИОН" +
            " успешно удалён.")
    @Test(priority = 3,dependsOnMethods = {"test_Add_Service"},groups = {"Delete_Orion"})
    void test_Delete_Orion(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_ORION_TYPE);
        assertTrue(skudPage.deleteSKUD(INTEGRATION_SERVICE_ORION_TYPE),
                "После удаления, сервис СКУД ОРИОН найден в таблице Подключенные сервисы");
    }

    @Story(value = "Состояние СКУД, после удаления СКУД ОРИОН")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Вместо надписи ОРИОН появилась надпись СКУД \n" +
            "2. Состояние СКУД - неактивно. Красный кружок.")
    @Test(dependsOnMethods = {"test_Delete_Orion"})
    void test_Status_SKUD_After_Delete_OM(){
        assertTrue(MonitoringPage.isStatusService(MONITORING_SERVICE_SKUD, classStatusServiceInactive),
                "Состояни СКУД - подключен, либо отсутсвтует сервис СКУД");
    }
}