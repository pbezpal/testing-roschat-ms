package chat.ros.testing2.server.mailpage;

import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.ResourcesServerPage;
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
@ExtendWith(ResourcesServerPage.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Почта")
public class TestMailPage extends TMailPage {

    @Story(value = "Проверяем настройки почты без защищённого соединения")
    @Description(value = "Вводим параметры почтового сервера infotek с незащищённым соединением и проверяем настройки")
    @Test
    void test_Settings_Mail_No_Security(){
        settingsMail(getSettingsMailServer(MAIL_INFOTEK_SERVER,
                MAIL_INFOTEK_USERNAME,
                MAIL_INFOTEK_PASSWORD,
                MAIL_PORT_NO_SECURITY,
                MAIL_INFOTEK_FROM_USER,
                MAIL_INFOTEK_FROM_MAIL),
                MAIL_TYPE_SECURITY_NO);
    }

    @Story(value = "Проверяем настройки почты с методом защиты соединения SSL ")
    @Description(value = "Вводим параметры почтового сервера google с защищённым соединением SSL и проверяем настройки")
    @Test
    void test_Settings_Mail_SSL_Security(){
        settingsMail(getSettingsMailServer(MAIL_GOOGLE_SMTP_SERVER,
                MAIL_GOOGLE_USERNAME,
                MAIL_GOOGLE_PASSWORD,
                MAIL_PORT_SSL,
                MAIL_GOOGLE_FROM_USER,
                MAIL_GOOGLE_FROM_MAIL),
                MAIL_TYPE_SECURITY_SSL);
    }

    @Story(value = "Проверяем настройки почты с методом защиты соединения STARTTLS ")
    @Description(value = "Вводим параметры почтового сервера google с защищённым соединением STARTTLS и проверяем настройки")
    @Test
    void test_Settings_Mail_STARTTLS_Security(){
        settingsMail(getSettingsMailServer(MAIL_GOOGLE_SMTP_SERVER,
                MAIL_GOOGLE_USERNAME,
                MAIL_GOOGLE_PASSWORD,
                MAIL_PORT_STARTTLS,
                MAIL_GOOGLE_FROM_USER,
                MAIL_GOOGLE_FROM_MAIL),
                MAIL_TYPE_SECURITY_STARTTLS);
    }

    @Story(value = "Перезагрузка страницы")
    @Description(value = "Переходим на страницу Почта, перезагружаем страницу и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Refresh_Page(){
        Selenide.refresh();
        sleep(3000);
        isNotShowLoaderSettings();
    }

    @Story(value = "Переходим на страницу через адресную строку")
    @Description(value = "После авторизации вставляем в адресную строку страницу Почта и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Open_Page(){
        sleep(3000);
        isNotShowLoaderSettings();
    }

}
