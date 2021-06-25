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
    private static int h;
    private static int m;
    private static HashMap<String,String> mapMenu;

    @Parameterized.Parameters(name = "{0}")
    private static Iterable<String> receiveMenuItems() {
        ArrayList<String> data = new ArrayList<>();

        for (String item: IVR_MENU_ITEMS) {
            data.add(item);
        }

        return data;
    }

    private String getHour(int h){
        if(h < 10) return "0" + String.valueOf(h);
        else return String.valueOf(h);
    }

    private String getMinute(int m){
        if(m < 10) return "0" + String.valueOf(m);
        else return String.valueOf(m);
    }

    @BeforeAll
    static void setUp(){
        num = 999;
        h = 0;
        m = 0;
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
    @Disabled
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
        String nameMenu = "Меню " + type;
        String descriptionMenu = IVR_MENU_DESCRIPTION + nameMenu;
        addMenu(nameMenu, type, descriptionMenu, wavFile1, String.valueOf(num));
        mapMenu.put(type,String.valueOf(num));
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем отображение настроек простого меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем у меню кнопку Показать \n" +
            "3. Проверяем, что настройки отображаются корректно")
    @Order(3)
    @Disabled
    @ParameterizedTest(name="Look simple menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Look_Simple_Menu(String type){
        String nameMenu = "Меню " + type;
        String numberSimpleMenu = mapMenu.get(type);
        checkLookModalWindowOfMenu(nameMenu, type, getModalWindow(), wavFile1, true, numberSimpleMenu);
    }

    @Story(value = "Добавляем расписание")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем расписание\n" +
            "3. Проверяем, что расписание успешно добавлено")
    @Order(4)
    @Disabled
    @ParameterizedTest(name="Add schedule={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Schedule(String schedule){
        addSchedule(schedule);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление правила по дням недели")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем правило для расписания\n" +
            "3. Проверяем, что правило успешно добавлено")
    @ParameterizedTest(name="Add rule with week days={0}")
    @MethodSource(value = "receiveMenuItems")
    @Disabled
    @Order(5)
    void test_Add_Rules_With_Week_Days(String schedule){
        String[] fullWeekDays;
        if(schedule.equals("Положить трубку") || schedule.equals("Вернуться в корневое меню"))
            fullWeekDays = new String[]{"Пн.", "Ср.", "Пт.", "Вс."};
        else
            fullWeekDays = new String[]{"Вт.","Чт.", "Сб."};
        String startHour = getHour(h);
        String startMinute = getMinute(m);
        String[] startTime = {startHour, startMinute};
        h++;
        m++;
        String endHour = getHour(h);
        String endMinute = getMinute(m);
        String[] endTime = {endHour, endMinute};
        h++;
        m++;
        addRules(schedule, IVR_SCHEDULE_RULE_TYPE_WEEK_DAY, fullWeekDays, startTime, endTime, false);
    }

    @Story(value = "Добавляем правила с календарными датами")
    @Description(value = "1. Переходим в раздел Голосовое меню\n" +
            "2. Добавляем правило с датами для расписания\n" +
            "3. Проверяем, что правило успешно добавлено")
    @ParameterizedTest(name="Add rule with calendar date={0}")
    @MethodSource(value = "receiveMenuItems")
    @Disabled
    @Order(6)
    void test_Add_Rules_With_Calendar_Date(String schedule){
        String[] typeDate = {"Дата начала", "Дата окончания"};
        String startHour = getHour(h);
        String startMinute = getMinute(m);
        String[] startTime = {startHour, startMinute};
        h++;
        m++;
        String endHour = getHour(h);
        String endMinute = getMinute(m);
        String[] endTime = {endHour, endMinute};
        h++;
        m++;
        addRules(schedule, IVR_SCHEDULE_RULE_TYPE_CALENDAR_DATE, typeDate, startTime, endTime, true);
    }

    @Story(value = "Добавление точки входа")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем точку входа обычным меню\n" +
            "3. Проверяем, что точка входа успешно добавлена")
    @Order(7)
    @Disabled
    @ParameterizedTest(name="Add entry point with simple menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Entry_Point_With_Simple_Menu(String typeMenu){
        num++;
        String nameMenu = "Меню " + typeMenu;
        String number = String.valueOf(num);
        num++;
        String aonNumber = String.valueOf(num);
        String aon = number + "," + aonNumber;
        String schedule = typeMenu;
        addEntryPoint(number, aon, nameMenu, schedule);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление меню с переходом в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем Меню \n" +
            "3. Выбираем тип Перейти в меню\n" +
            "4. Проверяем, что меню успешно добавлено")
    @Order(8)
    @ParameterizedTest(name="Add go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Go_To_Menu(String type){
        String title = "Перейти в меню " + type;
        String descriptionMenu = IVR_MENU_DESCRIPTION + title;
        String numberSimpleMenu = mapMenu.get(type);
        addMenu("Перейти в меню " + type, "Перейти в меню", descriptionMenu, wavFile1, String.valueOf(num), type);
        mapMenu.put(title, String.valueOf(num));
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем отображение настроек меню с переходом в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем у меню кнопку Показать \n" +
            "3. Проверяем, что настройки отображаются корректно")
    @Order(9)
    @ParameterizedTest(name="Look go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Look_Go_To_Menu(String type){
        String numberSimpleMenu = mapMenu.get(type);
        String titleGoToMenu = "Перейти в меню " + type;
        String secondTextLink = "Меню " + type;
        String numberGoToMenu = mapMenu.get(titleGoToMenu);
        checkLookModalWindowOfMenu(titleGoToMenu, type, secondTextLink, type, wavFile1, true, true, true, numberGoToMenu, numberSimpleMenu);
    }

    @Story(value = "Добавление точки входа с меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем точку входа \n" +
            "3. Проверяем, что точка входа успешно добавлена")
    @Order(10)
    @ParameterizedTest(name="Add entry point with go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Entry_Point_With_Go_To_Menu(String type){
        String titleGoToMenu = "Перейти в меню " + type;
        num++;
        String number = String.valueOf(num);
        num++;
        String aonNumber = String.valueOf(num);
        String aon = number + "," + aonNumber;
        String schedule = type;
        addEntryPoint(number, aon, titleGoToMenu, schedule);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление второго звукового файла")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем звуковой файл \n" +
            "3. Проверяем, что новый файл добавлен в таблицу звуковых файлов")
    @Test
    @Order(11)
    void test_Upload_Sound_File_2(){
        uploadMusicFile(pathWAVFile2, wavFile2);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Редактирование простого меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем Меню \n" +
            "3. Проверяем, что меню было успешно отредактировано")
    @Order(12)
    @ParameterizedTest(name="Edit menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Edit_Simple_Menu(String type){
        num++;
        String oldNameMenu = "Меню " + type;
        String newNameMenu = "Меню " + type + " отредактировано";
        String newDescriptionMenu = IVR_MENU_DESCRIPTION + newNameMenu;
        editMenu(newNameMenu, oldNameMenu, type, newDescriptionMenu, wavFile2, String.valueOf(num));
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем отображение настроек простого меню после редактирования")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем у меню кнопку Показать \n" +
            "3. Проверяем, что настройки отображаются корректно")
    @Order(13)
    @ParameterizedTest(name="Look simple menu after edit={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Look_Simple_Menu_After_Edit(String type){
        String nameMenu = "Меню " + type + " отредактировано";
        checkLookModalWindowOfMenu(nameMenu, type, getModalWindow(), wavFile2, false);
    }

    @Story(value = "Редактирование точки входа простым меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем точку входа \n" +
            "3. Проверяем, что точка входа успешно отредактирована")
    @Order(14)
    @ParameterizedTest(name="Edit entry point={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Edit_Entry_Point_With_Simple_Menu(String typeMenu){
        String oldMenu = "Меню " + typeMenu;
        String newMenu = "Меню " + typeMenu + " отредактировано";
        num++;
        String number = String.valueOf(num);
        num++;
        String aonNumber = String.valueOf(num);
        String aon = number + "," + aonNumber;
        String schedule = typeMenu;
        editEntryPoint(number, aon, oldMenu, newMenu, schedule);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Редактирование меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем меню - переход в меню \n" +
            "3. Проверяем, что меню было успешно отредактировано")
    @Order(15)
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
    @Order(16)
    @ParameterizedTest(name="Look go to menu after edit={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Look_Go_to_Menu_After_Edit(String type){
        String newTitleGoToMenu = "Перейти в меню " + type + " отредактировано";
        String simpleMenu = "Меню " + type + " отредактировано";
        checkLookModalWindowOfMenu(newTitleGoToMenu, type, simpleMenu, type, wavFile2, true, true, true, numberGoToMenu, numberSimpleMenu);
        //checkLookModalWindowOfMenu(newTitleGoToMenu, simpleMenu, type, wavFile2, true, false, false);
    }

    /*@Story(value = "Редактирование точки входа c меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем точку входа \n" +
            "3. Проверяем, что точка входа успешно отредактирована")
    @Order(17)
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
        String finalNameMenu = nameMenu;*/
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
        /*TestStatusResult.setTestResult(true);
    }

    @Story(value = "Удаляем точки входа с простым меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем точки входа \n" +
            "3. Проверяем, что в таблице удалённые точки входа не отображается")
    @Order(18)
    @Disabled
    @ParameterizedTest(name="Delete entry point with simple menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Delete_Entry_Point_With_Simple_Menu(String type){
        String titleSimpleMenu = "";
        if(TestStatusResult.getTestResult().get("Edit entry point=" + type) == null || ! TestStatusResult.getTestResult().get("Edit entry point=" + type)) titleSimpleMenu = type;
        else titleSimpleMenu = type + " отредактировано";
        clickButtonTable(IVR_ENTRY_POINTS_TITLE, titleSimpleMenu, IVR_BUTTON_DELETE);
        isFormConfirmActions(true)
                .clickButtonConfirmAction("Продолжить");
        isItemTable(IVR_ENTRY_POINTS_TITLE, titleSimpleMenu, false);
    }

    @Story(value = "Удаляем точки входа с меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем точки входа \n" +
            "3. Проверяем, что в таблице удалённые точки входа не отображается")
    @Order(19)
    @Disabled
    @ParameterizedTest(name="Delete entry point with go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Delete_Entry_Point_With_Go_To_Menu(String type){
        String titleGoToMenu = "";
        if(TestStatusResult.getTestResult().get("Edit entry point with go to menu=" + type) == null || ! TestStatusResult.getTestResult().get("Edit entry point with go to menu=" + type)) titleGoToMenu = "Перейти в меню " + type;
        else titleGoToMenu = "Перейти в меню " + type + " отредактировано";
        clickButtonTable(IVR_ENTRY_POINTS_TITLE, titleGoToMenu, IVR_BUTTON_DELETE);
        isFormConfirmActions(true)
                .clickButtonConfirmAction("Продолжить");
        isItemTable(IVR_ENTRY_POINTS_TITLE, titleGoToMenu, false);
    }

    @Story(value = "Удаляем простое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем Меню \n" +
            "3. Проверяем, что в таблице удалённое меню не отображается")
    @Order(20)
    @ParameterizedTest(name="Delete menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Delete_Simple_Menu(String type){
        String menu = "";
        if(TestStatusResult.getTestResult().get("Edit menu=" + type) == null || ! TestStatusResult.getTestResult().get("Edit menu=" + type)) menu = type;
        else menu = type + " отредактировано";
        clickButtonTable(IVR_MENU_TITLE, menu, IVR_BUTTON_DELETE);
        isFormConfirmActions(true)
                .clickButtonConfirmAction("Удалить");
        isItemTable(IVR_MENU_TITLE, menu, false);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем, настройки меню перехода в меню, после удаления простого меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Открываем просмотр настроек меню перехода в другое меню\n" +
            "3. Проверяем, что отсутствуют ссылки в настройках меню")
    @Order(21)
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
    @Order(22)
    @ParameterizedTest(name="Delete go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Delete_Go_To_Menu(String type){
        String menu = "Перейти в меню " + type + " отредактировано";*/
        /*if(TestStatusResult.getTestResult().get("Edit go to menu=" + type) == null || ! TestStatusResult.getTestResult().get("Edit go to menu=" + type)) menu = "Перейти в меню " + type;
        else menu = "Перейти в меню " + type + " отредактировано";*/
        /*clickButtonTable(IVR_MENU_TITLE, menu, IVR_BUTTON_DELETE);
        isFormConfirmActions(true)
                .clickButtonConfirmAction("Удалить");
        isItemTable(IVR_MENU_TITLE, menu, false);
    }*/
}
