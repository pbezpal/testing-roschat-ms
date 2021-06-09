package chat.ros.testing2.parameters;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.TestsBase;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.extension.*;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.refresh;

public class ResourcesParameters implements BeforeAllCallback, AfterEachCallback, AfterAllCallback {

    private String classTest;
    private TestsBase testsBase = new TestsBase();

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        classTest = context.getTestClass().toString();

        testsBase.init();

        if(classTest.contains("TestParametersServer")) testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки","Сервер");

        if(classTest.contains("TestParametersTelephony")) testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки", "Телефония");

        if(classTest.contains("TestParametersMail")) testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки","Почта");
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if(classTest.contains("TestParameters")){
            refresh();
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        closeWebDriver();
    }
}
