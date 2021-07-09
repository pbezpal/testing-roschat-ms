package chat.ros.testing2.server.services.codefortests;

import chat.ros.testing2.server.LoginPage;
import chat.ros.testing2.server.settings.services.AlertPage;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class TAlertPage extends TServicePage implements IAlertPage {

    private AlertPage alertPage = new AlertPage();
    private LoginPage loginPage = new LoginPage();

    public AlertPage getInstanceAlertPage(){
        return alertPage;
    }

    @Override
    public void addContactForRoschatAlertAndSipAlert(String contact) {
        alertPage.clickButtonAction(ALERT_TITLE_SECTION_SIP_ALERT, ALERT_BUTTON_SELECT_CONTACT_SIP);
        sendInputModalWindow("Поиск контакта", contact);
        isContactName(contact, true)
                .getContactName(contact).click();
        clickActionButtonOfModalWindow("Сохранить")
                .isModalWindow(false);
        alertPage.isTextAfterAction(ALERT_TITLE_SECTION_SIP_ALERT, "Росчат оповещение:", contact);
        alertPage.isTextAfterAction(ALERT_TITLE_SECTION_SIP_ALERT, "SIP-оповещение:", contact);
        alertPage.isTextAfterAction(ALERT_TITLE_SECTION_EMAIL_ALERT, "Отправитель:", contact);
    }

    @Override
    public void checkAuthToService(String login, String password, boolean stay, boolean auth, String... textLoginFailed) {
        alertPage
                .clickLinkToGoMSAlertPage();
        loginService(login, password, stay)
                .isLoginService(auth, textLoginFailed);
    }

    @Override
    public void checkAuthToService(String login, String password, boolean stay) {
        alertPage
                .clickLinkToGoMSAlertPage()
                .loginService(login, password, stay)
                .isLoginService(true);
        switchTo().window(getWebDriver().getWindowHandle()).close();
        switchTo().window(0);
        refresh();
        alertPage
                .clickLinkToGoMSAlertPage();
        if(stay) {
            isLoginService(true)
                    .logoutService();
            alertPage.
                    clickButtonConfirmAction("Продолжить");
        }
        isImgLogo()
                .isTextTitle("Система оповещения");

    }

    @Override
    public void checkElementsInAuthPage() {
        alertPage
                .clickLinkToGoMSAlertPage()
                .isImgLogo()
                .isTextTitle("Система оповещения")
                .isButtonSeePassword();
    }
}
