package chat.ros.testing2.administration;

import chat.ros.testing2.TestsBase;
import chat.ros.testing2.TestsParallelBase;
import chat.ros.testing2.helpers.SSHManager;
import chat.ros.testing2.server.administration.ChannelsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static chat.ros.testing2.TestHelper.isWebServerStatus;
import static chat.ros.testing2.data.ContactsData.CONTACT_NUMBER_7012;
import static data.CommentsData.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic(value = "Администрирование")
@Feature(value = "Публичный канал")
public class TestPublicChannel extends ChannelsPage implements TestsParallelBase {

    private static String nameChannel;
    private static String newNameChannel;
    private String newDescription = CLIENT_DESCRIPTION_CHANNEL_PUBLIC_PROVEN + System.currentTimeMillis();
    private String channel;
    private boolean status = false;
    private boolean status_edit = false;
    private boolean status_delete = false;
    private static TestsBase testsBase = new TestsBase();

    @BeforeAll
    static void setUp(){
        assertTrue(isWebServerStatus(), "Web сервер не запустился в течение минуты");
        nameChannel = "CHP" + System.currentTimeMillis();
        newNameChannel = nameChannel + System.currentTimeMillis();
        testsBase.init();
    }

    @Story(value = "Создаём новый публичный канал")
    @Description(value = "Авторизуемся под пользователем user_1 и создаём новый публичный канал")
    @Test
    @Order(1)
    void test_Create_Channel(){
        testsBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(
                createNewChannel(
                        nameChannel,
                        CLIENT_DESCRIPTION_CHANNEL_PUBLIC_PROVEN,
                        CLIENT_ITEM_NEW_CHANNEL,
                        CLIENT_TYPE_CHANNEL_PUBLIC).
                        isExistComments(nameChannel, true),
                "Канал не найден в списке бесед");
        clickChat(nameChannel);
        assertTrue(isTextInfoClosedChannel(false),
                "Присутствует надпись Закрытый в разделе 'Информация о канале'");
        status = true;
    }

    @Story(value = "Проверяем, отображается ли публичный канал в СУ")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов")
    @Test
    @Order(2)
    void test_Show_Public_Channel_In_MS(){
        assertTrue(status, "Канал не создан");
        testsBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(nameChannel, true),
                "Публичный канал " + nameChannel + " не отображается в СУ");
    }

    @Story(value = "Меняем название и описание канала")
    @Description(value = "Авторизуемся под администратором канала и меняем название и описание канала. Проверяем, что на" +
            "клиенте отображается новое название и описание канала.")
    @Test
    @Order(3)
    void test_Change_Name_And_Description_Channel(){
        assertTrue(status, "Канал не создан");
        testsBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(
                changeDataChannel(
                        nameChannel,true,true, false,
                        newNameChannel, newDescription).
                        isExistComments(newNameChannel, true),
                "Канал " + newNameChannel + " не найден в списке бесед после изменения имени и описания канала");
        clickChat(newNameChannel);
        assertTrue(isTextInfoClosedChannel(false),
                "Есть надписи Закрытый в разделе 'Информация о канале'");
        status_edit = true;

    }

    @Story(value = "Проверяем, отображается ли публичный канал в СУ после смены имени и описания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов после смены имени и описания канала")
    @Test
    @Order(4)
    void test_Show_Public_Channel_In_MS_After_Change(){
        assertTrue(status, "Канал не создан");
        assertTrue(status_edit, "Не поменялось имя и/или описание канала");
        testsBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(newNameChannel, true),
                "Публичный канал " + newNameChannel + " не отображается в СУ");
    }

    @Story(value = "Удаляем канал")
    @Description(value = "1. Авторизуемся под пользователем администратором канала и удаляем канал. ")
    @Test
    @Order(5)
    void test_Delete_Channel() {
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
    @Order(6)
    void test_Check_Exist_Channel_In_BD_After_Delete() {
        assertTrue(status, "Канал не создан");
        assertTrue(status_delete,"Канал не удален");
        assertFalse(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, channel)),
                "Запись о канале " + channel + " осталась в БД postgres после удаления");
    }

    @Story(value = "Проверяем отображается ли канал в СУ после удаления")
    @Description(value = "Проверяем в СУ, что канал не отображается после удаления канала")
    @Test
    @Order(7)
    void test_Show_Closed_Channel_In_MS_After_Delete(){
        assertTrue(status, "Канал не создан");
        assertTrue(status_delete,"Канал не удален");
        testsBase.openMS("Администрирование", "Каналы");
        assertTrue(isShowChannel(channel, false),
                "Закрытый канал " + channel + " отображается в СУ после удаления");
    }
}
