package chat.ros.testing2.parameters.serverpage;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static chat.ros.testing2.data.SettingsData.SERVER_CONNECT_INPUT_HTTPS_PORT;

@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Сервер")
public class TestParametersServerConnectWrongHttpsPort extends ServerParams {

    @Story(value = "Проверяем максимальный значение порта HTTPS в настройках Подключение")
    @Description(value = "Вводим в поле HTTPS порт значение 65536 и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @Test
    void test_Max_Length_Port_Http_Connect(){
        wrong_symbols_ports(SERVER_CONNECT_INPUT_HTTPS_PORT, "65536");
    }

    @Story(value = "Проверяем невалидные значения порта HTTPS в настройках Подключение")
    @Description(value = "Вводим невалидыне значения в поле порта HTTPS и проверяем: \n" +
            "1. Появилась ли красная надпись 'Невалидный порт' \n" +
            "2. Пропадает ли форма для редактирования настроек Подклюяения после нажатия кнопки Сохранить \n" +
            "3. Сохраняются ли настройки с невалидным значением в поле")
    @ParameterizedTest
    @MethodSource(value = "getWrongValueConnectPort")
    void test_Wrong_Symbols_Port_Http_Connect(Character c){
        wrong_symbols_ports(SERVER_CONNECT_INPUT_HTTPS_PORT, c.toString());
    }
}
