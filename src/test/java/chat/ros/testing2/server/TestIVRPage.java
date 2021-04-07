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

    private static boolean status_add_sound_file;
    private String wavFile = "test_sound.wav";
    private String pathWAVFile = getClass().
            getClassLoader().
            getResource("sound/" + wavFile).
            getFile();

    private String mp3File = "test_sound.mp3";
    private String pathMP3File = getClass().
            getClassLoader().
            getResource("sound/" + mp3File).
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

    private void checkLookModalWindowOfMenu(String name){
        assertTrue(isItemTable(IVR_MENU_TITLE, name, true),
                "Название  " + name + " не найдено в таблице меню");
        assertAll("Проверяем, отображение элементов в модальном окне" +
                "для просмотра настроек меню",
                () -> assertEquals(clickButtonTable(IVR_MENU_TITLE, name, IVR_MENU_BUTTON_LOOK_MENU)
                                .isVisibleTitleModalWrapper(),
                        name,
                        "Не найден заголовок модального окна при просмотре настроек меню " + name),
                () -> assertTrue(isIconSoundOfModalWindowVoiceMenu(),
                        "Отсутствует иконка звукового файла"),
                () -> assertTrue(isIconTimeOutOfModalWindowMenu(),
                        "Отсутствует иконка По таймауту"),
                () -> assertTrue(isIconErrorOutlineOfModalWindowMenu(),
                        "Отсутствует иконка неправильно набранного номера"),
                () -> assertTrue(isIconDTMFOfModalWindowMenu(),
                        "Отсутствует иконка DTMF"),
                () -> assertEquals(getTextSpanNameOfModalWindowMenu("Звуковой файл"),
                        wavFile,
                        "Отсуствтует значение " + wavFile + " в модальном окне просмотра настроек Звуковой файл"),
                () -> assertEquals(getTextSpanNameOfModalWindowMenu("По таймауту"),
                        name,
                        "Отсуствтует значение " + name + " в модальном окне просмотра настроек По таймауту"),
                () -> assertEquals(getTextSpanNameOfModalWindowMenu("При неправильном наборе"),
                        name,
                        "Отсуствтует значение " + name + " в модальном окне просмотра настроек При неправильном наборе"),
                () -> assertEquals(getNumberDTMFOfModalWindowMenu(),
                        "1000",
                        "Отсутствует номер в модальном окне просмотр настроек DTMF"),
                () -> assertEquals(getTextDTMFOfModalWindowMenu(),
                        name,
                        "Отсуствтует значение " + name + " в модальном окне просмотра настроек DTMF")
        );

        clickButtonPrimaryOfModalWindow();
    }

    @BeforeAll
    static void setUp(){
        status_add_sound_file = false;
        num = 1000;
    }

    @Story(value = "Загружаем звуковой wav файл")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем звуковой файл wav и описание к нему\n" +
            "3. Проверяем, что звуковой файл wav и описание к нему добавлены в таблицу звуковых файлов")
    @Test
    @Order(1)
    @Disabled
    void test_Upload_Sound_File_WAV(){
        assertEquals(uploadSoundFile(pathWAVFile, IVR_SOUND_FILES_DESCRIPTION_WAV)
                        .isVisibleTitleModalWrapper(),
                "Новый звуковой файл",
                "Не найден заголовок модального окна при добавлении звукового файла");
        clickButtonPrimaryOfModalWindow();
        assertAll("Проверяем, добавлен файл и описание к нему в таблицу",
                () -> assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, wavFile, true),
                        "Название файла " + wavFile + " не найдено в таблице звуковых файлов"),
                () -> assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, IVR_SOUND_FILES_DESCRIPTION_WAV, true),
                        "Описание " + IVR_SOUND_FILES_DESCRIPTION_WAV + " звукового файла " + wavFile + " " +
                                "не найдено в таблице звуковых файлов")
        );

        status_add_sound_file = true;
    }

    @Story(value = "Добавление меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем Меню \n" +
            "3. Проверяем, что меню успешно добавлено")
    @Order(2)
    @ParameterizedTest(name="#{index} - Add menu=''{0}''")
    @MethodSource(value = "receiveMenuItems")
    @Disabled
    void test_Add_Menu(String name){
        String descriptionMenu = IVR_MENU_DESCRIPTION + " " + name;
        assertTrue(status_add_sound_file, "Тест с добавлением звукового файла " + wavFile + " провалился. " +
                "Невозможно продолжать тестирование");
        assertAll("1. Добавляем голосовое меню " + name + " \n" +
                        "проверяем, что голосовое меню " + name + " было добавлено в таблицу",
                () -> assertEquals(clickButtonAdd(IVR_MENU_TITLE).isVisibleTitleModalWrapper(),
                        "Новое голосовое меню",
                        "Не найден заголовок модального окна при добавлении голосового меню"),
                () -> {
                    sendModalWindowByMenu(name, wavFile).clickButtonPrimaryOfModalWindow();},
                () -> assertTrue(isItemTable(IVR_MENU_TITLE, descriptionMenu, true),
                        "Описание " + descriptionMenu + " голосового меню " + name + " " +
                                "не найдено в таблице меню")
        );

        checkLookModalWindowOfMenu(name);

    }

    @Story(value = "Добавление точки входа")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем точку входа \n" +
            "3. Проверяем, что точка входа успешно добавлена")
    @Order(3)
    @ParameterizedTest(name="#{index} - Add entry point=''{0}''")
    @MethodSource(value = "receiveMenuItems")
    @Disabled
    void test_Add_Entry_Point(String menu){
        assertTrue(isItemTable(IVR_MENU_TITLE, menu, true),
                "Название  " + menu + " не найдено в таблице меню");
        String number = String.valueOf(num);
        String aon = menu + " " + number;
        assertAll("1. Добавляем точку входа " + menu + " \n" +
                        "проверяем, что точка входа " + menu + " была добавлена в таблицу",
                () -> assertEquals(clickButtonAdd(IVR_ENTRY_POINTS_TITLE).isVisibleTitleModalWrapper(),
                        "Создание точки входа",
                        "Не найден заголовок модального окна при добавлении точки входа"),
                () -> {
                    createEntryPoint(number, aon, menu).clickButtonPrimaryOfModalWindow();},
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, aon, true),
                        "АОН " + aon + " меню " + menu + " " +
                                "не найдено в таблице точек входа"),
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, number, true),
                        "Набираемый номер " + number + " меню " + menu + " " +
                                "не найдено в таблице точек входа"),
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, menu, true),
                        "Меню " + menu + " не найдено в таблице точек входа")
        );
        num++;
    }



    @Story(value = "Добавление звукового файла без описания")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем звуковой файл без описания \n" +
            "3. Проверяем, что новый файл добавлен в таблицу звуковых файлов")
    @Test
    @Order(4)
    @Disabled
    void test_Upload_Sound_File_Without_Description(){
        assertEquals(uploadSoundFile(pathMP3File, "")
                        .isVisibleTitleModalWrapper(),
                "Новый звуковой файл",
                "Не найден заголовок модального окна при добавлении звукового файла");
        clickButtonPrimaryOfModalWindow();
        assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, mp3File, true),
                "Название файла " + mp3File + " не найдено в таблице звуковых файлов");
    }

    @Story(value = "Удаляем звуковой файл")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем звуковой файл \n" +
            "3. Проверяем, что в таблице звуковых файлов отсуствтует удленный звуковой файл")
    @Test
    @Order(5)
    void test_Delete_Sound_File(){
        clickButtonTable(IVR_SOUND_FILES_TITLE, wavFile, IVR_BUTTON_DELETE);
        assertTrue(isFormConfirmActions(true),
                "Не появилась форма для удаления звукового файла");
        clickButtonConfirmAction("Удалить");
        assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, wavFile, false),
                "Название файла " + wavFile + " найдено в таблице звуковых файлов после удаления файла");
    }
}
