package chat.ros.testing2.server.settings;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;

public class TelephonyPage extends SettingsPage {

    //Общие переменные
    private String telephoneSection = "Телефония";

    private Map<String, String> mapInputValueNetwork = new HashMap() {{
        put(TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS, TELEPHONY_NETWORK_PUBLIC_ADDRESS);
        put(TELEPHONY_NETWORK_INPUT_FRONT_DEV, TELEPHONY_NETWORK_FRONT_IP);
        put(TELEPHONY_NETWORK_INPUT_INSIDE_DEV, TELEPHONY_NETWORK_INSIDE_IP);
    }};

    private Map<String, String> mapInputValueSipServer = new HashMap() {{
        put(TELEPHONY_SIP_INPUT_MIN_PORT, TELEPHONY_SIP_MIN_PORT);
        put(TELEPHONY_SIP_INPUT_MAX_PORT, TELEPHONY_SIP_MAX_PORT);
    }};

    private Map<String, String> mapInputValueTurnServer = new HashMap() {{
        put(TELEPHONY_TURN_INPUT_SECRET, TELEPHONY_TURN_SECRET);
    }};

    public TelephonyPage () {}

    //Настраиваем Сеть
    public TelephonyPage setNetwork(){
        if(isNotValueInField(TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS, TELEPHONY_NETWORK_PUBLIC_ADDRESS)
                || isNotValueInField(TELEPHONY_NETWORK_INPUT_FRONT_DEV, TELEPHONY_NETWORK_FRONT_IP)
                || isNotValueInField(TELEPHONY_NETWORK_INPUT_INSIDE_DEV, TELEPHONY_NETWORK_INSIDE_IP)){

            setSettingsServer(mapInputValueNetwork, TELEPHONY_NETWORK_TITLE_FORM, SETTINGS_BUTTON_SETTING);
            clickButtonSave();
            clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        }
        checkSettingsServer(TELEPHONY_NETWORK_TITLE_FORM, SETTINGS_BUTTON_CHECK);

        return this;

    }

    //Настраиваем SIP сервер
    public TelephonyPage setSipServer(){
        if(isNotValueInField(TELEPHONY_SIP_INPUT_SPEECH_PORTS, TELEPHONY_SIP_MIN_PORT) && isNotValueInField(TELEPHONY_SIP_INPUT_SPEECH_PORTS, TELEPHONY_SIP_MAX_PORT)){
            setSettingsServer(mapInputValueSipServer, TELEPHONY_SIP_TITLE_FORM, SETTINGS_BUTTON_SETTING);
            clickButtonSave();
            clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        }
        return this;
    }

    //Настраиваем Turn сервер
    public TelephonyPage setTurnserver(){
        if(isNotValueInField(TELEPHONY_TURN_INPUT_SECRET, TELEPHONY_TURN_SECRET)){
            setSettingsServer(mapInputValueTurnServer, TELEPHONY_TURN_TITLE_FORM, SETTINGS_BUTTON_SETTING);
            clickButtonSave();
            clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        }
        return this;
    }

}
