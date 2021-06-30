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

import java.time.Duration;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public interface BasePage {

    SelenideElement formChange = $("form.v-form");
    SelenideElement inputLogin = formChange.find("input[type='text']");
    SelenideElement inputPassword = formChange.find("input[type='password']");
    SelenideElement buttonSave = $(".modal-wrapper .primary");
    SelenideElement buttonClose = $(".modal-wrapper .secondary--text");
    ElementsCollection tdTableList = $$("table.v-datatable td");
    SelenideElement buttonAdd = $(".action-bar button.primary");
    SelenideElement textWrong = $(".v-messages__message");
    ElementsCollection listLeftItemMenu = $$(".v-list--dense .v-list__tile__title");
    ElementsCollection listItemMenuSettings = $$(".v-tabs__item");
    SelenideElement divProgressBar = $(".modal-progress");
    SelenideElement modalWindow = $(".modal-wrapper");
    SelenideElement dialogWrapper = $(".dialog-wrapper");

    default BasePage isModalWindow(boolean show){
        if(show) modalWindow.shouldBe(visible);
        else modalWindow.shouldNotBe(visible);
        return this;
    }

    default BasePage isVisibleElement(SelenideElement element, boolean show){
        if(show) element.shouldBe(visible);
        else element.shouldNotBe(visible);
        return this;
    }

    default BasePage isVisibleElement(ElementsCollection elements, String text, boolean show){
        if(show) elements.findBy(text(text)).shouldBe(visible);
        else elements.findBy(text(text)).shouldNotBe(visible);
        return this;
    }

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
    default BasePage isFormChange(){
        formChange.shouldBe(Condition.visible);
        return this;
    }

    @Step(value = "Вводим в поле {input} значение {value}")
    default void sendInputForm(String input, String value){
        SelenideElement element = $$(".modal-item__title h4")
                .findBy(text(input))
                .parent()
                .parent()
                .find("input");
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(value);
    }

    @Step(value = "Кликаем на поле {input}")
    default void clickInputForm(String input){
        $$(".modal-item__title h4").findBy(text(input)).
                closest("div").parent().find("input").click();
    }

    @Step(value = "Заполняем поля формы")
    default void sendInputsForm(Map<String, String> mapInputValue){
        for(Map.Entry<String, String> entry : mapInputValue.entrySet()){
            String input = entry.getKey();
            String value = entry.getValue();
            SelenideElement element = $$(".modal-item__title h4").findBy(text(input)).
                    closest("div").parent().find("input");
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.BACK_SPACE);
            if( ! value.equals("")) element.sendKeys(value);
        }
    }

    @Step(value = "Нажимаем кнопку Добавить")
    default void clickButtonAdd(){
        buttonAdd.click();
    }

    @Step(value = "Проверяем, активна ли кнопка Сохранить")
    default BasePage isActiveButtonSave(){
        buttonSave.shouldBe(Condition.enabled);
        return this;
    }

    @Step(value = "Нажимаем нопку Сохранить/Восстановить")
    default BasePage clickButtonSave(){
        buttonSave.click();
        return this;
    }

    @Step(value = "Нажимаем кнопку Закрыть")
    default BasePage clickButtonClose(){
        buttonClose.click();
        return this;
    }

    @Step(value = "Проверяем есть ли {show} запись {text} в таблице")
    default BasePage isExistsTableText(String text, boolean show){
        if(show) tdTableList.findBy(Condition.text(text)).shouldBe(Condition.visible, Duration.ofMinutes(1));
        else tdTableList.findBy(Condition.text(text)).shouldNotBe(Condition.visible);
        return this;
    }

    @Step(value = "Проверяем, отсутствует ли запись {text} в таблице")
    default boolean isNotExistsTableText(String user){
        try{
            tdTableList.findBy(Condition.text(user)).shouldNotBe(Condition.visible);
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
            divProgressBar.shouldBe(not(enabled));
        }catch (ElementShould elementShould){
            return true;
        }

        return false;
    }

    @Step(value = "Проверяем, виден ли элемент {element}")
    default boolean isShowElement(SelenideElement element, boolean show){
        if(show){
            try{
                element.shouldBe(visible, Duration.ofSeconds(15));
            }catch (ElementNotFound error){
                return false;
            }
        }else{
            try{
                element.shouldBe(not(visible));
            }catch (ElementShould error){
                return false;
            }
        }

        return true;
    }

    @Step(value = "Проверяем, отображается ли иконка {locator}")
    default boolean isShowIconModalWindow(String locator){
        try{
            modalWindow.find(locator).shouldBe(visible);
        }catch (ElementNotFound error){
            return false;
        }

        return true;
    }

    @Step(value = "Проверяем, отображается ли текст модального окна")
    default String getTextModalWindow(String locator){
        return modalWindow.find(locator).text();
    }

}
