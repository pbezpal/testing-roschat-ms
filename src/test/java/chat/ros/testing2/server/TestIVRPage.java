package chat.ros.testing2.server;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.IVRPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized;

import java.util.ArrayList;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic(value = "Голосовое меню")
@Feature(value = "Звуковые файлы")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
public class TestIVRPage extends IVRPage {

    private static boolean status_add_sound_file_with_description;
    private static boolean status_add_sound_file_without_description;
    private String wavFile1 = "tt-monkeys.wav";
    private String pathWAVFile1 = getClass().
            getClassLoader().
            getResource("sound/" + wavFile1).
            getFile();

    private String wavFile2 = "vm-msginstruct.wav";
    private String pathWAVFile2 = getClass().
            getClassLoader().
            getResource("sound/" + wavFile2).
            getFile();
    private static int num;

    @Parameterized.Parameters(name = "{0}")
    private static Iterable<String> receiveMenuItems() {
        ArrayList<String> data = new ArrayList<>();

        for (String item: IVR_MENU_ITEMS) {
            data.add(item);
        }

        return data;
    }

    private void checkLookModalWindowOfMenu(String title, String type, String soundFile, boolean dtmf){
        assertTrue(isItemTable(IVR_MENU_TITLE, type, true),
                "Название  " + type + " не найдено в таблице меню");
        assertAll("Проверяем, отображение элементов в модальном окне" +
                        "для просмотра настроек меню",
                () -> assertEquals(clickButtonTable(IVR_MENU_TITLE, type, IVR_MENU_BUTTON_LOOK_MENU)
                                .isVisibleTitleModalWrapper(),
                        title,
                        "Не найден заголовок модального окна при просмотре настроек меню " + title),
                () -> assertTrue(isIconSoundOfModalWindowVoiceMenu(),
                        "Отсутствует иконка звукового файла"),
                () -> assertTrue(isIconTimeOutOfModalWindowMenu(),
                        "Отсутствует иконка По таймауту"),
                () -> assertTrue(isIconErrorOutlineOfModalWindowMenu(),
                        "Отсутствует иконка неправильно набранного номера"),
                () -> assertEquals(getTextSpanNameOfModalWindowMenu("Звуковой файл"),
                        soundFile,
                        "Отсуствтует значение " + soundFile + " в модальном окне просмотра настроек Звуковой файл"),
                () -> assertEquals(getTextSpanNameOfModalWindowMenu("По таймауту"),
                        type,
                        "Отсуствтует значение " + type + " в модальном окне просмотра настроек По таймауту"),
                () -> assertEquals(getTextSpanNameOfModalWindowMenu("При неправильном наборе"),
                        type,
                        "Отсуствтует значение " + type + " в модальном окне просмотра настроек При неправильном наборе")
        );

        if(dtmf){
            assertAll("Проверяем, отображается ли настройки DTMF",
                    () -> assertTrue(isIconDTMFOfModalWindowMenu(),
                            "Отсутствует иконка DTMF"),
                    () -> assertEquals(getNumberDTMFOfModalWindowMenu(),
                            String.valueOf(num),
                            "Отсутствует номер в модальном окне просмотр настроек DTMF"),
                    () -> assertEquals(getTextDTMFOfModalWindowMenu(),
                            type,
                            "Отсуствтует значение " + type + " в модальном окне просмотра настроек DTMF")
            );
        }

        clickActionButtonOfModalWindow("Закрыть");
    }

    @BeforeAll
    static void setUp(){
        status_add_sound_file_with_description = false;
        status_add_sound_file_without_description = false;
        num = 1000;
    }

    @Story(value = "Загружаем звуковой wav файл")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем звуковой файл wav и описание к нему\n" +
            "3. Проверяем, что звуковой файл wav и описание к нему добавлены в таблицу звуковых файлов")
    @Test
    @Order(1)
    void test_Upload_Sound_File_WAV(){
        assertEquals(uploadSoundFile(pathWAVFile1, IVR_SOUND_FILES_DESCRIPTION_WAV_1)
                        .isVisibleTitleModalWrapper(),
                "Новый звуковой файл",
                "Не найден заголовок модального окна при добавлении звукового файла");
        clickActionButtonOfModalWindow("Сохранить");
        assertAll("Проверяем, добавлен файл и описание к нему в таблицу",
                () -> assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, wavFile1, true),
                        "Название файла " + wavFile1 + " не найдено в таблице звуковых файлов"),
                () -> assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, IVR_SOUND_FILES_DESCRIPTION_WAV_1, true),
                        "Описание " + IVR_SOUND_FILES_DESCRIPTION_WAV_1 + " звукового файла " + wavFile1 + " " +
                                "не найдено в таблице звуковых файлов")
        );

        status_add_sound_file_with_description = true;
    }

    @Story(value = "Добавление меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем Меню \n" +
            "3. Проверяем, что меню успешно добавлено")
    @Order(2)
    @ParameterizedTest(name="#{index} - Add menu=''{0}''")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Menu(String type){
        String descriptionMenu = IVR_MENU_DESCRIPTION + " " + type;
        assertTrue(status_add_sound_file_with_description, "Тест с добавлением звукового файла " + wavFile1 + " провалился. " +
                "Невозможно продолжать тестирование");
        assertAll("1. Добавляем голосовое меню " + type + " \n" +
                        "проверяем, что голосовое меню " + type + " было добавлено в таблицу",
                () -> assertEquals(clickButtonAdd(IVR_MENU_TITLE).isVisibleTitleModalWrapper(),
                        "Новое голосовое меню",
                        "Не найден заголовок модального окна при добавлении голосового меню"),
                () -> {
                    addVoiceMenu(type, type, wavFile1, String.valueOf(num)).clickActionButtonOfModalWindow("Сохранить");},
                () -> assertTrue(isItemTable(IVR_MENU_TITLE, descriptionMenu, true),
                        "Описание " + descriptionMenu + " голосового меню " + type + " " +
                                "не найдено в таблице меню")
        );

        checkLookModalWindowOfMenu(type, type, wavFile1, true);

        num++;

    }

    @Story(value = "Добавление точки входа")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем точку входа \n" +
            "3. Проверяем, что точка входа успешно добавлена")
    @Order(3)
    @ParameterizedTest(name="#{index} - Add entry point=''{0}''")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Entry_Point(String typeMenu){
        assertTrue(isItemTable(IVR_MENU_TITLE, typeMenu, true),
                "Название  " + typeMenu + " не найдено в таблице меню");
        String number = String.valueOf(num);
        String aon = typeMenu + " " + number;
        assertAll("1. Добавляем точку входа " + typeMenu + " \n" +
                        "проверяем, что точка входа " + typeMenu + " была добавлена в таблицу",
                () -> assertEquals(clickButtonAdd(IVR_ENTRY_POINTS_TITLE).isVisibleTitleModalWrapper(),
                        "Создание точки входа",
                        "Не найден заголовок модального окна при добавлении точки входа"),
                () -> {
                    sendModalWindowOfEntryPoint(number, aon, typeMenu).clickActionButtonOfModalWindow("Сохранить");},
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, aon, true),
                        "АОН " + aon + " меню " + typeMenu + " " +
                                "не найдено в таблице точек входа"),
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, number, true),
                        "Набираемый номер " + number + " меню " + typeMenu + " " +
                                "не найдено в таблице точек входа"),
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, typeMenu, true),
                        "Меню " + typeMenu + " не найдено в таблице точек входа")
        );
        num++;
    }

    @Story(value = "Добавление звукового файла без описания")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем звуковой файл без описания \n" +
            "3. Проверяем, что новый файл добавлен в таблицу звуковых файлов")
    @Test
    @Order(4)
    void test_Upload_Sound_File_Without_Description(){
        assertEquals(uploadSoundFile(pathWAVFile2, "")
                        .isVisibleTitleModalWrapper(),
                "Новый звуковой файл",
                "Не найден заголовок модального окна при добавлении звукового файла");
        clickActionButtonOfModalWindow("Сохранить");
        assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, wavFile2, true),
                "Название файла " + wavFile2 + " не найдено в таблице звуковых файлов");
        status_add_sound_file_without_description = true;
    }

    @Story(value = "Редактирование меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем Меню \n" +
            "3. Проверяем, что меню было успешно отредактировано")
    @Order(5)
    @ParameterizedTest(name="#{index} - Edit menu=''{0}''")
    @MethodSource(value = "receiveMenuItems")
    void test_Edit_Menu(String type){
        assertTrue(status_add_sound_file_without_description, "Тест с добавлением звукового файла " + wavFile2 + " провалился. " +
                "Невозможно продолжать тестирование");
        String newNameMenu = type + " отредактировано";
        String newDescriptionMenu = IVR_MENU_DESCRIPTION + " " + newNameMenu;
        assertAll("1. Редактируем голосовое меню " + type + " \n" +
                        "проверяем, что голосовое меню " + type + " было отредактировано",
                () -> assertEquals(clickButtonTable(IVR_MENU_TITLE, type, IVR_BUTTON_EDIT)
                                .isVisibleTitleModalWrapper(),
                        "Редактирование голосового меню",
                        "Не найден заголовок модального окна при редактировании голосового меню " + type),
                () -> {
                    editVoiceMenu(newNameMenu, type, wavFile2, String.valueOf(num)); },
                () -> assertTrue(isInputNumberDTMF(false),
                        "Поле Набрано в разделе DTMF отображается после удаления"),
                () -> assertTrue(isInputActionDTMF(false),
                        "Поле Действие в раздала DTMF отображается после удаления"),
                () -> {clickActionButtonOfModalWindow("Сохранить");},
                () -> assertTrue(isItemTable(IVR_MENU_TITLE, newDescriptionMenu, true),
                        "Описание " + newDescriptionMenu + " голосового меню " + newDescriptionMenu + " " +
                                "не найдено в таблице меню")
        );

        checkLookModalWindowOfMenu(newNameMenu, type, wavFile2, false);

    }

    @Story(value = "Добавление точки входа")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем точку входа \n" +
            "3. Проверяем, что точка входа успешно добавлена")
    @Order(6)
    @ParameterizedTest(name="#{index} - Edit entry point=''{0}''")
    @MethodSource(value = "receiveMenuItems")
    void test_Edit_Entry_Point(String typeMenu){
        String newMenu = typeMenu + " отредактировано";
        assertTrue(isItemTable(IVR_MENU_TITLE, newMenu, true),
                "Название  " + newMenu + " не найдено в таблице меню");
        String number = String.valueOf(num);
        String aon = newMenu + " " + number;
        assertAll("1. Редактируем точку входа " + typeMenu + " \n" +
                        "проверяем, что точка входа " + newMenu + " отображается в таблице после редактирования",
                () -> assertEquals(clickButtonTable(IVR_ENTRY_POINTS_TITLE, typeMenu, IVR_BUTTON_EDIT)
                                .isVisibleTitleModalWrapper(),
                        "Редактирование точки входа",
                        "Не найден заголовок модального окна при редактировании точки входа " + typeMenu),
                () -> {
                    sendModalWindowOfEntryPoint(number, aon, typeMenu).clickActionButtonOfModalWindow("Сохранить");},
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, aon, true),
                        "АОН " + aon + " меню " + typeMenu + " " +
                                "не найдено в таблице точек входа"),
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, number, true),
                        "Набираемый номер " + number + " меню " + typeMenu + " " +
                                "не найдено в таблице точек входа"),
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, typeMenu, true),
                        "Меню " + typeMenu + " не найдено в таблице точек входа")
        );
        num++;
    }

    @Story(value = "Удаляем меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем Меню \n" +
            "3. Проверяем, что в таблице удалённое меню не отображается")
    @Order(7)
    @ParameterizedTest(name="#{index} - Delete menu=''{0}''")
    @MethodSource(value = "receiveMenuItems")
    void test_Delete_Menu(String type){
        String menu = type + " отредактировано";
        clickButtonTable(IVR_MENU_TITLE, menu, IVR_BUTTON_DELETE);
        assertTrue(isFormConfirmActions(true),
                "Не появилась форма для удаления меню " + menu);
        clickButtonConfirmAction("Удалить");
        assertTrue(isItemTable(IVR_MENU_TITLE, menu, false),
                "Название меню " + menu + " найдено в таблице меню после удаления");
    }

    @Story(value = "Удаляем точки входа")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем точки входа \n" +
            "3. Проверяем, что в таблице удалённые точки входа не отображается")
    @Order(8)
    @ParameterizedTest(name="#{index} - Delete entry point=''{0}''")
    @MethodSource(value = "receiveMenuItems")
    void test_Delete_Entry_Point(String type){
        String menu = type + " отредактировано";
        clickButtonTable(IVR_ENTRY_POINTS_TITLE, menu, IVR_BUTTON_DELETE);
        assertTrue(isFormConfirmActions(true),
                "Не появилась форма для удаления точки входа " + menu);
        clickButtonConfirmAction("Продолжить");
        assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, menu, false),
                "Точка входа " + menu + " найдена в таблице после удаления");
    }

    @Story(value = "Удаляем звуковой файл")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем звуковой файл \n" +
            "3. Проверяем, что в таблице звуковых файлов отсуствтует удленный звуковой файл")
    @Test
    @Order(9)
    void test_Delete_Sound_File(){
        clickButtonTable(IVR_SOUND_FILES_TITLE, wavFile1, IVR_BUTTON_DELETE);
        assertTrue(isFormConfirmActions(true),
                "Не появилась форма для удаления звукового файла");
        clickButtonConfirmAction("Удалить");
        assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, wavFile1, false),
                "Название файла " + wavFile1 + " найдено в таблице звуковых файлов после удаления файла");
    }

    @Story(value = "Редактируем звуковой фал")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Выбираем редактировать звуковой файл добавленный в первом тесте \n" +
            "3. Меняем wav файл на файл MP3 и описание к файлу \n" +
            "4. Проверяем, что новый файл и описание добавлены в таблицу звуковых файлов")
    @Test
    @Order(10)
    void test_Edit_Sound_File() {
        assertTrue(status_add_sound_file_without_description, "Тест с добавлением звукового файла " + wavFile2 + " провалился. " +
                "Невозможно продолжать тестирование");
        assertAll("Проверяем, добавлен файл и описание к нему в таблицу",
                () -> assertEquals(clickButtonTable(IVR_SOUND_FILES_TITLE, wavFile2, IVR_BUTTON_EDIT)
                                .isVisibleTitleModalWrapper(),
                        "Редактирование звукового файла",
                        "Не найден заголовок модального окна при добавлении звукового файла"),
                () -> {
                    uploadSoundFileByModalWindow(pathWAVFile1, IVR_SOUND_FILES_DESCRIPTION_WAV_2)
                            .clickActionButtonOfModalWindow("Сохранить");
                },
                () -> assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, wavFile1, true),
                        "Название файла " + wavFile1 + " не найдено в таблице звуковых файлов")
        );

    }

}
