package chat.ros.testing2.data;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public interface MSGeneralElements {

    SelenideElement formChange = $("form.v-form");
    SelenideElement inputLogin = formChange.find("input[type='text']");
    SelenideElement inputPassword = formChange.find("input[type='password']");
    SelenideElement buttonSave = $("div.modal-wrapper button.v-btn.theme--light.primary");
    SelenideElement successCheckSettings = $("div.msg-wrapper.modal-wrapper i.v-icon.material-icons.theme--light.success--text");

    @Step(value = "Проверяем, что появилась форма редактирвоания")
    default boolean isFormChange(){
        try{
            formChange.shouldBe(Condition.visible);
        }catch (ElementNotFound element){
            return false;
        }

        return true;
    }

    @Step(value = "Вводим в поле {field} значение {value}")
    default void sendInputForm(String field, String value){
        $(By.xpath("//div[@class='modal-item__title']/h4[contains(text(),'" + field + "')]" +
                "//ancestor::li[@class='layout modal-item']//input")).sendKeys(Keys.CONTROL + "a");
        $(By.xpath("//div[@class='modal-item__title']/h4[contains(text(),'" + field + "')]" +
                "//ancestor::li[@class='layout modal-item']//input")).sendKeys(Keys.BACK_SPACE);
        $(By.xpath("//div[@class='modal-item__title']/h4[contains(text(),'" + field + "')]" +
                "//ancestor::li[@class='layout modal-item']//input")).sendKeys(value);
    }

    @Step(value = "Проверяем, активна ли кнопка Сохранить")
    default boolean isActiveButtonSave(){
        try{
            buttonSave.shouldBe(Condition.enabled);
        }catch (ElementNotFound element){
            return false;
        }

        return true;
    }

    @Step(value = "Нажимаем нопку Сохранить")
    default void clickButtonSave(){
        buttonSave.click();
    }

}
