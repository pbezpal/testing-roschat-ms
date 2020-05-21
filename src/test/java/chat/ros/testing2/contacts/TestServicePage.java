package chat.ros.testing2.contacts;

import chat.ros.testing2.TestSuiteBase;
import chat.ros.testing2.server.contacts.ContactsPage;
import chat.ros.testing2.server.contacts.UserPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static chat.ros.testing2.data.ContactsData.*;
import static chat.ros.testing2.data.SettingsData.INTEGRATION_SERVICE_TETRA_NAME;
import static org.testng.Assert.assertTrue;

@Epic(value = "Справочник")
@Feature(value = "Пользователи")
public class TestServicePage extends ContactsPage implements TestSuiteBase {

    private UserPage userPage;
    private SoftAssert softAssert;

    @Story(value = "Добавляем сервис Рация у контакта 7012")
    @Description(value = "Переходим в раздель Пользователь контакта 7012 и добавляем сервис Рация")
    @Test
    void test_Add_Service_Radio_Contact_7012(){
        userPage = actionsContact(CONTACT_NUMBER_7012);
        userPage.addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_RADIO);
        assertTrue(userPage.isShowService("h4",USER_SERVICES_TYPE_RADIO), "Сервис " + USER_SERVICES_TYPE_RADIO + " не был добавлен");
    }

    @Story(value = "Добавляем сервис SIP у контакта 7012")
    @Description(value = "Переходим в раздель Пользователь контакта 7012 и добавляем сервис SIP")
    @Test
    void test_Add_Service_SIP_Contact_7012(){
        softAssert = new SoftAssert();
        userPage = sendInputSearchContact(CONTACT_NUMBER_7012).clickContact(CONTACT_NUMBER_7012);
        userPage.addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_SIP, CONTACT_NUMBER_7012);
        softAssert.assertTrue(
                userPage.isShowService("h4",USER_SERVICES_TYPE_SIP),
                "Сервис " + USER_SERVICES_TYPE_RADIO + " не был добавлен");
        softAssert.assertTrue(
                userPage.isShowService("span",CONTACT_NUMBER_7012),
                "Не отображается SIP номер " + CONTACT_NUMBER_7012);
        softAssert.assertAll();
    }

    @Story(value = "Добавляем сервис Рация у контакта 7012")
    @Description(value = "Переходим в раздель Пользователь контакта 7012 и добавляем сервис Рация")
    @Test(groups = {"Service Tetra"})
    void test_Add_Service_Tetra_Contact_7012(){
        userPage = sendInputSearchContact(CONTACT_NUMBER_7012).clickContact(CONTACT_NUMBER_7012);
        userPage.addServices(USER_SERVICES_ITEM_MENU, USER_SERVICES_TYPE_TETRA, INTEGRATION_SERVICE_TETRA_NAME, "1");
        assertTrue(userPage.isShowService("h4",USER_SERVICES_TYPE_TETRA), "Сервис " + USER_SERVICES_TYPE_TETRA + " не был добавлен");
    }

    @Story(value = "Проверяем количество пользователей")
    @Description(value = "Переходим в раздел Справочник и проверяем, что количество контактов больше 700")
    @Test(groups = {"After Sync"})
    void test_Count_Contacts_After_Sync_Integrations(){
        assertTrue(countContacts() > 700, "Контакты из Офис-Монитора и/или Active Directory не были синхронизированы");
    }

}
