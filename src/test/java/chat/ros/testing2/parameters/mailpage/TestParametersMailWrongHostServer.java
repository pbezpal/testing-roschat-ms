package chat.ros.testing2.parameters.mailpage;

import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.parameters.ResourcesParameters;
import chat.ros.testing2.server.settings.MailPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static chat.ros.testing2.data.ParametersData.*;
import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ResourcesParameters.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Почта")
public class TestParametersMailWrongHostServer extends MailPage {

    private Map mapValueMail = null;

    public static Stream<String> getWrongValueHostMailServer() {
        return Arrays.stream(WRONG_VALUE_HOST);
    }

    @Story(value = "Проверяем невалидные значение хоста в поле 'Адрес почтового сервера' в настройках Почты")
    @Description(value = "Вводим невалидные значения в поле 'Адрес почтового сервера' в настройках Почты и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный адрес' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @ParameterizedTest(name = "#{index} => address=''{0}''")
    @MethodSource(value = "getWrongValueHostMailServer")
    void test_Wrong_Host_Server_Email(String address){
        this.mapValueMail = getSettingsMailServer(address, MAIL_INFOTEK_USERNAME,
                MAIL_INFOTEK_PASSWORD, MAIL_PORT_NO_SECURITY, MAIL_INFOTEK_FROM_USER, MAIL_INFOTEK_FROM_MAIL);
        settingsMailServerWithoutSave(this.mapValueMail, MAIL_TYPE_SECURITY_NO);
        assertAll("Проверка на некорректный адрес почтового сервера " + address,
                () -> assertEquals(isShowTextWrongValue(MAIL_CONNECT_INPUT_EMAIL_SERVER),"Неверный адрес",
                        "Надпись 'Неверный адрес' не появилась"),
                () -> { clickButtonSave(); },
                () -> assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия")
        );
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        assertTrue(isShowFieldAndValue(
                SERVER_CONNECT_TITLE_FORM,
                MAIL_CONNECT_INPUT_EMAIL_SERVER,
                address,
                false),
                "Значение " + address + " отображается в поле " + MAIL_CONNECT_INPUT_EMAIL_SERVER + ":");
    }
}
