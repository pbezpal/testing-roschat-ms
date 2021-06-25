package chat.ros.testing2.server.settings.integration;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;

public class SKUDPage implements IntegrationPage {

    public SKUDPage() {}

    public SKUDPage settingsSKUD(Map<String, String> mapInputValueOM, String skud){
        clickButtonActionService(SETTINGS_BUTTON_SETTING);
        sendInputsForm(mapInputValueOM);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        isExistsTableText(skud, true);
        return this;
    }

    public SKUDPage deleteSKUD(String skud){
        clickButtonActionService(SETTINGS_BUTTON_DELETE);
        clickButtonConfirmAction(SETTINGS_BUTTON_CONTINUE);
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        isExistsTableText(skud, false);
        return this;
    }

}
