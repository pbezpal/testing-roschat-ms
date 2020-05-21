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
import static org.testng.Assert.assertTrue;

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

    public Map<String, String> getSettingsMailServer(String server, String username, String password, String port,
                                                     String fromUser, String fromMail){
        Map<String, String> dataConnectionMailServer = new HashMap() {{
            put(MAIL_CONNECT_INPUT_EMAIL_SERVER, server);
            put(MAIL_CONNECT_INPUT_USERNAME, username);
            put(MAIL_CONNECT_INPUT_PASSWORD, password);
            put(MAIL_CONNECT_INPUT_EMAIL_PORT, port);
            put(MAIL_CONTACT_INPUT_FROM_USER, fromUser);
            put(MAIL_CONTACT_INPUT_FROM_MAIL, fromMail);
        }};
        return dataConnectionMailServer;
    }

    public MailPage settingsMailServer(Map<String, String> settingsMainServer, String security){
        //Настраиваем раздел Подключение
        setSettingsServer(settingsMainServer, SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        setInputSecurity(security);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);

        //Нажимаем кнопку проверить
        clickButtonSettings(SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_CHECK);

        return this;
    }
}
