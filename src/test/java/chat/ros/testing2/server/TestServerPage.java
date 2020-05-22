package chat.ros.testing2.server;

import chat.ros.testing2.TestSuiteBase;
import chat.ros.testing2.server.settings.ServerPage;
import client.ClientPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7012;
import static chat.ros.testing2.data.ContactsData.USER_ACCOUNT_PASSWORD;
import static chat.ros.testing2.data.LoginData.HOST_SERVER;
import static chat.ros.testing2.data.SettingsData.*;
import static org.testng.Assert.assertTrue;

@Epic(value = "Настройки")
@Feature(value = "Сервер")
public class TestServerPage extends ServerPage implements TestSuiteBase {

    private SoftAssert softAssert;

    @Story(value = "Настраиваем нестандартные порты в разделе подключение")
    @Description(value = "Настраиваем в разделе Подключение внешний адрес сервера и нестандартные порты http, https" +
            " и WebSocket")
    @Test(priority = 1)
    void test_Other_Settings_Connect(){
        softAssert = new SoftAssert();
        Map<String, String> mapInputOtherValueConnect = new HashMap() {{
            put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, HOST_SERVER);
            put(SERVER_CONNECT_INPUT_HTTP_PORT, SERVER_CONNECT_HTTP_OTHER_PORT);
            put(SERVER_CONNECT_INPUT_HTTPS_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT);
            put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT);
        }};
        setSectionConnect(mapInputOtherValueConnect);
        softAssert.assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        softAssert.assertTrue(isCheckSettings(), "Настройки сервера некорректны");
        clickButtonCloseCheckSettingsForm();
        softAssert.assertTrue(isShowValueInField(
                SERVER_CONNECT_TITLE_FORM,
                SERVER_CONNECT_INPUT_PUBLIC_NETWORK,
                HOST_SERVER,
                true),
                "Значение " + HOST_SERVER + " нет в поле " + SERVER_CONNECT_INPUT_PUBLIC_NETWORK);
        String ports = SERVER_CONNECT_HTTP_OTHER_PORT + ", " + SERVER_CONNECT_HTTPS_OTHER_PORT + ", "
                + SERVER_CONNECT_WEBSOCKET_OTHER_PORT;
        softAssert.assertTrue(isShowValueInField(
                SERVER_CONNECT_TITLE_FORM,
                SERVER_CONNECT_FIELD_PORTS,
                ports,
                true),
                "Значение " + ports + " нет в поле " + SERVER_CONNECT_FIELD_PORTS);
        softAssert.assertAll();
    }

    @Story(value = "Проверяем, подключается ли клиент с нестандарными портами")
    @Description(value = "В адресной строке браузера вводим адрес web клиента с нестандартным портом 88")
    @Test(priority = 2, dependsOnMethods = {"test_Other_Settings_Connect"})
    void test_Client_Connect_With_Other_Port(){
        assertTrue(ClientPage.loginClient(CONTACT_NUMBER_7012 + "@ros.chat", USER_ACCOUNT_PASSWORD, false),
                "Не удалось авторизоваться на порту " + SERVER_CONNECT_HTTP_OTHER_PORT);
    }

    @Story(value = "Настраиваем стандартные порты в разделе подключение")
    @Description(value = "Настраиваем в разделе Подключение внешний адрес сервера и нестандартные порты http, https" +
            " и WebSocket")
    @Test(priority = 3)
    void test_Settings_Connect_Standard_Ports(){
        softAssert = new SoftAssert();
        Map<String, String> mapInputValueConnect = new HashMap() {{
            put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, HOST_SERVER);
            put(SERVER_CONNECT_INPUT_HTTP_PORT, SERVER_CONNECT_HTTP_PORT);
            put(SERVER_CONNECT_INPUT_HTTPS_PORT, SERVER_CONNECT_HTTPS_PORT);
            put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, SERVER_CONNECT_WEBSOCKET_PORT);
        }};
        setSectionConnect(mapInputValueConnect);
        softAssert.assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        softAssert.assertTrue(isCheckSettings(), "Настройки сервера некорректны");
        clickButtonCloseCheckSettingsForm();
        softAssert.assertTrue(isShowValueInField(
                SERVER_CONNECT_TITLE_FORM,
                SERVER_CONNECT_INPUT_PUBLIC_NETWORK,
                HOST_SERVER,
                true),
                "Значение " + HOST_SERVER + " нет в поле " + SERVER_CONNECT_INPUT_PUBLIC_NETWORK);
        String ports =  SERVER_CONNECT_HTTP_PORT + ", " + SERVER_CONNECT_HTTPS_PORT + ", "
                + SERVER_CONNECT_WEBSOCKET_PORT;
        softAssert.assertTrue(isShowValueInField(
                SERVER_CONNECT_TITLE_FORM,
                SERVER_CONNECT_FIELD_PORTS,
                ports,
                true),
                "Значение " + ports + " нет в поле " + SERVER_CONNECT_FIELD_PORTS);
        softAssert.assertAll();
    }

    @Story(value = "Проверяем, подключается ли клиент со стандарными портами")
    @Description(value = "В адресной строке браузера вводим адрес web клиента со стандартным портом 80")
    @Test(priority = 4, dependsOnMethods = {"test_Settings_Connect_Standard_Ports"})
    void test_Client_Connect_With_Standard_Port(){
        assertTrue(ClientPage.loginClient(CONTACT_NUMBER_7012 + "@ros.chat", USER_ACCOUNT_PASSWORD, false),
                "Не удалось авторизоваться на порту " + SERVER_CONNECT_HTTP_OTHER_PORT);
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
}