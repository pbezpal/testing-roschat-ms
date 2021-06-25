package chat.ros.testing2.server.settings.integration;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;

public class ActiveDirectoryPage implements IntegrationPage {

    public ActiveDirectoryPage() {}

    public ActiveDirectoryPage settingsActiveDirectory(Map<String, String> mapInputValueAD){
        clickButtonActionService(SETTINGS_BUTTON_SETTING);
        sendInputsForm(mapInputValueAD);
        clickButtonSave();
        isExistsTableText(INTEGRATION_SERVICE_AD_TYPE, true);
        return this;
    }

    public ActiveDirectoryPage deleteActiveDirectory(){
        clickButtonActionService(SETTINGS_BUTTON_DELETE);
        clickButtonConfirmAction(SETTINGS_BUTTON_CONTINUE);
        isExistsTableText(INTEGRATION_SERVICE_AD_TYPE, false);
        return this;
    }
}
