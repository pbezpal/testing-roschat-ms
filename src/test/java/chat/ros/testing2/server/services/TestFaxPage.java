package chat.ros.testing2.server.services;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.contacts.ContactsPage;
import chat.ros.testing2.server.settings.services.FaxPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.ContactsData.USER_ACCOUNT_ITEM_MENU;
import static chat.ros.testing2.data.ContactsData.USER_ACCOUNT_PASSWORD;
import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic(value = "Сервисы")
@Feature(value = "Факс")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesFaxPage.class)
@ExtendWith(WatcherTests.class)
public class TestFaxPage extends FaxPage {

    private ContactsPage contactsPage = new ContactsPage();

    @Story(value = "Добавление контакта для проверки факса")
    @Description(value = "Переходим в раздел Справочник, добавляем пользователя, переходим настройки пользователя и " +
            "создаём учётную запись для пользователя")
    @Test
    @Order(1)
    void test_Add_Contact_For_Fax(){
        contactsPage.actionsContact(CONTACT_FOR_FAX).addUserAccount(CONTACT_FOR_FAX, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление номер факса с описанием")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс \n" +
            "2. Нажимаем кнопку Добавить в разделе Номер факсов\n" +
            "3. Вводим номер\n" +
            "4. Вводим описание\n" +
            "5. Сохраняем\n" +
            "6. Проверяем, что номер сохранён в таблице Номер факсов"
    )
    @Test
    void test_Add_Number_Fax_With_Description(){
        assertAll("Добаялем номер факса с описанием и проверяем, что номер с описанием был добавлен",
                () -> assertEquals(clickButtonAdd(FAX_NUMBERS_TITLE).getTextTitleModalWindow(),
                        "Добавление номера факса",
                        "Не найден заголовок модального окна при добавлении номера факса"),
                () -> {sendNumberFaxes(FAX_NUMBER_WITH_DESCRIPTION, FAX_DESCRIPTION_FAX, "Сохранить");},
                () -> assertTrue(isItemTable(FAX_NUMBERS_TITLE, FAX_NUMBER_WITH_DESCRIPTION, true),
                        "Не найдена запись " + FAX_NUMBER_WITH_DESCRIPTION + " в таблице " + FAX_NUMBERS_TITLE),
                () -> assertTrue(isItemTable(FAX_NUMBERS_TITLE, FAX_DESCRIPTION_FAX, true),
                        "Не найдена запись " + FAX_DESCRIPTION_FAX + " в таблице " + FAX_NUMBERS_TITLE)
                );
    }

    @Story(value = "Добавление номер факса без описания")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс \n" +
            "2. Нажимаем кнопку Добавить в разделе Номер факсов\n" +
            "3. Вводим номер\n" +
            "4. Сохраняем\n" +
            "5. Проверяем, что номер сохранён в таблице Номер факсов"
    )
    @Test
    @Order(2)
    void test_Add_Number_Fax_Without_Description(){
        assertAll("1. Вводим номер для факса\n" +
                        "2. Сохраняем\n" +
                        "3. Проверяем, что номер сохранён в таблице",
                () -> assertEquals(clickButtonAdd(FAX_NUMBERS_TITLE).getTextTitleModalWindow(),
                        "Добавление номера факса",
                        "Не найден заголовок модального окна при добавлении номера факса"),
                () -> {sendNumberFaxes(FAX_NUMBER_WITHOUT_DESCRIPTION, "", "Сохранить");},
                () -> assertTrue(isItemTable(FAX_NUMBERS_TITLE, FAX_NUMBER_WITHOUT_DESCRIPTION, true),
                        "Не найдена запись " + FAX_NUMBER_WITHOUT_DESCRIPTION + " в таблице " + FAX_NUMBERS_TITLE)
        );
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Удаление номера факса без описания")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс \n" +
            "2. Нажимаем кнопку удалить в таблице Номер факсов у номера без описания \n" +
            "3. Нажимаем кнопку Удалить в модальном окне\n" +
            "4. Проверяем, что номер отсутствует в таблице Номер факсов"
    )
    @Test
    @Order(3)
    void test_Delete_Number_Fax_Without_Description(){
        clickButtonTable(FAX_NUMBERS_TITLE, FAX_NUMBER_WITHOUT_DESCRIPTION, IVR_BUTTON_DELETE);
        assertTrue(isFormConfirmActions(true),
                "Не появилась форма для удаления меню " + FAX_NUMBER_WITHOUT_DESCRIPTION);
        clickButtonConfirmAction("Удалить");
        assertTrue(isItemTable(FAX_NUMBERS_TITLE, FAX_NUMBER_WITHOUT_DESCRIPTION, false),
                "Найдена запись " + FAX_NUMBER_WITHOUT_DESCRIPTION + " в таблице " + FAX_NUMBERS_TITLE);
    }

    @Story(value = "Добавление пользователя для факса")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс \n" +
            "2. Нажимаем кнопку Добавить в разделе Пользователи факсов\n" +
            "3. Ищем пользователя для добавления\n" +
            "4. Проверяем, что пользователь найден\n" +
            "5. Сохраняем пользователя\n" +
            "6. Проверяем, что пользователь добавлен в таблице Пользователи факсов")
    @Test
    @Order(4)
    void test_Add_User_For_Fax(){
        assertAll("1. Ищем пользователя для факса\n" +
                        "2. Проверяем, что пользователь найден\n" +
                        "3. Сохраняем пользователя\n" +
                        "4. Проверяем, что пользователь добавлен в таблице",
                () -> assertEquals(clickButtonAdd(FAX_USERS_TITLE).getTextTitleModalWindow(),
                        "Добавление пользователя",
                        "Не найден заголовок модального окна при добавлении пользователя для факса"),
                () -> {sendInputModalWindow("Поиск контакта", CONTACT_FOR_FAX);},
                () -> assertTrue(isContactName(CONTACT_FOR_FAX, true),
                        "Не найдена запись " + CONTACT_FOR_FAX + " в списке пользователей"),
                () -> {
                    getContactName(CONTACT_FOR_FAX).click();
                    clickActionButtonOfModalWindow("Сохранить");
                },
                () -> assertTrue(isItemTable(FAX_USERS_TITLE, CONTACT_FOR_FAX, true),
                        "Не найдена запись " + CONTACT_FOR_FAX + " в таблице " + FAX_USERS_TITLE)
        );
        TestStatusResult.setTestResult(true);
    }

}
