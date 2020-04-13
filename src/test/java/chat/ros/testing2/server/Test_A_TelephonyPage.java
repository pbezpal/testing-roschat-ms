package chat.ros.testing2.server;

import chat.ros.testing2.RecourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.TelephonyPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Epic(value = "Настройки")
@Feature(value = "Телефония")
@ExtendWith(RecourcesTests.class)
@ExtendWith(WatcherTests.class)
public class Test_A_TelephonyPage extends TelephonyPage {

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
