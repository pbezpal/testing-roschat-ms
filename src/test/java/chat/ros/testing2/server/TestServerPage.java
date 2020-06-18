package chat.ros.testing2.server;

import chat.ros.testing2.TestSuiteBase;
import chat.ros.testing2.TestsBase;
import chat.ros.testing2.server.settings.ServerPage;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.*;

import static chat.ros.testing2.TestHelper.isWebServerStatus;
import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7012;
import static chat.ros.testing2.data.LoginData.HOST_SERVER;
import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.Assert.assertTrue;

@Epic(value = "Настройки")
@Feature(value = "Сервер")
public class TestServerPage extends ServerPage implements TestSuiteBase {

    private SoftAssert softAssert;
    private static final String wrongSymbols = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~‘’“”—ё№»АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String validSymbols = "1234567890";

    @BeforeMethod
    public void beforeTest(Method method){
        if (method.toString().contains("Open_Page")) TestsBase.getInstance().openMS("/settings/web-server");
        else TestsBase.getInstance().openMS("Настройки", "Сервер");
        TestsBase.getInstance().addContactAndAccount(CONTACT_NUMBER_7012);
    }

    @DataProvider(name = "connect")
    public Object[][] getValueConnect(){
        return new Object[][] {
                {HOST_SERVER, SERVER_CONNECT_HTTP_OTHER_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {HOST_SERVER, SERVER_CONNECT_HTTP_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT, SERVER_CONNECT_WEBSOCKET_PORT},
                {HOST_SERVER, SERVER_CONNECT_HTTP_OTHER_PORT, SERVER_CONNECT_HTTPS_PORT, SERVER_CONNECT_WEBSOCKET_PORT},
                {HOST_SERVER, SERVER_CONNECT_HTTP_OTHER_PORT, SERVER_CONNECT_HTTPS_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {HOST_SERVER, SERVER_CONNECT_HTTP_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {HOST_SERVER, SERVER_CONNECT_HTTP_PORT, SERVER_CONNECT_HTTPS_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {HOST_SERVER, SERVER_CONNECT_HTTP_OTHER_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT, SERVER_CONNECT_WEBSOCKET_PORT},
                {HOST_SERVER, SERVER_CONNECT_HTTP_PORT, SERVER_CONNECT_HTTPS_PORT, SERVER_CONNECT_WEBSOCKET_PORT}};
    }

    @DataProvider(name = "empty_value")
    public Object[][] getEmptyValueConnect(){
        return new Object[][] {
                {"",SERVER_CONNECT_HTTP_OTHER_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {HOST_SERVER,"", SERVER_CONNECT_HTTPS_OTHER_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {HOST_SERVER,SERVER_CONNECT_HTTP_OTHER_PORT, "", SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {HOST_SERVER,SERVER_CONNECT_HTTP_OTHER_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT, ""}};
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

    @Story(value = "Проверяем функцию подключение на пустые поля")
    @Description(value = "Вводим в поля формы подключения пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @Test(priority = 1, dataProvider = "empty_value")
    void test_Settings_Connect_Empty_Value(String server, String http, String https, String websocket){
        softAssert = new SoftAssert();
        String field;
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
                "Формы редактирования настроек закрылась после нажатия кнопки Сохранить");
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

    @Story(value = "Проверяем невалидные значения в настройках Подключение")
    @Description(value = "Вводим в поля формы Подключение невалидные значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test(priority = 2, dataProvider = "wrong_value")
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
    @Test(priority = 3, dataProvider = "max_length_port")
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
    @Test(priority = 4, dataProvider = "valid_value")
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

    @Story(value = "Проверяем подключение с различными настройками портов")
    @Description(value = "Проверяем подключение Web-клиента с различными настройками портов http, https" +
            " и WebSocket")
    @Test(priority = 5,dataProvider = "connect")
    void test_Settings_Connect(String server, String http, String https, String websocket){
        softAssert = new SoftAssert();
        String client;
        Map<String, String> mapInputValueConnect = new HashMap() {{
            put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, server);
            put(SERVER_CONNECT_INPUT_HTTP_PORT, http);
            put(SERVER_CONNECT_INPUT_HTTPS_PORT, https);
            put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, websocket);
        }};
        setSectionConnect(mapInputValueConnect);
        softAssert.assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        softAssert.assertEquals(isCheckSuccessAction(),"Настройки сервера корректны.",
                "Настройки сервера некорректны");
        clickButtonCloseCheckSettingsForm();
        softAssert.assertTrue(isShowValueInField(
                SERVER_CONNECT_TITLE_FORM,
                SERVER_CONNECT_INPUT_PUBLIC_NETWORK,
                server,
                true),
                "Значение " + server + " нет в поле " + SERVER_CONNECT_INPUT_PUBLIC_NETWORK);
        String ports = http + ", " + https + ", " + websocket;
        softAssert.assertTrue(isShowValueInField(
                SERVER_CONNECT_TITLE_FORM,
                SERVER_CONNECT_FIELD_PORTS,
                ports,
                true),
                "Значение " + ports + " нет в поле " + SERVER_CONNECT_FIELD_PORTS);
        softAssert.assertAll();
        sleep(5000);
        assertTrue(isWebServerStatus(), "Web сервер не запустился в течение минуты");
        if(https.equals(SERVER_CONNECT_HTTPS_OTHER_PORT)) client = "http://" + server + ":" + http;
        else client = "https://" + server;
        TestsBase.getInstance().openClient(client,CONTACT_NUMBER_7012 + "@ros.chat", false);
    }

    /*@Story(value = "Настраиваем сертификат SSL")
    @Description(value = "Загружаем актуальные файлы для сертификата SSL")
    @Disabled
    @Test
    void test_Settings_Certificate(){ setCertificate(); }*/

    @Story(value = "Настраиваем Push сервер")
    @Description(value = "Настраиваем Push сервер в разделе Лицензирование и обсуживание")
    @Test
    void test_Settings_Push_Server(){
        setPushService();
    }

    @Story(value = "Перезагрузка страницы")
    @Description(value = "Переходим на страницу Сервер, перезагружаем страницу и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Refresh_Page(){
        Selenide.refresh();
        sleep(3000);
        assertTrue(isNotShowLoaderSettings(), "Настройки не загрузились, надпись" +
                " 'Идет загрузка настроек...' не пропала");
    }

    @Story(value = "Переходим на страницу через адресную строку")
    @Description(value = "После авторизации вставляем в адресную строку страницу Сервер и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Open_Page(){
        sleep(3000);
        assertTrue(isNotShowLoaderSettings(), "Настройки не загрузились, надпись" +
                " 'Идет загрузка настроек...' не пропала");
    }
}