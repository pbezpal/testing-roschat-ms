package chat.ros.testing2.server;

import chat.ros.testing2.TestSuiteBase;
import chat.ros.testing2.TestsBase;
import chat.ros.testing2.server.settings.UserPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.LoginData.LOGIN_ADMIN_MS;
import static chat.ros.testing2.data.LoginData.PASSWORD_ADMIN_MS;
import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
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

    @BeforeMethod
    void beforeTest(Method method){
        if(method.toString().contains("Login") || method.toString().contains("Delete")){
            Configuration.baseUrl = TestsBase.getInstance().getHostMS();
            open("/");
            logoutMS();
        }else if(method.toString().contains("Open")){
            TestsBase.getInstance().openMS();
            open("/settings/users");
        }else TestsBase.getInstance().openMS("Настройки","Настройка СУ");
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
        loginOnServer(USER_LOGIN, USER_PASSWORD);
        assertTrue(isLoginNewUser(USER_LOGIN), "Не удалось авторизоваться под пользователем " + USER_LOGIN);
    }

    @Story(value = "Удаляем нового пользователя")
    @Description(value = "Переходим в раздел Настройки -> Настройки СУ и удаляем нового пользователя СУ")
    @Test(priority = 2, dependsOnMethods = {"test_Add_New_User"})
    void test_Delete_New_User(){
        loginOnServer(LOGIN_ADMIN_MS, PASSWORD_ADMIN_MS);
        open("/settings/users");
        assertTrue(isDeleteUser(USER_LOGIN), "Не удалось авторизоваться под пользователем " + USER_LOGIN);
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
