package chat.ros.testing2.server.services;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.contacts.ContactsPage;
import chat.ros.testing2.server.services.codefortests.TFaxPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.ContactsData.*;
import static chat.ros.testing2.data.ContactsData.USER_SERVICES_TYPE_SIP;
import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.refresh;

@Epic(value = "Сервисы")
@Feature(value = "Факс")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesFaxPage.class)
@ExtendWith(WatcherTests.class)
public class TestFaxPage extends TFaxPage {

    private ContactsPage contactsPage = new ContactsPage();
    private String loginFax = CONTACT_FOR_FAX + "@ros.chat";
    private String wrongLogin = "7000ros.chat";
    private String wrongPassword = "87654321";
    private String titleFailedLogin = "Неудачная попытка авторизации";

    @Step(value = "Проверяем заголовок модального окна при добавление Номера факса")
    @Description(value = "1. Переходим в раздел Факс\n" +
            "2. Нажтимаем кнопку Добавить в разделе Номера факсов\n" +
            "3. Проверяем, правильно ли отображается заголовок модального окна\n" +
            "4. Нажимаем кнопку Отменить\n" +
            "5. Проверяем, что модальное окно закрылось")
    @Test
    void test_Check_Title_Text_Modal_Window_When_Add_Number_Fax(){
        checkTitleTextModalWindowWhenAddItem(FAX_NUMBERS_TITLE, "Добавление номера факса");
    }

    @Step(value = "Проверяем заголовок модального окна при добавление Пользователя факса")
    @Description(value = "1. Переходим в раздел Факс\n" +
            "2. Нажтимаем кнопку Добавить в разделе Пользователи факсов\n" +
            "3. Проверяем, правильно ли отображается заголовок модального окна\n" +
            "4. Нажимаем кнопку Отменить\n" +
            "5. Проверяем, что модальное окно закрылось")
    @Test
    void test_Check_Title_Text_Modal_Window_When_Add_Users_Fax(){
        checkTitleTextModalWindowWhenAddItem(FAX_USERS_TITLE, "Добавление пользователя");
    }

    @Step(value = "Проверяем информационный текст с ссылкой для входя в систему управления факсом")
    @Description(value = "1. Переходим в раздел Факс\n" +
            "2. Проверяем в разделе Пользователи факсов текст с ссылкой")
    @Test
    void test_Info_Text_With_Link_Of_Users_Fax(){
        getInstanceFaxPage().isLinkText();
    }

    @Story(value = "Проверяем элементы на странице авторизации в усправление факсами")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс \n" +
            "2. Переходим по ссылке на страницу авторизации в управление факсами\n" +
            "3. Проверяем элементы на старнице авторизации\n" +
            "4. Закрываем вкладку со страницей авторизации в управление факсами")
    @Test
    @Order(1)
    void test_Check_Elements_Auth_Fax_Page(){
        checkElementsInAuthPage();
    }

    @Story(value = "Добавление контакта для проверки факса")
    @Description(value = "Переходим в раздел Справочник, добавляем пользователя, переходим настройки пользователя и " +
            "создаём учётную запись для пользователя")
    @Test
    @Order(2)
    void test_Add_Contact_For_Fax(){
        contactsPage
                .actionsContact(CONTACT_FOR_FAX)
                .addUserAccount(CONTACT_FOR_FAX, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU)
                .addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_SIP, CONTACT_FOR_FAX)
                .isShowService("h4",USER_SERVICES_TYPE_SIP, true)
                .isShowService("span", CONTACT_FOR_FAX, true);
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
        addNumberFax(FAX_NUMBER_WITH_DESCRIPTION, FAX_DESCRIPTION_FAX);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление номер факса без описания")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс \n" +
            "2. Нажимаем кнопку Добавить в разделе Номер факсов\n" +
            "3. Вводим номер\n" +
            "4. Сохраняем\n" +
            "5. Проверяем, что номер сохранён в таблице Номер факсов"
    )
    @Test
    @Order(3)
    void test_Add_Number_Fax_Without_Description(){
        addNumberFax(FAX_NUMBER_WITHOUT_DESCRIPTION, "");
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Удаление номера факса без описания")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс \n" +
            "2. Нажимаем кнопку удалить в таблице Номер факсов у номера без описания \n" +
            "3. Нажимаем кнопку Удалить в модальном окне\n" +
            "4. Проверяем, что номер отсутствует в таблице Номер факсов"
    )
    @Test
    @Order(4)
    void test_Delete_Number_Fax_Without_Description(){
        deleteNumber(FAX_NUMBER_WITHOUT_DESCRIPTION);
    }

    @Story(value = "Проверяем авторизацию в управление факсами перед добавлением пользователя")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс\n" +
            "2. Переходим по ссылке на страницу управления факсами\n" +
            "3. Авторизуемся под учётной записью тестового пользователя\n" +
            "4. Проверяем, появилась ли окно с ошибкой при авторизации\n" +
            "5. Закрываем вкладку со страницей управлением факсами")
    @Test
    @Order(5)
    void test_Check_Auth_System_Fax_Before_Add_User_Fax(){
        String textLoginFailed = "Данный пользователь не имеет доступа к факсу";
        checkAuthToService(loginFax, USER_ACCOUNT_PASSWORD, true, false, titleFailedLogin, textLoginFailed);
    }

    @Story(value = "Добавление пользователя для факса")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс \n" +
            "2. Нажимаем кнопку Добавить в разделе Пользователи факсов\n" +
            "3. Ищем пользователя для добавления\n" +
            "4. Проверяем, что пользователь найден\n" +
            "5. Сохраняем пользователя\n" +
            "6. Проверяем, что пользователь добавлен в таблице Пользователи факсов")
    @Test
    @Order(6)
    void test_Add_User_For_Fax(){
        addContact(FAX_USERS_TITLE, CONTACT_FOR_FAX);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем авторизацию в управление факсами с некорректным логином")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс\n" +
            "2. Переходим по ссылке на страницу управления факсами\n" +
            "3. Авторизуемся под учётной записью с некорректным логином\n" +
            "4. Проверяем, появилась ли окно с ошибкой при авторизации\n" +
            "5. Закрываем вкладку со страницей управлением факсами")
    @Test
    @Order(7)
    void test_Check_Auth_System_Fax_With_Wrong_Login(){
        String textLoginFailed = "Нет такого пользователя";
        checkAuthToService(wrongLogin, USER_ACCOUNT_PASSWORD, true, false, titleFailedLogin, textLoginFailed);
    }

    @Story(value = "Проверяем авторизацию в управление факсами с некорректным паролем")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс\n" +
            "2. Переходим по ссылке на страницу управления факсами\n" +
            "3. Авторизуемся под учётной записью с некорректным паролем\n" +
            "4. Проверяем, появилась ли окно с ошибкой при авторизации\n" +
            "5. Закрываем вкладку со страницей управлением факсами")
    @Test
    @Order(7)
    void test_Check_Auth_System_Fax_With_Wrong_Password(){
        String textLoginFailed = "Проверьте правильность введенного пароля";
        checkAuthToService(loginFax, wrongPassword, true, false, titleFailedLogin, textLoginFailed);
    }

    @Story(value = "Проверяем авторизацию в управление факсами с функцией остаться в системе")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Вводим логин и пароль тестового пользователя и и оставляем галочку в поле Запомнить\n" +
            "4. Нажимаем кнопку Войти\n" +
            "4. Проверяем, что пользователь смог авторизоваться\n" +
            "5. Закрываем вкладку с авторизованной системой оповещения\n" +
            "6. Нажимаем на ссылку для перехода в управление факсами\n" +
            "7. Проверяем, что сразу открылась авторизованная страница управление факсами" +
            "8. Нажимаем на иконку или имя пользователя в управление факсами\n" +
            "9. Нажимаем кнопку Выход и подтверждаем выход\n" +
            "10. Закрываем вкладку управления факсами")
    @Test
    @Order(8)
    void test_Check_Auth_System_Fax_After_Add_User_Fax_With_Stay_System(){
        checkAuthToService(loginFax, USER_ACCOUNT_PASSWORD, true);
    }

    @Story(value = "Проверяем авторизацию в управление факсами после добавления пользователя")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс\n" +
            "2. Нажимаем на ссылку для перехода в управление факс\n" +
            "3. Вводим логин и пароль тестового пользователя и и оставляем галочку в поле Запомнить\n" +
            "4. Нажимаем кнопку Войти\n" +
            "4. Проверяем, что пользователь смог авторизоваться\n" +
            "5. Закрываем вкладку с авторизованной страницей с факсами\n" +
            "6. Нажимаем на ссылку для перехода в управление факсами\n" +
            "7. Проверяем, что открылась страница для авторизации в управление факсами" +
            "8. Закрываем вкладку управления факсами")
    @Test
    @Order(8)
    void test_Check_Auth_System_Fax_After_Add_User_Fax_Without_Stay_System(){
        checkAuthToService(loginFax, USER_ACCOUNT_PASSWORD, false);
    }

    @Story(value = "Удаляем пользователя для управления факсами")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс \n" +
            "2. Нажимаем кнопку Удалить у пользователя в разделе Пользователи факсов\n" +
            "3. Подтверждаем удаление пользователя\n" +
            "4. Проверяем, что пользователь удалён из таблицы Пользователи факсов")
    @Test
    @Order(9)
    void test_Delete_User_For_Fax(){
        deleteContact(FAX_USERS_TITLE, CONTACT_FOR_FAX);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем авторизацию в управление факсами после удаления пользователя")
    @Description(value = "1. Переходим в раздел Сервисы -> Факс\n" +
            "2. Переходим по ссылке на страницу управления факсами\n" +
            "3. Авторизуемся под учётной записью тестового пользователя\n" +
            "4. Проверяем, появилась ли окно с ошибкой при авторизации\n" +
            "5. Закрываем вкладку со страницей управлением факсами")
    @Test
    @Order(10)
    void test_Check_Auth_System_Fax_After_Delete_User_Fax(){
        String textLoginFailed = "Данный пользователь не имеет доступа к факсу";
        checkAuthToService(loginFax, USER_ACCOUNT_PASSWORD, true, false, titleFailedLogin, textLoginFailed);
    }
}
