package chat.ros.testing2.server.services.codefortests;

import chat.ros.testing2.server.settings.services.ServicesPage;

public abstract class TServicePage extends ServicesPage implements ITServicePage {

    public void isElementsLeftMenu(String item, String classNameIcon, String... dataLogin){
        if( ! isLoginService()) {
            String login = dataLogin[0];
            String password = dataLogin[1];
            loginService(login, password);
        }
        clickItemLeftMenu(item, true)
                .isIconLeftMenu(item, classNameIcon);
    }

    @Override
    public void checkTitleTextModalWindowWhenAddItem(String section, String title) {
        clickButtonAdd(section)
                .isModalWindow(true)
                .isTitleTextModalWindow(title)
                .clickActionButtonOfModalWindow("Отменить")
                .isModalWindow(false);
    }

    @Override
    public void addContact(String section, String contact) {
        clickButtonAdd(section)
                .sendInputModalWindow("Поиск контакта", contact);
        isContactName(contact, true)
                .getContactName(contact).click();
        clickActionButtonOfModalWindow("Сохранить")
                .isModalWindow(false)
                .isItemTable(section, contact, true);
    }

    @Override
    public void deleteContact(String section, String contact) {
        clickButtonTable(section, contact);
        clickButtonConfirmAction("Продолжить");
        isItemTable(section, contact, false);
    }
}
