package chat.ros.testing2.server;

import chat.ros.testing2.TestSuiteBase;
import chat.ros.testing2.server.settings.UserPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.LoginData.LOGIN_ADMIN_MS;
import static chat.ros.testing2.data.LoginData.PASSWORD_ADMIN_MS;
import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.open;
import static org.testng.Assert.assertTrue;

@Epic(value = "Настройки")
@Feature(value = "Настройки СУ")
public class TestUserPage extends UserPage implements TestSuiteBase {

    private Map<String, String> mapInputValueUser = new HashMap() {{
        put("Фамилия", USER_FIRST_NAME);
        put("Имя", USER_LAST_NAME);
        put("Отчество", USER_PATRON_NAME);
        put("Логин", USER_LOGIN);
        put("Пароль", USER_PASSWORD);
    }};

    @BeforeClass
    public void beforeAll(){
        testBase.openMS("Настройки","Настройка СУ");
    }

    @Story(value = "Добавляем нового пользователя в систему")
    @Description(value = "Переходим в раздел Настройки -> Настройки СУ и добавляем новго пользователя")
    @Test
    void test_Add_New_User(){
        assertTrue(addUser(mapInputValueUser, USER_LOGIN), "Пользователь " + USER_LOGIN + " не был добавлен в систему");
    }

    @Story(value = "Входим в систему под новым пользователем")
    @Description(value = "Выходим из системы усправления и авторизуемся под новым пользователем")
    @Test(priority = 1, dependsOnMethods = {"test_Add_New_User"})
    void test_Login_New_User(){
        logoutMS();
        loginOnServer(USER_LOGIN, USER_PASSWORD);
        assertTrue(isLoginNewUser(USER_LOGIN), "Не удалось авторизоваться под пользователем " + USER_LOGIN);
    }

    @Story(value = "Удаляем нового пользователя")
    @Description(value = "Переходим в раздел Настройки -> Настройки СУ и удаляем нового пользователя СУ")
    @Test(priority = 2, dependsOnMethods = {"test_Add_New_User"})
    void test_Delete_New_User(){
        logoutMS();
        loginOnServer(LOGIN_ADMIN_MS, PASSWORD_ADMIN_MS);
        open("/settings/users");
        assertTrue(isDeleteUser(USER_LOGIN), "Не удалось авторизоваться под пользователем " + USER_LOGIN);
    }
}
