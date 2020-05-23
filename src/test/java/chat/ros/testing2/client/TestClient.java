package chat.ros.testing2.client;

import chat.ros.testing2.TestSuiteBase;
import client.ClientPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7012;
import static chat.ros.testing2.data.ContactsData.USER_ACCOUNT_PASSWORD;
import static chat.ros.testing2.data.LoginData.HOST_SERVER;
import static chat.ros.testing2.data.SettingsData.SERVER_CONNECT_HTTP_OTHER_PORT;
import static chat.ros.testing2.data.SettingsData.SERVER_CONNECT_HTTP_PORT;
import static org.testng.Assert.assertTrue;

@Epic(value = "Web-Client")
@Feature(value = "Тестирование клиентской части")
public class TestClient implements TestSuiteBase {

    @BeforeClass
    void beforeALl(){
        testBase.addContactAndAccount(CONTACT_NUMBER_7012);
    }

    @Story(value = "Проверяем, подключается ли клиент с нестандарными портами")
    @Description(value = "В адресной строке браузера вводим адрес web клиента с нестандартным портом 88")
    @Test(groups = {"Client other port"})
    void test_Client_Connect_With_Other_Port(){
        String host = HOST_SERVER + ":" + SERVER_CONNECT_HTTP_OTHER_PORT;
        testBase.openClient(host,CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(ClientPage.loginClient(CONTACT_NUMBER_7012 + "@ros.chat", USER_ACCOUNT_PASSWORD, false),
                "Не удалось авторизоваться на порту " + SERVER_CONNECT_HTTP_OTHER_PORT);
    }

    @Story(value = "Проверяем, подключается ли клиент со стандарными портами")
    @Description(value = "В адресной строке браузера вводим адрес web клиента со стандартным портом 80")
    @Test(groups = {"Client standard port"})
    void test_Client_Connect_With_Standard_Port(){
        testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(ClientPage.loginClient(CONTACT_NUMBER_7012 + "@ros.chat", USER_ACCOUNT_PASSWORD, false),
                "Не удалось авторизоваться на порту " + SERVER_CONNECT_HTTP_PORT);
    }
}
