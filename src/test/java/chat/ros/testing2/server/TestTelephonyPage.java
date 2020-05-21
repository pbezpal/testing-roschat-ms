package chat.ros.testing2.server;

import chat.ros.testing2.TestSuiteBase;
import chat.ros.testing2.server.settings.TelephonyPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static org.testng.Assert.assertTrue;

@Epic(value = "Настройки")
@Feature(value = "Телефония")
public class TestTelephonyPage extends TelephonyPage implements TestSuiteBase {

    private SoftAssert softAssert;
    private String field;
    private Map<String, String> mapInputValueNetwork = new HashMap() {{
        put(TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS, TELEPHONY_NETWORK_PUBLIC_ADDRESS);
        put(TELEPHONY_NETWORK_INPUT_FRONT_DEV, TELEPHONY_NETWORK_FRONT_IP);
        put(TELEPHONY_NETWORK_INPUT_INSIDE_DEV, TELEPHONY_NETWORK_INSIDE_IP);
    }};
    private Map<String, String> mapInputValueSipServer = new HashMap() {{
        put(TELEPHONY_SIP_INPUT_MIN_PORT, TELEPHONY_SIP_MIN_PORT);
        put(TELEPHONY_SIP_INPUT_MAX_PORT, TELEPHONY_SIP_MAX_PORT);
    }};
    private Map<String,String> mapInputWrongValueSipServer = new HashMap() {{
        put(TELEPHONY_SIP_INPUT_MIN_PORT, TELEPHONY_SIP_MIN_PORT_MORE_MAX_PORT);
        put(TELEPHONY_SIP_INPUT_MAX_PORT, TELEPHONY_SIP_MAX_PORT);
    }};
    private Map<String, String> mapInputValueTurnServer = new HashMap() {{
        put(TELEPHONY_TURN_INPUT_MIN_PORT, TELEPHONY_TURN_MIN_PORT);
        put(TELEPHONY_TURN_INPUT_MAX_PORT, TELEPHONY_TURN_MAX_PORT);
        put(TELEPHONY_TURN_INPUT_SECRET, TELEPHONY_TURN_SECRET);
    }};
    private Map<String, String> mapInputWrongValueTurnServer = new HashMap() {{
        put(TELEPHONY_TURN_INPUT_MIN_PORT, TELEPHONY_TURN_MIN_PORT_MORE_MAX_PORT);
        put(TELEPHONY_TURN_INPUT_MAX_PORT, TELEPHONY_TURN_MAX_PORT);
    }};

    @Story(value = "Настройка сети")
    @Description(value = "Настраиваем сеть для телефонии и проверяем корректность настроек")
    @Test
    void test_Settings_Network(){
        softAssert = new SoftAssert();
        setNetwork(mapInputValueNetwork);
        clickButtonSettings(TELEPHONY_NETWORK_TITLE_FORM, SETTINGS_BUTTON_CHECK);
        softAssert.assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        softAssert.assertTrue(isCheckSettings(), "Настройки сервера некорректны");
        clickButtonCloseCheckSettingsForm();
        for(Map.Entry<String, String> entry : mapInputValueNetwork.entrySet()){
            softAssert.assertTrue(isShowValueInField(
                    TELEPHONY_NETWORK_TITLE_FORM,
                    entry.getKey(),
                    entry.getValue(),
                    true),
                    "В разделе " + TELEPHONY_NETWORK_TITLE_FORM + "Значения " + entry.getValue() + " " +
                            "нет в поле " + entry.getKey());
        }
        softAssert.assertAll();
    }

    @Story(value = "Настройка SIP-сервера")
    @Description(value = "Настраиваем речевые порты SIP-сервера для телефонии")
    @Test
    void test_Settings_SIP_Server(){
        softAssert = new SoftAssert();
        setSipServer(mapInputValueSipServer);
        for(Map.Entry<String, String> entry : mapInputValueSipServer.entrySet()){
            softAssert.assertTrue(isShowValuesInField(
                    TELEPHONY_SIP_TITLE_FORM,
                    TELEPHONY_INPUT_SPEECH_PORTS,
                    entry.getValue(),
                    true),
                    "В разделе " + TELEPHONY_SIP_TITLE_FORM + "Значения " + entry.getValue() + " " +
                            "нет в поле " + entry.getKey());
        }
        softAssert.assertAll();
    }

    @Story(value = "Настройка SIP-сервера. Минимальный порт больше максимального")
    @Description(value = "В настройках SIP-сервера вводим в поле 'Минимальный порт' значение больше, чем в поле " +
            "'Максимальный порт' и проверяем, будет ли СУ выдавать ошибку")
    @Test
    void test_SIP_Server_Min_Port_More_Max_Port(){
        softAssert = new SoftAssert();
        setSettingsServer(mapInputWrongValueSipServer, TELEPHONY_SIP_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        softAssert.assertEquals(getTextWrong(), "Значение макс порта должно быть больше",
                "Нет надписи об ошибке 'Значение макс порта должно быть больше'");
        clickButtonClose();
        softAssert.assertTrue(isShowValuesInField(
                TELEPHONY_SIP_TITLE_FORM,
                TELEPHONY_INPUT_SPEECH_PORTS,
                mapInputWrongValueSipServer.get(TELEPHONY_SIP_INPUT_MIN_PORT),
                false),
                "В СУ отображается значение минимального порта "
                        + mapInputWrongValueSipServer.get(TELEPHONY_SIP_INPUT_MIN_PORT) + "" +
                        ", Которое больше максимального порта "
                        + mapInputWrongValueSipServer.get(TELEPHONY_SIP_INPUT_MAX_PORT) + "" +
                        "в разделе " + TELEPHONY_SIP_TITLE_FORM);
        softAssert.assertAll();
    }

    @Story(value = "Настройка TURN/STUN")
    @Description(value = "Настраиваем речевые порты и ключ Turn/Stun сервер для телефонии")
    @Test
    void test_Settings_Turn_Server(){
        softAssert = new SoftAssert();
        setTurnserver(mapInputValueTurnServer);
        for(Map.Entry<String,String> entry : mapInputValueTurnServer.entrySet()){
            if(entry.getKey().contains("порт")) field = TELEPHONY_INPUT_SPEECH_PORTS;
            else field = entry.getKey();
            softAssert.assertTrue(isShowValuesInField(
                    TELEPHONY_TURN_TITLE_FORM,
                    field,
                    entry.getValue(),
                    true),
                    "В разделе " + TELEPHONY_TURN_TITLE_FORM + "Значения " + entry.getValue() + " " +
                            "нет в поле " + entry.getKey());
        }
        softAssert.assertAll();
    }

    @Story(value = "Настройка TURN/STUN. Минимальный порт больше максимального.")
    @Description(value = "Настраиваем Turn/Stun сервер для телефонии в поле 'Минимальный порт' значение больше, " +
            "чем в поле 'Максимальный порт' и проверяем, будет ли СУ выдавать ошибку")
    @Test
    void test_Settings_Turn_Server_Min_Port_More_Max_Port(){
        softAssert = new SoftAssert();
        setSettingsServer(mapInputWrongValueTurnServer, TELEPHONY_TURN_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        softAssert.assertEquals(getTextWrong(), "Значение макс порта должно быть больше",
                "Нет надписи об ошибке 'Значение макс порта должно быть больше'");
        clickButtonClose();
        softAssert.assertTrue(isShowValuesInField(
                TELEPHONY_TURN_TITLE_FORM,
                TELEPHONY_INPUT_SPEECH_PORTS,
                mapInputWrongValueTurnServer.get(TELEPHONY_TURN_INPUT_MIN_PORT),
                false),
                "В СУ отображается значение минимального порта "
                        + mapInputWrongValueSipServer.get(TELEPHONY_TURN_INPUT_MIN_PORT) + "" +
                        ", Которое больше максимального порта "
                        + mapInputWrongValueSipServer.get(TELEPHONY_TURN_INPUT_MAX_PORT) + " " +
                        "в разделе " + TELEPHONY_TURN_TITLE_FORM);
        softAssert.assertAll();
    }
}
