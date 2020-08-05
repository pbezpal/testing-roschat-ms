package chat.ros.testing2.parameters.mailpage;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.MailPage;
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
@Feature(value = "Почта")
public class TestParametersMailMaxLengthPort extends MailPage {

    private Map mapValueMail = null;

    @Story(value = "Проверяем максимальный порт в настройках Почты")
    @Description(value = "Вводим в поле настройки порта формы Почта порт 65536 и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test
    void test_Max_Length_Port_Email(){
        this.mapValueMail = getSettingsMailServer(MAIL_INFOTEK_SERVER, MAIL_INFOTEK_USERNAME,
                MAIL_INFOTEK_PASSWORD, "65536", MAIL_INFOTEK_FROM_USER, MAIL_INFOTEK_FROM_MAIL);
        settingsMailServerWithoutSave(this.mapValueMail, MAIL_TYPE_SECURITY_NO);
        assertAll("Проверяем значение порта на максимально возможное значение",
                () -> assertEquals(isShowTextWrongValue(MAIL_CONNECT_INPUT_EMAIL_PORT),"Невалидный порт",
                        "Надпись 'Невалидный порт' не появилась"),
                () -> { clickButtonSave(); },
                () -> assertTrue(isFormChange(),
                "Формы редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия")
        );
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        assertTrue(isShowSymbolsInField(
                SERVER_CONNECT_TITLE_FORM,
                MAIL_CONNECT_INPUT_EMAIL_PORT,
                MORE_MAX_VALUE_PORT,
                false),
                "Значение " + MORE_MAX_VALUE_PORT + " отображается в поле " + MAIL_CONNECT_INPUT_EMAIL_PORT);
    }
}
