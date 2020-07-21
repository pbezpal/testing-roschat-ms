package chat.ros.testing2.administration;

import chat.ros.testing2.TestsBase;
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
import static chat.ros.testing2.data.HelperData.commandDBCheckChannel;
import static chat.ros.testing2.data.HelperData.commandDBCheckTypeChannel;
import static data.CommentsData.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Администрирование")
@Feature(value = "Закрытый канал")
public class TestClosedChannel extends ChannelsPage {

    private static String nameChannel;
    private static String newNameChannel;
    private String channel;
    private String newDescription = CLIENT_DESCRIPTION_CHANNEL_CLOSED + " " + System.currentTimeMillis();
    private boolean status = false;
    private boolean status_edit = false;
    private boolean status_delete = false;
    private static TestsBase testsBase = new TestsBase();

    @BeforeAll
    static void setUp(){
        assertTrue(isWebServerStatus(), "Web сервер не запустился в течение минуты");
        testsBase.init();
        nameChannel = "CHC" + System.currentTimeMillis();
        newNameChannel = nameChannel + System.currentTimeMillis();
        testsBase = new TestsBase();
    }

    @Story(value = "Создаём новый закрытый канал")
    @Description(value = "Авторизуемся на клиенте и создаём новый закрытый канал. Проверяем, что канал был создан.")
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
        assertAll("Проверяем, есть и канал в БД",
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

    @Story(value = "Меняем название и описание канала")
    @Description(value = "Авторизуемся под администратором канала и меняем название и описание канала. Проверяем, что на" +
            "клиенте отображается новое название и описание канала.")
    @Test
    @Order(4)
    void test_Change_Name_And_Description_Channel(){
        assertTrue(status, "Канал не создан");
        testsBase.openClient(CONTACT_NUMBER_7012 + "@ros.chat", false);
        assertTrue(changeDataChannel(
                nameChannel,true,true, false,
                        newNameChannel, newDescription).
                        isExistComments(newNameChannel, true),
                "Канал " + newNameChannel + " не найден в списке бесед после изменения имени и описания канала");
        clickChat(newNameChannel);
        assertTrue(isTextInfoClosedChannel(true),
                "Нет надписи Закрытый в разделе 'Информация о канале'");
        status_edit = true;

    }

    @Story(value = "Проверяем канал есть в БД postgres после смены имени и описания")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем:" +
            "1. Появился ли канал в БД postgres" +
            "2. Правильного ли типа канал")
    @Test
    @Order(5)
    void test_Check_Exist_Channel_In_BD_After_Change_Name_And_Description(){
        assertTrue(status, "Канал не создан");
        assertTrue(status_edit, "Не поменялось имя и/или описание канала");
        assertAll("Проверяем, сохраниись ли изменения БД",
                () ->assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, newNameChannel)),
                        "Запись о канале " + newNameChannel + " не найден в БД postgres"),
                () -> assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, newNameChannel)).
                        replaceAll(" ",""),
                "1",
                "Тип канала " + newNameChannel + " в БД postgres не закрытого типа")
        );
    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после смены названия и описания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после смены названия и описания")
    @Test
    @Order(6)
    void test_Show_Closed_Channel_In_MS_After_Change(){
        assertTrue(status, "Канал не создан");
        assertTrue(status_edit, "Не поменялось имя и/или описание канала");
        testsBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(newNameChannel, false),
                "Закрытый канал " + newNameChannel + " отображается в СУ после смены названия и описания");
    }

    @Story(value = "Удаляем канал")
    @Description(value = "1. Авторизуемся под пользователем администратором канала и удаляем канал. ")
    @Test
    @Order(7)
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
    @Order(8)
    void test_Check_Exist_Channel_In_BD_After_Delete() {
        assertTrue(status, "Канал не создан");
        assertTrue(status_delete,"Канал не удален");
        assertFalse(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, channel)),
                "Запись о канале " + channel + " осталась в БД postgres после удаления");
    }

    @Story(value = "Проверяем отображается ли канал в СУ после удаления")
    @Description(value = "Проверяем в СУ, что канал не отображается после удаления канала")
    @Test
    @Order(9)
    void test_Show_Closed_Channel_In_MS_After_Delete(){
        assertTrue(status, "Канал не создан");
        assertTrue(status_delete,"Канал не удален");
            testsBase.openMS("Администрирование", "Каналы");
            assertTrue(isShowChannel(channel, false),
                    "Закрытый канал " + channel + " отображается в СУ после удаления");
    }
}
