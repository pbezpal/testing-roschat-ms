package chat.ros.testing2.server.settings;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.$;

public class SNMPPage implements SettingsPage {

    private SelenideElement spanSNMPAddress = $("div.bottom-content span.adress");
    private SelenideElement inputAddressServer = $("input[placeholder='225.225.25.5']");
    private SelenideElement inputPortServer = $("input[placeholder='161']");
    private SelenideElement buttonSave = $("div.buttons button.primary--text");


    public SNMPPage() {}

    @Step(value = "Вводим адрес SNMP сервера")
    public SNMPPage sendInputAddressServer(String server){
        inputAddressServer.sendKeys(server);
        return this;
    }

    @Step(value = "Вводим порт SNMP сервера")
    public SNMPPage sendInputPortServer(String port){
        inputPortServer.sendKeys(port);
        return this;
    }

    @Step(value = "Нажимаем кнопку Сохранить")
    @Override
    public SNMPPage clickButtonSave(){
        buttonSave.click();
        return this;
    }

    public String getSettingsSNMPPage(String server, String port){
        clickButtonSettings(SNMP_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        sendInputAddressServer(server);
        sendInputPortServer(port);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return spanSNMPAddress.text();
    }

}
