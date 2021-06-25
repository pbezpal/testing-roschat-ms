package chat.ros.testing2.server.provider;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.provider.codefortests.Provider;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static chat.ros.testing2.data.SettingsData.TELEPHONY_PROVIDER_INTERVAL_WITH_REG;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ResourcesRoutePage.class)
@ExtendWith(WatcherTests.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic(value = "Настройки")
@Feature(value = "Телефония -> Маршрут - экспертный режим")
public class TestProviderRouteExpertMode extends Provider {
    //The data for the section general in the form provider
    private Map<String, String> dataGeneralProvider = new HashMap() {{
        put(TELEPHONY_PROVIDER_INPUT_NAME, TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE);
        put(TELEPHONY_PROVIDER_INPUT_ADDRESS, TELEPHONY_PROVIDER_ADDRESS_ROUTE_EXPERT_MODE);
    }};
    //The data for the section registration in the form provider
    private Map<String, String> dataRegistrationProvider = new HashMap() {{
        put(TELEPHONY_PROVIDER_INPUT_USERNAME, TELEPHONY_PROVIDER_USERNAME_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_PASSWORD, TELEPHONY_PROVIDER_PASSWORD_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_INTERVAL, TELEPHONY_PROVIDER_INTERVAL_WITH_REG);
    }};

    //The data for add route in expert mode with empty values
    private Map<String,String> dataRouteWithEmptyValues = new HashMap(){{
    }};

    //################# DATA INCOMING ROUTE ###############################
    //The data for add incoming route in expert mode
    private Map<String,String> dataIncomingRouteExpertMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_INCOMING_ROUTE_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_INCOMING_ROUTE_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, TELEPHONY_PROVIDER_INCOMING_ROUTE_EXPERT_MODE_GROUP_REPLACE);
    }};
    //The data for edit incoming route in expert mode
    private Map<String,String> dataEditIncomingRouteExpertMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_EXPERT_MODE_GROUP_REPLACE);
    }};
    //The data for edit incoming route in expert mode without group replace
    private Map<String,String> dataEditIncomingRouteExpertModeWithoutGroupReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, "");
    }};
    //The data for edit incoming route in expert mode without pattern replace and group replace
    private Map<String,String> dataEditIncomingRouteExpertModeWithoutPatternReplaceAndGroupReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, "");
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, "");
    }};

    //################# DATA OUTGOING ROUTE ###############################
    //The data for add outgoing route in expert mode
    private Map<String,String> dataOutgoingRouteExpertMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_OUTGOING_ROUTE_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_OUTGOING_ROUTE_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, TELEPHONY_PROVIDER_OUTGOING_ROUTE_EXPERT_MODE_GROUP_REPLACE);
    }};
    //The data for edit outgoing route in expert mode
    private Map<String,String> dataEditOutgoingRouteExpertMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_EXPERT_MODE_GROUP_REPLACE);
    }};
    //The data for edit outgoing route in expert mode without group replace
    private Map<String,String> dataEditOutgoingRouteExpertModeWithoutGroupReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, "");
    }};
    //The data for edit outgoing route in expert mode without pattern replace and group replace
    private Map<String,String> dataEditOutgoingRouteExpertModeWithoutPatternReplaceAndGroupReplace = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_OUTGOING_ROUTE_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, "");
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, "");
    }};

    @Story(value = "Добавляем провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Добавить провайдера\n" +
            "3. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "4. Проверяем, что провайдер появился в таблице провайдеров")
    @Test
    @Order(1)
    void test_Add_Provider_For_Route_In_Expert_Mode(){
        addProvider(dataGeneralProvider, dataRegistrationProvider);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Закрываем модальное окно при добавление входящего маршрута с пустыми полямит")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Закрыть\n" +
            "5. Проверяем, что маршрут не сохранился в таблице маршрутов")
    @Order(2)
    @Test
    void test_Close_Modal_Window_When_Add_Incoming_Route_With_Empty_Values(){
        closeModalWindowForAddRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE,
                TELEPHONY_PROVIDER_INCOMING_ROUTE,
                dataRouteWithEmptyValues,
                TELEPHONY_PROVIDER_BUTTON_CREATE_ROUTE,
                false);
    }

    @Story(value = "Закрываем модальное окно при добавление исходящего маршрута с пустыми полямит")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Закрыть\n" +
            "5. Проверяем, что маршрут не сохранился в таблице маршрутов")
    @Order(3)
    @Test
    void test_Close_Modal_Window_When_Add_Outgoing_Route_With_Empty_Values(){
        closeModalWindowForAddRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE,
                TELEPHONY_PROVIDER_OUTGOING_ROUTE,
                dataRouteWithEmptyValues,
                TELEPHONY_PROVIDER_BUTTON_NEW_ROUTE,
                false);
    }

    @Story(value = "Закрываем модальное окно при добавление входящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Закрыть\n" +
            "5. Проверяем, что маршрут не сохранился в таблице маршрутов")
    @Order(4)
    @Test
    void test_Close_Modal_Window_When_Add_Incoming_Route(){
        closeModalWindowForAddRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE,
                TELEPHONY_PROVIDER_INCOMING_ROUTE,
                dataIncomingRouteExpertMode,
                TELEPHONY_PROVIDER_BUTTON_CREATE_ROUTE,
                false);
    }

    @Story(value = "Добавляем входящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут сохранился в таблице маршрутов")
    @Test
    @Order(5)
    void test_Add_Incoming_Route_Expert_Mode(){
        addRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE
                , TELEPHONY_PROVIDER_INCOMING_ROUTE
                , dataIncomingRouteExpertMode
                , TELEPHONY_PROVIDER_BUTTON_CREATE_ROUTE
                , false);
    }

    @Story(value = "Закрываем модальное окно при добавление исходящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Новый маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Закрыть\n" +
            "5. Проверяем, что маршрут не отображается в таблице маршрутов")
    @Test
    @Order(6)
    void test_Close_Modal_Window_When_Add_Outgoing_Route_Expert_Mode(){
        closeModalWindowForAddRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE,
                TELEPHONY_PROVIDER_OUTGOING_ROUTE,
                dataOutgoingRouteExpertMode,
                TELEPHONY_PROVIDER_BUTTON_NEW_ROUTE,
                false);
    }

    @Story(value = "Добавляем исходящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут сохранился в таблице маршрутов")
    @Test
    @Order(7)
    void test_Add_Outgoing_Route_Expert_Mode(){
        addRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE
                , dataOutgoingRouteExpertMode
                , TELEPHONY_PROVIDER_BUTTON_NEW_ROUTE
                , false);
    }

    @Story(value = "Закрываем модальное окно при редактирование входящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Закрыть\n" +
            "5. Проверяем, что изменения не сохранились в таблице маршрутов")
    @Test
    @Order(8)
    void test_Close_Modal_Window_When_Edit_Incoming_Route_Expert_Mode(){
        closeModalWindowForEditRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE,
                TELEPHONY_PROVIDER_INCOMING_ROUTE,
                dataEditIncomingRouteExpertMode);
    }

    @Story(value = "Редактируем входящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(9)
    void test_Edit_Incoming_Route_Expert_Mode(){
        editRouteExpertMode(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE
                , TELEPHONY_PROVIDER_INCOMING_ROUTE
                , dataEditIncomingRouteExpertMode);
    }

    @Story(value = "Закрываем модальное окно при редактирование исходящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Закрыть\n" +
            "5. Проверяем, что изменения не сохранились в таблице маршрутов")
    @Test
    @Order(10)
    void test_Close_Modal_Window_When_Edit_Outgoing_Route_Expert_Mode(){
        closeModalWindowForEditRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE,
                TELEPHONY_PROVIDER_OUTGOING_ROUTE,
                dataEditOutgoingRouteExpertMode);
    }

    @Story(value = "Редактируем исходящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(11)
    void test_Edit_Outgoing_Route_Expert_Mode(){
        editRouteExpertMode(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE
                , dataEditOutgoingRouteExpertMode);
    }

    @Story(value = "Редактируем входящий маршрут без группы замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(12)
    void test_Edit_Incoming_Route_Expert_Mode_Without_Group_Replace(){
        editRouteExpertMode(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE
                , TELEPHONY_PROVIDER_INCOMING_ROUTE
                , dataEditIncomingRouteExpertModeWithoutGroupReplace);
    }

    @Story(value = "Редактируем исходящего маршрут без группы замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(13)
    void test_Edit_Outgoing_Route_Expert_Mode_Without_Group_Replace(){
        editRouteExpertMode(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE
                , dataEditOutgoingRouteExpertModeWithoutGroupReplace);
    }

    @Story(value = "Редактируем входящий маршрут без шаблона замены и группы замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(14)
    void test_Edit_Incoming_Route_Expert_Mode_Without_Pattern_Replace_And_Group_Replace(){
        editRouteExpertMode(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE
                , TELEPHONY_PROVIDER_INCOMING_ROUTE
                , dataEditIncomingRouteExpertModeWithoutPatternReplaceAndGroupReplace);
    }

    @Story(value = "Редактируем исходящий маршрут без шаблона замены и группы замены")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку edit у входящего маршрута\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(15)
    void test_Edit_Outgoing_Route_Expert_Mode_Without_Pattern_Replace_And_Group_Replace(){
        editRouteExpertMode(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE
                , dataEditOutgoingRouteExpertModeWithoutPatternReplaceAndGroupReplace);
    }

    @Story(value = "Удаление Входящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у входящего маршрутов\n" +
            "4. Подтверждаем удаление входящего маршрута" +
            "5. Проверяем, что запись о входящем маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(16)
    void test_Delete_Incoming_Route_In_Expert_Mode(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE, TELEPHONY_PROVIDER_INCOMING_ROUTE);
    }

    @Story(value = "Удаление Исходящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у входящего маршрутов\n" +
            "4. Подтверждаем удаление входящего маршрута" +
            "5. Проверяем, что запись о входящем маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(17)
    void test_Delete_Outgoing_Route_In_Expert_Mode(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_ROUTE_EXPERT_MODE, TELEPHONY_PROVIDER_OUTGOING_ROUTE);
    }

}
