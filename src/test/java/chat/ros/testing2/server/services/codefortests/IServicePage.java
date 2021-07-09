package chat.ros.testing2.server.services.codefortests;

import chat.ros.testing2.server.settings.services.ServicesPage;

public interface IServicePage {

    /**
     * <p>check the title text of the modal window when we add an entry<p/>
     * @param section the name section where we add an entry
     * @param title the title text that we check
     */
    void checkTitleTextModalWindowWhenAddItem(String section, String title);

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

    /**
     * <p>check auth to ms alert</p>
     * @param login
     * @param password
     * @param auth if true then check that auth was successful
     *             if false then check that auth failed
     */
     void checkAuthToService(String login, String password, boolean stay, boolean auth, String... textLoginFailed);

     void checkAuthToService(String login, String password, boolean stay);

     void checkElementsInAuthPage();
}
