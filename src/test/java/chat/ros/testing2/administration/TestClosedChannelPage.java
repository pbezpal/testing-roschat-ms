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
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic(value = "Администрирование")
@Feature(value = "Закрытый канал")
public class TestClosedChannelPage extends ChannelsPage implements TestsParallelBase {

    private String nameChannel = "CHC%1$s";
    private String nameChannel_1 = "CHC_%1$s_1";
    private SoftAssert softAssert;

    @BeforeClass
    public void setUp(){
        nameChannel = String.format(nameChannel, System.currentTimeMillis());
        nameChannel_1 = String.format(nameChannel_1, System.currentTimeMillis());
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
        softAssert.assertTrue(getTextInfoClosedChannel(true),
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
    @Test(priority = 1, dependsOnMethods = {"test_Create_Closed_Channel_7012"})
    void test_Show_Closed_Channel_In_MS_After_Create(){
        assertTrue(isShowChannel(nameChannel, false),
                "Закрытый канал " + nameChannel + " отображается в СУ");
    }

    @Story(value = "Удаляем закртытый канал")
    @Description(value = "Авторизуемся под пользователем user_1 и удаляем канал")
    @Test(priority = 2, dependsOnMethods = {"test_Create_Closed_Channel_7012"})
    void test_Delete_Closed_Channel_7012(){
        assertTrue(
                deleteChannel(nameChannel).isExistComments(nameChannel, false),
                "Канал найден в списке бесед после удаления");
    }

    @Story(value = "Проверяем, отображается ли закртытый канал в СУ после удаления")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов после удаления")
    @Test(dependsOnMethods = {"test_Delete_Closed_Channel_7012"})
    void test_Show_Closed_Channel_In_MS_After_Delete(){
        assertTrue(isShowChannel(nameChannel, false),
                "Закрытый канал " + nameChannel + " отображается в СУ");
    }

    @Story(value = "Создаём новый закрытый канал")
    @Description(value = "Авторизуемся под пользователем user_1 и создаём новый закрытый канал")
    @Test(priority = 1)
    void test_2_Create_Closed_Channel_7012(){
        assertTrue(
                createNewChannel(
                        nameChannel_1,
                        CLIENT_DESCRIPTION_CHANNEL_CLOSED,
                        CLIENT_ITEM_NEW_CHANNEL,
                        CLIENT_TYPE_CHANNEL_CLOSED).
                        isExistComments(nameChannel_1, true),
                "Канал не найден в списке бесед");
        clickChat(nameChannel_1);
        softAssert.assertTrue(getTextInfoClosedChannel(true),
                "Отсутствует надпись Закрытый в разделе 'Информация о канале'");
        softAssert.assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel_1)),
                "Запись о канале " + nameChannel_1 + " не найден в БД postgres");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, nameChannel_1)).
                        replaceAll(" ",""),
                "1",
                "Тип канала " + nameChannel_1 + " в БД postgres не закрытого типа");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после создания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после создания")
    @Test(priority = 1, dependsOnMethods = {"test_2_Create_Closed_Channel_7012"})
    void test_2_Show_Closed_Channel_In_MS_After_Create(){
        assertTrue(isShowChannel(nameChannel_1, false),
                "Закрытый канал " + nameChannel_1 + " отображается в СУ");
    }

    @Story(value = "Меняем тип канала с закрытого на публичный")
    @Description(value = "Авторизуемся под пользователем user_1 и меняем тип у закрытого каналана на публичный")
    @Test(priority = 2, dependsOnMethods = {"test_2_Create_Closed_Channel_7012"})
    void test_2_Edit_Type_With_Closed_On_Public_Channel_7012(){
        softAssert.assertTrue(
                editTypeChannel(
                        nameChannel_1, CLIENT_TYPE_CHANNEL_PUBLIC).
                        isExistComments(nameChannel_1, true),
                "Канал не найден в списке бесед");
        clickChat(nameChannel_1);
        softAssert.assertTrue(getTextInfoClosedChannel(false),
                "Отображается надпись Закрытый в разделе 'Информация о канале'");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, nameChannel_1)).
                        replaceAll(" ", ""),
                "0",
                "Тип канала " + nameChannel_1 + " в БД postgres не публичного типа");
        softAssert.assertAll();
    }

    @Story(value = "Делаем проверенным канал после смены на публичный тип")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы," +
            " делаем публичный канал проверенным")
    @Test(dependsOnMethods = {"test_2_Edit_Type_With_Closed_On_Public_Channel_7012"})
    void test_2_Do_Proven_Channel_After_Edit_Type_Closed_Channel(){
        assertTrue(isShowChannel(nameChannel_1, true),
                "Канал " + nameChannel_1 + " не найден в списке каналов");
        doTestedChannel(nameChannel_1);
        assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckProvedChannel, nameChannel_1)).
                        replaceAll(" ", ""),
                "1",
                "Канал " + nameChannel_1 + " в БД postgres не проверенный");
    }

    @Story(value = "Проверяем статус проверенного канала, под учётной записью администратора канала")
    @Description(value = "Авторизуемся на клиенте под учётной записью администратора канала." +
            " Проверяем, что у канала появился статус Проверенный")
    @Test(dependsOnMethods = {"test_2_Do_Proven_Channel_After_Edit_Type_Closed_Channel"})
    void test_2_Check_Status_Closed_Channel_7012(){
        clickItemComments();
        softAssert.assertTrue(isStatusTestedChannelListChat(nameChannel_1),
                "Отсутствует статус Проверенный у канала в разделе Беседы");
        softAssert.assertTrue(isStatusTestedChannelMainHeader(nameChannel_1),
                "Отсутствует статус Проверенный в заголовке канала");
        softAssert.assertTrue(isStatusTestedInfoChannel(nameChannel_1),
                "Отсутствует статус Проверенный в разделе информация о канале");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем под пользователем, что у канала статус проверенного")
    @Description(value = "Авторизуемся на клиенте под учётной записью пользователя и вводим в поле поиска имя" +
            " публичного канала. Проверяем, что у канала статус Проверенный")
    @Test(priority = 1, dependsOnMethods = {"test_2_Do_Proven_Channel_After_Edit_Type_Closed_Channel"})
    void test_2_Search_Closed_Channel_7013(){
        assertTrue(searchChannel(nameChannel_1, CLIENT_TYPE_CHANNEL_PUBLIC),
                "Канал не найден");
        softAssert.assertTrue(isStatusTestedChannelListChat(nameChannel_1),
                "Отсутствует статус Проверенный у канала в разделе Беседы");
        softAssert.assertTrue(isStatusTestedChannelMainHeader(nameChannel_1),
                "Отсутствует статус Проверенный в заголовке канала");
        softAssert.assertTrue(isStatusTestedInfoChannel(nameChannel_1),
                "Отсутствует статус Проверенный в разделе информация о канале");
        softAssert.assertAll();
    }

    @Story(value = "Удаляем публичный проверенный канал")
    @Description(value = "Авторизуемся под пользователем user_1 и удаляем публичный проверенный канал")
    @Test(priority = 2, dependsOnMethods = {"test_2_Edit_Type_With_Closed_On_Public_Channel_7012"})
    void test_2_Delete_Closed_Channel_7012(){
        softAssert.assertTrue(
                deleteChannel(nameChannel_1).isExistComments(nameChannel_1, false),
                "Канал найден в списке бесед после удаления");
        softAssert.assertFalse(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel_1)),
                "Запись о канале " + nameChannel_1 + " осталась в БД postgres после удаления");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем, отображается ли публичный проверенный канал в СУ после удаления")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "публичный проверенный канал в списке каналов после удаления")
    @Test(dependsOnMethods = {"test_Delete_Closed_Channel_7012"})
    void test_2_Show_Closed_Channel_In_MS_After_Delete(){
        assertTrue(isShowChannel(nameChannel_1, false),
                "Канал " + nameChannel_1 + " отображается в СУ после удаления");
    }
}
