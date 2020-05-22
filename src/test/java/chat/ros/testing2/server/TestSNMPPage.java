package chat.ros.testing2.server;

import chat.ros.testing2.TestSuiteBase;
import chat.ros.testing2.server.settings.SNMPPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static chat.ros.testing2.data.SettingsData.SNMP_ADDRESS_SERVER;
import static chat.ros.testing2.data.SettingsData.SNMP_PORT_SERVER;
import static org.testng.Assert.assertEquals;

@Epic(value = "Настройки")
@Feature(value = "SNMP")
public class TestSNMPPage extends SNMPPage implements TestSuiteBase {

    @BeforeClass
    public void beforeAll(){
        testBase.openMS("Настройки","SNMP");
    }

    @Story(value = "Настраиваем SNMP сервер")
    @Description(value = "Переходим в раздел Настройки -> SNMP и прописываем парамететры для настройки SNMP сервера")
    @Test
    void test_SNMP_server(){
       assertEquals(getSettingsSNMPPage(SNMP_ADDRESS_SERVER, SNMP_PORT_SERVER), SNMP_ADDRESS_SERVER + ":" +
                       "" + SNMP_PORT_SERVER, "Сервер SNMP не настроен");
    }

}
