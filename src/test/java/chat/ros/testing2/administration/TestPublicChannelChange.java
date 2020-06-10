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
@Feature(value = "Публичный канал. Изменение данных канала.")
public class TestPublicChannelChange extends ChannelsPage implements TestsParallelBase {

    private SoftAssert softAssert;
    private String nameChannel;
    private String newNameChannel;
    private String newDescription = CLIENT_DESCRIPTION_CHANNEL_CLOSED + System.currentTimeMillis();
    private String channel;
    private boolean resultCreate;
    private boolean resultChange;

    @BeforeClass
    void setUp(){
        assertTrue(isWebServerStatus(), "Web сервер не запустился в течение минуты");
        TestsBase.getInstance().init();
        nameChannel = "CHPCH" + System.currentTimeMillis();
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

    @Story(value = "Меняем тип канала с публичного на закрытый")
    @Description(value = "Авторизуемся под администратором канала и меняем тип с публичного на закрытый канал")
    @Test(dependsOnMethods = {"test_Create_Channel"})
    void test_Edit_Type_With_Public_On_Closed_Channel(){
        TestsBase.getInstance().openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(
                changeDataChannel(nameChannel,false,false,true,
                        CLIENT_TYPE_CHANNEL_CLOSED).
                        isExistComments(nameChannel, true),
                "Канал не найден в списке бесед");
        assertTrue(isTextInfoClosedChannel(true),
                "Не отображается тип канала Закрытый в разделе 'Информация о канале'");
    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после изменения типа канала")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после изменения типа с публичного на закрытый")
    @Test(priority = 1, dependsOnMethods = {"test_Edit_Type_With_Public_On_Closed_Channel"})
    void test_Show_Public_Channel_In_MS_After_Change_Type(){
        TestsBase.getInstance().openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(nameChannel, false),
                "Закрытый канал " + nameChannel + " отображается в СУ");
    }

    @Story(value = "Меняем название и описание канала")
    @Description(value = "Авторизуемся под администратором канала и меняем название и описание канала. Проверяем, что на" +
            "клиенте отображается новое название и описание канала.")
    @Test(priority = 2, dependsOnMethods = {"test_Edit_Type_With_Public_On_Closed_Channel"})
    void test_Change_Name_And_Description_Channel(){
        TestsBase.getInstance().openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(
                changeDataChannel(
                        nameChannel,true,true, false,
                        newNameChannel, newDescription).
                        isExistComments(newNameChannel, true),
                "Канал не найден в списке бесед после смены типа на публичный");
        clickChat(newNameChannel);
        assertTrue(isTextInfoClosedChannel(true),
                "Нет надписи Закрытый в разделе 'Информация о канале'");

    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после смены названия и описания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после смены названия и описания")
    @Test(dependsOnMethods = {"test_Change_Name_And_Description_Channel"})
    void test_Show_Closed_Channel_In_MS_After_Change(){
        TestsBase.getInstance().openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(newNameChannel, false),
                "Закрытый канал " + newNameChannel + " отображается в СУ после смены имени " +
                        "и описания канала");
    }

    @AfterMethod(alwaysRun = true)
    public void afterTestMethod(Method m, ITestResult testResult){
        Method method = m;
        ITestResult result = testResult;
        if(method.toString().contains("test_Create_Channel")) resultCreate = result.isSuccess();
        if(method.toString().contains("test_Change_Name_And_Description_Channel")) resultChange = result.isSuccess();

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
