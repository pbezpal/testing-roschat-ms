package chat.ros.testing2.pages;

import chat.ros.testing2.RecourcesTests;
import chat.ros.testing2.WatcherTests;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Epic(value = "Настройки")
@Feature(value = "Настройки->Телефония")
@ExtendWith(RecourcesTests.class)
@ExtendWith(WatcherTests.class)
public class TelephonyPageTest extends TelephonyPage {

    @Story(value = "Настройка сети")
    @Description(value = "Настраиваем сеть для телефонии и проверяем корректность натсроек")
    @Test
    void test_Settings_Network(){
        setNetwork();
    }

    @Story(value = "Настройка SIP-сервера")
    @Description(value = "Настраиваем SIP-сервер для телефонии")
    @Test
    void test_Settings_SIP_Server(){
        setSipServer();
    }

    @Story(value = "Настройка TURN/STUN")
    @Description(value = "Настраиваем turnserver для телефонии")
    @Test
    void test_Settings_Turnserver(){
        setTurnserver();
    }
}
