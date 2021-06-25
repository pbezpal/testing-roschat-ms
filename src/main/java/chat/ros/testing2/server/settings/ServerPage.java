package chat.ros.testing2.server.settings;

import chat.ros.testing2.helpers.SSHManager;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;

import java.io.File;
import java.time.Duration;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.*;
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
        isActiveButtonSave()
                .clickButtonSave();

        return this;
    }

    @Step(value = "Проверяем, что Лицензия успешно обновилась и иконка с красной поменялась на зелёную")
    public boolean isCheckLicense(){
        $("html").scrollIntoView(false);
        SelenideElement parentLicense = $$(".content-license h4")
                .findBy(Condition.text("Лицензия"))
                .parent();
        try {
            parentLicense.$(".success--text").shouldBe(Condition.visible, Duration.ofSeconds(5));
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, что Лицензирования применились для сервера услуг {service}")
    public boolean isCheckLicenseServices(String service){
        SelenideElement parentService = $$(".block-sorm .block-body__item-text")
                .findBy(Condition.text(service))
                .parent();
        try {
            parentService.find(".accent--text").shouldBe(Condition.visible, Duration.ofSeconds(10));
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, что Лицензия применилась для {action}")
    public boolean isCheckLicenseAction(String action){
        //SelenideElement parentAction = $$("h4").findBy(Condition.text(action)).
        SelenideElement parentAction = $x("//h4[text()='" + action + "']//ancestor::div[@class='block-body__item']");
        try {
            parentAction.find(".accent--text").shouldBe(Condition.visible, Duration.ofSeconds(10));
        }catch (ElementNotFound e){
            return false;
        }

        return true;
    }

    @Step(value = "Проверяем, установилось ли соединение с Push сервером")
    public boolean isEstablishedPush(){ return SSHManager.isCheckQuerySSH(commandCheckEstablishedPush); }

    public boolean isUpdateLicense(){
        $("html").scrollIntoView(false);
        clickButtonSettings(SERVER_PUSH_TITLE_FORM, SERVER_PUSH_BUTTON_UPDATE_LICENSE);
        boolean update = getTextModalWindow("h4").equals("Лицензия успешно обновлена");
        clickButtonCloseCheckSettingsForm();
        return update;
    }

    public ServerPage setPushService(Map<String, String> mapInputValuePush){
        setSettingsServer(mapInputValuePush, SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);
        return this;
    }
}
