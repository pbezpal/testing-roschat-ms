package chat.ros.testing2.server;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.TestSuiteBase;
import chat.ros.testing2.TestsBase;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.GeozonesPage;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.Assert.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Геозоны")
public class TestGeozonesPage extends GeozonesPage {

    private boolean status = false;
    private Map<String,String> mapInputValueGeozone = new HashMap() {{
        put("Название", GEOZONES_NAME_ZONA);
        put("Широта", GEOZONES_WIDTH_ZONA);
        put("Долгота", GEOZONES_LENGHT_ZONA);
        put("Радиус (0-1000м)", GEOZONES_RADIUS_ZONA);
    }};
    private Map<String,String> mapInputValueBeacon = new HashMap() {{
        put("Идентификатор", GEOZONES_BEACONE_INDICATOR);
        put("Minor", GEOZONES_BEACONE_MINOR);
        put("Major", GEOZONES_BEACONE_MAJOR);
    }};

    @Story(value = "Добавляем геозону")
    @Description(value = "Переходим в раздел Геозоны, добавляем геозону и проверяем," +
            " что геозона была успешно добавлена на сервер")
    @Test
    @Order(1)
    void test_Add_Geozone(){
        assertTrue(addGeozone(mapInputValueGeozone, GEOZONES_NAME_ZONA), "Геозона " +
                "" + GEOZONES_NAME_ZONA + " не была добавлена на сервер");
        status = true;
    }

    @Story(value = "Добавляем Beacon")
    @Description(value = "Переходим в раздел Геозоны, открываем Геозону, добавляем Beacon и проверяем," +
            " что Beacon был успешно добавлен на сервер")
    @Test
    @Order(2)
    void test_Add_Beacon(){
        assertTrue(status,"Не создана Геозона");
        assertTrue(addBeacon(GEOZONES_NAME_ZONA, mapInputValueBeacon, GEOZONES_BEACONE_INDICATOR), "Beacon " +
                "" + GEOZONES_BEACONE_INDICATOR + " не был добавлен на сервер");
    }

    @Story(value = "Перезагрузка страницы")
    @Description(value = "Переходим на страницу Геозоны, перезагружаем страницу и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Refresh_Page(){
        Selenide.refresh();
        sleep(5000);
        assertTrue(isNotShowLoaderSettings(), "Настройки не загрузились, надпись" +
                " 'Идет загрузка настроек...' не пропала");
    }

    @Story(value = "Переходим на страницу через адресную строку")
    @Description(value = "После авторизации вставляем в адресную строку страницу Геозоны и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Open_Page(){
        sleep(5000);
        assertTrue(isNotShowLoaderSettings(), "Настройки не загрузились, надпись" +
                " 'Идет загрузка настроек...' не пропала");
    }
}
