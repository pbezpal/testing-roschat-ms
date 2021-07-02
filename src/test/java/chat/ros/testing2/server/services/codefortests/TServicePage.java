package chat.ros.testing2.server.services.codefortests;

import chat.ros.testing2.server.settings.services.ServicesPage;

public class TServicePage extends ServicesPage implements IServicePage {

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
        clickButtonTable(section, contact)
                .isItemTable(section, contact, false);
    }
}
