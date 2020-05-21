package chat.ros.testing2.server;

import chat.ros.testing2.TestSuiteBase;
import chat.ros.testing2.server.settings.MailPage;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;

@Epic(value = "Настройки")
@Feature(value = "Почта")
public class TestMailPage extends MailPage implements TestSuiteBase {

    private Map<String, String> getSettingsMailServer;
    private SoftAssert softAssert;

    @Story(value = "Проверяем настройки почты без защищённого соединения")
    @Description(value = "Вводим параметры почтового сервера infotek с незащищённым соединением и проверяем настройки")
    @Test
    void test_Settings_Mail_No_Security(){
        softAssert = new SoftAssert();
        this.getSettingsMailServer = getSettingsMailServer(MAIL_INFOTEK_SERVER, MAIL_INFOTEK_USERNAME,
                MAIL_INFOTEK_PASSWORD, MAIL_PORT_NO_SECURITY, MAIL_INFOTEK_FROM_USER, MAIL_INFOTEK_FROM_MAIL);
        settingsMailServer(this.getSettingsMailServer, MAIL_TYPE_SECURITY_NO);
        softAssert.assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        softAssert.assertTrue(isCheckSettings(), "Настройки сервера некорректны");
        clickButtonCloseCheckSettingsForm();
        softAssert.assertAll();
    }

    @Story(value = "Проверяем настройки почты с методом защиты соединения SSL ")
    @Description(value = "Вводим параметры почтового сервера google с защищённым соединением SSL и проверяем настройки")
    @Test
    void test_Settings_Mail_SSL_Security(){
        softAssert = new SoftAssert();
        this.getSettingsMailServer = getSettingsMailServer(MAIL_GOOGLE_SERVER, MAIL_GOOGLE_USERNAME,
                MAIL_GOOGLE_PASSWORD, MAIL_PORT_SSL, MAIL_GOOGLE_FROM_USER, MAIL_GOOGLE_FROM_MAIL);
        settingsMailServer(this.getSettingsMailServer, MAIL_TYPE_SECURITY_SSL);
        softAssert.assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        softAssert.assertTrue(isCheckSettings(), "Настройки сервера некорректны");
        clickButtonCloseCheckSettingsForm();
        softAssert.assertAll();
    }

    @Story(value = "Проверяем настройки почты с методом защиты соединения STARTTLS ")
    @Description(value = "Вводим параметры почтового сервера google с защищённым соединением STARTTLS и проверяем настройки")
    @Test
    void test_Settings_Mail_STARTTLS_Security(){
        softAssert = new SoftAssert();
        this.getSettingsMailServer = getSettingsMailServer(MAIL_GOOGLE_SERVER, MAIL_GOOGLE_USERNAME,
                MAIL_GOOGLE_PASSWORD, MAIL_PORT_STARTTLS, MAIL_GOOGLE_FROM_USER, MAIL_GOOGLE_FROM_MAIL);
        settingsMailServer(this.getSettingsMailServer, MAIL_TYPE_SECURITY_STARTTLS);
        softAssert.assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        softAssert.assertTrue(isCheckSettings(), "Настройки сервера некорректны");
        clickButtonCloseCheckSettingsForm();
        softAssert.assertAll();
    }

}
