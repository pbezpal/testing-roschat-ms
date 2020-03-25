package chat.ros.testing2.server;

import chat.ros.testing2.RecourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.ServerPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Epic(value = "Настройки")
@Feature(value = "Сервер")
@ExtendWith(RecourcesTests.class)
@ExtendWith(WatcherTests.class)
class MS_ServerPageTest extends ServerPage {

    @Story(value = "Настраиваем внешний адрес сервера")
    @Description(value = "Настраиваем внешний адрес сервера в разделе Подключение")
    @Test
    void test_Settings_Public_Network(){
        setPublicNetwork();
    }

    /*@Story(value = "Настраиваем сертификат SSL")
    @Description(value = "Загружаем актуальные файлы для сертификата SSL")
    @Disabled
    @Test
    void test_Settings_Certificate(){ setCertificate(); }*/

    @Story(value = "Настраиваем Push сервер")
    @Description(value = "Настраиваем Push сервер в разделе Лицензирование и обсуживание")
    @Test
    void test_Settings_Push_Server(){
        setPushService();
    }
}