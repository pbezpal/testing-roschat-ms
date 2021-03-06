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
public class TestParametersServerPushWrongHost extends ServerParams {

    private String field;

    public static Object[][] getEmptyValuePush(){
        return new Object[][] {
                {"",SERVER_PUSH_LOGIN_SERVER, SERVER_PUSH_PORT_SERVER, SERVER_PUSH_PASSWORD_SERVER},
                {SERVER_PUSH_HOST_SERVER,"", SERVER_PUSH_PORT_SERVER, SERVER_PUSH_PASSWORD_SERVER},
                {SERVER_PUSH_HOST_SERVER,SERVER_PUSH_LOGIN_SERVER, "", SERVER_PUSH_PASSWORD_SERVER},
                {SERVER_PUSH_HOST_SERVER,SERVER_PUSH_LOGIN_SERVER, SERVER_PUSH_PORT_SERVER, ""}};
    }

    @BeforeEach
    public void setUp(){
        field = null;
    }

    @Story(value = "Проверяем настройки Push сервера на пустые поля")
    @Description(value = "Вводим в поля формы Лицензирование и обслуживание пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @ParameterizedTest
    @MethodSource(value = "getEmptyValuePush")
    void test_Settings_Push_Empty_Value(String server, String login, String port, String password){
        Map<String, String> mapInputValuePush = new HashMap() {{
            put(SERVER_PUSH_INPUT_HOST, server);
            put(SERVER_PUSH_INPUT_LOGIN, login);
            put(SERVER_PUSH_INPUT_PORT, port);
            put(SERVER_PUSH_INPUT_PASSWORD, password);
        }};
        setSettingsServer(mapInputValuePush, SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        if(server.equals("")) field = SERVER_PUSH_INPUT_HOST;
        else if(login.equals("")) field = SERVER_PUSH_INPUT_LOGIN;
        else if(port.equals("")) field = SERVER_PUSH_INPUT_PORT;
        else if(password.equals("")) field = SERVER_PUSH_INPUT_PASSWORD;
        assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        clickButtonSave()
                .isFormChange()
                .clickButtonClose();
    }

    @Story(value = "Проверяем невалидные значение хоста в поле 'Адрес сервера' в настройках Лицензирование и обслуживание")
    @Description(value = "Вводим невалидный адрес хоста в поле 'Адрес сервера'" +
            "в настройках Лицензирование и обслуживание и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный адрес' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидными значениями")
    @ParameterizedTest(name = "#{index} => address = {0}")
    @MethodSource(value = "getWrongHosts")
    void test_Wrong_Host_Public_Address_Connect(String address){
        Map<String, String> mapInputValueConnect = new HashMap() {{
            put(SERVER_PUSH_INPUT_HOST, address);
            put(SERVER_PUSH_INPUT_LOGIN, SERVER_PUSH_LOGIN_SERVER);
            put(SERVER_PUSH_INPUT_PORT, SERVER_PUSH_PORT_SERVER);
            put(SERVER_PUSH_INPUT_PASSWORD, SERVER_PUSH_PASSWORD_SERVER);
        }};
        setSettingsServer(mapInputValueConnect, SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        assertEquals(isShowTextWrongValue(SERVER_PUSH_INPUT_HOST),"Неверный адрес",
                "Надпись 'Неверный адрес' не появилась");
        clickButtonSave()
                .isFormChange()
                .clickButtonClose();
    }
}
