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
@Feature(value = "Пользователи")
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
    @Description(value = "1. Авторизуемся на СУ под пользователем admin \n" +
            "2. Добавляем пользователя AdminSecurity с правами Администратор Безопасновти\n" +
            "3. Проверяем, что пользователь AdminSecurity был добавлен и отображается в таблице пользователей")
    @Test
    @Order(1)
    void test_Add_New_User_AS(){
        addUser(mapInputValueUser).selectRoleUser(0).clickButtonSave();
        assertTrue(isExistsTableText(USER_LOGIN_AS, true), "Пользователь " + USER_LOGIN_AS + " не был добавлен в систему");
        status_add = true;
    }

    @Story(value = "Входим в систему под новым пользователем с правами Администратор Безопасности")
    @Description(value = "1. Выходим из системы усправления \n" +
            "2. Авторизуемся в СУ под пользователем AdminSecurity \n" +
            "3. Проверяем, что пользователь AdminSecurity успешно авторизовался в СУ и отображается " +
            "таблица с пользователями СУ")
    @Test
    @Order(2)
    void test_Login_New_User_AS(){
        assertTrue(status_add, "Пользователь не создан");
        loginOnServer(USER_LOGIN_AS, USER_PASSWORD_AS);
        assertTrue(isLoginUser(USER_LOGIN_AS), "Не удалось авторизоваться под пользователем " + USER_LOGIN_AS);
    }

    @Story(value = "Удаляем нового пользователя с правами Администратор Безопасности")
    @Description(value = "1. Авторизуемся на СУ под пользователем admin \n" +
            "2. Удаляем пользователя AdminSecurity с правами Администратор \n" +
            "3. Проверяем, что пользователь AdminSecurity был удалён и не отображается в таблице пользователей")
    @Test
    @Order(3)
    void test_Delete_New_User_AS(){
        assertTrue(status_add, "Пользователь не создан");
        loginOnServer(LOGIN_AS_MS, PASSWORD_AS_MS);
        open("/settings/users");
        assertTrue(isDeleteUser(USER_LOGIN_AS), "Не удалось удалить пользователя " + USER_LOGIN_AS);
    }
}
