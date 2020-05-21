package chat.ros.testing2.administration;

import chat.ros.testing2.TestsParallelBase;
import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.administration.ChannelsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7012;
import static data.CommentsData.*;
import static org.testng.Assert.assertTrue;

@Epic(value = "Администрирование")
@Feature(value = "Закрытый канал")
public class TestClosedChannel extends ChannelsPage implements TestsParallelBase {

    private String nameChannel = "CHC%1$s";
    private SoftAssert softAssert;

    @BeforeClass
    public void setUp(){
        nameChannel = String.format(nameChannel, System.currentTimeMillis());
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod(){
        softAssert = new SoftAssert();
    }

    @Story(value = "Создаём новый закрытый канал")
    @Description(value = "Авторизуемся под пользователем user_1 и создаём новый закрытый канал")
    @Test
    void test_Create_Closed_Channel_7012(){
        assertTrue(
                createNewChannel(
                        nameChannel,
                        CLIENT_DESCRIPTION_CHANNEL_CLOSED,
                        CLIENT_ITEM_NEW_CHANNEL,
                        CLIENT_TYPE_CHANNEL_CLOSED).
                        isExistComments(nameChannel, true),
                "Канал не найден в списке бесед");
        clickChat(nameChannel);
        softAssert.assertTrue(isTextInfoClosedChannel(true),
                "Отсутствует надпись Закрытый в разделе 'Информация о канале'");
        softAssert.assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel)),
                "Запись о канале " + nameChannel + " не найден в БД postgres");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, nameChannel)).
                        replaceAll(" ",""),
                "1",
                "Тип канала " + nameChannel + " в БД postgres не закрытого типа");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после создания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после создания")
    @Test(dependsOnMethods = {"test_Create_Closed_Channel_7012"})
    void test_Show_Closed_Channel_In_MS_After_Create(){
        assertTrue(isShowChannel(nameChannel, false),
                "Закрытый канал " + nameChannel + " отображается в СУ");
    }

    @Story(value = "Удаляем закртытый канал и проверяем отображается ли канал в СУ после удаления")
    @Description(value = "1. Авторизуемся под пользователем администратором канала и удаляем канал. " +
            "2. Проверяем на СУ, что канал не отображается после удаления канала")
    @AfterClass
    void deleteChannel(){
        testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        softAssert = new SoftAssert();
        softAssert.assertTrue(
                deleteChannel(nameChannel).isExistComments(nameChannel, false),
                "Канал найден в списке бесед после удаления");
        softAssert.assertFalse(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel)),
                "Запись о канале " + nameChannel + " осталась в БД postgres после удаления");
        softAssert.assertAll();

        testBase.openMS("/admin/channels");
        assertTrue(isShowChannel(nameChannel, false),
                "Закрытый канал " + nameChannel + " отображается в СУ после удаления");
    }
}
