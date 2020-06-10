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
@Feature(value = "Интеграция->PERCo")
public class TestIntegrationSkudPerco implements IntegrationPage, TestSuiteBase {

    private String classStatusServiceActive = "status active";
    private String classStatusServiceInactive = "status inactive";
    private SKUDPage skudPage;
    private Map<String, String> mapInputValueConnectPerco = new HashMap() {{
        put("Адрес модуля интеграции с PERCo", INTEGRATION_SERVICE_PERCO_IP_MODULE);
        put("Порт модуля интеграции с PERCo", INTEGRATION_SERVICE_PERCO_PORT_MODULE);
        put("Адрес PERCo сервера", INTEGRATION_SERVICE_PERCO_IP_SERVER);
        put("Порт PERCo сервера", INTEGRATION_SERVICE_PERCO_PORT_SERVER);
        put("Имя пользователя", INTEGRATION_SERVICE_PERCO_USERNAME);
        put("Ключ шифрования", INTEGRATION_SERVICE_PERCO_KEY);
    }};
    private Map<String, String> mapInputValueDisconnectPerco = new HashMap() {{
        put("Адрес модуля интеграции с PERCo", INTEGRATION_SERVICE_PERCO_WRONG_IP_MODULE);
        put("Порт модуля интеграции с PERCo", INTEGRATION_SERVICE_PERCO_PORT_MODULE);
        put("Адрес PERCo сервера", INTEGRATION_SERVICE_PERCO_IP_SERVER);
        put("Порт PERCo сервера", INTEGRATION_SERVICE_PERCO_PORT_SERVER);
        put("Имя пользователя", INTEGRATION_SERVICE_PERCO_USERNAME);
        put("Ключ шифрования", INTEGRATION_SERVICE_PERCO_KEY);
    }};

    @BeforeMethod
    public void beforeTest(Method method){
        if(method.toString().contains("Status")) TestsBase.getInstance().openMS("Монитор");
        else TestsBase.getInstance().openMS("Настройки","Интеграция");
    }

    @Story(value = "Добавляем сервис СКУД PERCo")
    @Description(value = "Переходим в раздел Интеграция, добавляем и настраиваем сервис СКУД PERCo и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test(priority = 2,groups = {"PERCo"})
    void test_Add_Service(){
        skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_PERCO_TYPE);
        assertTrue(skudPage.settingsSKUD(mapInputValueConnectPerco, INTEGRATION_SERVICE_PERCO_TYPE),
                "После добавления сервис СКУД PERCo не найден в тиблице 'Подключенные сервисы'");
    }

    @Story(value = "Состояние СКУД PERCo, при корректных настройках подключения")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Вместо надписи СКУД появилась надпись PERCo \n" +
            "2. Состояние PERCo - активно. Зелённый кружок.")
    @Test(priority = 1, dependsOnMethods = {"test_Add_Service"})
    void test_Status_Perco_Active(){
        assertTrue(MonitoringPage.isStatusService(INTEGRATION_SERVICE_PERCO_TYPE, classStatusServiceActive),
                "Состояни СКУД PERCo - неактивно, либо отсутсвтует сервис ОPERCo");
    }

    @Story(value = "Синхронизация контактов со СКУД PERCo")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис СКУД PERCo и нажимаем Синхронизировать")
    @Test(groups = {"Sync"},dependsOnMethods = {"test_Add_Service"},enabled = false)
    void test_Sync_Contacts(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_PERCO_TYPE);
        assertTrue(skudPage.syncContacts(), "Ошибка при сихронизации контактов со СКУД PERCo");
    }

    @Story(value = "Настраиваем СКУД PERCo с некорректными данными")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис PERCo и вводим некорректные " +
            "данные для подключения.")
    @Test(priority = 2, dependsOnMethods = {"test_Add_Service"})
    void test_Change_Data_Disconnect_SKUD(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_PERCO_TYPE);
        assertTrue(skudPage.settingsSKUD(mapInputValueDisconnectPerco, INTEGRATION_SERVICE_PERCO_TYPE),
                "После редактирования настроек сервис СКУД PERCo не найден в таблице Подключенные сервисы");
    }

    @Story(value = "Состояние СКУД PERCo, при некорректных настройках подключения")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Осталась надпись PERCo \n" +
            "2. Состояние СКУД PERCo - неактивно. Красный кружок.")
    @Test(dependsOnMethods = {"test_Change_Data_Disconnect_SKUD"})
    void test_Status_Perco_Inactive(){
        assertTrue(MonitoringPage.isStatusService(INTEGRATION_SERVICE_PERCO_TYPE, classStatusServiceInactive),
                "Состояни СКУД PERCo - активно, либо отсутсвтует сервис PERCo");
    }

    @Story(value = "Удаляем СКУД PERCo")
    @Description(value = "Переходим в раздел Настройки -> Интеграция, переходим в сервис СКУД PERCo, нажимаем" +
            " кнопку удалить, подтвержаем жействие и перезагружаем сервисы. Проверяем, что сервис СКУД PERCo" +
            " успешно удалён.")
    @Test(priority = 3,dependsOnMethods = {"test_Add_Service"})
    void test_Delete_Perco(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_PERCO_TYPE);
        assertTrue(skudPage.deleteSKUD(INTEGRATION_SERVICE_PERCO_TYPE),
                "После удаления, сервис СКУД PERCo найден в таблице Подключенные сервисы");
    }

    @Story(value = "Состояние СКУД, после удаления СКУД PERCo")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Вместо надписи PERCo появилась надпись СКУД \n" +
            "2. Состояние СКУД - неактивно. Красный кружок.")
    @Test(dependsOnMethods = {"test_Delete_Perco"})
    void test_Status_SKUD_After_Delete_SKUD(){
        assertTrue(MonitoringPage.isStatusService(MONITORING_SERVICE_SKUD, classStatusServiceInactive),
                "Состояни СКУД - подключен, либо отсутсвтует сервис СКУД");
    }
}
