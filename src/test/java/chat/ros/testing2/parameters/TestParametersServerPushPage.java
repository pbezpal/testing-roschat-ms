package chat.ros.testing2.parameters;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.ServerPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.ParametersData.*;
import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Сервер")
public class TestParametersServerPushPage extends ServerPage {


    private String field;

    public static Object[][] getEmptyValuePush(){
        return new Object[][] {
                {"",SERVER_PUSH_LOGIN_SERVER, SERVER_PUSH_PORT_SERVER, SERVER_PUSH_PASSWORD_SERVER},
                {SERVER_PUSH_HOST_SERVER,"", SERVER_PUSH_PORT_SERVER, SERVER_PUSH_PASSWORD_SERVER},
                {SERVER_PUSH_HOST_SERVER,SERVER_PUSH_LOGIN_SERVER, "", SERVER_PUSH_PASSWORD_SERVER},
                {SERVER_PUSH_HOST_SERVER,SERVER_PUSH_LOGIN_SERVER, SERVER_PUSH_PORT_SERVER, ""}};
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> getWrongValueHostPushServer() {
        ArrayList<String> data = new ArrayList<>();

        for (String host: WRONG_VALUE_HOST) {
            data.add(host);
        }

        return data;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Character> getWrongValuePushPort() {
        ArrayList<Character> data = new ArrayList<>();

        for(char c: WRONG_SYMBOLS_PORT.toCharArray()) {
            data.add(c);
        }

        return data;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Character> getValidValuePushPort() {
        ArrayList<Character> data = new ArrayList<>();

        for (char c : VALID_SYMBOLS_PORT.toCharArray()) {
            data.add(c);
        }

        return data;
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
        assertAll("Проверяем настройки Push сервер с пустыми значениями",
                () -> assertEquals(isShowTextWrongValue(field),"Введите значение",
                        "Надпись 'Введите значение' не появилась"),
                () -> { clickButtonSave(); },
                () -> assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> { clickButtonClose(); },
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия")
        );
    }

    @Story(value = "Проверяем невалидные значение хоста в поле 'IP адрес' в настройках Лицензирование и обслуживание")
    @Description(value = "Вводим невалидный адрес хоста в поле 'IP адрес'" +
            "в настройках Лицензирование и обслуживание и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный адрес' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @ParameterizedTest
    @MethodSource(value = "getWrongValueHostPushServer")
    void test_Wrong_Host_Public_Address_Connect(String address){
        Map<String, String> mapInputValueConnect = new HashMap() {{
            put(SERVER_PUSH_INPUT_HOST, address);
        }};
        setSettingsServer(mapInputValueConnect, SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        assertAll("Проверяем неправильный адрес Push сервера",
                () ->assertEquals(isShowTextWrongValue(SERVER_PUSH_INPUT_HOST),"Неверный адрес",
                        "Надпись 'Неверный адрес' не появилась"),
                () -> { clickButtonSave(); },
                () -> assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> { clickButtonClose(); },
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия")
        );
    }

    @Story(value = "Проверяем номер максимального порта в настройках Лицензирование и обслуживание")
    @Description(value = "Вводим в поля настройки портов формы Лицензирование и обслуживание порт 65536 и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test
    void test_Max_Length_Ports_Push(){
        clickButtonSettings(SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        sendInputForm(SERVER_PUSH_INPUT_PORT, MORE_MAX_VALUE_PORT);
        assertAll("Проверяем значение порта больше максимального",
                () -> assertEquals(isShowTextWrongValue(SERVER_PUSH_INPUT_PORT),"Невалидный порт",
                        "Надпись 'Невалидный порт' не появилась"),
                () -> { clickButtonSave(); },
                () -> assertTrue(isFormChange(),
                        "Формы редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> { clickButtonClose(); },
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия")
        );
    }

    @Story(value = "Проверяем невалидные значения портов в настройках Лицензирование и обслуживание")
    @Description(value = "Вводим невалидные значения портов в настройках Лицензирование и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @ParameterizedTest
    @MethodSource(value = "getWrongValuePushPort")
    void test_Wrong_Value_Ports_Push(Character c){
        clickButtonSettings(SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        sendInputForm(SERVER_PUSH_INPUT_PORT, c.toString());
        assertAll("Проверяем невалидные значения порта Push сервера " + c.toString(),
                () -> assertEquals(isShowTextWrongValue(SERVER_PUSH_INPUT_PORT),"Невалидный порт",
                        "Надпись 'Невалидный порт' не появилась"),
                () -> { clickButtonSave(); },
                () -> assertTrue(isFormChange(),
                        "Формы редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> { clickButtonClose(); },
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия")
        );
    }

    @Story(value = "Проверяем валидные значения порта в настройках Лицензирование и обслуживание")
    @Description(value = "Вводим валидные значения портов в настройках Лицензирование и проверяем: \n" +
            "Сохраняются ли настройки с валидным значением в поле")
    @ParameterizedTest
    @MethodSource(value = "getValidValuePushPort")
    void test_Valid_Ports_Push(Character c){
        clickButtonSettings(SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        sendInputForm(SERVER_PUSH_INPUT_PORT, c.toString());
        clickButtonSave();
        assertTrue(isFormConfirmActions(true), "Не появилась форма, Подтвердите свои действия");
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
    }
}
