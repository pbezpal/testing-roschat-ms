package chat.ros.testing2.server.integration;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.TestsBase;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Интеграция->PERCo")
public class TestMonitorSkudPerco implements IntegrationPage {

    private final String classStatusServiceActive = "status active";
    private final String classStatusServiceInactive = "status inactive";
    private SKUDPage skudPage = null;
    private static boolean status_Add;
    private static boolean status_Sync;
    private static boolean status_Disconnect;
    private static boolean status_Delete;
    private final TestsBase testsBase = new TestsBase();
    private final Map<String, String> mapInputValueConnectPerco = new HashMap() {{
        put("Адрес модуля интеграции с PERCo", INTEGRATION_SERVICE_PERCO_IP_MODULE);
        put("Порт модуля интеграции с PERCo", INTEGRATION_SERVICE_PERCO_PORT_MODULE);
        put("Адрес PERCo сервера", INTEGRATION_SERVICE_PERCO_IP_SERVER);
        put("Порт PERCo сервера", INTEGRATION_SERVICE_PERCO_PORT_SERVER);
        put("Имя пользователя", INTEGRATION_SERVICE_PERCO_USERNAME);
        put("Ключ шифрования", INTEGRATION_SERVICE_PERCO_KEY);
    }};
    private final Map<String, String> mapInputValueDisconnectPerco = new HashMap() {{
        put("Адрес модуля интеграции с PERCo", INTEGRATION_SERVICE_PERCO_WRONG_IP_MODULE);
        put("Порт модуля интеграции с PERCo", INTEGRATION_SERVICE_PERCO_PORT_MODULE);
        put("Адрес PERCo сервера", INTEGRATION_SERVICE_PERCO_IP_SERVER);
        put("Порт PERCo сервера", INTEGRATION_SERVICE_PERCO_PORT_SERVER);
        put("Имя пользователя", INTEGRATION_SERVICE_PERCO_USERNAME);
        put("Ключ шифрования", INTEGRATION_SERVICE_PERCO_KEY);
    }};

    @BeforeAll
    static void beforeAll(){
        status_Add = false;
        status_Sync = false;
        status_Disconnect = false;
        status_Delete = false;
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
        status_Add = true;
    }

    @Story(value = "Синхронизация контактов со СКУД PERCo")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис СКУД PERCo и нажимаем Синхронизировать")
    @Test
    @Order(2)
    void test_Sync_Contacts(){
        assertTrue(status_Add,"СКУД PERCo не добавлен");
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_PERCO_TYPE);
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
        status_Sync = true;
    }

    @Story(value = "Состояние СКУД PERCo, при корректных настройках подключения")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Вместо надписи СКУД появилась надпись PERCo \n" +
            "2. Состояние PERCo - активно. Зелённый кружок.")
    @Test
    @Order(3)
    void test_Status_Perco_Active(){
        assertTrue(status_Add,"СКУД PERCo не добавлен");
        assertTrue(status_Sync,"Ошибка при синхронизации контактов со СКУД ОРИОН");
        assertTrue(MonitoringPage.isStatusService(INTEGRATION_SERVICE_PERCO_TYPE, classStatusServiceActive),
                "Состояни СКУД PERCo - неактивно, либо отсутсвтует сервис ОPERCo");
    }

    @Story(value = "Настраиваем СКУД PERCo с некорректными данными")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис PERCo и вводим некорректные " +
            "данные для подключения.")
    @Test
    @Order(4)
    void test_Change_Data_Disconnect_SKUD(){
        assertTrue(status_Add,"СКУД PERCo не добавлен");
        assertTrue(status_Sync,"Ошибка при синхронизации контактов со СКУД ОРИОН");
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_PERCO_TYPE);
        assertTrue(skudPage.settingsSKUD(mapInputValueDisconnectPerco, INTEGRATION_SERVICE_PERCO_TYPE),
                "После редактирования настроек сервис СКУД PERCo не найден в таблице Подключенные сервисы");
        status_Disconnect = true;
    }

    @Story(value = "Состояние СКУД PERCo, при некорректных настройках подключения")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Осталась надпись PERCo \n" +
            "2. Состояние СКУД PERCo - неактивно. Красный кружок.")
    @Test
    @Order(5)
    void test_Status_Perco_Inactive(){
        assertTrue(status_Add,"СКУД PERCo не добавлен");
        assertTrue(status_Sync,"Ошибка при синхронизации контактов со СКУД ОРИОН");
        assertTrue(status_Disconnect, "Ошибка при настройке невалидных данных");
        assertTrue(MonitoringPage.isStatusService(INTEGRATION_SERVICE_PERCO_TYPE, classStatusServiceInactive),
                "Состояни СКУД PERCo - активно, либо отсутсвтует сервис PERCo");
    }

    @Story(value = "Удаляем СКУД PERCo")
    @Description(value = "Переходим в раздел Настройки -> Интеграция, переходим в сервис СКУД PERCo, нажимаем" +
            " кнопку удалить, подтвержаем жействие и перезагружаем сервисы. Проверяем, что сервис СКУД PERCo" +
            " успешно удалён.")
    @Test
    @Order(6)
    void test_Delete_Perco(){
        assertTrue(status_Add,"СКУД PERCo не добавлен");
        skudPage = (SKUDPage) clickServiceType(INTEGRATION_SERVICE_PERCO_TYPE);
        assertTrue(skudPage.deleteSKUD(INTEGRATION_SERVICE_PERCO_TYPE),
                "После удаления, сервис СКУД PERCo найден в таблице Подключенные сервисы");
        status_Delete = true;
    }

    @Story(value = "Состояние СКУД, после удаления СКУД PERCo")
    @Description(value = "Переходим в разде Монитор и проверяем: \n" +
            "1. Вместо надписи PERCo появилась надпись СКУД \n" +
            "2. Состояние СКУД - неактивно. Красный кружок.")
    @Test
    @Order(7)
    void test_Status_SKUD_After_Delete_SKUD(){
        assertTrue(status_Add,"СКУД PERCo не добавлен");
        assertTrue(status_Delete, "СКУД PERCo не был удалён");
        assertTrue(MonitoringPage.isStatusService(MONITORING_SERVICE_SKUD, classStatusServiceInactive),
                "Состояни СКУД - подключен, либо отсутсвтует сервис СКУД");
    }
}
