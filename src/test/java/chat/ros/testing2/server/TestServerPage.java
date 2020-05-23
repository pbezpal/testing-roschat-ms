package chat.ros.testing2.server;

import chat.ros.testing2.TestSuiteBase;
import chat.ros.testing2.server.settings.ServerPage;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.LoginData.HOST_SERVER;
import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.Assert.assertTrue;

@Epic(value = "Настройки")
@Feature(value = "Сервер")
public class TestServerPage extends ServerPage implements TestSuiteBase {

    private SoftAssert softAssert;

    @BeforeMethod
    public void beforeTest(Method method){
        if(method.toString().contains("Open_Page")) testBase.openMS("/settings/web-server");
        else testBase.openMS("Настройки","Сервер");
    }

    @Story(value = "Настраиваем нестандартные порты в разделе подключение")
    @Description(value = "Настраиваем в разделе Подключение внешний адрес сервера и нестандартные порты http, https" +
            " и WebSocket")
    @Test(groups = {"Connect_other_port"})
    void test_Other_Settings_Connect(){
        softAssert = new SoftAssert();
        testBase.openMS("Настройки", "Сервер");
        Map<String, String> mapInputOtherValueConnect = new HashMap() {{
            put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, HOST_SERVER);
            put(SERVER_CONNECT_INPUT_HTTP_PORT, SERVER_CONNECT_HTTP_OTHER_PORT);
            put(SERVER_CONNECT_INPUT_HTTPS_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT);
            put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT);
        }};
        setSectionConnect(mapInputOtherValueConnect);
        softAssert.assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        softAssert.assertEquals(isCheckSuccessAction(),"Настройки сервера корректны.",
                "Настройки сервера некорректны");
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

    @Story(value = "Настраиваем стандартные порты в разделе подключение")
    @Description(value = "Настраиваем в разделе Подключение внешний адрес сервера и нестандартные порты http, https" +
            " и WebSocket")
    @Test(groups = {"Connect_standard_ports"})
    void test_Settings_Connect_Standard_Ports(){
        softAssert = new SoftAssert();
        testBase.openMS("Настройки", "Сервер");
        Map<String, String> mapInputValueConnect = new HashMap() {{
            put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, HOST_SERVER);
            put(SERVER_CONNECT_INPUT_HTTP_PORT, SERVER_CONNECT_HTTP_PORT);
            put(SERVER_CONNECT_INPUT_HTTPS_PORT, SERVER_CONNECT_HTTPS_PORT);
            put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, SERVER_CONNECT_WEBSOCKET_PORT);
        }};
        setSectionConnect(mapInputValueConnect);
        softAssert.assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        softAssert.assertEquals(isCheckSuccessAction(),"Настройки сервера корректны.",
                "Настройки сервера некорректны");
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

    /*@Story(value = "Настраиваем сертификат SSL")
    @Description(value = "Загружаем актуальные файлы для сертификата SSL")
    @Disabled
    @Test
    void test_Settings_Certificate(){ setCertificate(); }*/

    @Story(value = "Настраиваем Push сервер")
    @Description(value = "Настраиваем Push сервер в разделе Лицензирование и обсуживание")
    @Test
    void test_Settings_Push_Server(){
        testBase.openMS("Настройки", "Сервер");
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