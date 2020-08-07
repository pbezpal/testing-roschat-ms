package chat.ros.testing2.server.users;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.UserPage;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.LoginData.LOGIN_AS_MS;
import static chat.ros.testing2.data.LoginData.PASSWORD_AS_MS;
import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Настройки СУ")
public class TestUserASPage extends UserPage {

    private static boolean status_add;
    private Map<String, String> mapInputValueUser = new HashMap() {{
        put("Фамилия", USER_FIRST_NAME_AS);
        put("Имя", USER_LAST_NAME_AS);
        put("Отчество", USER_PATRON_NAME_AS);
        put("Логин", USER_LOGIN_AS);
        put("Пароль", USER_PASSWORD_AS);
    }};

    @BeforeAll
    static void setUp(){
        status_add = false;
    }

    @Story(value = "Добавляем пользователя с правами Администратор Безопасности")
    @Description(value = "Авторизовываемся на СУ и добавляем нового пользователя с правами Администратор Безопасности")
    @Test
    @Order(1)
    void test_Add_New_User_AS(){
        addUser(mapInputValueUser).selectRoleUser(0).clickButtonSave();
        assertTrue(isExistsTableText(USER_LOGIN_AS, true), "Пользователь " + USER_LOGIN_AS + " не был добавлен в систему");
        status_add = true;
    }

    @Story(value = "Входим в систему под новым пользователем")
    @Description(value = "Выходим из системы усправления и авторизуемся под новым пользователем")
    @Test
    @Order(2)
    void test_Login_New_User(){
        assertTrue(status_add, "Пользователь не создан");
        loginOnServer(USER_LOGIN_AS, USER_PASSWORD_AS);
        assertTrue(isLoginNewUser(USER_LOGIN_AS), "Не удалось авторизоваться под пользователем " + USER_LOGIN_AS);
    }

    @Story(value = "Удаляем нового пользователя")
    @Description(value = "Переходим в раздел Настройки -> Настройки СУ и удаляем нового пользователя СУ")
    @Test
    @Order(3)
    void test_Delete_New_User(){
        assertTrue(status_add, "Пользователь не создан");
        loginOnServer(LOGIN_AS_MS, PASSWORD_AS_MS);
        open("/settings/users");
        assertTrue(isDeleteUser(USER_LOGIN_AS), "Не удалось удалить пользователя " + USER_LOGIN_AS);
    }

    @Story(value = "Перезагрузка страницы")
    @Description(value = "Переходим на страницу Телефония, перезагружаем страницу и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Refresh_Page(){
        Selenide.refresh();
        sleep(3000);
        assertTrue(isNotShowLoaderSettings(), "Настройки не загрузились, надпись" +
                " 'Идет загрузка настроек...' не пропала");
    }

    @Story(value = "Переходим на страницу через адресную строку")
    @Description(value = "После авторизации вставляем в адресную строку страницу Телефония и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Open_Page(){
        sleep(3000);
        assertTrue(isNotShowLoaderSettings(), "Настройки не загрузились, надпись" +
                " 'Идет загрузка настроек...' не пропала");
    }
}
