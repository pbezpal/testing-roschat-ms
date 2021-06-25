package chat.ros.testing2.parameters.mailpage;

import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.parameters.ResourcesParameters;
import chat.ros.testing2.server.settings.MailPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static chat.ros.testing2.data.ParametersData.*;
import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ResourcesParameters.class)
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
        assertEquals(isShowTextWrongValue(MAIL_CONNECT_INPUT_EMAIL_PORT),"Невалидный порт",
                "Надпись 'Невалидный порт' не появилась");
        clickButtonSave()
                .isFormChange()
                .clickButtonClose();
        assertTrue(isShowFieldAndValue(
                SERVER_CONNECT_TITLE_FORM,
                MAIL_CONNECT_INPUT_EMAIL_PORT,
                MORE_MAX_VALUE_PORT,
                false),
                "Значение " + MORE_MAX_VALUE_PORT + " отображается в поле " + MAIL_CONNECT_INPUT_EMAIL_PORT);
    }
}
