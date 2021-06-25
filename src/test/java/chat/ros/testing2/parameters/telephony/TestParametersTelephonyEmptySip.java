package chat.ros.testing2.parameters.telephony;

import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.parameters.ResourcesParameters;
import chat.ros.testing2.server.settings.TelephonyPage;
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
@Feature(value = "Телефония")
public class TestParametersTelephonyEmptySip extends TelephonyPage {

    private String field;

    private static Object[][] getEmptyValueSip(){
        return new Object[][] {
                {"",TELEPHONY_SIP_MAX_PORT},
                {TELEPHONY_SIP_MIN_PORT,""}};
    }

    @BeforeEach
    public void setUp(){
        field = null;
    }

    @Story(value = "Проверяем настройки SIP-сервер на пустые поля")
    @Description(value = "Вводим в поля формы SIP-сервер пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @ParameterizedTest
    @MethodSource(value = "getEmptyValueSip")
    void test_Settings_SIP_Empty_Value(String minPort, String maxPort){
        Map<String, String> mapInputValueSip = new HashMap() {{
            put(TELEPHONY_SIP_INPUT_MIN_PORT, minPort);
            put(TELEPHONY_SIP_INPUT_MAX_PORT, maxPort);
        }};
        setSettingsServer(mapInputValueSip, TELEPHONY_SIP_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        if(minPort.equals("")) field = TELEPHONY_SIP_INPUT_MIN_PORT;
        else field = TELEPHONY_SIP_INPUT_MAX_PORT;
        assertEquals(isShowTextWrongValue(field),"Введите значение",
                "Надпись 'Введите значение' не появилась");
        clickButtonSave()
                .isFormChange()
                .clickButtonClose();
        assertTrue(isShowValuesInField(
                TELEPHONY_SIP_TITLE_FORM,
                TELEPHONY_INPUT_SPEECH_PORTS,
                "",
                false),
                "В поле " + TELEPHONY_INPUT_SPEECH_PORTS + ", сохранилось пустое значение");
    }
}
