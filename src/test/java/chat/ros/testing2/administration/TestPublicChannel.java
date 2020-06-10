package chat.ros.testing2.administration;

import chat.ros.testing2.TestsBase;
import chat.ros.testing2.TestsParallelBase;
import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.administration.ChannelsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;

import static chat.ros.testing2.TestHelper.isWebServerStatus;
import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7012;
import static chat.ros.testing2.helpers.AttachToReport.*;
import static data.CommentsData.*;
import static org.testng.Assert.assertTrue;

@Epic(value = "Администрирование")
@Feature(value = "Публичный канал")
public class TestPublicChannel extends ChannelsPage implements TestsParallelBase {

    private SoftAssert softAssert;
    private String nameChannel;
    private String newNameChannel;
    private String newDescription = CLIENT_DESCRIPTION_CHANNEL_PUBLIC_PROVEN + System.currentTimeMillis();
    private String channel;
    private boolean resultCreate;
    private boolean resultChange;

    @BeforeClass
    void setUp(){
        assertTrue(isWebServerStatus(), "Web сервер не запустился в течение минуты");
        TestsBase.getInstance().init();
        nameChannel = "CHP" + System.currentTimeMillis();
        newNameChannel = nameChannel + System.currentTimeMillis();
    }

    @Story(value = "Создаём новый публичный канал")
    @Description(value = "Авторизуемся под пользователем user_1 и создаём новый публичный канал")
    @Test
    void test_Create_Channel(){
        TestsBase.getInstance().openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(
                createNewChannel(
                        nameChannel,
                        CLIENT_DESCRIPTION_CHANNEL_PUBLIC_PROVEN,
                        CLIENT_ITEM_NEW_CHANNEL,
                        CLIENT_TYPE_CHANNEL_PUBLIC).
                        isExistComments(nameChannel, true),
                "Канал не найден в списке бесед");
        clickChat(nameChannel);
        assertTrue(isTextInfoClosedChannel(false),
                "Присутствует надпись Закрытый в разделе 'Информация о канале'");
    }

    @Story(value = "Проверяем, отображается ли публичный канал в СУ")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов")
    @Test(priority = 1, dependsOnMethods = {"test_Create_Channel"})
    void test_Show_Public_Channel_In_MS(){
        TestsBase.getInstance().openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(nameChannel, true),
                "Публичный канал " + nameChannel + " не отображается в СУ");
    }

    @Story(value = "Меняем название и описание канала")
    @Description(value = "Авторизуемся под администратором канала и меняем название и описание канала. Проверяем, что на" +
            "клиенте отображается новое название и описание канала.")
    @Test(priority = 2, dependsOnMethods = {"test_Create_Channel"})
    void test_Change_Name_And_Description_Channel(){
        TestsBase.getInstance().openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(
                changeDataChannel(
                        nameChannel,true,true, false,
                        newNameChannel, newDescription).
                        isExistComments(newNameChannel, true),
                "Канал не найден в списке бесед после смены типа на публичный");
        clickChat(newNameChannel);
        assertTrue(isTextInfoClosedChannel(false),
                "Есть надписи Закрытый в разделе 'Информация о канале'");

    }

    @Story(value = "Проверяем, отображается ли публичный канал в СУ после смены имени и описания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов после смены имени и описания канала")
    @Test(dependsOnMethods = {"test_Change_Name_And_Description_Channel"})
    void test_Show_Public_Channel_In_MS_After_Change(){
        TestsBase.getInstance().openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(newNameChannel, true),
                "Публичный канал " + newNameChannel + " не отображается в СУ");
    }

    @AfterMethod(alwaysRun = true)
    public void afterTestMethod(Method m, ITestResult testResult){
        Method method = m;
        ITestResult result = testResult;

        if(method.toString().contains("test_Create_Channel")) resultCreate = testResult.isSuccess();
        if(method.toString().contains("test_Change_Name_And_Description_Channel")) resultChange = testResult.isSuccess();

        if(!result.isSuccess() && !method.toString().contains("BD")){
            AScreenshot(method.toString());
            ABrowserLogNetwork();
            ABrowserLogConsole();
        }
    }

    @Story(value = "Удаляем канал и проверяем отображается ли канал в СУ после удаления")
    @Description(value = "1. Авторизуемся под пользователем администратором канала и удаляем канал. " +
            "2. Проверяем на СУ, что канал не отображается после удаления канала")
    @AfterClass
    public void test_Delete_Channel(){
        if (resultCreate || resultChange) {
            if (resultChange) channel = newNameChannel;
            else channel = nameChannel;
            TestsBase.getInstance().openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
            softAssert = new SoftAssert();
            softAssert.assertTrue(
                    deleteChannel(channel).isExistComments(channel, false),
                    "Канал найден в списке бесед после удаления");
            softAssert.assertFalse(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, channel)),
                    "Запись о канале " + channel + " осталась в БД postgres после удаления");
            softAssert.assertAll();

            TestsBase.getInstance().openMS("Администрирование", "Каналы");
            assertTrue(isShowChannel(channel, false),
                    "Закрытый канал " + channel + " отображается в СУ после удаления");
        }
    }
}
