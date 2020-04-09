package chat.ros.testing2.server.settings.integration;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.gen5.api.Assertions.assertTrue;

public class ActiveDirectoryPage extends IntegrationPage {

    public ActiveDirectoryPage() {}

    private Map<String, String> mapInputValueAD = new HashMap() {{
        put("Имя сервера", INTEGRATION_SERVICE_AD_SERVER);
        put("Порт", INTEGRATION_SERVICE_AD_PORT);
        put("Корневой элемент (Base DN)", INTEGRATION_SERVICE_AD_BASE_DN);
        put("Имя пользователя (Bind DN)", INTEGRATION_SERVICE_AD_USERNAME);
        put("Пароль", INTEGRATION_SERVICE_AD_PASSWORD);
    }};

    public IntegrationPage settingsActiveDirectory(){
        clickButtonSettings();
        sendInputsForm(mapInputValueAD);
        clickButtonSave();
        assertTrue(isService(INTEGRATION_SERVICE_AD_TYPE), "Сервис Active Directory не был добавлен");
        return new IntegrationPage();
    }
}
