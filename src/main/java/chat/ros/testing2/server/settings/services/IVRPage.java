package chat.ros.testing2.server.settings.services;

import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class IVRPage extends ServicesPage {

    private SelenideElement inputUploadSoundFile = $("#upload");
    private SelenideElement modalWindowContent = getModalWindow().find(".modal-window__content");
    private SelenideElement inputUploadSoundFileByModalWindow = getModalWindow().find("#upload");
    private SelenideElement buttonClose = getModalWindow().find(".v-btn--flat");
    private ElementsCollection listContextMenu = activeContextMenu.$$(".list__tile__title");
    private SelenideElement buttonAddDTMF = $(".dtmf-header button");
    private SelenideElement buttonDeleteDTMF = $(".mx-0");
    private SelenideElement inputNumberDTMF = $(".flex.xs2");
    private SelenideElement inputActionDTMF = $(".flex.xs8");
    private SelenideElement audioPlayer = $(".modal-window__content audio");
    private SelenideElement buttonDownloadFile = getModalWindow().$(".melody-buttons").find("button[title='Скачать']");

    //Elements for entry point
    ElementsCollection labelsComboBoxWindowModal = getModalWindow().findAll("div[role='combobox'] label");

    //Elements for schedule
    private SelenideElement scheduleForm = $(".schedule");
    private SelenideElement scheduleColumnLeft = $(".schedule-column.left");
    private SelenideElement titleScheduleColumnLeft = scheduleColumnLeft.find("h4");
    private SelenideElement buttonAddSchedule = scheduleColumnLeft.find(".column-header button");
    private ElementsCollection scheduleItemsText = scheduleColumnLeft.findAll(".schedule-items .schedule-item__text");

    private SelenideElement scheduleColumnRight = $(".schedule-column.right");
    private SelenideElement titleScheduleColumnRight = scheduleColumnRight.find("h4");
    private SelenideElement buttonAddRules = scheduleColumnRight.find(".column-header button");
    private ElementsCollection weekDays = getModalWindow().findAll(".week-days span");
    private ElementsCollection comboBoxWeekDays = getModalWindow().findAll(".inputs .v-input--selection-controls__ripple");
    private ElementsCollection inputComboBoxWeekDays = getModalWindow().findAll(".inputs input");
    private SelenideElement datePicker = $(".v-picker__body");
    private SelenideElement buttonNextMonth = datePicker.find(".mdi-chevron-right");
    private ElementsCollection dateList = datePicker.findAll("td");
    private SelenideElement tableRulesOfSchedule = $(".schedule-column.right table");

    /******************** Работа с модальным окном ***********************/

    @Step(value = "Прокручиваем контекст модального окна")
    public IVRPage scrollContentModalWindow(boolean scroll){
        modalWindowContent.scrollIntoView(false);
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
        inputUploadSoundFile.uploadFile(new File(file));
        return this;
    }

    @Step(value = "Заменяем звуковой файл на {file}")
    private IVRPage uploadSoundFileByModalWindow(String file){
        inputUploadSoundFileByModalWindow.uploadFile(new File(file));
        return this;
    }

    @Step(value = "Проверяем, отображается ли плеер")
    public IVRPage isAudioPlayer(){
        audioPlayer.shouldBe(visible);
        return this;
    }

    @Step(value = "Нажимаем кнопку проигрывания звукового файла")
    public IVRPage clickPlayAudio(){
        Selenide.executeJavaScript("document.querySelector(\".modal-window__content audio\").play();");
        return this;
    }

    @Step(value = "Проверяем функцию проигрывания аудио")
    public Double isPlayAudioPlayer(){
        sleep(5000);
        return (Double) Selenide.executeJavaScript("return document.querySelector(\".modal-window__content audio\").currentTime;");
    }

    @Step(value = "Проверяем функцию паузы в аудиоплеере")
    public boolean isPauseAudioPlayer(){
        Selenide.executeJavaScript("document.querySelector(\".modal-window__content audio\").pause();");
        return (boolean) Selenide.executeJavaScript("return document.querySelector(\".modal-window__content audio\").paused;");
    }

    @Step(value = "Проверяем длину звукового файла")
    public String isDurationAudio(){
        return Selenide.executeJavaScript("return document.querySelector(\".modal-window__content audio\").duration").toString();
    }

    @Step(value = "Проверяем функцию изменения уровня звука в аудиоплеере")
    public String isVolumeAudioPlayer(){
        Selenide.executeJavaScript("document.querySelector(\".modal-window__content audio\").volume = 0.5;");
            return Selenide.executeJavaScript("return document.querySelector(\".modal-window__content audio\").volume;").toString();
    }

    @Step(value = "Проверяем функцию выключения звука")
    public boolean isMutedAudioPlayer(){
        Selenide.executeJavaScript("document.querySelector(\".modal-window__content audio\").muted = true;");
        return (boolean) Selenide.executeJavaScript("return document.querySelector(\".modal-window__content audio\").muted;");
    }

    @Step(value = "Проверяем функцию включения звука")
    public boolean isOutMutedAudioPlayer(){
        Selenide.executeJavaScript("document.querySelector(\".modal-window__content audio\").muted = false;");
        return (boolean) Selenide.executeJavaScript("return document.querySelector(\".modal-window__content audio\").muted;");
    }

    @Step(value = "Скачиваем звуковой файл")
    public File downloadSoundFile() {
        File soundFile = null;
        try {
            soundFile = buttonDownloadFile.download();
        } catch (FileNotFoundException e) {
            return null;
        }

        return soundFile;
    }

    /********************** Работа с разделом Меню ****************************/

    @Step(value = "Выбираем звуковой файл {sound} в разделе {section}")
    private IVRPage selectSoundFile(String section, String sound){
        $$("h4")
                .findBy(text(section))
                .parent()
                .find(".mdi-menu-down")
                .click();
        listItemsComboBox.findBy(text(sound)).click();
        return this;
    }

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
    public IVRPage isIconSoundOfModalWindowVoiceMenu(SelenideElement firstElement){
        firstElement.$(".blue-grey--text").shouldBe(visible);
        return this;
    }

    @Step(value = "Проверяем наличие иконки таймаута в модальном окне")
    public IVRPage isIconTimeOutOfModalWindowMenu(SelenideElement firstElement){
        firstElement.find(".text--darken-3").shouldBe(visible);
        return this;
    }

    @Step(value = "Проверяем наличие иконки неправильного набора в модальном окне")
    public IVRPage isIconErrorOutlineOfModalWindowMenu(SelenideElement firstElement){
        firstElement.find(".text-darken-1").shouldBe(visible);
        return this;
    }

    @Step(value = "Проверяем наличие иконки звукового файла у {typeAction}")
    public IVRPage isIconMusicFileOfAction(SelenideElement firstElement, String action){
        firstElement.findAll("span").findBy(text(action)).find(".mdi-music-note-eighth").shouldBe(visible);
        return this;
    }

    @Step(value = "Проверяем наличие иконки DTMF в модальном окне")
    public IVRPage isIconDTMFOfModalWindowMenu(SelenideElement firstElement){
        firstElement.$(".indigo--text").shouldBe(visible);
        return this;
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

    @Step(value = "Проверяем открылась ли {show} ссылка у {span}")
    public IVRPage isElementMenuOfGoToAction(String span, boolean show){
        if(show) getElementMenuOfGoToAction(span).shouldBe(visible);
        else getElementMenuOfGoToAction(span).shouldNotBe(visible);
        return this;
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

    @Step(value = "Проверяем открылась ли {show} ссылка у DTMF")
    public IVRPage isElementMenuOfGoToActionWithDTMF(boolean show){
        if(show) getElementMenuOfGoToActionWithDTMF().shouldBe(visible);
        else getElementMenuOfGoToActionWithDTMF().shouldBe(not(visible));
        return this;
    }

    private SelenideElement getElementGoToAction(String span){
        //return getModalWindow().findAll("span").findBy(text(span)).closest("div").find(".go-to-action");
        return $x("//span[contains(text(),'" + span + "')]//following-sibling::*[@class='go-to-action']");
    }

    private SelenideElement getElementWithoutGoToAction(String span){
        return getModalWindow()
                .findAll("span")
                .findBy(text(span))
                .parent();
    }

    @Step(value = "Проверяем, наличие ссылки {show} у элемента {span}")
    public IVRPage isGoToActionOfSpanOfModalWindow(String span, boolean show){
        if(show) getElementGoToAction(span).shouldBe(visible);
        else getElementGoToAction(span).shouldNotBe((visible));
        return this;
    }

    @Step(value = "Проверяем, отображается ли первая часть текста ссылки у элемента {span}")
    public String getFirstTextGoToActionOfSpanOfModalWindow(String span, boolean action){
        if (action)
            return getElementGoToAction(span)
                    .find("span:not(.name)")
                    .text();
        else
            return getElementWithoutGoToAction(span)
                    .findAll("span")
                    .findBy(text("Перейти в меню"))
                    .text();
    }

    @Step(value = "Проверяем, отображается ли вторая часть текста ссылки у элемента {span}")
    public String getSecondTextGoToActionOfSpanOfModalWindow(String span, boolean action){
        if (action)
            return getElementGoToAction(span)
                    .find(".name")
                    .text();
        else
            return getElementWithoutGoToAction(span)
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
    public IVRPage isInputNumberDTMF(boolean show){
        if(show) inputNumberDTMF.shouldBe(visible);
        else inputNumberDTMF.shouldNotBe(visible);

        return this;
    }

    @Step(value = "Проверяем отображается ли {show} поле Действие в DTMF")
    public IVRPage isInputActionDTMF(boolean show){
        if(show) inputActionDTMF.shouldBe(visible);
        else inputActionDTMF.shouldNotBe(visible);

        return this;
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

    /**************************** Расписание *****************************/

    @Step(value = "Проверяем заголовок в столбце Расписание")
    public IVRPage isTitleSchedule(){
        titleScheduleColumnLeft.shouldHave(text("Расписание"));
        return this;
    }

    @Step(value = "Нажимаем кнопку добавить Расписание")
    public IVRPage clickButtonAddSchedule(){
        scheduleForm.scrollIntoView(false);
        buttonAddSchedule.click();
        return this;
    }

    @Step(value = "Проверяем, отображается ли {show} расписание {schedule}")
    public IVRPage isVisibleSchedule(String schedule, boolean show){
        scheduleForm.scrollIntoView(false);
        if(show) scheduleItemsText.findBy(text(schedule)).shouldBe(visible);
        else scheduleItemsText.findBy(text(schedule)).shouldNotBe(visible);
        return this;
    }

    @Step(value = "Выбираем распиание {schedule}")
    public IVRPage clickSchedule(String schedule){
        scheduleForm.scrollIntoView(false);
        scheduleItemsText.findBy(text(schedule)).click();
        return this;
    }

    @Step(value = "Надимаем кнопку {button} у раписания {schedule}")
    public IVRPage clickButtonActionSchedule(String schedule, String button){
        scheduleForm.scrollIntoView(false);
        scheduleItemsText.findBy(text(schedule)).parent().findAll("i").findBy(text(button)).click();
        return this;
    }

    @Step(value = "Проверяем заголовок в столбце Правила")
    public IVRPage isTitleRules(){
        scheduleForm.scrollIntoView(false);
        titleScheduleColumnRight.shouldHave(text("Правила"));
        return this;
    }

    @Step(value = "Нажимаем кнопку добавить Правила")
    public IVRPage clickButtonAddRules(){
        scheduleForm.scrollIntoView(false);
        buttonAddRules.click();
        return this;
    }

    @Step(value = "Выбираем Тип {type}")
    public IVRPage selectTypeDate(String type){
        $$("label").findBy(text("Тип")).parent().find(".v-select__selections").click();
        $$(".v-list__tile__title").findBy(text(type)).click();
        return this;
    }

    private Date datePlusDays(int days){
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    @Step(value = "Выбираем {typeDate}")
    public String getDateRules(String typeDate){
        getModalWindow().find("input[aria-label='" + typeDate + "']").click();
        datePicker.shouldBe(visible);
        DateFormat onlyDayFormat = new SimpleDateFormat("d");
        DateFormat onlyMonthFormat = new SimpleDateFormat("MM");
        DateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate = new Date();
        String date;
        String fullDate;
        if (typeDate.equals("Дата начала")) {
            date = onlyDayFormat.format(startDate);
            fullDate = fullDateFormat.format(startDate);
        } else {
            Date endDate = datePlusDays(14);
            String startMonth = onlyMonthFormat.format(startDate);
            String endMonth = onlyMonthFormat.format(endDate);
            if (!startMonth.equals(endMonth)) buttonNextMonth.shouldBe(visible).click();
            date = onlyDayFormat.format(endDate);
            fullDate = fullDateFormat.format(endDate);
        }

        dateList.findBy(text(date)).shouldBe(visible).click();
        datePicker.shouldNotBe(visible);

        return fullDate;
    }

    @Step(value = "Выбираем дни недели {day}")
    public String getWeekDaysRules(String... days){
        String listWeekDays = null;
        for(int d = 0; d < days.length; d++) {
            int i = 0;
            for (; i < weekDays.size() - 1; i++) {
                if (weekDays.get(i).text().equals(days[d])) break;
            }
            comboBoxWeekDays.get(i).click();
            inputComboBoxWeekDays.get(i).shouldBe(selected);
            if(d == 0) listWeekDays = days[d].replace(".","");
            else listWeekDays = listWeekDays + ", " + days[d].replace(".","");
        }

        return listWeekDays;
    }

    @Step(value = "Нажимаем кнопку {button} у правила {rules}")
    public IVRPage clickActionButtonRules(String rules, String button){
        scheduleColumnRight
                .find("table")
                .find(byText(rules))
                .closest("tr")
                .find(byText(button))
                .click();
        return this;
    }

    private SelenideElement parentElement(String text){
        return getModalWindow()
                .findAll(".content-item__title")
                .findBy(text(text))
                .parent();
    }

    @Step(value = "Выбираем час {hour} и минуты {minute} для {typeTime}")
    public String getTimeRules(String typeTime, String hour, String minute){
        SelenideElement listMenuActive = $(".menuable__content__active .v-select-list");
        ElementsCollection listTime = listMenuActive.findAll(".v-list__tile__title");
        parentElement(typeTime).find("input[aria-label='Час']").parent().click();
        if(Integer.parseInt(hour) > 19)
            listTime.findBy(text("19")).scrollIntoView(false);
        listTime.findBy(text(hour)).click();
        parentElement(typeTime).find("input[aria-label='Минута']").parent().click();
        if(Integer.parseInt(minute) > 19)
            listTime.findBy(text("19")).scrollIntoView(false);
        if(Integer.parseInt(minute) > 39)
            listTime.findBy(text("39")).scrollIntoView(false);
        listTime.findBy(text(minute)).click();
        return hour + ":" + minute;
    }

    @Step(value = "Выбираем исключение {select}")
    public IVRPage selectException(boolean select){
        SelenideElement comboBoxException = parentElement("Исключение").find(".v-input--selection-controls__ripple");
        SelenideElement inputComboBoxException = parentElement("Исключение").find("input");
        if(select){
            if(!inputComboBoxException.isSelected())
                comboBoxException.click();
            inputComboBoxException.shouldBe(selected);
        }else{
            if(inputComboBoxException.isSelected())
                comboBoxException.click();
            inputComboBoxException.shouldNotBe(selected);
        }

        return this;
    }

    @Step(value = "Проверяем параметры правила после добавления/редактирования")
    public IVRPage isItemRules(String date, String time, boolean exception, boolean show){
        scheduleForm.scrollIntoView(false);
        if(show) {
            if (exception) tableRulesOfSchedule.findAll("td").findBy(text(date)).parent().find(".schedule-except").find("i").shouldBe(visible);
            else tableRulesOfSchedule.findAll("td").findBy(text(date)).parent().find(".schedule-except").find("i").shouldNotBe(visible);
            tableRulesOfSchedule.findAll("td").findBy(text(date)).shouldBe(visible);
            tableRulesOfSchedule.findAll("td").findBy(text(time)).shouldBe(visible);
        }else{
            tableRulesOfSchedule.findAll("td").findBy(text(date)).shouldNotBe(visible);
            tableRulesOfSchedule.findAll("td").findBy(text(time)).shouldNotBe(visible);
        }
        return this;
    }

    /**************************** Точки входа ***************************************/

    @Step(value = "Вызываем контекстное меню в поле {field}")
    public IVRPage clickCallContextMenu(String field){
        labelsComboBoxWindowModal.findBy(text(field)).parent().find(".mdi-menu-down").click();
        return this;
    }

    @Step(value = "Проверяем, есть ли иконка АОН у записи {number} в таблице точки входа")
    public IVRPage isIconArrowAON(String number){
        getServiceSection(IVR_ENTRY_POINTS_TITLE).$("table").scrollIntoView(false);
        getServiceSection(IVR_ENTRY_POINTS_TITLE)
                .find("table")
                .findAll("td")
                .findBy(text(number))
                .find(".arrow-icon")
                .shouldBe(visible);

        return this;
    }

    /**
     * This method add sound file
     * @param file path of sound file
     * @param description
     * @return
     */

    public IVRPage uploadSoundFile(String file, String ...description){
        getContentWrapper().scrollIntoView(false);
        uploadSoundFile(file);
        sendInputModalWindow(IVR_SOUND_FILES_FIELD_NAME, description);
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
        sendInputModalWindow(IVR_SOUND_FILES_FIELD_NAME, description);
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

        sendInputModalWindow("Название", name)
                .sendInputModalWindow("Описание", IVR_MENU_DESCRIPTION + " " + name).
                sendInputModalWindow("Время ожидания донабора, сек", "30");
        selectSoundFile("Настройки", sound)
                .selectSoundFile("Таймаут донабора", sound)
                .clickSelectField("Действие по таймауту донабора");
        if (type.equals("Перейти в меню")) selectItemContextMenu("Перейти в меню", menu[0]);
        else selectItemContextMenu(type);
        selectSoundFile("Неверный донабор", sound)
                .clickSelectField("Действие при неверном донаборе");
        if (type.equals("Перейти в меню")) selectItemContextMenu("Перейти в меню", menu[0]);
        else selectItemContextMenu(type);

        return this;
    }

    /**
     * This method send inputs modal window of voice menu
     * @param name item context menu
     * @param sound
     * @param number
     * @param menu
     * @return
     */
    public IVRPage addVoiceMenu(String name, String type, String sound, String number, String ...menu){
        sendModalWindowOfMenu(name, type, sound, menu)
                .scrollContentModalWindow(false)
                .clickButtonAddDTMF()
                .scrollContentModalWindow(false)
                .clickSelectField("Действие");
                if(type.equals("Перейти в меню")) selectItemContextMenu("Перейти в меню", menu[0]);
                else selectItemContextMenu(type);
                sendInputModalWindow("Набрано", number);

        if(type.equals("Звонок")){
            return sendInputDialNumber(number, "Действие по таймауту донабора"
                    ,"Действие при неверном донаборе"
                    ,"Действие");
        }

        return this;
    }

    public IVRPage editVoiceMenu(String name, String type, String sound, String numberOfMenu){
        if(type.equals("Перейти в меню")) sendModalWindowOfMenu(name, type, sound, numberOfMenu).clickButtonDeleteDTMF();
        else {
            sendModalWindowOfMenu(name, type, sound)
                    .clickButtonDeleteDTMF();
            if (type.equals("Звонок")) {
                return sendInputDialNumber(numberOfMenu, "Действие по таймауту донабора"
                        ,"Действие при неверном донаборе");
            }
        }

        return this;
    }

    public IVRPage sendModalWindowOfSchedule(String schedule){
        sendInputModalWindow("Название", schedule);
        return this;
    }

    public IVRPage sendModalWindowOfEntryPoint(String number, String aon, String menu, String schedule){
        sendInputModalWindow("Набираемый номер", number)
                .sendInputModalWindow("АОН", aon);
        clickCallContextMenu("Меню");
        selectItemComboBox(menu);
        clickCallContextMenu("Расписание");
        selectItemComboBox(schedule);
        return this;
    }
}
