package chat.ros.testing2;

import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.administration.ChannelsPage;
import chat.ros.testing2.server.contacts.ContactsPage;
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

    @BeforeSuite(alwaysRun = true)
    default void beforeSuite(ITestContext c){
        ITestContext context = c;
        if(context.getCurrentXmlTest().getName().contains("Channel")) {
            isCheckContact(CONTACT_NUMBER_7012);
            isCheckContact(CONTACT_NUMBER_7013);
        }
    }

    @BeforeClass(alwaysRun = true)
    default void beforeClass(){
        testBase.getChannelName(this.getClass().getName());
        testBase.init();
    }

    @AfterMethod(alwaysRun = true)
    default void afterTestMethod(Method m, ITestResult testResult){
        String testClass = this.getClass().getName();
        testBase.afterTestMethod(m, testResult, testClass);
    }

    @AfterClass(alwaysRun = true)
    default void tearDown(){
        String testClass = this.getClass().getName();
        testBase.deleteChannel(testClass);
    }

    @AfterSuite(alwaysRun = true)
    default void afterSuite(ITestContext c){
        ITestContext context = c;
        if(context.getCurrentXmlTest().getName().equals("Tests-Channel-Public-Proven")){
            testBase.init();
            testBase.openMS("/admin/channels");
            ChannelsPage channelsPage = new ChannelsPage();
            assertTrue(channelsPage.getCountChannels() == 0,
                    "Отображаются записи каналов в СУ после удаления всех каналов");
        }
    }

    default void isCheckContact(String number){
        if (!SSHManager.isCheckQuerySSH(String.format(testBase.sshCommandIsContact, number))) {
            testBase.init();
            ContactsPage contactsPage = new ContactsPage();
            testBase.openMS("/contacts");
            if (contactsPage.isNotExistsTableText(number)) {
                contactsPage.actionsContact(number).addUserAccount(number, USER_ACCOUNT_PASSWORD, USER_ACOUNT_ITEM_MENU);
            }
            WebDriverRunner.closeWebDriver();
        }
    }


}
