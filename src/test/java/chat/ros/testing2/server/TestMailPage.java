package chat.ros.testing2.server;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.MailPage;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Почта")
public class TestMailPage extends MailPage {

    private Map<String, String> getSettingsMailServer;

    @Story(value = "Проверяем настройки почты без защищённого соединения")
    @Description(value = "Вводим параметры почтового сервера infotek с незащищённым соединением и проверяем настройки")
    @Test
    void test_Settings_Mail_No_Security(){
        this.getSettingsMailServer = getSettingsMailServer(MAIL_INFOTEK_SERVER, MAIL_INFOTEK_USERNAME,
                MAIL_INFOTEK_PASSWORD, MAIL_PORT_NO_SECURITY, MAIL_INFOTEK_FROM_USER, MAIL_INFOTEK_FROM_MAIL);
        settingsMailServerWithCheck(this.getSettingsMailServer, MAIL_TYPE_SECURITY_NO);
        assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
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

    @Story(value = "Проверяем настройки почты с методом защиты соединения SSL ")
    @Description(value = "Вводим параметры почтового сервера google с защищённым соединением SSL и проверяем настройки")
    @Test
    void test_Settings_Mail_SSL_Security(){
        this.getSettingsMailServer = getSettingsMailServer(MAIL_GOOGLE_SERVER, MAIL_GOOGLE_USERNAME,
                MAIL_GOOGLE_PASSWORD, MAIL_PORT_SSL, MAIL_GOOGLE_FROM_USER, MAIL_GOOGLE_FROM_MAIL);
        settingsMailServerWithCheck(this.getSettingsMailServer, MAIL_TYPE_SECURITY_SSL);
        assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        assertAll("Проверяем, появилась ли форма проверок настроек и корректны ли настройки",
                () -> assertTrue(isShowIconModalWindow(".success--text"),
                        "Нет иконки успещной проверки почты"),
                () -> assertEquals(getTextModalWindow("h3"),
                        "Проверка настроек",
                        "Заголовок модального окна не совпадает с ожидаемым"),
                () -> assertEquals(getTextModalWindow("h4"),
                        "Настройки почты корректны.",
                        "Текст модального окна не совпадает с ожидаемым")
        );
        clickButtonCloseCheckSettingsForm();
    }

    @Story(value = "Проверяем настройки почты с методом защиты соединения STARTTLS ")
    @Description(value = "Вводим параметры почтового сервера google с защищённым соединением STARTTLS и проверяем настройки")
    @Test
    void test_Settings_Mail_STARTTLS_Security(){
        this.getSettingsMailServer = getSettingsMailServer(MAIL_GOOGLE_SERVER, MAIL_GOOGLE_USERNAME,
                MAIL_GOOGLE_PASSWORD, MAIL_PORT_STARTTLS, MAIL_GOOGLE_FROM_USER, MAIL_GOOGLE_FROM_MAIL);
        settingsMailServerWithCheck(this.getSettingsMailServer, MAIL_TYPE_SECURITY_STARTTLS);
        assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        assertAll("Проверяем, появилась ли форма проверок настроек и корректны ли настройки",
                () -> assertTrue(isShowIconModalWindow(".success--text"),
                        "Нет иконки успещной проверки почты"),
                () -> assertEquals(getTextModalWindow("h3"),
                        "Проверка настроек",
                        "Заголовок модального окна не совпадает с ожидаемым"),
                () -> assertEquals(getTextModalWindow("h4"),
                        "Настройки почты корректны.",
                        "Текст модального окна не совпадает с ожидаемым")
        );
        clickButtonCloseCheckSettingsForm();
    }

    @Story(value = "Перезагрузка страницы")
    @Description(value = "Переходим на страницу Почта, перезагружаем страницу и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Refresh_Page(){
        Selenide.refresh();
        sleep(3000);
        assertTrue(isNotShowLoaderSettings(), "Настройки не загрузились, надпись" +
                " 'Идет загрузка настроек...' не пропала");
    }

    @Story(value = "Переходим на страницу через адресную строку")
    @Description(value = "После авторизации вставляем в адресную строку страницу Почта и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Open_Page(){
        sleep(3000);
        assertTrue(isNotShowLoaderSettings(), "Настройки не загрузились, надпись" +
                " 'Идет загрузка настроек...' не пропала");
    }

}
