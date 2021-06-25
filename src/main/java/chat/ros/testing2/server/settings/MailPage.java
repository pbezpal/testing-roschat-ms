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

    private SelenideElement iconSelectSecurityMethod = $(".mdi-menu-down");

    public MailPage(){}

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

    public MailPage settingsMailServerWithCheck(Map<String, String> settingsMainServer, String security){
        //Настраиваем раздел Подключение
        setSettingsServer(settingsMainServer, SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        iconSelectSecurityMethod.click();
        isItemsComboBox().selectItemComboBox(security);
        clickButtonSave();
        clickButtonConfirmAction(SETTINGS_BUTTON_RESTART);

        //Нажимаем кнопку проверить
        clickButtonSettings(SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_CHECK);

        return this;
    }

    public MailPage settingsMailServerWithoutSave(Map<String, String> settingsMainServer, String security){
        //Настраиваем раздел Подключение
        setSettingsServer(settingsMainServer, SERVER_CONNECT_TITLE_FORM, SETTINGS_BUTTON_SETTING);
        selectItemComboBox(security);

        return this;
    }
}
