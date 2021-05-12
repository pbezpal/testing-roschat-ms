package chat.ros.testing2.parameters;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.TestsBase;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;
import static com.codeborne.selenide.Selenide.refresh;

public class ResourcesParameters implements BeforeAllCallback, AfterEachCallback {

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
}
