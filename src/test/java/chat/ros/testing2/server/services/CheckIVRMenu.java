package chat.ros.testing2.server.services;

import chat.ros.testing2.server.settings.IVRPage;

import static chat.ros.testing2.data.SettingsData.IVR_MENU_BUTTON_LOOK_MENU;
import static chat.ros.testing2.data.SettingsData.IVR_MENU_TITLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckIVRMenu extends IVRPage {

    private String sound_field = "Звуковой файл: ";
    private String timeout_field = "По таймауту: ";
    private String wrong_number_field = "При неправильном наборе: ";

    public void checkLookModalWindowOfMenu(String title, String type, String soundFile, boolean dtmf, String ...number){
        assertTrue(isItemTable(IVR_MENU_TITLE, title, true),
                "Название  " + title + " не найдено в таблице меню");
        assertAll("Проверяем, отображение элементов в модальном окне" +
                        " для просмотра настроек меню",
                () -> assertEquals(clickButtonTable(IVR_MENU_TITLE, title, IVR_MENU_BUTTON_LOOK_MENU)
                                .isVisibleTitleModalWrapper(),
                        title,
                        "Не найден заголовок модального окна при просмотре настроек меню " + title),
                () -> assertTrue(isIconSoundOfModalWindowVoiceMenu(getModalWindow()),
                        "Отсутствует иконка звукового файла"),
                () -> assertTrue(isIconTimeOutOfModalWindowMenu(getModalWindow()),
                        "Отсутствует иконка По таймауту"),
                () -> assertTrue(isIconErrorOutlineOfModalWindowMenu(getModalWindow()),
                        "Отсутствует иконка неправильно набранного номера"),
                () -> assertEquals(getTextSpanNameOfModalWindowMenu(getModalWindow(),sound_field),
                        soundFile,
                        "Отсуствтует значение " + soundFile + " в модальном окне просмотра настроек Звуковой файл"),
                () -> assertEquals(getTextSpanNameOfModalWindowMenu(getModalWindow(),timeout_field),
                        type,
                        "Отсуствтует значение " + type + " в модальном окне просмотра настроек По таймауту"),
                () -> assertEquals(getTextSpanNameOfModalWindowMenu(getModalWindow(),wrong_number_field),
                        type,
                        "Отсуствтует значение " + type + " в модальном окне просмотра настроек При неправильном наборе")
        );

        if(dtmf){
            assertAll("Проверяем, отображается ли настройки DTMF",
                    () -> assertTrue(isIconDTMFOfModalWindowMenu(getModalWindow()),
                            "Отсутствует иконка DTMF"),
                    () -> assertEquals(getNumberDTMFOfModalWindowMenu(getModalWindow()),
                            number[0],
                            "Отсутствует номер в модальном окне просмотр настроек DTMF"),
                    () -> assertEquals(getTextDTMFOfModalWindowMenu(getModalWindow()),
                            type,
                            "Отсуствтует значение " + type + " в модальном окне просмотра настроек DTMF")
            );
        }

        clickActionButtonOfModalWindow("Закрыть");
    }

    public void checkLookModalWindowOfMenu(String field, String type, boolean dtmf){
        assertTrue(isIconSoundOfModalWindowVoiceMenu(getElementMenuOfGoToAction(field)),
                "Отсутствует иконка звукового файла");
        assertTrue(isIconTimeOutOfModalWindowMenu(getElementMenuOfGoToAction(field)),
                "Отсутствует иконка По таймауту");
        assertTrue(isIconErrorOutlineOfModalWindowMenu(getElementMenuOfGoToAction(field)),
                "Отсутствует иконка неправильно набранного номера");
        assertEquals(getTextSpanNameOfModalWindowMenu(getElementMenuOfGoToAction(field), timeout_field),
                type,
                "Отсуствтует значение " + type + " в модальном окне просмотра настроек По таймауту");
        assertEquals(getTextSpanNameOfModalWindowMenu(getElementMenuOfGoToAction(field), wrong_number_field),
                type,
                "Отсуствтует значение " + type + " в модальном окне просмотра настроек При неправильном наборе");
        /*assertAll("Проверяем, отображение элементов в разделе " + field,
                () -> assertTrue(isIconSoundOfModalWindowVoiceMenu(getElementMenuOfGoToAction(field)),
                        "Отсутствует иконка звукового файла"),
                () -> assertTrue(isIconTimeOutOfModalWindowMenu(getElementMenuOfGoToAction(field)),
                        "Отсутствует иконка По таймауту"),
                () -> assertTrue(isIconErrorOutlineOfModalWindowMenu(getElementMenuOfGoToAction(field)),
                        "Отсутствует иконка неправильно набранного номера"),
                () -> assertEquals(getTextSpanNameOfModalWindowMenu(getElementMenuOfGoToAction(field),timeout_field),
                        type,
                        "Отсуствтует значение " + type + " в модальном окне просмотра настроек По таймауту"),
                () -> assertEquals(getTextSpanNameOfModalWindowMenu(getElementMenuOfGoToAction(field),wrong_number_field),
                        type,
                        "Отсуствтует значение " + type + " в модальном окне просмотра настроек При неправильном наборе")
        );*/
    }

    public void checkLookModalWindowOfGoToTheMenu(String title, String type, String soundFile, boolean dtmf){
        assertTrue(isItemTable(IVR_MENU_TITLE, title, true),
                "Название  " + title + " не найдено в таблице меню");
        assertEquals(clickButtonTable(IVR_MENU_TITLE, title, IVR_MENU_BUTTON_LOOK_MENU)
                        .isVisibleTitleModalWrapper(),
                title,
                "Не найден заголовок модального окна при просмотре настроек меню " + title);
        assertTrue(isIconSoundOfModalWindowVoiceMenu(getModalWindow()),
                "Отсутствует иконка звукового файла");
        assertTrue(isIconTimeOutOfModalWindowMenu(getModalWindow()),
                "Отсутствует иконка По таймауту");
        assertTrue(isIconErrorOutlineOfModalWindowMenu(getModalWindow()),
                "Отсутствует иконка неправильно набранного номера");
        assertEquals(getTextSpanNameOfModalWindowMenu(getModalWindow(), sound_field),
                soundFile,
                "Отсуствтует значение " + soundFile + " в модальном окне просмотра настроек Звуковой файл");
        assertTrue(isGoToActionOfSpanOfModalWindow(timeout_field),
                "Отсуствтует ссылка для отображение настроек меню у поля " + timeout_field);
        assertEquals(getFirstTextGoToActionOfSpanOfModalWindow(timeout_field),
                "Перейти в меню",
                "Отсуствует текст Перейти в меню в поле По таймауту");
        assertEquals(getSecondTextGoToActionOfSpanOfModalWindow(timeout_field),
                "«" + type + "»",
                "Отсуствует текст «" + type + "» в поле " + timeout_field);
        assertTrue(isGoToActionOfSpanOfModalWindow(wrong_number_field),
                "Отсуствтует ссылка для отображение настроек меню у поля " + wrong_number_field);
        assertEquals(getFirstTextGoToActionOfSpanOfModalWindow(wrong_number_field),
                "Перейти в меню",
                "Отсуствует текст Перейти в меню в поле " + wrong_number_field);
        assertEquals(getSecondTextGoToActionOfSpanOfModalWindow(wrong_number_field),
                "«" + type + "»",
                "Отсуствует текст " + type + " в поле " + wrong_number_field);
        clickGoToActionOfModalWindow(timeout_field);
        checkLookModalWindowOfMenu(timeout_field, type, false);
        clickGoToActionOfModalWindow(wrong_number_field);
        checkLookModalWindowOfMenu(wrong_number_field, type, false);
        /*assertAll("Проверяем, отображение элементов в модальном окне" +
                        "для просмотра настроек меню",
                () -> assertEquals(clickButtonTable(IVR_MENU_TITLE, title, IVR_MENU_BUTTON_LOOK_MENU)
                                .isVisibleTitleModalWrapper(),
                        title,
                        "Не найден заголовок модального окна при просмотре настроек меню " + title),
                () -> assertTrue(isIconSoundOfModalWindowVoiceMenu(getModalWindow()),
                        "Отсутствует иконка звукового файла"),
                () -> assertTrue(isIconTimeOutOfModalWindowMenu(getModalWindow()),
                        "Отсутствует иконка По таймауту"),
                () -> assertTrue(isIconErrorOutlineOfModalWindowMenu(getModalWindow()),
                        "Отсутствует иконка неправильно набранного номера"),
                () -> assertEquals(getTextSpanNameOfModalWindowMenu(getModalWindow(),sound_field),
                        soundFile,
                        "Отсуствтует значение " + soundFile + " в модальном окне просмотра настроек Звуковой файл"),
                () -> assertTrue(isGoToActionOfSpanOfModalWindow(timeout_field),
                        "Отсуствтует ссылка для отображение настроек меню у поля По таймауту"),
                () -> assertEquals(getFirstTextGoToActionOfSpanOfModalWindow(timeout_field),
                        "Перейти в меню",
                        "Отсуствует текст Перейти в меню в поле По таймауту"),
                () -> assertEquals(getSecondTextGoToActionOfSpanOfModalWindow(timeout_field),
                        "«" + type + "»",
                        "Отсуствует текст «" + type + "» в поле " + timeout_field),
                () -> {clickGoToActionOfModalWindow(timeout_field);},
                () -> {checkLookModalWindowOfMenu(timeout_field, type, false);},
                () -> assertEquals(getFirstTextGoToActionOfSpanOfModalWindow(wrong_number_field),
                        "Перейти в меню",
                        "Отсуствует текст Перейти в меню в поле " + wrong_number_field),
                () -> assertEquals(getSecondTextGoToActionOfSpanOfModalWindow(wrong_number_field),
                        "" + type + "",
                        "Отсуствует текст  «" + type + "» в поле " + wrong_number_field),
                () -> {isGoToActionOfSpanOfModalWindow(wrong_number_field);},
                () -> {checkLookModalWindowOfMenu(timeout_field, type, false);}
        );*/

        /*if(dtmf){
            assertAll("Проверяем, отображается ли настройки DTMF",
                    () -> assertTrue(isIconDTMFOfModalWindowMenu(getModalWindow()),
                            "Отсутствует иконка DTMF"),
                    () -> assertEquals(getNumberDTMFOfModalWindowMenu(getModalWindow()),
                            String.valueOf(num),
                            "Отсутствует номер в модальном окне просмотр настроек DTMF"),
                    () -> assertEquals(getTextDTMFOfModalWindowMenu(getModalWindow()),
                            type,
                            "Отсуствтует значение " + type + " в модальном окне просмотра настроек DTMF")
            );
        }*/

        clickActionButtonOfModalWindow("Закрыть");
    }

}
