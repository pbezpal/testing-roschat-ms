package chat.ros.testing2.server;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.TestsBase;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.ServerPage;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.TestHelper.isWebServerStatus;
import static chat.ros.testing2.data.ContactsData.CLIENT_USER_K;
import static chat.ros.testing2.data.LoginData.HOST_SERVER;
import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Сервер")
public class TestServerPage extends ServerPage {

    private TestsBase testsBase = new TestsBase();
    private final String account = CLIENT_USER_K + "@ros.chat";
    private static Object[][] getValueConnect(){
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

    private Map<String, String> mapInputValuePush = new HashMap() {{
        put(SERVER_PUSH_INPUT_HOST, SERVER_PUSH_HOST_SERVER);
        put(SERVER_PUSH_INPUT_LOGIN, SERVER_PUSH_LOGIN_SERVER);
        put(SERVER_PUSH_INPUT_PORT, SERVER_PUSH_PORT_SERVER);
        put(SERVER_PUSH_INPUT_PASSWORD, SERVER_PUSH_PASSWORD_SERVER);
    }};

    @Story(value = "Проверяем подключение с различными настройками портов")
    @Description(value = "Проверяем подключение Web-клиента с различными настройками портов http, https" +
            " и WebSocket")
    @ParameterizedTest
    @MethodSource("getValueConnect")
    void test_Settings_Connect(String server, String http, String https, String websocket){
        String client;
        Map<String, String> mapInputValueConnect = new HashMap() {{
            put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, server);
            put(SERVER_CONNECT_INPUT_HTTP_PORT, http);
            put(SERVER_CONNECT_INPUT_HTTPS_PORT, https);
            put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, websocket);
        }};
        setSectionConnect(mapInputValueConnect);
        assertAll("Проверяем настройки портов подключения",
                () -> assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась"),
                () -> assertEquals(isCheckSuccessAction(),"Настройки сервера корректны.",
                "Настройки сервера некорректны")
        );
        clickButtonCloseCheckSettingsForm();
        assertAll( "Проверяем, сохранились ли настройки",
                () -> assertTrue(isShowValueInField(
                        SERVER_CONNECT_TITLE_FORM,
                        SERVER_CONNECT_INPUT_PUBLIC_NETWORK,
                        server,
                        true),
                        "Значение " + server + " нет в поле " + SERVER_CONNECT_INPUT_PUBLIC_NETWORK),
                () -> assertTrue(isShowValueInField(
                        SERVER_CONNECT_TITLE_FORM,
                        SERVER_CONNECT_FIELD_PORTS,
                        http + ", " + https + ", " + websocket,
                        true),
                        "Значение " + http + ", " + https + ", " + websocket + " нет в поле " + SERVER_CONNECT_FIELD_PORTS)
        );
        sleep(5000);
        assertTrue(isWebServerStatus(), "Web сервер не запустился в течение минуты");
        if(https.equals(SERVER_CONNECT_HTTPS_OTHER_PORT)) client = "http://" + server + ":" + http;
        else client = "https://" + server;
        testsBase.openClient(client, account, false);
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
        setPushService(mapInputValuePush);
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