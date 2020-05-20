package chat.ros.testing2;

import chat.ros.testing2.server.administration.ChannelsPage;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static chat.ros.testing2.data.ContactsData.*;
import static org.testng.Assert.assertTrue;

public interface TestsParallelBase {

    TestsBase testBase = new TestsBase();
    String commandDBCheckChannel = "sudo -u roschat psql -c \"select * from channels;\" | grep '%1$s'";
    String commandDBCheckTypeChannel = commandDBCheckChannel + "| awk -F\"|\" '{print $2}'";
    String commandDBCheckProvedChannel = commandDBCheckChannel + "| awk -F\"|\" '{print $4}'";

    @BeforeClass
    default void beforeClass(){
        testBase.init();
        String className = this.getClass().getName();
        if(className.contains("Channel")) {
            testBase.addContactAndAccount(CONTACT_NUMBER_7012);
            testBase.addContactAndAccount(CONTACT_NUMBER_7013);
        }
    }

    @BeforeMethod(alwaysRun = true)
    default void beforeTest(Method testMethod){
        String className = this.getClass().getName();
        Method method = testMethod;
        if(className.contains("Channel")){
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

    @AfterClass
    default void destroyWebDriver(){
        WebDriverRunner.closeWebDriver();
    }

    @AfterSuite
    default void afterSuite(ITestContext c){
        ITestContext context = c;
        if(context.getCurrentXmlTest().getName().equals("Channels-Public-Proven")){
            testBase.init();
            testBase.openMS("/admin/channels");
            ChannelsPage channelsPage = new ChannelsPage();
            assertTrue(channelsPage.getCountChannels() == 0,
                    "Отображаются записи каналов в СУ после удаления всех каналов");
        }
    }
}
