package chat.ros.testing2.parameters.telephony;

import chat.ros.testing2.server.settings.TelephonyPage;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.ParametersData.WRONG_SYMBOLS_PORT;
import static chat.ros.testing2.data.ParametersData.WRONG_VALUE_IP_ADDRESS;
import static chat.ros.testing2.data.SettingsData.*;
import static chat.ros.testing2.data.SettingsData.TELEPHONY_NETWORK_TITLE_FORM;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TelephonyParams extends TelephonyPage {

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> getWrongValueIPNetwork() {
        ArrayList<String> data = new ArrayList<>();

        for (String host: WRONG_VALUE_IP_ADDRESS) {
            data.add(host);
        }

        return data;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Character> getWrongValuePort() {
        ArrayList<Character> data = new ArrayList<>();

        for(char c: WRONG_SYMBOLS_PORT.toCharArray()) {
            data.add(c);
        }

        return data;
    }

    protected void wrong_ip_network(String field, String value){

        Map<String, String> mapInputValueNetwork = null;

        if(field.equals(TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS)) {
            mapInputValueNetwork = new HashMap() {{
                put(TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS, value);
                put(TELEPHONY_NETWORK_INPUT_FRONT_DEV, TELEPHONY_NETWORK_FRONT_IP);
                put(TELEPHONY_NETWORK_INPUT_INSIDE_DEV, TELEPHONY_NETWORK_INSIDE_IP);
            }};
        }else if(field.equals(TELEPHONY_NETWORK_INPUT_FRONT_DEV)) {
            mapInputValueNetwork = new HashMap() {{
                put(TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS, TELEPHONY_NETWORK_PUBLIC_ADDRESS);
                put(TELEPHONY_NETWORK_INPUT_FRONT_DEV, value);
                put(TELEPHONY_NETWORK_INPUT_INSIDE_DEV, TELEPHONY_NETWORK_INSIDE_IP);
            }};
        }else if(field.equals(TELEPHONY_NETWORK_INPUT_INSIDE_DEV)) {
            mapInputValueNetwork = new HashMap() {{
                put(TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS, TELEPHONY_NETWORK_PUBLIC_ADDRESS);
                put(TELEPHONY_NETWORK_INPUT_FRONT_DEV, TELEPHONY_NETWORK_FRONT_IP);
                put(TELEPHONY_NETWORK_INPUT_INSIDE_DEV, value);
            }};
        }
        setSettingsServer(mapInputValueNetwork, TELEPHONY_NETWORK_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        assertEquals(isShowTextWrongValue(field),"Невалидный IP адрес",
                "Надпись 'Невалидный IP адрес' не появилась");
        clickButtonSave()
                .isFormChange()
                .clickButtonClose();
        assertTrue(isShowFieldAndValue(
                TELEPHONY_NETWORK_TITLE_FORM,
                field,
                value,
                false),
                "Значение " + value + " отображается в поле " + field);
    }

    protected void wrong_sip_ports(String field, String value, String text_error){

        Map<String, String> mapInputValueSipPorts = null;

        if(field.equals(TELEPHONY_SIP_INPUT_MIN_PORT)) {
            mapInputValueSipPorts = new HashMap() {{
                put(TELEPHONY_SIP_INPUT_MIN_PORT, value);
                put(TELEPHONY_SIP_INPUT_MAX_PORT, TELEPHONY_SIP_MAX_PORT);
            }};
        }else if(field.equals(TELEPHONY_SIP_INPUT_MAX_PORT)) {
            mapInputValueSipPorts = new HashMap() {{
                put(TELEPHONY_SIP_INPUT_MIN_PORT, TELEPHONY_SIP_MIN_PORT);
                put(TELEPHONY_SIP_INPUT_MAX_PORT, value);
            }};
        }
        setSettingsServer(mapInputValueSipPorts, TELEPHONY_SIP_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        assertEquals(isShowTextWrongValue(field),text_error,
                "Надпись " + text_error + " не появилась");
        clickButtonSave()
                .isFormChange()
                .clickButtonClose();
        assertTrue(isShowValuesInField(
                TELEPHONY_SIP_TITLE_FORM,
                TELEPHONY_INPUT_SPEECH_PORTS,
                value,
                false),
                "В поле " + TELEPHONY_INPUT_SPEECH_PORTS + ", сохранилось значение " + value);
    }

    protected void wrong_turn_ports(String field, String value){

        Map<String, String> mapInputValueTurn = null;

        if(field.equals(TELEPHONY_TURN_INPUT_MIN_PORT)) {
            mapInputValueTurn = new HashMap() {{
                put(TELEPHONY_TURN_INPUT_MIN_PORT, value);
                put(TELEPHONY_TURN_INPUT_MAX_PORT, TELEPHONY_TURN_MAX_PORT);
                put(TELEPHONY_TURN_INPUT_SECRET, TELEPHONY_TURN_SECRET);
            }};
        }else if(field.equals(TELEPHONY_TURN_INPUT_MAX_PORT)) {
            mapInputValueTurn = new HashMap() {{
                put(TELEPHONY_TURN_INPUT_MIN_PORT, TELEPHONY_TURN_MIN_PORT);
                put(TELEPHONY_TURN_INPUT_MAX_PORT, value);
                put(TELEPHONY_TURN_INPUT_SECRET, TELEPHONY_TURN_SECRET);
            }};
        }
        setSettingsServer(mapInputValueTurn, TELEPHONY_TURN_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        assertEquals(isShowTextWrongValue(field),"Невалидный порт",
                "Надпись 'Невалидный порт' не появилась");
        clickButtonSave()
                .isFormChange()
                .clickButtonClose();
        assertTrue(isShowValuesInField(
                TELEPHONY_TURN_TITLE_FORM,
                TELEPHONY_INPUT_SPEECH_PORTS,
                value,
                false),
                "В поле " + field + ", сохранилось невалидное значение");
    }

}
