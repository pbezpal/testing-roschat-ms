package chat.ros.testing2.contacts;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.contacts.ContactsPage;
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
import static data.CommentsData.*;

@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Справочник")
@Feature(value = "Контакты")
public class TestContactsPage extends ContactsPage {

    private static String[] users_closed_channels = {CLIENT_USER_A,CLIENT_USER_B, CONTACT_A};
    private static String[] users_public_channels = {CLIENT_USER_C,CLIENT_USER_D};
    private static String[] users_public_proven_channels = {CLIENT_USER_E,CLIENT_USER_F,CONTACT_B,CONTACT_C};

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

    @Story(value = "Добавление контактов")
    @Description(value = "Переходим в раздел Справочник, добавляем пользователя, переходим настройки пользователя и " +
            "создаём учётную запись для пользователя")
    @ParameterizedTest
    @MethodSource("getUsersClosedChannel")
    void test_Add_Contacts_For_Closed_Channel(String user){
        actionsContact(user).addUserAccount(user, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU);
    }

    @Story(value = "Добавление контактов")
    @Description(value = "Переходим в раздел Справочник, добавляем пользователя, переходим настройки пользователя и " +
            "создаём учётную запись для пользователя")
    @ParameterizedTest
    @MethodSource("getUsersPublicChannel")
    void test_Add_Contacts_For_Public_Channel(String user){
        actionsContact(user).addUserAccount(user, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU);
    }

    @Story(value = "Добавление контактов")
    @Description(value = "Переходим в раздел Справочник, добавляем пользователя, переходим настройки пользователя и " +
            "создаём учётную запись для пользователя")
    @ParameterizedTest
    @MethodSource("getUsersPublicProvenChannel")
    void test_Add_Contacts_For_Public_Proven_Channel(String user){
        actionsContact(user).addUserAccount(user, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU);
    }

    @Story(value = "Добавление контактов")
    @Description(value = "Переходим в раздел Справочник, добавляем пользователя, переходим настройки пользователя и " +
            "создаём учётную запись для пользователя")
    @Test
    void test_Add_Contacts_For_Service(){
        actionsContact(CONTACT_D).addUserAccount(CONTACT_D, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU);
    }

    @Story(value = "Добавление контактов")
    @Description(value = "Переходим в раздел Справочник, добавляем пользователя, переходим настройки пользователя и " +
            "создаём учётную запись для пользователя")
    @Test
    void test_Add_Contacts_For_Connect(){
        actionsContact(CONTACT_E).addUserAccount(CONTACT_E, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU);
    }

}
