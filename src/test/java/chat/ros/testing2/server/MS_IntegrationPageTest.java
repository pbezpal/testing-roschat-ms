package chat.ros.testing2.server;

import chat.ros.testing2.RecourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.contacts.ContactsPage;
import chat.ros.testing2.server.contacts.UserPage;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.ContactsData.*;
import static chat.ros.testing2.data.SettingsData.INTEGRATION_SERVICE_TETRA_NAME;
import static chat.ros.testing2.data.SettingsData.INTEGRATION_SERVICE_TETRA_TYPE;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic(value = "Настройки")
@Feature(value = "Интеграция")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(RecourcesTests.class)
@ExtendWith(WatcherTests.class)
public class MS_IntegrationPageTest extends IntegrationPage {

    @Story(value = "Добавляем сервис МиниКом TETRA")
    @Description(value = "Переходим в раздел Интеграция, добавляем сервис МиниКом TETRA и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test
    void test_Add_Service_Tetra(){
        addIntegrationService(INTEGRATION_SERVICE_TETRA_TYPE);
    }

    @Epic(value = "Справочник")
    @Feature(value = "Пользователь -> Сервисы")
    @ExtendWith(RecourcesTests.class)
    @ExtendWith(WatcherTests.class)
    @Nested
    class MS_ServiceTetraTest extends ContactsPage {

        private UserPage userPage;

        @Story(value = "Добавляем сервис Рация у контакта 7012")
        @Description(value = "Переходим в раздель Пользователь контакта 7012 и добавляем сервис Рация")
        @Test
        void test_Add_Service_Tetra_Contact_7012(){
            userPage = sendInputSearchContact(CONTACT_NUMBER_7012).clickContact(CONTACT_NUMBER_7012);
            userPage.addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_TETRA, INTEGRATION_SERVICE_TETRA_NAME, "1");
            assertTrue(userPage.isShowService(USER_SERVICES_TYPE_TETRA), "Сервис " + USER_SERVICES_TYPE_TETRA + " не был добавлен");
        }
    }
}
