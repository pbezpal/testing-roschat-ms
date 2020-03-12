package chat.ros.testing2.pages.settings;

import chat.ros.testing2.helpers.SSHManager;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;

import java.io.File;

import static chat.ros.testing2.data.LoginData.HOST_SERVER;
import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.gen5.api.Assertions.assertTrue;

public class ServerPage extends SettingsPage{

    //Переменные для настроек сертификата ssl
    private String titleFormCertificate = "Сертификат";
    private SelenideElement inputSSLCertificate = $("label[for='sertUpload'] i");
    private SelenideElement inputPrivateKey = $("label[for='keyUpload'] i");

    //Проверки на сервере по ssh
    private String commandCheckEsteblishedPush = "netstat -alpn | grep '8088.*ESTABLISHED'";

    public static ServerPage serverPage = new ServerPage();
    public static ServerPage getInstance() { return serverPage; }

    public ServerPage setPublicNetwork(){
        //Настраиваем внешний адрес сервера
        if(isNotValueInField(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, HOST_SERVER)){
            //Нажимаем кнопку Настроить
            clickButtonSettings(SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
            //Проверяем, появилась ли форма редактирования Подключения
            assertTrue(isFormChange(), "Форма для редактирования не появилась");
            //Вводим в поле Внешний адрес сети публичный host сервера
            sendInputForm(SERVER_CONNECT_INPUT_PUBLIC_NETWORK, HOST_SERVER);
            //Проверяем, что кнопка Сохранить активна
            assertTrue(isActiveButtonSave(), "Невозможно сохранить настройки, кнопка 'Сохранить' не активна");
            //Нажимаем кнопку Сохранить
            clickButtonSave();
            //Проверяем, появилась ли форма для перезагрузки сервисов
            assertTrue(isFormConfirmActions(), "Форма для перезагрузки сервисов не появилась");
            //Нажимаем кнопку для перезагрузки сервисов
            clickButtonRestartServices(SETTINGS_BUTTON_RESTART);
        }

        //Нажимаем кнопку Проверить
        clickButtonSettings(SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_CHECK);
        //Проверяем, появилась ли форма проверки настроек
        assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        //Проверяем, что настройки сервера корректны
        assertTrue(isCheckSettings(), "Настройки сервера некорректны");
        //Нажимаем кнопку закрыть
        clickButtonCloseCheckSettingsForm();

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
    public boolean isEsteblishedPush(){ return SSHManager.isCheckQuerySSH(commandCheckEsteblishedPush); }

    private boolean isUpdateLicense(){
        clickButtonSettings(SERVER_PUSH_TITLE_FORM, SERVER_PUSH_BUTTON_UPDATE_LICENSE);
        boolean update = isCheckSettings();
        clickButtonCloseCheckSettingsForm();
        return update;
    }

    public ServerPage setPushService(){

        boolean result = true;
        String error = "";

        //Нажимаем кнопку Настроить в форме Лицензирование и обсуживание
        clickButtonSettings(SERVER_PUSH_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        //Проверяем, что появилась форма редактирования Лицензии
        assertTrue(isFormChange(), "Форма для редактирования не появилась");
        //Вводим IP адрес для Push сервера
        sendInputForm(SERVER_PUSH_INPUT_HOST, SERVER_PUSH_HOST_SERVER);
        //Вводим логин для Push сервера
        sendInputForm(SERVER_PUSH_INPUT_LOGIN, SERVER_PUSH_LOGIN_SERVER);
        //Вводим порт для Push сервера
        sendInputForm(SERVER_PUSH_INPUT_PORT, SERVER_PUSH_PORT_SERVER);
        //Вводим пароль для Push сервера
        sendInputForm(SERVER_PUSH_INPUT_PASSWORD, SERVER_PUSH_PASSWORD_SERVER);
        //Проверяем, что кнопка Сохранить активна
        assertTrue(isActiveButtonSave(), "Не возможно сохранить настройки, кнопка 'Сохранить' не активна");
        //Нажимаем кнопку Сохранить
        clickButtonSave();
        //Проверяем, появилась ли форма для перезагрузки сервисов
        assertTrue(isFormConfirmActions(), "Форма для перезагрузки сервисов не появилась");
        //Нажимаем кнопку для перезагрузки сервисов
        clickButtonRestartServices(SETTINGS_BUTTON_RESTART);
        //Ждём, когда настройки применятся
        assertTrue(isCheckUpdateLicense(), "Настройки не применились");
        //Прокручиваем страницу вниз
        $("html").scrollIntoView(false);
        //Нажимаем кнопку Обновить лицензию
        if(!isUpdateLicense()) Allure.step("Warning: Не удалось обновить лицензию. Запускаю поторное обновление лицензии.", Status.BROKEN);
        assertTrue(isUpdateLicense(), "Error: Не удалось обновить лицензию");

        return this;
    }
}
