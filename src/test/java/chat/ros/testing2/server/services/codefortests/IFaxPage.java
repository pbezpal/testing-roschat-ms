package chat.ros.testing2.server.services.codefortests;

public interface IFaxPage {

    void addNumberFax(String number, String description);

    void deleteNumber(String number, String... description);

}
