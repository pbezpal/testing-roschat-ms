package chat.ros.testing2.server.settings.integration;

import chat.ros.testing2.server.settings.SettingsPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.gen5.api.Assertions.assertTrue;

public interface IntegrationPage extends SettingsPage {

    ElementsCollection buttonActionService = $$(".block-actions .v-btn__content");
    ElementsCollection buttonComplex = $$("div.block-content.complex button div");
    SelenideElement buttonSaveContacts = $("div.sync-wrapper button.primary");
    SelenideElement divLoading = $("div.loader-wrapper");
    String locatorButton = "//table//td[contains(text(),'%1$s')]//ancestor::tr//button";

    @Step(value = "Проверяем, доступен ли сервис {service} для выбора")
    default boolean isAvailableTypeService(String service){
        try{
            listItems.findBy(Condition.text(service)).shouldBe(Condition.enabled);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Выбираем сервис {service}")
    default IntegrationPage clickTypeService(String service){
        assertTrue(isAvailableTypeService(service), "Сервис " + service + " недоступен для выбора");
        listItems.findBy(Condition.text(service)).click();
        return this;
    }

    @Step(value = "Нажимаем кнопку {button}")
    default IntegrationPage clickButtonOnComplex(String button){
        buttonComplex.findBy(Condition.text(button)).click();
        return this;
    }

    @Step(value = "Нажимаем кнопку {button}")
    default IntegrationPage clickButtonActionService(String button){
        buttonActionService.findBy(Condition.text(button)).waitUntil(Condition.enabled, 300000).click();
        return this;
    }

    @Step(value = "Нажимаем кнопку сохранить")
    default IntegrationPage clickSaveContacts(){
        buttonSaveContacts.waitUntil(Condition.enabled, 300000).click();
        return this;
    }

    @Step(value = "Ждём, когда пропадёт форма сохранения контактов")
    default IntegrationPage waitNotShowWindowSaveContacts(){
        buttonSaveContacts.should(Condition.not(Condition.visible));
        return this;
    }

    @Step(value = "Ждём, когда пропадёт элемент загрузки синхронизации")
    default IntegrationPage waitNotShowLoadWrapper(){
        divLoading.waitUntil(Condition.not(Condition.visible), 600000);
        return this;
    }

    default Object clickServiceType(String service){
        $x(String.format(locatorButton,service)).waitUntil(Condition.visible,60000).click();
        switch(service){
            case INTEGRATION_SERVICE_TETRA_TYPE:
                return new TetraPage();
            case INTEGRATION_SERVICE_OM_TYPE:
            case INTEGRATION_SERVICE_ORION_TYPE:
            case INTEGRATION_SERVICE_PERCO_TYPE:
            case "СКУД":
                return new SKUDPage();
            case INTEGRATION_SERVICE_AD_TYPE:
                return new ActiveDirectoryPage();
        }
        return null;
    }

    default Object addIntegrationService(String service){
        clickButtonAdd();
        assertTrue(isAvailableTypeService(service), service + " недоступно для выбора");
        clickTypeService(service);
        switch(service){
            case INTEGRATION_SERVICE_TETRA_TYPE:
                return new TetraPage();
            case INTEGRATION_SERVICE_OM_TYPE:
            case INTEGRATION_SERVICE_ORION_TYPE:
            case INTEGRATION_SERVICE_PERCO_TYPE:
                return new SKUDPage();
            case INTEGRATION_SERVICE_AD_TYPE:
                return new ActiveDirectoryPage();
        }
        return null;
    }

    default boolean syncContacts(){
        clickButtonOnComplex("Синхронизировать");
        if(isFormCheckSettings()) return false;
        clickSaveContacts();
        waitNotShowWindowSaveContacts();
        waitNotShowLoadWrapper();
        return true;
    }
}
