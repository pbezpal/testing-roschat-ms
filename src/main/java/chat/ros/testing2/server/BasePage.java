package chat.ros.testing2.server;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import com.codeborne.selenide.ex.ListSizeMismatch;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public interface BasePage {

    SelenideElement formChange = $("form.v-form");
    SelenideElement inputLogin = formChange.find("input[type='text']");
    SelenideElement inputPassword = formChange.find("input[type='password']");
    SelenideElement buttonSave = $(".modal-wrapper .primary");
    SelenideElement buttonClose = $(".modal-wrapper .secondary--text");
    SelenideElement modalSuccessCheckAction = $("div.msg-wrapper.modal-wrapper");
    ElementsCollection tdTableList = $$("table.v-datatable td");
    SelenideElement buttonAdd = $("div.action-bar button div");
    SelenideElement textWrong = $(".v-messages__message");
    ElementsCollection listLeftItemMenu = $$(".v-list--dense .v-list__tile__title");
    ElementsCollection listItemMenuSettings = $$(".v-tabs__item");
    SelenideElement divProgressBar = $("div.modal-progress");

    @Step(value = "Переходим в раздел {itemMenu} меню слева")
    static void clickItemMenu(String itemMenu){
        try {
            $$(listLeftItemMenu).shouldBe(CollectionCondition.sizeNotEqual(0));
        }catch (ListSizeMismatch l){
            try{
                $$(listLeftItemMenu).shouldBe(CollectionCondition.sizeNotEqual(0));
            }catch (ListSizeMismatch listSizeMismatch){
                listSizeMismatch.getStackTrace();
                return;
            }
        }
        $$(listLeftItemMenu).findBy(text(itemMenu)).click();
    }

    @Step(value = "Переходим в раздел {itemContainer}")
    static void clickItemSettings(String itemContainer){
        try {
            $$(listItemMenuSettings).shouldBe(CollectionCondition.sizeNotEqual(0));
        }catch (ListSizeMismatch l){
            try{
                $$(listItemMenuSettings).shouldBe(CollectionCondition.sizeNotEqual(0));
            }catch (ListSizeMismatch listSizeMismatch){
                listSizeMismatch.getStackTrace();
                return;
            }
        }
        $$(listItemMenuSettings).findBy(text(itemContainer)).click();
    }

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
        SelenideElement element = $$(".modal-item__title h4").findBy(text(input)).closest("li").find("input");
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(value);
    }

    @Step(value = "Кликаем на поле {input}")
    default void clickInputForm(String input){
        $$(".modal-item__title h4").findBy(text(input)).closest("li").find("input").click();
    }

    @Step(value = "Заполняем поля формы")
    default void sendInputsForm(Map<String, String> mapInputValue){
        for(Map.Entry<String, String> entry : mapInputValue.entrySet()){
            String input = entry.getKey();
            String value = entry.getValue();
            SelenideElement element = $$(".modal-item__title h4").findBy(text(input)).closest("li").find("input");
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.BACK_SPACE);
            element.sendKeys(value);
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

    @Step(value = "Проверяем есть ли {show} запись {text} в таблице")
    default boolean isExistsTableText(String text, boolean show){
        if(show) {
            try {
                tdTableList.findBy(Condition.text(text)).waitUntil(Condition.visible, 60000);
            } catch (ElementNotFound elementNotFound) {
                return false;
            }
        }else{
            try {
                tdTableList.findBy(Condition.text(text)).shouldBe(not(Condition.visible));
            } catch (ElementShould e) {
                return false;
            }
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

    @Step(value = "Проверяем, есть ли прогрессбар")
    default boolean isProgressBar(){
        try{
            divProgressBar.shouldNotBe(enabled);
        }catch (ElementShould elementShould){
            return true;
        }

        return false;
    }

}
