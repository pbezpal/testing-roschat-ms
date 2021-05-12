package chat.ros.testing2.parameters.telephony;

import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.parameters.ResourcesParameters;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static chat.ros.testing2.data.SettingsData.TELEPHONY_NETWORK_INPUT_FRONT_DEV;

@ExtendWith(ResourcesParameters.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Телефония")
public class TestParametersTelephonyNetworkWrongFrontDev extends TelephonyParams {

    @Story(value = "Проверяем поле Внешний интерфейс в настройках Сети на невалидный IP")
    @Description(value = "Вводим в поле Внешний интерфейс и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный IP адрес' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидными значениями")
    @ParameterizedTest(name = "#{index} => ip=''{0}''")
    @MethodSource(value = "getWrongValueIPNetwork")
    void test_Settings_Network_Wrong_Front_Dev(String ip){
        wrong_ip_network(TELEPHONY_NETWORK_INPUT_FRONT_DEV, ip);
    }
}
