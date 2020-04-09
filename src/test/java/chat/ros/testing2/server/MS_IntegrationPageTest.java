package chat.ros.testing2.server;

import chat.ros.testing2.RecourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.contacts.ContactsPage;
import chat.ros.testing2.server.contacts.UserPage;
import chat.ros.testing2.server.settings.integration.ActiveDirectoryPage;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import chat.ros.testing2.server.settings.integration.OfficeMonitorPage;
import chat.ros.testing2.server.settings.integration.TetraPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.ContactsData.*;
import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic(value = "Настройки")
@Feature(value = "Интеграция")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(RecourcesTests.class)
@ExtendWith(WatcherTests.class)
public class MS_IntegrationPageTest extends IntegrationPage {

    private TetraPage tetraPage;
    private OfficeMonitorPage officeMonitorPage;
    private ActiveDirectoryPage activeDirectoryPage;

    @Story(value = "Добавляем сервис МиниКом TETRA")
    @Description(value = "Переходим в раздел Интеграция, добавляем сервис МиниКом TETRA и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test
    @Order(1)
    void test_Add_Service_Tetra(){
        tetraPage = (TetraPage) addIntegrationService(INTEGRATION_SERVICE_TETRA_TYPE);
        tetraPage.addTetraServer();
    }

    @Story(value = "Добавляем сервис Офис-Монитор")
    @Description(value = "Переходим в раздел Интеграция, добавляем и настраиваем сервис Офис-Монитор и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test
    @Order(2)
    void test_Add_Service_Office_Monitor(){
        officeMonitorPage = (OfficeMonitorPage) addIntegrationService(INTEGRATION_SERVICE_OM_TYPE);
        officeMonitorPage.settingsOfficeMonitor();
    }

    @Story(value = "Добавляем сервис Active Directory")
    @Description(value = "Переходим в раздел Интеграция, добавляем и настраиваем сервис Active Directory и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test
    @Order(3)
    void test_Add_Service_Active_Directory(){
        activeDirectoryPage = (ActiveDirectoryPage) addIntegrationService(INTEGRATION_SERVICE_AD_TYPE);
        activeDirectoryPage.settingsActiveDirectory();
    }

    @Story(value = "Синхронизация контактов c Active Directory")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис Active Directory и нажимаем Синхронизировать")
    @Test
    @Order(4)
    void test_Sync_Contacts_Active_Directory(){
        activeDirectoryPage = (ActiveDirectoryPage) clickServiceType(INTEGRATION_SERVICE_AD_TYPE);
        assertTrue(activeDirectoryPage.syncContacts(), "Ошибка при сихронизации контактов");
    }

    @Story(value = "Синхронизация контактов в Офис-Монитор")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис Офис-Монитор и нажимаем Синхронизировать")
    @Test
    @Order(4)
    void test_Sync_Contacts_Office_Monitor(){
        officeMonitorPage = (OfficeMonitorPage) clickServiceType(INTEGRATION_SERVICE_OM_TYPE);
        assertTrue(officeMonitorPage.syncContacts(), "Ошибка при сихронизации контактов");
    }

    @Epic(value = "Справочник")
    @Feature(value = "Пользователь -> Сервисы")
    @ExtendWith(RecourcesTests.class)
    @ExtendWith(WatcherTests.class)
    @Nested
    class MS_ContactsPageTest extends ContactsPage {

        private UserPage userPage;

        @Story(value = "Добавляем сервис Рация у контакта 7012")
        @Description(value = "Переходим в раздель Пользователь контакта 7012 и добавляем сервис Рация")
        @Test
        void test_Add_Service_Tetra_Contact_7012(){
            userPage = sendInputSearchContact(CONTACT_NUMBER_7012).clickContact(CONTACT_NUMBER_7012);
            userPage.addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_TETRA, INTEGRATION_SERVICE_TETRA_NAME, "1");
            assertTrue(userPage.isShowService(USER_SERVICES_TYPE_TETRA), "Сервис " + USER_SERVICES_TYPE_TETRA + " не был добавлен");
        }

        @Story(value = "Проверяем количество пользователей")
        @Description(value = "Переходим в раздел Справочник и проверяем, что количество контактов больше 700")
        @Test
        void test_Count_Contacts_After_Sync_Office_Monitor(){
            assertTrue(countContacts() > 700, "Контакты из Офис-Монитора и/или Active Directory не были синхронизированы");
        }
    }
}
