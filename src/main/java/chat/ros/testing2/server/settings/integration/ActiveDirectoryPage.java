package chat.ros.testing2.server.settings.integration;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;

public class ActiveDirectoryPage implements IntegrationPage {

    public ActiveDirectoryPage() {}

    public boolean settingsActiveDirectory(Map<String, String> mapInputValueAD){
        clickButtonActionService(SETTINGS_BUTTON_SETTING);
        sendInputsForm(mapInputValueAD);
        clickButtonSave();
        return isExistsTableText(INTEGRATION_SERVICE_AD_TYPE, true);
    }

    public boolean deleteActiveDirectory(){
        clickButtonActionService(SETTINGS_BUTTON_DELETE);
        clickButtonConfirmAction(SETTINGS_BUTTON_CONTINUE);
        return isExistsTableText(INTEGRATION_SERVICE_AD_TYPE, false);
    }
}
