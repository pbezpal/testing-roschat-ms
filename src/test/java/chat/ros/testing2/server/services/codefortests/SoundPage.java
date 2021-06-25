package chat.ros.testing2.server.services.codefortests;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.server.settings.services.IVRPage;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SoundPage extends IVRPage implements ISoundPage {

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
    public void uploadMusicFile(String pathToFile, String filename) {
        assertAll("Проверяем добавление звукового файла " + filename,
                () -> assertEquals(uploadSoundFile(pathToFile, filename)
                        .getTextTitleModalWindow()
                        , "Новый звуковой файл",
                        "Не найден заголовок модального окна при добавлении звукового файла"),
                () -> {
                    clickActionButtonOfModalWindow("Сохранить")
                            .isModalWindow(false)
                            .isItemTable(IVR_SOUND_FILES_TITLE, filename, true);
                }
        );
    }

    @Override
    public void uploadMusicFile(String pathToFile, String filename, String durationFile, String volumePlayer) {
        assertAll("Проверяем:\n" +
                        "1. Заголовок модального окна\n" +
                        "2. Функции аудиоплеера\n" +
                        "3. Сохрание звукового файла",
                () -> assertEquals(uploadSoundFile(pathToFile, filename)
                        .getTextTitleModalWindow()
                        ,"Новый звуковой файл",
                        "Не найден заголовок модального окна при добавлении звукового файла " + filename),
                () -> {
                    verifyAudioPlayer(durationFile, volumePlayer);
                },
                () -> {clickActionButtonOfModalWindow("Сохранить").isModalWindow(false);},
                () -> {isItemTable(IVR_SOUND_FILES_TITLE, filename, true);}
        );
    }

    @Override
    public void verifyButtonAudioPlayer(String filename, String durationFile, String volumePlayer) {
        assertAll("Проверяем:\n" +
                "1. Заголовок модального окна\n" +
                "2. Функции аудиоплеера\n" +
                "3. Закрытие модального окна",
                () -> assertEquals(
                        clickButtonTable(IVR_SOUND_FILES_TITLE, filename, IVR_BUTTON_PLAY_AUDIO).getTextTitleModalWindow()
                        , "Редактирование звукового файла"
                        ,"Не найден заголовок модального окна при прослушивании звукового файла " + filename),
                () -> {
                    verifyAudioPlayer(durationFile, volumePlayer);
                },
                () -> {clickActionButtonOfModalWindow("Отменить").isModalWindow(false);}
        );
    }

    @Override
    public void editMusicFile(String oldFilename, String newFilename, String pathToFile, String durationFile, String volumePlayer) {
        assertAll("Проверяем:\n" +
                        "1. Заголовок модального окна\n" +
                        "2. Функции аудиоплеера\n" +
                        "3. Закрытие модального окна",
                () -> assertEquals(
                        clickButtonTable(IVR_SOUND_FILES_TITLE, oldFilename, IVR_BUTTON_PLAY_AUDIO).getTextTitleModalWindow()
                        , "Редактирование звукового файла"
                        ,"Не найден заголовок модального окна при прослушивании звукового файла " + oldFilename),
                () -> {
                    uploadSoundFileByModalWindow(pathToFile, newFilename);
                },
                () -> {
                    verifyAudioPlayer(durationFile, volumePlayer);
                },
                () -> {clickActionButtonOfModalWindow("Сохранить").isModalWindow(false);},
                () -> {isItemTable(IVR_SOUND_FILES_TITLE, newFilename, true);}
        );
    }

    @Override
    public void downloadMusicFile(String filename) {
        assertAll("Проверяем:\n" +
                        "1. Наличие заколовка модального окна\n" +
                        "2. скачивание файла " + filename + "\n" +
                        "3. Закрытие модального окна после нажатия кнопки Отмена",
                () -> assertEquals(clickButtonTable(IVR_SOUND_FILES_TITLE, filename, IVR_BUTTON_EDIT)
                                .getTextTitleModalWindow(),
                        "Редактирование звукового файла",
                        "Не найден заголовок модального окна при воспроизведение"),
                () -> assertEquals(downloadSoundFile().getName(), filename,
                        "Файл " + filename + " не удалось скачать"),
                () -> {clickActionButtonOfModalWindow("Отменить").isModalWindow(false);}
        );
    }

    @Override
    public void deleteMusicFile(String filename) {
        clickButtonTable(IVR_SOUND_FILES_TITLE, filename, IVR_BUTTON_DELETE);
        isFormConfirmActions(true)
                .clickButtonConfirmAction("Удалить");
        isItemTable(IVR_SOUND_FILES_TITLE, filename, false);
    }

    @Override
    public void addMenu(String name, String type, String description, String pathSound, String number, String... goToMenu) {
        clickButtonAdd(IVR_MENU_TITLE);
        /*if(!clickButtonAdd(IVR_MENU_TITLE).getTextTitleModalWindow().equals("Новое голосовое меню"))
            Allure.step("Не найден заголовок модального окна при добавлении голосового меню", Status.FAILED);*/
        if(type.equals("Перейти в меню") && goToMenu.length > 0) addVoiceMenu(name, type, pathSound, number, goToMenu[0]);
        else addVoiceMenu(name, type, pathSound, number);
        saveAndVerifyMenu(name, description);
    }

    @Override
    public void addSchedule(String name) {
        clickButtonAddSchedule().sendModalWindowOfSchedule(name);
        /*if(getTextTitleModalWindow().equals("Новое расписание"))
            Allure.step("Не найден заголовок модального окна при добаление нового рапсписания", Status.FAILED);*/
        clickActionButtonOfModalWindow("Сохранить").isModalWindow(false);
        isVisibleSchedule(name, true);
    }

    @Override
    public void addRules(String schedule, String typeDate, String[] dates, String[] startTimes, String[] endTimes, boolean except) {
        String date = null;
        String startTime;
        String endTime;

        isTitleRules().clickSchedule(schedule).clickButtonAddRules().selectTypeDate(typeDate).selectException(except);

        if(typeDate.equals(IVR_SCHEDULE_RULE_TYPE_WEEK_DAY)) date = getWeekDaysRules(dates);
        else {
            for(int i = 0; i < dates.length; i++){
                if(i == 0) date = getDateRules(dates[i]) + " - ";
                else date = date + getDateRules(dates[i]);
            }
        }

        startTime = getTimeRules("Время начала", startTimes[0], startTimes[1]);
        endTime = getTimeRules("Время окончания", endTimes[0], endTimes[1]);

        /*if( ! getTextTitleModalWindow().equals("Новое правило"))
            Allure.step("Не найден заголовок модального окна при добаление нового правила", Status.FAILED);*/

        clickActionButtonOfModalWindow("Сохранить").isModalWindow(false);

        isItemRules(date, startTime + " - " + endTime, except);
    }

    @Override
    public void addEntryPoint(String number, String aon, String menu, String schedule) {
        clickButtonAdd(IVR_ENTRY_POINTS_TITLE);
        sendModalWindowOfEntryPoint(number, aon, menu, schedule);
        saveAndVerifyEntryPoint(number, aon, menu, schedule);
    }

    @Override
    public void editMenu(String newNameMenu, String oldNameMenu, String type, String description, String pathSound, String numberOrTypeMenu) {
        assertAll("Проверяем редактирование меню " + oldNameMenu,
                () -> assertEquals(
                        clickButtonTable(IVR_MENU_TITLE, oldNameMenu, IVR_BUTTON_EDIT).getTextTitleModalWindow(),
                        "Редактирование голосового меню"
                        , "Не найден заголовок модального окна при редактировании голосового меню " + oldNameMenu),
                () -> {
                    editVoiceMenu(newNameMenu, type, pathSound, numberOrTypeMenu);
                    isInputNumberDTMF(false).isInputActionDTMF(false);
                    saveAndVerifyMenu(newNameMenu, description);
                }
                );
    }

    @Override
    public void editEntryPoint(String number, String aon, String oldMenu, String newMenu, String schedule) {
        clickButtonTable(IVR_ENTRY_POINTS_TITLE, oldMenu, IVR_BUTTON_EDIT);
        sendModalWindowOfEntryPoint(number, aon, newMenu, schedule);
        saveAndVerifyEntryPoint(number, aon, newMenu, schedule);
    }

    @Override
    public void checkLookModalWindowOfMenu(String title, String type, SelenideElement parent, String soundFile, boolean dtmf, String... number) {
        if ( ! title.contains("Перейти в меню")) {
            assertEquals(clickButtonTable(IVR_MENU_TITLE, title, IVR_MENU_BUTTON_LOOK_MENU)
                            .getTextTitleModalWindow(),
                    title,
                    "Не найден заголовок модального окна при просмотре настроек меню " + title);
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
        assertEquals(clickButtonTable(IVR_MENU_TITLE, title, IVR_MENU_BUTTON_LOOK_MENU)
                        .getTextTitleModalWindow(),
                title,
                "Не найден заголовок модального окна при просмотре настроек меню " + title);
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

    }

    @Override
    public void deleteEntryPoint(String number, String titleMenu) {

    }
}
