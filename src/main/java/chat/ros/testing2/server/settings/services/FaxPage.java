package chat.ros.testing2.server.settings.services;

public class FaxPage extends ServicesPage {

    public FaxPage sendNumberFaxes(String number, String description, String button){
        sendInputModalWindow("Номер факса", number)
                .sendInputModalWindow("Описание", description)
                .clickActionButtonOfModalWindow(button);
        return this;
    }
}
