package chat.ros.testing2.server.users;

import chat.ros.testing2.ResourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.settings.UserPage;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ResourcesTests.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Настройки СУ")
public class TestUserAdminPage extends UserPage {

    private static boolean status_add;
    private Map<String, String> mapInputValueUser = new HashMap() {{
        put("Фамилия", USER_FIRST_NAME_ADMIN_TEST);
        put("Имя", USER_LAST_NAME_ADMIN_TEST);
        put("Отчество", USER_PATRON_NAME_ADMIN_TEST);
        put("Логин", USER_LOGIN_ADMIN_TEST);
        put("Пароль", USER_PASSWORD_ADMIN_TEST);
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
        addUser(mapInputValueUser).selectRoleUser(1).clickButtonSave();
        assertTrue(isExistsTableText(USER_LOGIN_ADMIN_TEST, true), "Пользователь " + USER_LOGIN_ADMIN_TEST + " не был добавлен в систему");
        status_add = true;
    }

    @Story(value = "Входим в систему под новым пользователем")
    @Description(value = "Выходим из системы усправления и авторизуемся под новым пользователем")
    @Test
    @Order(2)
    void test_Login_New_User(){
        assertTrue(status_add, "Пользователь не создан");
        loginOnServer(USER_LOGIN_ADMIN_TEST, USER_PASSWORD_ADMIN_TEST);
        assertTrue(isLoginUser(USER_LOGIN_ADMIN_TEST), "Не удалось авторизоваться под пользователем " + USER_LOGIN_ADMIN_TEST);
    }

    @Story(value = "Удаляем нового пользователя")
    @Description(value = "Переходим в раздел Настройки -> Настройки СУ и удаляем нового пользователя СУ")
    @Test
    @Order(3)
    void test_Delete_New_User(){
        assertTrue(status_add, "Пользователь не создан");
        loginOnServer(LOGIN_AS_MS, PASSWORD_AS_MS);
        open("/settings/users");
        assertTrue(isDeleteUser(USER_LOGIN_ADMIN_TEST), "Не удалось удалить пользователя " + USER_LOGIN_ADMIN_TEST);
    }
}