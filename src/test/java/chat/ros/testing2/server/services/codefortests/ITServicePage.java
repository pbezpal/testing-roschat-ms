package chat.ros.testing2.server.services.codefortests;

import chat.ros.testing2.server.settings.services.ServicesPage;

public interface ITServicePage {

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
     * <p>
     *     authorization check to the ms service
     * </p>
     * @param login
     * @param password
     * @param auth if true then check that auth was successful
     *             if false then check that auth failed
     */
     void checkAuthToService(String login, String password, boolean stay, boolean auth, String... textLoginFailed);

    /**
     * <p>
     *     authorization check with and without stay in the system
     * </p>
     * @param login
     * @param password
     * @param stay
     */
     void checkAuthToService(String login, String password, boolean stay);

    /**
     * <p>
     *     elements check on the authorization page
     * </p>
     */
    void checkElementsInAuthPage();

    /**
     * <p>
     *     elements check in the left menu of service page
     * </p>
     * @param itemMenu is the item menu
     * @param classNameIcon is the icon's class of the item menu
     */
     void checkElementsLeftMenu(String itemMenu, String classNameIcon, String... dataLogin);
}
