package chat.ros.testing2.contacts;

import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.contacts.ContactsPage;
import chat.ros.testing2.server.contacts.UserPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.ContactsData.*;
import static chat.ros.testing2.data.SettingsData.INTEGRATION_SERVICE_TETRA_NAME;
import static data.CommentsData.CLIENT_7009;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesContactsPage.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Справочник")
@Feature(value = "Контакты")
public class TestServicePage extends ContactsPage {

    @Story(value = "Добавляем у контакта сервис Компьютерный клиент")
    @Description(value = "Переходим в раздель Пользователь и добавляем сервис Компьютерный клиент.  Проверяем, что сервис был добавлен")
    @Test
    void test_Add_Service_PC_Contact(){
        actionsContact(CLIENT_7009)
            .addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_PC)
            .isShowService("h4",USER_SERVICES_TYPE_PC, true);
    }

    @Story(value = "Добавляем у контакта сервис Мобильный клиент")
    @Description(value = "Переходим в раздель Пользователь и добавляем сервис Мобильный клиент.  Проверяем, что сервис был добавлен")
    @Test
    void test_Add_Service_Mobile_Contact(){
        actionsContact(CLIENT_7009)
            .addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_MOBILE)
            .isShowService("h4",USER_SERVICES_TYPE_MOBILE, true);
    }

    @Story(value = "Добавляем у контакта сервис Рация")
    @Description(value = "Переходим в раздель Пользователь и добавляем сервис Рация.  Проверяем, что сервис был добавлен")
    @Test
    void test_Add_Service_Radio_Contact(){
        actionsContact(CLIENT_7009)
            .addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_RADIO)
            .isShowService("h4",USER_SERVICES_TYPE_RADIO, true);
    }

    @Story(value = "Добавляем у контакта сервис SIP")
    @Description(value = "Переходим в раздель Пользователь и добавляем сервис SIP. Проверяем, что сервис был добавлен")
    @Test
    void test_Add_Service_SIP_Contact(){
        sendInputSearchContact(CLIENT_7009)
                .clickContact(CLIENT_7009)
                .addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_SIP, CLIENT_7009)
                .isShowService("h4",USER_SERVICES_TYPE_SIP, true)
                .isShowService("span", CLIENT_7009, true);
    }

    @Story(value = "Добавляем у контакта сервис Тетра")
    @Description(value = "Переходим в раздел Пользователь и добавляем сервис Тетра.  Проверяем, что сервис был добавлен")
    @Test
    void test_Add_Service_Tetra_Contact(){
        sendInputSearchContact(CLIENT_7009)
                .clickContact(CLIENT_7009)
                .addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_TETRA, INTEGRATION_SERVICE_TETRA_NAME, "1")
                .isShowService("h4", USER_SERVICES_TYPE_TETRA, true);
    }

    @Story(value = "Проверяем количество пользователей")
    @Description(value = "Переходим в раздел Справочник и проверяем, что количество контактов больше 700")
    @Test
    @Disabled
    void test_Count_Contacts_After_Sync_Integrations(){
        assertTrue(countContacts() > 700, "Контакты из Офис-Монитора и/или Active Directory не были синхронизированы");
    }

}
