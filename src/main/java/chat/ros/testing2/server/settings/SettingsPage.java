package chat.ros.testing2.server.settings;

import chat.ros.testing2.server.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertTrue;

public interface SettingsPage extends BasePage {

    SelenideElement buttonCloseForm = $("div.modal-wrapper button.v-btn.v-btn--flat.theme--light.secondary--text");
    SelenideElement formConformActions = $("div.dialog-header h3");
    SelenideElement divCheckSettings = $("div.msg-body h4");
    SelenideElement buttonCloseCheckSettingsForm = $("div.msg-actions.actions-wrapper button.v-btn.v-btn--flat.theme--light");
    SelenideElement elementLoaderSettings = $("div.loader-wrapper h2");

    String labelField = "//h2[text()='%1$s']//ancestor::div[@class='block-wrapper']//div[@class='block-content__item-name']" +
            "/h4[contains(text(),'%2$s')]//ancestor::li//span[@class='v-chip__content']";
    String buttonFormAction = "//div[@class='actions-wrapper']//div[@class='v-btn__content' " +
            "and contains(text(), '%1$s')]";
    String locatorForm = "//h2[text()='%1$s']//ancestor::div[@class='block-wrapper']";
    String locatorButtonForm = locatorForm + "//div[text()='%2$s']";
    String locatorInputForm = "input[aria-label='%1$s']";

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
        try{
            $x(String.format(labelField,form,field)).should(enabled);
        }catch (ElementNotFound e){
            return false;
        }

        $x(String.format(labelField,form,field)).scrollIntoView(false);

        if(show){
            return $x(String.format(labelField,form,field)).text().equals(value);
        }else {
            return ! $x(String.format(labelField,form,field)).text().equals(value);
        }
    }

    @Step(value = "Проверяем отображается {show} значение {value} в поле {field}")
    default boolean isShowValuesInField(String form, String field, String value, boolean show){
        try{
            $x(String.format(labelField,form,field)).should(enabled);
        }catch (ElementNotFound e){
            return false;
        }

        $x(String.format(labelField,form,field)).scrollIntoView(false);

        if(show){
           try {
               $$x(String.format(labelField,form,field)).findBy(text(value));
           }catch (ElementNotFound e){
               return false;
           }
        }else {
            try {
                $$x(String.format(labelField,form,field)).findBy(not(text(value)));
            }catch (ElementShould e){
                return false;
            }
        }

        return true;
    }

    @Step(value = "Нажимаем кнопку {button} в разделе {form}")
    default SettingsPage clickButtonSettings(String form, String button){
        $x(String.format(locatorForm,form)).scrollIntoView(false);
        $x(String.format(locatorButtonForm,form,button)).click();
        return this;
    }

    @Step(value = "Заполняем поля формы")
    default void sendLabelInputsForm(Map<String, String> mapInputValue){
        for(Map.Entry<String, String> entry : mapInputValue.entrySet()) {
            String input = entry.getKey();
            String value = entry.getValue();
            $(String.format(locatorInputForm,input)).click();
            $(String.format(locatorInputForm,input)).sendKeys(Keys.CONTROL + "a");
            $(String.format(locatorInputForm,input)).sendKeys(Keys.BACK_SPACE);
            $(String.format(locatorInputForm,input)).sendKeys(value);
        }
    }

    @Step(value = "Нажимаем кнопку Закрыть")
    default SettingsPage clickButtonCloseForm(){
        buttonCloseForm.click();
        return this;
    }

    @Step(value = "Проверяем, что появилась форма 'Подтвердите свои действия'")
    default boolean isFormConfirmActions(){
        try{
            formConformActions.shouldHave(text("Подтвердите свои действия"), Condition.visible);
        }catch (ElementNotFound element){
            return false;
        }

        return true;
    }

    @Step(value = "Нажимаем кнопку {button} в форме 'Подвердите свои действия'")
    default SettingsPage clickButtonConfirmAction(String button){
        $x(String.format(buttonFormAction,button)).click();
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
