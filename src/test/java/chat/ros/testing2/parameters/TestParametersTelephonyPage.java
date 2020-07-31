package chat.ros.testing2.parameters;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
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

@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Телефония")
public class TestParametersTelephonyPage extends TelephonyPage {

    private String field;

    public static Object[][] getEmptyValueNetwork(){
        return new Object[][] {
                {"",TELEPHONY_NETWORK_FRONT_IP, TELEPHONY_NETWORK_INSIDE_IP},
                {TELEPHONY_NETWORK_PUBLIC_ADDRESS,"", TELEPHONY_NETWORK_INSIDE_IP},
                {TELEPHONY_NETWORK_PUBLIC_ADDRESS,TELEPHONY_NETWORK_FRONT_IP, ""}};
    }

    public static Object[][] getEmptyValueSip(){
        return new Object[][] {
                {"",TELEPHONY_SIP_MAX_PORT},
                {TELEPHONY_SIP_MIN_PORT,""}};
    }

    public static Object[][] getEmptyValueTurn(){
        return new Object[][] {
                {"",TELEPHONY_TURN_MAX_PORT, TELEPHONY_TURN_REALM, TELEPHONY_TURN_SECRET},
                {TELEPHONY_TURN_MIN_PORT,"", TELEPHONY_TURN_REALM, TELEPHONY_TURN_SECRET},
                {TELEPHONY_TURN_MIN_PORT,TELEPHONY_TURN_MAX_PORT, "", TELEPHONY_TURN_SECRET},
                {TELEPHONY_TURN_MIN_PORT,TELEPHONY_TURN_MAX_PORT, TELEPHONY_TURN_REALM, ""}};
    }

    @BeforeEach
    public void setUp(){
        field = null;
    }

    @Story(value = "Проверяем настройки Сети на пустые поля")
    @Description(value = "Вводим в поля формы Сеть пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @ParameterizedTest
    @MethodSource(value = "getEmptyValueNetwork")
    void test_Settings_Network_Empty_Value(String publicIP, String frontIP, String insideIP){
        Map<String, String> mapInputValueNetwork = new HashMap() {{
            put(TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS, publicIP);
            put(TELEPHONY_NETWORK_INPUT_FRONT_DEV, frontIP);
            put(TELEPHONY_NETWORK_INPUT_INSIDE_DEV, insideIP);
        }};
        setSettingsServer(mapInputValueNetwork, TELEPHONY_NETWORK_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        if(publicIP.equals("")) field = TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS;
        else if(frontIP.equals("")) field = TELEPHONY_NETWORK_INPUT_FRONT_DEV;
        else field = TELEPHONY_NETWORK_INPUT_INSIDE_DEV;
        assertAll("Проверяем настройки сети на пустые значения",
                () -> assertEquals(isShowTextWrongValue(field),"Введите значение",
                        "Надпись 'Введите значение' не появилась"),
                () -> { clickButtonSave(); },
                () -> assertTrue(isFormChange(),
                        "Форма редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия")
        );
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        assertTrue(isShowValueInField(
                TELEPHONY_NETWORK_TITLE_FORM,
                field,
                "",
                false),
                "В поле " + field + ", сохранилось пустое значение");
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
        assertAll("Проверяем настройки SIP сервера на пустые поля",
                () -> assertEquals(isShowTextWrongValue(field),"Введите значение",
                        "Надпись 'Введите значение' не появилась"),
                () -> { clickButtonSave(); },
                () -> assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия")
        );
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        assertTrue(isShowValuesInField(
                TELEPHONY_SIP_TITLE_FORM,
                TELEPHONY_INPUT_SPEECH_PORTS,
                "",
                false),
                "В поле " + TELEPHONY_INPUT_SPEECH_PORTS + ", сохранилось пустое значение");
    }

    @Story(value = "Проверяем настройки Turn/Stun сервера на пустые поля")
    @Description(value = "Вводим в поля формы Turn/Stun пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @ParameterizedTest
    @MethodSource(value = "getEmptyValueTurn")
    void test_Settings_Turn_Empty_Value(String minPort, String maxPort, String realm, String secret){
        Map<String, String> mapInputValueTurn = new HashMap() {{
            put(TELEPHONY_TURN_INPUT_MIN_PORT, minPort);
            put(TELEPHONY_TURN_INPUT_MAX_PORT, maxPort);
            put(TELEPHONY_TURN_INPUT_REALM, realm);
            put(TELEPHONY_TURN_INPUT_SECRET, secret);
        }};
        setSettingsServer(mapInputValueTurn, TELEPHONY_TURN_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        if(minPort.equals("")) field = TELEPHONY_TURN_INPUT_MIN_PORT;
        else if(maxPort.equals("")) field = TELEPHONY_TURN_INPUT_MAX_PORT;
        else if(realm.equals("")) field = TELEPHONY_TURN_INPUT_REALM;
        else field = TELEPHONY_TURN_INPUT_SECRET;
        assertAll("Проверяем настройи Turn/Stun сервера на пустые поля",
                () -> assertEquals(isShowTextWrongValue(field),"Введите значение",
                        "Надпись 'Введите значение' не появилась"),
                () -> { clickButtonSave(); },
                () -> assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия")
        );
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        if(minPort.equals("") || maxPort.equals("")) {
            assertTrue(isShowValuesInField(
                    TELEPHONY_TURN_TITLE_FORM,
                    TELEPHONY_INPUT_SPEECH_PORTS,
                    "",
                    false),
                    "В поле " + field + ", сохранилось пустое значение");
        }else{
            assertTrue(isShowValueInField(
                    TELEPHONY_TURN_TITLE_FORM,
                    field,
                    "",
                    false),
                    "В поле " + field + ", сохранилось пустое значение");
        }
    }
}
