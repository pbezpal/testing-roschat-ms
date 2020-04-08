package chat.ros.testing2.server.settings.integration;

import chat.ros.testing2.server.settings.SettingsPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.gen5.api.Assertions.assertTrue;

public class IntegrationPage extends SettingsPage {

    private SelenideElement buttonAddService = $("div.table-box button");
    private ElementsCollection listServices = $$("div.menuable__content__active a:not([disabled]) div.v-list__tile__title");

    @Step(value = "Нажимаем кнопку Добавить")
    protected IntegrationPage clickButtonAddService(){
        buttonAddService.click();
        return this;
    }

    @Step(value = "Проверяем, доступен ли сервис {service} для выбора")
    protected boolean isAvailableTypeService(String service){
        try{
            listServices.findBy(Condition.text(service)).shouldBe(Condition.enabled);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Выбираем сервис {service}")
    protected IntegrationPage clickTypeService(String service){
        assertTrue(isAvailableTypeService(service), "Сервис " + service + " недоступен для выбора");
        listServices.findBy(Condition.text(service)).click();
        return this;
    }

    public Object clickServiceType(String service){
        $x("//table//td[contains(text(),'" + service + "')]//ancestor::tr//button").click();
        switch(service){
            case INTEGRATION_SERVICE_TETRA_TYPE:
                return new TetraPage();
            case INTEGRATION_SERVICE_OM_TYPE:
                return new OfficeMonitorPage();
        }
        return null;
    }

    public Object addIntegrationService(String service){
        clickButtonAddService();
        assertTrue(isAvailableTypeService(service), service + " недоступно для выбора");
        clickTypeService(service);
        switch(service){
            case INTEGRATION_SERVICE_TETRA_TYPE:
                return new TetraPage();
            case INTEGRATION_SERVICE_OM_TYPE:
                return new OfficeMonitorPage();
        }
        return null;
    }
}
