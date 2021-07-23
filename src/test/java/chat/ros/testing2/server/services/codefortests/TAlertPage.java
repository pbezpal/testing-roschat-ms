package chat.ros.testing2.server.services.codefortests;

import chat.ros.testing2.helpers.mailclient.MailClient;
import chat.ros.testing2.server.LoginPage;
import chat.ros.testing2.server.settings.services.AlertPage;

import javax.mail.Folder;
import java.util.Map;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class TAlertPage extends TServicePage implements ITAlertPage {

    private AlertPage alertPage = new AlertPage();
    private LoginPage loginPage = new LoginPage();
    private MailClient mailClient = null;
    private Folder folder = null;

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
        clickButtonConfirmAction("Перезапустить");
        alertPage.isTextAfterAction(ALERT_TITLE_SECTION_SIP_ALERT, "Росчат оповещение:", contact);
        alertPage.isTextAfterAction(ALERT_TITLE_SECTION_SIP_ALERT, "SIP-оповещение:", contact);
        alertPage.isTextAfterAction(ALERT_TITLE_SECTION_EMAIL_ALERT, "Отправитель:", contact);
    }

    @Override
    public void checkListTopMenuItems(String item, String... dataLogin) {
        alertPage
                .clickLinkToGoMSAlertPage();
        if( ! isLoginService()) {
            String login = dataLogin[0];
            String password = dataLogin[1];
            loginService(login, password);
        }
        alertPage
            .clickItemTopMenu(item, true);
    }

    @Override
    public void checkStatusAlertServices(String alert, String classNameIcon, String status, String login, String password) {
        alertPage
                .clickLinkToGoMSAlertPage();
        if( ! isLoginService()) {
            loginService(login, password);
        }
        alertPage
                .isStatusAlert(alert, classNameIcon, status);
    }

    @Override
    public void checkStatusAlertServices(String alert, String classNameIcon, String status, String error, String login, String password) {
        alertPage
                .clickLinkToGoMSAlertPage();
        if( ! isLoginService()) {
            loginService(login, password);
        }
        alertPage
                .isStatusAlert(alert, classNameIcon, status, error);
    }

    @Override
    public void uploadMusicFile(String file, String filename, String login, String password) {
        alertPage
                .clickLinkToGoMSAlertPage();
        if( ! isLoginService()) {
            loginService(login, password);
        }
        alertPage
                .clickItemTopMenu(ALERT_TITLE_ITEM_TOP_MENU_SOUND_FILES, false);
        uploadSoundFile(file)
                .isModalWindow(true);
        alertPage
                .sendFieldModalWindow(ALERT_INPUT_NAME, filename)
                .clickButtonOfModalWindow("Сохранить");
        isModalWindow(false);
        alertPage
                .isVisibleValueOfTable(filename, true);
    }

    @Override
    public void checkTitleOfModalWindow(String itemMenu, String title, String login, String password) {
        alertPage
                .clickLinkToGoMSAlertPage();
        if( ! isLoginService()) {
            loginService(login, password);
        }
        alertPage
                .clickItemTopMenu(itemMenu, false)
                .clickButtonAddService();
        isModalWindow(true)
                .isTitleTextModalWindow(title);
        if(itemMenu.equals("Списки оповещения"))
            alertPage
                    .clickButtonOfModalWindow("Закрыть");
        else
            alertPage
                    .clickButtonOfModalWindow("Отменить");
        isModalWindow(false);
    }

    @Override
    public void checkTitleOfModalWindow(String file, String login, String password) {
        alertPage
                .clickLinkToGoMSAlertPage();
        if( ! isLoginService()) {
            loginService(login, password);
        }
        alertPage
                .clickItemTopMenu(ALERT_TITLE_ITEM_TOP_MENU_SOUND_FILES, false);
        uploadSoundFile(file)
                .isModalWindow(true);
        alertPage
                .clickButtonOfModalWindow("Закрыть");
        isModalWindow(false);
    }

    @Override
    public void addListAlert(Map<String, String> dataListAlert, String contact, String[] listClassNameIcon, String email, String login, String password) {
        alertPage
                .clickLinkToGoMSAlertPage();
        if( ! isLoginService()) {
            loginService(login, password);
        }
        alertPage
                .clickItemTopMenu(ALERT_TITLE_ITEM_TOP_MENU_LIST_ALERT, false)
                .clickButtonAddService();
        isModalWindow(true);
        for(Map.Entry data : dataListAlert.entrySet()){
            alertPage
                    .sendFieldModalWindow(data.getKey().toString(), data.getValue().toString());
        }
        alertPage
                .clickComboBox("Добавить контакт")
                .selectValueFromList(contact);
        for(String classNameIcon : listClassNameIcon){
            alertPage
                    .isIconHeartsAlert(contact, classNameIcon);
        }
        alertPage
                .clickListCheckBoxServicesOfContact(contact)
                .isSelectServiceAlertOfContact(contact, "sms", true)
                .isSelectServiceAlertOfContact(contact, "sip", true)
                .isSelectServiceAlertOfContact(contact, "email", true, email);
        alertPage
                .clickButtonOfModalWindow("Сохранить");
        isModalWindow(false);
        for(Map.Entry data : dataListAlert.entrySet()){
            alertPage
                    .isVisibleValueOfTable(String.valueOf(data.getValue()), true);
        }
    }

    @Override
    public void addMessages(Map<String, String> dataMessages, String login, String password) {
        alertPage
                .clickLinkToGoMSAlertPage();
        if( ! isLoginService()) {
            loginService(login, password);
        }
        alertPage
                .clickItemTopMenu(ALERT_TITLE_ITEM_TOP_MENU_MESSAGES, false)
                .clickButtonAddService();
        isModalWindow(true);
        for(Map.Entry data : dataMessages.entrySet()){
            alertPage
                    .sendFieldModalWindow(data.getKey().toString(), data.getValue().toString());
        }
        alertPage
                .clickButtonOfModalWindow("Сохранить");
        isModalWindow(false);
        for(Map.Entry data : dataMessages.entrySet()){
            alertPage
                    .isVisibleValueOfTable(String.valueOf(data.getValue()), true);
        }
    }

    @Override
    public void addListTask(Map<String, String> dataListTasks, String listAlert, String message, String soundFile, String login, String password) {
        alertPage
                .clickLinkToGoMSAlertPage();
        if( ! isLoginService()) {
            loginService(login, password);
        }
        alertPage
                .clickItemTopMenu(ALERT_TITLE_ITEM_TOP_MENU_TASKS, false)
                .clickButtonAddService();
        for(Map.Entry data : dataListTasks.entrySet()){
            alertPage
                    .sendFieldModalWindow(data.getKey().toString(), data.getValue().toString());
        }
        alertPage
                .clickComboBox("Список оповещения")
                .selectValueFromList(listAlert)
                .clickOnOffAlert("SIP")
                .clickComboBox("Звуковой файл")
                .selectValueFromList(soundFile)
                .clickOnOffAlert("Email")
                .clickComboBox("Текстовое сообщение")
                .selectValueFromList(message)
                .clickButtonOfModalWindow("Сохранить")
                .isVisibleValueOfTable(listAlert, true);
        for(Map.Entry dataTask : dataListTasks.entrySet()){
            alertPage
                    .isVisibleValueOfTable(dataTask.getValue().toString(), true);
        }
    }

    @Override
    public void startTask(String task, String loginMail, String passwordMail, String mailServer, String login, String password, String... dataMail) {
        mailClient = new MailClient(mailServer, loginMail, passwordMail);
        mailClient.deleteAllMailFromMailServer();
        alertPage
                .clickLinkToGoMSAlertPage();
        if( ! isLoginService()) {
            loginService(login, password);
        }
        alertPage
                .clickItemTopMenu(ALERT_TITLE_ITEM_TOP_MENU_TASKS, false)
                .clickButtonActionOfTable(task, "Запустить")
                .clickButtonConfirmAction(
                        "Подтвердите запуск задания"
                        , "Данное действие запустит задание \"" + task + "\". Вы уверены, что хотите сделать это?"
                        , "Подтвердить"
                );
                //.clickButtonConfirmAction("Подтвердить");
        alertPage
                .isVisibleTaskProgress(true)
                .isTextTaskProgress("Инициализация...")
                .isTextTaskProgress("Выполняется...")
                .isVisibleTaskProgress(false);
        if(dataMail.length > 1){
            mailClient.getSubjectFromLastMail(dataMail[0]);
            mailClient.getContentFromLastMail(dataMail[1]);
        }else
            mailClient.getContentFromLastMail(dataMail[0]);

    }

    @Override
    public void checkAuthToService(String login, String password, boolean stay, boolean auth, String... textLoginFailed) {
        alertPage
                .clickLinkToGoMSAlertPage();
        if(isLoginService()){
            logoutService();
            alertPage
                    .clickButtonConfirmAction(
                            "Подтверждение выхода"
                            ,"Вы уверены, что хотите выйти из приложения?"
                            ,"Продолжить"
                    );
        }
        loginService(login, password, stay)
                .isLoginService(auth, textLoginFailed);
    }

    @Override
    public void checkAuthToService(String login, String password, boolean stay) {
        alertPage
                .clickLinkToGoMSAlertPage();
        if(isLoginService()){
            logoutService();
            alertPage
                    .clickButtonConfirmAction(
                            "Подтверждение выхода"
                            ,"Вы уверены, что хотите выйти из приложения?"
                            ,"Продолжить"
                    );
        }
        loginService(login, password, stay)
                .isLoginService(true);
        switchTo().window(getWebDriver().getWindowHandle()).close();
        switchTo().window(0);
        refresh();
        alertPage
                .clickLinkToGoMSAlertPage();
        if(stay) {
            isLoginService(true)
                    .logoutService();
            alertPage
                    .clickButtonConfirmAction(
                            "Подтверждение выхода"
                            ,"Вы уверены, что хотите выйти из приложения?"
                            ,"Продолжить"
                    );
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

    @Override
    public void checkElementsLeftMenu(String itemMenu, String classNameIcon, String... dataLogin) {
        alertPage
                .clickLinkToGoMSAlertPage();
        isElementsLeftMenu(itemMenu, classNameIcon, dataLogin);
    }
}
