package chat.ros.testing2.parameters;

import chat.ros.testing2.server.settings.ServerPage;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

@Epic(value = "Настройки")
@Feature(value = "Интеграция")
public class TestParametersIntegrationOMPage extends ServerPage implements IntegrationPage {

    /*private SKUDPage skudPage;
    private String field;
    private SoftAssert softAssert;
    private ServerPage serverPage = new ServerPage();
    public TestsBase testsBase;

    @DataProvider(name = "empty_value_om")
    public Object[][] getEmptyValueOM(){
        return new Object[][] {
                {"",INTEGRATION_SERVICE_OM_PORT_BD, INTEGRATION_SERVICE_OM_LOGIN_DB},
                {INTEGRATION_SERVICE_OM_IP_ADDRESS,"", INTEGRATION_SERVICE_OM_LOGIN_DB},
                {INTEGRATION_SERVICE_OM_IP_ADDRESS,INTEGRATION_SERVICE_OM_PORT_BD, ""}};
    }

    @DataProvider(name = "wrong_value_ip_om")
    public Iterator<Object[]> getWrongValueServerOM(){
        List<Object[]> list = new ArrayList<>();
        for(String ip: WRONG_VALUE_IP_ADDRESS) {
            list.add(new Object[]{ip});
        }
        return list.iterator();
    }

    @DataProvider(name = "wrong_symbols_port_om")
    public Iterator<Object[]> getWrongSymbolsPortOM(){
        List<Object[]> list = new ArrayList<>();
        for(Character c: WRONG_SYMBOLS_PORT.toCharArray()) {
            list.add(new Object[]{c});
        }
        return list.iterator();
    }

    @DataProvider(name = "valid_value_port_om")
    public Iterator<Object[]> getValidValuePortOM(){
        List<Object[]> list = new ArrayList<>();
        for(char c: VALID_SYMBOLS_PORT.toCharArray()) {
            list.add(new Object[]{c});
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
        softAssert = new SoftAssert();
        testsBase.init();
        testsBase.openMS("Настройки", "Интеграция");
        if (isExistsTableText("СКУД", false)) {
        } else {
            skudPage = (SKUDPage) clickServiceType("СКУД");
            assertTrue(skudPage.deleteSKUD("СКУД"),
                    "После удаления, сервис СКУД найден в таблице Подключенные сервисы");
        }
        skudPage = (SKUDPage) addIntegrationService(INTEGRATION_SERVICE_OM_TYPE);
        clickButtonActionService(SETTINGS_BUTTON_SETTING);
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
        sendInputsForm(mapInputValueOM);
        clickInputForm("Пароль БД");
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

    @Story(value = "Проверяем невалидные значение IP адреса в настройках СКУД Офис-Монитор")
    @Description(value = "Вводим в настройках СКУД Офис-Монитор пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный IP адрес' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидными значениями")
    @Test(dataProvider = "wrong_value_ip_om")
    void test_Wrong_IP_OM(String ip){
        sendInputForm("IP адрес", ip);
        softAssert.assertEquals(isShowTextWrongValue("IP адрес"),"Невалидный IP адрес",
                "Надпись 'Невалидный IP адрес' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        isExistsTableText(INTEGRATION_SERVICE_OM_TYPE, false);
        softAssert.assertAll();
    }

    @Story(value = "Проверяем невалидные значения портов в настройках СКУД Офис-Монитор")
    @Description(value = "Вводим невалидные значения портов в настройках СКУД Офис-Монитор и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test(dataProvider = "wrong_symbols_port_om")
    void test_Wrong_Symbols_Ports_OM(Character c){
        sendInputForm("Порт БД", c.toString());
        softAssert.assertEquals(isShowTextWrongValue("Порт БД"),"Невалидный порт",
                "Надпись 'Невалидный порт' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        isExistsTableText(INTEGRATION_SERVICE_OM_TYPE, false);
        softAssert.assertAll();
    }

    @Story(value = "Проверяем номер максимального порта в настройках СКУД Офис-Монитор")
    @Description(value = "Вводим в поле 'Порт БД' СКУД Офис-Монитор порт 65536 и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test
    void test_Max_Length_Ports_OM(){
        sendInputForm("Порт БД", MORE_MAX_VALUE_PORT);
        softAssert.assertEquals(isShowTextWrongValue("Порт БД"),"Невалидный порт",
                "Надпись 'Невалидный порт' не появилась");
        clickButtonSave();
        softAssert.assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить");
        softAssert.assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        isExistsTableText(INTEGRATION_SERVICE_OM_TYPE, false);
        softAssert.assertAll();
    }

    @Story(value = "Проверяем валидные значения портов в настройках СКУД Офис-Монитор")
    @Description(value = "Вводим валидные значения портов в настройках СКУД Офис-Монитор и проверяем: \n" +
            "Сохраняются ли настройки с валидным значением в поле")
    @Test(dataProvider = "valid_value_port_om")
    void test_Valid_Symbols_Ports_OM(Character c){
        sendInputForm("Порт БД", c.toString());
        clickButtonSave();
        softAssert.assertTrue(isFormConfirmActions(true), "Не появилась форма, Подтвердите свои действия");
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        isExistsTableText(INTEGRATION_SERVICE_OM_TYPE, true);
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
