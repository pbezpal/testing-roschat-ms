package chat.ros.testing2.server.settings;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.*;

public class TelephonyPage implements SettingsPage {

    //Общие переменные
    private String telephoneSection = "Телефония";
    private SelenideElement selectTypeRout = $(".v-input__icon--append i");
    private ElementsCollection listTypeRoute = $$(".v-select-list .v-list__tile__title");
    private ElementsCollection subtitlesModalWindow = modalWindow.findAll("h3");
    private ElementsCollection printsSettingsProvider = $$(".block-content__item p");

    public TelephonyPage () {}

    //Настраиваем Сеть
    public TelephonyPage setNetwork(Map <String,String> mapSettingsNetwork){
        setSettingsServer(mapSettingsNetwork, TELEPHONY_NETWORK_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return this;
    }

    //Настраиваем SIP сервер
    public TelephonyPage setSipServer(Map<String, String> mapSettingsSIPServer){
        setSettingsServer(mapSettingsSIPServer, TELEPHONY_SIP_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return this;
    }

    public TelephonyPage setProvider(Map<String, String> mapSettingsProviderServer, boolean reg, Map<String, String>... mapSettingsRegProvider){
        sendInputsForm(mapSettingsProviderServer);
        selectCheckboxRegProvider(false);
        if(reg) sendInputsForm(mapSettingsRegProvider[0]);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return this;
    }

    @Step(value = "Включаем {select} регистрацию провайдера")
    public TelephonyPage selectCheckboxRegProvider(boolean select){
        SelenideElement checkBoxRegProvider = modalWindow.findAll("input").findBy(Condition.type("checkbox"));
        if(select) {
            checkBoxRegProvider.setSelected(true);
            checkBoxRegProvider.shouldBe(Condition.selected);
        }
        else checkBoxRegProvider.shouldNotBe(Condition.selected);
        return this;
    }

    @Step(value = "Проверяем, отображается ли подзаголовок {subtitle} модального окна")
    public boolean isSubtitleModalWindow(String subtitle){
        try {
            subtitlesModalWindow.findBy(Condition.text(subtitle)).shouldBe(Condition.visible);
        }catch (ElementShould e){
            return false;
        }

        return true;
    }

    @Step(value = "Нажимаем кнопку {button} в таблице провайдеров у провайдера {provider}")
    public TelephonyPage clickButtonTableProvider(String provider, String button){
        $$("table td")
                .findBy(Condition.text(provider))
                .parent()
                .find("button i[title='" + button + "']")
                .click();
        return this;
    }

    @Step(value = "Проверяем отображается ли настройка {content} провайдера в форме настроек провайдера")
    public boolean isContentSettingProvider(String content, boolean show){
        if(show) {
            try {
                printsSettingsProvider.findBy(Condition.text(content)).shouldBe(Condition.visible);
            } catch (ElementNotFound e) {
                return false;
            }
        }else {
            try {
                printsSettingsProvider.findBy(Condition.text(content)).shouldNotBe(Condition.visible);
            } catch (ElementShould e) {
                return false;
            }
        }

        return true;
    }

    @Step(value = "Создаём новый маршрут {number} для направления {typeRoute}")
    public TelephonyPage setRoute(String number, String typeRoute, boolean simpleMode, String... replaceNumber){
        SelenideElement checkboxSimpleMode = modalWindow.findAll("input").findBy(Condition.type("checkbox"));
        selectTypeRout.click();
        listTypeRoute.findBy(Condition.text(typeRoute)).click();
        sendInputForm("Шаблон номера", number);
        if(replaceNumber.length > 0) sendInputForm("Шаблон замены", replaceNumber[0]);
        if(simpleMode) checkboxSimpleMode.shouldBe(Condition.checked);
        else{
            checkboxSimpleMode.setSelected(false);
            checkboxSimpleMode.shouldNotBe(Condition.checked);
        }
        return this;
    }

    //Настраиваем Turn сервер
    public TelephonyPage setTurnserver(Map<String, String> mapInputValueTurnServer){
        setSettingsServer(mapInputValueTurnServer, TELEPHONY_TURN_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return this;
    }

}
