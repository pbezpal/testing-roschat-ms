package chat.ros.testing2;

import chat.ros.testing2.server.administration.ChannelsPage;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7012;
import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7013;
import static org.testng.Assert.assertTrue;

public interface TestSuiteBase {

    TestsBase testBase = new TestsBase();
    String commandDBCheckChannel = "sudo -u roschat psql -c \"select * from channels;\" | grep '%1$s'";
    String commandDBCheckTypeChannel = commandDBCheckChannel + "| awk -F\"|\" '{print $2}'";
    String commandDBCheckProvedChannel = commandDBCheckChannel + "| awk -F\"|\" '{print $4}'";

    @BeforeSuite
    default void setUp(){
        testBase.init();
        testBase.addContactAndAccount(CONTACT_NUMBER_7012);
        testBase.addContactAndAccount(CONTACT_NUMBER_7013);
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
                testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
            }else testBase.openMS("/settings/web-server");
        }else if(className.contains("TestMailPage")) testBase.openMS("/settings/mail");
        else if (className.contains("TestServicePage")) {
            testBase.openMS("/contacts");
        }else if(className.contains("TestIntegrationPage")) testBase.openMS("/settings/integration");
        else if(className.contains("Channel")){
            if(method.toString().contains("7012")) {
                testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
            }
            else if(method.toString().contains("7013")) {
                testBase.openClient(CONTACT_NUMBER_7013 + "@ros.chat", false);
            }
            else testBase.openMS("/admin/channels");
        }
    }

    @AfterMethod(alwaysRun = true)
    default void afterTestMethod(Method m, ITestResult testResult){
        testBase.afterTestMethod(m, testResult);
    }


    @AfterSuite
    default void tearDown(ITestContext c){
        ITestContext context = c;
        if(context.getCurrentXmlTest().getName().equals("Tests-Channel")){
            testBase.init();
            testBase.openMS("/admin/channels");
            ChannelsPage channelsPage = new ChannelsPage();
            assertTrue(channelsPage.getCountChannels() == 0,
                    "Отображаются записи каналов в СУ после удаления всех каналов");
        }
        WebDriverRunner.closeWebDriver();
    }

}
