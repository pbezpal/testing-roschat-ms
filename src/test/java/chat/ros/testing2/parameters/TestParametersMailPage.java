package chat.ros.testing2.parameters;

import chat.ros.testing2.TestsBase;
import chat.ros.testing2.server.settings.MailPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static chat.ros.testing2.data.SettingsData.MAIL_CONNECT_INPUT_PASSWORD;
import static chat.ros.testing2.helpers.AttachToReport.*;

@Epic(value = "Настройки")
@Feature(value = "Почте")
public class TestParametersMailPage extends MailPage {

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

    @BeforeClass
    public void setUp(){
        testsBase = new TestsBase();
        testsBase.init();
    }

    @BeforeMethod
    public void beforeMethod(){
        field = null;
        softAssert = new SoftAssert();
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

    @AfterMethod(alwaysRun = true)
    public void afterTestMethod(Method method, ITestResult testResult){
        if(!testResult.isSuccess()){
            AScreenshot(method.toString());
            ABrowserLogNetwork();
            ABrowserLogConsole();
        }
    }

    @AfterClass
    public void tearDown(){
        testsBase.dismissWebDriver();
    }
}
