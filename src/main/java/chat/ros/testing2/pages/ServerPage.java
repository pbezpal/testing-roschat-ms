package chat.ros.testing2.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.gen5.api.Assertions.assertTrue;

public class ServerPage extends SettingsPage{

    //Общие переменные
    private String serverSection = "Сервер";

    //Переменные для настроек подключения
    private String titleFormConnect = "Подключение";
    private String inputPublicNetwork = "Внешний адрес сервера";
    private String hostPublickNetwork = "testing2.ros.chat";
    private String textCheckServer = "Настройки сервера корректны";

    //Переменные для настроек сертификата ssl
    private String titleFormCertificate = "Сертификат";
    private SelenideElement inputSSLCertificate = $("label[for='sertUpload'] i");
    private SelenideElement inputPrivateKey = $("label[for='keyUpload'] i");

    //Переменные для настроек лицензирования и обслуживания
    private String titleFormLicenseAndService = "Лицензирование и обсуживание";
    private String inputHostPushServer = "IP адрес";
    private String valueHostPushServer = "firelink.me";
    private String inputLoginPushServer = "Логин";
    private String valueLoginPushServer = "testing2";
    private String inputPortPushServer = "Порт";
    private String valuePortPushServer = "8088";
    private String inputPasswordPushServer = "Пароль";
    private String valuePasswordPushServer = "lJddfDnwycX0ag7o";
    private String buttonUpdateLicense = "Обновить лицензию";
    private String textCheckLicense = "Лицензия успешно обновлена";

    public static ServerPage serverPage = new ServerPage();
    public static ServerPage getInstance() { return serverPage; }

    public ServerPage setPublicNetwork(){
        //Настраиваем внешний адрес сервера
        if(isNotValueInField(inputPublicNetwork, hostPublickNetwork)){
            //Нажимаем кнопку Настроить
            clickButtonSettings(titleFormConnect, getButtonSetting());
            //Проверяем, появилась ли форма редактирования Подключения
            assertTrue(isFormChange(), "Форма для редактирования не появилась");
            //Вводим в поле Внешний адрес сети публичный host сервера
            sendInputForm(inputPublicNetwork, hostPublickNetwork);
            //Проверяем, что кнопка Сохранить активна
            assertTrue(isActiveButtonSave(), "Не возможно сохранить настройки, кнопка 'Сохранить' не активна");
            //Нажимаем кнопку Сохранить
            clickButtonSave();
            //Проверяем, появилась ли форма для перезагрузки сервисов
            assertTrue(isFormConfirmActions(), "Форма для перезагрузки сервисов не появилась");
            //Нажимаем кнопку для перезагрузки сервисов
            clickButtonRestartServices(getButtonRestartServices());
        }

        //Нажимаем кнопку Проверить
        clickButtonSettings(titleFormConnect, getButtonCheck());
        //Проверяем, появилась ли форма проверки настроек
        assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        //Проверяем, что настройки сервера корректны
        assertTrue(isCheckSettings(textCheckServer), "Настройки сервера некорректны");
        //Нажимаем кнопку закрыть
        clickButtonCloseCheckSettingsForm();

        return this;
    }


    public ServerPage setCertificate(){
        //Нажиаем кнопку Настроить в форме Сертификат
        clickButtonSettings(titleFormCertificate, getButtonSetting());
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

    public ServerPage setPushService(){
        //Нажимаем кнопку Настроить в форме Лицензирование и обсуживание
        clickButtonSettings(titleFormLicenseAndService, getButtonSetting());
        //Проверяем, что появилась форма редактирования Лицензии
        assertTrue(isFormChange(), "Форма для редактирования не появилась");
        //Вводим IP адрес для Push сервера
        sendInputForm(inputHostPushServer, valueHostPushServer);
        //Вводим логин для Push сервера
        sendInputForm(inputLoginPushServer, valueLoginPushServer);
        //Вводим порт для Push сервера
        sendInputForm(inputPortPushServer, valuePortPushServer);
        //Вводим пароль для Push сервера
        sendInputForm(inputPasswordPushServer, valuePasswordPushServer);
        //Проверяем, что кнопка Сохранить активна
        assertTrue(isActiveButtonSave(), "Не возможно сохранить настройки, кнопка 'Сохранить' не активна");
        //Нажимаем кнопку Сохранить
        clickButtonSave();
        //Проверяем, появилась ли форма для перезагрузки сервисов
        assertTrue(isFormConfirmActions(), "Форма для перезагрузки сервисов не появилась");
        //Нажимаем кнопку для перезагрузки сервисов
        clickButtonRestartServices(getButtonRestartServices());
        //Ждём, когда настройки применятся
        assertTrue(isCheckUpdateLicense(), "Настройки не применились");
        //Прокручиваем страницу вниз
        $("html").scrollIntoView(false);
        //Нажимаем кнопку Обновить лицензию
        clickButtonSettings(titleFormLicenseAndService, buttonUpdateLicense);
        //Проверяем, что лицензия успешно обновлена
        assertTrue(isCheckSettings(textCheckLicense), "Настройки сервера некорректны");
        //Нажимаем кнопку закрыть
        clickButtonCloseCheckSettingsForm();

        return this;
    }

}
