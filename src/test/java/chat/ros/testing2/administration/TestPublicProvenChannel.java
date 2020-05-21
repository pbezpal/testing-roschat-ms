package chat.ros.testing2.administration;

import chat.ros.testing2.TestSuiteBase;
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
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic(value = "Администрирование")
@Feature(value = "Публичный проверенный канал")
public class TestPublicProvenChannel extends ChannelsPage implements TestSuiteBase {

    private String nameChannel = "CHPP%1$s";
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
        softAssert.assertTrue(isTextInfoClosedChannel(false),
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
    @Test(dependsOnMethods = {"test_Create_Public_Channel_7012"})
    void test_Do_Proven_Channel_After_Create_Public_Channel(){
        assertTrue(isShowChannel(nameChannel, true));
        doTestedChannel(nameChannel);
        assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckProvedChannel, nameChannel)).
                        replaceAll(" ", ""),
                "1",
                "Канал " + nameChannel + " в БД postgres не проверенный");
    }

    @Story(value = "Проверяем статус проверенного канала, под учётной записью администратора канала")
    @Description(value = "Авторизуемся на клиенте под учётной записью администратора канала." +
            " Проверяем, что у канала появился статус Проверенный")
    @Test(dependsOnMethods = {"test_Do_Proven_Channel_After_Create_Public_Channel"})
    void test_Check_Status_Proven_Channel_7012(){
        clickItemComments();
        softAssert.assertTrue(isStatusTestedChannelListChat(nameChannel),
                "Отсутствует статус Проверенный у канала в разделе Беседы");
        softAssert.assertTrue(isStatusTestedChannelMainHeader(nameChannel),
                "Отсутствует статус Проверенный в заголовке канала");
        softAssert.assertTrue(isStatusTestedInfoChannel(nameChannel),
                "Отсутствует статус Проверенный в разделе информация о канале");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем под пользователем, что у канала статус проверенного")
    @Description(value = "Авторизуемся на клиенте под учётной записью пользователя и вводим в поле поиска имя" +
            " публичного канала. Проверяем, что у канала статус Проверенный")
    @Test(dependsOnMethods = {"test_Do_Proven_Channel_After_Create_Public_Channel"})
    void test_Search_Status_Proven_Channel_7013(){
        assertTrue(searchChannel(nameChannel, CLIENT_TYPE_CHANNEL_PUBLIC),
                "Канал не найден");
        softAssert.assertTrue(isStatusTestedChannelListChat(nameChannel),
                "Отсутствует статус Проверенный у канала в разделе Беседы");
        softAssert.assertTrue(isStatusTestedChannelMainHeader(nameChannel),
                "Отсутствует статус Проверенный в заголовке канала");
        softAssert.assertTrue(isStatusTestedInfoChannel(nameChannel),
                "Отсутствует статус Проверенный в разделе информация о канале");
        softAssert.assertAll();
    }

    @Story(value = "Удаляем публичный проверенный канал и проверяем отображается ли канал в СУ после удаления")
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
                "Публичный канал " + nameChannel + " отображается в СУ после удаления");
    }
}
