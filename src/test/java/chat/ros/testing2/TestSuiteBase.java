package chat.ros.testing2;

import com.codeborne.selenide.WebDriverRunner;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7012;

public interface TestSuiteBase {

    TestsBase testBase = new TestsBase();

    @BeforeSuite
    default void setUp(){
        testBase.init();
    }

    @BeforeClass
    default void beforeAll(){
        String className = this.getClass().getName();
        if (className.contains("TestTelephonyPage")) testBase.openMS("/settings/telephony");
        else if (className.contains("TestGeozonesPage")) testBase.openMS("/settings/geozones");
        else if (className.contains("TestSNMPPage")) testBase.openMS("/settings/snmp");
        else if (className.contains("TestUserPage")) testBase.openMS("/settings/users");
    }

    @BeforeMethod
    default void beforeTest(Method m){
        Method method = m;
        String className = this.getClass().getName();
        if(className.contains("TestServerPage")){
            if(method.toString().contains("Client")) {
                testBase.addContactAndAccount(CONTACT_NUMBER_7012);
                testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
            }else testBase.openMS("/settings/web-server");
        }else if(className.contains("TestMailPage")) testBase.openMS("/settings/mail");
        else if (className.contains("TestServicePage")) {
            testBase.addContactAndAccount(CONTACT_NUMBER_7012);
            testBase.openMS("/contacts");
        }else if(className.contains("TestIntegrationPage")) testBase.openMS("/settings/integration");
    }

    @AfterMethod(alwaysRun = true)
    default void afterTestMethod(Method m, ITestResult testResult){
        testBase.afterTestMethod(m, testResult);
    }


    @AfterSuite
    default void tearDown(){
        WebDriverRunner.closeWebDriver();
    }

}
