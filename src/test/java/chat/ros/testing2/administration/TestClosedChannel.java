package chat.ros.testing2.administration;

import chat.ros.testing2.TestsParallelBase;
import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.administration.ChannelsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7012;
import static data.CommentsData.*;
import static org.testng.Assert.assertTrue;

@Epic(value = "Администрирование")
@Feature(value = "Закрытый канал")
public class TestClosedChannel extends ChannelsPage implements TestsParallelBase {

    private String newDescription = CLIENT_DESCRIPTION_CHANNEL_CLOSED + " " + System.currentTimeMillis();
    private SoftAssert softAssert;

    @Story(value = "Создаём новый закрытый канал")
    @Description(value = "Авторизуемся на клиенте и создаём новый закрытый канал. Проверяем, что канал был создан.")
    @Test
    void test_Create_Closed_Channel(){
        testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(
                createNewChannel(
                        testBase.nameChannel,
                        CLIENT_DESCRIPTION_CHANNEL_CLOSED,
                        CLIENT_ITEM_NEW_CHANNEL,
                        CLIENT_TYPE_CHANNEL_CLOSED).
                        isExistComments(testBase.nameChannel, true),
                "Канал не найден в списке бесед");
        clickChat(testBase.nameChannel);
        assertTrue(isTextInfoClosedChannel(true),
                "Отсутствует надпись Закрытый в разделе 'Информация о канале'");
    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после создания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после создания")
    @Test(priority = 1, dependsOnMethods = {"test_Create_Closed_Channel"})
    void test_Show_Closed_Channel_In_MS_After_Create(){
        testBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(testBase.nameChannel, false),
                "Закрытый канал " + testBase.nameChannel + " отображается в СУ");
    }

    @Story(value = "Меняем название и описание канала")
    @Description(value = "Авторизуемся под администратором канала и меняем название и описание канала. Проверяем, что на" +
            "клиенте отображается новое название и описание канала.")
    @Test(priority = 2, dependsOnMethods = {"test_Create_Closed_Channel"})
    void test_Change_Name_And_Description_Channel(){
        testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(changeDataChannel(
                testBase.nameChannel,true,true, false,
                        testBase.newNameChannel, newDescription).
                        isExistComments(testBase.newNameChannel, true),
                "Канал не найден в списке бесед после смены типа на публичный");
        clickChat(testBase.newNameChannel);
        assertTrue(isTextInfoClosedChannel(true),
                "Нет надписи Закрытый в разделе 'Информация о канале'");

    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после смены названия и описания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после смены названия и описания")
    @Test(dependsOnMethods = {"test_Change_Name_And_Description_Channel"})
    void test_Show_Closed_Channel_In_MS_After_Change(){
        testBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(testBase.newNameChannel, false),
                "Закрытый канал " + testBase.newNameChannel + " отображается в СУ после ");
    }
}
