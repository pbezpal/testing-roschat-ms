package chat.ros.testing2.pages;

import chat.ros.testing2.RecourcesTests;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.open;

@Epic(value = "Справочник")
@Feature(value = "Добавление контактов")
@ExtendWith(RecourcesTests.class)
public class ContactsPageTest extends ContactsPage {

    private UserPage userPage;
    private String contact7010 = "7010";
    private String contact7011 = "7011";
    private String password = "12345678";
    private String itemMenu = "Учетная запись";


    @BeforeEach
    void tearDown(){
        open("/contacts");
    }

    @Story(value = "Добавляем контакт 7010")
    @Description(value = "Добавляем контакт 7010 и создаём для него учётную запись")
    @Test
    void test_Contact_7010(){
        addContact(contact7010).addUserAccount(contact7010, password, itemMenu);
    }

    @Story(value = "Добавляем контакт 7011")
    @Description(value = "Добавляем контакт 7011 и создаём для него учётную запись")
    @Test
    void test_Contact_7011(){
        addContact(contact7011).addUserAccount(contact7011, password, itemMenu);
    }
}
