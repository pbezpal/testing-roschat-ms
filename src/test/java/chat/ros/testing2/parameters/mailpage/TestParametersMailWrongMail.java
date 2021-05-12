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
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Map;

import static chat.ros.testing2.data.ParametersData.*;
import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ResourcesParameters.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Почта")
public class TestParametersMailWrongMail extends MailPage {

    private Map mapValueMail = null;

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> getWrongValueMail() {
        ArrayList<String> data = new ArrayList<>();

        for (String mail : WRONG_VALUE_EMAIL) {
            data.add(mail);
        }

        return data;
    }

    @Story(value = "Проверяем невалидные значение почты в поле 'Почтовый адрес' в настройках Почты")
    @Description(value = "Вводим невалидные значения в поле 'Почтовый адрес' в настройках Почты и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный адрес' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с пустым полем")
    @ParameterizedTest
    @MethodSource(value = "getWrongValueMail")
    void test_Wrong_Email(String mail){
        this.mapValueMail = getSettingsMailServer(MAIL_INFOTEK_SERVER, MAIL_INFOTEK_USERNAME,
                MAIL_INFOTEK_PASSWORD, MAIL_PORT_NO_SECURITY, MAIL_INFOTEK_FROM_USER, mail);
        settingsMailServerWithoutSave(this.mapValueMail, MAIL_TYPE_SECURITY_NO);
        assertAll("Проверяем на невалидное значение почты " + mail,
                () -> assertEquals(isShowTextWrongValue(MAIL_CONTACT_INPUT_FROM_MAIL),"Неверный адрес",
                        "Надпись 'Неверный адрес' не появилась"),
                () -> { clickButtonSave(); },
                () -> assertTrue(isFormChange(),
                "Форма редактирования настроек закрылась после нажатия кнопки Сохранить"),
                () -> assertTrue(isFormConfirmActions(false), "Появилась форма, Подтвердите свои действия")
        );
        if(isFormConfirmActions(true)) clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        else if(isFormChange()) clickButtonClose();
        assertTrue(isShowValueInField(
                SERVER_CONNECT_TITLE_FORM,
                MAIL_CONTACT_INPUT_FROM_MAIL,
                mail,
                false),
                "Значение " + mail + " отображается в поле " + MAIL_CONTACT_INPUT_FROM_MAIL + ":");
    }
}
