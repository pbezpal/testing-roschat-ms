package chat.ros.testing2.server.services.codefortests;

import chat.ros.testing2.server.settings.services.IVRPage;
import com.codeborne.selenide.SelenideElement;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TIVRPage extends IVRPage implements IIVRPage {

    private String sound_field = "Звуковой файл: ";
    private String timeout_field = "По таймауту";
    private String wrong_number_field = "При неправильном наборе";

    private void saveAndVerifyMenu(String name, String description){
        clickActionButtonOfModalWindow("Сохранить")
                .isModalWindow(false)
                .isItemTable(IVR_MENU_TITLE, name, true)
                .isItemTable(IVR_MENU_TITLE, description, true);
    }

    private void saveAndVerifyEntryPoint(String number, String aon, String menu, String schedule){
        clickActionButtonOfModalWindow("Сохранить")
                .isModalWindow(false)
                .isItemTable(IVR_ENTRY_POINTS_TITLE, number, true)
                .isItemTable(IVR_ENTRY_POINTS_TITLE, aon, true)
                .isItemTable(IVR_ENTRY_POINTS_TITLE, menu, true)
                .isItemTable(IVR_ENTRY_POINTS_TITLE, schedule, true);

        isIconArrowAON(number);
    }

    private void verifyAudioPlayer(String duration, String volume){
        isAudioPlayer();
        assertAll("Проверяем функции аудиоплеера",
                () -> assertTrue(clickPlayAudio().isPlayAudioPlayer() > 0, "Проигрывание аудио работает некорректно"),
                () -> assertTrue(isPauseAudioPlayer(), "Функция паузы аудио работает некорректно"),
                () -> assertEquals(isDurationAudio(),
                        duration,
                        "Продолжительность файла не соотвествует продолжительности загруженного файла"),
                () -> assertEquals(isVolumeAudioPlayer(),
                        volume,
                        "Некорректно работает настройки звука"),
                () -> assertTrue(isMutedAudioPlayer(),
                        "Некорректно работает выключение звука"),
                () -> assertFalse(isOutMutedAudioPlayer(),
                        "Некорректно работает включение звука")
        );
    }

    @Override
    public void checkTitleTextModalWindowWhenUploadFile(String pathFile, String filename, String text) {
        uploadSoundFile(pathFile, filename)
                .isModalWindow(true)
                .isTitleTextModalWindow(text)
                .clickActionButtonOfModalWindow("Отменить")
                .isModalWindow(false)
                .isItemTable(IVR_SOUND_FILES_TITLE, filename, false);
    }

    @Override
    public void checkTitleTextModalWindowWhenAddItem(String section, String text) {
        if(section.equals(IVR_SCHEDULE_TITLE))
            clickButtonAddSchedule();
        else
            clickButtonAdd(section);
        isModalWindow(true)
                .isTitleTextModalWindow(text)
                .clickActionButtonOfModalWindow("Отменить")
                .isModalWindow(false);
    }

    @Override
    public void checkTitleTextWhenEditItem(String section, String item, String text, String button) {
        if(section.equals(IVR_SCHEDULE_TITLE))
            clickButtonActionSchedule(item, button);
        else
            clickButtonTable(section, item, button);
        isModalWindow(true)
                .isTitleTextModalWindow(text)
                .clickActionButtonOfModalWindow("Отменить")
                .isModalWindow(false);
    }

    @Override
    public void checkTitleTextModalWindowWhenActionRules(String schedule, String text, String... rules) {
        if(rules.length == 0)
            clickSchedule(schedule)
                    .clickButtonAddRules();
        else
            clickSchedule(schedule)
                    .clickActionButtonRules(rules[0], IVR_BUTTON_EDIT);
        isModalWindow(true)
                .isTitleTextModalWindow(text)
                .clickActionButtonOfModalWindow("Отменить")
                .isModalWindow(false);
    }

    @Override
    public void uploadMusicFile(String pathToFile, String filename) {
        uploadSoundFile(pathToFile, filename)
                .clickActionButtonOfModalWindow("Сохранить")
                .isModalWindow(false)
                .isItemTable(IVR_SOUND_FILES_TITLE, filename, true);
    }

    @Override
    public void uploadMusicFile(String pathToFile, String filename, String durationFile, String volumePlayer) {
        uploadSoundFile(pathToFile, filename);
        verifyAudioPlayer(durationFile, volumePlayer);
        clickActionButtonOfModalWindow("Сохранить")
                .isModalWindow(false)
                .isItemTable(IVR_SOUND_FILES_TITLE, filename, true);
    }

    @Override
    public void verifyButtonAudioPlayer(String filename, String durationFile, String volumePlayer) {
        clickButtonTable(IVR_SOUND_FILES_TITLE, filename, IVR_BUTTON_PLAY_AUDIO);
        verifyAudioPlayer(durationFile, volumePlayer);
        clickActionButtonOfModalWindow("Отменить")
                .isModalWindow(false);
    }

    @Override
    public void editMusicFile(String oldFilename, String newFilename, String pathToFile, String durationFile, String volumePlayer) {
        clickButtonTable(IVR_SOUND_FILES_TITLE, oldFilename, IVR_BUTTON_EDIT);
        uploadSoundFileByModalWindow(pathToFile, newFilename);
        verifyAudioPlayer(durationFile, volumePlayer);
        clickActionButtonOfModalWindow("Сохранить")
                .isModalWindow(false)
                .isItemTable(IVR_SOUND_FILES_TITLE, newFilename, true);
    }

    @Override
    public void downloadMusicFile(String filename) {
        clickButtonTable(IVR_SOUND_FILES_TITLE, filename, IVR_BUTTON_EDIT);
        assertEquals(downloadSoundFile().getName(), filename,
                "Файл " + filename + " не удалось скачать");
        clickActionButtonOfModalWindow("Отменить")
                .isModalWindow(false);
    }

    @Override
    public void deleteMusicFile(String filename) {
        clickButtonTable(IVR_SOUND_FILES_TITLE, filename, IVR_BUTTON_DELETE);
        isFormConfirmActions(true)
                .clickButtonConfirmAction("Удалить");
        isVisibleElement(dialogWrapper, false);
        isItemTable(IVR_SOUND_FILES_TITLE, filename, false);
    }

    @Override
    public void addMenu(String name, String type, String description, String pathSound, String number, String... goToMenu) {
        clickButtonAdd(IVR_MENU_TITLE);
        if(type.equals("Перейти в меню") && goToMenu.length > 0) addVoiceMenu(name, type, pathSound, number, goToMenu[0]);
        else addVoiceMenu(name, type, pathSound, number);
        saveAndVerifyMenu(name, description);
    }

    @Override
    public void addSchedule(String name) {
        clickButtonAddSchedule().sendModalWindowOfSchedule(name);
        clickActionButtonOfModalWindow("Сохранить").isModalWindow(false);
        isVisibleSchedule(name, true);
    }

    @Override
    public String actionOnRules(String schedule, String typeDate, String[] dates, String[] startTimes, String[] endTimes, boolean except, String... rules) {
        String date = null;
        String startTime;
        String endTime;

        if(rules.length == 0)
            clickSchedule(schedule)
                    .clickButtonAddRules()
                    .selectTypeDate(typeDate)
                    .selectException(except);
        else
            clickSchedule(schedule)
                    .clickActionButtonRules(rules[0], IVR_BUTTON_EDIT)
                    .selectTypeDate(typeDate)
                    .selectException(except);

        if(typeDate.equals(IVR_SCHEDULE_RULE_TYPE_WEEK_DAY)) date = getWeekDaysRules(dates);
        else {
            for(int i = 0; i < dates.length; i++){
                if(i == 0) date = getDateRules(dates[i]) + " - ";
                else date = date + getDateRules(dates[i]);
            }
        }

        startTime = getTimeRules("Время начала", startTimes[0], startTimes[1]);
        endTime = getTimeRules("Время окончания", endTimes[0], endTimes[1]);

        clickActionButtonOfModalWindow("Сохранить").isModalWindow(false);

        isItemRules(date, startTime + " - " + endTime, except, true);

        return date;
    }

    @Override
    public void addEntryPoint(String number, String aon, String menu, String schedule) {
        clickButtonAdd(IVR_ENTRY_POINTS_TITLE);
        sendModalWindowOfEntryPoint(number, aon, menu, schedule);
        saveAndVerifyEntryPoint(number, aon, menu, schedule);
    }

    @Override
    public void editMenu(String newNameMenu, String oldNameMenu, String type, String description, String pathSound, String numberOrTypeMenu) {
        clickButtonTable(IVR_MENU_TITLE, oldNameMenu, IVR_BUTTON_EDIT);
        editVoiceMenu(newNameMenu, type, pathSound, numberOrTypeMenu)
                .isInputNumberDTMF(false)
                .isInputActionDTMF(false);
        saveAndVerifyMenu(newNameMenu, description);
    }

    @Override
    public void editEntryPoint(String number, String aon, String oldMenu, String newMenu, String schedule) {
        clickButtonTable(IVR_ENTRY_POINTS_TITLE, oldMenu, IVR_BUTTON_EDIT);
        sendModalWindowOfEntryPoint(number, aon, newMenu, schedule);
        saveAndVerifyEntryPoint(number, aon, newMenu, schedule);
    }

    @Override
    public void editSchedule(String oldSchedule, String newSchedule) {
        clickButtonActionSchedule(oldSchedule, IVR_BUTTON_EDIT)
                .sendModalWindowOfSchedule(newSchedule);
        clickActionButtonOfModalWindow("Сохранить")
                .isModalWindow(false);
        isVisibleSchedule(newSchedule, true);
    }

    @Override
    public void checkLookModalWindowOfMenu(String title, String type, SelenideElement parent, String soundFile, boolean dtmf, String... number) {
        if ( ! title.contains("Перейти в меню")) {
            clickButtonTable(IVR_MENU_TITLE, title, IVR_MENU_BUTTON_LOOK_MENU)
                    .isTitleTextModalWindow(title);
        }
        isIconSoundOfModalWindowVoiceMenu(parent)
                .isIconTimeOutOfModalWindowMenu(parent)
                .isIconErrorOutlineOfModalWindowMenu(parent);
        assertAll("Проверяем, отображение элементов в модальном окне" +
                        " для просмотра настроек меню",
                () -> assertEquals(getTextSpanNameOfModalWindowMenu(parent,sound_field),
                        soundFile,
                        "Отсуствтует значение " + soundFile + " в модальном окне просмотра настроек Звуковой файл"),
                () -> assertEquals(getTextSpanNameOfModalWindowMenu(parent,timeout_field),
                        type,
                        "Отсуствтует значение " + type + " в модальном окне просмотра настроек По таймауту"),
                () -> assertEquals(getTextSpanNameOfModalWindowMenu(parent,wrong_number_field),
                        type,
                        "Отсуствтует значение " + type + " в модальном окне просмотра настроек При неправильном наборе")
        );

        if (dtmf) {
            isIconDTMFOfModalWindowMenu(parent);
            assertAll("Проверяем, отображается ли настройки DTMF",
                    () -> assertEquals(getNumberDTMFOfModalWindowMenu(parent),
                            number[0],
                            "Отсутствует номер в модальном окне просмотр настроек DTMF"),
                    () -> assertEquals(getTextDTMFOfModalWindowMenu(parent),
                            type,
                            "Отсуствтует значение " + type + " в модальном окне просмотра настроек DTMF")
            );
        }

        if( ! title.contains("Перейти в меню"))
            clickActionButtonOfModalWindow("Закрыть").isModalWindow(false);
    }

    @Override
    public void checkLookModalWindowOfMenu(String title, String textGoToMenu, String secondTextLink, String type, String soundFile, boolean action, boolean dtmfSimpleMenu, boolean dtmf, String... number) {
        String numberSimpleMenu;
        if (dtmfSimpleMenu && dtmf) numberSimpleMenu = number[1];
        else if (dtmfSimpleMenu && !dtmf) numberSimpleMenu = number[0];
        else numberSimpleMenu = null;
        clickButtonTable(IVR_MENU_TITLE, title, IVR_MENU_BUTTON_LOOK_MENU)
                .isTitleTextModalWindow(title);
        isIconSoundOfModalWindowVoiceMenu(getModalWindow())
                .isIconTimeOutOfModalWindowMenu(getModalWindow())
                .isIconErrorOutlineOfModalWindowMenu(getModalWindow())
                .isGoToActionOfSpanOfModalWindow(timeout_field, action)
                .isGoToActionOfSpanOfModalWindow(wrong_number_field, action);
        assertAll("Проверяем, отображение элементов в модальном окне" +
                        "для просмотра настроек меню",
                () -> assertEquals(getTextSpanNameOfModalWindowMenu(getModalWindow(), sound_field),
                        soundFile,
                        "Отсуствтует значение " + soundFile + " в модальном окне просмотра настроек Звуковой файл"),
                () -> assertEquals(getFirstTextGoToActionOfSpanOfModalWindow(timeout_field, action),
                        "Перейти в меню",
                        "Отсуствует текст Перейти в меню в поле По таймауту"),
                () -> assertEquals(getSecondTextGoToActionOfSpanOfModalWindow(timeout_field, action),
                        "«" + secondTextLink + "»",
                        "Отсуствует текст «" + secondTextLink + "» в поле " + timeout_field),
                () -> assertEquals(getFirstTextGoToActionOfSpanOfModalWindow(wrong_number_field, action),
                        "Перейти в меню",
                        "Отсуствует текст Перейти в меню в поле " + wrong_number_field),
                () -> assertEquals(getSecondTextGoToActionOfSpanOfModalWindow(wrong_number_field, action),
                        "«" + secondTextLink + "»",
                        "Отсуствует текст " + secondTextLink + " в поле " + wrong_number_field)
        );
        if (dtmf) {
            isIconDTMFOfModalWindowMenu(getModalWindow());
            assertEquals(getNumberDTMFOfModalWindowMenu(getModalWindow()),
                    String.valueOf(number[0]),
                    "Отсутствует номер в модальном окне просмотр настроек DTMF");
            assertEquals(getFirstTextGoToActionOfDTMFOfModalWindowMenu(),
                    "Перейти в меню",
                    "Отсуствует текст Перейти в меню в поле DTMF");
            assertEquals(getSecondTextGoToActionOfDTMFOfModalWindowMenu(),
                    "«" + secondTextLink + "»",
                    "Отсуствует текст " + secondTextLink + " в поле DTMF");
            clickGoToActionOfDTMF().scrollContentModalWindow(false);
            checkLookModalWindowOfMenu(title, type, getElementMenuOfGoToActionWithDTMF(), soundFile, dtmfSimpleMenu, numberSimpleMenu);
            clickGoToActionOfDTMF().isElementMenuOfGoToActionWithDTMF(false);
        }

        if (action) {
            clickGoToActionOfModalWindow(timeout_field);
            checkLookModalWindowOfMenu(title, type, getElementMenuOfGoToAction(timeout_field), soundFile, dtmfSimpleMenu, numberSimpleMenu);
            clickGoToActionOfModalWindow(timeout_field)
                    .isElementMenuOfGoToAction(timeout_field, false)
                    .clickGoToActionOfModalWindow(wrong_number_field);
            checkLookModalWindowOfMenu(title, type, getElementMenuOfGoToAction(wrong_number_field), soundFile, dtmfSimpleMenu, numberSimpleMenu);
            clickGoToActionOfModalWindow(wrong_number_field)
                                    .isElementMenuOfGoToAction(wrong_number_field, false);
            clickActionButtonOfModalWindow("Показать все");
            checkLookModalWindowOfMenu(title, type, getElementMenuOfGoToAction(timeout_field), soundFile, dtmfSimpleMenu, numberSimpleMenu);
            checkLookModalWindowOfMenu(title, type, getElementMenuOfGoToAction(wrong_number_field), soundFile, dtmfSimpleMenu, numberSimpleMenu);
            if (dtmf) {
                scrollContentModalWindow(false);
                checkLookModalWindowOfMenu(title, type, getElementMenuOfGoToActionWithDTMF(), soundFile, dtmfSimpleMenu, numberSimpleMenu);
            }

            clickActionButtonOfModalWindow("Свернуть все");
            isElementMenuOfGoToAction(timeout_field, false)
                    .isElementMenuOfGoToAction(wrong_number_field, false);
            if (dtmf) isElementMenuOfGoToActionWithDTMF(false);
        }

        clickActionButtonOfModalWindow("Закрыть").isModalWindow(false);
    }

    @Override
    public void deleteMenu(String name, String description) {
        clickButtonTable(IVR_MENU_TITLE, name, IVR_BUTTON_DELETE);
        isFormConfirmActions(true)
                .clickButtonConfirmAction("Удалить");
        isVisibleElement(dialogWrapper, false);
        isItemTable(IVR_MENU_TITLE, name, false)
                .isItemTable(IVR_MENU_TITLE, description, false);
    }

    @Override
    public void deleteEntryPoint(String titleMenu, String[] data) {
        clickButtonTable(IVR_ENTRY_POINTS_TITLE, titleMenu, IVR_BUTTON_DELETE);
        isFormConfirmActions(true)
                .clickButtonConfirmAction("Продолжить");
        isVisibleElement(dialogWrapper, false);
        String number = data[0];
        String aon = data[1];
        String schedule = data[2];
        isItemTable(IVR_ENTRY_POINTS_TITLE, titleMenu, false)
                .isItemTable(IVR_ENTRY_POINTS_TITLE, number, false)
                .isItemTable(IVR_ENTRY_POINTS_TITLE, aon, false)
                .isItemTable(IVR_ENTRY_POINTS_TITLE, schedule, false);
    }

    @Override
    public void deleteSchedule(String schedule) {
        clickButtonActionSchedule(schedule, IVR_BUTTON_DELETE);
        isFormConfirmActions(true)
                .clickButtonConfirmAction("Продолжить");
        isVisibleElement(dialogWrapper, false);
        isVisibleSchedule(schedule, false);
    }

    @Override
    public void deleteRules(String schedule, String date, String time) {
        clickSchedule(schedule)
                .clickActionButtonRules(date, IVR_BUTTON_DELETE);
        isFormConfirmActions(true)
                .clickButtonConfirmAction("Продолжить");
        isVisibleElement(dialogWrapper, false);
        isItemRules(date, time, false, false);
    }
}
