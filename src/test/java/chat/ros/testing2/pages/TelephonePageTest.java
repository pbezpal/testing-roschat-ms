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
@Feature(value = "Настройки->Телефония")
public class TelephonePageTest extends TelephonyPage {

    private String filename;
    private static String telephonePage = "/settings/telephony";

    @BeforeAll
    static void loginServer(){
        RecourcesTests.beforeAllTests();
        open(telephonePage);
    }

    @AfterEach
    void tearDown(){
        AScreenshot(filename);
    }

    @Story(value = "Настройка сети")
    @Description(value = "Настраиваем сеть для телефонии и проверяем корректность натсроек")
    @Test
    void test_Settings_Network(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        setNetwork();
    }

    @Story(value = "Настройка SIP-сервера")
    @Description(value = "Настраиваем SIP-сервер для телефонии")
    @Test
    void test_Settings_SIP_Server(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        setSipServer();
    }

    @Story(value = "Настройка TURN/STUN")
    @Description(value = "Настраиваем turnserver для телефонии")
    @Test
    void test_Settings_Turnserver(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        setTurnserver();
    }
}
