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

import static chat.ros.testing2.data.ChannelsData.CLIENT_ITEM_NEW_CHANNEL;
import static chat.ros.testing2.data.ChannelsData.CLIENT_NAME_CHANNEL;
import static chat.ros.testing2.data.ContactsData.*;

@Epic(value = "Администрирование")
@Feature(value = "Каналы")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(RecourcesTests.class)
@ExtendWith(WatcherTests.class)
public class ChannelsPageTest extends ChannelsPage {

    private String login7012 = CONTACT_NUMBER_7012 + "@ros.chat";

    @Story(value = "Добавляем новый канал")
    @Description(value = "Добавляем новый канал на клиенте и проверяем, что канал был создан")
    @Order(1)
    @Test
    void test_Create_Channel(){

        createNewChannel(login7012, USER_ACCOUNT_PASSWORD, CLIENT_NAME_CHANNEL, CLIENT_ITEM_NEW_CHANNEL);
    }

    @Story(value = "Делаем канал проверенным")
    @Description(value = "Проверяем, что канал отображается в системе управления и делаем его проверенным")
    @Order(2)
    @Test
    void test_Tested_Channel(){
        doTestedChannel(CLIENT_NAME_CHANNEL);
    }

    @Story(value = "Проверяем, что у клиента 7012 у канала появился статус Проверенный")
    @Description(value = "Авторизуемся на клиенте под учётной записью 7012 и проверяем, что у канала появился статус Проверенный")
    @Order(3)
    @Test
    void test_Check_Status_Tested_Channel_7012(){
        checkStatusTestedChannel(login7012, USER_ACCOUNT_PASSWORD, CLIENT_ITEM_NEW_CHANNEL);
    }

    @Story(value = "Проверяем, что у клиента 7013 у канала появился статус Проверенный")
    @Description(value = "Авторизуемся на клиенте под учётной записью 7013 и проверяем, что у канала появился статус Проверенный")
    @Order(4)
    @Test
    void test_Check_Status_Tested_Channel_7013(){
        String login7013 = CONTACT_NUMBER_7013 + "@ros.chat";
        checkStatusTestedChannel(login7013, USER_ACCOUNT_PASSWORD, CLIENT_ITEM_NEW_CHANNEL);
    }
}
