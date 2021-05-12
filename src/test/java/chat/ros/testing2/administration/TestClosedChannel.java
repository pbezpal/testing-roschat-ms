package chat.ros.testing2.administration;

import chat.ros.testing2.TestsBase;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.server.administration.ChannelsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.TestHelper.isWebServerStatus;
import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;
import static data.CommentsData.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Администрирование")
@Feature(value = "Закрытый канал")
public class TestClosedChannel extends ChannelsPage {

    private static String nameChannel;
    private static String newNameChannel;
    private String channel = null;
    private final String login = CLIENT_7000 + "@ros.chat";
    private final String newDescription = CLIENT_DESCRIPTION_CHANNEL_CLOSED + " " + System.currentTimeMillis();
    private static boolean status_create;
    private static boolean status_edit;
    private static boolean status_delete;
    private static TestsBase testsBase = new TestsBase();

    @BeforeAll
    static void setUp(){
        assertTrue(isWebServerStatus(), "Web сервер не запустился в течение минуты");
        testsBase.init();
        nameChannel = "CHC" + System.currentTimeMillis();
        newNameChannel = nameChannel + System.currentTimeMillis();
        testsBase = new TestsBase();
        status_create = false;
        status_edit = false;
        status_delete = false;
    }

    @Story(value = "Создаём новый закрытый канал")
    @Description(value = "Авторизуемся на клиенте и создаём новый закрытый канал. Проверяем, что канал был создан.")
    @Test
    @Order(1)
    void test_Create_Channel(){
        testsBase.openClient(login, false);
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
        status_create = true;
    }

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после создания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после создания")
    @Test
    @Order(2)
    void test_Show_Closed_Channel_In_MS_After_Create(){
        assertTrue(status_create, "Канал не создан");
        testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Администрирование","Каналы");
        assertTrue(isShowChannel(nameChannel, false),
                "Закрытый канал " + nameChannel + " отображается в СУ");
    }

    @Story(value = "Меняем название и описание канала")
    @Description(value = "Авторизуемся под администратором канала и меняем название и описание канала. Проверяем, что на" +
            "клиенте отображается новое название и описание канала.")
    @Test
    @Order(3)
    void test_Change_Name_And_Description_Channel(){
        assertTrue(status_create, "Канал не создан");
        testsBase.openClient(login, false);
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

    @Story(value = "Проверяем, отображается ли закрытый канал в СУ после смены названия и описания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "закрытый канал в списке каналов после смены названия и описания")
    @Test
    @Order(4)
    void test_Show_Closed_Channel_In_MS_After_Change(){
        assertTrue(status_create, "Канал не создан");
        assertTrue(status_edit, "Не поменялось имя и/или описание канала");
        testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Администрирование","Каналы");
        assertTrue(isShowChannel(newNameChannel, false),
                "Закрытый канал " + newNameChannel + " отображается в СУ после смены названия и описания");
    }

    @Story(value = "Удаляем канал")
    @Description(value = "1. Авторизуемся под пользователем администратором канала и удаляем канал. ")
    @Test
    @Order(5)
    void test_Delete_Channel() {
        assertTrue(status_create, "Канал не создан");
        if (status_edit) channel = newNameChannel;
        else channel = nameChannel;
        testsBase.openClient(login, false);
        assertTrue(deleteChannel(channel).isExistComments(channel, false),
                        "Канал найден в списке бесед после удаления");
        status_delete = true;
    }

    @Story(value = "Проверяем отображается ли канал в СУ после удаления")
    @Description(value = "Проверяем в СУ, что канал не отображается после удаления канала")
    @Test
    @Order(6)
    void test_Show_Closed_Channel_In_MS_After_Delete(){
        assertTrue(status_create, "Канал не создан");
        assertTrue(status_delete,"Канал не удален");
        if (status_edit) channel = newNameChannel;
        else channel = nameChannel;
        testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Администрирование", "Каналы");
        assertTrue(isShowChannel(channel, false),
                "Канал " + channel + " отображается в СУ после удаления");
    }
}
