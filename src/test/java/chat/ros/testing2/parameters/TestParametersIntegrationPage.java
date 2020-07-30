package chat.ros.testing2.parameters;

import chat.ros.testing2.server.settings.ServerPage;
import chat.ros.testing2.server.settings.integration.IntegrationPage;

/*@Epic(value = "Настройки")
@Feature(value = "Интеграция")*/
public class TestParametersIntegrationPage extends ServerPage implements IntegrationPage {

    /*private SKUDPage skudPage;
    private String field;
    private ServerPage serverPage = new ServerPage();
    public TestsBase testsBase;

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

    @DataProvider(name = "wrong_value_server_om")
    public Iterator<Object[]> getWrongValueServerOM(){
        List<Object[]> list = new ArrayList<>();
        for(char c: WRONG_SYMBOLS_SERVER.toCharArray()) {
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

    @DataProvider(name = "valid_value")
    public Iterator<Object[]> getValidValue(){
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
    public void beforeMethod(Method method){
        field = null;
        testsBase.init();
        testsBase.openMS("Настройки", "Интеграция");
        if (isExistsTableText("СКУД", false)) {
        } else {
            skudPage = (SKUDPage) clickServiceType("СКУД");
            assertTrue(skudPage.deleteSKUD("СКУД"),
                    "После удаления, сервис СКУД найден в таблице Подключенные сервисы");
        }
    }

    @Story(value = "Проверяем настройки СКУД Офис-Монитор на пустые поля")
    @Description(value = "Вводим в настройках СКУД Офис-Монитор пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_om")
    void test_Settings_OM_Empty_Value(String ip, String port, String username){
        SoftAssert omAssert = new SoftAssert();
        Map<String, String> mapInputValueOM = new HashMap() {{
            put("IP адрес", ip);
            put("Порт БД", port);
            put("Имя пользователя БД", username);
        }};
        skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_OM_TYPE);
        clickButtonActionService(SETTINGS_BUTTON_SETTING);
        sendInputsForm(mapInputValueOM);
        clickInputForm("Пароль БД");
        if(ip.equals("")) field = "IP адрес";
        else if(port.equals("")) field = "Порт БД";
        else field = "Имя пользователя БД";
        omAssert.assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        clickButtonSave();
        omAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        omAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        isExistsTableText(INTEGRATION_SERVICE_OM_TYPE, false);
        omAssert.assertAll();
    }

    @Story(value = "Проверяем настройки СКУД ОРИОН на пустые поля")
    @Description(value = "Вводим в настройках СКУД ОРИОН пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_orion")
    void test_Settings_Oroin_Empty_Value(String ip, String port, String outport){
        SoftAssert orionAssert = new SoftAssert();
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
        orionAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        orionAssert.assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        orionAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        isExistsTableText(INTEGRATION_SERVICE_ORION_TYPE, false);
        orionAssert.assertAll();
    }

    @Story(value = "Проверяем настройки СКУД PERCo на пустые поля")
    @Description(value = "Вводим в настройках СКУД PERCo пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(dataProvider = "empty_value_perco")
    void test_Settings_Perco_Empty_Value(String module, String portModule, String server, String portServer,
                                         String username, String key){
        SoftAssert percoAssert = new SoftAssert();
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
        percoAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        percoAssert.assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        percoAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        isExistsTableText(INTEGRATION_SERVICE_PERCO_TYPE, false);
        percoAssert.assertAll();
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
