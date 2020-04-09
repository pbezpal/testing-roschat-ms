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

public class IntegrationPage extends SettingsPage {

    private ElementsCollection listServiceIntegration = $$("table td");
    private SelenideElement buttonAddService = $("div.table-box button");
    private ElementsCollection listServices = $$("div.menuable__content__active a:not([disabled]) div.v-list__tile__title");
    private SelenideElement buttonSettingsService = $("div.block-content button");
    private ElementsCollection buttonComplex = $$("div.block-content.complex button div");
    private SelenideElement buttonSaveContacts = $("div.sync-wrapper button.primary");
    private SelenideElement divLoading = $("div.loader-wrapper");
    private SelenideElement msgError = $("div.msg-header h3");

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

    @Step(value = "Нажимаем кнопку {button}")
    protected IntegrationPage clickButtonOnComplex(String button){
        buttonComplex.findBy(Condition.text(button)).click();
        return this;
    }

    @Step(value = "Нажимаем кнопку 'Настроить'")
    protected IntegrationPage clickButtonSettings(){
        buttonSettingsService.click();
        return this;
    }

    @Step(value = "Проверяем, что сервер Tetra {server} был добавлен")
    protected boolean isService(String service){
        try{
            listServiceIntegration.findBy(Condition.text(service)).shouldBe(Condition.visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Нажимаем кнопку сохранить")
    protected IntegrationPage clickSaveContacts(){
        buttonSaveContacts.waitUntil(Condition.enabled, 300000).click();
        return this;
    }

    @Step(value = "Ждём, когда пропадёт форма сохранения контактов")
    protected IntegrationPage waitNotShowWindowSaveContacts(){
        buttonSaveContacts.should(Condition.not(Condition.visible));
        return this;
    }

    @Step(value = "Ждём, когда пропадёт элемент загрузки синхронизации")
    protected IntegrationPage waitNotShowLoadWrapper(){
        divLoading.waitUntil(Condition.not(Condition.visible), 600000);
        return this;
    }

    @Step(value = "Проверяем, появилось ли окно с ошибкой")
    protected boolean isShowErrorWindow(){
        try{
            msgError.shouldBe(Condition.not(Condition.text("Ошибка")));
        }catch (ElementShould elementShould){
            return false;
        }

        return true;
    }

    public Object clickServiceType(String service){
        $x("//table//td[contains(text(),'" + service + "')]//ancestor::tr//button").click();
        switch(service){
            case INTEGRATION_SERVICE_TETRA_TYPE:
                return new TetraPage();
            case INTEGRATION_SERVICE_OM_TYPE:
                return new OfficeMonitorPage();
            case INTEGRATION_SERVICE_AD_TYPE:
                return new ActiveDirectoryPage();
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
            case INTEGRATION_SERVICE_AD_TYPE:
                return new ActiveDirectoryPage();
        }
        return null;
    }

    public boolean syncContacts(){
        clickButtonOnComplex("Синхронизировать");
        clickSaveContacts();
        waitNotShowWindowSaveContacts();
        waitNotShowLoadWrapper();
        return isShowErrorWindow();
    }
}
