package chat.ros.testing2.server.settings;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class IVRPage implements SettingsPage {

    private SelenideElement contentWrapper = $(".v-content__wrap");
    private SelenideElement inputUploadSoundFile = $("#upload");
    private SelenideElement modalWindow = $(".modal-window");
    private SelenideElement modalWindowContent = modalWindow.find(".modal-window__content");
    private SelenideElement inputUploadSoundFileByModalWindow = modalWindow.find("#upload");
    private SelenideElement titleModalWindow = modalWindow.find("h2");
    private ElementsCollection buttonActionOfModalWindow = modalWindow.$$(".modal-window__actions button div");
    private SelenideElement buttonClose = modalWindow.find(".v-btn--flat");
    private ElementsCollection listContextMenu = activeContextMenu.$$(".list__tile__title");
    private SelenideElement buttonAddDTMF = $(".dtmf-header button");
    private ElementsCollection spansModalWindowOfVoiceMenu = modalWindow.$$("span");
    private SelenideElement buttonDeleteDTMF = $(".mx-0");
    private SelenideElement inputNumberDTMF = $(".flex.xs2");
    private SelenideElement inputActionDTMF = $(".flex.xs8");

    private SelenideElement getIVRSection(String title){
        return mainWrapper.$$("h2").findBy(text(title)).parent();
    }

    public SelenideElement getModalWindow() {
        return modalWindow;
    }

    /******************* Работа с разделом Голосовое меню *******************/

    @Step(value = "Нажимаем кнопку Добавить в разделе {title}")
    public IVRPage clickButtonAdd(String title){
        getIVRSection(title).scrollIntoView(false);
        getIVRSection(title).find(".action-bar button").click();
        return this;
    }

    @Step(value = "Проверяем, отображается {show} ли запись {item] в разделе {section}")
    public boolean isItemTable(String section, String item, boolean show){
        getIVRSection(section).$("table").scrollIntoView(false);
        if(show){
            try{
                getIVRSection(section)
                        .$("table")
                        .$(byText(item))
                        .shouldBe(visible);
            }catch (ElementNotFound e){
                return false;
            }
        }else{
            try{
                getIVRSection(section).
                        $("table")
                        .$(byText(item))
                        .shouldBe(not(visible));
            }catch (ElementShould e){
                return false;
            }
        }

        return true;
    }

    @Step(value = "Нажимаем кнопку {button} в разделе {section} у записи {item}")
    public IVRPage clickButtonTable(String section, String item, String button){
        getIVRSection(section).scrollIntoView(false);
        getIVRSection(section)
                .$("table")
                .$(byText(item))
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

    @Step(value = "Прокручиваем контекст модального")
    public IVRPage scrollContentModalWindow(boolean scroll){
        modalWindowContent.scrollIntoView(false);
        return this;
    }

    @Step(value = "Нажимаем кнопку {button} в модальном окне")
    public IVRPage clickActionButtonOfModalWindow(String button){
        buttonActionOfModalWindow.findBy(text(button)).click();
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
    private IVRPage selectItemContextMenu(String item, String ...menu){
        if(item.equals("Перейти в меню")){
            listContextMenu.findBy(text(item)).hover();
            listItemsComboBox.findBy(text(menu[0])).click();
        }else listContextMenu.findBy(text(item)).click();

        return this;
    }

    @Step(value = "Добавляем DTMF")
    private IVRPage clickButtonAddDTMF(){
        buttonAddDTMF.click();
        return this;
    }

    @Step(value = "Проверяем наличие иконки звукового файла в модальном окне")
    public boolean isIconSoundOfModalWindowVoiceMenu(SelenideElement firstElement){
        try{
            firstElement.$(".blue-grey--text").shouldBe(visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем наличие иконки таймаута в модальном окне")
    public boolean isIconTimeOutOfModalWindowMenu(SelenideElement firstElement){
        try{
            firstElement.$(".text--darken-3").shouldBe(visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем наличие иконки неправильного набора в модальном окне")
    public boolean isIconErrorOutlineOfModalWindowMenu(SelenideElement firstElement){
        try{
            firstElement.$(".text-darken-1").shouldBe(visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем наличие иконки DTMF в модальном окне")
    public boolean isIconDTMFOfModalWindowMenu(SelenideElement firstElement){
        try{
            firstElement.$(".indigo--text").shouldBe(visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, параметр {span} при просмотре меню")
    public String getTextSpanNameOfModalWindowMenu(SelenideElement firstElement, String span){
        return firstElement
                .$$("span")
                .findBy(text(span))
                .parent()
                .find(".name")
                .text();
    }

    /***************** Проверка элементов при Переходе в меню ************************/

    /***
     * This method returns parent element go to menu for span field
     * @param span field name
     * @return
     */
    public SelenideElement getElementMenuOfGoToAction(String span){
        return getElementGoToAction(span)
                .parent()
                .parent()
                .find(".ivr-item__sub");
    }

    public boolean isElementMenuOfGoToAction(String span, boolean show){
        if(show) {
            try {
                getElementMenuOfGoToAction(span).shouldBe(visible);
            } catch (ElementNotFound e) {
                return false;
            }
        }else{
            try {
                getElementMenuOfGoToAction(span).shouldBe(not(visible));
            } catch (ElementShould e) {
                return false;
            }
        }

        return true;
    }

    /***
     * This method returns parent element go to menu of DTMF section
     * @return
     */
    public SelenideElement getElementMenuOfGoToActionWithDTMF(){
        return getGoToActionDTMF()
                .parent()
                .parent()
                .find(".ivr-item__sub");
    }

    public boolean isElementMenuOfGoToActionWithDTMF(boolean show){
        if(show) {
            try {
                getElementMenuOfGoToActionWithDTMF().shouldBe(visible);
            } catch (ElementNotFound e) {
                return false;
            }
        }else{
            try {
                getElementMenuOfGoToActionWithDTMF().shouldBe(not(visible));
            } catch (ElementShould e) {
                return false;
            }
        }

        return true;
    }

    private SelenideElement getElementGoToAction(String span){
        return $x("//span[contains(text(),'" + span + "')]//following-sibling::*[@class='go-to-action']");
    }

    @Step(value = "Проверяем, наличие ссылки у элемента {span}")
    public boolean isGoToActionOfSpanOfModalWindow(String span){
        try{
            getElementGoToAction(span).shouldBe(visible);
        }catch (ElementNotFound e){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, отображается ли первая часть текста ссылки у элемента {span}")
    public String getFirstTextGoToActionOfSpanOfModalWindow(String span){
        return getElementGoToAction(span)
                .find("span:not(.name)")
                .text();
    }

    @Step(value = "Проверяем, отображается ли вторая часть текста ссылки у элемента {span}")
    public String getSecondTextGoToActionOfSpanOfModalWindow(String span){
        return getElementGoToAction(span)
                .find(".name")
                .text();
    }

    @Step(value = "Кликаем по ссылки у элемента {span}")
    public IVRPage clickGoToActionOfModalWindow(String span){
        getElementGoToAction(span).click();
        return this;
    }

    /****************** DTMF **********************/

    public SelenideElement getGoToActionDTMF(){
        return $x("//span[contains(text(),'DTMF')]//parent::div//parent::div//*[@class='go-to-action']");
    }

    private SelenideElement getContentDTMP(SelenideElement firstElement){
        return firstElement.$$("span").findBy(text("DTMF"))
                .parent()
                .parent()
                .find(".ivr-item__content");
    }

    @Step(value = "Проверяем, номер DTMF при просмотре настроек меню")
    public String getNumberDTMFOfModalWindowMenu(SelenideElement firstElement){
        return getContentDTMP(firstElement)
                .find("span")
                .text();
    }

    @Step(value = "Проверяем, параметр DTMF при просмотре настроек меню")
    public String getTextDTMFOfModalWindowMenu(SelenideElement firstElement){
        return firstElement.$$("span").findBy(text("DTMF"))
                .parent()
                .parent()
                .find(".name")
                .text();
    }

    @Step(value = "Проверяем, текст первой части текста ссылки параметра DTMF при просмотре настроек Перехода в меню")
    public String getFirstTextGoToActionOfDTMFOfModalWindowMenu(){
        return getGoToActionDTMF()
                .find("span:not(.name)")
                .text();
    }

    @Step(value = "Проверяем, текст первой части текста ссылки параметра DTMF при просмотре настроек Перехода в меню")
    public String getSecondTextGoToActionOfDTMFOfModalWindowMenu(){
        return getGoToActionDTMF()
                .find(".name")
                .text();
    }

    @Step(value = "Нажимаем по ссылке у элемента DTMF")
    public IVRPage clickGoToActionOfDTMF(){
        getGoToActionDTMF().click();
        return this;
    }

    @Step(value = "Удаляем DTMF в меню")
    public IVRPage clickButtonDeleteDTMF(){
        buttonDeleteDTMF.click();
        return this;
    }

    @Step(value = "Проверяем отображается ли {show} поле Набрано в DTMF")
    public boolean isInputNumberDTMF(boolean show){
        if(show){
            try {
                inputNumberDTMF.shouldBe(visible);
            }catch (ElementNotFound e){
                return false;
            }
        }else{
            try {
                inputNumberDTMF.shouldBe(not(visible));
            }catch (ElementShould e){
                return false;
            }
        }

        return true;
    }

    @Step(value = "Проверяем отображается ли {show} поле Действие в DTMF")
    public boolean isInputActionDTMF(boolean show){
        if(show){
            try {
                inputActionDTMF.shouldBe(visible);
            }catch (ElementNotFound e){
                return false;
            }
        }else{
            try {
                inputActionDTMF.shouldBe(not(visible));
            }catch (ElementShould e){
                return false;
            }
        }

        return true;
    }

    @Step(value = "Проверяем, отображается ли звуковой файл в настройке голосового меню")
    public boolean isSoundFileWithCallMenu(boolean show) {
        if (show) {
            try {
                $(".v-select__selection--comma").shouldBe(visible);
            } catch (ElementNotFound e) {
                return false;
            }
        } else {
            try {
                $(".v-select__selection--comma").shouldBe(not(visible));
            } catch (ElementShould e) {
                return false;
            }
        }

        return true;
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
        contentWrapper.scrollIntoView(false);
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
    private IVRPage sendInputDialNumber(String number, String ...fields){
        for (String field: fields) {
            SelenideElement inputDialNumber = $x("//*[@aria-label='" + field + "']//ancestor::div[@class='layout']")
                    .find("input[aria-label='Набираемый номер']");
            inputDialNumber.click();
            inputDialNumber.sendKeys(Keys.CONTROL + "a");
            inputDialNumber.sendKeys(Keys.BACK_SPACE);
            inputDialNumber.sendKeys(number);
        }

        return this;
    }

    public IVRPage sendModalWindowOfMenu(String name, String type, String sound, String ...menu){
        selectItemComboBox(sound);

        sendInputModalWindow("Название", name)
                .sendInputModalWindow("Описание", IVR_MENU_DESCRIPTION + " " + name)
                .sendInputModalWindow("Таймаут, сек", "30")
                .clickSelectField("Действие при таймауте");
                if(type.equals("Перейти в меню")) selectItemContextMenu("Перейти в меню", menu[0]);
                else selectItemContextMenu(type);
                clickSelectField("Действие при неправильном наборе");
                if(type.equals("Перейти в меню")) selectItemContextMenu("Перейти в меню", menu[0]);
                else selectItemContextMenu(type);

        return this;
    }

    /**
     * This method send inputs modal window of voice menu
     * @param name item context menu
     * @param sound
     * @return
     */
    public IVRPage addVoiceMenu(String name, String type, String sound, String number, String ...menu){

        sendModalWindowOfMenu(name, type, sound, menu)
                .clickButtonAddDTMF()
                .clickSelectField("Действие");
                if(type.equals("Перейти в меню")) selectItemContextMenu("Перейти в меню", menu[0]);
                else selectItemContextMenu(type);
                sendInputModalWindow("Набрано", number);

        if(type.equals("Звонок")){
            return sendInputDialNumber(number, "Действие при таймауте"
                    ,"Действие при неправильном наборе"
                    ,"Действие");
        }

        return this;
    }

    public IVRPage editVoiceMenu(String name, String type, String sound, String number){

        sendModalWindowOfMenu(name, type, sound)
                .clickButtonDeleteDTMF();

        if(type.equals("Звонок")){
            return sendInputDialNumber(number, "Действие при таймауте"
                    ,"Действие при неправильном наборе");
        }

        return this;
    }

    public IVRPage sendModalWindowOfEntryPoint(String number, String aon, String type){
        sendInputModalWindow("Набираемый номер", number)
                .sendInputModalWindow("АОН", aon);
        selectItemComboBox(type);
        return this;
    }
}
