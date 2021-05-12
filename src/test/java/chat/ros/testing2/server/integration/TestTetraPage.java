package chat.ros.testing2.server.integration;

import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import chat.ros.testing2.server.settings.integration.TetraPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesIntegrationPage.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Интеграция")
public class TestTetraPage implements IntegrationPage {

    private TetraPage tetraPage;
    private Map<String, String> mapInputValueTetra = new HashMap() {{
        put("Название", INTEGRATION_SERVICE_TETRA_NAME);
        put("Описание", INTEGRATION_SERVICE_TETRA_DESCRIPTION);
    }};

    @Story(value = "Добавляем сервис МиниКом TETRA")
    @Description(value = "Переходим в раздел Интеграция, добавляем сервис МиниКом TETRA и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test
    void test_Add_Service_Tetra(){
        tetraPage = (TetraPage) addIntegrationService(INTEGRATION_SERVICE_TETRA_TYPE);
        tetraPage.clickServiceType(INTEGRATION_SERVICE_TETRA_TYPE);
        assertTrue(tetraPage.addTetraServer(mapInputValueTetra), "Сервис тетра не был добавлен");
    }
}
