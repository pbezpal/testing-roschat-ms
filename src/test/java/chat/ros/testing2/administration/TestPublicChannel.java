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
@Feature(value = "Публичный канал")
public class TestPublicChannel extends ChannelsPage implements TestsParallelBase {

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

    @Story(value = "Проверяем, отображается ли публичный канал в СУ")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов")
    @Test(priority = 1, dependsOnMethods = {"test_Create_Public_Channel"})
    void test_Show_Public_Channel_In_MS(){
        testBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(testBase.nameChannel, true),
                "Публичный канал " + testBase.nameChannel + " не отображается в СУ");
    }

    @Story(value = "Меняем название и описание канала")
    @Description(value = "Авторизуемся под администратором канала и меняем название и описание канала. Проверяем, что на" +
            "клиенте отображается новое название и описание канала.")
    @Test(priority = 2, dependsOnMethods = {"test_Create_Public_Channel"})
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
        testBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(testBase.newNameChannel, true),
                "Публичный канал " + testBase.newNameChannel + " не отображается в СУ");
    }
}
