package chat.ros.testing2.contacts;

import chat.ros.testing2.StartWebDriver;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public class ResourcesContactsPage extends StartWebDriver implements BeforeEachCallback, AfterAllCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        String classTest = context.getTestClass().toString();
        if (classTest.contains("TestServicePage") || classTest.contains("TestContactsPage"))
            getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Справочник");
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        closeWebDriver();
    }
}
