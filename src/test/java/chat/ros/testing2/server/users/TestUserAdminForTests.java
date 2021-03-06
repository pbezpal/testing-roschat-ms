package chat.ros.testing2.server.users;

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

import static chat.ros.testing2.data.SettingsData.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ResourcesUserPage.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Настройки")
@Feature(value = "Пользователи")
public class TestUserAdminForTests extends UserPage {

    private Map<String, String> mapInputValueUser = new HashMap() {{
        put("Фамилия", USER_FIRST_NAME_ADMIN);
        put("Имя", USER_LAST_NAME_ADMIN);
        put("Отчество", USER_PATRON_NAME_ADMIN);
        put("Логин", USER_LOGIN_ADMIN);
        put("Пароль", USER_PASSWORD_ADMIN);
    }};

    @Story(value = "Добавляем пользователя с правами Администратор для тестирования СУ")
    @Description(value = "Авторизовываемся на СУ и добавляем нового пользователя с правами Администратор")
    @Test
    void test_Add_New_User_Admin_MS(){
        addUser(mapInputValueUser).selectRoleUser(1).clickButtonSave();
        isExistsTableText(USER_LOGIN_ADMIN, true);
    }
}
