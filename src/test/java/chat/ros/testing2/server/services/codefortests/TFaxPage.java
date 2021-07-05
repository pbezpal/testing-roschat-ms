package chat.ros.testing2.server.services.codefortests;

import chat.ros.testing2.server.settings.services.FaxPage;

import static chat.ros.testing2.data.SettingsData.*;

public class TFaxPage extends TServicePage implements IFaxPage {

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
}
