package chat.ros.testing2.server.settings.integration;

import chat.ros.testing2.server.settings.SettingsPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.gen5.api.Assertions.assertTrue;

public class IntegrationPage extends SettingsPage {

    private TetraPage tetraPage = new TetraPage();

    private SelenideElement buttonAddService = $("div.table-box button");
    private ElementsCollection listServices = $$("div.menuable__content__active a:not([disabled]) div.v-list__tile__title");

    private Map<String, String> mapInputValueTetra = new HashMap() {{
        put("Название", INTEGRATION_SERVICE_TETRA_NAME);
        put("Описание", INTEGRATION_SERVICE_TETRA_DESCRIPTION);
    }};

    @Step(value = "Нажимаем кнопку Добавить")
    private IntegrationPage clickButtonAddService(){
        buttonAddService.click();
        return this;
    }

    @Step(value = "Проверяем, доступен ли сервис {service} для выбора")
    private boolean isAvailableTypeService(String service){
        try{
            listServices.findBy(Condition.text(service)).shouldBe(Condition.enabled);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Выбираем сервис {service}")
    private IntegrationPage clickTypeService(String service){
        assertTrue(isAvailableTypeService(service), "Сервис " + service + " недоступен для выбора");
        listServices.findBy(Condition.text(service)).click();
        return this;
    }

    public IntegrationPage addIntegrationService(String service){
        clickButtonAddService();
        assertTrue(isAvailableTypeService(service));
        clickTypeService(service);
        if(service.equals(INTEGRATION_SERVICE_TETRA_TYPE)){
            buttonAddService.click();
            tetraPage.sendInputsForm(mapInputValueTetra);
            tetraPage.clickButtonSave();
            clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
            assertTrue(isNotShowLoaderSettings(), "Элемент загрузки не пропал");
            assertTrue(tetraPage.isServerTetra(INTEGRATION_SERVICE_TETRA_NAME), "Сервер тетра");
        }
        return this;
    }
}
