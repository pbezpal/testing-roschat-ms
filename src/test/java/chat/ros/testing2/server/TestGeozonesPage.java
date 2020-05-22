package chat.ros.testing2.server;

import chat.ros.testing2.TestSuiteBase;
import chat.ros.testing2.server.settings.GeozonesPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static org.testng.Assert.assertTrue;

@Epic(value = "Настройки")
@Feature(value = "Геозоны")
public class TestGeozonesPage extends GeozonesPage implements TestSuiteBase {

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

    @BeforeClass
    public void beforeAll(){
        testBase.openMS("Настройки","Геозоны");
    }

    @Story(value = "Добавляем геозону")
    @Description(value = "Переходим в раздел Геозоны, добавляем геозону и проверяем," +
            " что геозона была успешно добавлена на сервер")
    @Test
    void test_Add_Geozone(){
        assertTrue(addGeozone(mapInputValueGeozone, GEOZONES_NAME_ZONA), "Геозона " +
                "" + GEOZONES_NAME_ZONA + " не была добавлена на сервер");
    }

    @Story(value = "Добавляем Beacon")
    @Description(value = "Переходим в раздел Геозоны, открываем Геозону, добавляем Beacon и проверяем," +
            " что Beacon был успешно добавлен на сервер")
    @Test(dependsOnMethods = {"test_Add_Geozone"})
    void test_Add_Beacon(){
        assertTrue(addBeacon(GEOZONES_NAME_ZONA, mapInputValueBeacon, GEOZONES_BEACONE_INDICATOR), "Beacon " +
                "" + GEOZONES_BEACONE_INDICATOR + " не был добавлен на сервер");
    }
}
