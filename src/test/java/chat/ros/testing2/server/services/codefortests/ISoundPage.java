package chat.ros.testing2.server.services.codefortests;

import com.codeborne.selenide.SelenideElement;

public interface ISoundPage {

    void uploadMusicFile(String pathToFile, String filename);

    void uploadMusicFile(String pathToFile, String filename, String durationFile, String volumePlayer);

    void verifyButtonAudioPlayer(String oldFilename, String durationFile, String volumePlayer);

    void editMusicFile(String filename, String newFilename, String pathToFile, String durationFile, String volumePlayer);

    void downloadMusicFile(String filename);

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
