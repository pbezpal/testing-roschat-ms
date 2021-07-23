package chat.ros.testing2.server.services.codefortests;

import com.codeborne.selenide.SelenideElement;

public interface ITIVRPage {

    /**
     * <p>check title text of modal window when we upload sound file</p>
     * @param pathFile path to sound file
     * @param filename name sound file
     * @param text title text of modal window
     */
    void checkTitleTextModalWindowWhenUploadFile(String pathFile, String filename, String text);

    /**
     * <p>check the title text of the modal window when we add an entry</p>
     * @param section
     * @param text
     */
    void checkTitleTextModalWindowWhenAddItem(String section, String text);

    /**
     * <p>check title text of modal window when we edit item</p>
     * @param item the item of table
     * @param text the title text of modal window
     * @param button the name of the button we are pressing
     */
    void checkTitleTextWhenEditItem(String section, String item, String text, String button);

    /**
     * <p>check the title of the modal window when we add or edit rules</p>
     * @param schedule the schedule name that we select
     * @param text the title text of the modal window that we check
     * @param rules the rules that we select if select not empty
     */
    void checkTitleTextModalWindowWhenActionRules(String schedule, String text, String... rules);

    /**
     * <p>upload music file and check what music file add to the table</p>
     * @param pathToFile it is path to the music file
     * @param filename
     */
    void uploadMusicFile(String pathToFile, String filename);

    /**
     * <p>checks the operation the audio player when upload a music file</p>
     * @param pathToFile
     * @param filename
     * @param durationFile
     * @param volumePlayer
     */
    void uploadMusicFile(String pathToFile, String filename, String durationFile, String volumePlayer);

    /**
     * <p>checks the start button of the audio player and the operation the audio player</p>
     * @param oldFilename
     * @param durationFile
     * @param volumePlayer
     */
    void verifyButtonAudioPlayer(String oldFilename, String durationFile, String volumePlayer);

    /**
     * <p>verifies editing a music file and the operation the audio player</>
     * @param filename
     * @param newFilename
     * @param pathToFile
     * @param durationFile
     * @param volumePlayer
     */
    void editMusicFile(String filename, String newFilename, String pathToFile, String durationFile, String volumePlayer);

    /**
     * <p>verifies the download of a music file to the computer</p>
     * @param filename
     */
    void downloadMusicFile(String filename);

    /**
     * <p>check the delete of a music file and verifies exists of a
     * music file in the table of music files after deletion</p>
     * @param filename
     */
    void deleteMusicFile(String filename);

    void addMenu(String name, String type, String description, String pathSound, String number, String... goToMenu);

    void addSchedule(String name);

    String actionOnRules(String schedule, String typeDate, String[] dates, String[] startTimes, String[] endTimes, boolean except, String... rules);

    void addEntryPoint(String number, String aon, String menu, String schedule);

    void editMenu(String newNameMenu, String oldNameMenu, String type, String description, String pathSound, String numberOrTypeMenu);

    void editEntryPoint(String number, String aon, String oldMenu, String newMenu, String schedule);

    void editSchedule(String oldSchedule, String newSchedule);

    void checkLookModalWindowOfMenu(String title, String type, SelenideElement parent, String soundFile, boolean dtmf, String ...number);

    void checkLookModalWindowOfMenu(String title, String textGoToMenu, String secondTextLink, String type, String soundFile, boolean action, boolean dtmfSimpleMenu, boolean dtmf, String... number);

    void deleteMenu(String name, String description);

    void deleteEntryPoint(String titleMenu, String[] data);

    void deleteSchedule(String schedule);

    void deleteRules(String schedule, String date, String time);

}
