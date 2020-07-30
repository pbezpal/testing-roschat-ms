package chat.ros.testing2.server;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.SNMPPage;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.SettingsData.SNMP_ADDRESS_SERVER;
import static chat.ros.testing2.data.SettingsData.SNMP_PORT_SERVER;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "SNMP")
public class TestSNMPPage extends SNMPPage {

    @Story(value = "Настраиваем SNMP сервер")
    @Description(value = "Переходим в раздел Настройки -> SNMP и прописываем парамететры для настройки SNMP сервера")
    @Test
    void test_SNMP_server(){
       assertEquals(getSettingsSNMPPage(SNMP_ADDRESS_SERVER, SNMP_PORT_SERVER), SNMP_ADDRESS_SERVER + ":" +
                       "" + SNMP_PORT_SERVER, "Сервер SNMP не настроен");
    }

    @Story(value = "Перезагрузка страницы")
    @Description(value = "Переходим на страницу SNMP, перезагружаем страницу и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Refresh_Page(){
        Selenide.refresh();
        sleep(3000);
        assertTrue(isNotShowLoaderSettings(), "Настройки не загрузились, надпись" +
                " 'Идет загрузка настроек...' не пропала");
    }

    @Story(value = "Переходим на страницу через адресную строку")
    @Description(value = "После авторизации вставляем в адресную строку страницу SNMP и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Open_Page(){
        sleep(3000);
        assertTrue(isNotShowLoaderSettings(), "Настройки не загрузились, надпись" +
                " 'Идет загрузка настроек...' не пропала");
    }

}
