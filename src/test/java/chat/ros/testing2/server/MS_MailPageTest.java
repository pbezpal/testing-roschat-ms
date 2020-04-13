package chat.ros.testing2.server;

import chat.ros.testing2.RecourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.MailPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.SettingsData.*;

@Epic(value = "Настройки")
@Feature(value = "Почта")
@ExtendWith(RecourcesTests.class)
@ExtendWith(WatcherTests.class)
public class MS_MailPageTest extends MailPage {

    @Story(value = "Проверяем настройки почты без защищённого соединения")
    @Description(value = "Вводим параметры почтового сервера infotek с незащищённым соединением и проверяем настройки")
    @Test
    void test_Settings_Mail_No_Security(){
        checkSettingsMailServer(MAIL_INFOTEK_SERVER, MAIL_INFOTEK_USERNAME, MAIL_INFOTEK_PASSWORD, MAIL_PORT_NO_SECURITY, MAIL_TYPE_SECURITY_NO, MAIL_INFOTEK_FROM_USER, MAIL_INFOTEK_FROM_MAIL);
    }

    @Story(value = "Проверяем настройки почты с методом защиты соединения SSL ")
    @Description(value = "Вводим параметры почтового сервера google с защищённым соединением SSL и проверяем настройки")
    @Test
    void test_Settings_Mail_SSL_Security(){
        checkSettingsMailServer(MAIL_GOOGLE_SERVER, MAIL_GOOGLE_USERNAME, MAIL_GOOGLE_PASSWORD, MAIL_PORT_SSL, MAIL_TYPE_SECURITY_SSL, MAIL_GOOGLE_FROM_USER, MAIL_GOOGLE_FROM_MAIL);
    }

    @Story(value = "Проверяем настройки почты с методом защиты соединения STARTTLS ")
    @Description(value = "Вводим параметры почтового сервера google с защищённым соединением STARTTLS и проверяем настройки")
    @Test
    void test_Settings_Mail_STARTTLS_Security(){
        checkSettingsMailServer(MAIL_GOOGLE_SERVER, MAIL_GOOGLE_USERNAME, MAIL_GOOGLE_PASSWORD, MAIL_PORT_STARTTLS, MAIL_TYPE_SECURITY_STARTTLS, MAIL_GOOGLE_FROM_USER, MAIL_GOOGLE_FROM_MAIL);
    }

}
