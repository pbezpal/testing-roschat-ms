package chat.ros.testing2.parameters.telephony;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static chat.ros.testing2.data.SettingsData.TELEPHONY_NETWORK_INPUT_FRONT_DEV;
import static chat.ros.testing2.data.SettingsData.TELEPHONY_NETWORK_INPUT_INSIDE_DEV;

@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Телефония")
public class TestParametersTelephonyNetworkWrongInsideDev extends TelephonyParams {

    @Story(value = "Проверяем поле Внутренний интерфейс в настройках Сети на невалидный IP")
    @Description(value = "Вводим в поле Внутренний интерфейс и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный IP адрес' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидными значениями")
    @ParameterizedTest
    @MethodSource(value = "getWrongValueIPNetwork")
    void test_Settings_Network_Wrong_Inside_Dev(String ip){
        wrong_ip_network(TELEPHONY_NETWORK_INPUT_INSIDE_DEV, ip);
    }
}
