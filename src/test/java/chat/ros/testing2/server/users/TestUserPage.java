package chat.ros.testing2.server.users;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.UserPage;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.LoginData.LOGIN_AS_MS;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Пользователи")
public class TestUserPage extends UserPage {

    @Story(value = "Перезагрузка страницы")
    @Description(value = "1. Перезагружаем страницу \n" +
            "2. Проверяем, появилась ли надпись 'Идет загрузка настроек...' \n" +
            "3. Проверяем, загрузились ли данные пользователей после перезагрузки")
    @Test
    void test_Refresh_Page(){
        Selenide.refresh();
        sleep(3000);
        assertTrue(isNotShowLoaderSettings(), "Настройки не загрузились, надпись" +
                " 'Идет загрузка настроек...' не пропала");
        assertTrue(isExistsTableText(LOGIN_AS_MS, true),
                "Пользователь " + LOGIN_AS_MS + " не отображается после перезагрузки страницы");
    }

    @Story(value = "Переходим на страницу через адресную строку")
    @Description(value = "После авторизации вставляем в адресную строку страницу Телефония и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Open_Page(){
        sleep(3000);
        assertTrue(isNotShowLoaderSettings(), "Настройки не загрузились, надпись" +
                " 'Идет загрузка настроек...' не пропала");
        assertTrue(isExistsTableText(LOGIN_AS_MS, true),
                "Пользователь " + LOGIN_AS_MS + " не отображается после перезагрузки страницы");
    }

}
