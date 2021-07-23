package chat.ros.testing2.server.mailpage;

import chat.ros.testing2.server.settings.MailPage;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TMailPage extends MailPage implements ITMailPage {

    @Override
    public void settingsMail(Map<String, String> values, String typeSecurity) {
        settingsMailServerWithCheck(values, typeSecurity);
        isFormCheckSettings();
        assertAll("Проверяем, появилась ли форма проверок настроек и корректны ли настройки",
                () -> assertTrue(isShowIconModalWindow(".success--text"),
                        "Нет иконки успешной проверки почты"),
                () -> assertEquals(getTextModalWindow("h3"),
                        "Проверка настроек",
                        "Заголовок модального окна не совпадает с ожидаемым"),
                () -> assertEquals(getTextModalWindow("h4"),
                        "Настройки почты корректны.",
                        "Текст модального окна не совпадает с ожидаемым")
        );
        clickButtonCloseCheckSettingsForm();
    }

}
