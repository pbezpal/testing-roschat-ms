package chat.ros.testing2.server.services;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.services.codefortests.SoundPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;

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
    private static Map<String, String[]> mapScheduleWeekDays;
    private static Map<String, String[]> mapScheduleCalendarDate;
    private static ArrayList<String[]> arrayListEntryPointWithSimpleMenu;
    private static String[] dataEntryPointWithSimpleMenu;
    private static int indexDataEntryPointWithSimpleMenu;
    private static ArrayList<String[]> arrayListEntryPointWithGoToMenu;
    private static String[] dataEntryPointWithGoToMenu;
    private static int indexDataEntryPointWithGoToMenu;

    @Parameterized.Parameters(name = "{0}")
    private static Iterable<String> receiveMenuItems() {
        ArrayList<String> data = new ArrayList<>();

        for (String item: IVR_MENU_ITEMS) {
            data.add(item);
        }

        return data;
    }

    @Parameterized.Parameters(name = "{0}")
    private static Iterable<String> receiveScheduleItems(){
        ArrayList<String> data = new ArrayList<>();

        for (String item: IVR_SCHEDULE_ITEMS) {
            data.add(item);
        }

        return data;
    }

    /**
     * write map for entry point with simple menu
     * map contains value menu and schedule
     * @return map with menu and schedule
     */
    private static Map<String, String> receiveDataEntryPointWIthSimpleMenu(){
        Map<String, String> data = new HashMap();

        for (int i = 0; i < IVR_MENU_ITEMS.length; i++) {
            data.put(IVR_MENU_ITEMS[i], IVR_SCHEDULE_ITEMS_FOR_SIMPLE_MENU[i]);
        }

        return data;
    }

    /**
     * <p>write map for entry point with go to menu</p>
     * <p>map contains values menu and schedule</p>
     * @return map with menu and schedule
     */
    private static Map<String, String> receiveDataEntryPointWIthGoToMenu(){
        Map<String, String> data = new HashMap();

        for (int i = 0; i < IVR_MENU_ITEMS.length; i++) {
            data.put(IVR_MENU_ITEMS[i], IVR_SCHEDULE_ITEMS_FOR_GO_TO_MENU[i]);
        }

        return data;
    }

    /**
     * get hour for rules
     * @param h means hour
     * @return hour in format string
     */
    private String getHour(int h){
        if(h < 10) return "0" + String.valueOf(h);
        else return String.valueOf(h);
    }

    /**
     * get minute for rules
     * @param m means minute
     * @return minute in format string
     */
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
        mapScheduleWeekDays = new HashMap<>();
        mapScheduleCalendarDate = new HashMap<>();
        arrayListEntryPointWithSimpleMenu = new ArrayList<String[]>();
        dataEntryPointWithSimpleMenu = new String[] {};
        indexDataEntryPointWithSimpleMenu = 0;
        arrayListEntryPointWithGoToMenu = new ArrayList<String[]>();
        dataEntryPointWithGoToMenu = new String[] {};
        indexDataEntryPointWithGoToMenu = 0;
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
    @ParameterizedTest(name="Look simple menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Look_Simple_Menu(String type){
        String nameMenu = "Меню " + type;
        String numberSimpleMenu = mapMenu.get(type);
        checkLookModalWindowOfMenu(nameMenu, type, getModalWindow(), wavFile1, true, numberSimpleMenu);
    }

    @Story(value = "Добавляем расписание")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем кнопку Добавить в разделе Расписание\n" +
            "3. Вводим название расписания\n" +
            "4. Сохраняем расписания\n" +
            "5. Проверяем, что расписание сохранилось в списке расписаний")
    @Order(4)
    @ParameterizedTest(name="Add schedule={0}")
    @MethodSource(value = "receiveScheduleItems")
    void test_Add_Schedule(String schedule){
        addSchedule(schedule);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление правила по дням недели")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Выбираем расписание" +
            "3. Нажимаем кнопку Добавить в разделе Правила\n" +
            "4. Выбираем тип правила День недели и дни недели\n" +
            "5. Выбираем Время начала и время окончания\n" +
            "6. Сохраняем настройки правила\n" +
            "7. Проверяем, что правило сохранилось в таблице правил")
    @ParameterizedTest(name="Add rule with week days={0}")
    @MethodSource(value = "receiveScheduleItems")
    @Order(5)
    void test_Add_Rules_With_Week_Days(String schedule){
        String[] fullWeekDays;
        if(schedule.equals("Расписание 1")
                || schedule.equals("Расписание 3")
                || schedule.equals("Расписание 5")
                || schedule.equals("Расписание 7")
        )
            fullWeekDays = new String[]{"Пн.", "Ср.", "Пт.", "Вс."};
        else
            fullWeekDays = new String[]{"Вт.","Чт.", "Сб."};
        if(h > 23) h = 0;
        if(m > 59) m = 0;
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
        String date = actionOnRules(schedule, IVR_SCHEDULE_RULE_TYPE_WEEK_DAY, fullWeekDays, startTime, endTime, false);
        String time = startTime + " - " + endTime;
        String[] dataRules = new String[]{date, time};
        mapScheduleWeekDays.put(schedule, dataRules);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавляем правила с календарными датами")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Выбираем расписание\n" +
            "3. Нажимаем кнопку Добавить в разделе Правила\n" +
            "4. Выбираем тип правила Календарные даты\n" +
            "5. Выбираем даты начала и окончания\n" +
            "6. Выбираем Время начала и время окончания\n" +
            "7. Сохраняем настройки правила\n" +
            "8. Проверяем, что правило сохранилось в таблице правил")
    @ParameterizedTest(name="Add rule with calendar date={0}")
    @MethodSource(value = "receiveScheduleItems")
    @Order(6)
    void test_Add_Rules_With_Calendar_Date(String schedule){
        String[] typeDate = {"Дата начала", "Дата окончания"};
        if(h > 23) h = 0;
        if(m > 59) m = 0;
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
        String date = actionOnRules(schedule, IVR_SCHEDULE_RULE_TYPE_CALENDAR_DATE, typeDate, startTime, endTime, true);
        String time = startTime + " - " + endTime;
        String[] dataRules = new String[]{date, time};
        mapScheduleCalendarDate.put(schedule, dataRules);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавление точки входа")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Добавляем точку входа обычным меню\n" +
            "3. Проверяем, что точка входа успешно добавлена")
    @Order(7)
    @ParameterizedTest(name="Add entry point with simple menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Add_Entry_Point_With_Simple_Menu(String menuItems){
        num++;
        String nameMenu = "Меню " + menuItems;
        String number = String.valueOf(num);
        num++;
        String aonNumber = String.valueOf(num);
        String aon = number + "," + aonNumber;
        String schedule = receiveDataEntryPointWIthSimpleMenu().get(menuItems);
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
        String schedule = receiveDataEntryPointWIthGoToMenu().get(type);
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
        mapMenu.put(newNameMenu, newDescriptionMenu);
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

    @Story(value = "Редактируем расписание")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем у расписания кнопку Редактировать\n" +
            "3. Вводим новое название расписания\n" +
            "4. Сохраняем новое название расписания\n" +
            "5. Проверяем, что изменения сохранились в списке расписаний")
    @Order(14)
    @ParameterizedTest(name="Edit schedule={0}")
    @MethodSource(value = "receiveScheduleItems")
    void test_Edit_Schedule(String schedule){
        String newTitleSchedule = schedule + " отредактировано";
        editSchedule(schedule, newTitleSchedule);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Редактирование правила с календарными датами")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Выбираем расписание\n" +
            "3. Нажимаем кнопку редактировать у правила с календарными датами\n" +
            "4. Выбираем тип правила День недели и выбираем все дни недели\n" +
            "5. Выбираем Время начала и время окончания\n" +
            "6. Снимаем галочку с исключения" +
            "7. Сохраняем настройки правила\n" +
            "8. Проверяем, что изменения сохранились в таблице правил")
    @ParameterizedTest(name="Edit rule with calendar date={0}")
    @MethodSource(value = "receiveScheduleItems")
    @Order(15)
    void test_Edit_Rules_With_Calendar_Date(String schedule){
        String titleSchedule = schedule + " отредактировано";
        String[] fullWeekDays = new String[]{"Пн.", "Вт.", "Ср.", "Чт.", "Пт.", "Сб.", "Вс."};
        if(h > 23) h = 0;
        if(m > 59) m = 0;
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
        String dateOld = mapScheduleCalendarDate.get(schedule)[0];
        String date = actionOnRules(titleSchedule, IVR_SCHEDULE_RULE_TYPE_WEEK_DAY, fullWeekDays, startTime, endTime, false, dateOld);
        String time = startTime + " - " + endTime;
        String[] data = new String[]{date, time};
        mapScheduleCalendarDate.put(schedule, data);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Редактирование правила с днями недели")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Выбираем расписание\n" +
            "3. Нажимаем кнопку редактировать у правила с днями недели\n" +
            "4. Выбираем тип правила Календарные дни и выбираем даты\n" +
            "5. Выбираем Время начала и время окончания\n" +
            "6. Ставим галочку для исключения" +
            "7. Сохраняем настройки правила\n" +
            "8. Проверяем, что изменения сохранились в таблице правил")
    @ParameterizedTest(name="Edit rule with week days={0}")
    @MethodSource(value = "receiveScheduleItems")
    @Order(16)
    void test_Edit_Rules_With_Week_Days(String schedule){
        String[] typeDate = {"Дата начала", "Дата окончания"};
        if(h > 23) h = 0;
        if(m > 59) m = 0;
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
        String dateOld = mapScheduleWeekDays.get(schedule)[0];
        String date = actionOnRules(schedule, IVR_SCHEDULE_RULE_TYPE_CALENDAR_DATE, typeDate, startTime, endTime, true, dateOld);
        String time = startTime + " - " + endTime;
        String[] data = new String[] {date, time};
        mapScheduleWeekDays.put(schedule, data);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Редактирование точки входа c простым меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем точку входа \n" +
            "3. Проверяем, что точка входа успешно отредактирована")
    @Order(17)
    @ParameterizedTest(name="Edit entry point={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Edit_Entry_Point_With_Simple_Menu(String typeMenu){
        String oldMenu = "Меню " + typeMenu;
        String newMenu = "Меню " + typeMenu + " отредактировано";
        String newTitleSchedule = receiveDataEntryPointWIthSimpleMenu().get(typeMenu) + " отредактировано";
        num++;
        String number = String.valueOf(num);
        num++;
        String aonNumber = String.valueOf(num);
        String aon = number + "," + aonNumber;
        editEntryPoint(number, aon, oldMenu, newMenu, newTitleSchedule);
        dataEntryPointWithSimpleMenu = new String[] {number, aon, newTitleSchedule};
        arrayListEntryPointWithSimpleMenu.add(dataEntryPointWithSimpleMenu);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Редактирование меню с переходом в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Редактируем меню - переход в меню \n" +
            "3. Проверяем, что меню было успешно отредактировано")
    @Order(18)
    @ParameterizedTest(name="Edit go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Edit_Go_To_Menu(String type){
        String simpleMenu = type + " отредактировано";
        String oldNameMenu = "Перейти в меню " + type;
        String newTitleGoToMenu = "Перейти в меню " + type + " отредактировано";
        String newDescriptionGoToMenu = IVR_MENU_DESCRIPTION + newTitleGoToMenu;
        editMenu(newTitleGoToMenu, oldNameMenu, "Перейти в меню", newDescriptionGoToMenu, wavFile2, simpleMenu);
        mapMenu.put(newTitleGoToMenu, newDescriptionGoToMenu);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем отображение настроек меню с переходом в другое меню после редактирования")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем у меню кнопку Показать \n" +
            "3. Проверяем, что настройки отображаются корректно")
    @Order(19)
    @ParameterizedTest(name="Look go to menu after edit={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Look_Go_to_Menu_After_Edit(String type){
        String newTitleGoToMenu = "Перейти в меню " + type + " отредактировано";
        String simpleMenu = "Меню " + type + " отредактировано";
        checkLookModalWindowOfMenu(newTitleGoToMenu, type, simpleMenu, type, wavFile2, true, false, false);
    }

    @Story(value = "Редактирование точки входа c меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Нажимаем у точки входа кнопку Редактировать\n" +
            "3. Вводим номер и аон\n" +
            "4. Выбираем меню с переходом в другое меню\n" +
            "5. Выбираем отредактированное расписание\n" +
            "6. Нажимаем кнопку Сохранить\n" +
            "3. Проверяем, что изменения сохранились в таблице Точек входа")
    @Order(20)
    @ParameterizedTest(name="Edit entry point with go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Edit_Entry_Point_With_Go_To_Menu(String typeMenu){
        String oldMenu = "Перейти в меню " + typeMenu;
        String newMenu = "Перейти в меню " + typeMenu + " отредактировано";
        String newTitleSchedule = receiveDataEntryPointWIthGoToMenu().get(typeMenu) + " отредактировано";
        num++;
        String number = String.valueOf(num);
        num++;
        String aonNumber = String.valueOf(num);
        String aon = number + "," + aonNumber;
        editEntryPoint(number, aon, oldMenu, newMenu, newTitleSchedule);
        dataEntryPointWithGoToMenu = new String[] {number, aon, newTitleSchedule};
        arrayListEntryPointWithGoToMenu.add(dataEntryPointWithGoToMenu);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Удаляем точки входа с простым меню")
    @Description(value = "1. Переходим в раздел Голосовое меню\n" +
            "2. Нажимаем кнопку удалить у точки входа с простым меню" +
            "3. Подтверждаем удаление точки входа\n" +
            "4. Проверяем, что в таблице удалённые точки входа не отображается")
    @Order(21)
    @ParameterizedTest(name="Delete entry point with simple menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Delete_Entry_Point_With_Simple_Menu(String type){
        String titleSimpleMenu = "Меню " + type;
        if(TestStatusResult.getTestResult().get("Edit entry point=" + type) != null || TestStatusResult.getTestResult().get("Edit entry point=" + type)) titleSimpleMenu = titleSimpleMenu + " отредактировано";
        String[] data = arrayListEntryPointWithSimpleMenu.get(indexDataEntryPointWithSimpleMenu);
        deleteEntryPoint(titleSimpleMenu, data);
        indexDataEntryPointWithSimpleMenu++;
    }

    @Story(value = "Удаляем точки входа с меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню\n" +
            "2. Нажимаем кнопку удалить у точки входа с переходом в другое меню" +
            "3. Подтверждаем удаление точки входа\n" +
            "4. Проверяем, что в таблице удалённые точки входа не отображается")
    @Order(22)
    @ParameterizedTest(name="Delete entry point with go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Delete_Entry_Point_With_Go_To_Menu(String type){
        String titleGoToMenu = "Перейти в меню " + type;
        if(TestStatusResult.getTestResult().get("Edit entry point with go to menu=" + type) != null || TestStatusResult.getTestResult().get("Edit entry point with go to menu=" + type)) titleGoToMenu = titleGoToMenu + " отредактировано";
        String[] data = arrayListEntryPointWithGoToMenu.get(indexDataEntryPointWithGoToMenu);
        deleteEntryPoint(titleGoToMenu, data);
        indexDataEntryPointWithGoToMenu++;
    }

    @Story(value = "Удаляем простое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем Меню \n" +
            "3. Проверяем, что в таблице удалённое меню не отображается")
    @Order(23)
    @ParameterizedTest(name="Delete menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Delete_Simple_Menu(String type){
        String menu = "Меню " + type;
        if(TestStatusResult.getTestResult().get("Edit menu=" + type) != null || TestStatusResult.getTestResult().get("Edit menu=" + type)) menu = menu + " отредактировано";
        String description = mapMenu.get(menu);
        deleteMenu(menu, description);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем, настройки меню перехода в меню, после удаления простого меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Открываем просмотр настроек меню перехода в другое меню\n" +
            "3. Проверяем, что отсутствуют ссылки в настройках меню")
    @Order(24)
    @ParameterizedTest(name="Check links go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_No_Links_Go_To_Menu(String type){
        String titleGoToMenu = "Перейти в меню " + type;
        String secondText = "Меню " + type;
        if(TestStatusResult.getTestResult().get("Edit go to menu=" + type) != null || TestStatusResult.getTestResult().get("Edit go to menu=" + type)) {
            titleGoToMenu = titleGoToMenu + " отредактировано";
            secondText = secondText + " отредактировано";
        }
        checkLookModalWindowOfMenu(titleGoToMenu, type, secondText, type, wavFile2, false, false, false);
    }

    @Story(value = "Удаляем меню перехода в другое меню")
    @Description(value = "1. Переходим в раздел Голосовое меню \n" +
            "2. Удаляем Меню \n" +
            "3. Проверяем, что в таблице удалённое меню не отображается")
    @Order(25)
    @ParameterizedTest(name="Delete go to menu={0}")
    @MethodSource(value = "receiveMenuItems")
    void test_Delete_Go_To_Menu(String type){
        String menu = "Перейти в меню " + type;
        if(TestStatusResult.getTestResult().get("Edit go to menu=" + type) != null || TestStatusResult.getTestResult().get("Edit go to menu=" + type)) menu = menu + " отредактировано" ;
        String description = mapMenu.get(menu);
        deleteMenu(menu, description);
    }

    @Story(value = "Удаляем правила с днями недели для расписания")
    @Description(value = "1. Переходим в раздел Голосовое меню\n" +
            "2. Нажимаем кнопку удалить у правила\n" +
            "3. Подтверждаем удаление правила\n" +
            "4. Проверяем, что правило не отображается в таблице правил после удаления")
    @Order(26)
    @ParameterizedTest(name="Delete rules with week days={0}")
    @MethodSource(value = "receiveScheduleItems")
    void test_Delete_Rules_With_Week_Days(String schedule){
        String finalSchedule = schedule;
        if(TestStatusResult.getTestResult().get("Edit rule with week days=" + schedule) != null || TestStatusResult.getTestResult().get("Edit rule with week days=" + schedule)) finalSchedule = finalSchedule + " отредактировано";
        String date = mapScheduleWeekDays.get(schedule)[0];
        String time = mapScheduleWeekDays.get(schedule)[1];
        deleteRules(finalSchedule, date, time);
    }

    @Story(value = "Удаляем правила с календарными датами для расписания")
    @Description(value = "1. Переходим в раздел Голосовое меню\n" +
            "2. Нажимаем кнопку удалить у правила\n" +
            "3. Подтверждаем удаление правила\n" +
            "4. Проверяем, что правило не отображается в таблице правил после удаления")
    @Order(27)
    @ParameterizedTest(name="Delete rules with calendar date={0}")
    @MethodSource(value = "receiveScheduleItems")
    void test_Delete_Rules_With_Calendar_Date(String schedule){
        String finalSchedule = schedule;
        if(TestStatusResult.getTestResult().get("Edit rule with calendar date=" + schedule) != null || TestStatusResult.getTestResult().get("Edit rule with calendar date=" + schedule)) finalSchedule = finalSchedule + " отредактировано";
        String date = mapScheduleCalendarDate.get(schedule)[0];
        String time = mapScheduleCalendarDate.get(schedule)[1];
        deleteRules(finalSchedule, date, time);
    }

    @Story(value = "Удаляем расписания")
    @Description(value = "1. Переходим в раздел Голосовое меню\n" +
            "2. Нажимаем кнопку удалить у расписания\n" +
            "3. Подтверждаем удаление расписания\n" +
            "4. Проверяем, что расписание не отображается в в разделе расписание")
    @Order(28)
    @ParameterizedTest(name="Delete schedule={0}")
    @MethodSource(value = "receiveScheduleItems")
    void test_Delete_Schedule(String schedule){
        String finalSchedule = schedule;
        if(TestStatusResult.getTestResult().get("Edit schedule=" + schedule) != null || TestStatusResult.getTestResult().get("Edit schedule=" + schedule)) finalSchedule = finalSchedule + " отредактировано";
        deleteSchedule(finalSchedule);
    }
}
