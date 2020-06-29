package chat.ros.testing2.server.settings;

import chat.ros.testing2.server.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import com.codeborne.selenide.ex.ElementShouldNot;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public interface SettingsPage extends BasePage {

    SelenideElement buttonCloseForm = $("div.modal-wrapper button.v-btn.v-btn--flat.theme--light.secondary--text");
    SelenideElement formConfirmActions = $("div.dialog-header h3");
    SelenideElement divCheckSettings = $("div.msg-body h4");
    SelenideElement buttonCloseCheckSettingsForm = $("div.msg-actions.actions-wrapper button.v-btn.v-btn--flat.theme--light");
    SelenideElement elementLoaderSettings = $("div.loader-wrapper h2");

    @Step(value = "Проверяем, находимся ли мы в разделе {itemContainer}")
    default boolean isNotSectionSettings(String itemContainer){
        try{
            $("a.v-tabs__item.v-tabs__item--active").waitUntil(Condition.not(text(itemContainer)), 10000);
        }catch (ElementShould element){
            return false;
        }

        return true;
    }

    @Step(value = "Проверяем отображается {show} значение {value} в поле {field}")
    default boolean isShowValueInField(String form, String field, String value, boolean show){
        SelenideElement element = $$("h2").findBy(text(form)).parent().$$(".block-content__item-name h4").
                findBy(text(field)).closest("li").find(".v-chip__content");
        try{
            element.should(enabled);
        }catch (ElementNotFound e){
            return false;
        }

        element.scrollIntoView(false);

        if(show){
            return element.text().equals(value);
        }else {
            return ! element.text().equals(value);
        }
    }

    @Step(value = "Проверяем отображается {show} значение {value} в поле {field}")
    default boolean isShowSymbolsInField(String form, String field, String value, boolean show){
        SelenideElement element = $$("h2").findBy(text(form)).parent().$$(".block-content__item-name h4").
                findBy(text(field)).closest("li").find(".v-chip__content");
        try{
            element.should(enabled);
        }catch (ElementNotFound e){
            return false;
        }

        element.scrollIntoView(false);

        if(show){
            return element.text().contains(value);
        }else {
            return ! element.text().contains(value);
        }
    }

    @Step(value = "Проверяем отображается {show} значение {value} в поле {field}")
    default boolean isShowValuesInField(String form, String field, String value, boolean show){
        ElementsCollection elements = $$("h2").findBy(text(form)).parent().$$(".block-content__item-name h4").
                findBy(text(field)).closest("li").$$(".v-chip__content");

        if(elements.size() == 0) return false;

        elements.last().scrollIntoView(false);

        if(show){
           try {
               elements.findBy(text(value));
           }catch (ElementNotFound e){
               return false;
           }
        }else {
            try {
                elements.findBy(not(text(value)));
            }catch (ElementShould e){
                return false;
            }
        }

        return true;
    }

    @Step(value = "Нажимаем кнопку {button} в разделе {form}")
    default SettingsPage clickButtonSettings(String form, String button){
        SelenideElement element = $$("h2").findBy(text(form)).parent();
        SelenideElement buttonForm = element.$$(".v-btn__content").findBy(text(button));
        element.scrollIntoView(false);
        buttonForm.click();
        return this;
    }

    @Step(value = "Заполняем поля формы")
    default void sendLabelInputsForm(Map<String, String> mapInputValue){
        for(Map.Entry<String, String> entry : mapInputValue.entrySet()) {
            String input = entry.getKey();
            String value = entry.getValue();
            SelenideElement element = $("input[aria-label='" + input + "']");
            element.click();
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.BACK_SPACE);
            element.sendKeys(value);
        }
    }

    @Step(value = "Проверяем, появилась ли надпись о пустом/невалидном значении")
    default String isShowTextWrongValue(String field){
        SelenideElement element = $$(".modal-item__title h4").findBy(text(field)).closest("li").
                find(".v-messages__message");
        try{
            element.shouldBe(visible);
        }catch (ElementNotFound e){
            return null;
        }

        return element.text();
    }

    @Step(value = "Нажимаем кнопку Закрыть")
    default SettingsPage clickButtonCloseForm(){
        buttonCloseForm.click();
        return this;
    }

    @Step(value = "Проверяем, что появилась форма 'Подтвердите свои действия'")
    default boolean isFormConfirmActions(boolean show){
        if(show){
            try{
                formConfirmActions.shouldBe(text("Подтвердите свои действия"), Condition.visible);
            }catch (ElementNotFound element){
                return false;
            }
        }else{
            try{
                formConfirmActions.shouldBe(not(text("Подтвердите свои действия")), not(Condition.visible));
            }catch (ElementShould element){
                return false;
            }
        }


        return true;
    }

    @Step(value = "Нажимаем кнопку {button} в форме 'Подвердите свои действия'")
    default SettingsPage clickButtonConfirmAction(String button){
        $$(".actions-wrapper .v-btn__content").findBy(text(button)).click();
        return this;
    }

    @Step(value = "Проверяем, появилась ли форма Проверки настроек")
    default boolean isFormCheckSettings(){
        try{
            divCheckSettings.shouldBe(Condition.visible);
        }catch (ElementNotFound element){
            return false;
        }

        return true;
    }

    @Step(value = "Проверяем, что проверка настроек прошла успешно")
    default String isCheckSuccessAction(){
        try{
            modalSuccessCheckAction.waitUntil(visible, 15000);
        }catch (ElementNotFound element){
            return null;
        }

        return modalSuccessCheckAction.find("h4").text();
    }

    @Step(value = "Нажимаем кнопку Закрыть на форме проверки настроек")
    default SettingsPage clickButtonCloseCheckSettingsForm(){
        buttonCloseCheckSettingsForm.click();
        return this;
    }

    @Step(value = "Ждём, когда пропадёт элемент загрузки настроек")
    default boolean isNotShowLoaderSettings(){
        try{
            elementLoaderSettings.waitUntil(not(text("Идет загрузка настроек...")), 30000);
        }catch (ElementShould e){
            return false;
        }

        return true;
    }

    default SettingsPage setSettingsServer(Map<String, String> mapInputValue, String form, String button){
        clickButtonSettings(form, button);
        sendInputsForm(mapInputValue);
        return this;
    }
}
