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
@Feature(value = "Публичный проверенный канал")
public class TestPublicProvenChannel extends ChannelsPage {

    private static String nameChannel;
    private static String newNameChannel;
    private final String newDescription = CLIENT_DESCRIPTION_CHANNEL_PUBLIC_PROVEN + System.currentTimeMillis();
    private final String admin = CLIENT_7005 + "@ros.chat";
    private final String user = CLIENT_7006 + "@ros.chat";
    private String channel = null;
    private static boolean status_create;
    private static boolean status_edit;
    private static boolean status_delete;
    private static boolean status_proven;
    private static TestsBase testsBase = new TestsBase();

    @BeforeAll
    static void setUp(){
        assertTrue(isWebServerStatus(), "Web сервер не запустился в течение минуты");
        nameChannel = "CHPP" + System.currentTimeMillis();
        newNameChannel = nameChannel + System.currentTimeMillis();
        status_create = false;
        status_edit = false;
        status_delete = false;
        status_proven = false;
        testsBase.init();
    }

    @Story(value = "Создаём новый публичный канал")
    @Description(value = "Авторизуемся под пользователем user_1 и создаём новый публичный канал")
    @Test
    @Order(1)
    void test_Create_Channel(){
        testsBase.openClient(admin, false);
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
                "Есть надпись Закрытый в разделе 'Информация о канале'");
        status_create = true;
    }

    @Story(value = "Делаем проверенным публичный канал")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы, делаем " +
            "публичный канал проверенным")
    @Test
    @Order(2)
    void test_Do_Proven_Channel_After_Create_Public_Channel(){
        assertTrue(status_create, "Канал не создан");
        testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Администрирование","Каналы");
        assertTrue(isShowChannel(nameChannel, true),
                "Канал " + nameChannel + " не найден в списке каналов");
        doTestedChannel(nameChannel);
        status_proven = true;
    }

    @Story(value = "Проверяем статус проверенного канала, под учётной записью администратора канала")
    @Description(value = "Авторизуемся на клиенте под учётной записью администратора канала." +
            " Проверяем, что у канала появился статус Проверенный")
    @Test
    @Order(3)
    void test_Check_Status_Closed_Channel(){
        assertTrue(status_create, "Канал не создан");
        assertTrue(status_proven, "Не удалось сделать канал проверенным");
        testsBase.openClient(admin, false);
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
    @Order(4)
    void test_Search_Closed_Channel(){
        assertTrue(status_create, "Канал не создан");
        assertTrue(status_proven, "Не удалось сделать канал проверенным");
        testsBase.openClient(user, false);
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
    @Description(value = "Авторизуемся под администратором канала и меняем название и описание канала. Проверяем, что на" +
            "клиенте отображается новое название и описание канала.")
    @Test
    @Order(5)
    void test_Change_Name_And_Description_Channel(){
        assertTrue(status_create, "Канал не создан");
        testsBase.openClient(admin, false);
        assertTrue(
                changeDataChannel(
                        nameChannel,true,true, false,
                        newNameChannel, newDescription).
                        isExistComments(newNameChannel, true),
                "Канал " + newNameChannel + " не найден в списке бесед после изменения имени и описания канала");
        clickChat(newNameChannel);
        assertTrue(isTextInfoClosedChannel(false),
                "Есть надпись Закрытый в разделе 'Информация о канале'");
        status_edit = true;
    }

    @Story(value = "Проверяем, отображается ли публичный канал в СУ после смены имени и описания")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы и проверяем, отображается ли " +
            "канал в списке каналов после смены имени и описания канала")
    @Test
    @Order(6)
    void test_Show_Public_Channel_In_MS_After_Change(){
        assertTrue(status_create, "Канал не создан");
        assertTrue(status_edit, "Не поменялось имя и/или описание канала");
        testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Администрирование","Каналы");
        assertTrue(isShowChannel(newNameChannel, true),
                "Публичный канал " + newNameChannel + " не отображается в СУ");
    }

    @Story(value = "Удаляем канал")
    @Description(value = "Авторизуемся под администратор канала и удаляем канал." )
    @Test
    @Order(7)
    public void test_Delete_Channel() {
        assertTrue(status_create, "Канал не создан");
        if (status_edit) channel = newNameChannel;
        else channel = nameChannel;
        testsBase.openClient(admin, false);
        Assertions.assertTrue(deleteChannel(channel).isExistComments(channel, false),
                "Канал найден в списке бесед после удаления");
        status_delete = true;
    }

    @Story(value = "Проверяем отображается ли канал в СУ после удаления")
    @Description(value = "Проверяем в СУ, что канал не отображается после удаления канала")
    @Test
    @Order(8)
    void test_Show_Public_Channel_In_MS_After_Delete(){
        assertTrue(status_create, "Канал не создан");
        assertTrue(status_delete,"Канал не удален");
        if (status_edit) channel = newNameChannel;
        else channel = nameChannel;
        testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Администрирование", "Каналы");
        assertTrue(isShowChannel(channel, false),
                "Канал " + channel + " отображается в СУ после удаления");
    }
}
