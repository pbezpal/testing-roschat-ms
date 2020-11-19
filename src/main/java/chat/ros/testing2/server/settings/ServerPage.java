package chat.ros.testing2.server.settings;

import chat.ros.testing2.helpers.SSHManager;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;

import java.io.File;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.gen5.api.Assertions.assertTrue;

public class ServerPage implements SettingsPage{

    //Переменные для настроек сертификата ssl
    private String titleFormCertificate = "Сертификат";
    private SelenideElement inputSSLCertificate = $("label[for='sertUpload'] i");
    private SelenideElement inputPrivateKey = $("label[for='keyUpload'] i");

    //Проверки на сервере по ssh
    private String commandCheckEstablishedPush = "netstat -alpn | grep '8088.*ESTABLISHED'";

    public static ServerPage serverPage = new ServerPage();
    public static ServerPage getInstance() { return serverPage; }

    public ServerPage setSectionConnect(Map<String, String> mapInputValueConnect){
        //Настраиваем внешний адрес сервера, HTTP порт, HTTPS порт, WebSocket порт
        setSettingsServer(mapInputValueConnect, SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);

        //Нажимаем кнопку Проверить
        clickButtonSettings(SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_CHECK);
        return this;
    }


    public ServerPage setCertificate(){
        //Нажиаем кнопку Настроить в форме Сертификат
        clickButtonSettings(titleFormCertificate, SETTINGS_BUTTON_SETTING);
        //Загружаем SSL сертификат
        inputSSLCertificate.uploadFile(new File("/opt/license/cert.crt"));
        //Загружаем приватный ключ
        inputPrivateKey.uploadFile(new File("/opt/license/private.key"));
        //Проверяем, что кнопка Сохранить активна
        assertTrue(isActiveButtonSave(), "Не возможно сохранить настройки, кнопка 'Сохранить' не активна");
        //Нажимаем кнопку Сохранить
        clickButtonSave();

        return this;
    }

    @Step(value = "Проверяем, что настройки Push сервера применились")
    public boolean isCheckUpdateLicense(){
        try {
            $("div.v-input--is-label-active").waitUntil(Condition.enabled, 30000);
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, установилось ли соединение с Push сервером")
    public boolean isEsteblishedPush(){ return SSHManager.isCheckQuerySSH(commandCheckEstablishedPush); }

    private boolean isUpdateLicense(){
        clickButtonSettings(SERVER_PUSH_TITLE_FORM, SERVER_PUSH_BUTTON_UPDATE_LICENSE);
        if(isShowElement(modalWindow, true)) return false;
        boolean update = getTextModalWindow("h4").equals("Лицензия успешно обновлена");
        clickButtonCloseCheckSettingsForm();
        return update;
    }

    public ServerPage setPushService(Map<String, String> mapInputValuePush){
        setSettingsServer(mapInputValuePush, SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        //Ждём, когда настройки применятся
        assertTrue(isCheckUpdateLicense(), "Настройки не применились");
        //Прокручиваем страницу вниз
        $("html").scrollIntoView(false);
        //Нажимаем кнопку Обновить лицензию
        if(!isUpdateLicense()) Allure.step("Warning: Не удалось обновить лицензию. Запускаю поторное обновление лицензии.", Status.BROKEN);
        sleep(30000);
        assertTrue(isUpdateLicense(), "Не удалось обновить лицензию");

        return this;
    }
}
