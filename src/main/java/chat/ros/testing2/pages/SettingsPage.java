package chat.ros.testing2.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class SettingsPage extends MonitoringPage {

    private String headSection = "Настройки";
    private String buttonSetting = "Настроить";
    private String buttonCheck = "Проверить";
    private String buttonRestartServices = "Перезапустить";

    private SelenideElement formSettings = $("form.v-form");
    private SelenideElement buttonSave = $("div.modal-wrapper button.v-btn.theme--light.primary");
    private SelenideElement buttonCloseForm = $("div.modal-wrapper button.v-btn.v-btn--flat.theme--light.secondary--text");
    private SelenideElement formConformActions = $("div.dialog-header h3");
    private SelenideElement divCheckSettings = $("div.msg-body h4");
    private SelenideElement buttonCloseCheckSettingsForm = $("div.msg-actions.actions-wrapper button.v-btn.v-btn--flat.theme--light");

    public static SettingsPage settingsPage = new SettingsPage();
    public static SettingsPage getInstance() { return settingsPage; }

    /*public void goSectionSettingsPage(){
        if( ! isSectionPage(headSection.toUpperCase())){
            clickItemMenu(headSection);
        }
    }*/

    public String getButtonSetting(){
        return buttonSetting;
    }

    public String getButtonRestartServices(){
        return buttonRestartServices;
    }

    public String getButtonCheck(){
        return buttonCheck;
    }

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
            $(By.xpath("//a[@class='v-tabs__item' and contains(text(), '" + itemContainer + "')]")).click();
            return this;
    }

    @Step(value = "Проверяем, что в поле {field} значение {value}")
    public static boolean isNotValueInField(String field, String value){
        $(By.xpath("//div[@class='block-content__item-name']/h4[contains(text(),'" + field + "')]" +
                "//ancestor::li//span[@class='v-chip__content']")).scrollIntoView(false);
        try{
            $(By.xpath("//div[@class='block-content__item-name']/h4[contains(text(),'" + field + "')]" +
                    "//ancestor::li//span[@class='v-chip__content']")).waitUntil(Condition.not(text(value)), 10000);
        }catch (ElementShould element){
            return false;
        }

        return true;
    }

    @Step(value = "Нажимаем кнопку {button}")
    public SettingsPage clickButtonSettings(String form, String button){
        $(By.xpath("//h2[text()='" + form + "']//ancestor::div[@class='block-wrapper']")).scrollIntoView(false);
        $(By.xpath("//h2[text()='" + form + "']//ancestor::div[@class='block-wrapper']" +
                "//div[text()='" + button + "']")).click();
        return this;
    }

    @Step(value = "Проверяем, что появилась форма настроек")
    public boolean isFormSettings(){
        try{
            formSettings.shouldBe(Condition.visible);
        }catch (ElementNotFound element){
            return false;
        }

        return true;
    }

    @Step(value = "Вводим в поле {field} значение {value}")
    public SettingsPage sendInputForm(String field, String value){
        $(By.xpath("//div[@class='modal-item__title']/h4[contains(text(),'" + field + "')]" +
                "//ancestor::li[@class='layout modal-item']//input")).sendKeys(Keys.CONTROL + "a");
        $(By.xpath("//div[@class='modal-item__title']/h4[contains(text(),'" + field + "')]" +
                "//ancestor::li[@class='layout modal-item']//input")).sendKeys(Keys.BACK_SPACE);
        $(By.xpath("//div[@class='modal-item__title']/h4[contains(text(),'" + field + "')]" +
                "//ancestor::li[@class='layout modal-item']//input")).sendKeys(value);

        return this;
    }

    @Step(value = "Проверяем, активна ли кнопка Сохранить")
    public boolean isActiveButtonSave(){
        try{
            buttonSave.shouldBe(Condition.enabled);
        }catch (ElementNotFound element){
            return false;
        }

        return true;
    }

    @Step(value = "Нажимаем нопку Сохранить")
    public SettingsPage clickButtonSave(){
        buttonSave.click();
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
    public SettingsPage clickButtonRestartServices(String button){
        $(By.xpath("//div[@class='actions-wrapper']" +
                "//div[@class='v-btn__content' and contains(text(), '" + button + "')]")).click();
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
    public boolean isCheckSettings(String value){
        try{
            divCheckSettings.text().contains(value);
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
}
