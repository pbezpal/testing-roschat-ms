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
import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7013;
import static data.CommentsData.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic(value = "Администрирование")
@Feature(value = "Публичный проверенный канал")
public class TestPublicProvenChannel extends ChannelsPage implements TestsParallelBase {

    private String newDescription = CLIENT_DESCRIPTION_CHANNEL_PUBLIC_PROVEN + System.currentTimeMillis();
    private SoftAssert softAssert;

    @Story(value = "Создаём новый публичный канал")
    @Description(value = "Авторизуемся под пользователем user_1 и создаём новый публичный канал")
    @Test
    void test_Create_Public_Channel(){
        softAssert = new SoftAssert();
        testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(
                createNewChannel(
                        testBase.nameChannel,
                        CLIENT_DESCRIPTION_CHANNEL_PUBLIC_PROVEN,
                        CLIENT_ITEM_NEW_CHANNEL,
                        CLIENT_TYPE_CHANNEL_PUBLIC).
                        isExistComments(testBase.nameChannel, true),
                "Канал не найден в списке бесед");
        clickChat(testBase.nameChannel);
        softAssert.assertTrue(isTextInfoClosedChannel(false),
                "Присутствует надпись Закрытый в разделе 'Информация о канале'");
        softAssert.assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, testBase.nameChannel)),
                "Запись о канале " + testBase.nameChannel + " не найден в БД postgres");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, testBase.nameChannel)).
                        replaceAll(" ",""),
                "0",
                "Тип канала " + testBase.nameChannel + " в БД postgres не публичного типа");
        softAssert.assertAll();
    }

    @Story(value = "Делаем проверенным публичный канал")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы, делаем " +
            "публичный канал проверенным")
    @Test(dependsOnMethods = {"test_Create_Public_Channel"})
    void test_Do_Proven_Channel_After_Create_Public_Channel(){
        testBase.openMS("/admin/channels");
        assertTrue(isShowChannel(testBase.nameChannel, true),
                "Канал " + testBase.nameChannel + " не найден в списке каналов");
        doTestedChannel(testBase.nameChannel);
        assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckProvedChannel, testBase.nameChannel)).
                        replaceAll(" ", ""),
                "1",
                "Канал " + testBase.nameChannel + " в БД postgres не проверенный");
    }

    @Story(value = "Проверяем статус проверенного канала, под учётной записью администратора канала")
    @Description(value = "Авторизуемся на клиенте под учётной записью администратора канала." +
            " Проверяем, что у канала появился статус Проверенный")
    @Test(priority = 1, dependsOnMethods = {"test_Do_Proven_Channel_After_Create_Public_Channel"})
    void test_Check_Status_Proven_Channel(){
        softAssert = new SoftAssert();
        testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        clickItemComments();
        softAssert.assertTrue(isStatusTestedChannelListChat(testBase.nameChannel),
                "Отсутствует статус Проверенный у канала в разделе Беседы");
        softAssert.assertTrue(isStatusTestedChannelMainHeader(testBase.nameChannel),
                "Отсутствует статус Проверенный в заголовке канала");
        softAssert.assertTrue(isStatusTestedInfoChannel(testBase.nameChannel),
                "Отсутствует статус Проверенный в разделе информация о канале");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем под пользователем, что у канала статус проверенного")
    @Description(value = "Авторизуемся на клиенте под учётной записью пользователя и вводим в поле поиска имя" +
            " публичного канала. Проверяем, что у канала статус Проверенный")
    @Test(priority = 2, dependsOnMethods = {"test_Do_Proven_Channel_After_Create_Public_Channel"})
    void test_Search_Status_Proven_Channel(){
        softAssert = new SoftAssert();
        testBase.openClient(CONTACT_NUMBER_7013 + "@ros.chat", false);
        assertTrue(searchChannel(testBase.nameChannel, CLIENT_TYPE_CHANNEL_PUBLIC),
                "Канал не найден");
        softAssert.assertTrue(isStatusTestedChannelListChat(testBase.nameChannel),
                "Отсутствует статус Проверенный у канала в разделе Беседы");
        softAssert.assertTrue(isStatusTestedChannelMainHeader(testBase.nameChannel),
                "Отсутствует статус Проверенный в заголовке канала");
        softAssert.assertTrue(isStatusTestedInfoChannel(testBase.nameChannel),
                "Отсутствует статус Проверенный в разделе информация о канале");
        softAssert.assertAll();
    }

    @Story(value = "Меняем название и описание канала")
    @Description(value = "Авторизуемся под администратором канала и меняем название и описание канала. Проверяем, что на" +
            "клиенте отображается новое название и описание канала.")
    @Test(priority = 3, dependsOnMethods = {"test_Do_Proven_Channel_After_Create_Public_Channel"})
    void test_Change_Name_And_Description_Channel(){
        softAssert = new SoftAssert();
        testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(
                changeDataChannel(
                        testBase.nameChannel,true,true, false,
                        testBase.newNameChannel, newDescription).
                        isExistComments(testBase.newNameChannel, true),
                "Канал не найден в списке бесед после смены типа на публичный");
        clickChat(testBase.newNameChannel);
        softAssert.assertTrue(isTextInfoClosedChannel(true),
                "Есть надписи Закрытый в разделе 'Информация о канале'");
        softAssert.assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, testBase.newNameChannel)),
                "Запись о канале " + testBase.newNameChannel + " не найден в БД postgres");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, testBase.newNameChannel)).
                        replaceAll(" ", ""),
                "0",
                "Тип канала " + testBase.newNameChannel + " в БД postgres не публичного типа");
        softAssert.assertAll();

    }

    @Story(value = "Проверяем, отображается ли публичный канал в СУ после смены имени и описания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов после смены имени и описания канала")
    @Test(dependsOnMethods = {"test_Change_Name_And_Description_Channel"})
    void test_Show_Public_Channel_In_MS_After_Change(){
        testBase.openMS("/admin/channels");
        assertTrue(isShowChannel(testBase.newNameChannel, true),
                "Публичный канал " + testBase.newNameChannel + " не отображается в СУ");
    }
}
