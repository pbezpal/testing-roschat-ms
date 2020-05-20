package chat.ros.testing2.server;

import chat.ros.testing2.JUnitRecourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.SNMPPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.SettingsData.SNMP_ADDRESS_SERVER;
import static chat.ros.testing2.data.SettingsData.SNMP_PORT_SERVER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic(value = "Настройки")
@Feature(value = "SNMP")
@ExtendWith(JUnitRecourcesTests.class)
@ExtendWith(WatcherTests.class)
public class TestSNMPPage extends SNMPPage {

    @Story(value = "Настраиваем SNMP сервер")
    @Description(value = "Переходим в раздел Настройки -> SNMP и прописываем парамететры для настройки SNMP сервера")
    @Test
    void test_SNMP_server(){
       assertEquals(getSettingsSNMPPage(SNMP_ADDRESS_SERVER, SNMP_PORT_SERVER), SNMP_ADDRESS_SERVER + ":" +
                       "" + SNMP_PORT_SERVER, "Сервер SNMP не настроен");
    }

}
