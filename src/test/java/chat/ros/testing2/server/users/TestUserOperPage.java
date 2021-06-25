package chat.ros.testing2.server.users;

import chat.ros.testing2.TestStatusResult;
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
@ExtendWith(ResourcesUserPage.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Пользователи")
public class TestUserOperPage extends UserPage {

    private static boolean status_add;
    private Map<String, String> mapInputValueUser = new HashMap() {{
        put("Фамилия", USER_FIRST_NAME_OPER);
        put("Имя", USER_LAST_NAME_OPER);
        put("Отчество", USER_PATRON_NAME_OPER);
        put("Логин", USER_LOGIN_OPER);
        put("Пароль", USER_PASSWORD_OPER);
    }};

    @BeforeAll
    static void setUp(){
        status_add = false;
    }

    @Story(value = "Добавляем пользователя с правами Оператор")
    @Description(value = "1. Авторизуемся на СУ под пользователем admin \n" +
            "2. Добавляем пользователя Oper с правами Оператор \n" +
            "3. Проверяем, что пользователь Oper был добавлен и отображается в таблице пользователей")
    @Test
    @Order(1)
    void test_Add_New_User_Oper(){
        addUser(mapInputValueUser).selectRoleUser(2).clickButtonSave();
        isExistsTableText(USER_LOGIN_OPER, true);
        TestStatusResult.setTestResult(true);
    }

    @Story(value = "Входим в систему под новым пользователем с правами Оператор")
    @Description(value = "1. Выходим из системы усправления \n" +
            "2. Авторизуемся в СУ под пользователем Oper \n" +
            "3. Проверяем, что пользователь Oper успешно авторизовался в СУ")
    @Test
    @Order(2)
    void test_Login_New_User_Oper(){
        loginOnServer(USER_LOGIN_OPER, USER_PASSWORD_OPER);
        assertTrue(isLoginUser(USER_LOGIN_OPER), "Не удалось авторизоваться под пользователем " + USER_LOGIN_OPER);
    }

    @Story(value = "Удаляем нового пользователя с правами Оператор")
    @Description(value = "1. Авторизуемся на СУ под пользователем admin \n" +
            "2. Удаляем пользователя Oper с правами Оператор \n" +
            "3. Проверяем, что пользователь Oper был удалён и не отображается в таблице пользователей")
    @Test
    @Order(3)
    void test_Delete_New_User_Oper(){
        loginOnServer(LOGIN_AS_MS, PASSWORD_AS_MS);
        open("/settings/users");
        isDeleteUser(USER_LOGIN_OPER);
    }
}
