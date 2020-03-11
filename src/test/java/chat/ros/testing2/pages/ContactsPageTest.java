package chat.ros.testing2.pages;

import chat.ros.testing2.RecourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.pages.contacts.ContactsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.ContactsData.*;

@Epic(value = "Справочник")
@Feature(value = "Добавление контактов")
@ExtendWith(RecourcesTests.class)
@ExtendWith(WatcherTests.class)
public class ContactsPageTest extends ContactsPage {

    @Story(value = "Добавляем контакт 7010")
    @Description(value = "Добавляем контакт 7010 и создаём для него учётную запись")
    @Test
    void test_Contact_7010(){
        addContact(CONTACT_NUMBER_7010).addUserAccount(CONTACT_NUMBER_7010, USER_ACCOUNT_PASSWORD, USER_ACOUNT_ITEM_MENU);
    }

    @Story(value = "Добавляем контакт 7011")
    @Description(value = "Добавляем контакт 7011 и создаём для него учётную запись")
    @Test
    void test_Contact_7011(){
        addContact(CONTACT_NUMBER_7011).addUserAccount(CONTACT_NUMBER_7011, USER_ACCOUNT_PASSWORD, USER_ACOUNT_ITEM_MENU);
    }
}
