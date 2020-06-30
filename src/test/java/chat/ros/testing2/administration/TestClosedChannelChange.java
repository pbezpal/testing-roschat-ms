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
import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7013;
import static chat.ros.testing2.helpers.AttachToReport.*;
import static data.CommentsData.*;
import static org.testng.Assert.assertTrue;

@Epic(value = "Администрирование")
@Feature(value = "Закрытый канал. Изменение данных канала.")
public class TestClosedChannelChange extends ChannelsPage implements TestsParallelBase {

    private SoftAssert softAssert;    
    private String nameChannel;
    private String newNameChannel;
    private String newDescription = CLIENT_DESCRIPTION_CHANNEL_CLOSED + " " + System.currentTimeMillis();
    private String channel;
    private boolean resultCreate;
    private boolean resultChange;
    private TestsBase testsBase;

    @BeforeClass
    void setUp(){
        assertTrue(isWebServerStatus(), "Web сервер не запустился в течение минуты");
        nameChannel = "CHCCH" + System.currentTimeMillis();
        newNameChannel = nameChannel + System.currentTimeMillis();
        testsBase = new TestsBase();
    }

    @BeforeMethod
    void beforeMethod(){
        testsBase.init();
    }

    @Story(value = "Создаём новый закрытый канал")
    @Description(value = "Авторизуемся под пользователем user_1 и создаём новый закрытый канал")
    @Test
    void test_Create_Channel(){
        testsBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(
                createNewChannel(
                        nameChannel,
                        CLIENT_DESCRIPTION_CHANNEL_CLOSED,
                        CLIENT_ITEM_NEW_CHANNEL,
                        CLIENT_TYPE_CHANNEL_CLOSED).
                        isExistComments(nameChannel, true),
                "Канал не найден в списке бесед");
        clickChat(nameChannel);
        assertTrue(isTextInfoClosedChannel(true),
                "Отсутствует надпись Закрытый в разделе 'Информация о канале'");
    }

    @Story(value = "Проверяем, что канал есть в БД postgres")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем:" +
            "1. Появился ли канал в БД postgres" +
            "2. Правильного ли типа канал")
    @Test(dependsOnMethods = {"test_Create_Channel"})
    void test_Check_Exist_Channel_In_BD(){
        softAssert = new SoftAssert();
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
    @Test(priority = 1, dependsOnMethods = {"test_Create_Channel"})
    void test_Show_Closed_Channel_In_MS_After_Create(){
        testsBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(nameChannel, false),
                "Закрытый канал " + nameChannel + " отображается в СУ");
    }

    @Story(value = "Меняем имя, описание и тип канала с закрытого на публичный")
    @Description(value = "Авторизуемся под администратором и меняем тип у закрытого каналана на публичный")
    @Test(priority = 2, dependsOnMethods = {"test_Create_Channel"})
    void test_Edit_Type_With_Closed_On_Public_Channel(){
        testsBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(
                changeDataChannel(
                        nameChannel,false,false,true, CLIENT_TYPE_CHANNEL_PUBLIC).
                        isExistComments(nameChannel, true),
                "Канал не найден в списке бесед после смены типа на публичный");
        clickChat(nameChannel);
        assertTrue(isTextInfoClosedChannel(false),
                "Отображается надпись Закрытый в разделе 'Информация о канале'");
    }

    @Story(value = "Проверяем канал в БД postgres после смены типа")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем:" +
            "1. Остался ли канал в БД postgres" +
            "2. Правильного ли типа канал")
    @Test(dependsOnMethods = {"test_Edit_Type_With_Closed_On_Public_Channel"})
    void test_Check_Exist_Channel_In_BD_After_Change_Type(){
        softAssert = new SoftAssert();
        softAssert.assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel)),
                "Запись о канале " + nameChannel + " не найден в БД postgres");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, nameChannel)).
                        replaceAll(" ",""),
                "0",
                "Тип канала " + nameChannel + " в БД postgres не закрытого типа");
        softAssert.assertAll();
    }

    @Story(value = "Делаем проверенным канал после смены на публичный тип")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы," +
            " делаем публичный канал проверенным")
    @Test(dependsOnMethods = {"test_Edit_Type_With_Closed_On_Public_Channel"})
    void test_Do_Proven_Channel_After_Edit_Type_Closed_Channel(){
        testsBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(nameChannel, true),
                "Канал " + nameChannel + " не найден в списке каналов");
        doTestedChannel(nameChannel);
    }

    @Story(value = "Проверяем канал в БД postgres после получения статуса проверенного")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем:" +
            "1. Остался ли канал в БД postgres" +
            "2. Правильного ли типа канал" +
            "3. Статус проверенного канала")
    @Test(dependsOnMethods = {"test_Do_Proven_Channel_After_Edit_Type_Closed_Channel"})
    void test_Check_Exist_Channel_In_BD_After_Add_Proven(){
        softAssert = new SoftAssert();
        softAssert.assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel)),
                "Запись о канале " + nameChannel + " не найден в БД postgres");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, nameChannel)).
                        replaceAll(" ",""),
                "0",
                "Тип канала " + nameChannel + " в БД postgres не закрытого типа");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckProvedChannel, nameChannel)).
                        replaceAll(" ", ""),
                "1",
                "Канал " + nameChannel + " в БД postgres не проверенный");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем статус проверенного канала, под учётной записью администратора канала")
    @Description(value = "Авторизуемся на клиенте под учётной записью администратора канала." +
            " Проверяем, что у канала появился статус Проверенный")
    @Test(priority = 1, dependsOnMethods = {"test_Do_Proven_Channel_After_Edit_Type_Closed_Channel"})
    void test_Check_Status_Closed_Channel(){
        softAssert = new SoftAssert();
        testsBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
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
    @Test(priority = 2, dependsOnMethods = {"test_Do_Proven_Channel_After_Edit_Type_Closed_Channel"})
    void test_Search_Closed_Channel(){
        softAssert = new SoftAssert();
        testsBase.openClient(CONTACT_NUMBER_7013 + "@ros.chat", false);
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

    @Story(value = "Меняем название и описание канала")
    @Description(value = "Авторизуемся под администратором канала, меняем его название и описание")
    @Test(priority = 3, dependsOnMethods = {"test_Do_Proven_Channel_After_Edit_Type_Closed_Channel"})
    void test_Change_Name_And_Description_Channel(){
        testsBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(
                changeDataChannel(
                        nameChannel,true,true, false,
                        newNameChannel, newDescription).
                        isExistComments(newNameChannel, true),
                "Канал " + newNameChannel +" не найден в списке бесед после изменения имени и описания канала");
        clickChat(newNameChannel);
        assertTrue(isTextInfoClosedChannel(false),
                "Отображается надпись Закрытый в разделе 'Информация о канале'");
    }

    @Story(value = "Проверяем канал в БД postgres после смены имени и описания")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем:" +
            "1. Сменилось ли имя канал в БД postgres" +
            "2. Правильного ли типа канал" +
            "3. Остался ли статус проверенного канала")
    @Test(dependsOnMethods = {"test_Change_Name_And_Description_Channel"})
    void test_Check_Exist_Channel_In_BD_After_Change_Name_And_Description(){
        softAssert = new SoftAssert();
        softAssert.assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, newNameChannel)),
                "Запись о канале " + newNameChannel + " не найден в БД postgres");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, newNameChannel)).
                        replaceAll(" ",""),
                "0",
                "Тип канала " + newNameChannel + " в БД postgres не закрытого типа");
        softAssert.assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckProvedChannel, newNameChannel)).
                        replaceAll(" ", ""),
                "1",
                "Канал " + newNameChannel + " в БД postgres не проверенный");
        softAssert.assertAll();
    }

    @Story(value = "Проверяем, отображается ли публичный канал в СУ")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов")
    @Test(dependsOnMethods = {"test_Change_Name_And_Description_Channel"})
    void test_Show_Public_Channel_In_MS(){
        testsBase.openMS("Администрирование","Каналы");
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
            testsBase.init();
            testsBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
            softAssert = new SoftAssert();
            softAssert.assertTrue(
                    deleteChannel(channel).isExistComments(channel, false),
                    "Канал найден в списке бесед после удаления");
            softAssert.assertFalse(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, channel)),
                    "Запись о канале " + channel + " осталась в БД postgres после удаления");
            softAssert.assertAll();

            testsBase.openMS("Администрирование", "Каналы");
            assertTrue(isShowChannel(channel, false),
                    "Закрытый канал " + channel + " отображается в СУ после удаления");
        }
    }
}
