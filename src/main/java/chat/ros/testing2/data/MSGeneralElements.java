package chat.ros.testing2.data;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.gen5.api.Assertions.assertTrue;

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
        $x("//div[@class='modal-item__title']/h4[contains(text(),'" + field + "')]" +
                "//ancestor::li[@class='layout modal-item']//input").sendKeys(Keys.CONTROL + "a");
        $x("//div[@class='modal-item__title']/h4[contains(text(),'" + field + "')]" +
                "//ancestor::li[@class='layout modal-item']//input").sendKeys(Keys.BACK_SPACE);
        $x("//div[@class='modal-item__title']/h4[contains(text(),'" + field + "')]" +
                "//ancestor::li[@class='layout modal-item']//input").sendKeys(value);
    }

    default void sendInputsForm(Map<String, String> mapInputValue){
        for(Map.Entry<String, String> entry : mapInputValue.entrySet()){
            String input = entry.getKey();
            String value = entry.getValue();
            $x("//div[@class='modal-item__title']/h4[contains(text(),'" + input + "')]" +
                    "//ancestor::li[@class='layout modal-item']//input").sendKeys(Keys.CONTROL + "a");
            $x("//div[@class='modal-item__title']/h4[contains(text(),'" + input + "')]" +
                    "//ancestor::li[@class='layout modal-item']//input").sendKeys(Keys.BACK_SPACE);
            $x("//div[@class='modal-item__title']/h4[contains(text(),'" + input + "')]" +
                    "//ancestor::li[@class='layout modal-item']//input").sendKeys(value);
        }
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
        assertTrue(isActiveButtonSave(), "Невозможно сохранить настройки, кнопка 'Сохранить' не активна");
        buttonSave.click();
    }

}
