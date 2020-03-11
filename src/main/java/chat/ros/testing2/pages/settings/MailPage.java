package chat.ros.testing2.pages.settings;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.gen5.api.Assertions.assertTrue;

public class MailPage extends SettingsPage {

    //Элементы для раздела Подключение
    private SelenideElement elementListTypeSecurity = $("div[role='combobox'] i.v-icon.material-icons.theme--light");
    private SelenideElement divComboboxActive = $("div.v-select--is-menu-active");
    private ElementsCollection divTypeSecurity = $$("div.v-list.theme--light div.v-list__tile__title");

    @Step(value = "Проверяем, что появился список с выбором защиты соединения")
    public boolean isListMethodSecurity(){
        try{
            divComboboxActive.shouldBe(Condition.visible);
        }catch (ElementNotFound element){
            return false;
        }

        return true;
    }

    @Step(value = "Выбираем метод защиты соединения {security}")
    public MailPage setInputSecurity(String security){
        //Нажимаем на input для выбора метода защиты
        elementListTypeSecurity.click();
        //Проверяем, что появился список с методами защиты
        assertTrue(isListMethodSecurity(), "Не появился список с методами защиты соединения");
        //Выбираем метод зациты соединения
        divTypeSecurity.findBy(Condition.text(security)).click();

        return this;
    }

    public MailPage setConnectionMailServer(String server, String username, String password, String port){
        Map<String, String> dataConnectionMailServer = new HashMap<>();
        dataConnectionMailServer.put(MAIL_CONNECT_INPUT_EMAIL_SERVER, server);
        dataConnectionMailServer.put(MAIL_CONNECT_INPUT_USERNAME, username);
        dataConnectionMailServer.put(MAIL_CONNECT_INPUT_PASSWORD, password);
        dataConnectionMailServer.put(MAIL_CONNECT_INPUT_EMAIL_PORT, port);
        setInputForm(dataConnectionMailServer);
        return this;
    }

    public MailPage setContactInfoMail(String fromUser, String fromMail){
        Map<String, String> dataContactInfoMail = new HashMap<>();
        dataContactInfoMail.put(MAIL_CONTACT_INPUT_FROM_USER, fromUser);
        dataContactInfoMail.put(MAIL_CONTACT_INPUT_FROM_MAIL, fromMail);
        setInputForm(dataContactInfoMail);
        return this;
    }

    public MailPage setInputForm(Map<String, String> setInputs){
        for(Map.Entry<String, String> setInput:setInputs.entrySet()){
            sendInputForm(setInput.getKey(), setInput.getValue());
        }
        return this;
    }

    public MailPage checkSettingsMailServer(String server, String username, String password, String port, String security, String fromUser, String fromMail){
        //Нажимаем кнопку Настроить в разделе Подключение
        clickButtonSettings(SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        //Проверяем, что появилась форма для редактирования
        assertTrue(isFormChange(), "Форма для редактирования не появилась");
        //Заполняем поля формы редактирования
        setConnectionMailServer(server, username, password, port);
        //Выбираем тип защиты соединения
        setInputSecurity(security);
        //Проверяем, что кнопка Сохранить активна
        assertTrue(isActiveButtonSave(), "Невозможно сохранить настройки, кнопка 'Сохранить' не активна");
        //Нажимаем кнопку Сохранить
        clickButtonSave();
        //Проверяем, появилась ли форма для перезагрузки сервисов
        assertTrue(isFormConfirmActions(), "Форма для перезагрузки сервисов не появилась");
        //Нажимаем кнопку для перезагрузки сервисов
        clickButtonRestartServices(SETTINGS_BUTTON_RESTART);

        //Нажимаем кнопку Настроить в разделе Контактная информация
        clickButtonSettings(MAIL_CONTACT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        //Проверяем, что появилась форма для редактирования
        assertTrue(isFormChange(), "Форма для редактирования не появилась");
        //Заполняем поля формы редактирования
        //setConnectionMailServer("mail.infotek.ru", "Noreply.roschat", "SmGRz6bc", "25");
        setContactInfoMail(fromUser, fromMail);
        //Проверяем, что кнопка Сохранить активна
        assertTrue(isActiveButtonSave(), "Невозможно сохранить настройки, кнопка 'Сохранить' не активна");
        //Нажимаем кнопку Сохранить
        clickButtonSave();

        //Нажимаем кнопку Проверить
        clickButtonSettings(SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_CHECK);
        //Проверяем, появилась ли форма проверки настроек
        assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        //Проверяем, что настройки сервера корректны
        assertTrue(isCheckSettings(MAIL_CONNECT_TEXT_CHECK), "Настройки сервера некорректны");
        //Нажимаем кнопку закрыть
        clickButtonCloseCheckSettingsForm();

        return this;
    }
}
