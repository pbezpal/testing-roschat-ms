package chat.ros.testing2.parameters.serverpage;

import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.parameters.ResourcesParameters;
import chat.ros.testing2.server.settings.ServerPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
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

@ExtendWith(ResourcesParameters.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Сервер")
public class TestParametersServerPushWrongPort extends ServerPage {

    private Map<String, String> mapInputValuePush = null;

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

    @Story(value = "Проверяем номер максимального порта в настройках Лицензирование и обслуживание")
    @Description(value = "Вводим в поля настройки портов формы Лицензирование и обслуживание порт 65536 и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test
    void test_Max_Length_Ports_Push(){
        this.mapInputValuePush = new HashMap() {{
            put(SERVER_PUSH_INPUT_HOST, SERVER_PUSH_HOST_SERVER);
            put(SERVER_PUSH_INPUT_LOGIN, SERVER_PUSH_LOGIN_SERVER);
            put(SERVER_PUSH_INPUT_PORT, "65536");
            put(SERVER_PUSH_INPUT_PASSWORD, SERVER_PUSH_PASSWORD_SERVER);
        }};
        setSettingsServer(this.mapInputValuePush, SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        assertEquals(isShowTextWrongValue(SERVER_PUSH_INPUT_PORT),"Невалидный порт",
                "Надпись 'Невалидный порт' не появилась");
        clickButtonSave()
                .isFormChange()
                .clickButtonClose();
    }

    @Story(value = "Проверяем невалидные значения портов в настройках Лицензирование и обслуживание")
    @Description(value = "Вводим невалидные значения портов в настройках Лицензирование и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @ParameterizedTest
    @MethodSource(value = "getWrongValuePushPort")
    void test_Wrong_Value_Ports_Push(Character c){
        this.mapInputValuePush = new HashMap() {{
            put(SERVER_PUSH_INPUT_HOST, SERVER_PUSH_HOST_SERVER);
            put(SERVER_PUSH_INPUT_LOGIN, SERVER_PUSH_LOGIN_SERVER);
            put(SERVER_PUSH_INPUT_PORT, c.toString());
            put(SERVER_PUSH_INPUT_PASSWORD, SERVER_PUSH_PASSWORD_SERVER);
        }};
        setSettingsServer(this.mapInputValuePush, SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        assertEquals(isShowTextWrongValue(SERVER_PUSH_INPUT_PORT),"Невалидный порт",
                "Надпись 'Невалидный порт' не появилась");
        clickButtonSave()
                .isFormChange()
                .clickButtonClose();
    }
}
