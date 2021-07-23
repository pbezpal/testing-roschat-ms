package chat.ros.testing2.server.services.codefortests;

import java.util.Map;

public interface ITAlertPage {

    void addContactForRoschatAlertAndSipAlert(String contact);

    void checkListTopMenuItems(String item, String... dataLogin);

    void checkStatusAlertServices(String alert, String classNameIcon, String status, String login, String password);

    void checkStatusAlertServices(String alert, String classNameIcon, String status, String error, String login, String password);

    void uploadMusicFile(String file, String filename, String login, String password);

    void checkTitleOfModalWindow(String itemMenu, String title, String login, String password);

    void checkTitleOfModalWindow(String file, String login, String password);

    void addListAlert(Map<String, String> dataListAlert, String contact, String[] listClassNameIcon, String email, String login, String password);

    void addMessages(Map<String, String> dataMessages, String login, String password);

    void addListTask(Map<String, String> dataListTasks, String listAlert, String message, String soundFile, String login, String password);

    void startTask(String task, String loginMail, String passwordMail, String mailServer, String login, String password, String... dataMail);
}
