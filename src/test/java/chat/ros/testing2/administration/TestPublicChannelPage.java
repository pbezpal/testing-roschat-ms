package chat.ros.testing2.administration;

import chat.ros.testing2.TestsParallelBase;
import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.administration.ChannelsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static data.CommentsData.*;
import static org.testng.Assert.assertTrue;

@Epic(value = "Администрирование")
@Feature(value = "Публичный канал")
public class TestPublicChannelPage extends ChannelsPage implements TestsParallelBase {

    private String nameChannel = "CHP%1$s";
    private String nameChannel_1 = "CHP_%1$s_1";
    private SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void setUp(){
        nameChannel = String.format(nameChannel, System.currentTimeMillis());
        nameChannel_1 = String.format(nameChannel_1, System.currentTimeMillis());
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod(){
        softAssert = new SoftAssert();
    }

    @Story(value = "Создаём новый публичный канал")
    @Description(value = "Авторизуемся под пользователем user_1 и создаём новый публичный канал")
    @Test
    void test_Create_Public_Channel_7012(){
        assertTrue(
                createNewChannel(
                        nameChannel,
                        CLIENT_DESCRIPTION_CHANNEL_PUBLIC_PROVEN,
                        CLIENT_ITEM_NEW_CHANNEL,
                        CLIENT_TYPE_CHANNEL_PUBLIC).
                        isExistComments(nameChannel, true),
                "Канал не найден в списке бесед");
        clickChat(nameChannel);
        softAssert.assertTrue(getTextInfoClosedChannel(false),
                "Присутствует надпись Закрытый в разделе 'Информация о канале'");
        softAssert.assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel)),
                "Запись о канале " + nameChannel + " не найден в БД postgres");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, nameChannel)).
                        replaceAll(" ",""),
                "0",
                "Тип канала " + nameChannel + " в БД postgres не публичного типа");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем, отображается ли публичный канал в СУ")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов")
    @Test(priority = 1, dependsOnMethods = {"test_Create_Public_Channel_7012"})
    void test_Show_Public_Channel_In_MS(){
        assertTrue(isShowChannel(nameChannel, true),
                "Публичный канал " + nameChannel + " не отображается в СУ");
    }

    @Story(value = "Удаляем публичный канал")
    @Description(value = "Авторизуемся под администароторм канала и удаляем канал")
    @Test(priority = 2, dependsOnMethods = {"test_Create_Public_Channel_7012"})
    void test_Delete_Public_Channel_7012(){
        softAssert.assertTrue(
                deleteChannel(nameChannel).isExistComments(nameChannel, false),
                "Канал найден в списке бесед после удаления");
        softAssert.assertFalse(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel)),
                "Запись о канале " + nameChannel + " осталась в БД postgres после удаления");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем, отображается ли публичный канал в СУ после удаления")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов после удаления")
    @Test(dependsOnMethods = {"test_Delete_Public_Channel_7012"})
    void test_Show_Public_Channel_In_MS_After_Delete(){
        assertTrue(isShowChannel(nameChannel, false),
                "Публичный канал " + nameChannel + " отображается в СУ после удаления");
    }

    @Story(value = "Создаём новый публичный канал")
    @Description(value = "Авторизуемся под пользователем user_1 и создаём новый публичный канал")
    @Test(priority = 1)
    void test_2_Create_Public_Channel_7012(){
        assertTrue(
                createNewChannel(
                        nameChannel_1,
                        CLIENT_DESCRIPTION_CHANNEL_PUBLIC_PROVEN,
                        CLIENT_ITEM_NEW_CHANNEL,
                        CLIENT_TYPE_CHANNEL_PUBLIC).
                        isExistComments(nameChannel_1, true),
                "Канал не найден в списке бесед");
        clickChat(nameChannel_1);
        softAssert.assertTrue(getTextInfoClosedChannel(false),
                "Присутствует надпись Закрытый в разделе 'Информация о канале'");
        softAssert.assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel_1)),
                "Запись о канале " + nameChannel_1 + " не найден в БД postgres");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, nameChannel_1)).
                        replaceAll(" ",""),
                "0",
                "Тип канала " + nameChannel_1 + " в БД postgres не публичного типа");
        softAssert.assertAll();
    }

    @Story(value = "Меняем тип канала с публичного на закрытый")
    @Description(value = "Авторизуемся под администратором канала и меняем тип с публичного на закрытый канал")
    @Test(dependsOnMethods = {"test_2_Create_Public_Channel_7012"})
    void test_2_Edit_Type_With_Public_On_Closed_Channel_7012(){
        softAssert.assertTrue(
                editTypeChannel(
                        nameChannel_1, CLIENT_TYPE_CHANNEL_CLOSED).
                        isExistComments(nameChannel_1, true),
                "Канал не найден в списке бесед");
        softAssert.assertTrue(getTextInfoClosedChannel(true),
                "Не отображается тип канала Закрытый в разделе 'Информация о канале'");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, nameChannel_1)).
                        replaceAll(" ", ""),
                "1",
                "Тип канала " + nameChannel_1 + " в БД postgres не закрытого типа");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после изменения типа канала")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после изменения типа с публичного на закрытый")
    @Test(dependsOnMethods = {"test_2_Edit_Type_With_Public_On_Closed_Channel_7012"})
    void test_2_Show_Public_Channel_In_MS_After_Change_Type(){
        assertTrue(isShowChannel(nameChannel_1, false),
                "Закрытый канал " + nameChannel_1 + " отображается в СУ");
    }

    @Story(value = "Удаляем закртытый канал")
    @Description(value = "Авторизуемся под пользователем администратором канала и удаляем канал")
    @Test(priority = 2, dependsOnMethods = {"test_2_Edit_Type_With_Public_On_Closed_Channel_7012"})
    void test_2_Delete_Public_Channel_7012(){
        softAssert.assertTrue(
                deleteChannel(nameChannel_1).isExistComments(nameChannel_1, false),
                "Канал найден в списке бесед после удаления");
        softAssert.assertFalse(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel_1)),
                "Запись о канале " + nameChannel_1 + " осталась в БД postgres после удаления");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем, отображается ли закртытый канал в СУ после удаления")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов после удаления")
    @Test(dependsOnMethods = {"test_2_Delete_Public_Channel_7012"})
    void test_2_Show_Public_Channel_In_MS_After_Delete(){
        assertTrue(isShowChannel(nameChannel_1, false),
                "Канал " + nameChannel_1 + " отображается в СУ после удаления");
    }
}
