package chat.ros.testing2.server.users;

import chat.ros.testing2.StartWebDriver;
import chat.ros.testing2.TestStatusResult;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.*;

import static chat.ros.testing2.data.LoginData.LOGIN_AS_MS;
import static chat.ros.testing2.data.LoginData.PASSWORD_AS_MS;
import static com.codeborne.selenide.Selenide.open;

public class ResourcesUserPage extends StartWebDriver implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        String methodTest = context.getRequiredTestMethod().getName();
        String classTest = context.getTestClass().toString();
        TestStatusResult.setTestResult(false);
        if(classTest.contains("TestUser")) {
            if (methodTest.contains("Add") || methodTest.equals("test_Refresh_Page")) {
                Configuration.baseUrl = hostServer;
                open("/");
                if(isLoginMS()) logoutMS();
                loginOnServer(LOGIN_AS_MS, PASSWORD_AS_MS);
            } else {
                if (methodTest.equals("test_Login_New_User_Admin") || methodTest.equals("test_Delete_New_User_Admin"))
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_New_User_Admin"),
                            "Admin MS add test failed. Skip the test!");
                else if (methodTest.equals("test_Login_New_User_AS") || methodTest.equals("test_Delete_New_User_AS"))
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_New_User_AS"),
                            "Admin AS add test failed. Skip the test!");
                else if (methodTest.equals("test_Login_New_User_Oper") || methodTest.equals("test_Delete_New_User_Oper"))
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_New_User_Oper"),
                            "Operator add test failed. Skip the test!");
                open("/");
            }
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        String methodTest = context.getRequiredTestMethod().getName();
        if(methodTest.contains("Add") && ! methodTest.equals("test_Add_New_User_Admin_MS"))
            TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusTest());
        logoutMS();
    }
}
