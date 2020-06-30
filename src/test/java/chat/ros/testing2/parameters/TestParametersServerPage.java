package chat.ros.testing2.parameters;

import chat.ros.testing2.TestsBase;
import chat.ros.testing2.TestsParallelBase;
import chat.ros.testing2.server.settings.ServerPage;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import chat.ros.testing2.server.settings.integration.SKUDPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.*;

import static chat.ros.testing2.data.LoginData.HOST_SERVER;
import static chat.ros.testing2.data.SettingsData.*;
import static chat.ros.testing2.helpers.AttachToReport.*;

@Epic(value = "Настройки")
@Feature(value = "Сервер")
public class TestParametersServerPage extends ServerPage implements TestsParallelBase {

    private SoftAssert softAssert;
    private String field;
    private static final String wrongSymbols = " !\"/#$%";//&'()*+,-.:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~‘’“”—ё№»АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String validSymbols = "1234567890";
    private TestsBase testsBase;

    @DataProvider(name = "empty_value_connect")
    public Object[][] getEmptyValueConnect(){
        return new Object[][] {
                {"",SERVER_CONNECT_HTTP_OTHER_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {HOST_SERVER,"", SERVER_CONNECT_HTTPS_OTHER_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {HOST_SERVER,SERVER_CONNECT_HTTP_OTHER_PORT, "", SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {HOST_SERVER,SERVER_CONNECT_HTTP_OTHER_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT, ""}};
    }

    @DataProvider(name = "empty_value_push")
    public Object[][] getEmptyValuePush(){
        return new Object[][] {
                {"",SERVER_PUSH_LOGIN_SERVER, SERVER_PUSH_PORT_SERVER, SERVER_PUSH_PASSWORD_SERVER},
                {SERVER_PUSH_HOST_SERVER,"", SERVER_PUSH_PORT_SERVER, SERVER_PUSH_PASSWORD_SERVER},
                {SERVER_PUSH_HOST_SERVER,SERVER_PUSH_LOGIN_SERVER, "", SERVER_PUSH_PASSWORD_SERVER},
                {SERVER_PUSH_HOST_SERVER,SERVER_PUSH_LOGIN_SERVER, SERVER_PUSH_PORT_SERVER, ""}};
    }

    @DataProvider(name = "wrong_value")
    public Iterator<Object[]> getWrongValueConnect(){
        List<Object[]> list = new ArrayList<>();
        for(char c: wrongSymbols.toCharArray()) {
            list.add(new Object[]{SERVER_CONNECT_INPUT_HTTP_PORT,c});
            list.add(new Object[]{SERVER_CONNECT_INPUT_HTTPS_PORT,c});
            list.add(new Object[]{SERVER_CONNECT_INPUT_WEBSOCKET_PORT,c});
        }
        return list.iterator();
    }

    @DataProvider(name = "max_length_port")
    public Object[][] getLengthValuePortConnect(){
        return new Object[][] {
                {SERVER_CONNECT_INPUT_HTTP_PORT,"65536"},
                {SERVER_CONNECT_INPUT_HTTPS_PORT,"65536"},
                {SERVER_CONNECT_INPUT_WEBSOCKET_PORT,"65536"}};
    }

    @DataProvider(name = "valid_value")
    public Iterator<Object[]> getValidValue(){
        List<Object[]> list = new ArrayList<>();
        for(char c: validSymbols.toCharArray()) {
            list.add(new Object[]{SERVER_CONNECT_INPUT_HTTP_PORT,c});
            list.add(new Object[]{SERVER_CONNECT_INPUT_HTTPS_PORT,c});
            list.add(new Object[]{SERVER_CONNECT_INPUT_WEBSOCKET_PORT,c});
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
        testsBase.openMS("Настройки", "Сервер");
    }

    @Story(value = "Проверяем настройки Подключение на пустые поля")
    @Description(value = "Вводим в поля формы подключения пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_connect")
    void test_Settings_Connect_Empty_Value(String server, String http, String https, String websocket){
        String ports = "";
        Map<String, String> mapInputValueConnect = new HashMap() {{
            put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, server);
            put(SERVER_CONNECT_INPUT_HTTP_PORT, http);
            put(SERVER_CONNECT_INPUT_HTTPS_PORT, https);
            put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, websocket);
        }};
        setSettingsServer(mapInputValueConnect, SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        if(server.equals("")) field = SERVER_CONNECT_INPUT_PUBLIC_NETWORK;
        else if(http.equals("")){
            field = SERVER_CONNECT_INPUT_HTTP_PORT;
            ports = ", " + https + ", " + websocket;
        }
        else if(https.equals("")){
            field = SERVER_CONNECT_INPUT_HTTPS_PORT;
            ports = http + ",, " + websocket;
        }
        else {
            field = SERVER_CONNECT_INPUT_WEBSOCKET_PORT;
            ports = http + ", " + https + ",";
        }
        softAssert.assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        if(server.equals("")){
            softAssert.assertTrue(isShowValueInField(
                    SERVER_CONNECT_TITLE_FORM,
                    SERVER_CONNECT_INPUT_PUBLIC_NETWORK + ":",
                    server,
                    false),
                    "Значение " + server + " отображается в поле " + SERVER_CONNECT_INPUT_PUBLIC_NETWORK + ":");
        }else {
            softAssert.assertTrue(isShowValueInField(
                    SERVER_CONNECT_TITLE_FORM,
                    SERVER_CONNECT_FIELD_PORTS,
                    ports,
                    false),
                    "Значение " + ports + " отображается в поле " + SERVER_CONNECT_FIELD_PORTS);
        }
        softAssert.assertAll();
    }

    @Story(value = "Проверяем настройки Push сервера на пустые поля")
    @Description(value = "Вводим в поля формы Лицензирование и обслуживание пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_push")
    void test_Settings_Push_Empty_Value(String server, String login, String port, String password){
        Map<String, String> mapInputValuePush = new HashMap() {{
            put(SERVER_PUSH_INPUT_HOST, server);
            put(SERVER_PUSH_INPUT_LOGIN, login);
            put(SERVER_PUSH_INPUT_PORT, port);
            put(SERVER_PUSH_INPUT_PASSWORD, password);
        }};
        setSettingsServer(mapInputValuePush, SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        if(server.equals("")) field = SERVER_PUSH_INPUT_HOST;
        else if(login.equals("")) field = SERVER_PUSH_INPUT_LOGIN;
        else if(port.equals("")) field = SERVER_PUSH_INPUT_PORT;
        else field = SERVER_PUSH_INPUT_PASSWORD;
        softAssert.assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
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
