package chat.ros.testing2.server.settings;

import com.codeborne.selenide.SelenideElement;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.$;

public class TelephonyPage implements SettingsPage {

    //Общие переменные
    private String telephoneSection = "Телефония";

    public TelephonyPage () {}

    //Настраиваем Сеть
    public TelephonyPage setNetwork(Map <String,String> mapSettingsNetwork){
        setSettingsServer(mapSettingsNetwork, TELEPHONY_NETWORK_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return this;
    }

    //Настраиваем SIP сервер
    public TelephonyPage setSipServer(Map<String, String> mapSettingsSIPServer){
        setSettingsServer(mapSettingsSIPServer, TELEPHONY_SIP_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return this;
    }

    //Настраиваем Turn сервер
    public TelephonyPage setTurnserver(Map<String, String> mapInputValueTurnServer){
        setSettingsServer(mapInputValueTurnServer, TELEPHONY_TURN_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return this;
    }

}
