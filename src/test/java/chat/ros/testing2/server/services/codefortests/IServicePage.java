package chat.ros.testing2.server.services.codefortests;

public interface IServicePage {

    /**
     * <p>add the contact and checking if the contact is in the contact table after adding</p>
     * @param contact
     */
    void addContact(String section, String contact);

    /**
     * <p>delete a contact and checking if the contact is in the contact table after deleting</p>
     * @param contact
     */
    void deleteContact(String section, String contact);

}
