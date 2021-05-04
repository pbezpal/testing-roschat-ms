package chat.ros.testing2.server.settings.services;

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
import static com.codeborne.selenide.Selenide.*;

public class IVRPage extends ServicesPage {

    private SelenideElement contentWrapper = $(".v-content__wrap");
    private SelenideElement inputUploadSoundFile = $("#upload");
    private SelenideElement modalWindowContent = getModalWindow().find(".modal-window__content");
    private SelenideElement inputUploadSoundFileByModalWindow = getModalWindow().find("#upload");
    private ElementsCollection buttonActionOfModalWindow = getModalWindow().$$(".modal-window__actions button div");
    private SelenideElement buttonClose = getModalWindow().find(".v-btn--flat");
    private ElementsCollection listContextMenu = activeContextMenu.$$(".list__tile__title");
    private SelenideElement buttonAddDTMF = $(".dtmf-header button");
    private SelenideElement buttonDeleteDTMF = $(".mx-0");
    private SelenideElement inputNumberDTMF = $(".flex.xs2");
    private SelenideElement inputActionDTMF = $(".flex.xs8");
    private SelenideElement audioPlayer = $(".modal-window__content audio");

    /******************** Работа с модальным окном ***********************/

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

    @Step(value = "Проверяем, отображается ли плеер")
    public boolean isAudioPlayer(){
        try {
            audioPlayer.shouldBe(visible);
        }catch (ElementNotFound e){
            return false;
        }

        return true;
    }

    @Step(value = "Нажимаем кнопку проигрывания звукового файла")
    public IVRPage clickPlayAudio(){
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("document.querySelector(\".modal-window__content audio\").play();");
        return this;
    }

    @Step(value = "Проверяем функцию проигрывания аудио")
    public Double isPlayAudioPlayer(){
        sleep(5000);
        return (Double) ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.querySelector(\".modal-window__content audio\").currentTime;");
    }

    @Step(value = "Проверяем функцию паузы в аудиоплеере")
    public boolean isPauseAudioPlayer(){
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("document.querySelector(\".modal-window__content audio\").pause();");
        return (boolean) ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.querySelector(\".modal-window__content audio\").paused;");
    }

    @Step(value = "Проверяем длину звукового файла")
    public String isDurationAudio(){
        return ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.querySelector(\".modal-window__content audio\").duration").toString();
    }

    @Step(value = "Проверяем функцию изменения уровня звука в аудиоплеере")
    public String isVolumeAudioPlayer(){
        boolean volume = true;
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("document.querySelector(\".modal-window__content audio\").volume = 0.5;");
            return  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.querySelector(\".modal-window__content audio\").volume;").toString();
    }

    @Step(value = "Проверяем функцию выключения звука")
    public boolean isMutedAudioPlayer(){
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("document.querySelector(\".modal-window__content audio\").muted = true;");
        return (boolean) ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.querySelector(\".modal-window__content audio\").muted;");
    }

    @Step(value = "Проверяем функцию включения звука")
    public boolean isOutMutedAudioPlayer(){
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("document.querySelector(\".modal-window__content audio\").muted = false;");
        return (boolean) ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.querySelector(\".modal-window__content audio\").muted;");
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
        uploadSoundFile(file);
        /*audioPlayer.shouldBe(visible);
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("document.querySelector(\".modal-window__content audio\").play();");
        sleep(3000);
        Double timePlay = (Double) ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.querySelector(\".modal-window__content audio\").currentTime;");
        if(timePlay > 0) System.out.println("Current time > 0");
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("document.querySelector(\".modal-window__content audio\").pause();");
        System.out.println(((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.querySelector(\".modal-window__content audio\").paused;"));
        System.out.println(((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.querySelector(\".modal-window__content audio\").duration"));
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("document.querySelector(\".modal-window__content audio\").volume = 0.3;");
        System.out.println(((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.querySelector(\".modal-window__content audio\").volume;"));
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("document.querySelector(\".modal-window__content audio\").muted = true;");
        System.out.println(((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.querySelector(\".modal-window__content audio\").muted;"));
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("document.querySelector(\".modal-window__content audio\").muted = false;");
        System.out.println( ! (boolean) ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.querySelector(\".modal-window__content audio\").muted;"));*/
        sendInputModalWindow(IVR_SOUND_FILES_FIELD_DESCRIPTION, description);
        return this;
    }

    /**
     * This method update sound file
     * @param file
     * @param description
     * @return
     */
    public IVRPage uploadSoundFileByModalWindow(String file, String ...description){
        uploadSoundFileByModalWindow(file);
        sendInputModalWindow(IVR_SOUND_FILES_FIELD_DESCRIPTION, description);
        return this;
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
                .sendInputModalWindow("Описание", IVR_MENU_DESCRIPTION + " " + name);
                sendInputModalWindow("Таймаут, сек", "30");
                clickSelectField("Действие при таймауте");
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

    public IVRPage editVoiceMenu(String name, String type, String sound, String numberOrMenu){

        if(type.equals("Перейти в меню")) sendModalWindowOfMenu(name, type, sound, numberOrMenu).clickButtonDeleteDTMF();
        else {
            sendModalWindowOfMenu(name, type, sound)
                    .clickButtonDeleteDTMF();

            if (type.equals("Звонок")) {
                return sendInputDialNumber(numberOrMenu, "Действие при таймауте"
                        , "Действие при неправильном наборе");
            }
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
