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
import static data.CommentsData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(WatcherTests.class)
@Epic(value = "Администрирование")
@Feature(value = "Публичный канал")
public class TestPublicChannel extends ChannelsPage implements TestsParallelBase {

    private static String nameChannel;
    private static String newNameChannel;
    private String newDescription = CLIENT_DESCRIPTION_CHANNEL_PUBLIC_PROVEN + System.currentTimeMillis();
    private final String login = CLIENT_USER_C + "@ros.chat";
    private String channel = null;
    private static boolean status_create;
    private static boolean status_edit;
    private static boolean status_delete;
    private static TestsBase testsBase = new TestsBase();

    @BeforeAll
    static void setUp(){
        assertTrue(isWebServerStatus(), "Web сервер не запустился в течение минуты");
        nameChannel = "CHP" + System.currentTimeMillis();
        newNameChannel = nameChannel + System.currentTimeMillis();
        status_create = false;
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

    @Story(value = "Проверяем, что канал есть в БД postgres")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем:" +
            "1. Появился ли канал в БД postgres" +
            "2. Правильного ли типа канал")
    @Test
    @Order(2)
    void test_Check_Exist_Channel_In_BD(){
        assertTrue(status_create, "Канал не создан");
        assertAll("Проверяем канал в БД Postgres",
                () -> assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, nameChannel)),
                        "Запись о канале " + nameChannel + " не найден в БД postgres"),
                () -> assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, nameChannel)).
                                replaceAll(" ",""),
                        "0",
                        "Тип канала " + nameChannel + " в БД postgres не закрытого типа")
        );
    }

    @Story(value = "Проверяем, отображается ли публичный канал в СУ")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов")
    @Test
    @Order(3)
    void test_Show_Public_Channel_In_MS(){
        assertTrue(status_create, "Канал не создан");
        testsBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(nameChannel, true),
                "Публичный канал " + nameChannel + " не отображается в СУ");
    }

    @Story(value = "Меняем название и описание канала")
    @Description(value = "Авторизуемся под администратором канала и меняем название и описание канала. Проверяем, что на" +
            "клиенте отображается новое название и описание канала.")
    @Test
    @Order(4)
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
        assertTrue(isTextInfoClosedChannel(false),
                "Есть надписи Закрытый в разделе 'Информация о канале'");
        status_edit = true;

    }

    @Story(value = "Проверяем канал в БД postgres после смены имени и описания")
    @Description(value = "Подключаемся к серверу по протоколу ssh и проверяем:" +
            "1. Есть ли новое имя канал в БД postgres" +
            "2. Правильного ли типа канал")
    @Test
    @Order(5)
    void test_Check_Exist_Channel_In_BD_After_Change_Name_And_Description(){
        assertTrue(status_create, "Канал не создан");
        assertTrue(status_edit, "Не поменялось имя и/или описание канала");
        assertAll("Проверяем, сохраниись ли изменения БД",
                () ->assertTrue(SSHManager.isCheckQuerySSH(String.format(commandDBCheckChannel, newNameChannel)),
                        "Запись о канале " + newNameChannel + " не найден в БД postgres"),
                () -> assertEquals(SSHManager.getQuerySSH(String.format(commandDBCheckTypeChannel, newNameChannel)).
                                replaceAll(" ",""),
                        "0",
                        "Тип канала " + newNameChannel + " в БД postgres не публичного типа")
        );
    }

    @Story(value = "Проверяем, отображается ли публичный канал в СУ после смены имени и описания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов после смены имени и описания канала")
    @Test
    @Order(6)
    void test_Show_Public_Channel_In_MS_After_Change(){
        assertTrue(status_create, "Канал не создан");
        assertTrue(status_edit, "Не поменялось имя и/или описание канала");
        testsBase.openMS("Администрирование","Каналы");
        assertTrue(isShowChannel(newNameChannel, true),
                "Публичный канал " + newNameChannel + " не отображается в СУ");
    }

    @Story(value = "Удаляем канал")
    @Description(value = "1. Авторизуемся под пользователем администратором канала и удаляем канал. ")
    @Test
    @Order(7)
    void test_Delete_Channel() {
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
    @Order(8)
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
    @Order(9)
    void test_Show_Closed_Channel_In_MS_After_Delete(){
        assertTrue(status_create, "Канал не создан");
        assertTrue(status_delete,"Канал не удален");
        if (status_edit) channel = newNameChannel;
        else channel = nameChannel;
        testsBase.openMS("Администрирование", "Каналы");
        assertTrue(isShowChannel(channel, false),
                "Канал " + channel + " отображается в СУ после удаления");
    }
}
