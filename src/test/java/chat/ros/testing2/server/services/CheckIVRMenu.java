package chat.ros.testing2.server.services;

import chat.ros.testing2.server.settings.services.IVRPage;
import com.codeborne.selenide.SelenideElement;

import static chat.ros.testing2.data.SettingsData.IVR_MENU_BUTTON_LOOK_MENU;
import static chat.ros.testing2.data.SettingsData.IVR_MENU_TITLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckIVRMenu extends IVRPage {

    private String sound_field = "Звуковой файл: ";
    private String timeout_field = "По таймауту";
    private String wrong_number_field = "При неправильном наборе";

    public void checkLookModalWindowOfMenu(String title, String type, SelenideElement parent, String soundFile, boolean dtmf, String ...number){
        if( ! title.contains("Перейти в меню")) {
            assertTrue(isItemTable(IVR_MENU_TITLE, title, true),
                    "Название  " + title + " не найдено в таблице меню");
        }
        assertAll("Проверяем, отображение элементов в модальном окне" +
                        " для просмотра настроек меню",
                () -> {
                    if ( ! title.contains("Перейти в меню")) {
                        assertEquals(clickButtonTable(IVR_MENU_TITLE, title, IVR_MENU_BUTTON_LOOK_MENU)
                                        .isVisibleTitleModalWrapper(),
                                title,
                                "Не найден заголовок модального окна при просмотре настроек меню " + title);
                    }
                },
                () -> assertTrue(isIconSoundOfModalWindowVoiceMenu(parent),
                        "Отсутствует иконка звукового файла"),
                () -> assertTrue(isIconTimeOutOfModalWindowMenu(parent),
                        "Отсутствует иконка По таймауту"),
                () -> assertTrue(isIconErrorOutlineOfModalWindowMenu(parent),
                        "Отсутствует иконка неправильно набранного номера"),
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
            assertAll("Проверяем, отображается ли настройки DTMF",
                    () -> assertTrue(isIconDTMFOfModalWindowMenu(parent),
                            "Отсутствует иконка DTMF"),
                    () -> assertEquals(getNumberDTMFOfModalWindowMenu(parent),
                            number[0],
                            "Отсутствует номер в модальном окне просмотр настроек DTMF"),
                    () -> assertEquals(getTextDTMFOfModalWindowMenu(parent),
                            type,
                            "Отсуствтует значение " + type + " в модальном окне просмотра настроек DTMF")
            );
        }

        if( ! title.contains("Перейти в меню")) clickActionButtonOfModalWindow("Закрыть");
    }

    public void checkLookModalWindowOfGoToTheMenu(String title, String textGoToMenu, String type, String soundFile, boolean dtmfSimpleMenu, boolean dtmf, String... number){
        String numberSimpleMenu;
        if(dtmfSimpleMenu && dtmf) numberSimpleMenu = number[1];
        else if(dtmfSimpleMenu && ! dtmf) numberSimpleMenu = number[0];
        else numberSimpleMenu = null;
        assertAll("Проверяем, отображение элементов в модальном окне" +
                        "для просмотра настроек меню",
                () -> assertTrue(isItemTable(IVR_MENU_TITLE, title, true),
                        "Название  " + title + " не найдено в таблице меню"),
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
                () -> assertEquals(getTextSpanNameOfModalWindowMenu(getModalWindow(), sound_field),
                soundFile,
                "Отсуствтует значение " + soundFile + " в модальном окне просмотра настроек Звуковой файл"),
                () -> assertTrue(isGoToActionOfSpanOfModalWindow(timeout_field),
                "Отсуствтует ссылка для отображение настроек меню у поля " + timeout_field),
                () -> assertEquals(getFirstTextGoToActionOfSpanOfModalWindow(timeout_field),
                "Перейти в меню",
                "Отсуствует текст Перейти в меню в поле По таймауту"),
                () -> assertEquals(getSecondTextGoToActionOfSpanOfModalWindow(timeout_field),
                "«" + textGoToMenu + "»",
                "Отсуствует текст «" + textGoToMenu + "» в поле " + timeout_field),
                () -> assertTrue(isGoToActionOfSpanOfModalWindow(wrong_number_field),
                "Отсуствтует ссылка для отображение настроек меню у поля " + wrong_number_field),
                () -> assertEquals(getFirstTextGoToActionOfSpanOfModalWindow(wrong_number_field),
                "Перейти в меню",
                "Отсуствует текст Перейти в меню в поле " + wrong_number_field),
                () -> assertEquals(getSecondTextGoToActionOfSpanOfModalWindow(wrong_number_field),
                "«" + textGoToMenu + "»",
                "Отсуствует текст " + textGoToMenu + " в поле " + wrong_number_field),
                () -> {
                    if(dtmf){
                        assertTrue(isIconDTMFOfModalWindowMenu(getModalWindow()),
                                "Отсутствует иконка DTMF");
                        assertEquals(getNumberDTMFOfModalWindowMenu(getModalWindow()),
                                String.valueOf(number[0]),
                                "Отсутствует номер в модальном окне просмотр настроек DTMF");
                        assertEquals(getFirstTextGoToActionOfDTMFOfModalWindowMenu(),
                                "Перейти в меню",
                                "Отсуствует текст Перейти в меню в поле DTMF");
                        assertEquals(getSecondTextGoToActionOfDTMFOfModalWindowMenu(),
                                "«" + textGoToMenu + "»",
                                "Отсуствует текст " + textGoToMenu + " в поле DTMF");
                        clickGoToActionOfDTMF().scrollContentModalWindow(false);
                        checkLookModalWindowOfMenu(title, type, getElementMenuOfGoToActionWithDTMF(), soundFile, dtmfSimpleMenu, numberSimpleMenu);
                        clickGoToActionOfDTMF().isElementMenuOfGoToActionWithDTMF(false);
                    }
                },
                () -> {clickGoToActionOfModalWindow(timeout_field);},
                () -> {checkLookModalWindowOfMenu(title, type, getElementMenuOfGoToAction(timeout_field), soundFile, dtmfSimpleMenu, numberSimpleMenu);},
                () -> assertTrue(clickGoToActionOfModalWindow(timeout_field)
                        .isElementMenuOfGoToAction(timeout_field, false),
                        "Отображается настройки меню в поле " + timeout_field),
                () -> {clickGoToActionOfModalWindow(wrong_number_field);},
                () -> {checkLookModalWindowOfMenu(title, type, getElementMenuOfGoToAction(wrong_number_field), soundFile, dtmfSimpleMenu, numberSimpleMenu);},
                () -> assertTrue(clickGoToActionOfModalWindow(wrong_number_field)
                        .isElementMenuOfGoToAction(wrong_number_field, false),
                        "Отображается настройки меню в поле " + wrong_number_field)
        );

        clickActionButtonOfModalWindow("Показать все");

        assertAll("Проверяем функцию Показать всё",
                () -> {
                    checkLookModalWindowOfMenu(title, type, getElementMenuOfGoToAction(timeout_field), soundFile, dtmfSimpleMenu, numberSimpleMenu);
                },
                () -> {
                    checkLookModalWindowOfMenu(title, type, getElementMenuOfGoToAction(wrong_number_field), soundFile, dtmfSimpleMenu, numberSimpleMenu);
                },
                () -> {
                    scrollContentModalWindow(false);
                },
                () -> {
                    if (dtmf) {
                        scrollContentModalWindow(false);
                        checkLookModalWindowOfMenu(title, type, getElementMenuOfGoToActionWithDTMF(), soundFile, dtmfSimpleMenu, numberSimpleMenu);
                    }
                }
        );

        clickActionButtonOfModalWindow("Свернуть все");

        assertAll("Проверяем функцию Свернуть все",
                () -> assertTrue(isElementMenuOfGoToAction(timeout_field, false),
                        "Отображается настройки меню в поле " + timeout_field),
                () -> assertTrue(isElementMenuOfGoToAction(wrong_number_field, false),
                        "Отображается настройки меню в поле " + wrong_number_field),
                () -> {
                    if (dtmf)
                        assertTrue(isElementMenuOfGoToActionWithDTMF(false), "Отображаются настройки меню в разделе DTMF");
                }
        );

        clickActionButtonOfModalWindow("Закрыть");
    }

}
