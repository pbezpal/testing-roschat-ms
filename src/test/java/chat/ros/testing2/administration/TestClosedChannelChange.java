package chat.ros.testing2.administration;

import chat.ros.testing2.TestsBase;
import chat.ros.testing2.TestsParallelBase;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.administration.ChannelsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.TestHelper.isWebServerStatus;
import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7012;
import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7013;
import static chat.ros.testing2.helpers.AttachToReport.*;
import static data.CommentsData.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Администрирование")
@Feature(value = "Закрытый канал. Изменение данных канала.")
public class TestClosedChannelChange extends ChannelsPage implements TestsParallelBase {

    private static String nameChannel;
    private static String newNameChannel;
    private String newDescription = CLIENT_DESCRIPTION_CHANNEL_CLOSED + " " + System.currentTimeMillis();
    private String channel;
    private boolean resultCreate;
    private boolean resultChange;
    private boolean status = false;
    private boolean status_type = false;
    private boolean status_proven = false;
    private boolean status_edit = false;
    private boolean status_delete = false;
    private static TestsBase testsBase = new TestsBase();

    @BeforeAll
    static void setUp(){
        assertTrue(isWebServerStatus(), "Web сервер не запустился в течение минуты");
        nameChannel = "CHCCH" + System.currentTimeMillis();
        newNameChannel = nameChannel + System.currentTimeMillis();
        testsBase.init();
    }

    @Story(value = "Создаём новый закрытый канал")
    @Description(value = "Авторизуемся под пользователем user_1 и создаём новый закрытый канал")
    @Test
    @Order(1)
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
        status = true;
    }

    @Story(value = "Проверяем, что канал есть в БД postgres")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем:" +
            "1. Появился ли канал в БД postgres" +
            "2. Правильного ли типа канал")
    @Test
    @Order(2)
    void test_Check_Exist_Channel_In_BD(){
        assertTrue(status, "Канал не создан");
        assertAll("Проверяем, есть ли канал в БД",
                () -> assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel)),
                        "Запись о канале " + nameChannel + " не найден в БД postgres"),
                () -> assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, nameChannel)).
                                replaceAll(" ",""),
                        "1",
                        "Тип канала " + nameChannel + " в БД postgres не закрытого типа")
        );

    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после создания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после создания")
    @Test
    @Order(3)
    void test_Show_Closed_Channel_In_MS_After_Create(){
        assertTrue(status, "Канал не создан");
        testsBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(nameChannel, false),
                "Закрытый канал " + nameChannel + " отображается в СУ");
    }

    @Story(value = "Меняем имя, описание и тип канала с закрытого на публичный")
    @Description(value = "Авторизуемся под администратором и меняем тип у закрытого каналана на публичный")
    @Test
    @Order(4)
    void test_Edit_Type_With_Closed_On_Public_Channel(){
        assertTrue(status, "Канал не создан");
        testsBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(
                changeDataChannel(
                        nameChannel,false,false,true, CLIENT_TYPE_CHANNEL_PUBLIC).
                        isExistComments(nameChannel, true),
                "Канал не найден в списке бесед после смены типа на публичный");
        clickChat(nameChannel);
        assertTrue(isTextInfoClosedChannel(false),
                "Отображается надпись Закрытый в разделе 'Информация о канале'");
        status_type = true;
    }

    @Story(value = "Проверяем канал в БД postgres после смены типа")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем:" +
            "1. Остался ли канал в БД postgres" +
            "2. Правильного ли типа канал")
    @Test
    @Order(5)
    void test_Check_Exist_Channel_In_BD_After_Change_Type(){
        assertTrue(status, "Канал не создан");
        assertTrue(status_type, "Тип канала не поменялся");
        assertAll("",
                () -> assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel)),
                "Запись о канале " + nameChannel + " не найден в БД postgres"),
                () -> assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, nameChannel)).
                        replaceAll(" ",""),
                "0",
                "Тип канала " + nameChannel + " в БД postgres не закрытого типа")
        );
    }

    @Story(value = "Делаем проверенным канал после смены на публичный тип")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы," +
            " делаем публичный канал проверенным")
    @Test
    @Order(6)
    void test_Do_Proven_Channel_After_Edit_Type_Closed_Channel(){
        assertTrue(status, "Канал не создан");
        assertTrue(status_type, "Тип канала не поменялся");
        testsBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(nameChannel, true),
                "Канал " + nameChannel + " не найден в списке каналов");
        doTestedChannel(nameChannel);
        status_proven = true;
    }

    @Story(value = "Проверяем канал в БД postgres после получения статуса проверенного")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем:" +
            "1. Остался ли канал в БД postgres" +
            "2. Правильного ли типа канал" +
            "3. Статус проверенного канала")
    @Test
    @Order(7)
    void test_Check_Exist_Channel_In_BD_After_Add_Proven(){
        assertTrue(status, "Канал не создан");
        assertTrue(status_type, "Тип канала не поменялся");
        assertTrue(status_proven, "Не удалось сделать канал проверенным");
        assertAll("",
                () -> assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel)),
                "Запись о канале " + nameChannel + " не найден в БД postgres"),
                () -> assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, nameChannel)).
                        replaceAll(" ",""),
                "0",
                "Тип канала " + nameChannel + " в БД postgres не закрытого типа"),
                () -> assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckProvedChannel, nameChannel)).
                        replaceAll(" ", ""),
                "1",
                "Канал " + nameChannel + " в БД postgres не проверенный")
        );
    }

    @Story(value = "Проверяем статус проверенного канала, под учётной записью администратора канала")
    @Description(value = "Авторизуемся на клиенте под учётной записью администратора канала." +
            " Проверяем, что у канала появился статус Проверенный")
    @Test
    @Order(8)
    void test_Check_Status_Closed_Channel(){
        assertTrue(status, "Канал не создан");
        assertTrue(status_type, "Тип канала не поменялся");
        assertTrue(status_proven, "Не удалось сделать канал проверенным");
        testsBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        clickItemComments();
        assertAll("",
                () -> assertTrue(isStatusTestedChannelListChat(nameChannel),
                "Отсутствует статус Проверенный у канала в разделе Беседы"),
                () -> assertTrue(isStatusTestedChannelMainHeader(nameChannel),
                "Отсутствует статус Проверенный в заголовке канала"),
                () -> assertTrue(isStatusTestedInfoChannel(nameChannel),
                "Отсутствует статус Проверенный в разделе информация о канале")
        );
    }

    @Story(value = "Проверяем под пользователем, что у канала статус проверенного")
    @Description(value = "Авторизуемся на клиенте под учётной записью пользователя и вводим в поле поиска имя" +
            " публичного канала. Проверяем, что у канала статус Проверенный")
    @Test
    @Order(9)
    void test_Search_Closed_Channel(){
        assertTrue(status, "Канал не создан");
        assertTrue(status_type, "Не сменилось имя и описание канала");
        assertTrue(status_proven, "Не удалось сделать канал проверенным");
        testsBase.openClient(CONTACT_NUMBER_7013 + "@ros.chat", false);
        assertTrue(searchChannel(nameChannel, CLIENT_TYPE_CHANNEL_PUBLIC),
                "Канал не найден");
        assertAll("",
                () -> assertTrue(isStatusTestedChannelListChat(nameChannel),
                "Отсутствует статус Проверенный у канала в разделе Беседы"),
                () -> assertTrue(isStatusTestedChannelMainHeader(nameChannel),
                "Отсутствует статус Проверенный в заголовке канала"),
                () -> assertTrue(isStatusTestedInfoChannel(nameChannel),
                "Отсутствует статус Проверенный в разделе информация о канале")
        );
    }

    @Story(value = "Меняем название и описание канала")
    @Description(value = "Авторизуемся под администратором канала, меняем его название и описание")
    @Test
    @Order(10)
    void test_Change_Name_And_Description_Channel(){
        assertTrue(status, "Канал не создан");
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
        status_edit = true;
    }

    @Story(value = "Проверяем канал в БД postgres после смены имени и описания")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем:" +
            "1. Сменилось ли имя канал в БД postgres" +
            "2. Правильного ли типа канал" +
            "3. Остался ли статус проверенного канала")
    @Test
    @Order(11)
    void test_Check_Exist_Channel_In_BD_After_Change_Name_And_Description(){
        assertTrue(status, "Канал не создан");
        assertTrue(status_edit, "Не поменялось имя и/или описание канала");
        assertTrue(status_proven, "Не удалось сделать канал проверенным");
        assertAll("",
                () -> assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, newNameChannel)),
                "Запись о канале " + newNameChannel + " не найден в БД postgres"),
                () -> assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, newNameChannel)).
                        replaceAll(" ",""),
                "0",
                "Тип канала " + newNameChannel + " в БД postgres не закрытого типа"),
                () -> assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckProvedChannel, newNameChannel)).
                        replaceAll(" ", ""),
                "1",
                "Канал " + newNameChannel + " в БД postgres не проверенный")
        );
    }

    @Story(value = "Проверяем, отображается ли публичный канал в СУ")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов")
    @Test
    @Order(12)
    void test_Show_Public_Channel_In_MS(){
        assertTrue(status, "Канал не создан");
        assertTrue(status_edit, "Не поменялось имя и/или описание канала");
        assertTrue(status_proven, "Не удалось сделать канал проверенным");
        testsBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(newNameChannel, true),
                "Публичный канал " + newNameChannel + " не отображается в СУ");
    }

    @Story(value = "Удаляем канал")
    @Description(value = "Авторизуемся под пользователем администратором канала и удаляем канал." )
    @Test
    @Order(13)
    public void test_Delete_Channel() {
        assertTrue(status, "Канал не создан");
        if (status_edit) channel = newNameChannel;
        else channel = nameChannel;
        testsBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(deleteChannel(channel).isExistComments(channel, false),
                        "Канал найден в списке бесед после удаления");
        status_delete = true;
    }

    @Story(value = "Проверяем канал в БД postgres после удаления")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем запись о канале в БД")
    @Test
    @Order(14)
    void test_Check_Exist_Channel_In_BD_After_Delete() {
        assertTrue(status, "Канал не создан");
        assertTrue(status_delete,"Канал не удален");
        assertFalse(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, channel)),
                "Запись о канале " + channel + " осталась в БД postgres после удаления");
    }

    @Story(value = "Проверяем отображается ли канал в СУ после удаления")
    @Description(value = "Проверяем в СУ, что канал не отображается после удаления канала")
    @Test
    @Order(15)
    void test_Show_Public_Channel_In_MS_After_Delete(){
        assertTrue(status, "Канал не создан");
        assertTrue(status_delete,"Канал не удален");
        testsBase.openMS("Администрирование", "Каналы");
        assertTrue(isShowChannel(channel, false),
                "Закрытый канал " + channel + " отображается в СУ после удаления");
    }
}
