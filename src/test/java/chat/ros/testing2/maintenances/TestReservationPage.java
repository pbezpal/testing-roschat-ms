package chat.ros.testing2.maintenances;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.maintenances.ReservationPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Epic(value = "Обслуживание")
@Feature(value = "Резервирование")
@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
public class TestReservationPage extends ReservationPage {

    private String configFile = getClass().getClassLoader().getResource("config/backup.tar.gz").getFile();

    @Story(value = "Восстановление настроек")
    @Description(value = "1. Восстанавливаем настройки сервера \n" +
            "2. Проверяем, что настройки успешно восстановились")
    @Test
    void test_Recovery_Config(){
        uploadConfigs(configFile);
        clickButtonSave();
    }
}
