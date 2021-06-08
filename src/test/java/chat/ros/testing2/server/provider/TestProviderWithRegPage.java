package chat.ros.testing2.server.provider;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.provider.codefortests.Provider;
import chat.ros.testing2.server.provider.codefortests.Routes;
import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ResourcesProviderPage.class)
@ExtendWith(WatcherTests.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic(value = "Настройки")
@Feature(value = "Телефония -> Провайдер c рагистрацией")
public class TestProviderWithRegPage extends Provider {

    private String buttonEdit = "Редактировать";
    private String buttonChange = "Изменить";

    //The data for the section general in the form provider
    private Map<String, String> dataGeneralProvider = new HashMap() {{
        put(TELEPHONY_PROVIDER_INPUT_NAME, TELEPHONY_PROVIDER_TITLE_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_DESCRIPTION, TELEPHONY_PROVIDER_DESCRIPTION_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_ADDRESS, TELEPHONY_PROVIDER_ADDRESS_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_AON, TELEPHONY_PROVIDER_AON_WITH_REG);
    }};
    //The data for the section registration in the form provider
    private Map<String, String> dataRegistrationProvider = new HashMap() {{
        put(TELEPHONY_PROVIDER_INPUT_USERNAME, TELEPHONY_PROVIDER_USERNAME_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_PASSWORD, TELEPHONY_PROVIDER_PASSWORD_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_INTERVAL, TELEPHONY_PROVIDER_INTERVAL_WITH_REG);
    }};
    //The data for edit the section general in the form provider
    private Map<String, String> dataEditGeneralProvider = new HashMap() {{
        put(TELEPHONY_PROVIDER_INPUT_NAME, TELEPHONY_PROVIDER_TITLE_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_DESCRIPTION, "");
        put(TELEPHONY_PROVIDER_INPUT_ADDRESS, TELEPHONY_PROVIDER_ADDRESS_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_AON, "");
    }};
    //The data for edit the section registration in the form provider
    private Map<String, String> dataEditRegistrationProvider = new HashMap() {{
        put(TELEPHONY_PROVIDER_INPUT_USERNAME, TELEPHONY_PROVIDER_EDIT_USERNAME_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_PASSWORD, TELEPHONY_PROVIDER_EDIT_PASSWORD_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_INTERVAL, TELEPHONY_PROVIDER_EDIT_INTERVAL_WITH_REG);
    }};
    //The data for add route in simple mode
    private Map<String,String> dataRouteSimpleMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_INCOMING_ROUTE_SIMPLE_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_INCOMING_ROUTE_SIMPLE_MODE_REPLACE);
    }};
    //The data for add route in simple mode after edit provider
    private Map<String,String> dataRouteSimpleModeAfterEditProvider = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_SIMPLE_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_SIMPLE_MODE_REPLACE);
    }};
    //The data for add route in expert mode
    private Map<String,String> dataRouteExpertMode = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_INCOMING_ROUTE_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_INCOMING_ROUTE_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, TELEPHONY_PROVIDER_INCOMING_ROUTE_EXPERT_MODE_GROUP_REPLACE);
    }};
    //The data for add route in expert mode after edit provider
    private Map<String,String> dataRouteExpertModeAfterEditProvider = new HashMap(){{
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_NUMBER, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_EXPERT_MODE_NUMBER);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_REPLACE, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_EXPERT_MODE_REPLACE);
        put(TELEPHONY_PROVIDER_ROUTE_EXPERT_MODE_INPUT_GROUP_REPLACE, TELEPHONY_PROVIDER_EDIT_INCOMING_ROUTE_EXPERT_MODE_GROUP_REPLACE);
    }};

    @Story(value = "Добавление провайдера с регистрации")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Добавить провайдера\n" +
            "3. Включаем регистрацию\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "5. Проверяем, что провайдер появился в таблице провайдеров")
    @Test
    @Order(1)
    void test_Add_Provider_With_Registration(){
        addProvider(dataGeneralProvider, dataRegistrationProvider);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем отображение настроек провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку изменить у провайдера\n" +
            "3. Проверяем, что настройки провайдера корректно отображаются в разеделе Провайдер")
    @Order(2)
    @Test
    void test_Show_Settings_Provider_With_Reg(){
        Map<String, String> dataProvider = new HashMap<>();
        dataProvider.putAll(dataGeneralProvider);
        dataProvider.putAll(dataRegistrationProvider);
        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_WITH_REG, buttonChange);
        verifyShowSettingsProvider(dataProvider, true);
    }

    @Story(value = "Добавляем входящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(3)
    void test_Add_Incoming_Rout_In_Simple_Mode(){
        addRoute(TELEPHONY_PROVIDER_TITLE_WITH_REG
                , TELEPHONY_PROVIDER_INCOMING_ROUTE
                , dataRouteSimpleMode
                , TELEPHONY_PROVIDER_BUTTON_CREATE_ROUTE, true);
    }

    @Story(value = "Добавляем исходящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Новый маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить" +
            "5. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(4)
    void test_Add_Outgoing_Rout_In_Expert_Mode(){
        addRoute(TELEPHONY_PROVIDER_TITLE_WITH_REG
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE
                , dataRouteExpertMode
                , TELEPHONY_PROVIDER_BUTTON_NEW_ROUTE, false);
    }

    @Story(value = "Удаление Входящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у входящего маршрутов\n" +
            "4. Подтверждаем удаление входящего маршрута" +
            "5. Проверяем, что запись о входящем маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(5)
    void test_Delete_Incoming_Route_In_Simple_Mode(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_WITH_REG, TELEPHONY_PROVIDER_INCOMING_ROUTE);
    }

    @Story(value = "Удаление Исходящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у исходящего маршрутов\n" +
            "4. Подтверждаем удаление исходящего маршрута" +
            "5. Проверяем, что запись о исходящего маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(6)
    void test_Delete_Route_Out_With_Expert_Mode_Of_Provider_With_Reg(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_WITH_REG, TELEPHONY_PROVIDER_OUTGOING_ROUTE);
    }

    @Story(value = "Редактируем провайдера с регистрацией")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку  Редактировать\n" +
            "3. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "4. Проверяем, что провайдер появился в таблице провайдеров")
    @Test
    @Order(7)
    void test_Edit_Provider_With_Reg(){
        Map<String, String> dataProvider = new HashMap<>();
        dataProvider.putAll(dataEditGeneralProvider);
        dataProvider.putAll(dataEditRegistrationProvider);
        editProvider(TELEPHONY_PROVIDER_TITLE_WITH_REG, dataProvider, true, buttonEdit);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем отображение настроек провайдера после редактирования")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку изменить у провайдера\n" +
            "3. Проверяем, что настройки провайдера корректно отображаются в разеделе Провайдер после редактирования")
    @Order(8)
    @Test
    void test_Show_Settings_Provider_With_Reg_After_Edit(){
        Map<String, String> dataProvider = new HashMap<>();
        dataProvider.putAll(dataEditGeneralProvider);
        dataProvider.putAll(dataEditRegistrationProvider);
        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_WITH_REG, buttonChange);
        verifyShowSettingsProvider(dataProvider, true);
    }

    @Story(value = "Добавляем исходящий маршрут после редактирования провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить" +
            "4. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(9)
    void test_Add_Outgoing_Rout_In_Simple_Mode_After_Edit_Provider(){
        addRoute(TELEPHONY_PROVIDER_TITLE_WITH_REG
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE
                , dataRouteSimpleModeAfterEditProvider
                , TELEPHONY_PROVIDER_BUTTON_CREATE_ROUTE, true);
    }

    @Story(value = "Добавляем Входящий маршрут после редактирования провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Новый маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить" +
            "4. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(10)
    void test_Add_Incoming_Rout_In_Expert_Mode_After_Edit_Provider(){
        addRoute(TELEPHONY_PROVIDER_TITLE_WITH_REG
                , TELEPHONY_PROVIDER_INCOMING_ROUTE
                , dataRouteExpertModeAfterEditProvider
                , TELEPHONY_PROVIDER_BUTTON_NEW_ROUTE, false);
    }

    @Story(value = "Удаление исходящего маршрута после редактирование провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у входящего маршрутов\n" +
            "4. Подтверждаем удаление входящего маршрута" +
            "5. Проверяем, что запись о входящем маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(11)
    void test_Delete_Outgoing_Route_In_Simple_Mode_After_Edit_Provider(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_WITH_REG, TELEPHONY_PROVIDER_OUTGOING_ROUTE);
    }

    @Story(value = "Удаление входящего маршрута после редактирования провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у исходящего маршрутов\n" +
            "4. Подтверждаем удаление исходящего маршрута" +
            "5. Проверяем, что запись о исходящего маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(12)
    void test_Delete_Incoming_Route_In_Expert_Mode_After_Edit_Provider(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_WITH_REG, TELEPHONY_PROVIDER_INCOMING_ROUTE);
    }

    @Story(value = "Редактируем провайдера без регистрации")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить\n" +
            "3. Нажимаем кнопку Настроить в разделе Провайдер\n" +
            "3. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "4. Проверяем, что провайдер появился в таблице провайдеров")
    @Test
    @Order(13)
    void test_Edit_Provider_Without_Reg(){
        editProvider(TELEPHONY_PROVIDER_TITLE_WITH_REG, dataGeneralProvider, false, buttonChange);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверка провайдера после включения регистрации")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Проверяем, что отображается Название в таблице провайдеров\n" +
            "3. Проверяем, что отображается Адрес в таблице провайдеров")
    @Test
    @Order(14)
    void test_Exist_Provider_Without_Registration_After_Edit(){
        verifyTableProvider(dataGeneralProvider, true);
    }

    @Story(value = "Добавляем входящий маршрут после редактирования провайдера без регистрации")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить" +
            "4. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(15)
    void test_Add_Incoming_Rout_In_Simple_Mode_After_Edit_Provider_Without_Reg(){
        addRoute(TELEPHONY_PROVIDER_TITLE_WITH_REG
                , TELEPHONY_PROVIDER_INCOMING_ROUTE
                , dataRouteSimpleMode
                , TELEPHONY_PROVIDER_BUTTON_CREATE_ROUTE, true);
    }

    @Story(value = "Добавляем исходящий маршрут после редактирования провайдера с регистрацией")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Новый маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить" +
            "4. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(16)
    void test_Add_Outgoing_Rout_In_Expert_Mode_After_Edit_Provider_Without_Reg(){
        addRoute(TELEPHONY_PROVIDER_TITLE_WITH_REG
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE
                , dataRouteExpertMode
                , TELEPHONY_PROVIDER_BUTTON_NEW_ROUTE, false);
    }

    @Story(value = "Удаление Входящего маршрута после редактирование провайдера с регистрацией")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у входящего маршрутов\n" +
            "4. Подтверждаем удаление входящего маршрута" +
            "5. Проверяем, что запись о входящем маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(17)
    void test_Delete_Incoming_Route_In_Simple_Mode_After_Edit_Provider_Without_Reg(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_WITH_REG, TELEPHONY_PROVIDER_INCOMING_ROUTE);
    }

    @Story(value = "Удаление Исходящего маршрута после редактирования провайдера с регистрацией")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у исходящего маршрутов\n" +
            "4. Подтверждаем удаление исходящего маршрута" +
            "5. Проверяем, что запись о исходящего маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(18)
    void test_Delete_Outgoing_Route_In_Expert_Mode_After_Edit_Provider_Without_Reg(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_WITH_REG, TELEPHONY_PROVIDER_OUTGOING_ROUTE);
    }

    @Story(value = "Удаляем провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Удалить\n" +
            "3. Нажимаем кнопку Подтверждение\n" +
            "4. Проверяем, что провайдер потсутствует в таблице")
    @Test
    @Order(19)
    void test_Delete_Provider_Without_Reg(){
        deleteProvider(TELEPHONY_PROVIDER_TITLE_WITH_REG, dataGeneralProvider, false);
    }
}
