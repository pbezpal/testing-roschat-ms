package chat.ros.testing2.server.services;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.contacts.ContactsPage;
import chat.ros.testing2.server.services.codefortests.TAlertPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.ContactsData.*;
import static chat.ros.testing2.data.ContactsData.USER_SERVICES_TYPE_SIP;
import static chat.ros.testing2.data.SettingsData.*;

@Epic(value = "Сервисы")
@Feature(value = "Система оповещения")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesAlertPage.class)
@ExtendWith(WatcherTests.class)
public class TestAlertPage extends TAlertPage {

    private ContactsPage contactsPage = new ContactsPage();

    @Story(value = "Проверяем информационный текст с ссылкой для перехода к управлению системой оповещения")
    @Description(value = "1. Переходим в раздел Сервсиы -> Система оповещения\n" +
            "2. Проверяем в разделе Пользователи системы оповещения информационный текст с ссылкой")
    @Test
    void test_Check_Info_Text_With_Link_To_Go_MS_Alert_Page(){
        getInstanceAlertPage().isTextLinkToGoMSAlertPage();
    }

    @Story(value = "Проверяем текст в разделе Росчат оповещение, SIP-оповещение")
    @Description(value = "1. Переходим в раздел Сервсиы -> Система оповещения\n" +
            "2. Проверяем в разделе Росчат оповещение, SIP-оповещение информационный текст")
    @Test
    void test_Text_Of_SIP_Alert(){
        getInstanceAlertPage().isInfoText(ALERT_TITLE_SECTION_SIP_ALERT
                ,"Выбор контакта РОСЧАТ. От имени этого контакта будут отправляться сообщения, " +
                        "осуществляться оповещения по телефону, отправляться сообщения по электронной почте");
    }

    @Story(value = "Проверяем текст в разделе SMS-оповещение")
    @Description(value = "1. Переходим в раздел Сервсиы -> Система оповещения\n" +
            "2. Проверяем в разделе SMS-оповещение информационный текст")
    @Test
    void test_Text_Of_SMS_Alert(){
        getInstanceAlertPage().isInfoText(ALERT_TITLE_SECTION_SMS_ALERT
                ,"Для отправки SMS сообщений необходимо настроить учетную запись SMS центра smsc.ru");
    }

    @Story(value = "Проверяем текст в разделе EMAIL-оповещение")
    @Description(value = "1. Переходим в раздел Сервсиы -> Система оповещения\n" +
            "2. Проверяем в разделе SMS-оповещение информационный текст")
    @Test
    void test_Text_Of_EMail_Alert(){
        getInstanceAlertPage().isInfoText(ALERT_TITLE_SECTION_EMAIL_ALERT
                ,"Настройка параметров письма, которое будет отправляться по электронной почте");
    }

    @Story(value = "Проверяем отображение элементов на странице авторизации в управление системой оповещения")
    @Description(value = "1. Переходим в раздел Сервсиы -> Система оповещения\n" +
            "2. Переходим по ссылке на страницу авторизации в упраление системой оповещения\n" +
            "3. Проверяем эелементы на странице авторизации\n" +
            "4. Закрываем вкладку со страницей авторизации в управление системой оповещения")
    @Test
    @Order(1)
    void test_Check_Elements_In_Auth_Alert_Page(){
        checkElementsInAuthPage();
    }

    @Story(value = "Добавление контакта для проверки системы оповещения")
    @Description(value = "1. Переходим в раздел Справочник\n" +
            "2. Добавляем пользователя для системы оповещения\n" +
            "3. Создаём учётную запись для Системы оповещения\n" +
            "4. Добавляем SIP сервис для пользователя Систсемы оповещения\n" +
            "5. Проверяем, что пользователь был успешно создан")
    @Test
    @Order(2)
    void test_Create_Contact_For_Alert(){
        contactsPage
                .actionsContact(CONTACT_FOR_ALERT, EMAIL_CONTACT_FOR_ALERT)
                .addUserAccount(CONTACT_FOR_ALERT, USER_ACCOUNT_PASSWORD, USER_ACCOUNT_ITEM_MENU)
                .addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_SIP, CONTACT_FOR_ALERT)
                .isShowService("h4",USER_SERVICES_TYPE_SIP, true)
                .isShowService("span", CONTACT_FOR_ALERT, true);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем, авторизовацию в управление системой оповещения перед добавлением пользователя системы оповещения")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Вводим логин и пароль тестового пользователя\n" +
            "4. Проверяем, что пользователь не смог авторизоваться\n" +
            "5. Заурваем вкладку управления системой оповещения")
    @Test
    @Order(3)
    void test_Check_Auth_Alert_System_Before_Add_User_Alert(){
        checkAuthToService(CONTACT_FOR_ALERT, USER_ACCOUNT_PASSWORD, true, true);
    }

    @Story(value = "Добавление тестового пользователя для управления системой оповещения")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем кнопку Добавить в разделе Пользователи системы оповещения\n" +
            "3. Выбираем тестового пользователя в списке пользоваетелей\n" +
            "4. Нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что пользователь добавлен в вписко пользователей системы оповещения")
    @Test
    @Order(4)
    void test_Add_Test_User_For_Alert(){
        addContact(ALERT_TITLE_SECTION_USERS, CONTACT_FOR_ALERT);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем, авторизовацию в управление системой оповещения после добавлением пользователя системы оповещения")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Вводим логин и пароль тестового пользователя\n" +
            "4. Проверяем, что пользователь не смог авторизоваться\n" +
            "5. Заурваем вкладку управления системой оповещения")
    @Test
    @Order(5)
    void test_Check_Auth_Alert_System_After_Add_User_Alert(){
        checkAuthToService(CONTACT_FOR_ALERT, USER_ACCOUNT_PASSWORD, true, true);
    }
}
