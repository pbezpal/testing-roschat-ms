package chat.ros.testing2.server.settings.integration;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.gen5.api.Assertions.assertTrue;

public class OfficeMonitorPage extends IntegrationPage {

    public OfficeMonitorPage() {}

    private Map<String, String> mapInputValueOM = new HashMap() {{
        put("IP адрес", INTEGRATION_SERVICE_OM_IP_ADDRESS);
        put("Порт БД", INTEGRATION_SERVICE_OM_PORT_BD);
        put("Имя пользователя БД", INTEGRATION_SERVICE_OM_LOGIN_DB);
    }};

    public IntegrationPage settingsOfficeMonitor(){
        clickButtonSettings();
        sendInputsForm(mapInputValueOM);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        assertTrue(isService(INTEGRATION_SERVICE_OM_TYPE), "Сервис Офис-Монитор не был добавлен");
        return new IntegrationPage();
    }

}
