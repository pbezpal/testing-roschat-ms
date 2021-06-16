package chat.ros.testing2.server.services;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.services.codefortests.SoundPage;
import io.qameta.allure.*;
import io.qameta.allure.model.Status;
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
public class TestMenuPage extends SoundPage {

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
        uploadMusicFile(pathWAVFile1, wavFile1);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем Меню \n" +
            "3. Проверяем, что меню успешно добавлено")
    @Order(2)
    @ParameterizedTest(name="Add menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Simple_Menu(String type){
        String descriptionMenu = IVR_MENU_DESCRIPTION + " " + type;
        addMenu(type, type, descriptionMenu, wavFile1, String.valueOf(num));
        mapMenu.put(type,String.valueOf(num));
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем отображение настроек простого меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем у меню кнопку Показать \n" +
            "3. Проверяем, что настройки отображаются корректно")
    @Order(3)
    @ParameterizedTest(name="Look simple menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Look_Simple_Menu(String type){
        String numberSimpleMenu = mapMenu.get(type);
        checkLookModalWindowOfMenu(type, type, getModalWindow(), wavFile1, true, numberSimpleMenu);
    }

    @Story(value = "Добавление точки входа")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем точку входа обычным меню\n" +
            "3. Проверяем, что точка входа успешно добавлена")
    @Order(4)
    @Disabled
    @ParameterizedTest(name="Add entry point with simple menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Entry_Point_With_Simple_Menu(String typeMenu){
        num++;
        String number = String.valueOf(num);
        String aon = typeMenu + " " + number;
        String schedule = "Расписание " + typeMenu;
        if(clickButtonAdd(IVR_ENTRY_POINTS_TITLE)
                .isModalWindow(true)
                .getTextTitleModalWindow().equals("Создание точки входа"))
            Allure.step("Не найден заголовок модального окна при добавлении чки входа", Status.FAILED);
        sendModalWindowOfEntryPoint(number, aon, typeMenu);
        clickActionButtonOfModalWindow("Сохранить")
                .isModalWindow(false)
                .isItemTable(IVR_ENTRY_POINTS_TITLE, aon, true)
                .isItemTable(IVR_ENTRY_POINTS_TITLE, number, true)
                .isItemTable(IVR_ENTRY_POINTS_TITLE, typeMenu, true);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление меню с переходом в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем Меню \n" +
            "3. Выбираем тип Перейти в меню\n" +
            "4. Проверяем, что меню успешно добавлено")
    @Order(5)
    @ParameterizedTest(name="Add go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Go_To_Menu(String type){
        String title = "Перейти в меню " + type;
        String descriptionMenu = IVR_MENU_DESCRIPTION + " " + title;
        String numberSimpleMenu = mapMenu.get(type);
        addMenu("Перейти в меню " + type, "Перейти в меню", descriptionMenu, wavFile1, String.valueOf(num), type);
        mapMenu.put(title, String.valueOf(num));
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем отображение настроек меню с переходом в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем у меню кнопку Показать \n" +
            "3. Проверяем, что настройки отображаются корректно")
    @Order(6)
    @ParameterizedTest(name="Look go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Look_Go_To_Menu(String type){
        String numberSimpleMenu = mapMenu.get(type);
        String titleGoToMenu = "Перейти в меню " + type;
        String numberGoToMenu = mapMenu.get(titleGoToMenu);
        checkLookModalWindowOfMenu(titleGoToMenu, type, type, wavFile1, true, true, true, numberGoToMenu, numberSimpleMenu);
    }

    @Story(value = "Добавление точки входа с меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем точку входа \n" +
            "3. Проверяем, что точка входа успешно добавлена")
    @Order(7)
    @Disabled
    @ParameterizedTest(name="Add entry point with go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Entry_Point_With_Go_To_Menu(String type){
        String titleGoToMenu = "Перейти в меню " + type;
        num++;
        String number = String.valueOf(num);
        String aon = titleGoToMenu + " " + number;
        /*if(clickButtonAdd(IVR_ENTRY_POINTS_TITLE).getTextTitleModalWindow().equals("Создание точки входа"))
            Allure.step("Не найден заголовок модального окна при добавлении чки входа", Status.FAILED);
        assertAll("1. Добавляем точку входа " + titleGoToMenu + " \n" +
                        "проверяем, что точка входа " + titleGoToMenu + " была добавлена в таблицу",
                () -> assertEquals(clickButtonAdd(IVR_ENTRY_POINTS_TITLE).getTextTitleModalWindow(),
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
        );*/
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление второго звукового файла")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем звуковой файл \n" +
            "3. Проверяем, что новый файл добавлен в таблицу звуковых файлов")
    @Test
    @Order(8)
    void test_Upload_Sound_File_2(){
        uploadMusicFile(pathWAVFile2, wavFile2);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Редактирование простого меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем Меню \n" +
            "3. Проверяем, что меню было успешно отредактировано")
    @Order(9)
    @ParameterizedTest(name="Edit menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Edit_Simple_Menu(String type){
        num++;
        String oldNameMenu = type;
        String newNameMenu = type + " отредактировано";
        String newDescriptionMenu = IVR_MENU_DESCRIPTION + " " + newNameMenu;
        editMenu(newNameMenu, oldNameMenu, type, newDescriptionMenu, wavFile2, String.valueOf(num));
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем отображение настроек простого меню после редактирования")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем у меню кнопку Показать \n" +
            "3. Проверяем, что настройки отображаются корректно")
    @Order(10)
    @ParameterizedTest(name="Look simple menu after edit={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Look_Simple_Menu_After_Edit(String type){
        String nameMenu = type + " отредактировано";
        checkLookModalWindowOfMenu(nameMenu, type, getModalWindow(), wavFile2, false);
    }

    @Story(value = "Редактирование точки входа простым меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем точку входа \n" +
            "3. Проверяем, что точка входа успешно отредактирована")
    @Order(11)
    @Disabled
    @ParameterizedTest(name="Edit entry point={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Edit_Entry_Point_With_Simple_Menu(String typeMenu){
        String newMenu = typeMenu + " отредактировано";
        num++;
        String number = String.valueOf(num);
        /*String aon = newMenu + " " + number;
        assertAll("1. Редактируем точку входа " + typeMenu + " \n" +
                        "проверяем, что точка входа " + newMenu + " отображается в таблице после редактирования",
                () -> assertEquals(clickButtonTable(IVR_ENTRY_POINTS_TITLE, typeMenu, IVR_BUTTON_EDIT)
                                .getTextTitleModalWindow(),
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
        );*/
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Редактирование меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем меню - переход в меню \n" +
            "3. Проверяем, что меню было успешно отредактировано")
    @Order(12)
    @ParameterizedTest(name="Edit go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Edit_Go_To_Menu(String type){
        String simpleMenu = type + " отредактировано";
        String oldNameMenu = "Перейти в меню " + type;
        String newTitleGoToMenu = "Перейти в меню " + type + " отредактировано";
        String newDescriptionGoToMenu = IVR_MENU_DESCRIPTION + " " + newTitleGoToMenu;
        editMenu(newTitleGoToMenu, oldNameMenu, "Перейти в меню", newDescriptionGoToMenu, wavFile2, simpleMenu);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем отображение настроек меню с переходом в другое меню после редактирования")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем у меню кнопку Показать \n" +
            "3. Проверяем, что настройки отображаются корректно")
    @Order(13)
    @ParameterizedTest(name="Look go to menu after edit={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Look_Go_to_Menu_After_Edit(String type){
        String newTitleGoToMenu = "Перейти в меню " + type + " отредактировано";
        String simpleMenu = type + " отредактировано";
        checkLookModalWindowOfMenu(newTitleGoToMenu, simpleMenu, type, wavFile2, true, false, false);
    }

    @Story(value = "Редактирование точки входа c меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем точку входа \n" +
            "3. Проверяем, что точка входа успешно отредактирована")
    @Order(14)
    @Disabled
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
        /*assertAll("1. Редактируем точку входа " + oldTitleEntryPoint + " \n" +
                        "проверяем, что точка входа " + nameMenu + " отображается в таблице после редактирования",
                () -> assertEquals(clickButtonTable(IVR_ENTRY_POINTS_TITLE, oldTitleEntryPoint, IVR_BUTTON_EDIT)
                                .getTextTitleModalWindow(),
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
        );*/
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Удаляем точки входа с простым меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем точки входа \n" +
            "3. Проверяем, что в таблице удалённые точки входа не отображается")
    @Order(15)
    @Disabled
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
        /*assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, titleSimpleMenu, false),
                "Точка входа " + titleSimpleMenu + " найдена в таблице после удаления");*/
    }

    @Story(value = "Удаляем точки входа с меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем точки входа \n" +
            "3. Проверяем, что в таблице удалённые точки входа не отображается")
    @Order(16)
    @Disabled
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
        /*assertTrue(isItemTable(IVR_ENTRY_POINTS_TITLE, titleGoToMenu, false),
                "Точка входа " + titleGoToMenu + " найдена в таблице после удаления");*/
    }

    @Story(value = "Удаляем простое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем Меню \n" +
            "3. Проверяем, что в таблице удалённое меню не отображается")
    @Order(17)
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
        /*assertTrue(isItemTable(IVR_MENU_TITLE, menu, false),
                "Название меню " + menu + " найдено в таблице меню после удаления");*/
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем, настройки меню перехода в меню, после удаления простого меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Открываем просмотр настроек меню перехода в другое меню\n" +
            "3. Проверяем, что отсутствуют ссылки в настройках меню")
    @Order(18)
    @ParameterizedTest(name="Check links go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_No_Links_Go_To_Menu(String type){
        String titleGoToMenu = "Перейти в меню " + type + " отредактировано";
        String secondText = type + " отредактировано";
        checkLookModalWindowOfMenu(titleGoToMenu, secondText, type, wavFile2, false, false, false);
    }

    @Story(value = "Удаляем меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем Меню \n" +
            "3. Проверяем, что в таблице удалённое меню не отображается")
    @Order(19)
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
        /*assertTrue(isItemTable(IVR_MENU_TITLE, menu, false),
                "Название меню " + menu + " найдено в таблице меню после удаления");*/
    }
}
