package chat.ros.testing2.contacts;

import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.contacts.ContactsPage;
import chat.ros.testing2.server.contacts.UserPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized;

import java.util.ArrayList;

import static chat.ros.testing2.data.ContactsData.*;
import static chat.ros.testing2.data.SettingsData.CONTACT_FOR_FAX;
import static data.CommentsData.*;

@ExtendWith(ResourcesContactsPage.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Справочник")
@Feature(value = "Контакты")
public class TestContactsPage extends ContactsPage {

    private static String[] users_closed_channels = {CLIENT_7000,CLIENT_7001, CLIENT_7002};
    private static String[] users_public_channels = {CLIENT_7003,CLIENT_7004};
    private static String[] users_public_proven_channels = {CLIENT_7005, CLIENT_7006, CLIENT_7007, CLIENT_7008};
    private UserPage userPage;

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> getUsersClosedChannel() {
        ArrayList<String> data = new ArrayList<>();

        for (String user: users_closed_channels) {
            data.add(user);
        }

        return data;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> getUsersPublicChannel() {
        ArrayList<String> data = new ArrayList<>();

        for (String user: users_public_channels) {
            data.add(user);
        }

        return data;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> getUsersPublicProvenChannel() {
        ArrayList<String> data = new ArrayList<>();

        for (String user: users_public_proven_channels) {
            data.add(user);
        }

        return data;
    }

    @Story(value = "Добавление контактов для закрытых каналов")
    @Description(value = "Переходим в раздел Справочник, добавляем пользователя, переходим настройки пользователя и " +
            "создаём учётную запись для пользователя")
    @ParameterizedTest
    @MethodSource("getUsersClosedChannel")
    void test_Add_Contacts_For_Closed_Channel(String user){
        userPage = actionsContact(user).addUserAccount(user, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU);
        userPage.addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_PC);
    }

    @Story(value = "Добавление контактов для публичного канала")
    @Description(value = "Переходим в раздел Справочник, добавляем пользователя, переходим настройки пользователя и " +
            "создаём учётную запись для пользователя")
    @ParameterizedTest
    @MethodSource("getUsersPublicChannel")
    void test_Add_Contacts_For_Public_Channel(String user){
        userPage = actionsContact(user).addUserAccount(user, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU);
        userPage.addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_PC);
    }

    @Story(value = "Добавление контактов для публичного проверенного канала")
    @Description(value = "Переходим в раздел Справочник, добавляем пользователя, переходим настройки пользователя и " +
            "создаём учётную запись для пользователя")
    @ParameterizedTest
    @MethodSource("getUsersPublicProvenChannel")
    void test_Add_Contacts_For_Public_Proven_Channel(String user){
        userPage = actionsContact(user).addUserAccount(user, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU);
        userPage.addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_PC);
    }

    @Story(value = "Добавление контакта для проверки добавления сервисов")
    @Description(value = "Переходим в раздел Справочник, добавляем пользователя, переходим настройки пользователя и " +
            "создаём учётную запись для пользователя")
    @Test
    void test_Add_Contacts_For_Service(){
        actionsContact(CLIENT_7009).addUserAccount(CLIENT_7009, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU);
    }

    @Story(value = "Добавление контакта для проверки подключения к серверу на разных портах")
    @Description(value = "Переходим в раздел Справочник, добавляем пользователя, переходим настройки пользователя и " +
            "создаём учётную запись для пользователя")
    @Test
    void test_Add_Contacts_For_Connect(){
        userPage = actionsContact(CLIENT_7010).addUserAccount(CLIENT_7010, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU);
        userPage.addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_PC);
    }

    @Story(value = "Добавление контакта для проверки факса")
    @Description(value = "Переходим в раздел Справочник, добавляем пользователя, переходим настройки пользователя и " +
            "создаём учётную запись для пользователя")
    @Test
    void test_Add_Contact_For_Fax(){
        actionsContact(CONTACT_FOR_FAX).addUserAccount(CONTACT_FOR_FAX, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU);
    }

}
