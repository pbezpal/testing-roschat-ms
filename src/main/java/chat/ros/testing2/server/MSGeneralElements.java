package chat.ros.testing2.server;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public interface MSGeneralElements {

    SelenideElement formChange = $("form.v-form");
    SelenideElement inputLogin = formChange.find("input[type='text']");
    SelenideElement inputPassword = formChange.find("input[type='password']");
    SelenideElement buttonSave = $(".modal-wrapper .primary");
    SelenideElement buttonClose = $(".modal-wrapper .secondary--text");
    SelenideElement successCheckSettings = $("div.msg-wrapper.modal-wrapper i.v-icon.material-icons.theme--light.success--text");
    ElementsCollection tdTableList = $$("table.v-datatable td");
    SelenideElement buttonAdd = $("div.action-bar button div");
    SelenideElement textWrong = $(".v-messages__message");
    String locatorInput = "//div[@class='modal-item__title']/h4[contains(text(),'%1$s')]//ancestor::" +
            "li[@class='layout modal-item']//input";

    @Step(value = "Проверяем, что появилась форма редактирвоания")
    default boolean isFormChange(){
        try{
            formChange.shouldBe(Condition.visible);
        }catch (ElementNotFound element){
            return false;
        }

        return true;
    }

    @Step(value = "Вводим в поле {input} значение {value}")
    default void sendInputForm(String input, String value){
        $x(String.format(locatorInput,input)).sendKeys(Keys.CONTROL + "a");
        $x(String.format(locatorInput,input)).sendKeys(Keys.BACK_SPACE);
        $x(String.format(locatorInput,input)).sendKeys(value);
    }

    @Step(value = "Заполняем поля формы")
    default void sendInputsForm(Map<String, String> mapInputValue){
        for(Map.Entry<String, String> entry : mapInputValue.entrySet()){
            String input = entry.getKey();
            String value = entry.getValue();
            $x(String.format(locatorInput,input)).sendKeys(Keys.CONTROL + "a");
            $x(String.format(locatorInput,input)).sendKeys(Keys.BACK_SPACE);
            $x(String.format(locatorInput,input)).sendKeys(value);
        }
    }

    @Step(value = "Нажимаем кнопку Добавить")
    default void clickButtonAdd(){
        buttonAdd.shouldBe(text("Добавить")).click();
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

    @Step(value = "Нажимаем кнопку Закрыть")
    default void clickButtonClose(){
        buttonClose.click();
    }

    @Step(value = "Проверяем, есть ли запись {text} в таблице контактов")
    default boolean isExistsTableText(String text){
        try{
            tdTableList.findBy(Condition.text(text)).shouldBe(Condition.visible);
        }catch (ElementNotFound elementNotFound){
            return false;
        }

        return true;
    }

    @Step(value = "Проверяем, отсутствует ли запись {text} в таблице")
    default boolean isNotExistsTableText(String user){
        try{
            tdTableList.findBy(Condition.text(user)).shouldBe(Condition.not(Condition.visible));
        }catch (ElementShould elementShould){
            return false;
        }

        return true;
    }

    @Step(value = "Проверяем, есть ли надпись об ошибке {text}")
    default String getTextWrong(){
        try{
            textWrong.shouldBe(visible);
        }catch (ElementNotFound e){
            return "Не найден элемент с текстом об ошибке";
        }

        return textWrong.text();
    }

}
