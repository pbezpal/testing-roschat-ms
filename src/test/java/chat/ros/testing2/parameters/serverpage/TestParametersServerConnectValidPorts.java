package chat.ros.testing2.parameters.serverpage;

import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.parameters.ResourcesParameters;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@ExtendWith(ResourcesParameters.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Сервер")
public class TestParametersServerConnectValidPorts extends ServerParams {

    @Story(value = "Проверяем валидные значения портов в настройках Подключение")
    @Description(value = "Вводим валидные значения портов в настройках Подключение и проверяем: \n" +
            "Сохраняются ли настройки с валидным значением портов")
    @ParameterizedTest
    @MethodSource(value = "getValidValueConnectPorts")
    void test_Valid_Ports_Connect(String field, Character c){
        valid_symbols_port(field, c.toString());
    }
}
