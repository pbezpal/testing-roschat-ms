package chat.ros.testing2.administration;

import chat.ros.testing2.TestsParallelBase;
import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.administration.ChannelsPage;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;

import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7012;
import static data.CommentsData.*;
import static org.testng.Assert.assertTrue;

@Epic(value = "Администрирование")
@Feature(value = "Закрытый канал")
public class TestClosedChannel extends ChannelsPage implements TestsParallelBase {

    private String nameChannel = "CHC%1$s";
    private String newNameChannel;
    private String channel;
    private String newDescription = "Новое описание закрытого тестового канала " + System.currentTimeMillis();
    private SoftAssert softAssert;
    private boolean resultCreate;
    private boolean resultChange;

    @BeforeClass
    public void setUp(){
        nameChannel = String.format(nameChannel, System.currentTimeMillis());
        newNameChannel = nameChannel + System.currentTimeMillis();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod(){
        softAssert = new SoftAssert();
    }

    @Story(value = "Создаём новый закрытый канал")
    @Description(value = "Авторизуемся на клиенте и создаём новый закрытый канал. Проверяем, что канал был создан.")
    @Test
    void test_Create_Closed_Channel(){
        testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
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
    @Test(priority = 1, dependsOnMethods = {"test_Create_Closed_Channel"})
    void test_Show_Closed_Channel_In_MS_After_Create(){
        testBase.openMS("/admin/channels");
        assertTrue(isShowChannel(nameChannel, false),
                "Закрытый канал " + nameChannel + " отображается в СУ");
    }

    @Story(value = "Меняем название и описание канала")
    @Description(value = "Авторизуемся под администратором канала и меняем название и описание канала. Проверяем, что на" +
            "клиенте отображается новое название и описание канала.")
    @Test(priority = 2, dependsOnMethods = {"test_Create_Closed_Channel"})
    void test_Change_Name_And_Description_Channel(){
        testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(changeDataChannel(
                        nameChannel,true,true, false,
                        newNameChannel, newDescription).
                        isExistComments(newNameChannel, true),
                "Канал не найден в списке бесед после смены типа на публичный");
        clickChat(newNameChannel);
        softAssert.assertTrue(isTextInfoClosedChannel(true),
                "Нет надписи Закрытый в разделе 'Информация о канале'");
        softAssert.assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, newNameChannel)),
                "Запись о канале " + newNameChannel + " не найден в БД postgres");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, newNameChannel)).
                        replaceAll(" ", ""),
                "1",
                "Тип канала " + newNameChannel + " в БД postgres не закрытого типа");
        softAssert.assertAll();

    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после смены названия и описания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после смены названия и описания")
    @Test(dependsOnMethods = {"test_Change_Name_And_Description_Channel"})
    void test_Show_Closed_Channel_In_MS_After_Change(){
        testBase.openMS("/admin/channels");
        assertTrue(isShowChannel(nameChannel, false),
                "Закрытый канал " + nameChannel + " отображается в СУ после ");
    }

    @AfterMethod(alwaysRun = true)
    void getResult(Method m, ITestResult r){
        Method method = m;
        ITestResult result = r;
        if(method.toString().contains("test_Create_Closed_Channel")) resultCreate = result.isSuccess();
        if(method.toString().contains("test_Change_Name_And_Description_Channel")) resultChange = result.isSuccess();
    }

    @Story(value = "Удаляем закртытый канал и проверяем отображается ли канал в СУ после удаления")
    @Description(value = "1. Авторизуемся под пользователем администратором канала и удаляем канал. " +
            "2. Проверяем на СУ, что канал не отображается после удаления канала")
    @AfterClass(alwaysRun = true)
    void deleteChannel(){
        if(resultCreate || resultChange) {
            if(resultChange) channel = newNameChannel;
            else channel = nameChannel;
            testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
            softAssert = new SoftAssert();
            softAssert.assertTrue(
                    deleteChannel(channel).isExistComments(channel, false),
                    "Канал найден в списке бесед после удаления");
            softAssert.assertFalse(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, channel)),
                    "Запись о канале " + channel + " осталась в БД postgres после удаления");
            softAssert.assertAll();

            testBase.openMS("/admin/channels");
            assertTrue(isShowChannel(channel, false),
                    "Закрытый канал " + channel + " отображается в СУ после удаления");
        }
        WebDriverRunner.closeWebDriver();
    }
}
