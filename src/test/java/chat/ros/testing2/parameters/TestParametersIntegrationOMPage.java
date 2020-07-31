package chat.ros.testing2.parameters;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.ServerPage;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import chat.ros.testing2.server.settings.integration.SKUDPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.ParametersData.*;
import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Интеграция")
public class TestParametersIntegrationOMPage extends ServerPage implements IntegrationPage {

    private SKUDPage skudPage;
    private String field;
    private static ServerPage serverPage = new ServerPage();

    public static Object[][] getEmptyValueOM(){
        return new Object[][] {
                {"",INTEGRATION_SERVICE_OM_PORT_BD, INTEGRATION_SERVICE_OM_LOGIN_DB},
                {INTEGRATION_SERVICE_OM_IP_ADDRESS,"", INTEGRATION_SERVICE_OM_LOGIN_DB},
                {INTEGRATION_SERVICE_OM_IP_ADDRESS,INTEGRATION_SERVICE_OM_PORT_BD,""}};
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> getWrongValueServerOM() {
        ArrayList<String> data = new ArrayList<>();

        for (String ip: WRONG_VALUE_IP_ADDRESS) {
            data.add(ip);
        }

        return data;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Character> getWrongSymbolsPortOM() {
        ArrayList<Character> data = new ArrayList<>();

        for (Character symbol: WRONG_SYMBOLS_PORT.toCharArray()) {
            data.add(symbol);
        }

        return data;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Character> getValidValuePortOM() {
        ArrayList<Character> data = new ArrayList<>();

        for (Character symbol: VALID_SYMBOLS_PORT.toCharArray()) {
            data.add(symbol);
        }

        return data;
    }

    @BeforeEach
    public void setUp(){
        field = null;
    }

    @Story(value = "Проверяем настройки СКУД Офис-Монитор на пустые поля")
    @Description(value = "Вводим в настройках СКУД Офис-Монитор пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Введите значение' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @ParameterizedTest
    @MethodSource("getEmptyValueOM")
    void test_Settings_OM_Empty_Value(String ip, String port, String username){
        Map<String, String> mapInputValueOM = new HashMap() {{
            put("IP адрес", ip);
            put("Порт БД", port);
            put("Имя пользователя БД", username);
        }};
        sendInputsForm(mapInputValueOM);
        clickInputForm("Пароль БД");
        if(ip.equals("")) field = "IP адрес";
        else if(port.equals("")) field = "Порт БД";
        else field = "Имя пользователя БД";
        assertAll("Проверяем настрйоки СКУД ОМ с пустыми полями",
                () -> assertEquals(isShowTextWrongValue(field),"Введите значение",
                        "Надпись 'Введите значение' не появилась"),
                () -> { clickButtonSave(); },
                () -> assertTrue(isFormChange(),
                        "Форма редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия"));
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
    }

    @Story(value = "Проверяем невалидные значение IP адреса в настройках СКУД Офис-Монитор")
    @Description(value = "Вводим в настройках СКУД Офис-Монитор пустые значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный IP адрес' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидными значениями")
    @ParameterizedTest
    @MethodSource("getWrongValueServerOM")
    void test_Wrong_IP_OM(String ip){
        sendInputForm("IP адрес", ip);
        assertAll("Проверяем IP адрес на невалидные значения",
                () -> assertEquals(isShowTextWrongValue("IP адрес"),"Невалидный IP адрес",
                        "Надпись 'Невалидный IP адрес' не появилась"),
                () -> {clickButtonSave();},
                () -> assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия")
                );
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
    }

    @Story(value = "Проверяем невалидные значения портов в настройках СКУД Офис-Монитор")
    @Description(value = "Вводим невалидные значения портов в настройках СКУД Офис-Монитор и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @ParameterizedTest
    @MethodSource("getWrongSymbolsPortOM")
    void test_Wrong_Symbols_Ports_OM(Character c){
        sendInputForm("Порт БД", c.toString());
        assertAll("Проверяем настройки Порт БД с невалидными значениями",
                () -> assertEquals(isShowTextWrongValue("Порт БД"),"Невалидный порт",
                        "Надпись 'Невалидный порт' не появилась"),
                () -> { clickButtonSave(); },
                () -> assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия")
        );
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
    }

    @Story(value = "Проверяем номер максимального порта в настройках СКУД Офис-Монитор")
    @Description(value = "Вводим в поле 'Порт БД' СКУД Офис-Монитор порт 65536 и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test
    void test_Max_Length_Ports_OM(){
        sendInputForm("Порт БД", MORE_MAX_VALUE_PORT);
        assertAll("Вводим порт БД больше максимально возможного",
                () -> assertEquals(isShowTextWrongValue("Порт БД"),"Невалидный порт",
                        "Надпись 'Невалидный порт' не появилась"),
                () -> { clickButtonSave(); },
                () -> assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия")
                );
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
    }

    @Story(value = "Проверяем валидные значения портов в настройках СКУД Офис-Монитор")
    @Description(value = "Вводим валидные значения портов в настройках СКУД Офис-Монитор и проверяем: \n" +
            "Сохраняются ли настройки с валидным значением в поле")
    @ParameterizedTest
    @MethodSource("getValidValuePortOM")
    void test_Valid_Symbols_Ports_OM(Character c){
        sendInputForm("Порт БД", c.toString());
        clickButtonSave();
        assertAll("Проверяем валидное значение " + c.toString(),
                () -> assertTrue(isFormConfirmActions(true), "Не появилась форма, Подтвердите свои действия")
        );
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
    }
}
