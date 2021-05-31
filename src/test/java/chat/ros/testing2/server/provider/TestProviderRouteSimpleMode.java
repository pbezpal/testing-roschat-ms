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
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ResourcesRoutePage.class)
@ExtendWith(WatcherTests.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic(value = "Настройки")
@Feature(value = "Телефония -> Маршрут - простой режим")
public class TestProviderRouteSimpleMode extends TelephonyPage {

    private String patternReplaceExpertMode;
    //The data for the section general in the form provider
    private Map<String, String> dataGeneralProvider = new HashMap() {{
        put(TELEPHONY_PROVIDER_INPUT_NAME, TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE);
        put(TELEPHONY_PROVIDER_INPUT_ADDRESS, TELEPHONY_PROVIDER_ADDRESS_ROUTE_SIMPLE_MODE);
    }};
    //The data for create route with simple mode with replace
    private Map<String,String> dataRouteSimpleModeWithReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_REPLACE);
    }};
    //The data for create route with simple mode without replace
    private Map<String,String> dataRouteSimpleModeWithoutReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_REPLACE, "");
    }};
    private Map<String,String> dataEditRouteFromSimpleToExpertMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_ROUTE_SIMPLE_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_ROUTE_SIMPLE_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, TELEPHONY_PROVIDER_EDIT_ROUTE_SIMPLE_MODE_GROUP_REPLACE);
    }};
    private Map<String,String> dataEditRouteInExpertMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_ROUTE_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_ROUTE_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, TELEPHONY_PROVIDER_EDIT_ROUTE_EXPERT_MODE_GROUP_REPLACE);
    }};
    private Map<String,String> dataEditRouteInExpertModeWithoutGroupReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_ROUTE_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_ROUTE_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, "");
    }};
    private Map<String,String> dataEditRouteInExpertModeWithoutReplaceAmdGroupReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_ROUTE_EXPERT_MODE_NUMBER);
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
    void test_Add_Provider(){
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
            "4. Нажимаем на иконку для открытия информационного окна" +
            "4. Проверяем, что окно информации открылось и информация отображается в окне")
    @ParameterizedTest(name="Field = {0}")
    @MethodSource(value = "fieldsRouteSimpleMode")
    @Order(2)
    void test_Modal_Info_Of_Route(String field){
        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE, "Изменить");
        clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE, "Создать маршрут");
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
                () -> assertTrue(isContentInfoWrapper("Пример шаблона выбора номера: ^(1[0-9]{3})$, где"),
                        "Не отображается текст 'Пример шаблона выбора номера: ^(1[0-9]{3})$, где' в " +
                                "модальном окне с информацией"),
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
            "4. Заполняем поля и нажимаем кнопку Сохранить" +
            "4. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(3)
    void test_Add_Rout_In_Simple_Mode(){
        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE, "Изменить");
        clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE, "Создать маршрут");
        assertAll("1. Проверяем, что правильно отображаются заголовок и подзаголовки модального окна\n" +
                        "2. Заполняем поля модального окна и сохраняем настройки\n" +
                        "3. Проверяем, что провайдер добавлен в таблицу провайдеров",
                () -> assertEquals(getTitleOfModalWindow(),
                        "Провайдеры",
                        "Не найден заголовок модального окна при добавлении провайдера"),
                () -> {
                    createRoute(TELEPHONY_PROVIDER_ROUTE_IN, true, dataRouteSimpleModeWithReplace);
                    clickButtonSave();
                    clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
                },
                () -> assertTrue(isExistsTableText(TELEPHONY_PROVIDER_ROUTE_IN, true),
                        "Не отображается значение " + TELEPHONY_PROVIDER_ROUTE_IN +
                                " в столбце Направление в таблице маршрутов"),
                () -> assertTrue(isExistsTableText(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_NUMBER, true),
                        "Не отображается значение " + TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_NUMBER +
                                " в столбце Шаблон номера в таблице маршрутов")
        );
        TestStatusResult.setTestResult(true);
        assertTrue(isExistsTableText(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_REPLACE, true),
                "Не отображается Шаблон замены " + TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_REPLACE +
                        " в столбце Шаблон номера в таблице маршрутов");
    }
}
