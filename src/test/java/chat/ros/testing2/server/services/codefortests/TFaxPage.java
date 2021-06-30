package chat.ros.testing2.server.services.codefortests;

import chat.ros.testing2.server.settings.services.FaxPage;

import static chat.ros.testing2.data.SettingsData.CONTACT_FOR_FAX;
import static chat.ros.testing2.data.SettingsData.FAX_USERS_TITLE;

public class TFaxPage extends FaxPage implements IFaxPage {

    @Override
    public void addContact(String contact) {
        clickButtonAdd(FAX_USERS_TITLE)
                .sendInputModalWindow("Поиск контакта", CONTACT_FOR_FAX);
        isContactName(CONTACT_FOR_FAX, true)
                .getContactName(CONTACT_FOR_FAX).click();
        clickActionButtonOfModalWindow("Сохранить")
                .isModalWindow(false)
                .isItemTable(FAX_USERS_TITLE, CONTACT_FOR_FAX, true);
    }

    @Override
    public void deleteContact(String contact) {

    }
}
