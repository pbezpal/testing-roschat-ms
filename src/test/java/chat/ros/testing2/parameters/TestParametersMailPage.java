package chat.ros.testing2.parameters;

import chat.ros.testing2.TestsBase;
import chat.ros.testing2.TestsParallelBase;
import chat.ros.testing2.server.settings.MailPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.*;

import static chat.ros.testing2.data.ParametersData.*;
import static chat.ros.testing2.data.SettingsData.*;
import static chat.ros.testing2.data.SettingsData.MAIL_CONNECT_INPUT_PASSWORD;
import static chat.ros.testing2.helpers.AttachToReport.*;

@Epic(value = "Настройки")
@Feature(value = "Почте")
public class TestParametersMailPage extends MailPage implements TestsParallelBase {

    private SoftAssert softAssert;
    private String field;
    private TestsBase testsBase;

    @DataProvider(name = "empty_value_mail")
    public Object[][] getEmptyValueMail(){
        return new Object[][] {
                {"",MAIL_INFOTEK_USERNAME, MAIL_INFOTEK_PASSWORD, MAIL_PORT_SSL, MAIL_INFOTEK_FROM_USER, MAIL_INFOTEK_FROM_MAIL},
                {MAIL_INFOTEK_SERVER,"", MAIL_INFOTEK_PASSWORD, MAIL_PORT_SSL, MAIL_INFOTEK_FROM_USER, MAIL_INFOTEK_FROM_MAIL},
                {MAIL_INFOTEK_SERVER,MAIL_INFOTEK_USERNAME, "", MAIL_PORT_SSL, MAIL_INFOTEK_FROM_USER, MAIL_INFOTEK_FROM_MAIL},
                {MAIL_INFOTEK_SERVER,MAIL_INFOTEK_USERNAME, MAIL_INFOTEK_PASSWORD, "", MAIL_INFOTEK_FROM_USER, MAIL_INFOTEK_FROM_MAIL},
                {MAIL_INFOTEK_SERVER,MAIL_INFOTEK_USERNAME, MAIL_INFOTEK_PASSWORD, MAIL_PORT_SSL, "", MAIL_INFOTEK_FROM_MAIL},
                {MAIL_INFOTEK_SERVER,MAIL_INFOTEK_USERNAME, MAIL_INFOTEK_PASSWORD, MAIL_PORT_SSL, MAIL_INFOTEK_FROM_USER, ""}};
    }

    @DataProvider(name = "wrong_value_server_mail")
    public Iterator<Object[]> getWrongValueHostMailServer(){
        List<Object[]> list = new ArrayList<>();
        for(String host: WRONG_VALUE_HOST) {
            list.add(new Object[]{host});
        }
        return list.iterator();
    }

    @DataProvider(name = "wrong_symbols_mail_port")
    public Iterator<Object[]> getWrongValueMailPort(){
        List<Object[]> list = new ArrayList<>();
        for(char c: WRONG_SYMBOLS_PORT.toCharArray()) {
            list.add(new Object[]{c});
        }
        return list.iterator();
    }

    @DataProvider(name = "valid_value_mail_port")
    public Iterator<Object[]> getValidValueMailPorts(){
        List<Object[]> list = new ArrayList<>();
        for(char c: VALID_SYMBOLS_PORT.toCharArray()) {
            list.add(new Object[]{c});
        }
        return list.iterator();
    }

    @DataProvider(name = "wrong_value_mail")
    public Iterator<Object[]> getWrongValueMail(){
        List<Object[]> list = new ArrayList<>();
        for(String host: WRONG_VALUE_EMAIL) {
            list.add(new Object[]{host});
        }
        return list.iterator();
    }

    @BeforeClass
    void setUp(){
        testsBase = new TestsBase();
    }

    @BeforeMethod
    public void beforeMethod(){
        field = null;
        softAssert = new SoftAssert();
        testsBase.init();
        testsBase.openMS("Настройки", "Почта");
    }

    @Story(value = "Проверяем настройки Почты на пустые поля")
    @Description(value = "Вводим в поля формы Почта пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_mail")
    void test_Settings_Mail_Empty_Value(String server, String username, String password, String port, String fromUser, String fromMail){
        Map<String, String> mapInputValueMail = new HashMap() {{
            put(MAIL_CONNECT_INPUT_EMAIL_SERVER, server);
            put(MAIL_CONNECT_INPUT_USERNAME, username);
            put(MAIL_CONNECT_INPUT_PASSWORD, password);
            put(MAIL_CONNECT_INPUT_EMAIL_PORT, port);
            put(MAIL_CONTACT_INPUT_FROM_USER, fromUser);
            put(MAIL_CONTACT_INPUT_FROM_MAIL, fromMail);
        }};
        setSettingsServer(mapInputValueMail, SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        if(server.equals("")) field = MAIL_CONNECT_INPUT_EMAIL_SERVER;
        else if(username.equals("")) field = MAIL_CONNECT_INPUT_USERNAME;
        else if(password.equals("")) field = MAIL_CONNECT_INPUT_PASSWORD;
        else if(port.equals("")) field = MAIL_CONNECT_INPUT_EMAIL_PORT;
        else if(fromUser.equals("")) field = MAIL_CONTACT_INPUT_FROM_USER;
        else field = MAIL_CONTACT_INPUT_FROM_MAIL;
        softAssert.assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        if(field != MAIL_CONNECT_INPUT_PASSWORD) {
            softAssert.assertTrue(isShowValueInField(
                    field,
                    "",
                    false),
                    "В поле " + field + ", сохранилось пустое значение");
        }
        softAssert.assertAll();
    }

    @Story(value = "Проверяем невалидные значение хоста в поле 'Адрес почтового сервера' в настройках Почты")
    @Description(value = "Вводим невалидные значения в поле 'Адрес почтового сервера' в настройках Почты и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный адрес' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "wrong_value_server_mail")
    void test_Wrong_Host_Server_Email(String address){
        Map<String, String> mapInputValueConnect = new HashMap() {{
            put(MAIL_CONNECT_INPUT_EMAIL_SERVER, address);
        }};
        setSettingsServer(mapInputValueConnect, SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        softAssert.assertEquals(isShowTextWrongValue(MAIL_CONNECT_INPUT_EMAIL_SERVER),"Невалидный адрес",
                "Надпись 'Невалидный адрес' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        softAssert.assertTrue(isShowValueInField(
                SERVER_CONNECT_TITLE_FORM,
                MAIL_CONNECT_INPUT_EMAIL_SERVER,
                address,
                false),
                "Значение " + address + " отображается в поле " + MAIL_CONNECT_INPUT_EMAIL_SERVER + ":");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем максимальный порт в настройках Почты")
    @Description(value = "Вводим в поле настройки порта формы Почта порт 65536 и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test
    void test_Max_Length_Port_Email(){
        clickButtonSettings(SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        sendInputForm(MAIL_CONNECT_INPUT_EMAIL_PORT, MORE_MAX_VALUE_PORT);
        softAssert.assertEquals(isShowTextWrongValue(MAIL_CONNECT_INPUT_EMAIL_PORT),"Невалидный порт",
                "Надпись 'Невалидный порт' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Формы редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        softAssert.assertTrue(isShowSymbolsInField(
                SERVER_CONNECT_TITLE_FORM,
                MAIL_CONNECT_INPUT_EMAIL_PORT,
                MORE_MAX_VALUE_PORT,
                false),
                "Значение " + MORE_MAX_VALUE_PORT + " отображается в поле " + MAIL_CONNECT_INPUT_EMAIL_PORT);
        softAssert.assertAll();
    }

    @Story(value = "Проверяем невалидные значение почты в поле 'Почтовый адрес' в настройках Почты")
    @Description(value = "Вводим невалидные значения в поле 'Почтовый адрес' в настройках Почты и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный адрес' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "wrong_value_mail")
    void test_Wrong_Email(String mail){
        Map<String, String> mapInputValueConnect = new HashMap() {{
            put(MAIL_CONTACT_INPUT_FROM_MAIL, mail);
        }};
        setSettingsServer(mapInputValueConnect, SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        softAssert.assertEquals(isShowTextWrongValue(MAIL_CONTACT_INPUT_FROM_MAIL),"Невалидный адрес",
                "Надпись 'Невалидный адрес' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        softAssert.assertTrue(isShowValueInField(
                SERVER_CONNECT_TITLE_FORM,
                MAIL_CONTACT_INPUT_FROM_MAIL,
                mail,
                false),
                "Значение " + mail + " отображается в поле " + MAIL_CONTACT_INPUT_FROM_MAIL + ":");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTestMethod(Method method, ITestResult testResult){
        if(!testResult.isSuccess()){
            AScreenshot(method.toString());
            ABrowserLogNetwork();
            ABrowserLogConsole();
        }
    }
}
