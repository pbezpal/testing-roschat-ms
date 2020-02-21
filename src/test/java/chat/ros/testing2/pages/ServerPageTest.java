package chat.ros.testing2.pages;

import chat.ros.testing2.RecourcesTests;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static chat.ros.testing2.helpers.ScreenshotTests.AScreenshot;
import static com.codeborne.selenide.Selenide.open;

@Epic(value = "Настройки")
@Feature(value = "Настройки->Сервер")
class ServerPageTest extends ServerPage{

    private String filename;
    private static String serverPage = "/settings/web-server";

    @BeforeAll
    static void loginServer(){
        RecourcesTests.beforeAllTests();
        open(serverPage);
    }

    @AfterEach
    void tearDown(){
        AScreenshot(filename);
    }

    @Story(value = "Настраиваем внешний адрес сервера")
    @Description(value = "Настраиваем внешний адрес сервера в разделе Подключение")
    @Test
    void test_Settings_Public_Network(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        setPublicNetwork();
    }

    /*@Story(value = "Настраиваем сертификат SSL")
    @Description(value = "Загружаем актуальные файлы для сертификата SSL")
    @Disabled
    @Test
    void test_Settings_Certificate(){ setCertificate(); }*/

    @Story(value = "Настраиваем Push сервер")
    @Description(value = "Настраиваем Push сервер в разделе Лицензирование и обсуживание")
    @Test
    void test_Settings_Push_Server(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        setPushService();
    }
}