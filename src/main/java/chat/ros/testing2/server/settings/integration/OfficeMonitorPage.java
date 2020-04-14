package chat.ros.testing2.server.settings.integration;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;

public class OfficeMonitorPage implements IntegrationPage {

    public OfficeMonitorPage() {}

    public boolean settingsOfficeMonitor(Map<String, String> mapInputValueOM){
        clickButtonSettings();
        sendH4InputsForm(mapInputValueOM);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return isExistsTableText(INTEGRATION_SERVICE_OM_TYPE);
    }

}
