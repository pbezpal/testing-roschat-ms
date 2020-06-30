package chat.ros.testing2.parameters;

import chat.ros.testing2.TestsBase;
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
import static chat.ros.testing2.data.SettingsData.SERVER_CONNECT_INPUT_WEBSOCKET_PORT;
import static chat.ros.testing2.helpers.AttachToReport.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic(value = "Настройки")
@Feature(value = "Параметризированные тесты")
public class TestParametersEmptyValue extends ServerPage implements IntegrationPage {

    private SoftAssert softAssert;
    private SKUDPage skudPage;
    private String field;
    private static final String wrongSymbols = " !\"/#$%";//&'()*+,-.:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~‘’“”—ё№»АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String validSymbols = "1234567890";

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

    @DataProvider(name = "empty_value_network")
    public Object[][] getEmptyValueNetwork(){
        return new Object[][] {
                {"",TELEPHONY_NETWORK_FRONT_IP, TELEPHONY_NETWORK_INSIDE_IP},
                {TELEPHONY_NETWORK_PUBLIC_ADDRESS,"", TELEPHONY_NETWORK_INSIDE_IP},
                {TELEPHONY_NETWORK_PUBLIC_ADDRESS,TELEPHONY_NETWORK_FRONT_IP, ""}};
    }

    @DataProvider(name = "empty_value_sip")
    public Object[][] getEmptyValueSip(){
        return new Object[][] {
                {"",TELEPHONY_SIP_MAX_PORT},
                {TELEPHONY_SIP_MIN_PORT,""}};
    }

    @DataProvider(name = "empty_value_turn")
    public Object[][] getEmptyValueTurn(){
        return new Object[][] {
                {"",TELEPHONY_TURN_MAX_PORT, TELEPHONY_TURN_REALM, TELEPHONY_TURN_SECRET},
                {TELEPHONY_TURN_MIN_PORT,"", TELEPHONY_TURN_REALM, TELEPHONY_TURN_SECRET},
                {TELEPHONY_TURN_MIN_PORT,TELEPHONY_TURN_MAX_PORT, "", TELEPHONY_TURN_SECRET},
                {TELEPHONY_TURN_MIN_PORT,TELEPHONY_TURN_MAX_PORT, TELEPHONY_TURN_REALM, ""}};
    }

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

    @DataProvider(name = "empty_value_om")
    public Object[][] getEmptyValueOM(){
        return new Object[][] {
                {"",INTEGRATION_SERVICE_OM_PORT_BD, INTEGRATION_SERVICE_OM_LOGIN_DB},
                {INTEGRATION_SERVICE_OM_IP_ADDRESS,"", INTEGRATION_SERVICE_OM_LOGIN_DB},
                {INTEGRATION_SERVICE_OM_IP_ADDRESS,INTEGRATION_SERVICE_OM_PORT_BD, ""}};
    }

    @DataProvider(name = "empty_value_orion")
    public Object[][] getEmptyValueOrion(){
        return new Object[][] {
                {"",INTEGRATION_SERVICE_ORION_PORT, INTEGRATION_SERVICE_ORION_OUTGOING_PORT},
                {INTEGRATION_SERVICE_ORION_IP_ADDRESS,"", INTEGRATION_SERVICE_ORION_OUTGOING_PORT},
                {INTEGRATION_SERVICE_ORION_IP_ADDRESS,INTEGRATION_SERVICE_ORION_PORT, ""}};
    }

    @DataProvider(name = "empty_value_perco")
    public Object[][] getEmptyValuePerco(){
        return new Object[][] {
                {"",INTEGRATION_SERVICE_PERCO_PORT_MODULE, INTEGRATION_SERVICE_PERCO_IP_SERVER, INTEGRATION_SERVICE_PERCO_PORT_SERVER, INTEGRATION_SERVICE_PERCO_USERNAME, INTEGRATION_SERVICE_PERCO_KEY},
                {INTEGRATION_SERVICE_PERCO_IP_MODULE,"", INTEGRATION_SERVICE_PERCO_IP_SERVER, INTEGRATION_SERVICE_PERCO_PORT_SERVER, INTEGRATION_SERVICE_PERCO_USERNAME, INTEGRATION_SERVICE_PERCO_KEY},
                {INTEGRATION_SERVICE_PERCO_IP_MODULE,INTEGRATION_SERVICE_PERCO_PORT_MODULE, "", INTEGRATION_SERVICE_PERCO_PORT_SERVER, INTEGRATION_SERVICE_PERCO_USERNAME, INTEGRATION_SERVICE_PERCO_KEY},
                {INTEGRATION_SERVICE_PERCO_IP_MODULE,INTEGRATION_SERVICE_PERCO_PORT_MODULE, INTEGRATION_SERVICE_PERCO_IP_SERVER, "", INTEGRATION_SERVICE_PERCO_USERNAME, INTEGRATION_SERVICE_PERCO_KEY},
                {INTEGRATION_SERVICE_PERCO_IP_MODULE,INTEGRATION_SERVICE_PERCO_PORT_MODULE, INTEGRATION_SERVICE_PERCO_IP_SERVER, INTEGRATION_SERVICE_PERCO_PORT_SERVER, "", INTEGRATION_SERVICE_PERCO_KEY},
                {INTEGRATION_SERVICE_PERCO_IP_MODULE,INTEGRATION_SERVICE_PERCO_PORT_MODULE, INTEGRATION_SERVICE_PERCO_IP_SERVER, INTEGRATION_SERVICE_PERCO_PORT_SERVER, INTEGRATION_SERVICE_PERCO_USERNAME, ""}};
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

    @BeforeMethod
    public void setUp(Method method){
        softAssert = new SoftAssert();
        field = null;
        TestsBase.getInstance().init();
        if(method.toString().contains("test_Settings_OM_Empty_Value") ||
        method.toString().contains("test_Settings_Oroin_Empty_Value") ||
        method.toString().contains("test_Settings_Perco_Empty_Value")){
            TestsBase.getInstance().openMS("Настройки", "Интеграция");
            if(isExistsTableText("СКУД", false)){}
            else{
                skudPage = (SKUDPage) clickServiceType("СКУД");
                assertTrue(skudPage.deleteSKUD("СКУД"),
                        "После удаления, сервис СКУД найден в таблице Подключенные сервисы");
            }
        }
    }

    @Story(value = "Проверяем настройки Подключение на пустые поля")
    @Description(value = "Вводим в поля формы подключения пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_connect")
    void test_Settings_Connect_Empty_Value(String server, String http, String https, String websocket){
        TestsBase.getInstance().openMS("Настройки", "Сервер");
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

    @Story(value = "Проверяем настройки СКУД Офис-Монитор на пустые поля")
    @Description(value = "Вводим в настройках СКУД Офис-Монитор пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_om")
    void test_Settings_OM_Empty_Value(String ip, String port, String username){
        Map<String, String> mapInputValueOM = new HashMap() {{
            put("IP адрес", ip);
            put("Порт БД", port);
            put("Имя пользователя БД", username);
        }};
        skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_OM_TYPE);
        clickButtonActionService(SETTINGS_BUTTON_SETTING);
        sendInputsForm(mapInputValueOM);
        if(ip.equals("")) field = "IP адрес";
        else if(port.equals("")) field = "Порт БД";
        else field = "Имя пользователя БД";
        softAssert.assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        isExistsTableText(INTEGRATION_SERVICE_OM_TYPE, false);
        softAssert.assertAll();
    }

    @Story(value = "Проверяем настройки Push сервера на пустые поля")
    @Description(value = "Вводим в поля формы Лицензирование и обслуживание пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_push")
    void test_Settings_Push_Empty_Value(String server, String login, String port, String password){
        TestsBase.getInstance().openMS("Настройки", "Сервер");
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

    @Story(value = "Проверяем настройки Сети на пустые поля")
    @Description(value = "Вводим в поля формы Сеть пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_network")
    void test_Settings_Network_Empty_Value(String publicIP, String frontIP, String insideIP){
        TestsBase.getInstance().openMS("Настройки", "Телефония");
        Map<String, String> mapInputValueNetwork = new HashMap() {{
            put(TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS, publicIP);
            put(TELEPHONY_NETWORK_INPUT_FRONT_DEV, frontIP);
            put(TELEPHONY_NETWORK_INPUT_INSIDE_DEV, insideIP);
        }};
        setSettingsServer(mapInputValueNetwork, TELEPHONY_NETWORK_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        if(publicIP.equals("")) field = TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS;
        else if(frontIP.equals("")) field = TELEPHONY_NETWORK_INPUT_FRONT_DEV;
        else field = TELEPHONY_NETWORK_INPUT_INSIDE_DEV;
        softAssert.assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        softAssert.assertTrue(isShowValueInField(
                TELEPHONY_NETWORK_TITLE_FORM,
                field + ":",
                "",
                 false),
                 "В поле " + field + ", сохранилось пустое значение");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем настройки СКУД ОРИОН на пустые поля")
    @Description(value = "Вводим в настройках СКУД ОРИОН пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_orion")
    void test_Settings_Oroin_Empty_Value(String ip, String port, String outport){
        Map<String, String> mapInputValueOrion = new HashMap() {{
            put("IP адрес", ip);
            put("Порт", port);
            put("Исходящий порт", outport);
        }};
        skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_ORION_TYPE);
        clickButtonActionService(SETTINGS_BUTTON_SETTING);
        sendInputsForm(mapInputValueOrion);
        if(ip.equals("")) field = "IP адрес";
        else if(port.equals("")) field = "Порт";
        else field = "Исходящий порт";
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        //if( ! isShowTextWrongValue(field).equals("Введите значение")) System.out.println("Yes");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        isExistsTableText(INTEGRATION_SERVICE_ORION_TYPE, false);
        softAssert.assertAll();
    }

    @Story(value = "Проверяем настройки SIP-сервер на пустые поля")
    @Description(value = "Вводим в поля формы SIP-сервер пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_sip")
    void test_Settings_SIP_Empty_Value(String minPort, String maxPort){
        TestsBase.getInstance().openMS("Настройки", "Телефония");
        Map<String, String> mapInputValueSip = new HashMap() {{
            put(TELEPHONY_SIP_INPUT_MIN_PORT, minPort);
            put(TELEPHONY_SIP_INPUT_MAX_PORT, maxPort);
        }};
        setSettingsServer(mapInputValueSip, TELEPHONY_SIP_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        if(minPort.equals("")) field = TELEPHONY_SIP_INPUT_MIN_PORT;
        else field = TELEPHONY_SIP_INPUT_MAX_PORT;
        softAssert.assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        softAssert.assertTrue(isShowValuesInField(
                TELEPHONY_SIP_TITLE_FORM,
                TELEPHONY_INPUT_SPEECH_PORTS,
                "",
                false),
                "В поле " + TELEPHONY_INPUT_SPEECH_PORTS + ", сохранилось пустое значение");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем настройки Turn/Stun сервера на пустые поля")
    @Description(value = "Вводим в поля формы Turn/Stun пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_turn")
    void test_Settings_Turn_Empty_Value(String minPort, String maxPort, String realm, String secret){
        TestsBase.getInstance().openMS("Настройки", "Телефония");
        Map<String, String> mapInputValueTurn = new HashMap() {{
            put(TELEPHONY_TURN_INPUT_MIN_PORT, minPort);
            put(TELEPHONY_TURN_INPUT_MAX_PORT, maxPort);
            put(TELEPHONY_TURN_INPUT_REALM, realm);
            put(TELEPHONY_TURN_INPUT_SECRET, secret);
        }};
        setSettingsServer(mapInputValueTurn, TELEPHONY_TURN_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        if(minPort.equals("")) field = TELEPHONY_TURN_INPUT_MIN_PORT;
        else if(maxPort.equals("")) field = TELEPHONY_TURN_INPUT_MAX_PORT;
        else if(realm.equals("")) field = TELEPHONY_TURN_INPUT_REALM;
        else field = TELEPHONY_TURN_INPUT_SECRET;
        softAssert.assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        if(field.equals(TELEPHONY_TURN_INPUT_MIN_PORT) || field.equals(TELEPHONY_TURN_INPUT_MAX_PORT)) {
            softAssert.assertTrue(isShowValuesInField(
                    TELEPHONY_TURN_TITLE_FORM,
                    TELEPHONY_INPUT_SPEECH_PORTS,
                    "",
                    false),
                    "В поле " + field + ", сохранилось пустое значение");
        }else{
            softAssert.assertTrue(isShowValueInField(
                    TELEPHONY_TURN_TITLE_FORM,
                    field,
                    "",
                    false),
                    "В поле " + field + ", сохранилось пустое значение");
        }
        softAssert.assertAll();
    }

    @Story(value = "Проверяем настройки Почты на пустые поля")
    @Description(value = "Вводим в поля формы Почта пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_mail")
    void test_Settings_Mail_Empty_Value(String server, String username, String password, String port, String fromUser, String fromMail){
        TestsBase.getInstance().openMS("Настройки", "Почта");
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
        if(! field.equals(MAIL_CONNECT_INPUT_PASSWORD)) {
            softAssert.assertTrue(isShowValueInField(
                    SERVER_CONNECT_TITLE_FORM,
                    field,
                    "",
                    false),
                    "В поле " + field + ", сохранилось пустое значение");
        }
        softAssert.assertAll();
    }

    @Story(value = "Проверяем настройки СКУД PERCo на пустые поля")
    @Description(value = "Вводим в настройках СКУД PERCo пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_perco")
    void test_Settings_Perco_Empty_Value(String module, String portModule, String server, String portServer,
                                         String username, String key){
        Map<String, String> mapInputValuePerco = new HashMap() {{
            put("Адрес модуля интеграции с PERCo", module);
            put("Порт модуля интеграции с PERCo", portModule);
            put("Адрес PERCo сервера", server);
            put("Порт PERCo сервера", portServer);
            put("Имя пользователя", username);
            put("Ключ шифрования", key);
        }};
        skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_PERCO_TYPE);
        clickButtonActionService(SETTINGS_BUTTON_SETTING);
        sendInputsForm(mapInputValuePerco);
        if(module.equals("")) field = "Адрес модуля интеграции с PERCo";
        else if(portModule.equals("")) field = "Порт модуля интеграции с PERCo";
        else if(server.equals("")) field = "Адрес PERCo сервера";
        else if(portServer.equals("")) field = "Порт PERCo сервера";
        else if(username.equals("")) field = "Имя пользователя";
        else field = "Ключ шифрования";
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        isExistsTableText(INTEGRATION_SERVICE_PERCO_TYPE, false);
        softAssert.assertAll();
    }

    /*@Story(value = "Проверяем невалидные значения в настройках Подключение")
    @Description(value = "Вводим в поля формы Подключение невалидные значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test(dataProvider = "wrong_value")
    void test_No_Valid_Ports_Connect(String field, Character c){
        softAssert = new SoftAssert();
        String port = null;
        if(field.equals(SERVER_CONNECT_INPUT_HTTP_PORT)) port = c.toString() + ",";
        else if(field.equals(SERVER_CONNECT_INPUT_HTTPS_PORT)) port = ", " + c.toString() + ",";
        else if(field.equals(SERVER_CONNECT_INPUT_WEBSOCKET_PORT)) port = ", " + c.toString();
        clickButtonSettings(SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        sendInputForm(field, c.toString());
        softAssert.assertEquals(isShowTextWrongValue(field),"Невалидный порт",
                "Надпись 'Невалидный порт' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Формы редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        softAssert.assertTrue(isShowSymbolsInField(
                SERVER_CONNECT_TITLE_FORM,
                SERVER_CONNECT_FIELD_PORTS,
                port,
                false),
                "Значение " + port + " отображается в поле " + SERVER_CONNECT_FIELD_PORTS);
        softAssert.assertAll();
    }

    @Story(value = "Проверяем номер максимального порта в настройках Подключение")
    @Description(value = "Вводим в поля настройки портов формы Подключение порт 65536 и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test(dataProvider = "max_length_port")
    void test_Max_Length_Ports_Connect(String field, String maxPort){
        softAssert = new SoftAssert();
        String port = null;
        if(field.equals(SERVER_CONNECT_INPUT_HTTP_PORT)) port = maxPort + ",";
        else if(field.equals(SERVER_CONNECT_INPUT_HTTPS_PORT)) port = ", " + maxPort + ",";
        else if(field.equals(SERVER_CONNECT_INPUT_WEBSOCKET_PORT)) port = ", " + maxPort;
        clickButtonSettings(SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        sendInputForm(field, maxPort);
        softAssert.assertEquals(isShowTextWrongValue(field),"Невалидный порт",
                "Надпись 'Невалидный порт' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Формы редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        softAssert.assertTrue(isShowSymbolsInField(
                SERVER_CONNECT_TITLE_FORM,
                SERVER_CONNECT_FIELD_PORTS,
                port,
                false),
                "Значение " + port + " отображается в поле " + SERVER_CONNECT_FIELD_PORTS);
        softAssert.assertAll();
    }

    @Story(value = "Проверяем валидные значения в настройках Подключение")
    @Description(value = "Вводим в поля формы Подключение валидные значения и проверяем: \n" +
            "Сохраняются ли настройки с валидным значением в поле")
    @Test(dataProvider = "valid_value")
    void test_Valid_Ports_Connect(String field, Character c){
        softAssert = new SoftAssert();
        String port = null;
        if(field.equals(SERVER_CONNECT_INPUT_HTTP_PORT)) port = c.toString() + ",";
        else if(field.equals(SERVER_CONNECT_INPUT_HTTPS_PORT)) port = ", " + c.toString() + ",";
        else if(field.equals(SERVER_CONNECT_INPUT_WEBSOCKET_PORT)) port = ", " + c.toString();
        clickButtonSettings(SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        sendInputForm(field, c.toString());
        clickButtonSave();
        softAssert.assertTrue(isFormConfirmActions(true), "Не появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        softAssert.assertTrue(isShowSymbolsInField(
                SERVER_CONNECT_TITLE_FORM,
                SERVER_CONNECT_FIELD_PORTS,
                port,
                true),
                "Значение " + port + " не отображается в поле " + SERVER_CONNECT_FIELD_PORTS);
        softAssert.assertAll();
    }*/

    @AfterMethod(alwaysRun = true)
    public void afterTestMethod(Method method, ITestResult testResult){
        if(!testResult.isSuccess()){
            AScreenshot(method.toString());
            ABrowserLogNetwork();
            ABrowserLogConsole();
        }
    }
}
