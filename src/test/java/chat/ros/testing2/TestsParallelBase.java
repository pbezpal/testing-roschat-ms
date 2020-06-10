package chat.ros.testing2;

import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.administration.ChannelsPage;
import chat.ros.testing2.server.contacts.ContactsPage;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.ITestContext;
import org.testng.annotations.*;

import static chat.ros.testing2.data.ContactsData.*;
import static org.testng.Assert.assertTrue;

public interface TestsParallelBase {

    String commandDBCheckChannel = "sudo -u roschat psql -c \"select * from channels;\" | grep '%1$s'";
    String commandDBCheckTypeChannel = commandDBCheckChannel + "| awk -F\"|\" '{print $2}'";
    String commandDBCheckProvedChannel = commandDBCheckChannel + "| awk -F\"|\" '{print $4}'";

    @BeforeSuite(alwaysRun = true)
    default void beforeSuite(ITestContext c){
        ITestContext context = c;
        if(context.getCurrentXmlTest().getName().contains("Channel")) {
            isCheckContacts(CONTACT_NUMBER_7012, CONTACT_NUMBER_7013);
        }
    }

    @AfterSuite(alwaysRun = true)
    default void afterSuite(ITestContext c){
        ITestContext context = c;
        if(context.getCurrentXmlTest().getName().equals("Tests-Channel-Public-Proven")){
            TestsBase.getInstance().init();
            TestsBase.getInstance().openMS("Администрирование","Каналы");
            ChannelsPage channelsPage = new ChannelsPage();
            assertTrue(channelsPage.getCountChannels() == 0,
                    "Отображаются записи каналов в СУ после удаления всех каналов");
        }
    }

    default void isCheckContacts(String... number){
        for(int i = 0; i < number.length; i++){
            if (!SSHManager.isCheckQuerySSH(String.format(TestsBase.getInstance().getSshCommandIsContact(), number[i]))) {
                TestsBase.getInstance().init();
                ContactsPage contactsPage = new ContactsPage();
                TestsBase.getInstance().openMS("Справочник");
                if (contactsPage.isNotExistsTableText(number[i])) {
                    contactsPage.actionsContact(number[i]).addUserAccount(number[i], USER_ACCOUNT_PASSWORD, USER_ACOUNT_ITEM_MENU);
                }
                TestsBase.getInstance().dismissWebDriver();
            }
        }
    }


}
