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
import static chat.ros.testing2.data.HelperData.commandDBCheckChannel;
import static chat.ros.testing2.data.HelperData.commandDBCheckTypeChannel;
import static data.CommentsData.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Администрирование")
@Feature(value = "Публичный канал. Изменение данных канала.")
public class TestPublicChannelChange extends ChannelsPage {

    private static String nameChannel;
    private static String newNameChannel;
    private final String newDescription = CLIENT_DESCRIPTION_CHANNEL_CLOSED + System.currentTimeMillis();
    private final String login = CLIENT_USER_D + "@ros.chat";
    private String channel = null;
    private static boolean status_create;
    private static boolean status_type;
    private static boolean status_edit;
    private static boolean status_delete;
    private static TestsBase testsBase = new TestsBase();

    @BeforeAll
    static void setUp(){
        assertTrue(isWebServerStatus(), "Web сервер не запустился в течение минуты");
        nameChannel = "CHPCH" + System.currentTimeMillis();
        newNameChannel = nameChannel + System.currentTimeMillis();
        status_create = false;
        status_type = false;
        status_edit = false;
        status_delete = false;
        testsBase.init();
    }

    @Story(value = "Создаём новый публичный канал")
    @Description(value = "Авторизуемся под пользователем user_1 и создаём новый публичный канал")
    @Test
    @Order(1)
    void test_Create_Channel(){
        testsBase.openClient(login, false);
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
        status_create = true;
    }

    @Story(value = "Меняем тип канала с публичного на закрытый")
    @Description(value = "Авторизуемся под администратором канала и меняем тип с публичного на закрытый канал")
    @Test
    @Order(2)
    void test_Edit_Type_With_Public_On_Closed_Channel(){
        assertTrue(status_create, "Канал не создан");
        testsBase.openClient(login, false);
        assertTrue(
                changeDataChannel(nameChannel,false,false,true,
                        CLIENT_TYPE_CHANNEL_CLOSED).
                        isExistComments(nameChannel, true),
                "Канал не найден в списке бесед");
        assertTrue(isTextInfoClosedChannel(true),
                "Не отображается тип канала Закрытый в разделе 'Информация о канале'");
        status_type = true;
    }

    @Story(value = "Проверяем канал в БД postgres после смены типа")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем:" +
            "1. Остался ли канал в БД postgres" +
            "2. Правильного ли типа канал")
    @Test
    @Order(3)
    void test_Check_Exist_Channel_In_BD_After_Change_Type(){
        assertTrue(status_create, "Канал не создан");
        assertTrue(status_type, "Тип канала не поменялся");
        assertAll("Проверяем канал в БД Postgres, после смены типа",
                () -> assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel)),
                        "Запись о канале " + nameChannel + " не найден в БД postgres"),
                () -> assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, nameChannel)).
                                replaceAll(" ",""),
                        "1",
                        "Тип канала " + nameChannel + " в БД postgres не закрытого типа")
        );
    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после изменения типа канала")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после изменения типа с публичного на закрытый")
    @Test
    @Order(4)
    void test_Show_Public_Channel_In_MS_After_Change_Type(){
        assertTrue(status_create, "Канал не создан");
        assertTrue(status_type, "Тип канала не поменялся");
        testsBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(nameChannel, false),
                "Закрытый канал " + nameChannel + " отображается в СУ");
    }

    @Story(value = "Меняем название и описание канала")
    @Description(value = "Авторизуемся под администратором канала и меняем название и описание канала. Проверяем, что на" +
            "клиенте отображается новое название и описание канала.")
    @Test
    @Order(5)
    void test_Change_Name_And_Description_Channel(){
        assertTrue(status_create, "Канал не создан");
        testsBase.openClient(login, false);
        assertTrue(
                changeDataChannel(
                        nameChannel,true,true, false,
                        newNameChannel, newDescription).
                        isExistComments(newNameChannel, true),
                "Канал " + newNameChannel + " не найден в списке бесед после изменения имени и описания канала");
        clickChat(newNameChannel);
        assertTrue(isTextInfoClosedChannel(true),
                "Нет надписи Закрытый в разделе 'Информация о канале'");
        status_edit = true;
    }

    @Story(value = "Проверяем канал в БД postgres после смены имени и описания")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем:" +
            "1. Появился ли канал в БД postgres" +
            "2. Правильного ли типа канал")
    @Test
    @Order(6)
    void test_Check_Exist_Channel_In_BD_After_Change_Name_And_Description(){
        assertTrue(status_create, "Канал не создан");
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
    @Order(7)
    void test_Show_Closed_Channel_In_MS_After_Change(){
        testsBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(newNameChannel, false),
                "Закрытый канал " + newNameChannel + " отображается в СУ после смены имени " +
                        "и описания канала");
    }

    @Story(value = "Удаляем канал")
    @Description(value = "Авторизуемся под администратором канала и удаляем канал." )
    @Test
    @Order(8)
    public void test_Delete_Channel() {
        assertTrue(status_create, "Канал не создан");
        if (status_edit) channel = newNameChannel;
        else channel = nameChannel;
        testsBase.openClient(login, false);
        assertTrue(deleteChannel(channel).isExistComments(channel, false),
                "Канал найден в списке бесед после удаления");
        status_delete = true;
    }

    @Story(value = "Проверяем канал в БД postgres после удаления")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем запись о канале в БД")
    @Test
    @Order(9)
    void test_Check_Exist_Channel_In_BD_After_Delete() {
        assertTrue(status_create, "Канал не создан");
        assertTrue(status_delete,"Канал не удален");
        if (status_edit) channel = newNameChannel;
        else channel = nameChannel;
        assertFalse(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, channel)),
                "Запись о канале " + channel + " осталась в БД postgres после удаления");
    }

    @Story(value = "Проверяем отображается ли канал в СУ после удаления")
    @Description(value = "Проверяем в СУ, что канал не отображается после удаления канала")
    @Test
    @Order(10)
    void test_Show_Public_Channel_In_MS_After_Delete(){
        assertTrue(status_create, "Канал не создан");
        assertTrue(status_delete,"Канал не удален");
        if (status_edit) channel = newNameChannel;
        else channel = nameChannel;
        testsBase.openMS("Администрирование", "Каналы");
        assertTrue(isShowChannel(channel, false),
                "Канал " + channel + " отображается в СУ после удаления");
    }
}
