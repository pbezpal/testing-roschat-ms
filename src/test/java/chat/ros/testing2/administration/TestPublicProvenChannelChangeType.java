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
@Feature(value = "Публичный проверенный канал. Смена типа канала.")
public class TestPublicProvenChannelChangeType extends ChannelsPage implements TestsParallelBase {

    private String nameChannel = "CHPPCT%1$s";
    private SoftAssert softAssert;

    @BeforeClass
    public void setUp(){
        nameChannel = String.format(nameChannel, System.currentTimeMillis());
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod(){
        softAssert = new SoftAssert();
    }

    @Story(value = "Создаём новый публичный канал")
    @Description(value = "Авторизуемся под пользователем user_1 и создаём новый публичный канал")
    @Test
    void test_2_Create_Public_Channel_7012(){
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

    @Story(value = "Делаем проверенным публичный канал")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы, делаем " +
            "публичный канал проверенным")
    @Test(dependsOnMethods = {"test_2_Create_Public_Channel_7012"})
    void test_2_Do_Proven_Channel_After_Create_Public_Channel(){
        assertTrue(isShowChannel(nameChannel, true),
                "Канал " + nameChannel + " не найден в списке каналов");
        doTestedChannel(nameChannel);
        assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckProvedChannel, nameChannel)).
                        replaceAll(" ", ""),
                "1",
                "Канал " + nameChannel + " в БД postgres не проверенный");
    }

    @Story(value = "Меняем тип канала с публичного на закрытый")
    @Description(value = "Авторизуемся под администратором канала и меняем тип с публичного на закрытый канал")
    @Test(dependsOnMethods = {"test_2_Do_Proven_Channel_After_Create_Public_Channel"})
    void test_2_Edit_Type_With_Public_On_Closed_Channel_7012(){
        softAssert.assertTrue(
                editTypeChannel(
                        nameChannel, CLIENT_TYPE_CHANNEL_CLOSED).
                        isExistComments(nameChannel, true),
                "Канал не найден в списке бесед");
        clickChat(nameChannel);
        softAssert.assertTrue(getTextInfoClosedChannel(true),
                "Не отображается тип канала Закрытый в разделе 'Информация о канале'");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, nameChannel)).
                        replaceAll(" ", ""),
                "1",
                "Тип канала " + nameChannel + " в БД postgres не закрытого типа");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после изменения типа канала")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после изменения типа с публичного на закрытый")
    @Test(priority = 1, dependsOnMethods = {"test_2_Edit_Type_With_Public_On_Closed_Channel_7012"})
    void test_2_Show_Public_Channel_In_MS_After_Change_Type(){
        assertTrue(isShowChannel(nameChannel, false),
                "Закрытый канал " + nameChannel + " отображается в СУ");
    }

    @Story(value = "Удаляем закртытый канал")
    @Description(value = "Авторизуемся под пользователем администратором канала и удаляем канал")
    @Test(priority = 1, dependsOnMethods = {"test_2_Create_Public_Channel_7012"})
    void test_2_Delete_Public_Channel_7012(){
        softAssert.assertTrue(
                deleteChannel(nameChannel).isExistComments(nameChannel, false),
                "Канал найден в списке бесед после удаления");
        softAssert.assertFalse(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel)),
                "Запись о канале " + nameChannel + " осталась в БД postgres после удаления");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем, отображается ли закртытый канал в СУ после удаления")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов после удаления")
    @Test(dependsOnMethods = {"test_2_Delete_Public_Channel_7012"})
    void test_2_Show_Public_Channel_In_MS_After_Delete(){
        assertTrue(isShowChannel(nameChannel, false),
                "Канал " + nameChannel + " отображается в СУ после удаления");
    }

}