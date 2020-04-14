package chat.ros.testing2.server.settings;

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

public class MailPage implements SettingsPage {

    //Элементы для раздела Подключение
    private SelenideElement elementListTypeSecurity = $("div[role='combobox'] i.v-icon.material-icons.theme--light");
    private SelenideElement divComboboxActive = $("div.v-select--is-menu-active");
    private ElementsCollection divTypeSecurity = $$("div.v-list.theme--light div.v-list__tile__title");

    public MailPage(){}

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

    public Map<String, String> getConnectionMailServer(String server, String username, String password, String port){
        Map<String, String> dataConnectionMailServer = new HashMap() {{
            put(MAIL_CONNECT_INPUT_EMAIL_SERVER, server);
            put(MAIL_CONNECT_INPUT_USERNAME, username);
            put(MAIL_CONNECT_INPUT_PASSWORD, password);
            put(MAIL_CONNECT_INPUT_EMAIL_PORT, port);
        }};
        return dataConnectionMailServer;
    }

    public Map<String, String> getContactInfoMail(String fromUser, String fromMail){
        Map<String, String> dataContactInfoMail = new HashMap() {{
            put(MAIL_CONTACT_INPUT_FROM_USER, fromUser);
            put(MAIL_CONTACT_INPUT_FROM_MAIL, fromMail);
        }};
        return dataContactInfoMail;
    }

    public MailPage checkSettingsMailServer(String server, String username, String password, String port, String security, String fromUser, String fromMail){
        //Настраиваем раздел Подключение
        setSettingsServer(getConnectionMailServer(server, username, password, port), SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        setInputSecurity(security);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);

        //Настраиваем раздел Контактная информация
        setSettingsServer(getContactInfoMail(fromUser, fromMail), MAIL_CONTACT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        clickButtonSave();
        clickButtonSettings(SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_CHECK);
        assertTrue(isFormCheckSettings(), "Форма проверки настроек не появилась");
        assertTrue(isCheckSettings(), "Настройки сервера некорректны");
        clickButtonCloseCheckSettingsForm();

        return this;
    }
}
