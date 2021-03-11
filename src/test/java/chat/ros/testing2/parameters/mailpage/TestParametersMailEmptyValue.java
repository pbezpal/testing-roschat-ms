package chat.ros.testing2.parameters.mailpage;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.MailPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.Ignore;
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
@Feature(value = "Почта")
public class TestParametersMailEmptyValue extends MailPage {

    private String field;

    public static Object[][] getEmptyValueMail(){
        return new Object[][] {
                {"",MAIL_INFOTEK_USERNAME, MAIL_INFOTEK_PASSWORD, MAIL_PORT_SSL, MAIL_INFOTEK_FROM_USER, MAIL_INFOTEK_FROM_MAIL},
                {MAIL_INFOTEK_SERVER,"", MAIL_INFOTEK_PASSWORD, MAIL_PORT_SSL, MAIL_INFOTEK_FROM_USER, MAIL_INFOTEK_FROM_MAIL},
                {MAIL_INFOTEK_SERVER,MAIL_INFOTEK_USERNAME, "", MAIL_PORT_SSL, MAIL_INFOTEK_FROM_USER, MAIL_INFOTEK_FROM_MAIL},
                {MAIL_INFOTEK_SERVER,MAIL_INFOTEK_USERNAME, MAIL_INFOTEK_PASSWORD, "", MAIL_INFOTEK_FROM_USER, MAIL_INFOTEK_FROM_MAIL},
                {MAIL_INFOTEK_SERVER,MAIL_INFOTEK_USERNAME, MAIL_INFOTEK_PASSWORD, MAIL_PORT_SSL, MAIL_INFOTEK_FROM_USER, ""}};
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Character> getWrongValueMailPort() {
        ArrayList<Character> data = new ArrayList<>();

        for (Character host: WRONG_SYMBOLS_PORT.toCharArray()) {
            data.add(host);
        }

        return data;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Character> getValidValueMailPorts() {
        ArrayList<Character> data = new ArrayList<>();

        for (Character host: VALID_SYMBOLS_PORT.toCharArray()) {
            data.add(host);
        }

        return data;
    }

    @BeforeEach
    public void setUp(){
        field = null;
    }

    @Story(value = "Проверяем настройки Почты на пустые поля")
    @Description(value = "Вводим в поля формы Почта пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @ParameterizedTest
    @MethodSource(value = "getEmptyValueMail")
    void test_Settings_Mail_Empty_Value(String server, String username, String password, String port, String fromUser, String fromMail){
        Map<String, String> mapInputValueMail = new HashMap() {{
            put(MAIL_CONNECT_INPUT_EMAIL_SERVER, server);
            put(MAIL_CONNECT_INPUT_USERNAME, username);
            put(MAIL_CONNECT_INPUT_PASSWORD, password);
            put(MAIL_CONNECT_INPUT_EMAIL_PORT, port);
            put(MAIL_CONTACT_INPUT_FROM_MAIL, fromMail);
        }};
        setSettingsServer(mapInputValueMail, SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        if(server.equals("")) field = MAIL_CONNECT_INPUT_EMAIL_SERVER;
        else if(username.equals("")) field = MAIL_CONNECT_INPUT_USERNAME;
        else if(password.equals("")) field = MAIL_CONNECT_INPUT_PASSWORD;
        else if(port.equals("")) field = MAIL_CONNECT_INPUT_EMAIL_PORT;
        else field = MAIL_CONTACT_INPUT_FROM_MAIL;
        assertAll("Проверяем настройки почты с пустыми полями",
                () -> assertEquals(isShowTextWrongValue(field),"Введите значение",
                        "Надпись 'Введите значение' не появилась"),
                () -> { clickButtonSave(); },
                () -> assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия")
        );
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
    }
}
