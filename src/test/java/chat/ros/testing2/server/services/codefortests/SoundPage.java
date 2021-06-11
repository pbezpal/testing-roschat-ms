package chat.ros.testing2.server.services.codefortests;

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

    @Override
    public void uploadMusicFile(String pathToFile, String filename) {
        if(!uploadSoundFile(pathToFile, filename)
                .getTextTitleModalWindow().equals("Новый звуковой файл"))
            Allure.step("Не найден заголовок модального окна при добавлении звукового файла", Status.FAILED);
        clickActionButtonOfModalWindow("Сохранить")
                .isModalWindow(false)
                .isItemTable(IVR_SOUND_FILES_TITLE, filename, true);
    }

    @Override
    public void addMenu(String name, String type, String description, String pathSound, String number, String... goToMenu) {
        if(clickButtonAdd(IVR_MENU_TITLE).getTextTitleModalWindow().equals("Новое голосовое меню"))
            Allure.step("Не найден заголовок модального окна при добавлении голосового меню", Status.FAILED);
        if(type.equals("Перейти в меню")) addVoiceMenu(name, type, pathSound, number, goToMenu[0]);
        else addVoiceMenu(type, type, pathSound, number);
        saveAndVerifyMenu(name, description);
    }

    @Override
    public void addEntryPoint(String number, String aon, String menu, String schedule) {
        if(clickButtonAdd(IVR_ENTRY_POINTS_TITLE)
                .isModalWindow(true)
                .getTextTitleModalWindow()
                .equals("Создание точки входа"))
            Allure.step("Не найден заголовок модального окна при добавлении чки входа", Status.FAILED);
        sendModalWindowOfEntryPoint(number, aon, menu);
        saveAndVerifyEntryPoint(number, aon, menu, schedule);
    }

    @Override
    public void editMenu(String name, String type, String description, String pathSound, String numberOrTypeMenu) {
        if(!clickButtonTable(IVR_MENU_TITLE, type, IVR_BUTTON_EDIT).getTextTitleModalWindow().equals("Редактирование голосового меню"))
            Allure.step("Не найден заголовок модального окна при редактировании голосового меню " + type, Status.FAILED);
        editVoiceMenu(name, type, pathSound, numberOrTypeMenu);
        isInputNumberDTMF(false).isInputActionDTMF(false);
        saveAndVerifyMenu(name, description);
    }

    @Override
    public void editEntryPoint(String number, String aon, String type, String schedule) {

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
    public void checkLookModalWindowOfMenu(String title, String textGoToMenu, String type, String soundFile, boolean action, boolean dtmfSimpleMenu, boolean dtmf, String... number) {
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
                        "«" + textGoToMenu + "»",
                        "Отсуствует текст «" + textGoToMenu + "» в поле " + timeout_field),
                () -> assertEquals(getFirstTextGoToActionOfSpanOfModalWindow(wrong_number_field, action),
                        "Перейти в меню",
                        "Отсуствует текст Перейти в меню в поле " + wrong_number_field),
                () -> assertEquals(getSecondTextGoToActionOfSpanOfModalWindow(wrong_number_field, action),
                        "«" + textGoToMenu + "»",
                        "Отсуствует текст " + textGoToMenu + " в поле " + wrong_number_field)
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
                    "«" + textGoToMenu + "»",
                    "Отсуствует текст " + textGoToMenu + " в поле DTMF");
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
