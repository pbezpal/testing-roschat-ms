package chat.ros.testing2.parameters.telephony;

import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.parameters.ResourcesParameters;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static chat.ros.testing2.data.SettingsData.*;

@ExtendWith(ResourcesParameters.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Телефония")
public class TestParametersTelephonySipWrongMinPort extends TelephonyParams {

    @Story(value = "Проверяем невалидные значения в поле Минимальный порт SIP-сервер")
    @Description(value = "Вводим в поле Минимальный порт невалидные значения и проверяем: \n" +
            "1. Появилась ли красная надпись 'Значение должно быть целым числом' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле Минимальный порт")
    @ParameterizedTest
    @MethodSource(value = "getWrongValuePort")
    void test_Settings_SIP_Wrong_Min_Port(Character symbol){
        wrong_sip_ports(TELEPHONY_SIP_INPUT_MIN_PORT, symbol.toString(),"Невалидный порт");
    }

    @Story(value = "Проверяем максимальное значения порта в поле Минимальный порт SIP-сервер")
    @Description(value = "Вводим в поле Минимальный порт значение 65536 и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле Минимальный порт")
    @Test
    void test_Settings_SIP_Max_Length_Value_Min_Port(){
        wrong_sip_ports(TELEPHONY_SIP_INPUT_MIN_PORT, "65536", "Невалидный порт");
    }
}
