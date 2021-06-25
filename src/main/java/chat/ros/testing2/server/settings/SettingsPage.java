package chat.ros.testing2.server.settings;

import chat.ros.testing2.server.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.gen5.api.Assertions.assertTrue;

public interface SettingsPage extends BasePage {

    ElementsCollection listItems = $$(".menuable__content__active a:not([disabled]) div.v-list__tile__title");
    SelenideElement buttonCloseForm = $(".modal-wrapper button.v-btn.v-btn--flat.theme--light.secondary--text");
    SelenideElement formConfirmActions = $(".dialog-header h3");
    SelenideElement buttonCloseCheckSettingsForm = $(".msg-actions.actions-wrapper button.v-btn.v-btn--flat.theme--light");
    SelenideElement elementLoaderSettings = $(".loader-wrapper h2");
    SelenideElement mainWrapper = $(".main-wrapper");

    /**
     * Items for combo box
     */
    SelenideElement elementComboBox = $("div[role='combobox'] i.v-icon.material-icons.theme--light");
    SelenideElement divComboBoxActive = $(".v-select--is-menu-active");
    SelenideElement activeContextMenu = $(".menuable__content__active");
    ElementsCollection listItemsComboBox = activeContextMenu.$$(".v-list__tile__title");

    SelenideElement modalWindowTitle = modalWindow.find("h2");

    @Step(value = "Проверяем, находимся ли мы в разделе {itemContainer}")
    default boolean isNotSectionSettings(String itemContainer){
        try{
            $("a.v-tabs__item.v-tabs__item--active").shouldBe(not(text(itemContainer)), Duration.ofSeconds(10));
        }catch (ElementShould element){
            return false;
        }

        return true;
    }

    @Step(value = "Возвращаем заголовок модального окна")
    default String getTitleOfModalWindow(){
        modalWindowTitle.shouldBe(visible);
        return modalWindowTitle.getText();
    }

    @Step(value = "Проверяем, отображается ли {show} поле {field}")
    default boolean isShowField(String form, String field, boolean show){
        SelenideElement element = $$("h2").findBy(text(form)).parent().$$("div.block-content__item-name h4").
                findBy(text(field));

        $$("h2").findBy(text(form)).scrollIntoView(false);

        if(show){
            try{
                element.should(enabled);
            }catch (ElementNotFound e){
                return false;
            }
        }else {
            try{
                element.should(not(enabled));
            }catch (ElementShould e){
                return false;
            }
        }

        return true;
    }

    @Step(value = "Проверяем отображается {show} значение {value} в поле {field}")
    default boolean isShowValueInField(String form, String field, String value, boolean show){
        SelenideElement element = $$("h2").findBy(text(form)).parent().$$("div.block-content__item-name h4").
                findBy(text(field)).closest("li").find("span.v-chip__content");

        $$("h2").findBy(text(form)).scrollIntoView(false);

        element.should(visible);

        if(show){
            return element.text().equals(value);
        }else {
            return ! element.text().equals(value);
        }
    }

    @Step(value = "Проверяем отображается {show} значение {value} в поле {field}")
    default boolean isShowValueInField(String field, String value, boolean show){
        SelenideElement element = $$("h4").findBy(text(field)).closest("li").find("span.v-chip__content");

        element.shouldBe(visible);

        element.scrollIntoView(false);

        if(show){
            return element.text().equals(value);
        }else {
            return ! element.text().equals(value);
        }
    }

    @Step(value = "Проверяем отображается {show} значение {value} в поле {field}")
    default boolean isShowFieldAndValue(String form, String field, String value, boolean show) {
        SelenideElement element = $$("h2").findBy(text(form)).parent().$$(".block-content__item-name h4").
                findBy(text(field)).closest("li").find(".v-chip__content");

        $$("h2").findBy(text(form)).scrollIntoView(false);

        if (show) {
            return element.text().equals(value);
        } else {
            element.shouldNotBe(visible);
            return ! element.text().equals(value);
        }
    }

    @Step(value = "Проверяем отображается {show} значение {value} в поле {field}")
    default boolean isShowValuesInField(String form, String field, String value, boolean show){
        ElementsCollection elements = $$("h2").findBy(text(form)).parent().$$(".block-content__item-name h4").
                findBy(text(field)).closest("li").$$(".v-chip__content");

        if(elements.size() == 0) return false;

        elements.last().scrollIntoView(false);

        if(show)
            elements.findBy(text(value)).shouldBe(visible);
        else {
            if(value.length() == 0)
                for(SelenideElement element: elements){
                    if(element.text().length() == 0)
                        return false;
                }
            else{
                if(value.equals(" ")) elements.findBy(not(text(value)));
                else elements.findBy(text(value)).shouldNotBe(visible);
            }
        }

        return true;
    }

    @Step(value = "Провкручиваем внизу до формы {form}")
    default SelenideElement scrollDownForm(String form){
        SelenideElement element = $$("h2").findBy(text(form)).parent();
        if(isShowElement(element,false)) $(".v-content__wrap").scrollIntoView(false);
        element.scrollIntoView(false);
        return element;
    }

    @Step(value = "Нажимаем кнопку {button} в разделе {form}")
    default SettingsPage clickButtonSettings(String form, String button){
        SelenideElement buttonForm = scrollDownForm(form).findAll(".v-btn__content").findBy(text(button));
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

    @Step(value = "Проверяем, появилась ли надпись о пустом/невалидном значении у поля {field}")
    default String isShowTextWrongValue(String field){
        SelenideElement element = $$(".modal-item__title h4")
                .findBy(text(field))
                .parent()
                .parent()
                .find(".v-messages__message");
        element.shouldBe(visible);
        return element.text();
    }

    @Step(value = "Нажимаем кнопку Закрыть")
    default SettingsPage clickButtonCloseForm(){
        buttonCloseForm.click();
        return this;
    }

    @Step(value = "Проверяем, отображается ли {show} форма 'Подтвердите свои действия'")
    default SettingsPage isFormConfirmActions(boolean show){
        if(show) formConfirmActions.shouldBe(text("Подтвердите свои действия"), visible);
        else formConfirmActions.shouldNotBe(text("Подтвердите свои действия"), visible);
        return this;
    }

    @Step(value = "Нажимаем кнопку {button} в форме 'Подвердите свои действия'")
    default SettingsPage clickButtonConfirmAction(String button){
        $$(".actions-wrapper .v-btn__content")
                .findBy(text(button))
                .shouldBe(visible,Duration.ofSeconds(10)).click();
        return this;
    }

    @Step(value = "Проверяем, появилась ли форма Проверки настроек")
    default boolean isFormCheckSettings(){
        if(isShowElement(modalWindow, true)) return true;
        else return false;
    }

    @Step(value = "Нажимаем кнопку Закрыть на форме проверки настроек")
    default SettingsPage clickButtonCloseCheckSettingsForm(){
        buttonCloseCheckSettingsForm.click();
        return this;
    }

    @Step(value = "Ждём, когда пропадёт элемент загрузки настроек")
    default SettingsPage isNotShowLoaderSettings(){
        elementLoaderSettings.shouldNotBe(visible, Duration.ofSeconds(30));
        return this;
    }

    @Step(value = "Проверяем, что появился список")
    default SettingsPage isItemsComboBox(){
        activeContextMenu.shouldBe(Condition.visible);
        return this;
    }

    @Step(value = "Выбираем элемент {item} из списка")
    default SettingsPage selectItemComboBox(String item){
        isItemsComboBox();
        //Select item
        listItemsComboBox.findBy(Condition.text(item)).click();

        return this;
    }

    /**
     * Send value to fields of form
     * @param mapInputValue
     * @param form
     * @param button
     * @return SettingsPage
     */
    default SettingsPage setSettingsServer(Map<String, String> mapInputValue, String form, String button){
        clickButtonSettings(form, button);
        sendInputsForm(mapInputValue);
        return this;
    }
}
