package chat.ros.testing2.server.provider;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.provider.codefortests.Provider;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static chat.ros.testing2.data.SettingsData.TELEPHONY_PROVIDER_AON_WITHOUT_REG;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ResourcesProviderPage.class)
@ExtendWith(WatcherTests.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic(value = "Настройки")
@Feature(value = "Телефония -> Провайдер без рагистрации")
public class TestProviderWithoutRegPage extends Provider {

    private String buttonEdit = "Редактировать";
    private String buttonChange = "Изменить";

    //The data for the section general in the form provider
    private Map<String, String> dataGeneralProvider = new HashMap() {{
        put(TELEPHONY_PROVIDER_INPUT_NAME, TELEPHONY_PROVIDER_TITLE_WITHOUT_REG);
        put(TELEPHONY_PROVIDER_INPUT_DESCRIPTION, TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG);
        put(TELEPHONY_PROVIDER_INPUT_ADDRESS, TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG);
        put(TELEPHONY_PROVIDER_INPUT_AON, TELEPHONY_PROVIDER_AON_WITHOUT_REG);
    }};
    //The data for edit the section general in the form provider
    private Map<String, String> dataEditGeneralProvider = new HashMap() {{
        put(TELEPHONY_PROVIDER_INPUT_NAME, TELEPHONY_PROVIDER_TITLE_WITHOUT_REG);
        put(TELEPHONY_PROVIDER_INPUT_DESCRIPTION, "");
        put(TELEPHONY_PROVIDER_INPUT_ADDRESS, TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG);
        put(TELEPHONY_PROVIDER_INPUT_AON, "");
    }};
    //The data for the section registration in the form provider
    private Map<String, String> dataRegistrationProvider = new HashMap() {{
        put(TELEPHONY_PROVIDER_INPUT_USERNAME, TELEPHONY_PROVIDER_USERNAME_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_PASSWORD, TELEPHONY_PROVIDER_PASSWORD_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_INTERVAL, TELEPHONY_PROVIDER_INTERVAL_WITH_REG);
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

    @Story(value = "Проверяем заголовки и подзаголовков модального окна при добавление провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Добавить провайдера\n" +
            "3. Проверяем, отображается ли заголовок и подзаголовки модального окна\n" +
            "4. Нажимаем на кнопку Закрыть")
    @Order(1)
    @Test
    void test_Check_Title_And_Subtitles_Modal_Window_When_Add_Provider(){
        checkHeaderAndSubtitlesWindowModalProvider();
    }

    @Story(value = "Добавление провайдера без регистрации")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Добавить провайдера\n" +
            "3. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "4. Проверяем, что модальное окно закрылось после сохранения" +
            "4. Проверяем, что провайдер появился в таблице провайдеров")
    @Test
    @Order(2)
    void test_Add_Provider_Without_Registration(){
        addProvider(dataGeneralProvider);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем заголовка и подзаголовков модального окна при редактирование провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Редактировать у провайдера\n" +
            "3. Проверяем, отображается ли заголовок и подзаголовки модального окна\n" +
            "4. Нажимаем на кнопку Закрыть\n" +
            "5. Проверяем, что модальное окно закрылось")
    @Order(3)
    @Test
    void test_Check_Title_And_Subtitles_Modal_Window_When_Edit_Provider(){
        checkHeaderAndSubtitlesWindowModalProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, buttonEdit);
    }

    @Story(value = "Проверяем заголовка и подзаголовков модального окна при редактирование провайдера из просмотра " +
            "настроек провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провайдера\n" +
            "3. Нажимаем кнопку Настроить" +
            "3. Проверяем, отображается ли заголовок и подзаголовки модального окна\n" +
            "4. Нажимаем на кнопку Закрыть\n" +
            "5. Проверяем, что модальное окно закрылось")
    @Order(4)
    @Test
    void test_Check_Title_And_Subtitles_Modal_Window_When_Edit_Provider_From_View_Provider_Settings(){
        checkHeaderAndSubtitlesWindowModalProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, buttonChange);
    }

    @Story(value = "Проверяем заголовки и название полей при просмотре настроек провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провайдера\n" +
            "3. Проверяем заголовки и название полей отображения при просмотре настроек провайдера в разеделе Провайдер")
    @Order(5)
    @Test
    void test_Headers_And_Titles_View_Provider_Settings_Without_Registration(){
        checkHeadersAndTitlesViewProviderSettings(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, false);
    }

    @Story(value = "Проверяем отображение настроек провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку изменить у провайдера\n" +
            "3. Проверяем, что настройки провайдера корректно отображаются в разеделе Провайдер")
    @Order(6)
    @Test
    void test_View_Provider_Settings_Without_Registration(){
        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, buttonChange);
        checkViewProviderSettings(dataGeneralProvider, false);
    }

    @Story(value = "Добавляем входящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить" +
            "4. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(7)
    void test_Add_Incoming_Rout_In_Simple_Mode(){
        addRoute(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG
                , TELEPHONY_PROVIDER_INCOMING_ROUTE
                , dataRouteSimpleMode, TELEPHONY_PROVIDER_BUTTON_CREATE_ROUTE
                , true);
    }

    @Story(value = "Добавляем исходящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Новый маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить" +
            "4. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(8)
    void test_Add_Outgoing_Rout_In_Expert_Mode(){
        addRoute(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG
                , TELEPHONY_PROVIDER_OUTGOING_ROUTE
                , dataRouteExpertMode
                , TELEPHONY_PROVIDER_BUTTON_NEW_ROUTE
                , false);
    }

    @Story(value = "Удаление Входящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у входящего маршрутов\n" +
            "4. Подтверждаем удаление входящего маршрута" +
            "5. Проверяем, что запись о входящем маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(9)
    void test_Delete_Incoming_Route_In_Simple_Mode(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, TELEPHONY_PROVIDER_INCOMING_ROUTE);
    }

    @Story(value = "Удаление Исходящего маршрута")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у исходящего маршрутов\n" +
            "4. Подтверждаем удаление исходящего маршрута" +
            "5. Проверяем, что запись о исходящего маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(10)
    void test_Delete_Outgoing_Route_In_Expert_Mode(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, TELEPHONY_PROVIDER_OUTGOING_ROUTE);
    }

    @Story(value = "Редактируем провайдера без регистрации")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку  Редактировать\n" +
            "3. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "4. Проверяем, что провайдер появился в таблице провайдеров")
    @Test
    @Order(11)
    void test_Edit_Provider_Without_Reg(){
        editProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG,
                dataEditGeneralProvider,
                false,
                buttonEdit);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем заголовки и название полей при просмотре настроек провайдера после редактирования")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провайдера\n" +
            "3. Проверяем заголовки и название полей отображения при просмотре настроек провайдера в разеделе Провайдер")
    @Order(12)
    @Test
    void test_Headers_And_Titles_View_Provider_Settings_Without_Registration_After_Edit(){
        checkHeadersAndTitlesViewProviderSettings(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, false);
    }

    @Story(value = "Проверяем отображение настроек провайдера после редактирования")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку изменить у провайдера\n" +
            "3. Проверяем, что настройки провайдера корректно отображаются в разеделе Провайдер после редактирования")
    @Order(13)
    @Test
    void test_View_Provider_Settings_Without_Registration_After_Edit(){
        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, buttonChange);
        checkViewProviderSettings(dataEditGeneralProvider, false);
    }

    @Story(value = "Добавляем исходящий маршрут после редактирования провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить" +
            "4. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(14)
    void test_Add_Outgoing_Rout_In_Simple_Mode_After_Edit_Provider(){
        addRoute(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG
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
    @Order(15)
    void test_Add_Incoming_Rout_In_Expert_Mode_After_Edit_Provider(){
        addRoute(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG
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
    @Order(16)
    void test_Delete_Outgoing_Route_In_Simple_Mode_After_Edit_Provider(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, TELEPHONY_PROVIDER_OUTGOING_ROUTE);
    }

    @Story(value = "Удаление входящего маршрута после редактирования провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у исходящего маршрутов\n" +
            "4. Подтверждаем удаление исходящего маршрута" +
            "5. Проверяем, что запись о исходящего маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(17)
    void test_Delete_Incoming_Route_In_Expert_Mode_After_Edit_Provider(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, TELEPHONY_PROVIDER_INCOMING_ROUTE);
    }

    @Story(value = "Редактируем провайдера с регистрацией")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить\n" +
            "3. Нажимаем кнопку Настроить в разделе Провайдер\n" +
            "3. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "4. Проверяем, что провайдер появился в таблице провайдеров")
    @Test
    @Order(18)
    void test_Edit_Provider_With_Reg(){
        Map<String, String> dataProvider = new HashMap<>();
        dataProvider.putAll(dataEditGeneralProvider);
        dataProvider.putAll(dataRegistrationProvider);
        editProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, dataProvider, true, buttonChange);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем заголовки и название полей при просмотре настроек провайдера после редактирования с регистрацией")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провайдера\n" +
            "3. Проверяем заголовки и название полей отображения при просмотре настроек провайдера в разеделе Провайдер")
    @Order(19)
    @Test
    void test_Headers_And_Titles_View_Provider_Settings_With_Registration_After_Edit(){
        checkHeadersAndTitlesViewProviderSettings(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, true);
    }

    @Story(value = "Проверка провайдера после включения регистрации")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Проверяем, что отображается Название в таблице провайдеров\n" +
            "3. Проверяем, что отображается Адрес в таблице провайдеров")
    @Test
    @Order(20)
    void test_Exist_Provider_With_Registration(){
        checkExistProviderInTableProviders(dataEditGeneralProvider, true);
    }

    @Story(value = "Добавляем входящий маршрут после редактирования провайдера с регистрацией")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить" +
            "4. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(21)
    void test_Add_Incoming_Rout_In_Simple_Mode_After_Edit_Provider_With_Reg(){
        addRoute(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG
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
    @Order(22)
    void test_Add_Outgoing_Rout_In_Expert_Mode_After_Edit_Provider_With_Reg(){
        addRoute(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG
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
    @Order(23)
    void test_Delete_Incoming_Route_In_Simple_Mode_After_Edit_Provider_With_Reg(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, TELEPHONY_PROVIDER_INCOMING_ROUTE);
    }

    @Story(value = "Удаление Исходящего маршрута после редактирования провайдера с регистрацией")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Удаления у исходящего маршрутов\n" +
            "4. Подтверждаем удаление исходящего маршрута" +
            "5. Проверяем, что запись о исходящего маршруте пропадала в таблице маршрутов после удаления")
    @Test
    @Order(24)
    void test_Delete_Outgoing_Route_In_Expert_Mode_After_Edit_Provider_With_Reg(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, TELEPHONY_PROVIDER_OUTGOING_ROUTE);
    }

    @Story(value = "Удаляем провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Удалить\n" +
            "3. Нажимаем кнопку Подтверждение\n" +
            "4. Проверяем, что провайдер потсутствует в таблице")
    @Test
    @Order(25)
    void test_Delete_Provider_With_Reg(){
        deleteProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, dataGeneralProvider, true);
    }
}
