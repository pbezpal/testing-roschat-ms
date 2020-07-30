package chat.ros.testing2.server.integration;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.monitoring.MonitoringPage;
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

import static chat.ros.testing2.data.MonitoringData.MONITORING_SERVICE_SKUD;
import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Интеграция->Офис-Монитор")
public class TestMonitorSkudOM implements IntegrationPage {

    private final String classStatusServiceActive = "status active";
    private final String classStatusServiceInactive = "status inactive";
    private SKUDPage skudPage = null;
    private static boolean status_Add;
    private static boolean status_Sync;
    private static boolean status_Disconnect;
    private static boolean status_Delete;
    private final Map<String, String> mapInputValueConnectOM = new HashMap() {{
        put("IP адрес", INTEGRATION_SERVICE_OM_IP_ADDRESS);
        put("Порт БД", INTEGRATION_SERVICE_OM_PORT_BD);
        put("Имя пользователя БД", INTEGRATION_SERVICE_OM_LOGIN_DB);
    }};

    private final Map<String, String> mapInputValueDisconnectOM = new HashMap() {{
        put("IP адрес", INTEGRATION_SERVICE_OM_WRONG_IP_ADDRESS);
        put("Порт БД", INTEGRATION_SERVICE_OM_PORT_BD);
        put("Имя пользователя БД", INTEGRATION_SERVICE_OM_LOGIN_DB);
    }};

    @BeforeAll
    static void setUp(){
        status_Add = false;
        status_Sync = false;
        status_Disconnect = false;
        status_Delete = false;
    }

    @Story(value = "Состояние СКУД, перед добавлением СКУД Офис-Монитор")
    @Description(value = "Переходим в раздел Монитор и проверяем, что состояние сервиса СКУД - неактивно," +
            "до настройки СКУД в разделе Интеграция. Красный кружок.")
    @Test
    @Order(1)
    void test_Status_Skud_Before_Add_Skud(){
        assertTrue(MonitoringPage.isStatusService(MONITORING_SERVICE_SKUD, classStatusServiceInactive),
                "Состояни СКУД - активно, либо отсуствут сервис СКУД");
    }

    @Story(value = "Добавляем сервис Офис-Монитор")
    @Description(value = "Переходим в раздел Интеграция, добавляем и настраиваем сервис СКУД Офис-Монитор и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test
    @Order(2)
    void test_Add_Service(){
        skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_OM_TYPE);
        assertTrue(skudPage.settingsSKUD(mapInputValueConnectOM, INTEGRATION_SERVICE_OM_TYPE),
                "Сервис СКУД Офис-Монитор не найден в тиблице 'Подключенные сервисы'");
        status_Add = true;
    }

    @Story(value = "Синхронизация контактов со СКУД Офис-Монитор")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис Офис-Монитор и нажимаем Синхронизировать")
    @Test
    @Order(3)
    void test_Sync_Contacts() {
        assertTrue(status_Add,"СКУД ОМ не добавлен");
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_OM_TYPE);
        assertTrue(skudPage.syncContacts(), "Ошибка при сихронизации контактов со СКУД Офис-Монитор");
        status_Sync = true;
    }

    @Story(value = "Состояние СКУД Офис-Монитор, при корректных настройках подключения")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Вместо надписи СКУД появилась надпись Офис-Монитор \n" +
            "2. Состояние Офис-Монитор - активно. Зелённый кружок.")
    @Test
    @Order(4)
    void test_Status_OM_Active(){
        assertTrue(status_Add,"СКУД ОМ не добавлен");
        assertTrue(status_Sync,"Ошибка при синхронизации контактов со СКУД ОМ");
        assertTrue(MonitoringPage.isStatusService(INTEGRATION_SERVICE_OM_TYPE, classStatusServiceActive),
                "Состояни СКУД Офис-Монитор - неактивно, либо отсутсвтует сервис Офис-Монитор");
    }

    @Story(value = "Настраиваем СКУД Офис-Монитор с некорректными данными")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис Офис-Монитор и вводим некорректные " +
            "данные для подключения.")
    @Test
    @Order(5)
    void test_Change_Data_Disconnect_SKUD(){
        assertTrue(status_Add,"СКУД ОМ не добавлен");
        assertTrue(status_Sync,"Ошибка при синхронизации контактов со СКУД ОМ");
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_OM_TYPE);
        assertTrue(skudPage.settingsSKUD(mapInputValueDisconnectOM, INTEGRATION_SERVICE_OM_TYPE),
                "После редактирования настроек сервис СКУД Офис-Монитор не найден в таблице Подключенные сервисы");
        status_Disconnect = true;
    }

    @Story(value = "Состояние СКУД Офис-Монитор, при некорректных настройках подключения")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Осталась надпись Офис-Монитор \n" +
            "2. Состояние Офис-Монитор - неактивно. Красный кружок.")
    @Test
    @Order(6)
    void test_Status_OM_Inactive(){
        assertTrue(status_Add,"СКУД ОМ не добавлен");
        assertTrue(status_Sync,"Ошибка при синхронизации контактов со СКУД ОМ");
        assertTrue(status_Disconnect, "Ошибка при некорректных настройках СКУД ОМ");
        assertTrue(MonitoringPage.isStatusService(INTEGRATION_SERVICE_OM_TYPE, classStatusServiceInactive),
                "Состояни СКУД Офис-Монитор - активно, либо отсутсвтует сервис Офис-Монитор");
    }

    @Story(value = "Удаляем СКУД Офис-Монитор")
    @Description(value = "Переходим в раздел Настройки -> Интеграция, переходим в сервис СКУД Офис-Монитор, нажимаем" +
            " кнопку удалить, подтвержаем жействие и перезагружаем сервисы. Проверяем, что сервис СКУД Офис-Монитор" +
            " успешно удалён.")
    @Test
    @Order(7)
    void test_Delete_OM(){
        assertTrue(status_Add,"СКУД ОМ не добавлен");
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_OM_TYPE);
        assertTrue(skudPage.deleteSKUD(INTEGRATION_SERVICE_OM_TYPE),
                "После удаления, сервис СКУД Офис-Мониторинг найден в таблице Подключенные сервисы");
        status_Delete = true;
    }

    @Story(value = "Состояние СКУД, после удаления СКУД Офис-Мониторинг")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Вместо надписи Офис-Монитор появилась надпись СКУД \n" +
            "2. Состояние СКУД - неактивно. Красный кружок.")
    @Test
    @Order(8)
    void test_Status_SKUD_After_Delete_SKUD(){
        assertTrue(status_Add,"СКУД ОМ не добавлен");
        assertTrue(status_Delete, "Ошибка при удалении СКУД ОМ");
        assertTrue(MonitoringPage.isStatusService(MONITORING_SERVICE_SKUD, classStatusServiceInactive),
                "Состояни СКУД - активно, либо отсутсвтует сервис СКУД");
    }
}
