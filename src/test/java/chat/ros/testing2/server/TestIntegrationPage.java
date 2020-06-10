package chat.ros.testing2.server;

import chat.ros.testing2.TestSuiteBase;
import chat.ros.testing2.TestsBase;
import chat.ros.testing2.server.settings.integration.ActiveDirectoryPage;
import chat.ros.testing2.server.settings.integration.IntegrationPage;
import chat.ros.testing2.server.settings.integration.TetraPage;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.Assert.assertTrue;

@Epic(value = "Настройки")
@Feature(value = "Интеграция")
public class TestIntegrationPage implements IntegrationPage, TestSuiteBase {

    private TetraPage tetraPage;
    private ActiveDirectoryPage activeDirectoryPage;
    private Map<String, String> mapInputValueTetra = new HashMap() {{
        put("Название", INTEGRATION_SERVICE_TETRA_NAME);
        put("Описание", INTEGRATION_SERVICE_TETRA_DESCRIPTION);
    }};
    private Map<String, String> mapInputValueAD = new HashMap() {{
        put("Имя сервера", INTEGRATION_SERVICE_AD_SERVER);
        put("Порт", INTEGRATION_SERVICE_AD_PORT);
        put("Корневой элемент (Base DN)", INTEGRATION_SERVICE_AD_BASE_DN);
        put("Имя пользователя (Bind DN)", INTEGRATION_SERVICE_AD_USERNAME);
        put("Пароль", INTEGRATION_SERVICE_AD_PASSWORD);
    }};

    @BeforeMethod
    public void beforeTest(Method method){
        if(method.toString().contains("Open_Page")) TestsBase.getInstance().openMS("/settings/integration");
        else TestsBase.getInstance().openMS("Настройки","Интеграция");
    }

    @Story(value = "Добавляем сервис МиниКом TETRA")
    @Description(value = "Переходим в раздел Интеграция, добавляем сервис МиниКом TETRA и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test(groups = {"Tetra"})
    void test_Add_Service_Tetra(){
        tetraPage = (TetraPage) addIntegrationService(INTEGRATION_SERVICE_TETRA_TYPE);
        tetraPage.clickServiceType(INTEGRATION_SERVICE_TETRA_TYPE);
        assertTrue(tetraPage.addTetraServer(mapInputValueTetra), "Сервис тетра не был добавлен");
    }

    @Story(value = "Добавляем сервис Active Directory")
    @Description(value = "Переходим в раздел Интеграция, добавляем и настраиваем сервис Active Directory и проверяем," +
            " что сервис был успешно добавлен на сервер")
    @Test
    void test_Add_Service_Active_Directory(){
        activeDirectoryPage = (ActiveDirectoryPage) addIntegrationService(INTEGRATION_SERVICE_AD_TYPE);
        assertTrue(activeDirectoryPage.settingsActiveDirectory(mapInputValueAD), "Сервис Active Directory не был добавлен");
    }

    @Story(value = "Синхронизация контактов c Active Directory")
    @Description(value = "Переходим в раздел Интеграция, заходим в сервис Active Directory и нажимаем Синхронизировать")
    @Test(groups = {"Sync"},dependsOnMethods = {"test_Add_Service_Active_Directory"})
    void test_Sync_Contacts_Active_Directory(){
        activeDirectoryPage = (ActiveDirectoryPage) clickServiceType(INTEGRATION_SERVICE_AD_TYPE);
        assertTrue(activeDirectoryPage.syncContacts(), "Ошибка при сихронизации контактов");
    }

    @Story(value = "Перезагрузка страницы")
    @Description(value = "Переходим на страницу Интеграция, перезагружаем страницу и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Refresh_Page(){
        Selenide.refresh();
        sleep(3000);
        assertTrue(isNotShowLoaderSettings(), "Настройки не загрузились, надпись" +
                " 'Идет загрузка настроек...' не пропала");
    }

    @Story(value = "Переходим на страницу через адресную строку")
    @Description(value = "После авторизации вставляем в адресную строку страницу Интеграция и проверяем, появилась ли " +
            "надпись 'Идет загрузка настроек...'")
    @Test
    void test_Open_Page(){
        sleep(3000);
        assertTrue(isNotShowLoaderSettings(), "Настройки не загрузились, надпись" +
                " 'Идет загрузка настроек...' не пропала");
    }
}
