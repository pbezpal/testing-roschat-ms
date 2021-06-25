package chat.ros.testing2.parameters.serverpage;

import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.parameters.ResourcesParameters;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ResourcesParameters.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Сервер")
public class TestParametersServerConnectWrongHost extends ServerParams {

    private static String hostServer = System.getProperty("hostserver");
    private String field;

    public static Object[][] getEmptyValueConnect(){
        return new Object[][] {
                {"",SERVER_CONNECT_HTTP_OTHER_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {hostServer,"", SERVER_CONNECT_HTTPS_OTHER_PORT, SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {hostServer,SERVER_CONNECT_HTTP_OTHER_PORT, "", SERVER_CONNECT_WEBSOCKET_OTHER_PORT},
                {hostServer,SERVER_CONNECT_HTTP_OTHER_PORT, SERVER_CONNECT_HTTPS_OTHER_PORT, ""}};
    }

    @BeforeEach
    public void setUp(){
        field = null;
    }

    @Story(value = "Проверяем настройки Подключение на пустые поля")
    @Description(value = "Вводим в поля формы подключения пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @ParameterizedTest
    @MethodSource(value = "getEmptyValueConnect")
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
        assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        clickButtonSave()
                .isFormChange()
                .clickButtonClose();
        if(server.equals("")){
            assertTrue(isShowValueInField(
                    SERVER_CONNECT_TITLE_FORM,
                    SERVER_CONNECT_INPUT_PUBLIC_NETWORK + ":",
                    server,
                    false),
                    "Значение " + server + " отображается в поле " + SERVER_CONNECT_INPUT_PUBLIC_NETWORK + ":");
        }else {
            assertTrue(isShowValueInField(
                    SERVER_CONNECT_TITLE_FORM,
                    SERVER_CONNECT_FIELD_PORTS,
                    ports,
                    false),
                    "Значение " + ports + " отображается в поле " + SERVER_CONNECT_FIELD_PORTS);
        }
    }

    @Story(value = "Проверяем невалидные значение хоста в поле 'Внешний адрес сервера' в настройках Подключение")
    @Description(value = "Вводим невалидные символы в поле 'Внешний адрес сервера' в настройках Подключение и проверяем:\n" +
            "1. Появилась ли красная надпись 'Невалидный адрес'\n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить\n" +
            "3. Сохраняются ли настройки с невалидными значениями")
    @ParameterizedTest(name = "#{index} => address = {0}")
    @MethodSource(value = "getWrongHosts")
    void test_Wrong_Host_Public_Address_Connect(String address){
        Map<String, String> mapInputValueConnect = new HashMap() {{
            put(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, address);
        }};
        setSettingsServer(mapInputValueConnect, SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        assertEquals(isShowTextWrongValue(SERVER_CONNECT_INPUT_PUBLIC_NETWORK),"Неверный адрес",
                "Надпись 'Неверный адрес' не появилась");
        clickButtonSave()
                .isFormChange()
                .clickButtonClose();
        assertTrue(isShowValueInField(
                SERVER_CONNECT_TITLE_FORM,
                SERVER_CONNECT_INPUT_PUBLIC_NETWORK,
                address,
                false),
                "Значение " + address + " отображается в поле " + SERVER_CONNECT_INPUT_PUBLIC_NETWORK + ":");
    }
}
