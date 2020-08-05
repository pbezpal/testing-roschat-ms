package chat.ros.testing2.parameters.telephony;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.TelephonyPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.ParametersData.WRONG_VALUE_HOST;
import static chat.ros.testing2.data.ParametersData.WRONG_VALUE_IP_ADDRESS;
import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Телефония")
public class TestParametersTelephonyNetworkWrongPublicAddress extends TelephonyParams {

    @Story(value = "Проверяем поле Публичный адрес в настройках Сети на невалидный IP")
    @Description(value = "Вводим в поле Публичный адрес и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный IP адрес' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидными значениями")
    @ParameterizedTest
    @MethodSource(value = "getWrongValueIPNetwork")
    void test_Settings_Network_Wrong_Public_Address(String ip){
        wrong_ip_network(TELEPHONY_NETWORK_INPUT_PUBLIC_ADDRESS, ip);
    }
}
