package chat.ros.testing2.server.settings;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class TelephonyPage implements SettingsPage {

    private SelenideElement selectTypeRout = $(".v-input__icon--append i");
    private ElementsCollection listTypeRoute = $$(".v-select-list .v-list__tile__title");
    private ElementsCollection subtitlesModalWindow = modalWindow.findAll("h3");
    private ElementsCollection printsSettingsProvider = $$(".block-content__item p");
    private SelenideElement mainModalInfo = $(".v-dialog .modal-info");
    private ElementsCollection headersModalInfo = mainModalInfo.findAll("h2");
    private ElementsCollection contentModalInfo = mainModalInfo.findAll(".modal-infoContent div");
    private SelenideElement buttonCloseModalInfo = mainModalInfo.find("button");

    public TelephonyPage () {}

    /***
     * this method configures the ip addresses for the network
     * @param mapSettingsNetwork the data with input name and ip addresses for configuration the network
     * @return
     */
    public TelephonyPage setNetwork(Map <String,String> mapSettingsNetwork){
        setSettingsServer(mapSettingsNetwork, TELEPHONY_NETWORK_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return this;
    }

    /**
     * this method configures  the sip server
     * @param mapSettingsSIPServer data for configuring the sip server
     * @return
     */
    public TelephonyPage setSipServer(Map<String, String> mapSettingsSIPServer){
        setSettingsServer(mapSettingsSIPServer, TELEPHONY_SIP_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return this;
    }

    /***
     * this method configures the provider for general section
     * @param mapSettingsProviderServer data for configuring the general section of the provider
     * @return
     */
    public TelephonyPage setProvider(Map<String, String> mapSettingsProviderServer){
        sendInputsForm(mapSettingsProviderServer);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return this;
    }

    /***
     * this method configures the provider with and without registration checkbox
     * @param mapSettingsProviderServer data for configuration the general section of the provider
     * @param mapSettingsRegProvider data for configuration the registration section of the provider
     * @param registration registration checkbox
     * @return
     */
    public TelephonyPage setProvider(Map<String, String> mapSettingsProviderServer, Map<String, String> mapSettingsRegProvider, boolean registration){
        sendInputsForm(mapSettingsProviderServer);
        selectCheckboxProvider(registration);
        if(registration) sendInputsForm(mapSettingsRegProvider);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return this;
    }

    @Step(value = "Включаем {select}")
    public TelephonyPage selectCheckboxProvider(boolean select){
        SelenideElement inputCheckBox = modalWindow.findAll("input").findBy(Condition.type("checkbox"));
        SelenideElement checkBox = modalWindow.find(".v-input--selection-controls__ripple");
        if(select) {
            if( ! inputCheckBox.isSelected())
                checkBox.click();
            inputCheckBox.shouldBe(Condition.selected);
        }
        else {
            if (inputCheckBox.isSelected())
                checkBox.click();
            inputCheckBox.shouldNotBe(Condition.selected);
        }
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
        SelenideElement sectionProvider = $$("h2").findBy(text("Провайдеры")).parent();
        sectionProvider.scrollIntoView(false);
        sectionProvider.
                findAll("table td")
                .findBy(Condition.text(provider))
                .parent()
                .find("button i[title='" + button + "']")
                .click();
        return this;
    }

    @Step(value = "Проверяем отображается {show} ли настройка {content} провайдера в форме настроек провайдера")
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

    @Step(value = "Создаём новый маршрут для направления {typeRoute}")
    public TelephonyPage createRoute(String typeRoute, boolean simpleMode, Map<String, String> dataRoute){
        selectTypeRout.click();
        listTypeRoute.findBy(Condition.text(typeRoute)).click();
        selectCheckboxProvider(simpleMode);
        sendInputsForm(dataRoute);
        return this;
    }

    @Step(value = "Редактируем маршрут {dataRoute}")
    public TelephonyPage editRoute(Map<String, String> dataRoute, boolean... simpleMode){
        if(simpleMode.length > 0) selectCheckboxProvider(simpleMode[0]);
        sendInputsForm(dataRoute);
        return this;
    }

    @Step(value = "Нажимаем кнопку {button} в таблице маршрутов у маршрута {route}")
    public TelephonyPage clickButtonTableRoute(String route, String button){
        $$("table td")
                .findBy(Condition.text(route))
                .parent()
                .findAll("button i")
                .findBy(Condition.text(button))
                .click();
        return this;
    }

    @Step(value = "Нажимаем на иконку вызова tooltip с информацией")
    public TelephonyPage clickIconCallToolTipInfo(String field){
        modalWindow
                .findAll("h4")
                .findBy(text(field))
                .parent()
                .parent()
                .find("i")
                .click();
        return this;
    }

    @Step(value = "Проверяем, отобрается {visible} ли модальное окно с информацией")
    public boolean isVisibleInfoWrapper(boolean visible){
        if(visible){
            try{
                mainModalInfo.shouldBe(Condition.visible);
            }catch (ElementNotFound e){
                return false;
            }
        }else{
            try{
                mainModalInfo.shouldNotBe(Condition.visible);
            }catch (ElementShould e){
                return false;
            }
        }

        return true;
    }

    @Step(value = "Проверяем, отображается ли заголовок {title} модального окна с информацией")
    public boolean isTitleInfoWrapper(String title){
        try {
            headersModalInfo.findBy(text(title)).shouldBe(visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, отображается ли контент {content} модального окна с информацией")
    public boolean isContentInfoWrapper(String content){
        try{
            contentModalInfo.findBy(text(content)).shouldBe(visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Закрываем модальное окно с информацией")
    public TelephonyPage clickButtonCloseModalInfo(){
        buttonCloseModalInfo.click();
        return this;
    }

    /***
     * this method configures the turn/stun server
     * @param mapInputValueTurnServer data for configuration the turn/stun server
     * @return
     */
    public TelephonyPage setTurnserver(Map<String, String> mapInputValueTurnServer){
        setSettingsServer(mapInputValueTurnServer, TELEPHONY_TURN_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return this;
    }

}
