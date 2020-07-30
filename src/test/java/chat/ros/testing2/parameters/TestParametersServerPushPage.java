package chat.ros.testing2.parameters;

import chat.ros.testing2.TestsBase;
import chat.ros.testing2.server.settings.ServerPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static chat.ros.testing2.data.ParametersData.*;
import static chat.ros.testing2.data.SettingsData.*;

@Epic(value = "Настройки")
@Feature(value = "Сервер")
public class TestParametersServerPushPage extends ServerPage {


    private String field;
    private TestsBase testsBase;


    public Object[][] getEmptyValuePush(){
        return new Object[][] {
                {"",SERVER_PUSH_LOGIN_SERVER, SERVER_PUSH_PORT_SERVER, SERVER_PUSH_PASSWORD_SERVER},
                {SERVER_PUSH_HOST_SERVER,"", SERVER_PUSH_PORT_SERVER, SERVER_PUSH_PASSWORD_SERVER},
                {SERVER_PUSH_HOST_SERVER,SERVER_PUSH_LOGIN_SERVER, "", SERVER_PUSH_PASSWORD_SERVER},
                {SERVER_PUSH_HOST_SERVER,SERVER_PUSH_LOGIN_SERVER, SERVER_PUSH_PORT_SERVER, ""}};
    }

    public Iterator<Object[]> getWrongValueHostPushServer(){
        List<Object[]> list = new ArrayList<>();
        for(String host: WRONG_VALUE_HOST) {
            list.add(new Object[]{host});
        }
        return list.iterator();
    }

    public Iterator<Object[]> getWrongValuePushPort(){
        List<Object[]> list = new ArrayList<>();
        for(char c: WRONG_SYMBOLS_PORT.toCharArray()) {
            list.add(new Object[]{SERVER_PUSH_PORT_SERVER,c});
        }
        return list.iterator();
    }

    public Iterator<Object[]> getValidValuePushPort(){
        List<Object[]> list = new ArrayList<>();
        for(char c: VALID_SYMBOLS_PORT.toCharArray()) {
            list.add(new Object[]{c});
        }
        return list.iterator();
    }

    /*@BeforeClass
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

    @Story(value = "Проверяем невалидные значение хоста в поле 'IP адрес' в настройках Лицензирование и обслуживание")
    @Description(value = "Вводим невалидный адрес хоста в поле 'IP адрес'" +
            "в настройках Лицензирование и обслуживание и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный адрес' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "wrong_value_host_push_server")
    void test_Wrong_Host_Public_Address_Connect(String address){
        Map<String, String> mapInputValueConnect = new HashMap() {{
            put(SERVER_PUSH_INPUT_HOST, address);
        }};
        setSettingsServer(mapInputValueConnect, SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        softAssert.assertEquals(isShowTextWrongValue(SERVER_PUSH_INPUT_HOST),"Невалидный адрес",
                "Надпись 'Невалидный адрес' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        softAssert.assertAll();
    }

    @Story(value = "Проверяем номер максимального порта в настройках Лицензирование и обслуживание")
    @Description(value = "Вводим в поля настройки портов формы Лицензирование и обслуживание порт 65536 и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test
    void test_Max_Length_Ports_Push(){
        clickButtonSettings(SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        sendInputForm(SERVER_PUSH_INPUT_PORT, MORE_MAX_VALUE_PORT);
        softAssert.assertEquals(isShowTextWrongValue(SERVER_PUSH_INPUT_PORT),"Невалидный порт",
                "Надпись 'Невалидный порт' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Формы редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        softAssert.assertAll();
    }

    @Story(value = "Проверяем невалидные значения портов в настройках Лицензирование и обслуживание")
    @Description(value = "Вводим невалидные значения портов в настройках Лицензирование и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test(dataProvider = "wrong_value_push_port")
    void test_No_Valid_Ports_Push(String field, Character c){
        softAssert = new SoftAssert();
        clickButtonSettings(SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        sendInputForm(field, c.toString());
        softAssert.assertEquals(isShowTextWrongValue(field),"Невалидный порт",
                "Надпись 'Невалидный порт' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Формы редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        softAssert.assertAll();
    }

    @Story(value = "Проверяем валидные значения порта в настройках Лицензирование и обслуживание")
    @Description(value = "Вводим валидные значения портов в настройках Лицензирование и проверяем: \n" +
            "Сохраняются ли настройки с валидным значением в поле")
    @Test(dataProvider = "valid_value_push_port")
    void test_Valid_Ports_Push(Character c){
        softAssert = new SoftAssert();
        clickButtonSettings(SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        sendInputForm(SERVER_PUSH_PORT_SERVER, c.toString());
        clickButtonSave();
        softAssert.assertTrue(isFormConfirmActions(true), "Не появилась форма, Подтвердите свои действия");
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
    }*/
}
