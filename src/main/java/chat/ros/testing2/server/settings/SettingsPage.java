package chat.ros.testing2.server.settings;

import chat.ros.testing2.data.MSGeneralElements;
import chat.ros.testing2.server.MonitoringPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.gen5.api.Assertions.assertTrue;

public class SettingsPage extends MonitoringPage implements MSGeneralElements {

    private SelenideElement buttonCloseForm = $("div.modal-wrapper button.v-btn.v-btn--flat.theme--light.secondary--text");
    private SelenideElement formConformActions = $("div.dialog-header h3");
    private SelenideElement divCheckSettings = $("div.msg-body h4");
    private SelenideElement buttonCloseCheckSettingsForm = $("div.msg-actions.actions-wrapper button.v-btn.v-btn--flat.theme--light");
    private SelenideElement elementLoaderSettings = $("div.loader-wrapper i.loader");

    public SettingsPage () {}

    @Step(value = "Проверяем, находимся ли мы в разделе {itemContainer}")
    public boolean isNotSectionSettings(String itemContainer){
        try{
            $("a.v-tabs__item.v-tabs__item--active").waitUntil(Condition.not(text(itemContainer)), 10000);
        }catch (ElementShould element){
            return false;
        }

        return true;
    }

    @Step(value = "Переходим в раздел {itemContainer}")
    public SettingsPage clickItemSettings(String itemContainer){
            $x("//a[@class='v-tabs__item' and contains(text(), '" + itemContainer + "')]").click();
            return this;
    }

    @Step(value = "Проверяем, что в поле {field} значение {value}")
    public static boolean isNotValueInField(String field, String value){
        $x("//div[@class='block-content__item-name']/h4[contains(text(),'" + field + "')]" +
                "//ancestor::li//span[@class='v-chip__content']").scrollIntoView(false);
        try{
            $x("//div[@class='block-content__item-name']/h4[contains(text(),'" + field + "')]" +
                    "//ancestor::li//span[@class='v-chip__content']").waitUntil(Condition.not(text(value)), 10000);
        }catch (ElementShould element){
            return false;
        }

        return true;
    }

    @Step(value = "Нажимаем кнопку {button} в разделе {form}")
    public SettingsPage clickButtonSettings(String form, String button){
        $x("//h2[text()='" + form + "']//ancestor::div[@class='block-wrapper']").scrollIntoView(false);
        $x("//h2[text()='" + form + "']//ancestor::div[@class='block-wrapper']" +
                "//div[text()='" + button + "']").click();
        return this;
    }

    @Step(value = "Нажимаем кнопку Закрыть")
    public SettingsPage clickButtonCloseForm(){
        buttonCloseForm.click();
        return this;
    }

    @Step(value = "Проверяем, что появилась форма 'Подтвердите свои действия'")
    public boolean isFormConfirmActions(){
        try{
            formConformActions.shouldHave(text("Подтвердите свои действия"), Condition.visible);
        }catch (ElementNotFound element){
            return false;
        }

        return true;
    }

    @Step(value = "Нажимаем кнопку {button} в форме 'Подвердите свои действия'")
    public SettingsPage clickButtonConfirmAction(String button){
        assertTrue(isFormConfirmActions(), "Отсутствует форма для подтверждения действий");
        $x("//div[@class='actions-wrapper']" +
                "//div[@class='v-btn__content' and contains(text(), '" + button + "')]").click();
        return this;
    }

    @Step(value = "Проверяем, появилась ли форма Проверки настроек")
    public boolean isFormCheckSettings(){
        try{
            divCheckSettings.shouldBe(Condition.visible);
        }catch (ElementNotFound element){
            return false;
        }

        return true;
    }

    @Step(value = "Проверяем, что проверка настроек прошла успешно")
    public boolean isCheckSettings(){
        try{
            successCheckSettings.waitUntil(visible, 15000);
        }catch (ElementNotFound element){
            return false;
        }

        return true;
    }

    @Step(value = "Нажимаем кнопку Закрыть на форме проверки настроек")
    public SettingsPage clickButtonCloseCheckSettingsForm(){
        buttonCloseCheckSettingsForm.click();
        return this;
    }

    @Step(value = "Ждём, когда пропадёт элемент загрузки настроек")
    public boolean isNotShowLoaderSettings(){
        try{
            elementLoaderSettings.waitUntil(not(visible), 10000);
        }catch (ElementShould e){
            return false;
        }

        return true;
    }

    public SettingsPage setSettingsServer(Map<String, String> mapInputValue, String form, String button){
        clickButtonSettings(form, button);
        sendInputsForm(mapInputValue);
        return this;
    }

    public SettingsPage checkSettingsServer(String form, String button){
        //Нажимаем кнопку Проверить
        clickButtonSettings(form, button);
        //Проверяем, появилась ли форма проверки настроек
        assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        //Проверяем, что настройки сервера корректны
        assertTrue(isCheckSettings(), "Настройки сервера некорректны");
        //Нажимаем кнопку закрыть
        clickButtonCloseCheckSettingsForm();
        return this;
    }
}
