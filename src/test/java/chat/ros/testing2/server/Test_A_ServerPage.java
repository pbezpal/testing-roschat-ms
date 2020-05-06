package chat.ros.testing2.server;

import chat.ros.testing2.RecourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.ServerPage;
import client.ClientPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7012;
import static chat.ros.testing2.data.ContactsData.USER_ACCOUNT_PASSWORD;
import static chat.ros.testing2.data.LoginData.HOST_SERVER;
import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic(value = "Настройки")
@Feature(value = "Сервер")
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(RecourcesTests.class)
@ExtendWith(WatcherTests.class)
class Test_A_ServerPage extends ServerPage {

    @Story(value = "Настраиваем нестандартные порты в разделе подключение")
    @Description(value = "Настраиваем в разделе Подключение внешний адрес сервера и нестандартные порты http, https" +
            " и WebSocket")
    @Test
    void test_A_Other_Settings_Connect(){
        Map<String, String> mapInputOtherValueConnect = new HashMap() {{
            put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, HOST_SERVER);
            put(SERVER_CONNECT_INPUT_HTTP_PORT, SERVER_CONNECT_HTTP_OTHER_PORT);
            put(SERVER_CONNECT_INPUT_HTTPS_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT);
            put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT);
        }};
        setSectionConnect(mapInputOtherValueConnect);
    }

    @Story(value = "Проверяем, подключается ли клиент с нестандарными портами")
    @Description(value = "В адресной строке браузера вводим адрес web клиента с нестандартным портом 88")
    @Test
    void test_B_Client_Connect_With_Other_Port(){
        assertTrue(ClientPage.loginClient(CONTACT_NUMBER_7012 + "@ros.chat", USER_ACCOUNT_PASSWORD, false),
                "Не удалось авторизоваться на порту " + SERVER_CONNECT_HTTP_OTHER_PORT);
    }

    @Story(value = "Настраиваем нестандартные порты в разделе подключение")
    @Description(value = "Настраиваем в разделе Подключение внешний адрес сервера и нестандартные порты http, https" +
            " и WebSocket")
    @Test
    void test_C_Settings_Connect_Standard_Ports(){
        Map<String, String> mapInputValueConnect = new HashMap() {{
            put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, HOST_SERVER);
            put(SERVER_CONNECT_INPUT_HTTP_PORT, SERVER_CONNECT_HTTP_PORT);
            put(SERVER_CONNECT_INPUT_HTTPS_PORT, SERVER_CONNECT_HTTPS_PORT);
            put(SERVER_CONNECT_INPUT_WEBSOCKET_PORT, SERVER_CONNECT_WEBSOCKET_PORT);
        }};
        setSectionConnect(mapInputValueConnect);
    }

    @Story(value = "Проверяем, подключается ли клиент с нестандарными портами")
    @Description(value = "В адресной строке браузера вводим адрес web клиента с нестандартным портом 88")
    @Test
    void test_D_Client_Connect_With_Standard_Port(){
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
    void test_E_Settings_Push_Server(){
        setPushService();
    }
}