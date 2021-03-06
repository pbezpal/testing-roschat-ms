package chat.ros.testing2.server.integration;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.monitoring.MonitoringPage;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import chat.ros.testing2.server.settings.integration.SKUDPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.MonitoringData.MONITORING_SERVICE_SKUD;
import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesIntegrationPage.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Интеграция->Орион")
public class TestMonitorSkudOrion implements IntegrationPage {

    private final String classStatusServiceActive = ".status.active";
    private final String classStatusServiceInactive = ".status.inactive";
    private SKUDPage skudPage;
    private static boolean status_Add;
    private static boolean status_Sync;
    private static boolean status_Disconnect;
    private static boolean status_Delete;
    private final Map<String, String> mapInputValueConnectOrion = new HashMap() {{
        put("IP адрес", INTEGRATION_SERVICE_ORION_IP_ADDRESS);
        put("Порт", INTEGRATION_SERVICE_ORION_PORT);
        put("Исходящий порт", INTEGRATION_SERVICE_ORION_OUTGOING_PORT);
    }};
    private final Map<String, String> mapInputValueDisconnectOrion = new HashMap() {{
        put("IP адрес", INTEGRATION_SERVICE_ORION_WRONG_IP_ADDRESS);
        put("Порт", INTEGRATION_SERVICE_ORION_PORT);
        put("Исходящий порт", INTEGRATION_SERVICE_ORION_OUTGOING_PORT);
    }};

    @Story(value = "Добавляем сервис СКУД ОРИОН")
    @Description(value = "Переходим в раздел Интеграция, добавляем и настраиваем сервис СКУД ОРИОН и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test
    @Order(1)
    void test_Add_SKUD_Orion(){
        skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_ORION_TYPE);
        skudPage.settingsSKUD(mapInputValueConnectOrion, INTEGRATION_SERVICE_ORION_TYPE);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Синхронизация контактов со СКУД ОРИОН")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис СКУД ОРИОН и нажимаем Синхронизировать")
    @Test
    @Order(2)
    void test_Sync_Contacts_SKUD_Orion(){
        assertTrue(status_Add,"СКУД ОРИОН не добавлен");
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_ORION_TYPE);
        assertTrue(skudPage.syncContacts(), "Ошибка при сихронизации контактов");
        assertAll("Проверяем, успешно ли синхронизировались контакты AD",
                () -> assertTrue(isShowIconModalWindow(".primary--text"),
                        "Нет иконки успешной синхронизации"),
                () -> assertEquals(getTextModalWindow("h3"),
                        "Успешно",
                        "Текст в заголовке модального окна не совпадает с ожидаемым"),
                () -> assertEquals(getTextModalWindow("h4"),
                        "Контакты успешно синхронизированы.",
                        "Текст в модальном окне не совпадает с ожидаемым")
        );
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Состояние СКУД ОРИОН, при корректных настройках подключения")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Вместо надписи СКУД появилась надпись ОРИОН \n" +
            "2. Состояние ОРИОН - активно. Зелённый кружок.")
    @Test
    @Order(3)
    void test_Status_Active_SKUD_Orion(){
        assertTrue(MonitoringPage.isStatusService(INTEGRATION_SERVICE_ORION_TYPE, classStatusServiceActive),
                "Состояни СКУД ОРИОН - неактивно, либо отсутсвтует сервис ОРИОН");
    }

    @Story(value = "Настраиваем СКУД ОРИОН с некорректными данными")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис ОРИОН и вводим некорректные " +
            "данные для подключения.")
    @Test
    @Order(4)
    void test_Change_Data_Disconnect_SKUD_Orion(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_ORION_TYPE);
        skudPage.settingsSKUD(mapInputValueDisconnectOrion, INTEGRATION_SERVICE_ORION_TYPE);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Состояние СКУД ОРИОН, при некорректных настройках подключения")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Осталась надпись ОРИОН \n" +
            "2. Состояние СКУД ОРИОН - неактивно. Красный кружок.")
    @Test
    @Order(5)
    void test_Status_Inactive_SKUD_Orion(){
        assertTrue(MonitoringPage.isStatusService(INTEGRATION_SERVICE_ORION_TYPE, classStatusServiceInactive),
                "Состояни СКУД ОРИОН - активно, либо отсутсвтует сервис ОРИОН");
    }

    @Story(value = "Удаляем СКУД ОРИОН")
    @Description(value = "Переходим в раздел Настройки -> Интеграция, переходим в сервис СКУД ОРИОН, нажимаем" +
            " кнопку удалить, подтвержаем жействие и перезагружаем сервисы. Проверяем, что сервис СКУД ОРИОН" +
            " успешно удалён.")
    @Test
    @Order(6)
    void test_Delete_SKUD_Orion(){
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_ORION_TYPE);
        skudPage.deleteSKUD(INTEGRATION_SERVICE_ORION_TYPE);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Состояние СКУД, после удаления СКУД ОРИОН")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Вместо надписи ОРИОН появилась надпись СКУД \n" +
            "2. Состояние СКУД - неактивно. Красный кружок.")
    @Test
    @Order(7)
    void test_Status_SKUD_After_Delete_SKUD_Orion(){
        assertTrue(MonitoringPage.isStatusService(MONITORING_SERVICE_SKUD, classStatusServiceInactive),
                "Состояни СКУД - подключен, либо отсутсвтует сервис СКУД");
    }
}
