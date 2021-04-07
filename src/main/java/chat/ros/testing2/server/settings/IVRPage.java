package chat.ros.testing2.server.settings;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class IVRPage implements SettingsPage {

    private SelenideElement inputUploadSoundFile = $("#upload");
    private SelenideElement modalWindow = $(".modal-window");
    private SelenideElement inputUploadSoundFileByModalWindow = modalWindow.find("#upload");
    private SelenideElement titleModalWindow = modalWindow.find("h2");
    private SelenideElement buttonPrimaryOfModalWindow = modalWindow.find(".primary");
    private SelenideElement buttonClose = modalWindow.find(".v-btn--flat");
    private ElementsCollection listContextMenu = activeContextMenu.$$(".list__tile__title");
    private SelenideElement buttonAddDTMF = $(".dtmf-header button");
    private ElementsCollection spansModalWindowOfVoiceMenu = modalWindow.$$("span");

    private SelenideElement getIVRSection(String title){
        return mainWrapper.$$("h2").findBy(text(title)).parent();
    }

    /******************* Работа с разделом Голосовое меню *******************/

    @Step(value = "Нажимаем кнопку Добавить в разделе {title}")
    public IVRPage clickButtonAdd(String title){
        getIVRSection(title).find(".action-bar button").click();
        return this;
    }

    @Step(value = "Проверяем, отображается {show} ли запись {item] в разделе {section}")
    public boolean isItemTable(String section, String item, boolean show){
        if(show){
            try{
                getIVRSection(section).$$("table td")
                        .findBy(text(item))
                        .shouldBe(visible);
            }catch (ElementNotFound e){
                return false;
            }
        }else{
            try{
                getIVRSection(section).$$("table td")
                        .findBy(text(item))
                        .shouldBe(not(visible));
            }catch (ElementShould e){
                return false;
            }
        }

        return true;
    }

    @Step(value = "Нажимаем кнопку {button} в разделе {section} у записи {item}")
    public IVRPage clickButtonTable(String section, String item, String button){
        getIVRSection(section)
                .$$("table td")
                .findBy(text(item))
                .parent()
                .$$(".layout i")
                .findBy(text(button))
                .click();
        return this;
    }

    /******************** Работа с модальным окном ***********************/

    @Step(value = "Проверяем, отображается ли заголовок {title} модального окна")
    public String isVisibleTitleModalWrapper(){
        try {
            titleModalWindow.shouldBe(visible);
        }catch (ElementNotFound e){
            return null;
        }

        return titleModalWindow.getText();
    }

    @Step(value = "Нажимаем кнопку Сохранить/Закрыть в модальном окне")
    public IVRPage clickButtonPrimaryOfModalWindow(){
        buttonPrimaryOfModalWindow.click();
        return this;
    }

    @Step(value = "Нажимаем кнопку Отменить в модальном окне")
    private IVRPage clickButtonCloseOfModalWindow(){
        buttonClose.click();
        return this;
    }

    /************** Загрузка музыкальных файлов **************************/

    @Step(value = "Добавляем звуковой файл {file}")
    private IVRPage uploadSoundFile(String file){
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].style.display = 'block';", inputUploadSoundFile);
        inputUploadSoundFile.sendKeys(file);
        return this;
    }

    @Step(value = "Заменяем звуковой файл на {file}")
    private IVRPage uploadSoundFileByModalWindow(String file){
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].style.display = 'block';", inputUploadSoundFileByModalWindow);
        inputUploadSoundFileByModalWindow.sendKeys(file);
        return this;
    }

    @Step(value = "Вводим {value} в поле {field}")
    private IVRPage sendInputModalWindow(String field, String ...value){
        if(value.length > 0) {
            SelenideElement input = $("input[aria-label='" + field + "']");
            input.click();
            input.sendKeys(Keys.CONTROL + "a");
            input.sendKeys(Keys.BACK_SPACE);
            input.sendKeys(value);
        }
        return this;
    }

    /********************** Работа с разделом Меню ****************************/

    @Step(value = "Нажимаем на поле {field} для вызова контекстного меню")
    private IVRPage clickSelectField(String field){
        SelenideElement selectField = $(".custom-select input[aria-label='" + field + "']");
        selectField.parent().click();
        return this;
    }

    @Step(value = "Выбираем пункт контекстного меню {item}")
    private IVRPage selectItemContextMenu(String item){
        if(item.equals("Перейти в меню")){
            listContextMenu.findBy(text(item)).hover();
            listItemsComboBox.findBy(text("Главное меню")).click();
        }else listContextMenu.findBy(text(item)).click();

        return this;
    }

    @Step(value = "Добавляем DTMF")
    private IVRPage clickButtonAddDTMF(){
        buttonAddDTMF.click();
        return this;
    }

    @Step(value = "Проверяем наличие иконки звукового файла в модальном окне")
    public boolean isIconSoundOfModalWindowVoiceMenu(){
        try{
            modalWindow.$(".blue-grey--text").shouldBe(visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем наличие иконки таймаута в модальном окне")
    public boolean isIconTimeOutOfModalWindowMenu(){
        try{
            modalWindow.$(".text--darken-3").shouldBe(visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем наличие иконки неправильного набора в модальном окне")
    public boolean isIconErrorOutlineOfModalWindowMenu(){
        try{
            modalWindow.$(".text-darken-1").shouldBe(visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем наличие иконки DTMF в модальном окне")
    public boolean isIconDTMFOfModalWindowMenu(){
        try{
            modalWindow.$(".indigo--text").shouldBe(visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, параметр {span} при просмотре меню")
    public String getTextSpanNameOfModalWindowMenu(String span){
        return modalWindow.$$("span").findBy(text(span))
                .parent()
                .find(".name")
                .text();
    }

    @Step(value = "Проверяем, номер DTMF при просмотре настроек меню")
    public String getNumberDTMFOfModalWindowMenu(){
        return modalWindow.$$("span").findBy(text("DTMF"))
                .parent()
                .parent()
                .find(".ivr-item__content span")
                .text();
    }

    @Step(value = "Проверяем, параметр DTMF при просмотре настроек меню")
    public String getTextDTMFOfModalWindowMenu(){
        return modalWindow.$$("span").findBy(text("DTMF"))
                .parent()
                .parent()
                .find(".name")
                .text();
    }

    /*@Step(value = "Проверяем, параметры настройки голосового меню")
    public boolean getTextSpansGoToMenuOfModalWindowVoiceMenu(String span) {
        try {

        }
        modalWindow.$$("span").findBy(text(span))
                .parent()
                .find(".name")
                .text();
    }*/

    /**
     * This method add sound file
     * @param file path of sound file
     * @param description
     * @return
     */
    public IVRPage uploadSoundFile(String file, String ...description){
        return uploadSoundFile(file).sendInputModalWindow(IVR_SOUND_FILES_FIELD_DESCRIPTION, description);
    }

    /**
     * This method update sound file
     * @param file
     * @param description
     * @return
     */
    public IVRPage uploadSoundFileByModalWindow(String file, String ...description){
        return uploadSoundFileByModalWindow(file).sendInputModalWindow(IVR_SOUND_FILES_FIELD_DESCRIPTION, description);
    }

    /**
     * This method send input of dial number
     * @param fields
     * @return
     */
    private IVRPage sendInputDialNumber(String ...fields){
        for (String field: fields) {
            SelenideElement inputDialNumber = $x("//*[@aria-label='" + field + "']//ancestor::div[@class='layout']")
                    .find("input[aria-label='Набираемый номер']");
            inputDialNumber.click();
            inputDialNumber.sendKeys(Keys.CONTROL + "a");
            inputDialNumber.sendKeys(Keys.BACK_SPACE);
            inputDialNumber.sendKeys("1000");
        }

        return this;
    }

    /**
     * This method send inputs modal window of voice menu
     * @param name item context menu
     * @param sound
     * @return
     */
    public IVRPage sendModalWindowByMenu(String name, String sound){
        selectItemComboBox(sound);

        sendInputModalWindow("Название", name)
                .sendInputModalWindow("Описание", IVR_MENU_DESCRIPTION + " " + name)
                .sendInputModalWindow("Таймаут, сек", "30")
                .clickSelectField("Действие при таймауте")
                .selectItemContextMenu(name)
                .clickSelectField("Действие при неправильном наборе")
                .selectItemContextMenu(name)
                .clickButtonAddDTMF()
                .clickSelectField("Действие")
                .selectItemContextMenu(name)
                .sendInputModalWindow("Набрано", "1000");

        if(name.equals("Звонок")){
            return sendInputDialNumber("Действие при таймауте"
                    ,"Действие при неправильном наборе"
                    ,"Действие");
        }

        return this;
    }

    public IVRPage createEntryPoint(String number, String aon, String item){
        sendInputModalWindow("Набираемый номер", number)
                .sendInputModalWindow("АОН", aon);
        selectItemComboBox(item);
        return this;
    }
}
