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
@Feature(value = "Публичный канал.Изменение данных канала.")
public class TestPublicChannelChange extends ChannelsPage implements TestsParallelBase {

    private String newDescription = CLIENT_DESCRIPTION_CHANNEL_CLOSED + System.currentTimeMillis();
    private SoftAssert softAssert;

    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod(){
        softAssert = new SoftAssert();
    }

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

    @Story(value = "Меняем тип канала с публичного на закрытый")
    @Description(value = "Авторизуемся под администратором канала и меняем тип с публичного на закрытый канал")
    @Test(dependsOnMethods = {"test_Create_Public_Channel"})
    void test_Edit_Type_With_Public_On_Closed_Channel(){
        softAssert = new SoftAssert();
        testBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        softAssert.assertTrue(
                changeDataChannel(testBase.nameChannel,false,false,true,
                        CLIENT_TYPE_CHANNEL_CLOSED).
                        isExistComments(testBase.nameChannel, true),
                "Канал не найден в списке бесед");
        softAssert.assertTrue(isTextInfoClosedChannel(true),
                "Не отображается тип канала Закрытый в разделе 'Информация о канале'");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, testBase.nameChannel)).
                        replaceAll(" ", ""),
                "1",
                "Тип канала " + testBase.nameChannel + " в БД postgres не закрытого типа");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после изменения типа канала")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после изменения типа с публичного на закрытый")
    @Test(priority = 1, dependsOnMethods = {"test_Edit_Type_With_Public_On_Closed_Channel"})
    void test_Show_Public_Channel_In_MS_After_Change_Type(){
        testBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(testBase.nameChannel, false),
                "Закрытый канал " + testBase.nameChannel + " отображается в СУ");
    }

    @Story(value = "Меняем название и описание канала")
    @Description(value = "Авторизуемся под администратором канала и меняем название и описание канала. Проверяем, что на" +
            "клиенте отображается новое название и описание канала.")
    @Test(priority = 2, dependsOnMethods = {"test_Edit_Type_With_Public_On_Closed_Channel"})
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
                "Нет надписи Закрытый в разделе 'Информация о канале'");
        softAssert.assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, testBase.newNameChannel)),
                "Запись о канале " + testBase.newNameChannel + " не найден в БД postgres");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, testBase.newNameChannel)).
                        replaceAll(" ", ""),
                "1",
                "Тип канала " + testBase.newNameChannel + " в БД postgres не закрытого типа");
        softAssert.assertAll();

    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после смены названия и описания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после смены названия и описания")
    @Test(dependsOnMethods = {"test_Change_Name_And_Description_Channel"})
    void test_Show_Closed_Channel_In_MS_After_Change(){
        testBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(testBase.newNameChannel, false),
                "Закрытый канал " + testBase.newNameChannel + " отображается в СУ после смены имени " +
                        "и описания канала");
    }
}
