package chat.ros.testing2.server.services.codefortests;

import chat.ros.testing2.server.settings.services.FaxPage;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class TFaxPage extends TServicePage implements ITFaxPage {

    private FaxPage faxPage = new FaxPage();

    public FaxPage getInstanceFaxPage(){
        return faxPage;
    }

    @Override
    public void addNumberFax(String number, String description) {
        clickButtonAdd(FAX_NUMBERS_TITLE);
        faxPage.sendNumberFaxes(number, description, "Сохранить");
        isModalWindow(false);
        isItemTable(FAX_NUMBERS_TITLE, number, true);
        if( ! description.equals(""))
            isItemTable(FAX_NUMBERS_TITLE, description, true);
    }

    @Override
    public void deleteNumber(String number, String... description) {
        clickButtonTable(FAX_NUMBERS_TITLE, number, IVR_BUTTON_DELETE);
        isFormConfirmActions(true)
                .clickButtonConfirmAction("Удалить");
        isVisibleElement(dialogWrapper, false);
        isItemTable(FAX_NUMBERS_TITLE, number, false);
        if(description.length > 0)
            isItemTable(FAX_NUMBERS_TITLE, description[0], false);
    }

    @Override
    public void checkAuthToService(String login, String password, boolean stay, boolean auth, String... textLoginFailed) {
        faxPage
                .clickLinkToGoMSFax();
        loginService(login, password, stay)
                .isLoginService(auth, textLoginFailed);
    }

    @Override
    public void checkAuthToService(String login, String password, boolean stay) {
        faxPage
                .clickLinkToGoMSFax();
        loginService(login, password, stay)
                .isLoginService(true);
        switchTo().window(getWebDriver().getWindowHandle()).close();
        switchTo().window(0);
        faxPage
                .clickLinkToGoMSFax();
        if(stay) {
            isLoginService(true)
                    .logoutService();
            faxPage.
                    clickButtonConfirmAction("Выйти");
        }
        isImgLogo()
                .isTextTitle("ФАКС");
    }

    @Override
    public void checkElementsInAuthPage() {
        faxPage.
                clickLinkToGoMSFax()
                .isImgLogo()
                .isTextTitle("ФАКС")
                .isButtonSeePassword();
    }

    @Override
    public void checkElementsLeftMenu(String itemMenu, String classNameIcon, String... dataLogin) {
        faxPage
                .clickLinkToGoMSFax();
        isElementsLeftMenu(itemMenu, classNameIcon, dataLogin);
    }
}
