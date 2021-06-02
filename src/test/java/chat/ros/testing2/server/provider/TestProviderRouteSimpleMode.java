package chat.ros.testing2.server.provider;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.TelephonyPage;
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
import java.util.List;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ResourcesRoutePage.class)
@ExtendWith(WatcherTests.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic(value = "Настройки")
@Feature(value = "Телефония -> Маршрут - простой режим")
public class TestProviderRouteSimpleMode extends TelephonyPage {

    private List<String> patternNumbersIncomingRoute = new ArrayList<String>();
    private List<String> patternsReplaceIncomingRouteSimpleMode = new ArrayList<String>();
    private List<String> replacesIncomingRouteExpertMode = new ArrayList<String>();
    private List<String> groupReplacesIncomingRouteExpertMode = new ArrayList<String>();
    private List<String> patternIncomingRouteExpertMode = new ArrayList<String>();

    private List<String> patternNumbersOutgoingRoute = new ArrayList<String>();
    private List<String> patternsReplaceOutgoingRouteSimpleMode = new ArrayList<String>();
    private List<String> replacesOutgoingRouteExpertMode = new ArrayList<String>();
    private List<String> groupReplacesOutgoingRouteExpertMode = new ArrayList<String>();
    private List<String> patternOutgoingRouteExpertMode = new ArrayList<String>();

    //The data for the section general in the form provider
    private Map<String, String> dataGeneralProvider = new HashMap() {{
        put(TELEPHONY_PROVIDER_INPUT_NAME, TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE);
        put(TELEPHONY_PROVIDER_INPUT_ADDRESS, TELEPHONY_PROVIDER_ADDRESS_ROUTE_SIMPLE_MODE);
    }};

    //################# DATA INCOMING ROUTE ###############################
    //The data for add incoming route in simple mode
    private Map<String,String> dataIncomingRouteSimpleMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_INCOMING_ROUTE_SIMPLE_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_INCOMING_ROUTE_SIMPLE_MODE_REPLACE);
    }};
    //The data for edit incoming route in simple mode
    private Map<String,String> dataEditIncomingRouteSimpleMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_MODE_REPLACE);
    }};
    //The data for edit incoming route in simple mode without pattern replace
    private Map<String,String> dataEditIncomingRouteSimpleModeWithoutReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_REPLACE, "");
    }};
    //The data edit incoming route from simple to expert mode
    private Map<String,String> dataEditIncomingRouteFromSimpleToExpertMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_TO_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_TO_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_TO_EXPERT_MODE_GROUP_REPLACE);
    }};
    //The data edit incoming route in expert mode without group replace
    private Map<String,String> dataEdiIncomingtRouteInExpertModeWithoutGroupReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_TO_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_TO_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, "");
    }};
    //The data edit incoming route in expert mode without replace and group replace
    private Map<String,String> dataEditIncomingRouteInExpertModeWithoutReplaceAndGroupReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_TO_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, "");
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, "");
    }};

    //################# DATA OUTGOING ROUTE ###############################
    //The data for add outgoing route in simple mode
    private Map<String,String> dataOutgoingRouteSimpleMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_OUTGOING_ROUTE_SIMPLE_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_OUTGOING_ROUTE_SIMPLE_MODE_REPLACE);
    }};
    //The data for edit outgoing route with simple mode
    private Map<String,String> dataEditOutgoingRouteSimpleMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_MODE_REPLACE);
    }};
    //The data for edit щгепщштп route in simple mode without pattern replace
    private Map<String,String> dataEditOutgoingRouteSimpleModeWithoutReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_REPLACE, "");
    }};
    //The data edit outgoing route from simple to expert mode
    private Map<String,String> dataEditOutgoingRouteFromSimpleToExpertMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_TO_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_TO_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_TO_EXPERT_MODE_GROUP_REPLACE);
    }};
    //The data edit outgoing route in expert mode without group replace
    private Map<String,String> dataEditOutgoingRouteInExpertModeWithoutGroupReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_TO_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_TO_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, "");
    }};
    //The data edit outgoing route in expert mode without replace and group replace
    private Map<String,String> dataEditOutgoingRouteInExpertModeWithoutReplaceAndGroupReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_TO_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, "");
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, "");
    }};

    @Parameterized.Parameters(name = "{0}")
    private static Iterable<String> fieldsRouteSimpleMode() {
        ArrayList<String> data = new ArrayList<>();

        for (String item: TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_FIELDS) {
            data.add(item);
        }

        return data;
    }

    @Story(value = "Добавляем провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Добавить провайдера\n" +
            "3. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "4. Проверяем, что провайдер появился в таблице провайдеров")
    @Test
    @Order(1)
    void test_Add_Provider_For_Route_In_Simple_Mode(){
        clickButtonSettings(TELEPHONY_PROVIDER_TITLE_FORM, "Добавить провайдера");
        setProvider(dataGeneralProvider);
        assertTrue(isExistsTableText(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE, true),
                "Не отображается название " + TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE +
                        " в таблице провайдеров");
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем модальное окно инфорамции при добавление/редактирование маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Нажимаем на иконку для открытия информационного окна\n" +
            "5. Проверяем, что окно информации открылось и информация отображается в окне")
    @ParameterizedTest(name="Field = {0}")
    @MethodSource(value = "fieldsRouteSimpleMode")
    @Order(2)
    void test_Modal_Info_Of_Route(String field){
        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE, "Изменить");
        if (field.equals(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER))
            clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE, "Создать маршрут");
        else
            clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE, "Новый маршрут");
        clickIconCallToolTipInfo(field);
        assertTrue(isVisibleInfoWrapper(true),
                "Модальное окно с информацией не появилось");
        assertAll("Проверяем, корректно ли отображается информация в tooltip",
                () -> assertTrue(isTitleInfoWrapper("Описание синтаксиса составления шаблонов номеров"),
                        "Не отображается заголовок 'Описание синтаксиса составления шаблонов номеров' " +
                                "у модального окна с информацией"),
                () -> assertTrue(isTitleInfoWrapper("Простой режим"),
                        "Не отображается заголовок 'Простой режим' у модального окна с информацией"),
                () -> assertTrue(isContentInfoWrapper("+ - добавить префикс (пример, \"1+555\" совпадает с \"555\" и " +
                                "преобразуется в \"1555\")"),
                        "Не отображается текст '+ - добавить префикс (пример, \"1+555\" совпадает с \"555\" и " +
                                "преобразуется в \"1555\")' в модальном окне с информацией"),
                () -> assertTrue(isContentInfoWrapper("| - удалить префикс (пример, \"1|1555\" совпадает с \"1555\" и " +
                                "преобразуется в \"555\")"),
                        "Не отображается текст '| - удалить префикс (пример, \"1|1555\" совпадает с \"1555\" и " +
                                "преобразуется в \"555\")' в модальном окне с информацией"),
                () -> assertTrue(isContentInfoWrapper("Х - совпадение с символами 0-9"),
                        "Не отображается текст 'Х - совпадение с символами 0-9' в модальном окне с информацией"),
                () -> assertTrue(isContentInfoWrapper("[] - совпадение с указанными символами (пример, [13-5] поиск " +
                                "совпадения с \"1\",\"3\",\"4\",\"5\")"),
                        "Не отображается текст '[] - совпадение с указанными символами (пример, [13-5] поиск " +
                                "совпадения с \"1\",\"3\",\"4\",\"5\")' в модальном окне с информацией"),
                () -> assertTrue(isContentInfoWrapper("[^] - отсутствие совпадения с указанными символами (пример, " +
                        "[^12] поиск всех символов кроме \"1\" и \"2\")"),
                "Не отображается текст '[^] - отсутствие совпадения с указанными символами (пример, [^12] " +
                        "поиск всех символов кроме \"1\" и \"2\")' в модальном окне с информацией"),
                () -> assertTrue(isContentInfoWrapper("{} - число совпадающих символов (пример, [13-5]{3} поиск " +
                                "совпадения трех символов с \"1\",\"3\",\"4\",\"5\" )"),
                        "Не отображается текст '{} - число совпадающих символов (пример, [13-5]{3} поиск " +
                                "совпадения трех символов с \"1\",\"3\",\"4\",\"5\" )' в модальном окне с информацией"),
                () -> assertTrue(isContentInfoWrapper(". - совпадение с одним или более символов (пример, 7Х. поиск " +
                                "всех номеров с первым символом \"7\" и хотя бы одним символом [0-9]; допускается " +
                                "использовать с поиском совпадений Х,[] и [^]; не допускается использовать " +
                                "после \"+\" и \"|\")"),
                        "Не отображается текст '. - совпадение с одним или более символов (пример, 7Х. " +
                                "поиск всех номеров с первым символом \"7\" и хотя бы одним символом [0-9]; " +
                                "допускается использовать с поиском совпадений Х,[] и [^]; не допускается " +
                                "использовать после \"+\" и \"|\")' в модальном окне с информацией"),
                () -> assertTrue(isContentInfoWrapper("! - совпадение с нулём или более символов (пример, 7Х! поиск " +
                                "всех номеров с первым символом \"7\" и символами [0-9] или только символа \"7\"; " +
                                "допускается использовать с поиском совпадений Х,[] и [^]); не допускается " +
                                "использовать после \"+\" и \"|\")"),
                        "Не отображается текст '! - совпадение с нулём или более символов (пример, 7Х! " +
                                "поиск всех номеров с первым символом \"7\" и символами [0-9] или только " +
                                "символа \"7\"; допускается использовать с поиском совпадений Х,[] и [^]); не " +
                                "допускается использовать после \"+\" и \"|\")' в модальном окне с информацией"),
                () -> assertTrue(isContentInfoWrapper("\\ - экранирование указанного символа (пример \\Х, поиск " +
                                "совпадения с символом \"X\")"),
                        "Не отображается текст '\\ - экранирование указанного символа (пример \\Х, поиск " +
                                "совпадения с символом \"X\")' в модальном окне с информацией"),
                () -> assertTrue(isContentInfoWrapper("Пример шаблона: 5+1|XXX"),
                        "Не отображается текст 'Пример шаблона: 5+1|XXX' в модальном окне с информацией"),
                () -> assertTrue(isContentInfoWrapper("Номер до преобразования: 1043"),
                        "Не отображается текст 'Номер до преобразования: 1043' в модальном окне с информацией"),
                () -> assertTrue(isContentInfoWrapper("Преобразованный номер: 5043"),
                        "Не отображается текст 'Преобразованный номер: 5043' в модальном окне с информацией"),
                () -> assertTrue(isTitleInfoWrapper("Экспертный режим"),
                        "Не отображается заголовок 'Экспертный режим' у модального окна с информацией"),
                () -> assertTrue(isContentInfoWrapper("В экспертном режиме регулярные выражения записываются в " +
                                "формате РOSIX."),
                        "Не отображается текст 'В экспертном режиме регулярные выражения записываются в " +
                                "формате РOSIX.' в модальном окне с информацией"),
                () -> {
                    if (field.equals(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER))
                        assertTrue(isContentInfoWrapper("Пример шаблона выбора номера: ^(1[0-9]{3})$, где"),
                                "Не отображается текст 'Пример шаблона выбора номера: ^(1[0-9]{3})$, где' в " +
                                        "модальном окне с информацией");
                    else
                        assertTrue(isContentInfoWrapper("Пример шаблона замены номера: /^1([0-9]{3})$/5\\1/ ‚где"),
                                "Не отображается текст 'Пример шаблона замены номера: " +
                                        "/^1([0-9]{3})$/5\\1/ ‚где' в модальном окне с информацией");
                },
                () -> assertTrue(isContentInfoWrapper("Выражение - ^(1[0-9]{3})$"),
                        "Не отображается текст 'Выражение - ^(1[0-9]{3})$' в модальном окне с информацией"),
                () -> assertTrue(clickButtonCloseModalInfo().isVisibleInfoWrapper(false),
                        "Модальное окно с информацией не закрылось после нажатия кнопки 'Закрыть'")
        );
    }

    @Story(value = "Добавляем входящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(3)
    void test_Add_Incoming_Route_Simple_Mode(){
        addRouteInSimpleMode(TELEPHONY_PROVIDER_INCOMING_ROUTE, dataIncomingRouteSimpleMode, "Создать маршрут");
    }

    @Story(value = "Добавляем исходящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(4)
    void test_Add_Outgoing_Route_Simple_Mode(){
        addRouteInSimpleMode(TELEPHONY_PROVIDER_OUTGOING_ROUTE, dataOutgoingRouteSimpleMode, "Новый маршрут");
    }

    @Story(value = "Редактируем входящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(5)
    void test_Edit_Incoming_Route_Simple_Mode(){
        editRouteInSimpleMode(TELEPHONY_PROVIDER_INCOMING_ROUTE, dataEditIncomingRouteSimpleMode);
    }

    @Story(value = "Редактируем исходящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(6)
    void test_Edit_Outgoing_Route_Simple_Mode(){
        editRouteInSimpleMode(TELEPHONY_PROVIDER_OUTGOING_ROUTE, dataEditOutgoingRouteSimpleMode);
    }

    @Story(value = "Редактируем входящий маршрут без шаблона замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(7)
    void test_Edit_Incoming_Route_Simple_Mode_Without_Pattern_Replace(){
        editRouteInSimpleMode(TELEPHONY_PROVIDER_INCOMING_ROUTE, dataEditIncomingRouteSimpleModeWithoutReplace);
    }

    @Story(value = "Редактируем исходящий маршрут без шаблона замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(8)
    void test_Edit_Outgoing_Route_Simple_Mode_Without_Pattern_Replace(){
        editRouteInSimpleMode(TELEPHONY_PROVIDER_OUTGOING_ROUTE, dataEditOutgoingRouteSimpleModeWithoutReplace);
    }

    @Story(value = "Редактируем входящий маршрут с переключением в экспертный режим")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Переключаем настройки в экспертный режим\n" +
            "5. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "6. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(9)
    void test_Edit_Incoming_Route_From_Simple_To_Expert_Mode(){
        editRouteExpertMode(TELEPHONY_PROVIDER_INCOMING_ROUTE, dataEditIncomingRouteFromSimpleToExpertMode, true);
    }

    @Story(value = "Редактируем исходящий маршрут с переключением в экспертный режим")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у исходящего маршрута\n" +
            "4. Переключаем настройки в экспертный режим\n" +
            "5. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "6. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(10)
    void test_Edit_Outgoing_Route_From_Simple_To_Expert_Mode(){
        editRouteExpertMode(TELEPHONY_PROVIDER_OUTGOING_ROUTE, dataEditOutgoingRouteFromSimpleToExpertMode, true);
    }

    @Story(value = "Редактируем входящий маршрут в экспертном режиме без группы замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(11)
    void test_Edit_Incoming_Route_Expert_Mode_Without_Group_Replace(){
        editRouteExpertMode(TELEPHONY_PROVIDER_INCOMING_ROUTE, dataEdiIncomingtRouteInExpertModeWithoutGroupReplace);
    }

    @Story(value = "Редактируем исходящий маршрут в экспертном режиме без группы замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у исходящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(12)
    void test_Edit_Outgoing_Route_Expert_Mode_Without_Group_Replace(){
        editRouteExpertMode(TELEPHONY_PROVIDER_OUTGOING_ROUTE, dataEditOutgoingRouteInExpertModeWithoutGroupReplace);
    }

    @Story(value = "Редактируем входящий маршрут в экспертном режиме без шаблона замены и группы замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(13)
    void test_Edit_Incoming_Route_Expert_Mode_Without_Pattern_Replace_And_Group_Replace(){
        editRouteExpertMode(TELEPHONY_PROVIDER_INCOMING_ROUTE, dataEditIncomingRouteInExpertModeWithoutReplaceAndGroupReplace);
    }

    @Story(value = "Редактируем исходящего маршрут в экспертном режиме без шаблона замены и группы замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у исходящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(14)
    void test_Edit_Outgoing_Route_Expert_Mode_Without_Pattern_Replace_And_Group_Replace(){
        editRouteExpertMode(TELEPHONY_PROVIDER_OUTGOING_ROUTE, dataEditOutgoingRouteInExpertModeWithoutReplaceAndGroupReplace);
    }

    @Story(value = "Удаление Входящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у входящего маршрутов\n" +
            "4. Подтверждаем удаление входящего маршрута" +
            "5. Проверяем, что запись о входящем маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(15)
    void test_Delete_Incoming_Route_In_Simple_Mode(){
        deleteRoute(TELEPHONY_PROVIDER_INCOMING_ROUTE);
    }

    @Story(value = "Удаление Исходящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у исходящего маршрутов\n" +
            "4. Подтверждаем удаление исходящего маршрута" +
            "5. Проверяем, что запись о исходящем маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(16)
    void test_Delete_Outgoing_Route_In_Simple_Mode(){
        deleteRoute(TELEPHONY_PROVIDER_OUTGOING_ROUTE);
    }

    private void addRouteInSimpleMode(String direction, Map<String, String> dataRoute, String addButton){
        String patternNumber = null;
        String patternReplace = null;
        for(Map.Entry data: dataRoute.entrySet()){
            if(data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER))
                patternNumber = data.getValue().toString();
            else
                patternReplace = data.getValue().toString();
        }

        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE, "Изменить");
        clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE, addButton);
        final String finalPatternNumber = patternNumber;
        createRoute(direction, true, dataRoute);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        assertAll("\"1. Проверяем, правильно ли отображается заголовок модального окна\n" +
                        "2. Заполняем поля модального окна и сохраняем настройки\n" +
                        "3. Проверяем, что в столбце Шаблон номера отображается значение " + finalPatternNumber + "\n" +
                        "4. Проверяем, что в столбце Шаблон замены отображается значение " + patternReplace,
                () -> assertTrue(isExistsTableText(direction, true),
                        "Не отображается значение " + direction +
                                " в столбце Направление в таблице маршрутов"),
                () -> assertTrue(isExistsTableText(finalPatternNumber, true),
                        "Не отображается значение " + finalPatternNumber +
                                " в столбце Шаблон номера в таблице маршрутов")
        );
        TestStatusResult.setTestResult(true);
        assertTrue(isExistsTableText(patternReplace, true),
                "Не отображается Шаблон замены " + patternReplace +
                        " в столбце Шаблон номера в таблице маршрутов");
        if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
            patternsReplaceIncomingRouteSimpleMode.add(patternReplace);
        else
            patternsReplaceOutgoingRouteSimpleMode.add(patternReplace);
    }

    private void editRouteInSimpleMode(String direction, Map<String, String> dataRoute){
        String patternNumber = null;
        String patternReplace = null;
        for(Map.Entry data: dataRoute.entrySet()){
            if(data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER))
                patternNumber = data.getValue().toString();
            else
                patternReplace = data.getValue().toString();
        }

        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE, "Изменить")
                .clickButtonTableRoute(direction, "edit");
        final String finalPatternNumber = patternNumber;
        editRoute(dataRoute, true);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        assertAll("1. Проверяем, правильно ли отображается заголовок модального окна\n" +
                        "2. Заполняем поля модального окна и сохраняем настройки\n" +
                        "3. Проверяем, что в столбце Шаблон номера отображается значение " + finalPatternNumber + "\n" +
                        "4. Проверяем, что в столбце Шаблон замены отображается значение " + patternReplace,
                () -> assertTrue(isExistsTableText(direction, true),
                        "Не отображается значение " + direction +
                                " в столбце Направление в таблице маршрутов"),
                () -> {
                    assertTrue(isExistsTableText(finalPatternNumber, true),
                            "Не отображается значение " + finalPatternNumber +
                                    " в столбце Шаблон номера в таблице маршрутов");
                    if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                        patternNumbersIncomingRoute.add(finalPatternNumber);
                    else
                        patternNumbersOutgoingRoute.add(finalPatternNumber);
                }
        );
        TestStatusResult.setTestResult(true);

        if(patternReplace.equals("")){
            patternsReplaceIncomingRouteSimpleMode.forEach((replace) -> assertTrue(isExistsTableText(replace, false),
                    "Отображается Шаблон замены " + replace +
                            " в столбце Шаблон номера в таблице маршрутов"));
        }else {
            assertTrue(isExistsTableText(patternReplace, true),
                    "Не отображается Шаблон замены " + patternReplace +
                            " в столбце Шаблон номера в таблице маршрутов");
            if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                patternsReplaceIncomingRouteSimpleMode.add(patternReplace);
            else
                patternsReplaceOutgoingRouteSimpleMode.add(patternReplace);
        }
    }

    private void editRouteExpertMode(String direction, Map<String, String> dataRoute, boolean... expertMode){
        String number = null;
        String replace = null;
        String groupReplace = null;

        for(Map.Entry data: dataRoute.entrySet()){
            if(data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER))
                number = data.getValue().toString();
            else if(data.getKey().equals(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE))
                replace = data.getValue().toString();
            else
                groupReplace = data.getValue().toString();
        }

        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE, "Изменить")
                .clickButtonTableRoute(direction, "edit");
        String patternReplace = replace + groupReplace;
        String finalNumber = number;
        if(expertMode.length > 0 && expertMode[0])
            editRoute(dataRoute, false);
        else
            editRoute(dataRoute);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        assertAll("Проверяем, что корректно отображаютс данные в таблице маршрутов:\n" +
                        "1. В столбце Шаблон номера отображается значение " + finalNumber + "\n" +
                        "2. В столбце Шаблон замены отображается значение " + patternReplace,
                () -> assertTrue(isExistsTableText(direction, true),
                        "Не отображается значение " + direction +
                                " в столбце Направление в таблице маршрутов"),
                () -> {
                    assertTrue(isExistsTableText(finalNumber, true),
                            "Не отображается значение " + finalNumber +
                                    " в столбце Шаблон номера в таблице маршрутов");
                    if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                        patternNumbersIncomingRoute.add(finalNumber);
                    else
                        patternNumbersOutgoingRoute.add(finalNumber);
                }
        );
        TestStatusResult.setTestResult(true);
        if(groupReplace.equals("") && ! replace.equals("")) {
            assertTrue(isExistsTableText(patternReplace, true),
                    "Не отображается значение " + patternReplace + " в столбце Шаблон замены в таблице маршрутов");
            if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE)) replacesIncomingRouteExpertMode.add(replace);
            else replacesOutgoingRouteExpertMode.add(replace);
        }else if(groupReplace.equals("") && replace.equals("")){
            if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE)) {
                replacesIncomingRouteExpertMode.forEach((rep) -> assertTrue(isExistsTableText(rep, false),
                        "Отображается значение " + rep + " в столбце Шаблон замены в таблице маршрутов"));
                groupReplacesIncomingRouteExpertMode.forEach((gRep) -> assertTrue(isExistsTableText(gRep, false),
                        "Отображается значение " + gRep + " в столбце Шаблон замены в таблице маршрутов"));
                patternIncomingRouteExpertMode.forEach((pattern) -> assertTrue(isExistsTableText(pattern, false),
                        "Отображается значение " + pattern + " в столбце Шаблон замены в таблице маршрутов"));
            }
            else {
                replacesOutgoingRouteExpertMode.forEach((rep) -> assertTrue(isExistsTableText(rep, false),
                        "Отображается значение " + rep + " в столбце Шаблон замены в таблице маршрутов"));
                groupReplacesOutgoingRouteExpertMode.forEach((gRep) -> assertTrue(isExistsTableText(gRep, false),
                        "Отображается значение " + gRep + " в столбце Шаблон замены в таблице маршрутов"));
                patternOutgoingRouteExpertMode.forEach((pattern) -> assertTrue(isExistsTableText(pattern, false),
                        "Отображается значение " + pattern + " в столбце Шаблон замены в таблице маршрутов"));
            }
        }else{
            if (direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE)) {
                replacesIncomingRouteExpertMode.add(replace);
                groupReplacesIncomingRouteExpertMode.add(groupReplace);
                patternIncomingRouteExpertMode.add(patternReplace);
            }else{
                replacesOutgoingRouteExpertMode.add(replace);
                groupReplacesOutgoingRouteExpertMode.add(groupReplace);
                patternOutgoingRouteExpertMode.add(patternReplace);
            }
        }
    }

    private void deleteRoute(String direction){
        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE, "Изменить")
                .clickButtonTableRoute(direction, "delete");
        clickButtonConfirmAction("Продолжить");
        assertAll("Проверяем, что после удаления входящего маршрута:\n" +
                        "1. Не отображается в таблице маршрутов в столбце Направление значение Входящий\n" +
                        "2. Не отображается в таблице маршрутов Шаблон номера\n" +
                        "3. Не отображается в таблице маршрутов Шаблон замены",
                () -> assertTrue(isExistsTableText(direction, false),
                        "Отображается значение " + direction +
                                " в столбце Направление в таблице маршрутов после удаления маршрута"),
                () -> {
                    if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                        patternNumbersIncomingRoute.forEach((number) -> assertTrue(isExistsTableText(number, false),
                                "Отображается значение " + number + " с столбце Шаблон номера в таблице " +
                                        "маршрутов после удаления маршрута"));
                    else
                        patternNumbersOutgoingRoute.forEach((number) -> assertTrue(isExistsTableText(number, false),
                                "Отображается значение " + number + " с столбце Шаблон номера в таблице " +
                                        "маршрутов после удаления маршрута"));
                },
                () -> {
                    if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                        patternsReplaceIncomingRouteSimpleMode.forEach((replace) -> assertTrue(isExistsTableText(replace, false),
                                "Отображается значение " + replace + " с столбце Шаблон замены в таблице " +
                                        "маршрутов после удаления маршрута"));
                    else
                        patternsReplaceOutgoingRouteSimpleMode.forEach((replace) -> assertTrue(isExistsTableText(replace, false),
                                "Отображается значение " + replace + " с столбце Шаблон замены в таблице " +
                                        "маршрутов после удаления маршрута"));
                },
                () -> {
                    if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                        replacesIncomingRouteExpertMode.forEach((replace) -> assertTrue(isExistsTableText(replace, false),
                                "Отображается значение " + replace + " с столбце Шаблон замены в таблице " +
                                        "маршрутов после удаления маршрута"));
                    else
                        replacesOutgoingRouteExpertMode.forEach((replace) -> assertTrue(isExistsTableText(replace, false),
                                "Отображается значение " + replace + " с столбце Шаблон замены в таблице " +
                                        "маршрутов после удаления маршрута"));
                },
                () -> {
                    if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                        groupReplacesIncomingRouteExpertMode.forEach((gReplace) -> assertTrue(isExistsTableText(gReplace, false),
                                "Отображается значение " + gReplace + " с столбце Шаблон замены в таблице " +
                                        "маршрутов после удаления маршрута"));
                    else
                        groupReplacesOutgoingRouteExpertMode.forEach((gReplace) -> assertTrue(isExistsTableText(gReplace, false),
                                "Отображается значение " + gReplace + " с столбце Шаблон замены в таблице " +
                                        "маршрутов после удаления маршрута"));
                },
                () -> {
                    if(direction.equals(TELEPHONY_PROVIDER_INCOMING_ROUTE))
                        patternIncomingRouteExpertMode.forEach((pattern) -> assertTrue(isExistsTableText(pattern, false),
                                "Отображается значение " + pattern + " с столбце Шаблон замены в таблице " +
                                        "маршрутов после удаления маршрута"));
                    else
                        patternOutgoingRouteExpertMode.forEach((pattern) -> assertTrue(isExistsTableText(pattern, false),
                                "Отображается значение " + pattern + " с столбце Шаблон замены в таблице " +
                                        "маршрутов после удаления маршрута"));
                }
        );
    }
}
