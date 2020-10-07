package chat.ros.testing2.parameters.serverpage;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.ServerPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
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
public class TestParametersServerPushValidPort extends ServerPage {

    private Map<String, String> mapInputValuePush = null;

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Character> getValidValuePushPort() {
        ArrayList<Character> data = new ArrayList<>();

        for (char c : VALID_SYMBOLS_PORT.toCharArray()) {
            data.add(c);
        }

        return data;
    }

    @Story(value = "Проверяем валидные значения порта в настройках Лицензирование и обслуживание")
    @Description(value = "Вводим валидные значения портов в настройках Лицензирование и проверяем: \n" +
            "Сохраняются ли настройки с валидным значением в поле")
    @ParameterizedTest
    @MethodSource(value = "getValidValuePushPort")
    void test_Valid_Ports_Push(Character c){
        this.mapInputValuePush = new HashMap() {{
            put(SERVER_PUSH_INPUT_HOST, SERVER_PUSH_HOST_SERVER);
            put(SERVER_PUSH_INPUT_LOGIN, SERVER_PUSH_LOGIN_SERVER);
            put(SERVER_PUSH_INPUT_PORT, c.toString());
            put(SERVER_PUSH_INPUT_PASSWORD, SERVER_PUSH_PASSWORD_SERVER);
        }};
        setSettingsServer(this.mapInputValuePush, SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        clickButtonSave();
        assertTrue(isFormConfirmActions(true), "Не появилась форма, Подтвердите свои действия");
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
    }
}
