package chat.ros.testing2.server.settings.integration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class OfficeMonitorPage extends IntegrationPage {

    private SelenideElement buttonSettingsOM = $("div.block-content button");
    private SelenideElement buttonSaveContacts = $("div.sync-wrapper button.primary");
    private SelenideElement divLoading = $("div.loader-wrapper");
    private ElementsCollection buttonComplex = $$("div.block-content.complex button div");
    private SelenideElement msgError = $("div.msg-header h3");

    public OfficeMonitorPage() {}

    private Map<String, String> mapInputValueOM = new HashMap() {{
        put("IP адрес", INTEGRATION_SERVICE_OM_IP_ADDRESS);
        put("Порт БД", INTEGRATION_SERVICE_OM_PORT_BD);
        put("Имя пользователя БД", INTEGRATION_SERVICE_OM_LOGIN_DB);
    }};

    @Step(value = "Нажимаем кнопку 'Настроить'")
    private OfficeMonitorPage clickButtonSettings(){
        buttonSettingsOM.click();
        return this;
    }

    @Step(value = "Нажимаем кнопку {button}")
    private OfficeMonitorPage clickButtonOnComplex(String button){
        buttonComplex.findBy(Condition.text(button)).click();
        return this;
    }

    @Step(value = "Нажимаем кнопку сохранить")
    private OfficeMonitorPage clickSaveContacts(){
        buttonSaveContacts.waitUntil(Condition.enabled, 300000).click();
        return this;
    }

    @Step(value = "Ждём, когда пропадёт форма сохранения контактов")
    private OfficeMonitorPage waitNotShowWindowSaveContacts(){
        buttonSaveContacts.should(Condition.not(Condition.visible));
        return this;
    }

    @Step(value = "Ждём, когда пропадёт элемент загрузки синхронизации")
    private OfficeMonitorPage waitNotShowLoadWrapper(){
        divLoading.waitUntil(Condition.not(Condition.visible), 600000);
        return this;
    }

    @Step(value = "Проверяем, появилось ли окно с ошибкой")
    private boolean isShowErrorWindow(){
        try{
            msgError.shouldBe(Condition.not(Condition.text("Ошибка")));
        }catch (ElementShould elementShould){
            return false;
        }

        return true;
    }

    public IntegrationPage settingsOfficeMonitor(){
        clickButtonSettings();
        sendInputsForm(mapInputValueOM);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return new IntegrationPage();
    }

    public boolean syncContacts(){
        clickButtonOnComplex("Синхронизировать");
        clickSaveContacts();
        waitNotShowWindowSaveContacts();
        waitNotShowLoadWrapper();
        return isShowErrorWindow();
    }

}
