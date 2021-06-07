package chat.ros.testing2.server.provider;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.provider.codefortests.Provider;
import chat.ros.testing2.server.provider.codefortests.Routes;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static chat.ros.testing2.data.SettingsData.TELEPHONY_PROVIDER_AON_WITHOUT_REG;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ResourcesProviderPage.class)
@ExtendWith(WatcherTests.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic(value = "Настройки")
@Feature(value = "Телефония -> Провайдер без рагистрации")
public class TestProviderWithoutRegPage extends Provider {

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

    @Story(value = "Добавление провайдера без регистрации")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Добавить провайдера\n" +
            "3. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "4. Проверяем, что провайдер появился в таблице провайдеров")
    @Test
    @Order(1)
    void test_Add_Provider_Without_Registration(){
        addProvider(dataGeneralProvider);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем отображение настроек провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку изменить у провайдера\n" +
            "3. Проверяем, что настройки провайдера корректно отображаются в разеделе Провайдер")
    @Order(2)
    @Test
    void test_Show_Settings_Provider_Without_Reg(){
        verifyShowSettingsProvider(dataGeneralProvider, false);
    }

    @Story(value = "Добавляем входящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить" +
            "4. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(3)
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
    @Order(4)
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
    @Order(5)
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
    @Order(6)
    void test_Delete_Outgoing_Route_In_Expert_Mode(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, TELEPHONY_PROVIDER_OUTGOING_ROUTE);
    }

    @Story(value = "Редактируем провайдера без регистрации")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку  Редактировать\n" +
            "3. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "4. Проверяем, что провайдер появился в таблице провайдеров")
    @Test
    @Order(7)
    void test_Edit_Provider_Without_Reg(){
        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, "Редактировать");
        assertAll("1. Проверяем, что правильно отображаются заголовок и подзаголовки модального окна\n" +
                        "2. Заполняем поля модального окна и сохраняем настройки\n" +
                        "3. Проверяем, что провайдер добавлен в таблицу провайдеров",
                () -> assertEquals(getTitleOfModalWindow(),
                        "Провайдеры",
                        "Не найден заголовок модального окна при добавлении провайдера"),
                () -> assertTrue(isSubtitleModalWindow("Общее"),
                        "Не найден подзаголовок 'Общее' модального окна"),
                () -> assertTrue(isSubtitleModalWindow("Регистрация настроек провайдера"),
                        "Не найден подзаголовок 'Регистрация настроек провайдера' модального окна"),
                () -> {setProvider(dataEditGeneralProvider);},
                () -> assertTrue(isExistsTableText(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, true),
                        "Не отображается название " + TELEPHONY_PROVIDER_TITLE_WITHOUT_REG + " в таблице" +
                                " провайдеров после редактирования"),
                () -> assertTrue(isExistsTableText(TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG, false),
                        "Отображается описание " + TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG + " в таблице" +
                                " провайдеров после редактирования"),
                () -> assertTrue(isExistsTableText(TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG, true),
                        "Не отображается адрес " + TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG + " в таблице" +
                                " провайдеров после реадктирования")
        );
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверяем отображение настроек провайдера после редактирования")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку изменить у провайдера\n" +
            "3. Проверяем, что настройки провайдера корректно отображаются в разеделе Провайдер после редактирования")
    @Order(8)
    @Test
    void test_Show_Settings_Provider_Without_Reg_After_Edit(){
        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, "Изменить");
        assertAll("1. Нажимаем кнопку изманить\n" +
                        "2. Проверяем, что в разделе Провайдер отображаются настройки провайдера " + TELEPHONY_PROVIDER_TITLE_WITHOUT_REG,
                () -> assertTrue(isContentSettingProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, true),
                        "Не отображается название " + TELEPHONY_PROVIDER_TITLE_WITHOUT_REG + " в настройках провадера"),
                () -> assertTrue(isContentSettingProvider(TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG, false),
                        "Отображается описание " + TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG + " в настройках провадера"),
                () -> assertTrue(isContentSettingProvider(TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG, true),
                        "Не отображается адрес провайдера " + TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG + " в настройках провадера"),
                () -> assertTrue(isContentSettingProvider(TELEPHONY_PROVIDER_AON_WITHOUT_REG, false),
                        "Отображается АОН " + TELEPHONY_PROVIDER_AON_WITHOUT_REG + " в настройках провадера")
        );
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
    @Order(10)
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
    @Order(11)
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
    @Order(12)
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
    @Order(13)
    void test_Edit_Provider_With_Reg(){
        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, "Изменить");
        clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_PROVIDER, "Настроить");
        assertAll("1. Проверяем, что правильно отображаются заголовок и подзаголовки модального окна\n" +
                        "2. Заполняем поля модального окна и сохраняем настройки\n" +
                        "3. Проверяем, что провайдер добавлен в таблицу провайдеров",
                () -> assertEquals(getTitleOfModalWindow(),
                        "Провайдеры",
                        "Не найден заголовок модального окна при добавлении провайдера"),
                () -> assertTrue(isSubtitleModalWindow("Общее"),
                        "Не найден подзаголовок 'Общее' модального окна"),
                () -> assertTrue(isSubtitleModalWindow("Регистрация настроек провайдера"),
                        "Не найден подзаголовок 'Регистрация настроек провайдера' модального окна"),
                () -> {setProvider(dataGeneralProvider, dataRegistrationProvider, true);},
                () -> assertTrue(isContentSettingProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, true),
                        "Не отображается название " + TELEPHONY_PROVIDER_TITLE_WITHOUT_REG + " в настройках" +
                                " провадера"),
                () -> assertTrue(isContentSettingProvider(TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG, true),
                "Не отображается описание " + TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG + " в " +
                        "настройках провадера"),
                () -> assertTrue(isContentSettingProvider(TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG, true),
                "Не отображается адрес провайдера " + TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG + " в " +
                        "настройках провадера"),
                () -> assertTrue(isContentSettingProvider(TELEPHONY_PROVIDER_AON_WITHOUT_REG, true),
                "Не отображается АОН " + TELEPHONY_PROVIDER_AON_WITHOUT_REG + " в настройках провадера после" +
                        " редактирования"),
                () -> assertTrue(isContentSettingProvider(TELEPHONY_PROVIDER_USERNAME_WITH_REG, true),
                "Не отображается Имя пользователя " + TELEPHONY_PROVIDER_USERNAME_WITH_REG + " в настройках" +
                        " провадера после редактирования"),
                () -> assertTrue(isContentSettingProvider(TELEPHONY_PROVIDER_INTERVAL_WITH_REG, true),
                "Не отображается Интервал регистрации " + TELEPHONY_PROVIDER_INTERVAL_WITH_REG + " в " +
                        "настройках провадера после редактирования")
        );
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Проверка провайдера после включения регистрации")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Проверяем, что отображается Название в таблице провайдеров\n" +
            "3. Проверяем, что отображается Адрес в таблице провайдеров")
    @Test
    @Order(14)
    void test_Exist_Provider_With_Registration(){
        assertAll("1. Проверяем, что правильно отображаются Название в таблице провайдеров\n" +
                        "2. Проверяем, что правильно отображаются Адрес в таблице провайдеров",
                () -> assertTrue(isExistsTableText(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, true),
                        "Не отображается название " + TELEPHONY_PROVIDER_TITLE_WITHOUT_REG + " в таблице провайдеров"),
                () -> assertTrue(isExistsTableText(TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG, true),
                        "Не отображается описание " + TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG + " в таблице " +
                                "провайдеров после редактирования"),
                () -> assertTrue(isExistsTableText(TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG, true),
                        "Не отображается адрес " + TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG + " в таблице " +
                                "провайдеров после редактирования")
        );
    }

    @Story(value = "Добавляем входящий маршрут после редактирования провайдера с регистрацией")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить" +
            "4. Проверяем, что маршрут появился в таблице маршрутов")
    @Test
    @Order(15)
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
    @Order(16)
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
    @Order(17)
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
    @Order(18)
    void test_Delete_Outgoing_Route_In_Expert_Mode_After_Edit_Provider_With_Reg(){
        deleteRoute(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, TELEPHONY_PROVIDER_OUTGOING_ROUTE);
    }

    @Story(value = "Удаляем провайдера")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Удалить\n" +
            "3. Нажимаем кнопку Подтверждение\n" +
            "4. Проверяем, что провайдер потсутствует в таблице")
    @Test
    @Order(19)
    void test_Delete_Provider_With_Reg(){
        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, "Удалить");
        clickButtonConfirmAction("Продолжить");
        assertAll("1. Проверяем, что отсутствует Название " + TELEPHONY_PROVIDER_TITLE_WITHOUT_REG + " не" +
                        " отображается в таблице провайдеров после удаления \n 2. Проверяем, что отсутствует Адрес " +
                        "" + TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG + " не отображается в таблице провайдеров",
                () -> assertTrue(isExistsTableText(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, false),
                        "Отображается название " + TELEPHONY_PROVIDER_TITLE_WITHOUT_REG + " в таблице " +
                                "провайдеров после удаления"),
                () -> assertTrue(isExistsTableText(TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG, false),
                        "Отображается описание " + TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG + " в таблице " +
                                "провайдеров после удаления"),
                () -> assertTrue(isExistsTableText(TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG, false),
                        "Отображается адрес " + TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG + " в таблице " +
                                "провайдеров после удаления")
        );
    }
}
