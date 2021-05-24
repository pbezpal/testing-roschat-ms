package chat.ros.testing2.server.provider;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.TelephonyPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static chat.ros.testing2.data.SettingsData.TELEPHONY_PROVIDER_AON_WITHOUT_REG;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ResourcesProviderPage.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Телефония")
public class TestProviderWithoutRegPage extends TelephonyPage {

    private Map<String, String> mapInputValueProviderWithoutReg = new HashMap() {{
        put(TELEPHONY_PROVIDER_INPUT_NAME, TELEPHONY_PROVIDER_TITLE_WITHOUT_REG);
        put(TELEPHONY_PROVIDER_INPUT_DESCRIPTION, TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG);
        put(TELEPHONY_PROVIDER_INPUT_ADDRESS, TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG);
        put(TELEPHONY_PROVIDER_INPUT_AON, TELEPHONY_PROVIDER_AON_WITHOUT_REG);
    }};
    private Map<String, String> mapInputValueProviderEditWithoutReg = new HashMap() {{
        put(TELEPHONY_PROVIDER_INPUT_NAME, TELEPHONY_PROVIDER_TITLE_WITHOUT_REG);
        put(TELEPHONY_PROVIDER_INPUT_DESCRIPTION, "");
        put(TELEPHONY_PROVIDER_INPUT_ADDRESS, TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG);
        put(TELEPHONY_PROVIDER_INPUT_AON, "");
    }};
    private Map<String, String> mapInputValueProviderEditWithReg = new HashMap() {{
        put(TELEPHONY_PROVIDER_INPUT_USERNAME, TELEPHONY_PROVIDER_EDIT_USERNAME_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_PASSWORD, TELEPHONY_PROVIDER_EDIT_PASSWORD_WITH_REG);
        put(TELEPHONY_PROVIDER_INPUT_ADDRESS, TELEPHONY_PROVIDER_EDIT_INTERVAL_WITH_REG);
    }};

    @Story(value = "Добавление провайдера без регистрации")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Добавить провайдера\n" +
            "3. Заполняем поля и нажимаем кнопку Сохранить\n" +
            "4. Проверяем, что провайдер появился в таблице провайдеров")
    @Test
    @Order(1)
    void test_Add_Provider_Without_Registration(){
        clickButtonSettings(TELEPHONY_PROVIDER_TITLE_FORM, "Добавить провайдера");
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
                () -> {setProvider(mapInputValueProviderWithoutReg, false);},
                () -> assertTrue(isExistsTableText(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, true),
                        "Не отображается название " + TELEPHONY_PROVIDER_TITLE_WITHOUT_REG + " в таблице провайдеров"),
                () -> assertTrue(isExistsTableText(TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG, true),
                        "Не отображается описание " + TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG + " в таблице провайдеров"),
                () -> assertTrue(isExistsTableText(TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG, true),
                        "Не отображается адрес " + TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG + " в таблице провайдеров")
        );
        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, "Изменить");
        assertAll("1. Нажимаем кнопку изманить\n" +
                "2. Проверяем, что в разделе Провайдер отображаются настройки провайдера " + TELEPHONY_PROVIDER_TITLE_WITHOUT_REG,
                () -> assertTrue(isContentSettingProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, true),
                        "Не отображается название " + TELEPHONY_PROVIDER_TITLE_WITHOUT_REG + " в настройках провадера"),
                () -> assertTrue(isContentSettingProvider(TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG, true),
                        "Не отображается описание " + TELEPHONY_PROVIDER_DESCRIPTION_WITHOUT_REG + " в настройках провадера"),
                () -> assertTrue(isContentSettingProvider(TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG, true),
                        "Не отображается адрес провайдера " + TELEPHONY_PROVIDER_ADDRESS_WITHOUT_REG + " в настройках провадера"),
                () -> assertTrue(isContentSettingProvider(TELEPHONY_PROVIDER_AON_WITHOUT_REG, true),
                        "Не отображается АОН " + TELEPHONY_PROVIDER_AON_WITHOUT_REG + " в настройках провадера")
        );
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Добавляем входящий маршрут")
    @Description(value = "1. Переходим в раздел Настройки -> Телефония\n" +
            "2. Нажимаем кнопку Изменить у провадера в таблице провайдеров\n" +
            "3. Нажимаем кнопку Создать маршрут\n" +
            "4. Заполняем поля и нажимаем кнопку Сохранить" +
            "4. Проверяем, что маршрут появился в таблице маршрутизаторов")
    @Test
    @Order(2)
    void test_Add_Rout_Of_Provider_Without_Reg(){
        clickButtonTableProvider(TELEPHONY_PROVIDER_TITLE_WITHOUT_REG, "Изменить");
        clickButtonSettings(TELEPHONE_PROVIDER_EDIT_TITLE_ROUTE, "Создать маршрут");
        assertAll("1. Проверяем, что правильно отображаются заголовок и подзаголовки модального окна\n" +
                        "2. Заполняем поля модального окна и сохраняем настройки\n" +
                        "3. Проверяем, что провайдер добавлен в таблицу провайдеров",
                () -> assertEquals(getTitleOfModalWindow(),
                        "Провайдеры",
                        "Не найден заголовок модального окна при добавлении провайдера"),
                () -> {
                    setRoute("5000", "Входящий", true, "/^1([0-9]{3})$/5\\1/");
                    clickButtonSave();
                    clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
                },
                () -> assertTrue(isExistsTableText("Входящий", true),
                        "Не отображается название Входящий в таблице маршрутов"),
                () -> assertTrue(isExistsTableText("5000", true),
                        "Не отображается описание 5000 в таблице маршрутизаторов"),
                () -> assertTrue(isExistsTableText("/^1([0-9]{3})$/5\\1/", true),
                        "Не отображается адрес /^1([0-9]{3})$/5\\1/ в таблице провайдеров")
        );
        TestStatusResult.setTestResult(true);
    }

}
