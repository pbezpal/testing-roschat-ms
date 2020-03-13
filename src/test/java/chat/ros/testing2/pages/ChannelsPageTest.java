package chat.ros.testing2.pages;

import chat.ros.testing2.RecourcesTests;
import chat.ros.testing2.WatcherTests;
import chat.ros.testing2.pages.administration.ChannelsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import static chat.ros.testing2.data.ChannelsData.*;
import static chat.ros.testing2.data.ContactsData.*;

@Epic(value = "Администрирование")
@Feature(value = "Каналы")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(RecourcesTests.class)
@ExtendWith(WatcherTests.class)
public class ChannelsPageTest extends ChannelsPage {

    private String login7012 = CONTACT_NUMBER_7012 + "@ros.chat";
    private String login7013 = CONTACT_NUMBER_7013 + "@ros.chat";

    @Story(value = "Создаём новый публичный канал")
    @Description(value = "Авторизуемся под пользователем 7012 и создаём новый публичный канал")
    @Order(1)
    @Test
    void test_Create_Public_Channel_7012(){
        createNewChannel(login7012, USER_ACCOUNT_PASSWORD, CLIENT_NAME_CHANNEL_PUBLIC, CLIENT_ITEM_NEW_CHANNEL, CLIENT_TYPE_CHANNEL_PUBLIC);
    }

    @Story(value = "Делаем публичному каналу статус Проверенный")
    @Description(value = "Авторизуемся в СУ, переходим в раздел Администрирование->Каналы, делаем публичный канал проверенным")
    @Order(2)
    @Test
    void test_Do_Tested_Channel(){
        doTestedChannel(CLIENT_NAME_CHANNEL_PUBLIC);
    }

    @Story(value = "Проверяем статус публичного канала под учёткой 7012")
    @Description(value = "Авторизуемся на клиенте под учётной записью 7012. Проверяем, что у канала появился статус Проверенный")
    @Order(3)
    @Test
    void test_Check_Status_Public_Tested_Channel_7012(){
        checkStatusTestedChannel(login7012, USER_ACCOUNT_PASSWORD, CLIENT_ITEM_NEW_CHANNEL);
    }

    @Story(value = "Ищем на клиенте 7013 публичный канал")
    @Description(value = "Авторизуемся на клиенте под учётной записью 7013 и вводим в поле поиска имя публичного канала. Проверяем, что у канала статус Проверенный")
    @Order(4)
    @Test
    void test_Search_Public_Channel_7013(){
        searchChannel(login7013, USER_ACCOUNT_PASSWORD, CLIENT_NAME_CHANNEL_PUBLIC, CLIENT_TYPE_CHANNEL_PUBLIC);
    }

    @Story(value = "Создаём новый закрытый канал")
    @Description(value = "Авторизуемся под пользователем 7012 и создаём новый закрытый канал")
    @Order(5)
    @Test
    void test_Create_Closed_Channel_7012(){
        createNewChannel(login7012, USER_ACCOUNT_PASSWORD, CLIENT_NAME_CHANNEL_CLOSED, CLIENT_ITEM_NEW_CHANNEL, CLIENT_TYPE_CHANNEL_CLOSED);
    }

    @Story(value = "Ищем на клиенте 7013 закрытый канал")
    @Description(value = "Авторизуемся на клиенте под учётной записью 7013 и вводим в поле поиска имя закрытого канала. Проверяем, что канал не отображается в списке каналов")
    @Order(6)
    @Test
    void test_Search_Closed_Channel_7013(){
        searchChannel(login7013, USER_ACCOUNT_PASSWORD, CLIENT_NAME_CHANNEL_CLOSED, CLIENT_TYPE_CHANNEL_CLOSED);
    }
}
