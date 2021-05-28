package chat.ros.testing2.server.services;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
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
import java.util.HashMap;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic(value = "Сервисы")
@Feature(value = "Голосовое меню")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesIVRPage.class)
@ExtendWith(WatcherTests.class)
public class TestMenuPage extends CheckIVRMenu {

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
    private static HashMap<String,String> mapMenu;

    @Parameterized.Parameters(name = "{0}")
    private static Iterable<String> receiveMenuItems() {
        ArrayList<String> data = new ArrayList<>();

        for (String item: IVR_MENU_ITEMS) {
            data.add(item);
        }

        return data;
    }

    @BeforeAll
    static void setUp(){
        num = 999;
        mapMenu = new HashMap<>();
    }

    @AfterEach
    void incrementNumber(){
        num++;
    }

    @Story(value = "Загружаем звуковой wav файл")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем звуковой файл wav и описание к нему\n" +
            "3. Проверяем, что звуковой файл wav и описание к нему добавлены в таблицу звуковых файлов")
    @Test
    @Order(1)
    void test_Upload_Sound_File(){
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

        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем Меню \n" +
            "3. Проверяем, что меню успешно добавлено")
    @Order(2)
    @ParameterizedTest(name="Add menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Menu(String type){
        String descriptionMenu = IVR_MENU_DESCRIPTION + " " + type;
        assertAll("1. Добавляем голосовое меню " + type + " \n" +
                        "проверяем, что голосовое меню " + type + " было добавлено в таблицу",
                () -> assertEquals(clickButtonAdd(IVR_MENU_TITLE).isVisibleTitleModalWrapper(),
                        "Новое голосовое меню",
                        "Не найден заголовок модального окна при добавлении голосового меню"),
                () -> {
                    addVoiceMenu(type, type, wavFile1, String.valueOf(num));
                    clickActionButtonOfModalWindow("Сохранить");
                    },
                () -> assertTrue(isItemTable(IVR_MENU_TITLE, descriptionMenu, true),
                        "Описание " + descriptionMenu + " голосового меню " + type + " " +
                                "не найдено в таблице меню")
        );

        checkLookModalWindowOfMenu(type, type, getModalWindow(), wavFile1, true, String.valueOf(num));
        mapMenu.put(type,String.valueOf(num));
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление точки входа")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем точку входа обычным меню\n" +
            "3. Проверяем, что точка входа успешно добавлена")
    @Order(3)
    @ParameterizedTest(name="Add entry point with simple menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Entry_Point_WIth_Simple_Menu(String typeMenu){
        num++;
        String number = String.valueOf(num);
        String aon = typeMenu + " " + number;
        assertAll("1. Добавляем точку входа " + typeMenu + " \n" +
                        "проверяем, что точка входа " + typeMenu + " была добавлена в таблицу",
                () -> assertEquals(clickButtonAdd(IVR_ENTRY_POINTS_TITLE).isVisibleTitleModalWrapper(),
                        "Создание точки входа",
                        "Не найден заголовок модального окна при добавлении точки входа"),
                () -> {
                    sendModalWindowOfEntryPoint(number, aon, typeMenu);
                    clickActionButtonOfModalWindow("Сохранить");
                    },
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, aon, true),
                        "АОН " + aon + " меню " + typeMenu + " " +
                                "не найдено в таблице точек входа"),
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, number, true),
                        "Набираемый номер " + number + " меню " + typeMenu + " " +
                                "не найдено в таблице точек входа"),
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, typeMenu, true),
                        "Меню " + typeMenu + " не найдено в таблице точек входа")
        );
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление перехода в меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем Меню \n" +
            "3. Выбираем тип Перейти в меню\n" +
            "4. Проверяем, что меню успешно добавлено")
    @Order(4)
    @ParameterizedTest(name="Add go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Go_To_Menu(String type){
        String title = "Перейти в меню " + type;
        String descriptionMenu = IVR_MENU_DESCRIPTION + " " + title;
        String numberSimpleMenu = mapMenu.get(type);
        assertAll("1. Добавляем голосовое меню \n" +
                        "2. Выбираем в действиях Перейти в меню -> " + type + " \n" +
                        "проверяем, что голосовое меню было добавлено в таблицу",
                () -> assertEquals(clickButtonAdd(IVR_MENU_TITLE).isVisibleTitleModalWrapper(),
                        "Новое голосовое меню",
                        "Не найден заголовок модального окна при добавлении голосового меню"),
                () -> {
                    addVoiceMenu("Перейти в меню " + type, "Перейти в меню", wavFile1, String.valueOf(num), type);
                    clickActionButtonOfModalWindow("Сохранить");},
                () -> assertTrue(isItemTable(IVR_MENU_TITLE, descriptionMenu, true),
                        "Описание " + descriptionMenu + " голосового меню " +
                                "не найдено в таблице меню")
        );

        checkLookModalWindowOfGoToTheMenu(title, type, type, wavFile1, true, true, true, String.valueOf(num), numberSimpleMenu);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление точки входа с меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем точку входа \n" +
            "3. Проверяем, что точка входа успешно добавлена")
    @Order(5)
    @ParameterizedTest(name="Add entry point with go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Entry_Point_With_Go_To_Menu(String type){
        String titleGoToMenu = "Перейти в меню " + type;
        num++;
        String number = String.valueOf(num);
        String aon = titleGoToMenu + " " + number;
        assertAll("1. Добавляем точку входа " + titleGoToMenu + " \n" +
                        "проверяем, что точка входа " + titleGoToMenu + " была добавлена в таблицу",
                () -> assertEquals(clickButtonAdd(IVR_ENTRY_POINTS_TITLE).isVisibleTitleModalWrapper(),
                        "Создание точки входа",
                        "Не найден заголовок модального окна при добавлении точки входа"),
                () -> {
                    sendModalWindowOfEntryPoint(number, aon, titleGoToMenu);
                    clickActionButtonOfModalWindow("Сохранить");},
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, aon, true),
                        "АОН " + aon + " меню " + titleGoToMenu + " " +
                                "не найдено в таблице точек входа"),
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, number, true),
                        "Набираемый номер " + number + " меню " + titleGoToMenu + " " +
                                "не найдено в таблице точек входа"),
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, titleGoToMenu, true),
                        "Меню " + titleGoToMenu + " не найдено в таблице точек входа")
        );
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление звукового файла без описания")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем звуковой файл без описания \n" +
            "3. Проверяем, что новый файл добавлен в таблицу звуковых файлов")
    @Test
    @Order(6)
    void test_Upload_Sound_File_Without_Description(){
        assertEquals(uploadSoundFile(pathWAVFile2, "")
                        .isVisibleTitleModalWrapper(),
                "Новый звуковой файл",
                "Не найден заголовок модального окна при добавлении звукового файла");
        clickActionButtonOfModalWindow("Сохранить");
        assertTrue(isItemTable(IVR_SOUND_FILES_TITLE, wavFile2, true),
                "Название файла " + wavFile2 + " не найдено в таблице звуковых файлов");
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Редактирование простого меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем Меню \n" +
            "3. Проверяем, что меню было успешно отредактировано")
    @Order(7)
    @ParameterizedTest(name="Edit menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Edit_Simple_Menu(String type){
        num++;
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

        checkLookModalWindowOfMenu(newNameMenu, type, getModalWindow(), wavFile2, false);
        TestStatusResult.setTestResult(true);

    }

    @Story(value = "Редактирование точки входа простым меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем точку входа \n" +
            "3. Проверяем, что точка входа успешно отредактирована")
    @Order(8)
    @ParameterizedTest(name="Edit entry point={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Edit_Entry_Point_With_Simple_Menu(String typeMenu){
        String newMenu = typeMenu + " отредактировано";
        num++;
        String number = String.valueOf(num);
        String aon = newMenu + " " + number;
        assertAll("1. Редактируем точку входа " + typeMenu + " \n" +
                        "проверяем, что точка входа " + newMenu + " отображается в таблице после редактирования",
                () -> assertEquals(clickButtonTable(IVR_ENTRY_POINTS_TITLE, typeMenu, IVR_BUTTON_EDIT)
                                .isVisibleTitleModalWrapper(),
                        "Редактирование точки входа",
                        "Не найден заголовок модального окна при редактировании точки входа " + typeMenu),
                () -> {
                    sendModalWindowOfEntryPoint(number, aon, newMenu);
                    clickActionButtonOfModalWindow("Сохранить");},
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, aon, true),
                        "АОН " + aon + " меню " + typeMenu + " " +
                                "не найдено в таблице точек входа"),
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, number, true),
                        "Набираемый номер " + number + " меню " + typeMenu + " " +
                                "не найдено в таблице точек входа"),
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, newMenu, true),
                        "Меню " + newMenu + " не найдено в таблице точек входа")
        );
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Редактирование меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем меню - переход в меню \n" +
            "3. Проверяем, что меню было успешно отредактировано")
    @Order(9)
    @ParameterizedTest(name="Edit go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Edit_Go_To_Menu(String type){
        String secondText = type + " отредактировано";
        String newTitleGoToMenu = "Перейти в меню " + type + " отредактировано";
        String newDescriptionGoToMenu = IVR_MENU_DESCRIPTION + " " + newTitleGoToMenu;
        assertAll("1. Добавляем голосовое меню \n" +
                        "2. Выбираем в действиях Перейти в меню -> " + secondText + " \n" +
                        "3. Проверяем, что голосовое меню " + newTitleGoToMenu + " было добавлено в таблицу",
                () -> assertEquals(clickButtonTable(IVR_MENU_TITLE, "Перейти в меню " + type, IVR_BUTTON_EDIT).isVisibleTitleModalWrapper(),
                        "Редактирование голосового меню",
                        "Не найден заголовок модального окна при редактировании голосового меню"),
                () -> {
                    editVoiceMenu(newTitleGoToMenu, "Перейти в меню", wavFile2, secondText);
                    clickActionButtonOfModalWindow("Сохранить");
                },
                () -> assertTrue(isItemTable(IVR_MENU_TITLE, newDescriptionGoToMenu, true),
                        "Описание " + newDescriptionGoToMenu + " голосового меню " +
                                "не найдено в таблице меню")
        );

        checkLookModalWindowOfGoToTheMenu(newTitleGoToMenu, secondText, type, wavFile2, true, false, false);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Редактирование точки входа c меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем точку входа \n" +
            "3. Проверяем, что точка входа успешно отредактирована")
    @Order(10)
    @ParameterizedTest(name="Edit entry point with go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Edit_Entry_Point_With_Go_To_Menu(String typeMenu){
        String nameMenu = "";
        if(TestStatusResult.getTestResult().get("Edit go to menu=" + typeMenu) == null || ! TestStatusResult.getTestResult().get("Edit go to menu=" + typeMenu)) nameMenu = "Перейти в меню " + typeMenu;
        else nameMenu = "Перейти в меню " + typeMenu + " отредактировано";
        num++;
        String oldTitleEntryPoint = "Перейти в меню " + typeMenu;
        String number = String.valueOf(num);
        String aon = "Перейти в меню " + typeMenu + " отредактировано " + number;
        String finalNameMenu = nameMenu;
        assertAll("1. Редактируем точку входа " + oldTitleEntryPoint + " \n" +
                        "проверяем, что точка входа " + nameMenu + " отображается в таблице после редактирования",
                () -> assertEquals(clickButtonTable(IVR_ENTRY_POINTS_TITLE, oldTitleEntryPoint, IVR_BUTTON_EDIT)
                                .isVisibleTitleModalWrapper(),
                        "Редактирование точки входа",
                        "Не найден заголовок модального окна при редактировании точки входа " + oldTitleEntryPoint),
                () -> {
                    sendModalWindowOfEntryPoint(number, aon, finalNameMenu);
                    clickActionButtonOfModalWindow("Сохранить");
                    },
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, aon, true),
                        "АОН " + aon + " меню " + finalNameMenu + " " +
                                "не найдено в таблице точек входа"),
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, number, true),
                        "Набираемый номер " + number + " меню " + finalNameMenu + " " +
                                "не найдено в таблице точек входа"),
                () -> assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, finalNameMenu, true),
                        "Меню " + finalNameMenu + " не найдено в таблице точек входа")
        );
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Удаляем точки входа с простым меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем точки входа \n" +
            "3. Проверяем, что в таблице удалённые точки входа не отображается")
    @Order(11)
    @ParameterizedTest(name="Delete entry point with simple menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Delete_Entry_Point_With_Simple_Menu(String type){
        String titleSimpleMenu = "";
        if(TestStatusResult.getTestResult().get("Edit entry point=" + type) == null || ! TestStatusResult.getTestResult().get("Edit entry point=" + type)) titleSimpleMenu = type;
        else titleSimpleMenu = type + " отредактировано";
        clickButtonTable(IVR_ENTRY_POINTS_TITLE, titleSimpleMenu, IVR_BUTTON_DELETE);
        assertTrue(isFormConfirmActions(true),
                "Не появилась форма для удаления точки входа " + titleSimpleMenu);
        clickButtonConfirmAction("Продолжить");
        assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, titleSimpleMenu, false),
                "Точка входа " + titleSimpleMenu + " найдена в таблице после удаления");
    }

    @Story(value = "Удаляем точки входа с меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем точки входа \n" +
            "3. Проверяем, что в таблице удалённые точки входа не отображается")
    @Order(12)
    @ParameterizedTest(name="Delete entry point with go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Delete_Entry_Point_With_Go_To_Menu(String type){
        String titleGoToMenu = "";
        if(TestStatusResult.getTestResult().get("Edit entry point with go to menu=" + type) == null || ! TestStatusResult.getTestResult().get("Edit entry point with go to menu=" + type)) titleGoToMenu = "Перейти в меню " + type;
        else titleGoToMenu = "Перейти в меню " + type + " отредактировано";
        clickButtonTable(IVR_ENTRY_POINTS_TITLE, titleGoToMenu, IVR_BUTTON_DELETE);
        assertTrue(isFormConfirmActions(true),
                "Не появилась форма для удаления точки входа " + titleGoToMenu);
        clickButtonConfirmAction("Продолжить");
        assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, titleGoToMenu, false),
                "Точка входа " + titleGoToMenu + " найдена в таблице после удаления");
    }

    @Story(value = "Удаляем простое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем Меню \n" +
            "3. Проверяем, что в таблице удалённое меню не отображается")
    @Order(13)
    @ParameterizedTest(name="Delete menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Delete_Simple_Menu(String type){
        String menu = "";
        if(TestStatusResult.getTestResult().get("Edit menu=" + type) == null || ! TestStatusResult.getTestResult().get("Edit menu=" + type)) menu = type;
        else menu = type + " отредактировано";
        clickButtonTable(IVR_MENU_TITLE, menu, IVR_BUTTON_DELETE);
        assertTrue(isFormConfirmActions(true),
                "Не появилась форма для удаления меню " + menu);
        clickButtonConfirmAction("Удалить");
        assertTrue(isItemTable(IVR_MENU_TITLE, menu, false),
                "Название меню " + menu + " найдено в таблице меню после удаления");
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем, настройки меню перехода в меню, после удаления простого меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Открываем просмотр настроек меню перехода в другое меню\n" +
            "3. Проверяем, что отсутствуют ссылки в настройках меню")
    @Order(14)
    @ParameterizedTest(name="Check links go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_No_Links_Go_To_Menu(String type){
        String titleGoToMenu = "Перейти в меню " + type + " отредактировано";
        String secondText = type + " отредактировано";
        checkLookModalWindowOfGoToTheMenu(titleGoToMenu, secondText, type, wavFile2, false, false, false);
    }

    @Story(value = "Удаляем меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем Меню \n" +
            "3. Проверяем, что в таблице удалённое меню не отображается")
    @Order(15)
    @ParameterizedTest(name="Delete go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Delete_Go_To_Menu(String type){
        String menu = "Перейти в меню " + type + " отредактировано";
        /*if(TestStatusResult.getTestResult().get("Edit go to menu=" + type) == null || ! TestStatusResult.getTestResult().get("Edit go to menu=" + type)) menu = "Перейти в меню " + type;
        else menu = "Перейти в меню " + type + " отредактировано";*/
        clickButtonTable(IVR_MENU_TITLE, menu, IVR_BUTTON_DELETE);
        assertTrue(isFormConfirmActions(true),
                "Не появилась форма для удаления меню " + menu);
        clickButtonConfirmAction("Удалить");
        assertTrue(isItemTable(IVR_MENU_TITLE, menu, false),
                "Название меню " + menu + " найдено в таблице меню после удаления");
    }
}
