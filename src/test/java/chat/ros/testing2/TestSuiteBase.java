package chat.ros.testing2;

import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7012;
import static chat.ros.testing2.data.LoginData.HOST_SERVER;
import static chat.ros.testing2.data.SettingsData.SERVER_CONNECT_HTTP_OTHER_PORT;

public interface TestSuiteBase {

    TestsBase testBase = new TestsBase();

    @BeforeSuite
    default void setUp(){
        testBase.init();
    }

    @BeforeClass
    default void beforeAll(){
        String className = this.getClass().getName();
        if (className.contains("Test_A_TelephonyPage")) testBase.openMS("/settings/telephony");
        else if (className.contains("chat.ros.testing2.server.Test_A_GeozonesPage")) testBase.openMS("/settings/geozones");
        else if (className.contains("Test_A_SNMPPage")) testBase.openMS("/settings/snmp");
        else if (className.contains("Test_A_UserPage")) testBase.openMS("/settings/users");
    }

    @BeforeMethod
    default void beforeTest(Method m){
        Method method = m;
        if(method.toString().contains("TestServerPage")){
            if(method.toString().contains("Client")) {
                testBase.addContactAndAccount(CONTACT_NUMBER_7012);
                testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
            }else testBase.openMS("/settings/web-server");
        }else if(method.toString().contains("TestMailPage")) testBase.openMS("/settings/mail");
        else if (method.toString().contains("TestServicePage")) {
            testBase.addContactAndAccount(CONTACT_NUMBER_7012);
            testBase.openMS("/contacts");
        }
    }

    @AfterSuite
    default void tearDown(){
        WebDriverRunner.closeWebDriver();
    }

}
