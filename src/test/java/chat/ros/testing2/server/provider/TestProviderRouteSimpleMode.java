package chat.ros.testing2.server.provider;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.provider.codefortests.Provider;
import chat.ros.testing2.server.provider.codefortests.Routes;
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
public class TestProviderRouteSimpleMode extends Provider {

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
    //The data for testing close modal window when edit incoming route on expert mode
    private Map<String,String> dataEditIncomingRouteExpertMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_INCOMING_ROUTE_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_INCOMING_ROUTE_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, TELEPHONY_PROVIDER_INCOMING_ROUTE_EXPERT_MODE_GROUP_REPLACE);
    }};
    //The data edit incoming route from simple to expert mode
    private Map<String,String> dataEditIncomingRouteFromSimpleToExpertMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_TO_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_TO_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_TO_EXPERT_MODE_GROUP_REPLACE);
    }};
    //The data for edit incoming route in expert mode without group replace
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
    //The data for edit outgoing route in simple mode without pattern replace
    private Map<String,String> dataEditOutgoingRouteSimpleModeWithoutReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_SIMPLE_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_REPLACE, "");
    }};
    //The data for testing close modal window when edit outgoing route on expert mode
    private Map<String,String> dataEditOutgoingRouteExpertMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_OUTGOING_ROUTE_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_OUTGOING_ROUTE_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, TELEPHONY_PROVIDER_OUTGOING_ROUTE_EXPERT_MODE_GROUP_REPLACE);
    }};
    //The data for edit outgoing route from simple to expert mode
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
        addProvider(dataGeneralProvider);
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
        isVisibleInfoWrapper(true)
                .isTitleInfoWrapper("Описание синтаксиса составления шаблонов номеров")
                .isTitleInfoWrapper("Простой режим")
                .isContentInfoWrapper("+ - добавить префикс (пример, \"1+555\" совпадает с \"555\" и преобразуется" +
                        " в \"1555\")")
                .isContentInfoWrapper("| - удалить префикс (пример, \"1|1555\" совпадает с \"1555\" и преобразуется" +
                        " в \"555\")")
                .isContentInfoWrapper("Х - совпадение с символами 0-9")
                .isContentInfoWrapper("[] - совпадение с указанными символами (пример, [13-5] поиск совпадения" +
                        " с \"1\",\"3\",\"4\",\"5\")")
                .isContentInfoWrapper("[^] - отсутствие совпадения с указанными символами (пример, [^12] поиск всех" +
                        " символов кроме \"1\" и \"2\")")
                .isContentInfoWrapper("{} - число совпадающих символов (пример, [13-5]{3} поиск совпадения трех" +
                        " символов с \"1\",\"3\",\"4\",\"5\" )")
                .isContentInfoWrapper(". - совпадение с одним или более символов (пример, 7Х. поиск " +
                        "всех номеров с первым символом \"7\" и хотя бы одним символом [0-9]; допускается " +
                        "использовать с поиском совпадений Х,[] и [^]; не допускается использовать " +
                        "после \"+\" и \"|\")")
                .isContentInfoWrapper("! - совпадение с нулём или более символов (пример, 7Х! поиск " +
                        "всех номеров с первым символом \"7\" и символами [0-9] или только символа \"7\"; " +
                        "допускается использовать с поиском совпадений Х,[] и [^]); не допускается " +
                        "использовать после \"+\" и \"|\")")
                .isContentInfoWrapper("\\ - экранирование указанного символа (пример \\Х, поиск " +
                        "совпадения с символом \"X\")")
                .isContentInfoWrapper("Пример шаблона: 5+1|XXX")
                .isContentInfoWrapper("Номер до преобразования: 1043")
                .isContentInfoWrapper("Преобразованный номер: 5043")
                .isTitleInfoWrapper("Экспертный режим")
                .isContentInfoWrapper("В экспертном режиме регулярные выражения записываются в " +
                        "формате РOSIX.")
                .isContentInfoWrapper("Выражение - ^(1[0-9]{3})$");
        if (field.equals(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER))
            isContentInfoWrapper("Пример шаблона выбора номера: ^(1[0-9]{3})$, где");
        else
            isContentInfoWrapper("Пример шаблона замены номера: /^1([0-9]{3})$/5\\1/ ‚где");
        clickButtonCloseModalInfo().isVisibleInfoWrapper(false);
        clickButtonClose().isModalWindow(false);
    }

    @Story(value = "Проверяем заголовок модального окна при добавление провайдера через кнопку Создать маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Проверяем заголовок модального окна\n" +
            "5. Нажимаем кнопку Закрыть\n" +
            "5. Проверяем, что модальное окно закрылось")
    @Order(3)
    @Test
    void test_Header_Modal_Window_When_Add_Route_Button_Create_Route(){
        checkHeaderModalWindowWhenAddRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_BUTTON_CREATE_ROUTE);
    }

    @Story(value = "Проверяем заголовок модального окна при добавление провайдера через кнопку Новый маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Проверяем заголовок модального окна\n" +
            "5. Нажимаем кнопку Закрыть\n" +
            "6. Проверяем, что модальное окно закрылось")
    @Order(4)
    @Test
    void test_Header_Modal_Window_When_Add_Route_Button_New_Route(){
        checkHeaderModalWindowWhenAddRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_BUTTON_NEW_ROUTE);
    }

    @Story(value = "Закрываем модальное окно при добавление входящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Закрыть\n" +
            "5. Проверяем, что маршрут не отображается в таблице маршрутов")
    @Test
    @Order(5)
    void test_Close_Modal_Window_When_Add_Incoming_Route_Simple_Mode(){
        closeModalWindowForAddRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE,
                TELEPHONY_PROVIDER_INCOMING_ROUTE,
                dataIncomingRouteSimpleMode,
                TELEPHONY_PROVIDER_BUTTON_CREATE_ROUTE,
                true);
    }

    @Story(value = "Добавляем входящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(6)
    void test_Add_Incoming_Route_Simple_Mode(){
        addRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_INCOMING_ROUTE
                , dataIncomingRouteSimpleMode
                , TELEPHONY_PROVIDER_BUTTON_CREATE_ROUTE
                , true);
    }

    @Story(value = "Проверяем заголовок модального окна после добавления входящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Редактировать у маршрута\n" +
            "4. Проверяем заголовок модального окна\n" +
            "5. Нажимаем кнопку Закрыть\n" +
            "6. Проверяем, что модальное окно закрылось")
    @Order(7)
    @Test
    void test_Header_Modal_Window_After_Add_Incoming_Route(){
        checkHeaderModalWindowWhenEditRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_INCOMING_ROUTE);
    }

    @Story(value = "Закрываем модальное окно при добавление исходящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Новый маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Закрыть\n" +
            "5. Проверяем, что маршрут не отображается в таблице маршрутов")
    @Test
    @Order(8)
    void test_Close_Modal_Window_When_Add_Outgoing_Route_Simple_Mode(){
        closeModalWindowForAddRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE,
                TELEPHONY_PROVIDER_OUTGOING_ROUTE,
                dataOutgoingRouteSimpleMode,
                TELEPHONY_PROVIDER_BUTTON_NEW_ROUTE,
                true);
    }

    @Story(value = "Добавляем исходящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(9)
    void test_Add_Outgoing_Route_Simple_Mode(){
        addRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE
                , dataOutgoingRouteSimpleMode
                , TELEPHONY_PROVIDER_BUTTON_NEW_ROUTE
                , true);
    }

    @Story(value = "Проверяем заголовок модального окна после добавления исходящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Редактировать у маршрута\n" +
            "4. Проверяем заголовок модального окна\n" +
            "5. Нажимаем кнопку Закрыть\n" +
            "6. Проверяем, что модальное окно закрылось")
    @Order(10)
    @Test
    void test_Header_Modal_Window_After_Add_Outgoing_Route(){
        checkHeaderModalWindowWhenEditRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_INCOMING_ROUTE);
    }

    @Story(value = "Закрываем модальное окно при редактирование входящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Закрыть\n" +
            "5. Проверяем, что изменения не сохранились в таблице маршрутов")
    @Test
    @Order(11)
    void test_Close_Modal_Window_When_Edit_Incoming_Route_Simple_Mode(){
        closeModalWindowForEditRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE,
                TELEPHONY_PROVIDER_INCOMING_ROUTE,
                dataEditIncomingRouteSimpleMode,
                true);
    }

    @Story(value = "Редактируем входящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(12)
    void test_Edit_Incoming_Route_Simple_Mode(){
        editRouteSimpleMode(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_INCOMING_ROUTE
                , dataEditIncomingRouteSimpleMode);
    }

    @Story(value = "Закрываем модальное окно при редактирование исходящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Закрыть\n" +
            "5. Проверяем, что изменения не сохранились в таблице маршрутов")
    @Test
    @Order(13)
    void test_Close_Modal_Window_When_Edit_Outgoing_Route_Simple_Mode(){
        closeModalWindowForEditRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE,
                TELEPHONY_PROVIDER_OUTGOING_ROUTE,
                dataEditOutgoingRouteSimpleMode,
                true);
    }

    @Story(value = "Редактируем исходящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(14)
    void test_Edit_Outgoing_Route_Simple_Mode(){
        editRouteSimpleMode(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE
                , dataEditOutgoingRouteSimpleMode);
    }

    @Story(value = "Редактируем входящий маршрут без шаблона замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(15)
    void test_Edit_Incoming_Route_Simple_Mode_Without_Pattern_Replace(){
        editRouteSimpleMode(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_INCOMING_ROUTE
                , dataEditIncomingRouteSimpleModeWithoutReplace);
    }

    @Story(value = "Редактируем исходящий маршрут без шаблона замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(16)
    void test_Edit_Outgoing_Route_Simple_Mode_Without_Pattern_Replace(){
        editRouteSimpleMode(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE
                , dataEditOutgoingRouteSimpleModeWithoutReplace);
    }

    @Story(value = "Закрываем модальное окно при редактирование входящего маршрута c переключением в экспертный режим")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Переключаем настройки в экспертный режим\n" +
            "5. Заполняем поля и нажимаем кнопку Закрыть\n" +
            "6. Проверяем, что изменеия не сохранились в таблице маршрутов")
    @Test
    @Order(17)
    void test_Close_Modal_Window_When_Edit_Incoming_Route_From_Simple_To_Expert_Mode(){
        closeModalWindowForEditRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE,
                TELEPHONY_PROVIDER_INCOMING_ROUTE,
                dataEditIncomingRouteFromSimpleToExpertMode,
                false);
    }

    @Story(value = "Редактируем входящий маршрут с переключением в экспертный режим")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Переключаем настройки в экспертный режим\n" +
            "5. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "6. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(18)
    void test_Edit_Incoming_Route_From_Simple_To_Expert_Mode(){
        editRouteExpertMode(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_INCOMING_ROUTE
                , dataEditIncomingRouteFromSimpleToExpertMode, true);
    }

    @Story(value = "Проверяем заголовок модального окна после переключения входящего маршрута в экспертный режим")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Редактировать у маршрута\n" +
            "4. Проверяем заголовок модального окна\n" +
            "5. Нажимаем кнопку Закрыть\n" +
            "6. Проверяем, что модальное окно закрылось")
    @Order(19)
    @Test
    void test_Header_Modal_Window_After_Edit_Incoming_Route_From_Simple_To_Expert_Mode(){
        checkHeaderModalWindowWhenEditRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_INCOMING_ROUTE);
    }

    @Story(value = "Закрываем модальное окно при редактирование исходящего маршрута c переключением в экспертный режим")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Переключаем настройки в экспертный режим\n" +
            "5. Заполняем поля и нажимаем кнопку Закрыть\n" +
            "6. Проверяем, что изменеия не сохранились в таблице маршрутов")
    @Test

    @Order(20)
    void test_Close_Modal_Window_When_Edit_Outgoing_Route_From_Simple_To_Expert_Mode(){
        closeModalWindowForEditRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE,
                TELEPHONY_PROVIDER_OUTGOING_ROUTE,
                dataEditOutgoingRouteFromSimpleToExpertMode,
                false);
    }

    @Story(value = "Редактируем исходящий маршрут с переключением в экспертный режим")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у исходящего маршрута\n" +
            "4. Переключаем настройки в экспертный режим\n" +
            "5. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "6. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(21)
    void test_Edit_Outgoing_Route_From_Simple_To_Expert_Mode(){
        editRouteExpertMode(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE
                , dataEditOutgoingRouteFromSimpleToExpertMode
                , true);
    }

    @Story(value = "Проверяем заголовок модального окна после переключения исходящего маршрута в экспертный режим")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Редактировать у маршрута\n" +
            "4. Проверяем заголовок модального окна\n" +
            "5. Нажимаем кнопку Закрыть\n" +
            "6. Проверяем, что модальное окно закрылось")
    @Order(22)
    @Test
    void test_Header_Modal_Window_After_Edit_Outgoing_Route_From_Simple_To_Expert_Mode(){
        checkHeaderModalWindowWhenEditRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE);
    }

    @Story(value = "Закрываем модальное окно при редактирование входящего маршрута в экспертном режиме")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Закрыть\n" +
            "5. Проверяем, что изменеия не сохранились в таблице маршрутов")
    @Test
    @Order(23)
    void test_Close_Modal_Window_When_Edit_Incoming_Route_Expert_Mode(){
        closeModalWindowForEditRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE,
                TELEPHONY_PROVIDER_INCOMING_ROUTE,
                dataEditIncomingRouteExpertMode);
    }

    @Story(value = "Редактируем входящий маршрут в экспертном режиме без группы замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(24)
    void test_Edit_Incoming_Route_Expert_Mode_Without_Group_Replace(){
        editRouteExpertMode(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_INCOMING_ROUTE
                , dataEdiIncomingtRouteInExpertModeWithoutGroupReplace);
    }

    @Story(value = "Закрываем модальное окно при редактирование исходящего маршрута в экспертном режиме")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Закрыть\n" +
            "5. Проверяем, что изменеия не сохранились в таблице маршрутов")
    @Test
    @Order(25)
    void test_Close_Modal_Window_When_Edit_Outgoing_Route_Expert_Mode(){
        closeModalWindowForEditRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE,
                TELEPHONY_PROVIDER_OUTGOING_ROUTE,
                dataEditOutgoingRouteExpertMode);
    }

    @Story(value = "Редактируем исходящий маршрут в экспертном режиме без группы замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у исходящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(26)
    void test_Edit_Outgoing_Route_Expert_Mode_Without_Group_Replace(){
        editRouteExpertMode(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE
                , dataEditOutgoingRouteInExpertModeWithoutGroupReplace);
    }

    @Story(value = "Редактируем входящий маршрут в экспертном режиме без шаблона замены и группы замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(27)
    void test_Edit_Incoming_Route_Expert_Mode_Without_Pattern_Replace_And_Group_Replace(){
        editRouteExpertMode(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_INCOMING_ROUTE
                , dataEditIncomingRouteInExpertModeWithoutReplaceAndGroupReplace);
    }

    @Story(value = "Редактируем исходящего маршрут в экспертном режиме без шаблона замены и группы замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у исходящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(28)
    void test_Edit_Outgoing_Route_Expert_Mode_Without_Pattern_Replace_And_Group_Replace(){
        editRouteExpertMode(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE
                , dataEditOutgoingRouteInExpertModeWithoutReplaceAndGroupReplace);
    }

    @Story(value = "Удаление Входящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у входящего маршрутов\n" +
            "4. Подтверждаем удаление входящего маршрута" +
            "5. Проверяем, что запись о входящем маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(29)
    void test_Delete_Incoming_Route_In_Simple_Mode(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE, TELEPHONY_PROVIDER_INCOMING_ROUTE);
    }

    @Story(value = "Удаление Исходящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у исходящего маршрутов\n" +
            "4. Подтверждаем удаление исходящего маршрута" +
            "5. Проверяем, что запись о исходящем маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(30)
    void test_Delete_Outgoing_Route_In_Simple_Mode(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_SIMPLE_MODE, TELEPHONY_PROVIDER_OUTGOING_ROUTE);
    }
}
