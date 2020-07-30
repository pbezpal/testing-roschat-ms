package chat.ros.testing2.parameters;

import chat.ros.testing2.server.settings.ServerPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

@Epic(value = "Настройки")
@Feature(value = "Сервер")
public class TestParametersServerConnectPage extends ServerPage {

    /*private SoftAssert softAssert;
    private String field;
    private TestsBase testsBase;

    @DataProvider(name = "empty_value_connect")
    public Object[][] getEmptyValueConnect(){
        return new Object[][] {
                {"",SERVER_CONNECT_HTTP_OTHER_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {HOST_SERVER,"", SERVER_CONNECT_HTTPS_OTHER_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {HOST_SERVER,SERVER_CONNECT_HTTP_OTHER_PORT, "", SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {HOST_SERVER,SERVER_CONNECT_HTTP_OTHER_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT, ""}};
    }

    @DataProvider(name = "wrong_value_host_connect_server")
    public Iterator<Object[]> getWrongValueHostConnectServer(){
        List<Object[]> list = new ArrayList<>();
        for(String host: WRONG_VALUE_HOST) {
            list.add(new Object[]{host});
        }
        return list.iterator();
    }

    @DataProvider(name = "wrong_value_connect_port")
    public Iterator<Object[]> getWrongValueConnectPort(){
        List<Object[]> list = new ArrayList<>();
        for(char c: WRONG_SYMBOLS_PORT.toCharArray()) {
            list.add(new Object[]{SERVER_CONNECT_INPUT_HTTP_PORT,c});
            list.add(new Object[]{SERVER_CONNECT_INPUT_HTTPS_PORT,c});
            list.add(new Object[]{SERVER_CONNECT_INPUT_WEBSOCKET_PORT,c});
        }
        return list.iterator();
    }

    @DataProvider(name = "max_length_port")
    public Object[][] getLengthValuePortConnect(){
        return new Object[][] {
                {SERVER_CONNECT_INPUT_HTTP_PORT,MORE_MAX_VALUE_PORT},
                {SERVER_CONNECT_INPUT_HTTPS_PORT,MORE_MAX_VALUE_PORT},
                {SERVER_CONNECT_INPUT_WEBSOCKET_PORT,MORE_MAX_VALUE_PORT}};
    }

    @DataProvider(name = "valid_value_connect_ports")
    public Iterator<Object[]> getValidValueConnectPorts(){
        List<Object[]> list = new ArrayList<>();
        for(char c: VALID_SYMBOLS_PORT.toCharArray()) {
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

    @Story(value = "Проверяем невалидные значение хоста в поле 'Внешний адрес сервера' в настройках Подключение")
    @Description(value = "Вводим невалидные символы в поле 'Внешний адрес сервера' в настройках Подключение и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный адрес' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "wrong_value_host_connect_server")
    void test_Wrong_Host_Public_Address_Connect(String address){
        Map<String, String> mapInputValueConnect = new HashMap() {{
            put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, address);
        }};
        setSettingsServer(mapInputValueConnect, SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        softAssert.assertEquals(isShowTextWrongValue(SERVER_CONNECT_INPUT_PUBLIC_NETWORK),"Невалидный адрес",
                "Надпись 'Невалидный адрес' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        softAssert.assertTrue(isShowValueInField(
                SERVER_CONNECT_TITLE_FORM,
                SERVER_CONNECT_INPUT_PUBLIC_NETWORK,
                address,
                false),
                "Значение " + address + " отображается в поле " + SERVER_CONNECT_INPUT_PUBLIC_NETWORK + ":");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем максимальный порт в настройках Подключение")
    @Description(value = "Вводим в поля настройки портов формы Подключение порт 65536 и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test(dataProvider = "max_length_port")
    void test_Max_Length_Ports_Connect(String field, String maxPort){
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

    @Story(value = "Проверяем невалидные значения портов в настройках Подключение")
    @Description(value = "Вводим невалидыне значения портов в настройках Подключение и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test(dataProvider = "wrong_value_connect_port")
    void test_Wrong_Symbols_Ports_Connect(String field, Character c){
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

    @Story(value = "Проверяем валидные значения портов в настройках Подключение")
    @Description(value = "Вводим валидные значения портов в настройках Подключение и проверяем: \n" +
            "Сохраняются ли настройки с валидным значением портов")
    @Test(dataProvider = "valid_value_connect_ports")
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
