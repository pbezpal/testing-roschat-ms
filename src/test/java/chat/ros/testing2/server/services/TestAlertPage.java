package chat.ros.testing2.server.services;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.contacts.ContactsPage;
import chat.ros.testing2.server.mailpage.TMailPage;
import chat.ros.testing2.server.services.codefortests.TAlertPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private TMailPage tMailPage = new TMailPage();
    private String loginAlert = CONTACT_FOR_ALERT + "@ros.chat";
    private String wrongLoginAlert = CONTACT_FOR_ALERT + "ros.chat";
    private String wrongPasswordAlert = "87654321";

    private String soundFileName = "conf-adminmenu.wav";
    private String pathSoundFile = getClass().
            getClassLoader().
            getResource("alert/" + soundFileName).
            getFile();

    private Map<String, String> dataMessage = new HashMap() {{
        put(ALERT_INPUT_NAME, ALERT_VALUE_NAME_FOR_MESSAGE);
        put(ALERT_INPUT_DESCRIPTION, ALERT_VALUE_DESCRIPTION_FOR_MESSAGE);
        put(ALERT_INPUT_MESSAGE, ALERT_VALUE_TEXT_FOR_MESSAGE);
    }};

    private Map<String, String> dataAlert = new HashMap() {{
        put(ALERT_INPUT_NAME, ALERT_VALUE_NAME_FOR_ALERT);
        put(ALERT_INPUT_DESCRIPTION, ALERT_VALUE_DESCRIPTION_FOR_ALERT);
    }};

    private Map<String, String> dataTask = new HashMap() {{
        put(ALERT_INPUT_NAME, ALERT_VALUE_NAME_FOR_TASK);
        put(ALERT_INPUT_DESCRIPTION, ALERT_VALUE_DESCRIPTION_FOR_TASK);
    }};

    @Parameterized.Parameters(name = "{0}")
    private static Object[][] getItemsLeftMenuWithClassIcon(){
        return new Object[][] {
                {ALERT_TITLE_ITEM_LEFT_MENU_TASKS, "mdi-calendar-check"},
                {ALERT_TITLE_ITEM_LEFT_MENU_JOURNALS, "mdi-notebook-outline"}
        };
    }

    @Parameterized.Parameters(name = "{0}")
    private static Object[][] getStatusAlertServices(){
        return new Object[][] {
                {"Сообщение", "mdi-message-processing", "bad-login-psw", ""},
                {"Email", "mdi-email-outline", "error", "unknown"},
                {"SMS", "mdi-cellphone-message", "ip is blocked, https://smsc.ru/faq/99/", "IP адрес временно заблокирован"}
        };
    }

    @Parameterized.Parameters(name = "{0}")
    private static Iterable<String> receiveTopMenuItems() {
        ArrayList<String> data = new ArrayList<>();

        for (String item: ALERT_TITLE_TOP_MENU_ITEMS) {
            data.add(item);
        }

        return data;
    }

    @Parameterized.Parameters(name = "{1}")
    private static Object[][] getTitleModalWindow(){
        return new Object[][] {
                {ALERT_TITLE_ITEM_TOP_MENU_TASKS, "Создание задания"},
                {ALERT_TITLE_ITEM_TOP_MENU_LIST_ALERT, "Создание списка оповещения"},
                {ALERT_TITLE_ITEM_TOP_MENU_MESSAGES, "Создание сообщения"}
        };
    }

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
                .actionsContact(CONTACT_FOR_ALERT, MAIL_INFOTEK_FROM_MAIL)
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
        String titleLoginFailed = "Неудачная попытка авторизации";
        String textLoginFailed = "Данный пользователь не имеет доступа к системе оповещения";
        checkAuthToService(loginAlert, USER_ACCOUNT_PASSWORD, true, false, titleLoginFailed, textLoginFailed);
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

    @Story(value = "Проверяем авторизацию с некорректным логином")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Вводим некорректный логин и корректный пароль и нажимаем кнопку Войти\n" +
            "4. Проверяем, что пользователь не смог авторизоваться\n" +
            "5. Закрываем вкладку управления системой оповещения")
    @Test
    @Order(5)
    void test_Check_Auth_System_Alert_With_Wrong_Login(){
        String titleLoginFailed = "Неудачная попытка авторизации";
        String textLoginFailed = "Нет такого пользователя";
        checkAuthToService(wrongLoginAlert, USER_ACCOUNT_PASSWORD, true, false, titleLoginFailed, textLoginFailed);
    }

    @Story(value = "Проверяем авторизацию с некорректным паролем")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Вводим корректный логин и некорректный пароль и нажимаем кнопку Войти\n" +
            "4. Проверяем, что пользователь не смог авторизоваться\n" +
            "5. Закрываем вкладку управления системой оповещения")
    @Test
    @Order(5)
    void test_Check_Auth_System_Alert_With_Wrong_Password(){
        String titleLoginFailed = "Неудачная попытка авторизации";
        String textPasswordFailed = "Проверьте правильность введенного пароля";
        checkAuthToService(loginAlert, wrongPasswordAlert, true, false, titleLoginFailed, textPasswordFailed);
    }

    @Story(value = "Проверяем, функцию остаться в системе после авторизации в управление системой оповещения")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Вводим логин и пароль тестового пользователя и и оставляем галочку в поле Запомнить\n" +
            "4. Нажимаем кнопку Войти\n" +
            "4. Проверяем, что пользователь смог авторизоваться\n" +
            "5. Закрываем вкладку с авторизованной системой оповещения\n" +
            "6. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "7. Проверяем, что сразу открылась авторизованная страница управление системой оповещения" +
            "8. Нажимаем на иконку или имя пользователя в управление системой оповещения\n" +
            "9. Нажимаем кнопку Выход и подтверждаем выход\n" +
            "10. Закрываем вкладку управления системой оповещения")
    @Test
    @Order(5)
    void test_Check_Auth_Alert_System_After_Add_User_Alert_With_Stay_System(){
        checkAuthToService(loginAlert, USER_ACCOUNT_PASSWORD, true);
    }

    @Story(value = "Проверяем, авторизовацию в управление системой оповещения после добавлением пользователя системы оповещения")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Вводим логин и пароль тестового пользователя и и оставляем галочку в поле Запомнить\n" +
            "4. Нажимаем кнопку Войти\n" +
            "4. Проверяем, что пользователь смог авторизоваться\n" +
            "5. Закрываем вкладку с авторизованной системой оповещения\n" +
            "6. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "7. Проверяем, что открылась страница для авторизации в управление системой оповещения" +
            "8. Закрываем вкладку управления системой оповещения")
    @Test
    @Order(5)
    void test_Check_Auth_Alert_System_After_Add_User_Alert_Without_Stay_System(){
        checkAuthToService(loginAlert, USER_ACCOUNT_PASSWORD, false);
    }

    @Story(value = "Проверяем, статус сервиса оповещения")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Проверяем, авторизованы ли в управление системой оповещения\n" +
            "4. Если не авторизованы, вводим логин и пароль и нажимаем кнопку Войти\n" +
            "5. Проверяем статус сервиса оповещения"
    )
    @ParameterizedTest(name="Alert={0}")
    @MethodSource(value = "getStatusAlertServices")
    @Order(5)
    void test_Check_Status_Alert(String alert, String classNameIcon, String status, String error){
        if(error.equals(""))
            checkStatusAlertServices(alert, classNameIcon, status, loginAlert, USER_ACCOUNT_PASSWORD);
        else
            checkStatusAlertServices(alert, classNameIcon, status, error, loginAlert, USER_ACCOUNT_PASSWORD);
    }

    @Story(value = "Проверяем, заголовок модального окна")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Проверяем, авторизованы ли в управление системой оповещения\n" +
            "4. Если не авторизованы, вводим логин и пароль и нажимаем кнопку Войти\n" +
            "5. Переходим в раздел меню\n" +
            "6. Нажимаем кнопку Добавить и проверяем, появилось ли модальное окно\n" +
            "7. Проверяем заголовок модальное окна\n" +
            "8. Закрываем моадльное окно и проверяем, что модальное окно закрылось"
    )
    @ParameterizedTest(name="Menu={0}")
    @MethodSource(value = "getTitleModalWindow")
    @Order(5)
    void test_Check_Title_Modal_Window(String itemMenu, String title){
        checkTitleOfModalWindow(itemMenu, title, loginAlert, USER_ACCOUNT_PASSWORD);
    }

    @Story(value = "Проверяем, заголовок модального окна при добавление звукового файла")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Проверяем, авторизованы ли в управление системой оповещения\n" +
            "4. Если не авторизованы, вводим логин и пароль и нажимаем кнопку Войти\n" +
            "5. Переходим в меню Звуковые файлы\n" +
            "6. Нажимаем кнопку Добавить и проверяем, появилось ли модальное окно\n" +
            "7. Проверяем заголовок модальное окна\n" +
            "8. Закрываем моадльное окно и проверяем, что модальное окно закрылось"
    )
    @Test
    @Order(5)
    void test_Check_Title_Modal_Window_When_Add_Sound_File(){
        checkTitleOfModalWindow(pathSoundFile, loginAlert, USER_ACCOUNT_PASSWORD);
    }

    @Story(value = "Добавляем звуковой файл")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Проверяем, авторизованы ли в управление системой оповещения\n" +
            "4. Если не авторизованы, вводим логин и пароль и нажимаем кнопку Войти\n" +
            "5. Переходим в меню Звуковые файлы\n" +
            "6. Нажимаем кнопку Добавить и выбираем звуковой файл\n" +
            "7. Вводим название звукового файла и нажимаем кнопку сохранить\n" +
            "8. Проверяем, что модальное окно закрылось и в таблице отображается звуковой файл"
    )
    @Test
    @Order(5)
    void test_Add_Sound_File(){
        uploadMusicFile(pathSoundFile, soundFileName, loginAlert, USER_ACCOUNT_PASSWORD);
    }

    @Story(value = "Добавляем сообщение")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Проверяем, авторизованы ли в управление системой оповещения\n" +
            "4. Если не авторизованы, вводим логин и пароль и нажимаем кнопку Войти\n" +
            "5. Переходим в меню Сообщения\n" +
            "6. Нажимаем кнопку Добавить и заполняем поля модального кона\n" +
            "7. Нажимаем кнопку сохранить\n" +
            "8. Проверяем, что модальное окно закрылось и в таблице сохранилось сообщение"
    )
    @Test
    @Order(5)
    void test_Add_Message(){
        addMessages(dataMessage, loginAlert, USER_ACCOUNT_PASSWORD);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавляем оповещение")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Проверяем, авторизованы ли в управление системой оповещения\n" +
            "4. Если не авторизованы, вводим логин и пароль и нажимаем кнопку Войти\n" +
            "5. Переходим в меню Список оповещения\n" +
            "6. Нажимаем кнопку Добавить и заполняем поля модального кона\n" +
            "7. Добавляем контакта и проверяем иконки и сервисы\n" +
            "7. Нажимаем кнопку сохранить\n" +
            "8. Проверяем, что модальное окно закрылось и в таблице сохранилось оповещение"
    )
    @Test
    @Order(5)
    void test_Add_Alert(){
        addListAlert(dataAlert, CONTACT_FOR_ALERT, ALERT_LIST_ICONS_ALERT, MAIL_INFOTEK_FROM_MAIL, loginAlert, USER_ACCOUNT_PASSWORD);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавляем контакт для Росчат, SIP и EMAIL оповещений")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на кнопку Выбрать контакт в разделе Росчат оповещение, SIP-оповещение\n" +
            "3. Выбираем контакт для оповещения и нажимаем кнопку Сохранить\n" +
            "4. Проверяем, что выбранный контакт отображается в полях для оповещения")
    @Test
    @Order(6)
    void test_Add_Contact_For_SIP_And_MAIL_Alert(){
        addContactForRoschatAlertAndSipAlert(CONTACT_FOR_ALERT);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем элементы левого меню на странице управления системными оповещениями")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Проверяем, авторизованы ли в управление системой оповещения\n" +
            "4. Если не авторизованы, вводим логин и пароль и нажимаем кнопку Войти\n" +
            "5. Кликаем на пункт меню и проверяем все эелементы меню\n" +
            "6. Закрываем вкладку с управлением системой оповещения")
    @ParameterizedTest(name="Item menu={0}")
    @MethodSource(value = "getItemsLeftMenuWithClassIcon")
    @Order(6)
    void test_Check_Elements_Left_Menu(String item, String classNameIcon){
        checkElementsLeftMenu(item, classNameIcon, loginAlert, USER_ACCOUNT_PASSWORD);
    }

    @Story(value = "Проверяем элементы верхнего меню в разделе Задания на странице управления системными оповещениями")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Проверяем, авторизованы ли в управление системой оповещения\n" +
            "4. Если не авторизованы, вводим логин и пароль и нажимаем кнопку Войти\n" +
            "5. Кликаем на пункт меню и проверяем все эелементы меню\n" +
            "6. Закрываем вкладку с управлением системой оповещения")
    @ParameterizedTest(name="Item menu={0}")
    @MethodSource(value = "receiveTopMenuItems")
    @Order(6)
    void test_Check_Elements_Top_Menu(String item){
        checkListTopMenuItems(item, loginAlert, USER_ACCOUNT_PASSWORD);
    }

    @Story(value = "Проверяем, статус сервиса сообщений после добавление контакта")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Проверяем, авторизованы ли в управление системой оповещения\n" +
            "4. Если не авторизованы, вводим логин и пароль и нажимаем кнопку Войти\n" +
            "5. Проверяем статус сервиса Сообщение"
    )
    @Test
    @Order(7)
    void test_Check_Status_Message_After_Add_Contact_For_SIP_And_MAIL_Alert(){
        checkStatusAlertServices("Сообщение", "mdi-message-processing", "ок", loginAlert, USER_ACCOUNT_PASSWORD);
    }

    @Story(value = "Настраиваем почту для оповещения")
    @Description(value = "1. Переходим в раздел Настройки -> Почта\n" +
            "2. Нажимаем кнопку Настроить и заполняем данные в модальном окне\n" +
            "4. Нажимаем кнопку Сохранить и Нажимаем кнопку Перезагрузить в модальном окне\n" +
            "5. Нажимаем кнопку Проверить\n" +
            "6. Проверяем, что почта настроена корректно")
    @Test
    @Order(8)
    void test_Settings_Email_For_Alert(){
        tMailPage.settingsMail(tMailPage.getSettingsMailServer(MAIL_INFOTEK_SERVER,
                MAIL_INFOTEK_USERNAME,
                MAIL_INFOTEK_PASSWORD,
                MAIL_PORT_NO_SECURITY,
                MAIL_INFOTEK_FROM_USER,
                MAIL_INFOTEK_FROM_MAIL),
                MAIL_TYPE_SECURITY_NO);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем, статус сервиса EMail после настройки почты")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Проверяем, авторизованы ли в управление системой оповещения\n" +
            "4. Если не авторизованы, вводим логин и пароль и нажимаем кнопку Войти\n" +
            "5. Проверяем статус сервиса EMail"
    )
    @Test
    @Order(9)
    void test_Check_Status_Email_After_Settings_EMail(){
        checkStatusAlertServices("Email", "mdi-email-outline", "ок", loginAlert, USER_ACCOUNT_PASSWORD);
    }

    @Story(value = "Добавляем задачу")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Проверяем, авторизованы ли в управление системой оповещения\n" +
            "4. Если не авторизованы, вводим логин и пароль и нажимаем кнопку Войти\n" +
            "5. Переходим в меню СПИСОК ЗАДАНИЙ\n" +
            "6. Нажимаем кнопку Добавить и заполняем поля модального кона\n" +
            "7. Выбираем сервисы и настраиваем их\n" +
            "8. Нажимаем кнопку сохранить\n" +
            "9. Проверяем, что модальное окно закрылось и в таблице сохранилось задание"
    )
    @Test
    @Order(9)
    void test_Add_Task(){
        addListTask(dataTask, ALERT_VALUE_NAME_FOR_ALERT, ALERT_VALUE_NAME_FOR_MESSAGE, soundFileName, loginAlert, USER_ACCOUNT_PASSWORD);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Запускаем задачу")
    @Description(value = "1. Переходим в раздел Сервисы -> Система оповещения\n" +
            "2. Нажимаем на ссылку для перехода в управление системой оповещением\n" +
            "3. Проверяем, авторизованы ли в управление системой оповещения\n" +
            "4. Если не авторизованы, вводим логин и пароль и нажимаем кнопку Войти\n" +
            "5. Переходим в меню СПИСОК ЗАДАНИЙ\n" +
            "6. Удаляем письма на почте" +
            "7. Нажимаем кнопку Запустить у задачи\n" +
            "8. Проверяем, появилась ли форма запуска задачи\n" +
            "9. Ждём, когда пропадёт форма запуска задачи\n" +
            "10. Проверяем, пришло ли письмо на почту"
    )
    @Test
    @Order(10)
    void test_Start_Task(){
        startTask(
                ALERT_VALUE_NAME_FOR_TASK
                , MAIL_INFOTEK_USERNAME
                , MAIL_INFOTEK_PASSWORD
                , MAIL_INFOTEK_SERVER
                , loginAlert
                , USER_ACCOUNT_PASSWORD
                , ALERT_VALUE_TEXT_FOR_MESSAGE
        );
        TestStatusResult.setTestResult(true);
    }
}
