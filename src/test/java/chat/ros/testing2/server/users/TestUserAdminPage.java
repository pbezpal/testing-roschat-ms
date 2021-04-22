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
@Feature(value = "Пользователи")
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

    @Story(value = "Добавляем пользователя с правами Администратор")
    @Description(value = "1. Авторизуемся на СУ под пользователем admin \n" +
            "2. Добавляем пользователя Admintest с правами Администратор \n" +
            "3. Проверяем, что пользователь Admintest был добавлен и отображается в таблице пользователей")
    @Test
    @Order(1)
    void test_Add_New_User_Admin(){
        addUser(mapInputValueUser).selectRoleUser(1).clickButtonSave();
        assertTrue(isExistsTableText(USER_LOGIN_ADMIN_TEST, true), "Пользователь " + USER_LOGIN_ADMIN_TEST + " не был добавлен в систему");
        status_add = true;
    }

    @Story(value = "Авторизуемся в систему под новым пользователем с правами Администратор")
    @Description(value = "1. Выходим из системы усправления \n" +
            "2. Авторизуемся в СУ под пользователем Admintest \n" +
            "3. Проверяем, что пользователь Admintest успешно авторизовался в СУ")
    @Test
    @Order(2)
    void test_Login_New_User_Admin(){
        assertTrue(status_add, "Пользователь не создан");
        loginOnServer(USER_LOGIN_ADMIN_TEST, USER_PASSWORD_ADMIN_TEST);
        assertTrue(isLoginUser(USER_LOGIN_ADMIN_TEST), "Не удалось авторизоваться под пользователем " + USER_LOGIN_ADMIN_TEST);
    }

    @Story(value = "Удаляем нового пользователя с правами Администратор")
    @Description(value = "1. Авторизуемся на СУ под пользователем admin \n" +
            "2. Удаляем пользователя Admintest с правами Администратор \n" +
            "3. Проверяем, что пользователь Admintest был удалён и не отображается в таблице пользователей")
    @Test
    @Order(3)
    void test_Delete_New_User_Admin(){
        assertTrue(status_add, "Пользователь не создан");
        loginOnServer(LOGIN_AS_MS, PASSWORD_AS_MS);
        open("/settings/users");
        assertTrue(isDeleteUser(USER_LOGIN_ADMIN_TEST), "Не удалось удалить пользователя " + USER_LOGIN_ADMIN_TEST);
    }
}
