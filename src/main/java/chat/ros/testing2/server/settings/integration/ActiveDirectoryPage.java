package chat.ros.testing2.server.settings.integration;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;

public class ActiveDirectoryPage implements IntegrationPage {

    public ActiveDirectoryPage() {}

    public boolean settingsActiveDirectory(Map<String, String> mapInputValueAD){
        clickButtonSettings();
        sendInputsForm(mapInputValueAD);
        clickButtonSave();
        return isExistsTableText(INTEGRATION_SERVICE_AD_TYPE);
    }
}
